package hr.java.vjezbe.entitet;

public enum Ocjena {

    NEDOVOLJAN(1,"Nedovoljan"),
    DOVOLJAN(2,"Dovoljan"),
    DOBAR(3,"Dobar"),
    VRLO_DOBAR(4,"Vrlo Dobar"),
    IZVRSTAN(5,"Izvrstan");


    private Integer ocjena;
    private String tekstOcjene;

    Ocjena(Integer ocjena, String tekstOcjene) {
        this.ocjena = ocjena;
        this.tekstOcjene = tekstOcjene;
    }

    public Integer getOcjena() {
        return ocjena;
    }

    public void setOcjena(Integer ocjena) {
        this.ocjena = ocjena;
    }

    public String getTekstOcjene() {
        return tekstOcjene;
    }

    public void setTekstOcjene(String tekstOcjene) {
        this.tekstOcjene = tekstOcjene;
    }

    @Override
    public String toString() {
        return "Ocjena{" +
                "ocjena=" + ocjena +
                ", tekstOcjene='" + tekstOcjene + '\'' +
                '}';
    }
}
