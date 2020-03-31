package Engine;

import View.MainPaneController;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import javafx.application.Platform;

import java.util.Random;

import static java.lang.Thread.sleep;

public class PrzybycieKlienta extends BasicSimEvent<Oddział, Object> {
    Oddział oddział = getSimObj();



    public PrzybycieKlienta(Oddział entity, double delay) throws SimControlException{
        super(entity, delay);


    }

    protected void stateChange() throws SimControlException {
        if (oddział.otoczenie.getLiczbaKlientow() < oddział.otoczenie.getMaxKlientow()){
            oddział.otoczenie.setLiczbaKlientow(oddział.otoczenie.getLiczbaKlientow()+1);
            Random random = new Random();
            Klient klient = new Klient(
                    oddział.otoczenie.getLiczbaKlientow(),
                    random.nextInt(10),
                    oddział.otoczenie.getMinZniecierpliwienia() + (oddział.otoczenie.getMaxZniecierpliwienia() - oddział.otoczenie.getMinZniecierpliwienia()));

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Main.controller.dodajKlienta(klient);
                }
            });
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (oddział.liczbaKlientów < oddział.maxKlientów){

                oddział.liczbaKlientów++;
                oddział.getKlientówWOddziale().setValue(oddział.liczbaKlientów);
                oddział.kolejka.dodaj(klient);

                System.out.println(simTime()+ ":: Klient nr " + klient.getID() + " wchodzi do banku (Priorytet:" + klient.getPriorytet()+ ").");

                Platform.runLater(()-> Main.controller.dodajDoKolejki(oddział.kolejka.size()-1, klient.getID()));
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                klient.setStartOczekiwania(simTime());
                oddział.getKlientówWKolejce().setValue(oddział.kolejka.size());
                double zniecierpliwienie = oddział.simgenerator.uniform(1/oddział.otoczenie.getMaxZniecierpliwienia(), 1/oddział.otoczenie.getMinZniecierpliwienia());
                ZniecierpliwienieKlienta zniecierpliwienieKlienta = new ZniecierpliwienieKlienta(oddział,zniecierpliwienie, klient);
                klient.setZniecierpliwienieKlienta(zniecierpliwienieKlienta);



                int ID = 0;
                boolean flag=false;
                for (Okienko okienko: oddział.okienka) {
                    if (okienko.isWolne() && !flag){
                        flag =true;
                        ID = okienko.getID();
                        break;
                    }

                }
                if (flag){
                    new ZajęcieOkienka(oddział, ID,0);
                }

            }
            else {
                oddział.otoczenie.setStrata(oddział.otoczenie.getStrata() + 1);
                System.out.println(simTime()+ ":: Klient nr " + klient.getID() + " nie miesci się w banku");
                Platform.runLater(()-> Main.controller.wyjscieKlienta(klient.getID()));
            }

            double dt = oddział.simgenerator.exponential(1/oddział.otoczenie.getCzestotliwoscPrzybycia());
            new PrzybycieKlienta(oddział, dt);

        }
    }

    protected void onTermination() throws SimControlException {

    }

    protected void onInterruption() throws SimControlException {

    }
}
