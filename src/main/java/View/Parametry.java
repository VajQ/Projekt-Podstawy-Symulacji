package View;

public class Parametry {
    public static final double MAX_HEIGHT = 900;
    public static final double MAX_WIDTH = 1920;
    public static int maxKlientów;
    public static int maxPrzybylych;
    public static int liczbaOkienek;

    public static void setLiczbaOkienek(int liczbaOkienek) {
        Parametry.liczbaOkienek = liczbaOkienek;
    }

    public static void setMaxKlientów(int nMaxKlientów){
        maxKlientów = nMaxKlientów;
    }

    public static void setMaxPrzybylych(int maxPrzybylych) {
        Parametry.maxPrzybylych = maxPrzybylych;
    }
}
