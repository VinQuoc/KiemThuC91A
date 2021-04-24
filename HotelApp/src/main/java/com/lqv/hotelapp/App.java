package com.lqv.hotelapp;

import com.lqv.pojo.Employee;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Employee emp = new Employee();

    @Override
    public void start(Stage stage) throws IOException {
//        scene = new Scene(loadFXML("primary"), 640, 480);
//        scene = new Scene(loadFXML("secondary"), 800, 600);
//        scene = new Scene(loadFXML("infoCus"), 800, 600);
        scene = new Scene(loadFXML("login"), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
    
    public static void setEmp(Employee e) throws IOException {
        emp.setId(e.getId());
        emp.setName(e.getName());
        emp.setRole(e.getRole());
    }
    
    public static Employee getEmp() {
        return emp;
    }

}
