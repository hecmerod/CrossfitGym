package crossfitgym.Controllers;

import crossfitgym.Classes.Grupo;
import crossfitgym.Classes.Gym;
import crossfitgym.Classes.SesionTipo;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;

public class MainController implements Initializable {
    
    private Label label;
    @FXML private Font x1;
    
    @FXML private TextField gruposBuscador, sesionesBuscador;
    
    @FXML private ListView<String> listViewGrupos, listViewSesiones;    
    private List<String> gruposStringList, sesionesStringList;
    private ObservableList<String> gruposObsList, sesionesObsList;
    private Gym gym;


    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.gym = new Gym();
        
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
    }

    @FXML private void crearSesion(ActionEvent event) {
    }
    
    @FXML private void go(ActionEvent event) {
    }
    
    @FXML private void buscarGrupos(KeyEvent event) {
        //Reset
        gruposStringList = new ArrayList<>();            
        for(Grupo grupo : this.gym.getGrupos()) 
            gruposStringList.add(grupo.getNombre());             
        listViewGrupos.setItems(gruposObsList);
        
        //Si no está vacio
        if(!gruposBuscador.getText().equals("")) {
            String aux;
            for(int i = gruposStringList.size(); i < 0; i--) {
                if(!gruposStringList.get(i).contains(gruposBuscador.getText())){
                    gruposObsList.remove(i); gruposStringList.remove(i);
                }
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
