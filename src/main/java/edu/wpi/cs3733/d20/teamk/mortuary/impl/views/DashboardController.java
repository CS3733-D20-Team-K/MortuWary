package edu.wpi.cs3733.d20.teamk.mortuary.impl.views;

import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamEnvironment;
import io.github.socraticphoenix.jamfx.JamProperties;
import javafx.scene.Scene;

public class DashboardController extends JamController {
  public DashboardController(JamEnvironment environment, JamProperties properties, Scene scene) {
    super(environment, properties, scene);
  }

  @Override
  public void init() {
    super.init();
    this.getScene().getStylesheets().add(getClass().getResource("default.css").toExternalForm());
  }
}
