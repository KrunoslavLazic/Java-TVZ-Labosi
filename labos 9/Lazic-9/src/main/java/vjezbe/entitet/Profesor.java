package vjezbe.entitet;

public class Profesor extends Osoba {
    private String sifra;
    private String titula;

    public Profesor(long id, String sifra, String ime, String prezime, String titula) {
        super(id, ime, prezime);
        this.sifra = sifra;
        this.titula = titula;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getTitula() {
        return titula;
    }

    public void setTitula(String titula) {
        this.titula = titula;
    }


    /**
     * Klasa za generiranje objekata tipa {@link Profesor}
     */
    public static class Builder {
        private long id;
        private String sifra;
        private String ime;
        private String prezime;
        private String titula;

        public Builder(long id) {
            this.id = id;
        }

        public Builder withSifra(String sifra) {
            this.sifra = sifra;
            return this;
        }

        public Builder withIme(String ime) {
            this.ime = ime;
            return this;
        }

        public Builder withPrezime(String prezime) {
            this.prezime = prezime;
            return this;
        }

        public Builder withTitula(String titula) {
            this.titula = titula;
            return this;
        }

        /**
         * Generira objekt tipa {@link Profesor}.
         * @return Generiran objekt tipa {@link Profesor}.
         */
        public Profesor build() {
            return new Profesor(id, sifra, ime, prezime, titula);
        }
    }
}
