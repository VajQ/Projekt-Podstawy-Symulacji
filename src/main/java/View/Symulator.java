package View;

import Engine.*;
import dissimlab.monitors.Diagram;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Symulator implements Runnable {
    private int liczbaOkienek = 7;
    private int limitKlientow = 100;
    private int maxKlientow = 15;
    private double minZniecierpliwienie = 0.2;
    private double maxZniecierpliwienie = 0.5;
    private double czestotliwoscPrzybycia = 5;
    private double szansaPowrotu = 0.1;

    public Symulator(int liczbaOkienek, int limitKlientow, int maxKlientow, double szansaPowrotu) {
        this.liczbaOkienek = liczbaOkienek;
        this.limitKlientow = limitKlientow;
        this.maxKlientow = maxKlientow;
        this.szansaPowrotu = szansaPowrotu;
        Parametry.setMaxKlientów(maxKlientow);
        Parametry.setLiczbaOkienek(liczbaOkienek);
        Parametry.setMaxPrzybylych(limitKlientow);
    }

    public Symulator (){
        Parametry.setMaxKlientów(maxKlientow);
        Parametry.setLiczbaOkienek(liczbaOkienek);
        Parametry.setMaxPrzybylych(limitKlientow);
    }

    public void run(){
        SimManager sim = new SimManager();
        sim.setEndSimTime(10);
        //sim.setSimTimeRatio(0.1);
       //SimManager.simMode = SimParameters.SimMode.ASTRONOMICAL;





        Random random = new Random();

        List<Okienko> okienka= new ArrayList<Okienko>();
        for (int i = 0; i < liczbaOkienek; i++) {
            okienka.add(new Okienko(i, random.nextDouble()));
        }

        Otoczenie otoczenie = new Otoczenie(limitKlientow, minZniecierpliwienie, maxZniecierpliwienie, czestotliwoscPrzybycia, szansaPowrotu);

        Kolejka kolejka = new Kolejka();

        Oddział oddział = new Oddział(okienka, otoczenie, kolejka, maxKlientow, sim);

        try {
            new PrzybycieKlienta(oddział, 0);
        } catch (SimControlException e) {
            e.printStackTrace();
        }

        int noweOkienko = random.nextInt(oddział.okienka.size());
        double dt = oddział.simgenerator.uniform(2/okienka.get(noweOkienko).getCzasObslugi(), 4/okienka.get(noweOkienko).getCzasObslugi());

        try {
            new Awaria(oddział, noweOkienko, dt);
        } catch (SimControlException e) {
            e.printStackTrace();
        }


        try {
            sim.startSimulation();
        } catch (SimControlException e) {
            e.printStackTrace();
        }

        System.out.println("Obsłużono wszystkich klientów, Bank zostaje zamknięty");
        System.out.println("Strata = " + (double) oddział.otoczenie.getStrata() / (double)oddział.otoczenie.getMaxKlientow()*100 + "%");
        System.out.println("Zniecierpliwonych = " + (double) oddział.otoczenie.getZniecierpliwionych() / (double)oddział.otoczenie.getMaxKlientow()*100 + "%");

        System.out.println("Powrotów = " + (double) oddział.otoczenie.getPowrotów() / (double)oddział.otoczenie.getMaxKlientow()*100 + "%");
        System.out.println("Awarii = " +  oddział.otoczenie.getAwarii());








        Diagram diagram = new Diagram(Diagram.DiagramType.TIME_FUNCTION, "Oczekiwana graniczna liczba klientow w oddziale oraz kolejce");
        diagram.add(oddział.getKlientówWOddziale(), Color.BLUE);
        diagram.add(oddział.getKlientówWKolejce(), Color.RED);
        diagram.show();

        Diagram diagram2 = new Diagram(Diagram.DiagramType.HISTOGRAM, "Oczekiwany graniczny czas obsługi klienta");
        diagram2.add(oddział.getCzasObsługi(), Color.GREEN);
        diagram2.show();

        Diagram diagram3 = new Diagram(Diagram.DiagramType.HISTOGRAM, "Oczekiwany graniczny czas oczekiwania w kolejca na rozpoczęcie obsługi");
        diagram3.add(oddział.getCzasOczekiwania(), Color.BLACK);
        diagram3.show();

    }




}
