package crossfitgym.Controllers;

import crossfitgym.Classes.Gym;
import crossfitgym.Classes.SesionTipo;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class CrearSesionController implements Initializable {
    
    private Stage stage;
    
    @FXML
    private Font x1;
    @FXML
    private Font x5;
    @FXML
    private Insets x4;
    @FXML
    private TextField nombre;
    @FXML
    private Label errNombre;
    @FXML
    private TextField tCalentamiento;
    @FXML
    private TextField tEjercicio;
    @FXML
    private Label errTE;
    @FXML
    private TextField tDescanso;
    @FXML
    private Label errTD;
    @FXML
    private TextField tDescansoCircuitos;
    @FXML
    private Label errTDC;
    @FXML
    private TextField nCircuitos;
    @FXML
    private Label errNC;
    @FXML
    private ListView<String> listViewEjercicios;
    private List<String> ejercicios;
    private ObservableList<String> ejerciciosObs;
    
    private Gym gym;
    
    private static final int MAX_EJERCICIOS = 12;
    @FXML
    private Label errTC;
    @FXML
    private ImageView logoGrande;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ejercicios = new ArrayList<>();
    }    
    public void initStage(Stage s, Gym g) {
        this.stage = s; this.gym = g;
        BufferedImage image;
        try {
                image = ImageIO.read(new File(System.getProperty("user.dir") 
                                        + "/DB/Images/CROSSFIT.PNG"));
                logoGrande.setImage(SwingFXUtils.toFXImage(image, null)); 
        } catch(IOException e){e.printStackTrace();}
    }

    @FXML private void addEjercicio(ActionEvent event) {
        if (ejercicios.size() <= MAX_EJERCICIOS) {
            ejercicios.add(((Button)event.getSource()).getId());
            ejerciciosObs = FXCollections.observableArrayList(ejercicios);
            listViewEjercicios.setItems(ejerciciosObs);
        }
    }

    @FXML private void eliminarEjercicio(ActionEvent event) {
        int i = listViewEjercicios.getSelectionModel().getSelectedIndex(); 
        System.err.println(i);
        if(i >= 0) {
            ejercicios.remove(i);
            ejerciciosObs = FXCollections.observableArrayList(ejercicios);
            listViewEjercicios.setItems(ejerciciosObs);
        }
    }

    @FXML
    private void crear(ActionEvent event) {      
        boolean ok = true;
        
        if(nombre.getText().equals("")) { errNombre.setText("*"); ok = false;}
        else errNombre.setText("");
        
        if(tEjercicio.getText().equals("")) {errTE.setText("*"); ok = false;}
        else errTE.setText("");
        
        if(tDescanso.getText().equals("")) {errTD.setText("*"); ok = false;}
        else errTD.setText("");
        
        if(nCircuitos.getText().equals("")) {errNC.setText("*"); ok = false;}
        else errNC.setText("");
        
        if(tDescansoCircuitos.getText().equals("")) {
            errTDC.setText("*"); ok = false;
        }
        else errTDC.setText("");       
        
        
        if(ok && ejercicios.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);                
            alert.setHeaderText("");
            alert.setContentText("Necesitas añadir al menos un ejercicio");
            alert.showAndWait();
        } 
        else {
            int[] t = new int[5];
            int aux = 0; String err = "";
            try {
                if(tCalentamiento.getText().equals("")) t[aux] = 0;
                else {
                    t[aux] = Integer.parseInt(tCalentamiento.getText()); 
                    aux++;
                }
                t[aux] = Integer.parseInt(tEjercicio.getText()); aux++;
                t[aux] = Integer.parseInt(tDescanso.getText()); aux++;
                t[aux] = Integer.parseInt(nCircuitos.getText()); aux++;
                t[aux] = Integer.parseInt(tDescansoCircuitos.getText()); aux++;
                
            } catch(NumberFormatException e) {
                switch(aux) {
                    case 0: 
                        err = "Sólo se admiten números en "
                                + "el tiempo de calentamiento";
                    break;
                    case 1: err = "Sólo se admiten números en "
                                + "el tiempo por ejercicio";
                    break;
                    case 2: err = "Sólo se admiten números en "
                                + "el tiempo de descanso";
                    break;
                    case 3: err = "Sólo se admiten números en "
                                + "el número de ejercicios";
                    break;
                    case 4: err = "Sólo se admiten números en "
                                + "el tiempo de descanso entre circuitos";
                    break;
                }
                Alert alert = new Alert(Alert.AlertType.ERROR);                
                alert.setHeaderText("");
                alert.setContentText(err);
                alert.showAndWait();
                
                aux = -1;
            }
            
            if(aux != -1) {
                gym.getTiposSesion().add(new SesionTipo(
                        nombre.getText(), t[0], ejercicios.size(), t[1],
                        t[2], t[3], t[4], ejercicios));
            
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
                } catch(IOException e){}
            }
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
