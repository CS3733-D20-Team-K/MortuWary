package edu.wpi.cs3733.d20.teamk.mortuary.impl.cert;

import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryRequest;
import edu.wpi.cs3733.d20.teamk.mortuary.impl.views.DeathCertController;
import edu.wpi.cs3733.d20.teamk.mortuary.impl.views.SignatureController;
import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamEnvironment;
import io.github.socraticphoenix.jamfx.JamProperties;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Pair;

public class CertPrinter {

  public static void print(MortuaryRequest request, Scene scene, JamProperties parent) {
    AtomicReference<Pair<SignatureController, Stage>> loadRes = new AtomicReference<>();
    Pair<SignatureController, Stage> loaded =
        JamController.loadStage(
            SignatureController.class.getResource("signature.fxml"),
            new JamEnvironment(),
            parent.put(
                "onSubmit",
                (Consumer<Image>)
                    image -> {
                      Pair<DeathCertController, Pane> controller =
                          JamController.load(
                              DeathCertController.class.getResource("death.fxml"),
                              new Scene(new AnchorPane()),
                              loadRes.get().getKey().getEnvironment(),
                              new JamProperties());
                      controller.getKey().set(request, request.getLocation(), image);

                      Window owner = scene.getWindow();
                      Pane toPrint = controller.getValue();

                      PrinterJob job = PrinterJob.createPrinterJob();
                      if (job != null) {
                        boolean proceed = job.showPrintDialog(owner);
                        if (proceed) {
                          Printer printer = job.getPrinter();
                          PageLayout layout =
                              printer.createPageLayout(
                                  Paper.NA_LETTER,
                                  PageOrientation.LANDSCAPE,
                                  Printer.MarginType.DEFAULT);
                          toPrint.setPrefSize(
                              layout.getPrintableWidth(), layout.getPrintableHeight());
                          boolean printed = job.printPage(layout, toPrint);
                          if (printed) {
                            job.endJob();
                          }
                        }
                      }
                    }));
    loadRes.set(loaded);
    loaded.getValue().show();
  }
}
