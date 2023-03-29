package vjezbe.entitet;

public enum Ocjena {

    NEDOVOLJAN(1,"Nedovoljan"),
    DOVOLJAN(2,"Dovoljan"),
    DOBAR(3,"Dobar"),
    VRLO_DOBAR(4,"Vrlo Dobar"),
    IZVRSTAN(5,"Izvrstan");


    private final Integer ocjena;
    private final String tekstOcjene;

    Ocjena(Integer ocjena, String tekstOcjene) {
        this.ocjena = ocjena;
        this.tekstOcjene = tekstOcjene;
    }

    public Integer getOcjena() {
        return ocjena;
    }

    public String getTekstOcjene() {
        return tekstOcjene;
    }
    public static Ocjena ocjenaUEnum(Integer ocjena){
        return switch (ocjena){
            case 1->Ocjena.NEDOVOLJAN;
            case 2->Ocjena.DOVOLJAN;
            case 3->Ocjena.DOBAR;
            case 4->Ocjena.VRLO_DOBAR;
            case 5->Ocjena.IZVRSTAN;
            default->throw new IllegalArgumentException("Unexpected value: " + ocjena);
        };
    }


    @Override
    public String toString() {
        return "Ocjena{" +
                "ocjena=" + ocjena +
                ", tekstOcjene='" + tekstOcjene + '\'' +
                '}';
    }
}
