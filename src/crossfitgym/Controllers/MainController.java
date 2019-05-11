package crossfitgym.Controllers;

import crossfitgym.Classes.Grupo;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainController implements Initializable {
    
    private Stage stage;
    
    private Label label;
    @FXML private Font x1;
    
    @FXML private TextField gruposBuscador, sesionesBuscador;
    
    @FXML private ListView<String> listViewGrupos, listViewSesiones;    
    private List<String> gruposStringList, sesionesStringList;
    private ObservableList<String> gruposObsList, sesionesObsList;
    private Gym gym;


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(gym == null) gym = new Gym();
    }    
    
    public void initStage(Stage s) {this.stage = s;}
    public void initStage(Stage s, Gym g) {
        this.stage = s; this.gym = g;
        
        gruposStringList = new ArrayList<>();
        sesionesStringList = new ArrayList<>();
          
        for(Grupo grupo : this.gym.getGrupos()) 
            gruposStringList.add(grupo.getNombre());
        for(SesionTipo sesionT : this.gym.getTiposSesion())
            sesionesStringList.add(sesionT.getNombre());
        
        gruposObsList = FXCollections.observableArrayList(gruposStringList);
        sesionesObsList = FXCollections.observableArrayList(sesionesStringList);
        
        listViewGrupos.setItems(gruposObsList);
        listViewSesiones.setItems(sesionesObsList); 
    }

    @FXML private void crearGrupos(ActionEvent event) {          
        try {
            FXMLLoader cargador = new FXMLLoader(getClass()
                        .getResource("/crossfitgym/CrearGrupo.fxml"));
            Parent root = cargador.load();
            
            CrearGrupoController controller = cargador
                    .<CrearGrupoController>getController();
            controller.initStage(this.stage, gym);
            
            Scene scene = new Scene(root);
            
            this.stage.setScene(scene);
            this.stage.show();
        }catch(IOException e){}
    }
    @FXML private void crearSesion(ActionEvent event) {                
        try {
            FXMLLoader cargador = new FXMLLoader(getClass()
                        .getResource("/crossfitgym/CrearSesion.fxml"));
            Parent root = cargador.load();
            
            CrearSesionController controller = cargador
                    .<CrearSesionController>getController();
            controller.initStage(this.stage, this.gym);
            
            Scene scene = new Scene(root);
            
            this.stage.setScene(scene);
            this.stage.show();
        }catch(IOException e){e.printStackTrace();}
        
    }
    
    @FXML private void go(ActionEvent event) {
        try {
            FXMLLoader cargador = new FXMLLoader(getClass()
                        .getResource("/crossfitgym/Crono.fxml"));
            Parent root = cargador.load();
            
            CronoController controller = cargador
                    .<CronoController>getController();
            controller.initStage(this.stage);
            
            Scene scene = new Scene(root);
            
            this.stage.setScene(scene);
            this.stage.show();
        }catch(IOException e){e.printStackTrace();}
    }
    
    @FXML private void buscarGrupos(KeyEvent event) {        
        //Reset
        gruposStringList = new ArrayList<>();  
        for(Grupo grupo : this.gym.getGrupos()) {
            gruposStringList.add(grupo.getNombre()); 
        }        
        gruposObsList = FXCollections.observableArrayList(gruposStringList);
        listViewGrupos.setItems(gruposObsList);
        
        
        //Si no está vacio
        if(!gruposBuscador.getText().equals("") && !gruposStringList.isEmpty()){
            String aux;
            
            int i = gruposStringList.size() - 1;
            while (i >= 0) {
                //System.err.println(gruposStringList.get(i));
                if(!gruposStringList.get(i).contains(gruposBuscador.getText())){
                    gruposObsList.remove(i);
                    gruposStringList.remove(i);
                }
                i--;
            } 
        }
    }
    @FXML private void buscarSesiones(KeyEvent event) {
        //Reset
        sesionesStringList = new ArrayList<>();            
        for(SesionTipo sesionT : this.gym.getTiposSesion()) 
            sesionesStringList.add(sesionT.getNombre());             
        listViewSesiones.setItems(sesionesObsList);
        
        //Si no está vacio
        if(!gruposBuscador.getText().equals("")) {
            String aux;
            for(int i = sesionesStringList.size(); i < 0; i--) {
                if(!sesionesStringList.get(i).contains(sesionesBuscador.getText())){
                    sesionesObsList.remove(i); sesionesStringList.remove(i);
                }
            } 
        }
    }

    
}
