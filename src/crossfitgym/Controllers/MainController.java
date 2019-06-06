package crossfitgym.Controllers;

import crossfitgym.Classes.Grupo;
import crossfitgym.Classes.Gym;
import crossfitgym.Classes.SesionTipo;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class MainController implements Initializable {
    
    private Stage stage;
    
    private Label label;
    @FXML private Font x1;
    
    @FXML private TextField gruposBuscador, sesionesBuscador;
    
    @FXML private ListView<String> listViewGrupos, listViewSesiones;    
    private List<String> gruposStringList, sesionesStringList;
    private ObservableList<String> gruposObsList, sesionesObsList;
    private Gym gym;
    @FXML
    private VBox datosDeSesion;
    @FXML
    private Label nombreSesion;
    @FXML
    private Label tC;
    @FXML
    private Font x2;
    @FXML
    private Label tE;
    @FXML
    private Label tD;
    @FXML
    private Label nC;
    @FXML
    private Label tDC;
    @FXML
    private ListView<String> ejerciciosListView;
    @FXML
    private VBox graficasGrupo;
    @FXML
    private HBox separador;
    @FXML
    private ImageView logoGrande;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(gym == null) try { 
            FileInputStream fileIn = new FileInputStream(System.getProperty("user.dir") 
                                        + "/DB/gymObj");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            
            if(objectIn == null) gym = new Gym();
            else { gym = (Gym) objectIn.readObject(); initListViews();}
            objectIn.close();            
            
        } catch (Exception ex) { gym = new Gym(); }
        
        BufferedImage image;
         try {
                image = ImageIO.read(new File(System.getProperty("user.dir") 
                                        + "/DB/Images/CROSSFIT.PNG"));
                logoGrande.setImage(SwingFXUtils.toFXImage(image, null)); 
        } catch(IOException e){e.printStackTrace();}
    }
    
    public void initStage(Stage s) {this.stage = s;}
    public void initStage(Stage s, Gym g) { 
        this.stage = s; this.gym = g; 
        initListViews();
    }
    protected void initListViews() {
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
        int i = listViewSesiones.getSelectionModel().getSelectedIndex();
        int j = listViewGrupos.getSelectionModel().getSelectedIndex();
        if(i >= 0 && j >= 0){
            int aux = 0;
            for(SesionTipo sesion : this.gym.getTiposSesion()) {
                if(sesion.getNombre().equals(sesionesStringList.get(i))) break;
                aux++;
            }
            i = aux;
        
            try {
                FXMLLoader cargador = new FXMLLoader(getClass()
                        .getResource("/crossfitgym/Crono.fxml"));
                Parent root = cargador.load();
            
                CronoController controller = cargador
                        .<CronoController>getController();
                controller.initStage(this.stage,
                        this.gym.getTiposSesion().get(aux), j);
                
                Scene scene = new Scene(root);
                this.stage.setMinWidth(1400);
                this.stage.setMinHeight(930);
                this.stage.setScene(scene);
                this.stage.show();
            } catch(IOException e){e.printStackTrace();}
        }        
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
            int i = gruposStringList.size() - 1;
            while (i >= 0) {
                if(!gruposStringList.get(i).contains(gruposBuscador.getText())){
                    gruposObsList.remove(i); gruposStringList.remove(i);
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
        sesionesObsList = FXCollections.observableArrayList(sesionesStringList);
        listViewSesiones.setItems(sesionesObsList);
        
        //Si no está vacio
        if(!sesionesBuscador.getText().equals("")) {
            int i = sesionesStringList.size() - 1;
            while (i >= 0) {
                if(!sesionesStringList.get(i).contains(sesionesBuscador.getText())){
                    sesionesObsList.remove(i); sesionesStringList.remove(i);
                }
                i--;
            } 
        }
    }    

    @FXML
    private void isSelectedG(MouseEvent event) {
        int i = listViewGrupos.getSelectionModel().getSelectedIndex();
        
        if(i >= 0) {
            graficasGrupo.setVisible(true);
            separador.setVisible(true); 
            logoGrande.setVisible(false);
        }
        else {
            graficasGrupo.setVisible(false);
            if(!datosDeSesion.isVisible()) {
                separador.setVisible(false); logoGrande.setVisible(true);
            }            
        }
        
    }

    @FXML
    private void isSelectedS(MouseEvent event) {
        int i = listViewSesiones.getSelectionModel().getSelectedIndex();
    
        
        if(i >= 0) {
            int aux = 0;
            for(SesionTipo sesion : this.gym.getTiposSesion()) {
                if(sesion.getNombre().equals(sesionesStringList.get(i))) break;
                aux++;
            }
            i = aux;
            
            nombreSesion.setText(this.gym.getTiposSesion().get(i).getNombre());
            tC.setText("Tiempo de calentamiento:  " + 
                       this.gym.getTiposSesion().get(i).getTCalentamiento() +
                       "s");
            tE.setText("Tiempo por ejercicio:  " + 
                       this.gym.getTiposSesion().get(i).getTEjercicios() +
                       "s");
            tD.setText("Tiempo de descanso:  " + 
                       this.gym.getTiposSesion().get(i).getTDescanso()+
                       "s");
            nC.setText("Numero de circuitos:  " + 
                       this.gym.getTiposSesion().get(i).getNumCircuitos());
            tDC.setText("Tiempo de descanso por ejercicio:  " + 
                       this.gym.getTiposSesion().get(i).getTDCircuitos()+
                       "s");
            
        List<String> ejercicios = new ArrayList<>();            
        for(String ejercicio : this.gym.getTiposSesion().get(i).getEjercicios()) 
            ejercicios.add(ejercicio); 
        ObservableList<String> obsEjercicios = FXCollections.
                                                observableArrayList(ejercicios);
        ejerciciosListView.setItems(obsEjercicios);            
        datosDeSesion.setVisible(true);
        separador.setVisible(true);   
        logoGrande.setVisible(false);
        }                   
        else {
            datosDeSesion.setVisible(false);
            if(!graficasGrupo.isVisible()) {
                separador.setVisible(false);
                logoGrande.setVisible(true);
            } 
        }
    }

    public Gym getGym() { return this.gym; }
    
}
