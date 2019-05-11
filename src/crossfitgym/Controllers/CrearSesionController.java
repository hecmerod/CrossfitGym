package crossfitgym.Controllers;

import crossfitgym.Classes.Gym;
import crossfitgym.Classes.SesionTipo;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;

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
    
    private static final int MAX_EJERCICIOS = 13;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ejercicios = new ArrayList<>();
    }    
    public void initStage(Stage s, Gym g) {this.stage = s; this.gym = g;}

    @FXML private void addEjercicio(ActionEvent event) {
        if (ejercicios.size() < MAX_EJERCICIOS) {
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
        
        if(nombre.getText().equals("")) {errNombre.setText("*"); ok = false;}
        else errNombre.setText(" ");
        
        if(tEjercicio.getText().equals("")) {errTE.setText("*"); ok = false;}
        else errTE.setText(" ");
        
        if(tDescanso.getText().equals("")) {errTD.setText("*"); ok = false;}
        else errTD.setText(" ");
        
        if(nCircuitos.getText().equals("")) {errNC.setText("*"); ok = false;}
        else errNC.setText(" ");
        
        if(tDescansoCircuitos.getText().equals("")) {
            errTDC.setText("*"); ok = false;
        }
        else errTDC.setText(" ");
        
        if(ok && ejercicios.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);                
            alert.setHeaderText("");
            alert.setContentText("Necesitas añadir al menos un ejercicio");
            alert.showAndWait();
        } 
        else {
            int tC;
            if(tCalentamiento.getText().equals("")) tC = 0;
            else tC = Integer.parseInt(tCalentamiento.getText());
            gym.getTiposSesion().add(new SesionTipo(
                    nombre.getText(), tC, ejercicios.size(),
                    Integer.parseInt(tEjercicio.getText()),
                    Integer.parseInt(tDescanso.getText()),
                    Integer.parseInt(nCircuitos.getText()),
                    Integer.parseInt(tDescansoCircuitos.getText()),
                    ejercicios
            ));
            
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
