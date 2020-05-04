package edu.wpi.cs3733.d20.teamk.mortuary.impl.views;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamEnvironment;
import io.github.socraticphoenix.jamfx.JamProperties;
import javafx.fxml.FXML;
import javafx.scene.Scene;

public class RequestFieldsController extends JamController {
  @FXML private JFXTextField nameOfDeceased;
  @FXML private JFXTextField gender;
  @FXML private JFXComboBox circumstance;
  @FXML private JFXTextArea description;
  @FXML private JFXTextField timeOfDeath;
  @FXML private JFXTextField dateOfDeath;
  @FXML private JFXTextField place;



  public RequestFieldsController(
      JamEnvironment environment, JamProperties properties, Scene scene) {
    super(environment, properties, scene);
  }

  /**
   * Sets whether or not the fields are user editable.
   *
   * @param editMode True for edit mode.
   */
  public void setEdit(boolean editMode) {
    this.nameOfDeceased.setDisable(!editMode);
    this.gender.setDisable(!editMode);
    this.circumstance.setDisable(!editMode);
    this.description.setDisable(!editMode);
    this.timeOfDeath.setDisable(!editMode);
    this.dateOfDeath.setDisable(!editMode);
    this.place.setDisable(!editMode);
  }

  @Override
  public void init() {}
}
