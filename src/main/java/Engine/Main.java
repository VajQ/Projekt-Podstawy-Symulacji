package Engine;


import View.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import dissimlab.monitors.Diagram;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimManager;
import dissimlab.simcore.SimParameters;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main extends Application {

    public static MainPaneController controller;
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj liczbę okienek:");
        int liczbaOkienek = scanner.nextInt();
        System.out.println("Podaj liczbę klientów do obsłużenia:");
        int limitKlientow = scanner.nextInt();
        System.out.println("Podaj limit klientów w banku:");
        int maxKlientow = scanner.nextInt();
        System.out.println("Podaj szansę powrotu:");
        double szansaPowrotu = scanner.nextDouble();





        new Thread(){
            @Override
            public void run() {
                launch(Main.class, args);
            }
        }.start();

        Symulator symulator = new Symulator(liczbaOkienek, limitKlientow, maxKlientow, szansaPowrotu);
        symulator.run();


    }


    public void start(Stage primaryStage) throws Exception {

        AnchorPane root = FXMLLoader.load(getClass().getClassLoader().getResource("MainPane.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.show();
        controller = new MainPaneController();
        controller.addRoot(root);
        controller.addOkienka();





    }
}
