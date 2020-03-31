package Engine;

public class Otoczenie {
    private int liczbaKlientow;
    private int maxKlientow;
    private int strata;
    private int zniecierpliwionych;
    private int awarii;
    private int powrotów;

    private double minZniecierpliwienia;
    private double maxZniecierpliwienia;
    private double czestotliwoscPrzybycia;
    private double szansaPowrotu;

    public Otoczenie(int maxKlientow, double minZniecierpliwienia, double maxZniecierpliwienia, double czestotliwoscPrzybycia, double szansaPowrotu){
        strata = 0;
        zniecierpliwionych = 0;
        awarii = 0;
        powrotów = 0;
        liczbaKlientow = 0;
        this.maxKlientow = maxKlientow;
        this.minZniecierpliwienia = minZniecierpliwienia;
        this.maxZniecierpliwienia = maxZniecierpliwienia;
        this.czestotliwoscPrzybycia = czestotliwoscPrzybycia;
        this.szansaPowrotu = szansaPowrotu;
    }

    public void setLiczbaKlientow(int liczbaKlientow) {
        this.liczbaKlientow = liczbaKlientow;
    }

    public void setStrata(int strata) {
        this.strata = strata;
    }

    public double getCzestotliwoscPrzybycia() {
        return czestotliwoscPrzybycia;
    }

    public double getMinZniecierpliwienia() {
        return minZniecierpliwienia;
    }

    public double getMaxZniecierpliwienia() {
        return maxZniecierpliwienia;
    }

    public int getLiczbaKlientow() {
        return liczbaKlientow;
    }

    public int getMaxKlientow() {
        return maxKlientow;
    }

    public int getStrata() {
        return strata;
    }

    public double getSzansaPowrotu() {
        return szansaPowrotu;
    }

    public int getZniecierpliwionych() {
        return zniecierpliwionych;
    }

    public void setZniecierpliwionych(int zniecierpliwionych) {
        this.zniecierpliwionych = zniecierpliwionych;
    }

    public int getAwarii() {
        return awarii;
    }

    public void setAwarii(int awarii) {
        this.awarii = awarii;
    }

    public int getPowrotów() {
        return powrotów;
    }

    public void setPowrotów(int powrotów) {
        this.powrotów = powrotów;
    }
}
