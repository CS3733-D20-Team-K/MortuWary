package edu.wpi.cs3733.d20.teamk.mortuary.impl.views;

import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamEnvironment;
import io.github.socraticphoenix.jamfx.JamProperties;
import io.github.socraticphoenix.jamfx.JamProperty;
import java.awt.geom.Point2D;
import java.util.function.Consumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SignatureController extends JamController {
  @FXML private Canvas canvas;

  @JamProperty("onSubmit")
  private Consumer<Image> image;

  @JamProperty("css")
  private String css;

  public SignatureController(JamEnvironment environment, JamProperties properties, Scene scene) {
    super(environment, properties, scene);
  }

  private double xOld = -1;
  private double yOld = -1;

  @Override
  public void init() {
    super.init();
    this.getScene().getStylesheets().add(this.css);

    this.canvas.getGraphicsContext2D().setLineWidth(5);
    this.canvas.setOnMouseDragged(
        me -> {
          double x = me.getX();
          double y = me.getY();

          if (xOld == -1 || Point2D.distance(xOld, yOld, x, y) >= 30) {
            xOld = x;
            yOld = y;
          } else {
            canvas.getGraphicsContext2D().strokeLine(xOld, yOld, x, y);
            xOld = x;
            yOld = y;
          }
        });
  }

  @FXML
  private void onSubmit(ActionEvent actionEvent) {
    SnapshotParameters parameters = new SnapshotParameters();
    parameters.setFill(Color.TRANSPARENT);
    this.image.accept(this.canvas.snapshot(parameters, null));
  }

  @FXML
  private void onClear(ActionEvent actionEvent) {
    this.canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
  }
}
