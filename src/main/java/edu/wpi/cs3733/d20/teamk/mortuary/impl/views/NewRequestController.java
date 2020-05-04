package edu.wpi.cs3733.d20.teamk.mortuary.impl.views;

import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamEnvironment;
import io.github.socraticphoenix.jamfx.JamProperties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NewRequestController extends JamController {
  @FXML Pane fieldPane;
  RequestFieldsController fields;

  public NewRequestController(JamEnvironment environment, JamProperties properties, Scene scene) {
    super(environment, properties, scene);
  }

  @Override
  public void init() {
    super.init();
    this.getScene().getStylesheets().add(getClass().getResource("default.css").toExternalForm());
    Pair<RequestFieldsController, Pane> pair =
        JamController.load(
            RequestFieldsController.class.getResource("requestFields.fxml"),
            this.getScene(),
            this.getEnvironment(),
            this.makeChildProperties());
    this.fields = pair.getKey();
    fieldPane.getChildren().add(pair.getValue());
  }

  @FXML
  public void submit(ActionEvent actionEvent) {
    this.fields.submitRequest();
  }

  @FXML
  public void dashboard(ActionEvent actionEvent) {}
}
