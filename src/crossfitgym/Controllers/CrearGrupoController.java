package crossfitgym.Controllers;

import crossfitgym.Classes.Grupo;
import crossfitgym.Classes.Gym;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class CrearGrupoController {

    private Stage stage;
    
    @FXML
    private Font x1;
    @FXML
    private Insets x2;
    @FXML
    private Font x5;    
    @FXML
    private TextField codigo;
    @FXML
    private TextArea descripcion;
    
    private Gym gym;
    @FXML
    private Label errCodigo;
    @FXML
    private ImageView logoGrande;
     
    public void initStage(Stage s, Gym g) {
        this.stage = s; this.gym = g;
        
        BufferedImage image;
        try {
                image = ImageIO.read(new File(System.getProperty("user.dir") 
                                        + "/DB/Images/CROSSFIT.PNG"));
                logoGrande.setImage(SwingFXUtils.toFXImage(image, null)); 
        } catch(IOException e){e.printStackTrace();}
    }

    
    
    @FXML
    private void crear(ActionEvent event) {
        
        if(codigo.getText().equals("")) errCodigo.setText("*");
        else {
            this.gym.getGrupos().
                    add(new Grupo(codigo.getText(), descripcion.getText()));
            try {
                FXMLLoader cargador = new FXMLLoader(getClass()
                        .getResource("/crossfitgym/Main.fxml"));
                Parent root = cargador.load();
            
                MainController controller = cargador
                        .<MainController>getController();
                controller.initStage(this.stage, gym);
            
                Scene scene = new Scene(root);
            
                this.stage.setScene(scene);
                this.stage.show();
            }catch(IOException e){}
        }        
    }

    @FXML
    private void atras(ActionEvent event) {
        try {
            FXMLLoader cargador = new FXMLLoader(getClass()
                        .getResource("/crossfitgym/Main.fxml"));
            Parent root = cargador.load();
            
            MainController controller = cargador
                    .<MainController>getController();
            controller.initStage(this.stage, gym);
            
            Scene scene = new Scene(root);
            
            this.stage.setScene(scene);
            this.stage.show();
        }catch(IOException e){}
    }
    
    
}
