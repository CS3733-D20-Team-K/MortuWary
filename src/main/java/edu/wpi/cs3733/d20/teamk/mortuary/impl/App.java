package edu.wpi.cs3733.d20.teamk.mortuary.impl;

import edu.wpi.cs3733.d20.teamk.mortuary.impl.views.NewRequestController;
import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamEnvironment;
import io.github.socraticphoenix.jamfx.JamProperties;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) {
    Scene scene = new Scene(new AnchorPane());
    primaryStage.setScene(scene);
    Pair<NewRequestController, Pane> loaded =
        JamController.load(
            NewRequestController.class.getResource("dashboard.fxml"),
            scene,
            new JamEnvironment(),
            new JamProperties());
    scene.setRoot(loaded.getValue());
    primaryStage.show();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
