package edu.wpi.cs3733.d20.teamk.mortuary.impl.views;

import edu.wpi.cs3733.d20.teamk.mortuary.Employee;
import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamEnvironment;
import io.github.socraticphoenix.jamfx.JamProperties;
import io.github.socraticphoenix.jamfx.JamProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import lombok.Getter;

public class EmployeeBlockController extends JamController {

  @FXML private Label name;
  @FXML private Label id;
  @FXML private Label username;

  @Getter
  @JamProperty("employee")
  private Employee employee;

  public EmployeeBlockController(
      JamEnvironment environment, JamProperties properties, Scene scene) {
    super(environment, properties, scene);
  }

  @Override
  public void init() {
    super.init();

    this.name.setText(this.employee.getName());
    this.id.setText(this.employee.getId());
    this.username.setText(this.employee.getId());
  }

  @FXML
  private void onView(ActionEvent actionEvent) {
    this.popup("editEmployee.fxml").getValue().show();
  }
}
