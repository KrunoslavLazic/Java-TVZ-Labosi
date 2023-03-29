package hr.java.vjezbe.entitet;

public class Profesor extends Osoba{

    private final String sifra;
    private final String titula;

    private Profesor(long id,Builder builder,String ime,String prezime){
        super(id,ime,prezime);
        this.sifra=builder.sifra;
        this.titula=builder.titula;
    }
    public static class Builder{

        private long id;
        private String sifra;
        private String titula;

        public Builder withId(long id){
            this.id = id;
            return this;
        }
        public Builder withSifra(String sifra){
            this.sifra=sifra;
            return this;
        }
        public Builder withTitula(String titula){
            this.titula=titula;
            return this;
        }
        public Profesor build(String ime, String prezime){
            Profesor profesor=new Profesor(id,this,ime,prezime);
            return profesor;
        }
    }
    public String getSifra() {
        return sifra;
    }

    public String getTitula() {
        return titula;
    }
}

