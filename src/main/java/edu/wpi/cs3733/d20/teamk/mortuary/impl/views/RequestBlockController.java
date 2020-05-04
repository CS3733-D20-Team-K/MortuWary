package edu.wpi.cs3733.d20.teamk.mortuary.impl.views;

import edu.wpi.cs3733.d20.teamk.mortuary.MortuaryRequest;
import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamEnvironment;
import io.github.socraticphoenix.jamfx.JamProperties;
import io.github.socraticphoenix.jamfx.JamProperty;
import javafx.scene.Scene;

public class RequestBlockController extends JamController {

    @JamProperty("request") MortuaryRequest request;

    public RequestBlockController(JamEnvironment environment, JamProperties properties, Scene scene) {
        super(environment, properties, scene);
    }

}
