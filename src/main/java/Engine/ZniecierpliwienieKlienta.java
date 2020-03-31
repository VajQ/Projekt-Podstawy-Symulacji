package Engine;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import javafx.application.Platform;

import static java.lang.Thread.sleep;

public class ZniecierpliwienieKlienta extends BasicSimEvent<Oddział, Klient> {
    Klient klient;


    public ZniecierpliwienieKlienta(Oddział entity, double delay, Klient klient) throws SimControlException {
        super(entity, delay, klient);
        this.klient = klient;

    }

    @Override
    protected void stateChange() throws SimControlException {
        Oddział oddział = getSimObj();
        if (!klient.isPrzyOkienku()){
            oddział.kolejka.remove(klient);
            System.out.println(simTime() + " :: zniecierpliwiony klienta nr=" + klient.getID() + " odchodzi z kolejki");
            Platform.runLater(()-> Main.controller.zniecierpliwienieKlientaKolejka(klient.getID()));
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            oddział.getKlientówWKolejce().setValue(oddział.kolejka.size());

        }
        else {
            Okienko okienko = klient.getZwolnienieOkienka().okienko;
            klient.getZwolnienieOkienka().interrupt();
            okienko.setWolne(true);
            System.out.println(simTime() + " :: zniecierpliwiony klienta nr=" + klient.getID() + " odchodzi z okienka nr " + okienko.getID());
            Platform.runLater(()->Main.controller.wolneOkienko(okienko.getID()));
            Platform.runLater(()-> Main.controller.zniecierpliwienieKlientaOkienko(klient.getID()));
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(oddział.kolejka.size()>0){
                new ZajęcieOkienka(oddział, okienko.getID(), 0);
            }
        }
        oddział.otoczenie.setZniecierpliwionych(oddział.otoczenie.getZniecierpliwionych() + 1);

        oddział.liczbaKlientów--;

        oddział.getKlientówWOddziale().setValue(oddział.liczbaKlientów);






    }

    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    protected void onInterruption() throws SimControlException {

    }

}
