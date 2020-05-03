package edu.wpi.cs3733.d20.teamk.mortuary.impl;

import edu.wpi.cs3733.d20.teamk.mortuary.Circumstance;
import edu.wpi.cs3733.d20.teamk.mortuary.Employee;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryRequest;
import edu.wpi.cs3733.d20.teamk.mortuary.Person;
import java.time.LocalDateTime;

import edu.wpi.cs3733.d20.teamk.mortuary.impl.views.SignatureController;
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
    MortuaryRequest request =
        new MortuaryRequest(
            new Employee("Bob"),
            new Person("Dead K. Died", "male", 42),
            Circumstance.CRIME,
            LocalDateTime.now(),
            "He got crime'd",
            "lunch_room");

    Scene scene = new Scene(new AnchorPane());
    primaryStage.setScene(scene);
    Pair<SignatureController, Pane> loaded =
            JamController.load(
                    SignatureController.class.getResource("newRequest.fxml"),
                    scene,
                    new JamEnvironment(),
                    new JamProperties());
    scene.setRoot(loaded.getValue());
    primaryStage.show();
    //request.printCertificate("Brigham and Women's Faulkner Hospital");
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
