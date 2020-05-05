package edu.wpi.cs3733.d20.teamk.mortuary.impl.views;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.d20.teamk.mortuary.Circumstance;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryRequest;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryService;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryServiceException;
import edu.wpi.cs3733.d20.teamk.mortuary.PermissionLevel;
import edu.wpi.cs3733.d20.teamk.mortuary.Person;
import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamEnvironment;
import io.github.socraticphoenix.jamfx.JamProperties;
import io.github.socraticphoenix.jamfx.JamProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class DashboardController extends JamController {
  @FXML private JFXComboBox<String> circumstance;
  @FXML private JFXDatePicker start;
  @FXML private JFXDatePicker end;
  @FXML private JFXTextField deceased;
  @FXML private JFXCheckBox openOnly;

  @FXML private JFXListView<Pane> list;

  @JamProperty("permissions")
  private PermissionLevel permissions;

  @JamProperty("css")
  private String css;

  private Map<MortuaryRequest, Pair<RequestBlockController, Pane>> panes = new LinkedHashMap<>();

  public DashboardController(JamEnvironment environment, JamProperties properties, Scene scene) {
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

    this.circumstance.getItems().add("ANY");
    for (Circumstance circumstance : Circumstance.values()) {
      this.circumstance.getItems().add(circumstance.toString());
    }
    this.circumstance.getSelectionModel().select("ANY");

    this.end.setValue(LocalDate.now().plusDays(1));
    this.start.setValue(LocalDate.now().minusDays(31));

    refresh();

    this.circumstance
        .getSelectionModel()
        .selectedItemProperty()
        .addListener((o, a, b) -> buildList());
    this.start.valueProperty().addListener((o, a, b) -> buildList());
    this.end.valueProperty().addListener((o, a, b) -> buildList());
    this.deceased.textProperty().addListener((o, a, b) -> buildList());
    this.openOnly.selectedProperty().addListener((o, a, b) -> buildList());


  }

  @Override
  public void refresh() {
    super.refresh();
    fetchTickets();
    buildList();
  }

  private void fetchTickets() {
    try {
      this.panes.clear();
      for (MortuaryRequest request : MortuaryService.instance().getRequests()) {
        Pair<RequestBlockController, Pane> loaded =
            JamController.load(
                RequestBlockController.class.getResource("requestBlock.fxml"),
                this.getScene(),
                this.getEnvironment(),
                this.makePopupProperties().put("request", request));
        this.panes.put(request, loaded);
      }
    } catch (MortuaryServiceException e) {
      log.error("Failed to refresh tickets", e);
    }
  }

  private void buildList() {
    this.list.getItems().clear();
    for (Pair<RequestBlockController, Pane> loaded : this.panes.values()) {
      if (this.filter(loaded.getKey().getRequest())) {
        this.list.getItems().add(loaded.getValue());
      }
    }
  }

  private boolean filter(MortuaryRequest request) {
    String circumstance = this.circumstance.getSelectionModel().getSelectedItem();
    LocalDate start = this.start.getValue();
    LocalDate end = this.end.getValue();
    String deceased = this.deceased.getText();
    boolean open = this.openOnly.isSelected();

    boolean filter = true;

    if (!circumstance.equals("ANY")) {
      filter &= circumstance.equalsIgnoreCase(request.getCircumstance().toString());
    }

    if (open) {
      filter &= request.isOpen();
    }

    LocalDate date = request.getTime().toLocalDate();
    filter &=
        (date.equals(start) || date.isAfter(start)) && (date.equals(end) || date.isBefore(end));

    filter &= Util.filter(request.getDeceased().getName(), deceased);

    return filter;
  }

  @FXML
  private void onNew(ActionEvent actionEvent) {
    MortuaryRequest request =
        new MortuaryRequest(
            MortuaryService.instance().getCurrent(),
            new Person("John Doe", "male", 50),
            Circumstance.PENDING,
            LocalDateTime.now(),
            "",
            "Brigham Women's Hospital");
    this.switchView("newRequest.fxml", this.makeChildProperties().put("request", request));
  }

  @FXML
  private void onViewEmployees(ActionEvent actionEvent) {
    this.switchView("employeeDashboard.fxml");
  }
}
