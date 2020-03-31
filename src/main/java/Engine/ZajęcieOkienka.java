package Engine;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import javafx.application.Platform;

import static java.lang.Thread.sleep;

public class ZajęcieOkienka extends BasicSimEvent<Oddział, Object> {
    Oddział oddział = getSimObj();
    Okienko okienko;



    public ZajęcieOkienka(Oddział entity, int okienkoID, double delay) throws SimControlException{
        super(entity, delay);
        this.okienko = oddział.okienka.get(okienkoID);


    }

    protected void stateChange() throws SimControlException {
        okienko.setWolne(false);
        Platform.runLater(()-> Main.controller.zajeteOkienko(okienko.getID()));


        int maxPriorytet = 0;
        for (int i = 0; i < oddział.kolejka.size(); i++) {
            if (oddział.kolejka.get(i).getPriorytet() > maxPriorytet){
                maxPriorytet = oddział.kolejka.get(i).getPriorytet();
            }
        }
        boolean znaleziono = false;
        int i=0;
        Klient k = null;
        while (!znaleziono){
            k = oddział.kolejka.get(i);
            if (k.getPriorytet() == maxPriorytet) {
                znaleziono = true;
            }
            i++;
        }
        okienko.setObslugiwany(k);
        okienko.getObslugiwany().setPrzyOkienku(true);
        System.out.println(simTime()+ ":: Klient nr."+ k.getID() + " Zajmuje okienko nr." + okienko.getID());
        Platform.runLater(()-> Main.controller.podejdzDoOkienka(okienko.getObslugiwany().getID(),okienko.getID()));
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        oddział.kolejka.remove(k);
        oddział.getCzasOczekiwania().setValue(simTime()- k.getStartOczekiwania());
        k.setStartObsługi(simTime());
        oddział.getKlientówWKolejce().setValue(oddział.kolejka.size());


        double dt = oddział.simgenerator.exponential(1/okienko.getCzasObslugi());
        ZwolnienieOkienka zwolnienieOkienka = new ZwolnienieOkienka(oddział, dt, okienko);
        okienko.getObslugiwany().setZwolnienieOkienka(zwolnienieOkienka);
        okienko.setZwolnienieOkienka(zwolnienieOkienka);






    }

    protected void onTermination() throws SimControlException {

    }

    protected void onInterruption() throws SimControlException {

    }
}
