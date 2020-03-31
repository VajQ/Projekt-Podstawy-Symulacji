package Engine;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import javafx.application.Platform;

import java.util.Random;

import static java.lang.Thread.sleep;

public class Naprawa extends BasicSimEvent<Oddział, Object> {
    Oddział oddział = getSimObj();
    Okienko okienko;


    public Naprawa(Oddział entity, int okienkoID, double delay) throws SimControlException {
        super(entity, delay);
        okienko = oddział.okienka.get(okienkoID);

    }

    protected void stateChange() throws SimControlException {
        okienko.setWolne(true);
        Platform.runLater(()-> Main.controller.wolneOkienko(okienko.getID()));
        if (!oddział.kolejka.isEmpty()) {
            new ZajęcieOkienka(oddział, okienko.getID(), 0);
        }
        System.out.println(simTime()+":: Okienko nr. " + okienko.getID() + " zostało naprawione");
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        double dt = oddział.simgenerator.uniform(2/okienko.getCzasObslugi(), 4/okienko.getCzasObslugi());
        Random random = new Random();
        int noweOkienko = random.nextInt(oddział.okienka.size());
        if (oddział.otoczenie.getLiczbaKlientow() < oddział.otoczenie.getMaxKlientow()) {
            new Awaria(oddział, noweOkienko, dt);
        }
    }

    protected void onTermination() throws SimControlException {

    }

    protected void onInterruption() throws SimControlException {

    }
}
