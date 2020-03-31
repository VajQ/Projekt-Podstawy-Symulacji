package Engine;

public class Okienko {
    private int status;
    private int ID;
    private Klient obslugiwany;
    private double czasObslugi;
    private boolean dziala;
    private boolean wolne;
    private ZwolnienieOkienka zwolnienieOkienka;


    public Okienko(int ID, double czasObslugi){
        wolne = true;
        dziala = true;
        this.czasObslugi = czasObslugi;
        this.ID = ID;
    }

    public ZwolnienieOkienka getZwolnienieOkienka() {
        return zwolnienieOkienka;
    }

    public void setZwolnienieOkienka(ZwolnienieOkienka zwolnienieOkienka) {
        this.zwolnienieOkienka = zwolnienieOkienka;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Klient getObslugiwany() {
        return obslugiwany;
    }

    public void setObslugiwany(Klient obslugiwany) {
        this.obslugiwany = obslugiwany;
    }

    public double getCzasObslugi() {
        return czasObslugi;
    }

    public void setCzasObslugi(double czasObslugi) {
        this.czasObslugi = czasObslugi;
    }

    public boolean isDziala() {
        return dziala;
    }

    public void setDziala(boolean dziala) {
        this.dziala = dziala;
    }

    public boolean isWolne() {
        return wolne;
    }

    public void setWolne(boolean wolne) {
        this.wolne = wolne;
    }
}
