package Controllers;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MapaController {
	
	@FXML
    private WebView webView;

    public void initialize() {
        WebEngine webEngine = webView.getEngine();
        webEngine.load("https://www.google.com/maps");
    }

}
