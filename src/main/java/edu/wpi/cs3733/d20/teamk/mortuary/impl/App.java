package edu.wpi.cs3733.d20.teamk.mortuary.impl;

import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryService;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryServiceException;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws MortuaryServiceException {
    MortuaryService.instance().run();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
