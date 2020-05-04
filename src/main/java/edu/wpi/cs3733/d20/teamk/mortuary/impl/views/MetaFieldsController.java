package edu.wpi.cs3733.d20.teamk.mortuary.impl.views;

import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryRequest;
import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamEnvironment;
import io.github.socraticphoenix.jamfx.JamProperties;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import lombok.Getter;
import lombok.Setter;

public class MetaFieldsController extends JamController {
  @FXML private JFXTextField requestor;
  @FXML private JFXTextField timeOpened;
  @FXML private JFXTextField dateOpened; // TODO: Input validation
  @FXML private JFXTextField timeClosed;
  @FXML private JFXTextField dateClosed;

  @Getter @Setter private MortuaryRequest request;

  public MetaFieldsController(JamEnvironment environment, JamProperties properties, Scene scene) {
    super(environment, properties, scene);
  }

  public MetaFieldsController(
      JamEnvironment environment, JamProperties properties, Scene scene, MortuaryRequest request) {
    super(environment, properties, scene);
    this.request = request;
  }

  /** Fills fields with the relevant ticket. Deletes any present info. */
  public void displayRequest() {
    if (this.request != null) {
      //      this.nameOfDeceased.setText(this.request.getDeceased().getName());
      //      this.gender.setText((this.request.getDeceased().getGender()));
      //      this.circumstance.setValue(this.request.getCircumstance());
      //      this.description.setText(this.request.getDescription());
      // TODO: Prefill
    }
  }

  /**
   * Sets whether or not the fields are user editable.
   *
   * @param editMode True for edit mode.
   */
  public void setEdit(boolean editMode) {
    this.requestor.setDisable(!editMode);
    this.timeOpened.setDisable(!editMode);
    this.dateOpened.setDisable(!editMode);
    this.timeClosed.setDisable(!editMode);
    this.dateClosed.setDisable(!editMode);
  }

  /**
   * Updates the ticket that is currently held by the field. Note, the ticket must already exist.
   */
  public void updateRequest() {}
}
