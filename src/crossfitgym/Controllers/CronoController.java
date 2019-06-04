package crossfitgym.Controllers;

import crossfitgym.Classes.Crono;
import crossfitgym.Classes.SesionTipo;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class CronoController implements Initializable{

    private Stage stage;
    
    @FXML
    private Font x1;
    @FXML
    private Insets x2;
    @FXML
    private Insets x3;

    private SesionTipo sTipo;
    
    private int nGrupo;
    @FXML
    private Label timer;
    @FXML
    private Font x6;
    @FXML
    private Label grupo;
    @FXML
    private Label sesion;
    @FXML
    private Insets x4;
    @FXML
    private Insets x5;
    
    @FXML
    private ImageView ej0, ej1, ej2, ej3, ej4, ej5, ej6, ej7, ej8, ej9, ej10,
            ej11, ej12, ej13;
    private ImageView[] ej;
    @FXML
    private Insets x7;
    
    private boolean isStarted = false;
    private boolean isPaused;
    
    private int contEj = 0;
    
    private Crono crono;
    
    @FXML
    private Button start_pause;
    
    private StringProperty tiempo = new SimpleStringProperty();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ej = new ImageView[14];
        ej[0] = ej0; ej[1] = ej1; ej[2] = ej2; ej[3] = ej3; ej[4] = ej4;
        ej[5] = ej5; ej[6] = ej6; ej[7] = ej7; ej[8] = ej8; ej[9] = ej9;
        ej[10] = ej10; ej[11] = ej11; ej[12] = ej12; ej[13] = ej13;
        
        this.crono = new Crono(this.tiempo);
        
        tiempo.setValue("00:00");
        timer.textProperty().bind(tiempo);
    }    
    
    public void initStage(Stage s, SesionTipo sT, int nG) {
        this.stage = s; this.sTipo = sT; this.nGrupo = nG;     
        setImages();
    }
    
    private void setImages() {
        BufferedImage image = null;
        for(int i = 0; i <= this.sTipo.getNumEjercicios(); i++) {
            ej[i].setDisable(false); ej[i].setVisible(true);  
            try {
                image = ImageIO.read(new File(System.getProperty("user.dir") 
                                        + "/DB/Images/Sit-ups.JPG"));
                                        //+ this.sTipo.getEjercicios().get(i)));
                ej[i].setImage(SwingFXUtils.toFXImage(image, null)); 
            } catch(IOException e) {
                System.err.println("No se ha podido cargar la foto del ejercico "
                        + i);
            }
        }
    }

    @FXML
    private void stop(ActionEvent event) {
        isStarted = false;
        start_pause.setText("PLAY");
    }

    @FXML
    private void next(ActionEvent event) {
        contEj++;
    }

    @FXML
    private void start_pause(ActionEvent event) {        
        if(!isStarted) {            
            start_pause.setText("PAUSE");
            isStarted = true;
            
            this.crono.start();
        }
        else if (isPaused){
            start_pause.setText("PAUSE");
            isPaused = false;
        }
        else {
            start_pause.setText("PLAY");
            isPaused = true;
        }  
    }  
   
    
}
