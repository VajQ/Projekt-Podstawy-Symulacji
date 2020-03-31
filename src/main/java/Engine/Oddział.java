package Engine;

import dissimlab.monitors.MonitoredVar;
import dissimlab.random.SimGenerator;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimManager;

import java.util.List;

public class Oddział extends BasicSimObj {
    public List<Okienko> okienka;
    public Otoczenie otoczenie;
    public Kolejka kolejka;
    public SimGenerator simgenerator;
    public int maxKlientów;
    public int liczbaKlientów;
    private MonitoredVar klientówWOddziale;
    private MonitoredVar klientówWKolejce;
    private MonitoredVar czasObsługi;
    private MonitoredVar czasOczekiwania;
    private double prawdopodobieństwoRezygnacji;




    public Oddział(List<Okienko> okienka, Otoczenie otoczenie, Kolejka kolejka, int maxKlientów, SimManager sm){
        this.okienka = okienka;
        this.otoczenie = otoczenie;
        this.kolejka = kolejka;
        this.maxKlientów = maxKlientów;
        this.simgenerator = new SimGenerator();
        liczbaKlientów = 0;
        klientówWOddziale = new MonitoredVar(sm);
        klientówWKolejce = new MonitoredVar(sm);
        czasObsługi = new MonitoredVar(sm);
        czasOczekiwania = new MonitoredVar(sm);
    }

    public MonitoredVar getKlientówWOddziale() {
        return klientówWOddziale;
    }

    public MonitoredVar getKlientówWKolejce() {
        return klientówWKolejce;
    }

    public MonitoredVar getCzasObsługi() {
        return czasObsługi;
    }

    public MonitoredVar getCzasOczekiwania() {
        return czasOczekiwania;
    }
}
