package Engine;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import javafx.application.Platform;

import java.util.Random;

import static java.lang.Thread.sleep;

public class ZwolnienieOkienka  extends BasicSimEvent<Oddział, Object> {
    Oddział oddział = getSimObj();
    Okienko okienko;


    public ZwolnienieOkienka(Oddział entity, double delay, Okienko okienko) throws SimControlException {
        super(entity, delay, okienko);
        this.okienko = okienko;

    }

    protected void stateChange() throws SimControlException {
        okienko.setWolne(true);
        Platform.runLater(()-> Main.controller.wolneOkienko(okienko.getID()));


        if (okienko.getObslugiwany()!=null) {
            okienko.getObslugiwany().getZniecierpliwienieKlienta().interrupt();
    }

        System.out.println(simTime()+ ":: Klient nr."+ okienko.getObslugiwany().getID() + " opuszcza okienko nr." + okienko.getID());
        oddział.getCzasObsługi().setValue(simTime() - okienko.getObslugiwany().getStartObsługi());
        Random random = new Random();
        if (oddział.otoczenie.getSzansaPowrotu() > random.nextDouble()){
            System.out.println(simTime()+ ":: Klient nr."+ okienko.getObslugiwany().getID() + " wraca do kolejki");
            oddział.otoczenie.setPowrotów(oddział.otoczenie.getPowrotów() + 1);

            oddział.kolejka.dodaj(okienko.getObslugiwany());
            Platform.runLater(()->Main.controller.powrotDoKolejki(oddział.kolejka.size()-1, okienko.getObslugiwany().getID()));
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            oddział.getKlientówWKolejce().setValue(oddział.kolejka.size());
            okienko.getObslugiwany().setPrzyOkienku(false);
        }
        else {

            oddział.liczbaKlientów--;
            Platform.runLater(()->Main.controller.obsluzonyKlient(okienko.getObslugiwany().getID()));
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            oddział.getKlientówWOddziale().setValue(oddział.liczbaKlientów);

        }
        if(oddział.kolejka.size()>0){
            new ZajęcieOkienka(oddział, okienko.getID(), 0);
        }

    }

    protected void onTermination() throws SimControlException {

    }

    protected void onInterruption() throws SimControlException {

    }
}

