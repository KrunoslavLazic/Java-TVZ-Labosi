package hr.java.vjezbe.entitet;

public enum Semestar{

    PRVI(1,"zimski"),
    DRUGI(2,"ljetni"),
    TRECI(3,"zimski"),
    CETVRTI(4,"ljetni"),
    PETI(5,"zimski"),
    SESTI(6,"ljetni");


    private Integer brojSemestra;
    private String dobaSemestra;

    Semestar(Integer brojSemestra, String dobaSemestra) {
        this.brojSemestra = brojSemestra;
        this.dobaSemestra = dobaSemestra;
    }

    public Integer getBrojSemestra() {
        return brojSemestra;
    }

    public String getDobaSemestra() {
        return dobaSemestra;
    }
}
