package hr.java.vjezbe.entitet;

/**
 * Record za novu dvoranu
 * @param ime ime dvorane
 * @param zgrada ime zgrade
 */
public record Dvorana(String ime, String zgrada) {

    public Dvorana(String ime, String zgrada){
        this.ime=ime;
        this.zgrada=zgrada;
    }

}
