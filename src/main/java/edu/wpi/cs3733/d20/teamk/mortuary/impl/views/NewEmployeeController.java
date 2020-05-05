package edu.wpi.cs3733.d20.teamk.mortuary.impl.views;

import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryServiceException;
import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamEnvironment;
import io.github.socraticphoenix.jamfx.JamProperties;
import io.github.socraticphoenix.jamfx.JamProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

public class NewEmployeeController extends JamController {
  private EmployeeFieldsController fieldsController;
  @FXML private HBox fields;

  @JamProperty("css")
  private String css;

  public NewEmployeeController(JamEnvironment environment, JamProperties properties, Scene scene) {
    super(environment, properties, scene);
  }

  @Override
  public void init() {
    super.init();
    this.getScene().getStylesheets().add(this.css);

    Pair<EmployeeFieldsController, Pane> pair =
        JamController.load(
            RequestFieldsController.class.getResource("employeeFields.fxml"),
            this.getScene(),
            this.getEnvironment(),
            this.makeChildProperties());
    this.fieldsController = pair.getKey();
    fields.getChildren().add(pair.getValue());
  }

  @FXML
  public void submit(ActionEvent actionEvent) throws MortuaryServiceException {
    this.fieldsController.submitEmployee();
    this.switchView("employeeDashboard.fxml");
  }

  @FXML
  private void onCancel(ActionEvent actionEvent) {
    this.switchView("employeeDashboard.fxml");
  }
}
