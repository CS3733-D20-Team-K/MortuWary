package edu.wpi.cs3733.d20.teamk.mortuary.impl;

import edu.wpi.cs3733.d20.teamk.mortuary.EntryPoint;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryService;
import edu.wpi.cs3733.d20.teamk.mortuary.PermissionLevel;
import edu.wpi.cs3733.d20.teamk.mortuary.impl.views.DashboardController;
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
    MortuaryService.instance();

    Scene scene = new Scene(new AnchorPane());
    primaryStage.setScene(scene);
    Pair<DashboardController, Pane> loaded =
        JamController.load(
            DashboardController.class.getResource("dashboard.fxml"),
            scene,
            new JamEnvironment(),
            new JamProperties()
                .put("permissions", PermissionLevel.ADMIN)
                .put("entry_point", EntryPoint.DIRECTORY)
                .put("css", DashboardController.class.getResource("default.css").toExternalForm()));
    scene.setRoot(loaded.getValue());
    primaryStage.show();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
