package gm.tareas.presentacion;

import gm.tareas.TareasApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class SistemaTareasFx extends Application {

    private ConfigurableApplicationContext applicationContext;


    /**public static void main(String[] args) {
        launch(args);
    }**/

    /**
     * Inicia la aplicacion de javaFX
     * @param primaryStage
     */


    /**
     * Inicia la ejecucion de Spring con el metodo RUN de la clase APPLICATION, donde inicializa la clase principal
     * haciendo un llamado a esta clase e iniciando con este metodo al metodo de Spring con el <b>new SpringAplicationbuilde(TareasAplication.class)</b>
     * el cual hara el llamado hacia las funciones de Spring de la clase configurada
     * Con el metodo init() se hace el llamdo a la
     */
    @Override
    public void init(){
        this.applicationContext= new SpringApplicationBuilder(TareasApplication.class).run();

    }

    /**
     * Despues de la ejecucion del metodo initi() se ejecuta el metodo start()
     * Se inicia Stage(Escenario donde tendra varias escenas para modificar)
     */

    @Override
    public void start(Stage stage) throws IOException {
        //Cargar el archivo fxml desde la ubicacion desde los templates
        FXMLLoader loader = new FXMLLoader(TareasApplication.class.getResource("/templates/index.fxml"));
        loader.setControllerFactory(applicationContext::getBean);   // proporciona todos los objetos de spring para cargarlos en FX
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);          // Se establece la scena que vamos a mostrar
        stage.show();
    }

    /**
     * Se cierra toda clase de conexion abierta
     */
    @Override
    public void stop(){
        applicationContext.close();
        Platform.exit();
    }
}
