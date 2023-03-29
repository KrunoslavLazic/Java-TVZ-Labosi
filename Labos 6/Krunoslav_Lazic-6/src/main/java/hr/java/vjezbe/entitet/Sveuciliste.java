package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

public class Sveuciliste <T extends ObrazovnaUstanova> {

    private List<T> listaSveucilista;

    public Sveuciliste() {
        this.listaSveucilista = new ArrayList<>();
    }

    public void dodajObrazovnuUstanovu(T ustanova){
        this.listaSveucilista.add(ustanova);
    }
    public T dohvatiObrazovnuUstanovu(Integer i){
        return this.listaSveucilista.get(i);
    }

    public List<T> getListaSveucilista() {
        return new ArrayList<>(listaSveucilista);
    }
}
