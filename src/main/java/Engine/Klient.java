package Engine;

public class Klient {
    private int ID;
    private int priorytet;
    private double wspolczynnikZniecierpliwienia;
    private boolean przyOkienku = false;
    private ZniecierpliwienieKlienta zniecierpliwienieKlienta;
    private ZwolnienieOkienka zwolnienieOkienka;
    private double startObsługi;
    private double startOczekiwania;




    public Klient(int ID, int priotytet, double wZ){
        this.ID = ID;
        this.priorytet = priotytet;
        this.wspolczynnikZniecierpliwienia = wZ;

    }


    public ZniecierpliwienieKlienta getZniecierpliwienieKlienta() {
        return zniecierpliwienieKlienta;
    }

    public void setZniecierpliwienieKlienta(ZniecierpliwienieKlienta zniecierpliwienieKlienta) {
        this.zniecierpliwienieKlienta = zniecierpliwienieKlienta;
    }

    public ZwolnienieOkienka getZwolnienieOkienka() {
        return zwolnienieOkienka;
    }

    public void setZwolnienieOkienka(ZwolnienieOkienka zwolnienieOkienka) {
        this.zwolnienieOkienka = zwolnienieOkienka;
    }

    public void setPrzyOkienku(boolean przyOkienku) {
        this.przyOkienku = przyOkienku;
    }

    public boolean isPrzyOkienku() {
        return przyOkienku;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPriorytet() {
        return priorytet;
    }

    public void setPriorytet(int priorytet) {
        this.priorytet = priorytet;
    }

    public double getWspolczynnikZniecierpliwienia() {
        return wspolczynnikZniecierpliwienia;
    }

    public void setWspolczynnikZniecierpliwienia(double wspolczynnikZniecierpliwienia) {
        this.wspolczynnikZniecierpliwienia = wspolczynnikZniecierpliwienia;
    }

    public double getStartObsługi() {
        return startObsługi;
    }

    public void setStartObsługi(double startObsługi) {
        this.startObsługi = startObsługi;
    }

    public double getStartOczekiwania() {
        return startOczekiwania;
    }

    public void setStartOczekiwania(double startOczekiwania) {
        this.startOczekiwania = startOczekiwania;
    }
}
