package edu.wpi.cs3733.d20.teamk.mortuary.impl.views;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.d20.teamk.mortuary.Circumstance;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryRequest;
import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamEnvironment;
import io.github.socraticphoenix.jamfx.JamProperties;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import lombok.Getter;
import lombok.Setter;

public class RequestFieldsController extends JamController {
  @FXML private JFXTextField nameOfDeceased;
  @FXML private JFXTextField gender;
  @FXML private JFXComboBox circumstance;
  @FXML private JFXTextArea description;
  @FXML private JFXTextField timeOfDeath; // TODO: Input validation
  @FXML private JFXTextField dateOfDeath;
  @FXML private JFXTextField place;

  @Getter @Setter private MortuaryRequest request;

  public RequestFieldsController(
      JamEnvironment environment, JamProperties properties, Scene scene) {
    super(environment, properties, scene);
  }

  public RequestFieldsController(
      JamEnvironment environment, JamProperties properties, Scene scene, MortuaryRequest request) {
    super(environment, properties, scene);
    this.request = request;
  }

  /** Fills fields with the relevant ticket. Deletes any present info. */
  public void displayRequest() {
    if (this.request != null) {
      this.nameOfDeceased.setText(this.request.getDeceased().getName());
      this.gender.setText((this.request.getDeceased().getGender()));
      this.circumstance.setValue(this.request.getCircumstance());
      this.description.setText(this.request.getDescription());
      // TODO: Datetime formatting
    }
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

  /** Submits a new request using the current fields */
  public void submitRequest() {}

  /**
   * Updates the ticket that is currently held by the field. Note, the ticket must already exist.
   */
  public void updateRequest() {}

  @Override
  public void init() {
    this.circumstance.getItems().setAll(Circumstance.values());
  }
}
