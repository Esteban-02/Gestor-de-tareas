package gm.tareas.controlador;

import gm.tareas.modelo.Tarea;
import gm.tareas.servicio.TareaServicio;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.scene.control.*;


import java.net.URL;
import java.util.ResourceBundle;

@Component
public class IndexControlador implements Initializable {
    // enviar informacion a consola
    private final Logger logger = LoggerFactory.getLogger(IndexControlador.class);

    @Autowired
    private TareaServicio tareaServicio;

    @FXML
    private TableView<Tarea> tareaTabla;
    @FXML
    private TableColumn<Tarea, Integer> idTareaColumna;
    @FXML
    private TableColumn<Tarea, String> tareaColumna;
    @FXML
    private TableColumn<Tarea, String> responsableColumna;
    @FXML
    private TableColumn<Tarea, String> statusColumna;
    @FXML
    private TextField nombreTareaTexto;
    @FXML
    private TextField responsableTexto;
    @FXML
    private TextField estatusTexto;




    // mostrar la informacion segun como se vaya recuperando de la base de datos, se hara automaticamente
    private final ObservableList<Tarea> tareaList = FXCollections.observableArrayList();
    private Integer idTareaInterno;

    /**
     * Inicializa los diferentes metodos que se estaran usando durante toda la ejecucion del programa,
     * desde seleccionar un registro individual en la tabla, llamar los diferentes metodos de inicializacion
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tareaTabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);      // Se selecciona un solo registro a la vez de la tabla
        configurarColumnas();
        listarTareas();

    }

    /**
     * Lista las tareas que estan en la base de datos en cada una de las filas
     */
    private void listarTareas() {
        logger.info("Ejecutando la lista de tareas");
        tareaList.clear();
        tareaList.addAll(tareaServicio.listarTareas());     //Se obtiene toda la informacion en un obcervable y se esta agrgando toda la informacion de la tabla
        tareaTabla.setItems(tareaList); //Se agrega todo a la tabla
    }

    /**
     * Configura los valores que se guardan en los atributos y los guarda en las columnas que estan definidas en la tabla
     */
    private void configurarColumnas() {
        idTareaColumna.setCellValueFactory(new PropertyValueFactory<>("idTarea"));
        tareaColumna.setCellValueFactory(new PropertyValueFactory<>("nombreTarea"));
        responsableColumna.setCellValueFactory(new PropertyValueFactory<>("responsable"));
        statusColumna.setCellValueFactory(new PropertyValueFactory<>("estatus"));
    }

    /**
     *  Agrega nuevas tareas a la base de datos, siguiendo unas validaciones, ademas de mostrar mensajes de alerta
     *  segun la cituacion como si tiene un nombre o no la nueva tarea
     */
    public void agregarTarea(){
        if (nombreTareaTexto.getText()==null){
            mostrarMensaje("Error de validacion", "Debe proporcionar una tarea");
            nombreTareaTexto.requestFocus();
            return;
        }else{
            Tarea tarea = new Tarea();
            recolectarDatosFormulario(tarea);
            tarea.setIdTarea(null);
            tareaServicio.guardarTarea(tarea);
            mostrarMensaje("Informacion","Tarea agregada");
            limpiarFormulario();
            listarTareas();
        }
    }

    /**
     * Al momento de seleccionar una fila en la tabla se carga en los campos de texto
     */
    public void cargarTareaFormulario(){
        var tarea = tareaTabla.getSelectionModel().getSelectedItem();     // Se selecciona una sola fila
        if (tarea != null){
            idTareaInterno = tarea.getIdTarea();
            nombreTareaTexto.setText(tarea.getNombreTarea());
            responsableTexto.setText(tarea.getResponsable());
            estatusTexto.setText(tarea.getEstatus());
        }

    }

    /**
     * una vez que se carga un registro de la tabla queda listo para realizar la modificacion en la base de
     * datos y realizar un <b>UPDATE</b>
     */
    public void modificarTarea(){

        if (idTareaInterno == null){
            mostrarMensaje("Informacion", "Seleccione una tarea");
            return;
        }
        if (nombreTareaTexto.getText().isEmpty()){
            mostrarMensaje("Informacion","Ingrese una tarea");
            return;
        }

        Tarea tarea = new Tarea();
        recolectarDatosFormulario(tarea);
        tareaServicio.guardarTarea(tarea);
        limpiarFormulario();
        listarTareas();

    }

    /**
     * Una vez agregada la nueva tarea todos los campos de los TextField son limpiados para agregar nuevas tareas
     */
    public void limpiarFormulario() {
        idTareaInterno= null;
        nombreTareaTexto.clear();
        responsableTexto.clear();
        estatusTexto.clear();
    }

    /**
     * De los datos que el usuario escribe en los espacios de texto en la ventana, los recupera y los guarda en el
     * objeto tarea para luego ser guardados en la base de datos
     * @param tarea
     */
    private void recolectarDatosFormulario(Tarea tarea) {
        if (idTareaInterno != null){
            tarea.setIdTarea(idTareaInterno);
        }
        tarea.setNombreTarea(nombreTareaTexto.getText());
        tarea.setResponsable(responsableTexto.getText());
        tarea.setEstatus(estatusTexto.getText());
    }

    /**
     * Metodo que mostrara mensajes en forma de alerta para mostrar informacion relevante
     * @param titulo    Muestra el titulo de la informacion que se va a mostrar
     * @param mensaje   Muestra el mensaje de informacion
     */
    private void mostrarMensaje(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void eliminarTarea(){
        if (idTareaInterno==null){
            mostrarMensaje("Error", "No se ha seleccionado ninguna tarea");
            return;
        }
        Tarea tarea = new Tarea();
        recolectarDatosFormulario(tarea);
        tareaServicio.eliminarTarea(tarea);
        mostrarMensaje("Informacion", "Tarea eliminada");
        limpiarFormulario();
        listarTareas();
    }
}
