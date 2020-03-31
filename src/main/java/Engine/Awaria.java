package Engine;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import javafx.application.Platform;

import static java.lang.Thread.sleep;

public class Awaria extends BasicSimEvent<Oddział, Object> {
    Oddział oddział = getSimObj();
    Okienko okienko;

    public Awaria(Oddział entity, int okienkoID, double delay) throws SimControlException {
        super(entity, delay);
        okienko = oddział.okienka.get(okienkoID);

    }

    protected void stateChange() throws SimControlException {
        if (!okienko.isWolne()) {

            if (okienko.getZwolnienieOkienka() != null) {
                okienko.getZwolnienieOkienka().interrupt();
            }
            if (okienko.getObslugiwany() != null) {
                okienko.setWolne(false);
                //Platform.runLater(()-> StartController.controller.zamknieteOkienko(okienko.getID()));
                Platform.runLater(()-> Main.controller.zamknieteOkienko(okienko.getID()));
                System.out.println(simTime() + ":: Okienko nr. " + okienko.getID() + " uległo awarii, klient nr." + okienko.getObslugiwany().getID() + " przeniesiony do kolejki");

                okienko.getObslugiwany().setPriorytet(10);

                //Platform.runLater(()-> StartController.controller.poAwarii(oddział.kolejka.size(), okienko.getObslugiwany().getID()));
                Platform.runLater(()-> Main.controller.poAwarii(oddział.kolejka.size(), okienko.getObslugiwany().getID()));
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                oddział.kolejka.dodaj(okienko.getObslugiwany());


            }
        }
        else{
            okienko.setWolne(false);
            //Platform.runLater(()->StartController.controller.zamknieteOkienko(okienko.getID()));
            Platform.runLater(()->Main.controller.zamknieteOkienko(okienko.getID()));
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(simTime() + ":: Okienko nr. " + okienko.getID() + " uległo awarii");
        }

        oddział.otoczenie.setAwarii(oddział.otoczenie.getAwarii() + 1);
        double dt = oddział.simgenerator.uniform(2/okienko.getCzasObslugi(), 4/okienko.getCzasObslugi());
        new Naprawa(oddział, okienko.getID(), dt);

    }

    protected void onTermination() throws SimControlException {

    }

    protected void onInterruption() throws SimControlException {

    }
}
