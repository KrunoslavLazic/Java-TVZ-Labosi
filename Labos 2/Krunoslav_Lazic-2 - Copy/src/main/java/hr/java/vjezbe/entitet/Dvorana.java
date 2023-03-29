package hr.java.vjezbe.entitet;

public record Dvorana(String ime, String zgrada) {

    public Dvorana(String ime, String zgrada){
        this.ime=ime;
        this.zgrada=zgrada;
    }

}
