package edu.wpi.cs3733.d20.teamk.mortuary.impl.views;

import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryRequest;
import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamEnvironment;
import io.github.socraticphoenix.jamfx.JamProperties;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class DeathCertController extends JamController {
  @FXML private Label name;
  @FXML private Label day;
  @FXML private Label month;
  @FXML private Label year;
  @FXML private Label hospital;
  @FXML private ImageView signature;
  @FXML private AnchorPane pane;
  @FXML private ImageView background;

  public DeathCertController(JamEnvironment environment, JamProperties properties, Scene scene) {
    super(environment, properties, scene);
  }

  public void set(MortuaryRequest request, String hospital, Image signature) {
    this.pane.setPrefSize(1920, 1080);

    this.name.setText(request.getDeceased().getName());

    LocalDateTime date = request.getTime();
    this.day.setText(
        date.getDayOfWeek().getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault()));
    this.month.setText(
        date.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault()));
    this.year.setText(String.valueOf(date.getYear()));

    this.hospital.setText(hospital);

    this.signature.setImage(signature);
  }
}
