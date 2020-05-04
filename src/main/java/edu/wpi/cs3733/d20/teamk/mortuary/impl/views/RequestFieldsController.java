package edu.wpi.cs3733.d20.teamk.mortuary.impl.views;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import edu.wpi.cs3733.d20.teamk.mortuary.Circumstance;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryRequest;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryService;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryServiceException;
import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamEnvironment;
import io.github.socraticphoenix.jamfx.JamProperties;
import io.github.socraticphoenix.jamfx.JamProperty;
import java.time.LocalDateTime;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import lombok.Getter;
import lombok.Setter;

public class RequestFieldsController extends JamController {
  @FXML private JFXTextField nameOfDeceased;
  @FXML private JFXTextField gender;
  @FXML private JFXComboBox<Integer> age;
  @FXML private JFXComboBox<Circumstance> circumstance;
  @FXML private JFXTextArea description;
  @FXML private JFXTimePicker timeOfDeath;
  @FXML private JFXDatePicker dateOfDeath;
  @FXML private JFXTextField place;

  @Getter
  @Setter
  @JamProperty(value = "request", optional = true)
  private MortuaryRequest request;

  @JamProperty("css")
  private String css;

  public RequestFieldsController(
      JamEnvironment environment, JamProperties properties, Scene scene) {
    super(environment, properties, scene);
    properties.get("request", MortuaryRequest.class).ifPresent(r -> this.request = r);
  }

  @Override
  public void init() {
    super.init();
    this.getScene().getStylesheets().add(this.css);

    this.circumstance.getItems().setAll(Circumstance.values());
    this.circumstance.getSelectionModel().select(Circumstance.PENDING);
    for (int i = 0; i < 150; i++) {
      this.age.getItems().add(i);
    }

    this.nameOfDeceased
        .textProperty()
        .addListener((o, a, b) -> this.request.getDeceased().setName(b));
    this.gender
        .textProperty()
        .addListener((o, a, b) -> this.request.getDeceased().setGender(b.toUpperCase()));
    this.age
        .getSelectionModel()
        .selectedItemProperty()
        .addListener((o, a, b) -> this.request.getDeceased().setAge(b));
    this.circumstance
        .getSelectionModel()
        .selectedItemProperty()
        .addListener((o, a, b) -> this.request.setCircumstance(b));
    this.description.textProperty().addListener((o, a, b) -> this.request.setDescription(b));
    this.timeOfDeath
        .valueProperty()
        .addListener(
            (o, a, b) ->
                this.request.setTime(LocalDateTime.of(this.request.getTime().toLocalDate(), b)));
    this.dateOfDeath
        .valueProperty()
        .addListener(
            (o, a, b) ->
                this.request.setTime(LocalDateTime.of(b, this.request.getTime().toLocalTime())));
    this.place.textProperty().addListener((o, a, b) -> this.request.setLocation(b));

    displayRequest();
  }

  /** Fills fields with the relevant ticket. Deletes any present info. */
  public void displayRequest() {
    if (this.request != null) {
      this.nameOfDeceased.setText(this.request.getDeceased().getName());
      this.gender.setText((this.request.getDeceased().getGender()));
      this.circumstance.setValue(this.request.getCircumstance());
      this.description.setText(this.request.getDescription());
      this.timeOfDeath.setValue(this.request.getTime().toLocalTime());
      this.dateOfDeath.setValue(this.request.getTime().toLocalDate());
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
  public void submitRequest() throws MortuaryServiceException {
    MortuaryService.instance().addRequest(this.request);
  }

  /**
   * Updates the ticket that is currently held by the field. Note, the ticket must already exist.
   */
  public void updateRequest() throws MortuaryServiceException {
    MortuaryService.instance().updateRequest(this.request);
  }
}
