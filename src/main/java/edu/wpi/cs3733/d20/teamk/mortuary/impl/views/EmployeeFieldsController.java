package edu.wpi.cs3733.d20.teamk.mortuary.impl.views;

import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.d20.teamk.mortuary.Employee;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryService;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryServiceException;
import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamEnvironment;
import io.github.socraticphoenix.jamfx.JamProperties;
import io.github.socraticphoenix.jamfx.JamProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import lombok.Getter;
import lombok.Setter;

public class EmployeeFieldsController extends JamController {
  @FXML private JFXTextField username;
  @FXML private JFXTextField name;
  @FXML private JFXTextField id;

  @Getter
  @Setter
  @JamProperty(value = "employee", optional = true)
  private Employee employee;

  @JamProperty("css")
  private String css;

  public EmployeeFieldsController(
      JamEnvironment environment, JamProperties properties, Scene scene) {
    super(environment, properties, scene);
  }

  @Override
  public void init() {
    super.init();
    this.getScene().getStylesheets().add(this.css);

    this.username.textProperty().addListener((o, a, b) -> this.employee.setUsername(b));
    this.name.textProperty().addListener((o, a, b) -> this.employee.setName(b));
    this.id.textProperty().addListener((o, a, b) -> this.employee.setId(b));
  }

  /** Fills fields with the relevant employee. Deletes any present info. */
  public void displayEmployee() {
    if (this.employee != null) {
      this.id.setText(this.employee.getId());
      this.name.setText(this.name.getText());
      this.username.setText(this.username.getText());
    }
  }

  /**
   * Sets whether or not the fields are user editable.
   *
   * @param editMode True for edit mode.
   */
  public void setEdit(boolean editMode) {
    this.id.setDisable(!editMode);
    this.name.setDisable(!editMode);
    this.username.setDisable(!editMode);
  }

  /** Submits a new employee using the current fields */
  public void submitEmployee() throws MortuaryServiceException {
    MortuaryService.instance().addEmployee(this.employee);
  }

  /**
   * Updates the employee that is currently held by the field. Note, the ticket must already exist.
   */
  public void updateEmployee() throws MortuaryServiceException {
    MortuaryService.instance().updateEmployee(this.employee);
  }
}
