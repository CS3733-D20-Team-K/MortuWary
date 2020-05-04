package edu.wpi.cs3733.d20.teamk.mortuary.impl.views;

import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryRequest;
import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamEnvironment;
import io.github.socraticphoenix.jamfx.JamProperties;
import io.github.socraticphoenix.jamfx.JamProperty;
import java.time.format.DateTimeFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import lombok.Getter;

public class RequestBlockController extends JamController {
  private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm");

  @Getter
  @JamProperty("request")
  MortuaryRequest request;

  @FXML private Label name;
  @FXML private Label circumstance;
  @FXML private Label place;
  @FXML private Label date;

  public RequestBlockController(JamEnvironment environment, JamProperties properties, Scene scene) {
    super(environment, properties, scene);
  }

  @Override
  public void init() {
    super.init();
    this.name.setText(request.getDeceased().getName());
    this.circumstance.setText(request.getCircumstance().toString());
    this.place.setText(request.getLocation());
    this.date.setText(FORMAT.format(this.request.getTime()));
  }

  @Override
  public void refresh() {
    super.refresh();
    this.name.setText(request.getDeceased().getName());
    this.circumstance.setText(request.getCircumstance().toString());
    this.place.setText(request.getLocation());
    this.date.setText(DateTimeFormatter.ISO_DATE.format(this.request.getTime()));
  }

  @FXML
  private void onView(ActionEvent actionEvent) {
    this.popup("editRequest.fxml").getValue().show();
  }
}
