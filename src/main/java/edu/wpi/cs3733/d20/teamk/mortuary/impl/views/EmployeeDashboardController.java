package edu.wpi.cs3733.d20.teamk.mortuary.impl.views;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.d20.teamk.mortuary.Employee;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryService;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryServiceException;
import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamEnvironment;
import io.github.socraticphoenix.jamfx.JamProperties;
import io.github.socraticphoenix.jamfx.JamProperty;
import java.util.LinkedHashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmployeeDashboardController extends JamController {
  @FXML private JFXListView<Pane> list;

  @FXML private JFXTextField id;
  @FXML private JFXTextField username;
  @FXML private JFXTextField name;

  @JamProperty("css")
  private String css;

  private Map<Employee, Pair<EmployeeBlockController, Pane>> panes = new LinkedHashMap<>();

  public EmployeeDashboardController(
      JamEnvironment environment, JamProperties properties, Scene scene) {
    super(environment, properties, scene);
  }

  @Override
  public void init() {
    super.init();
    this.getScene().getStylesheets().add(this.css);

    this.list.setCellFactory(
        nodeListView -> {
          JFXListCell<Pane> cell = new JFXListCell<>();
          cell.getStylesheets().add(this.css);
          cell.setAlignment(Pos.CENTER);
          return cell;
        });

    this.list.setSelectionModel(NoSelectionModel.emptySelection());

    refresh();
  }

  @Override
  public void refresh() {
    super.refresh();
    fetchEmployee();
    buildList();
  }

  private void fetchEmployee() {
    try {
      this.panes.clear();
      for (Employee employee : MortuaryService.instance().getEmployees()) {
        Pair<EmployeeBlockController, Pane> loaded =
            JamController.load(
                EmployeeBlockController.class.getResource("employeeBlock.fxml"),
                this.getScene(),
                this.getEnvironment(),
                this.makePopupProperties().put("employee", employee));
        this.panes.put(employee, loaded);
      }
    } catch (MortuaryServiceException e) {
      log.error("Failed to refresh tickets", e);
    }
  }

  private void buildList() {
    this.list.getItems().clear();
    for (Pair<EmployeeBlockController, Pane> loaded : this.panes.values()) {
      if (this.filter(loaded.getKey().getEmployee())) {
        this.list.getItems().add(loaded.getValue());
      }
    }
  }

  private boolean filter(Employee employee) {
    return true;
  }

  @FXML
  private void onNew(ActionEvent actionEvent) {
    Employee employee = new Employee("Guest", "guest");
    this.switchView("newEmployee.fxml", this.makeChildProperties().put("employee", employee));
  }

  @FXML
  private void onViewRequests(ActionEvent actionEvent) {
    this.switchView("dashboard.fxml");
  }
}
