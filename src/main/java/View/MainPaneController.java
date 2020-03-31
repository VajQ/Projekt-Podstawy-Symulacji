package View;

import Engine.Klient;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;



public class MainPaneController implements Initializable {
    private Circle circles[] = new Circle[Parametry.maxPrzybylych];
    private Text texts[] = new Text[Parametry.maxPrzybylych];
    private StackPane stacks[] = new StackPane[Parametry.maxPrzybylych];
    private StackPane[] kolejka = new StackPane[Parametry.maxKlientów];

    private AnchorPane root;

    private int count= 0;
    private double radius;
    private Rectangle[] okienka;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void addOkienka(){
        okienka = new Rectangle[Parametry.liczbaOkienek];
        for (int i = 0; i < Parametry.liczbaOkienek; i++) {
            okienka[i] = new Rectangle(Parametry.MAX_WIDTH/Parametry.liczbaOkienek, Parametry.MAX_HEIGHT/6);
            okienka[i].setStroke(Color.FORESTGREEN);
            okienka[i].setStrokeWidth(10);
            okienka[i].setStrokeType(StrokeType.INSIDE);
            okienka[i].setFill(Color.AZURE);
            okienka[i].setLayoutX(i*okienka[i].getWidth());
        }

        root.getChildren().addAll(okienka);
    };

    public void addRoot(AnchorPane root){
        this.root = root;

    }
    public AnchorPane getRoot(){
        return root;
    }

    public void dodajKlienta(Klient klient){
        circles[count] = new Circle(Parametry.MAX_WIDTH / (2*Parametry.maxKlientów));
        circles[count].setFill(Color.YELLOW);
        texts[count] = new Text(klient.getID() + "(" + Integer.toString(klient.getPriorytet()) + ")");
        texts[count].setBoundsType(TextBoundsType.VISUAL);
        stacks[count] = new StackPane();
        stacks[count].getChildren().addAll(circles[count], texts[count]);

        stacks[count].setLayoutX(stacks[count].getWidth());
        stacks[count].setLayoutY(Parametry.MAX_HEIGHT-circles[count].getRadius());
        radius = circles[count].getRadius();
        root.getChildren().add(stacks[count]);
        count++;
    }


    public void poAwarii(int pozycja, int ID){
        TranslateTransition tt = new TranslateTransition(Duration.millis(100), stacks[ID-1]);
        Text tempText = (Text) stacks[ID-1].getChildren().get(1);
        tempText.setText(ID + "(10)");
        Circle tempCircle = (Circle)stacks[ID-1].getChildren().get(0);
        tempCircle.setFill(Color.ORANGE);
        kolejka[pozycja] = stacks[ID-1];

        tt.setToY(-2*radius);
        tt.setToX(pozycja * 2 * radius);
        tt.play();

    }


    public void powrotDoKolejki(int pozycja, int ID){
        TranslateTransition tt = new TranslateTransition(Duration.millis(100), stacks[ID-1]);
        Circle tempCircle = (Circle)stacks[ID-1].getChildren().get(0);
        tempCircle.setFill(Color.BLUE);
        kolejka[pozycja] = stacks[ID-1];
        tt.setToY(-2*radius);
        tt.setToX(pozycja * 2 * radius);
        tt.play();

    }


    public void dodajDoKolejki(int pozycja, int ID){
        TranslateTransition tt = new TranslateTransition(Duration.millis(100), stacks[ID-1]);

        kolejka[pozycja] = stacks[ID-1];
        tt.setToY(-2*radius);
        tt.setToX(pozycja * 2 * radius);
        tt.play();

    }

    public int getPozycja(int ID){
        for (int i = 0; i < kolejka.length; i++) {
            if (kolejka[i]==stacks[ID-1]){
                return i;
            }

        }
        return -1;
    }

    public void aktualizujKolejke(int pozycja){




            for (int i = pozycja; i < kolejka.length -1; i++) {

                if (kolejka[i + 1] != null) {
                    kolejka[i] = kolejka[i + 1];
                    kolejka[i+1]=null;
                    TranslateTransition tt = new TranslateTransition(Duration.millis(1000), kolejka[i]);
                    tt.setByX(-2 * radius);
                    tt.play();

                }

            }
            kolejka[kolejka.length-1] = null;

    }

    public void wyjscieKlienta(int ID){
        TranslateTransition tt = new TranslateTransition(Duration.millis(3000), stacks[ID-1]);
        Circle tempCircle = (Circle)stacks[ID-1].getChildren().get(0);
        tempCircle.setFill(Color.BLACK);
        tt.setToX(Parametry.MAX_WIDTH - 2 * radius);
        tt.setToY(0);
        tt.play();
        tt.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                root.getChildren().remove(stacks[ID-1]);
            }
        });

    }

    public void zniecierpliwienieKlientaOkienko(int ID){
        TranslateTransition tt = new TranslateTransition(Duration.millis(3000), stacks[ID-1]);
        Circle tempCircle = (Circle)stacks[ID-1].getChildren().get(0);
        tempCircle.setFill(Color.RED);
        tt.setToX(Parametry.MAX_WIDTH - 2 * radius);
        tt.setToY(0);
        tt.play();
        tt.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {

                root.getChildren().remove(stacks[ID-1]);
            }
        });

    }

    public void zniecierpliwienieKlientaKolejka(int ID){
        int pozycja = getPozycja(ID);
        kolejka[pozycja]=null;

        aktualizujKolejke(pozycja);

        TranslateTransition tt = new TranslateTransition(Duration.millis(3000), stacks[ID-1]);
        Circle tempCircle = (Circle)stacks[ID-1].getChildren().get(0);
        tempCircle.setFill(Color.RED);
        tt.setToX(Parametry.MAX_WIDTH - 2 * radius);
        tt.setToY(0);
        tt.play();
        tt.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {

                root.getChildren().remove(stacks[ID-1]);
            }
        });

    }



    public void obsluzonyKlient(int ID){
        TranslateTransition tt = new TranslateTransition(Duration.millis(3000), stacks[ID-1]);
        Circle tempCircle = (Circle)stacks[ID-1].getChildren().get(0);
        tempCircle.setFill(Color.GREEN);
        tt.setToX(Parametry.MAX_WIDTH - 2 * radius);
        tt.setToY(0);
        tt.play();
        tt.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {

                root.getChildren().remove(stacks[ID-1]);
            }
        });

    }

    public StackPane[] getStack() {
        return stacks;
    }

    public void podejdzDoOkienka(int ID, int okienkoID) {
        int pozycja = getPozycja(ID);
        kolejka[pozycja]=null;

        aktualizujKolejke(pozycja);

        TranslateTransition tt = new TranslateTransition(Duration.millis(100), stacks[ID - 1]);
        tt.setToX(okienka[okienkoID].getWidth()  * okienkoID + okienka[okienkoID].getWidth()/2);
        tt.setToY(-5 * okienka[okienkoID].getHeight());
        tt.play();
        tt.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {

        }
        });

    }

    public void zajeteOkienko(int okienkoID){
        okienka[okienkoID].setStroke(Color.YELLOW);
    }

    public void wolneOkienko(int okienkoID){
        okienka[okienkoID].setStroke(Color.FORESTGREEN);
    }

    public void zamknieteOkienko(int okienkoID){
        okienka[okienkoID].setStroke(Color.RED);
    }
}
