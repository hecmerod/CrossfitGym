package crossfitgym.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CrearSesionController implements Initializable {
    
    private Stage stage;
    
    @FXML
    private Font x1;
    @FXML
    private Insets x2;
    @FXML
    private Insets x3;
    @FXML
    private Font x5;
    @FXML
    private Insets x4;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    public void initStage(Stage s) {this.stage = s;}
    
}
