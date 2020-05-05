package edu.wpi.cs3733.d20.teamk.mortuary.impl.views;

import edu.wpi.cs3733.d20.teamk.mortuary.Employee;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryService;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryServiceException;
import edu.wpi.cs3733.d20.teamk.mortuary.PermissionLevel;
import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamEnvironment;
import io.github.socraticphoenix.jamfx.JamProperties;
import io.github.socraticphoenix.jamfx.JamProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;
import lombok.Getter;

public class EditEmployeeController extends JamController {
  @FXML private HBox fields;

  @Getter
  @JamProperty("employee")
  private Employee employee;

  @JamProperty("css")
  private String css;

  @JamProperty("permissions")
  private PermissionLevel level;

  public EditEmployeeController(JamEnvironment environment, JamProperties properties, Scene scene) {
    super(environment, properties, scene);
  }

  @Override
  public void init() {
    super.init();
    this.getScene().getStylesheets().add(this.css);

    Pair<EmployeeFieldsController, Pane> loaded =
        JamController.load(
            EmployeeFieldsController.class.getResource("employeeFields.fxml"),
            this.getScene(),
            this.getEnvironment(),
            this.makeChildProperties());
    this.fields.getChildren().add(loaded.getValue());

    if (level != PermissionLevel.ADMIN) {
      loaded.getKey().setEdit(false);
    } else {
      loaded.getKey().setEdit(true);
    }
  }

  @FXML
  private void onSave(ActionEvent actionEvent) throws MortuaryServiceException {
    MortuaryService.instance().updateEmployee(this.employee);
    refresh();
    ((Stage) getScene().getWindow()).close();
  }

  @FXML
  private void onDelete(ActionEvent actionEvent) throws MortuaryServiceException {
    MortuaryService.instance().removeEmployee(this.employee);
    refresh();
    ((Stage) getScene().getWindow()).close();
  }
}
