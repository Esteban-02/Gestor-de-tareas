package gm.tareas;

import gm.tareas.presentacion.SistemaTareasFx;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TareasApplication {

	/**
	 * La forma de iniciar la aplicacion es a traves de la clase de <b>TareasApplication</b> la cual esta programada para dar inicio a toda la
	 * logica por la clase de javaFX <b>SistemaTareasFX.class</b> que se encuentra en el paquete de <b>presentacion</b>, desde esta clase se
	 * hace la construccion de todas las librerias de SpringBoot y se inicia todo el proceso de FX
	 * @param args
	 */

	public static void main(String[] args) {
		//SpringApplication.run(TareasApplication.class, args);
		Application.launch(SistemaTareasFx.class, args);
	}

}
