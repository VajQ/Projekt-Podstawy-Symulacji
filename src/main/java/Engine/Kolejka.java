package Engine;

import java.util.LinkedList;

public class Kolejka extends LinkedList<Klient> {

    public void dodaj(Klient k){
        this.add(k);
    }
    @Override
    public Klient getFirst(){
        Klient k = this.getFirst();
        return k;
    }


}
