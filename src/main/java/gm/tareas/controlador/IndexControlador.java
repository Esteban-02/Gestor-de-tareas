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

    // mostrar la informacion segun como se vaya recuperando de la base de datos, se hara automaticamente
    private final ObservableList<Tarea> tareaList = FXCollections.observableArrayList();


    /**
     * Inicializa los diferentes metodos que se estaran usando durante toda la ejecucion del programa,
     * desde seleccionar un registro individual en la tabla, llamar los diferentes metodos de inicializacion
     * @param url
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resourceBundle
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tareaTabla.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);      // Se selecciona un solo registro a la vez de la tabla
        configurarColumnas();
        listarTareas();

    }

    private void listarTareas() {
        logger.info("Ejecutando la lista de tareas");
        tareaList.clear();
        tareaList.addAll(tareaServicio.listarTareas());     //Se obtiene toda la informacion en un obcervable y se esta agrgando toda la informacion de la tabla
        tareaTabla.setItems(tareaList); //Se agrega todo a la tabla
    }

    private void configurarColumnas() {
        idTareaColumna.setCellValueFactory(new PropertyValueFactory<>("idTarea"));
        tareaColumna.setCellValueFactory(new PropertyValueFactory<>("nombreTarea"));
        responsableColumna.setCellValueFactory(new PropertyValueFactory<>("responsable"));
        statusColumna.setCellValueFactory(new PropertyValueFactory<>("estatus"));
    }
}
