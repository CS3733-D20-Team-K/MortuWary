package edu.wpi.cs3733.d20.teamk.mortuary.impl.views;

import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryRequest;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryService;
import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryServiceException;
import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamEnvironment;
import io.github.socraticphoenix.jamfx.JamProperties;
import io.github.socraticphoenix.jamfx.JamProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import lombok.Getter;

public class EditRequestController extends JamController {

  @Getter
  @JamProperty("request")
  private MortuaryRequest request;

  @JamProperty("css")
  private String css;

  @FXML private HBox fields;

  public EditRequestController(JamEnvironment environment, JamProperties properties, Scene scene) {
    super(environment, properties, scene);
  }

  @Override
  public void init() {
    super.init();
    this.getScene().getStylesheets().add(this.css);

    Pair<RequestFieldsController, Pane> loaded =
        JamController.load(
            RequestFieldsController.class.getResource("requestFields.fxml"),
            this.getScene(),
            this.getEnvironment(),
            this.makeChildProperties());
    this.fields.getChildren().add(loaded.getValue());
  }

  @FXML
  private void onSave(ActionEvent actionEvent) throws MortuaryServiceException {
    MortuaryService.instance().updateRequest(this.request);
  }

  @FXML
  private void onDelete(ActionEvent actionEvent) throws MortuaryServiceException {
    MortuaryService.instance().removeRequest(this.request);
    refresh();
  }

  @FXML
  private void onPrint(ActionEvent actionEvent) {
    this.request.printCertificate();
  }
}
