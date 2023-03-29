package hr.java.vjezbe.entitet;

public sealed interface Online permits Ispit{

    String softwareZaIspit(String imeSoftwarea);

}
