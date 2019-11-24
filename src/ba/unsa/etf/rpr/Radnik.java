package ba.unsa.etf.rpr;

public class Radnik implements Comparable<Radnik> {

    private String imePrezime;
    private String jmbg;
    private double[] plate = new double[1000];
    private int brojPlata = 0;

    public Radnik(String imePrezime, String jmbg) {
        this.imePrezime = imePrezime;
        this.jmbg = jmbg;
    }

    public String getImePrezime() {
        return this.imePrezime;
    }

    public String getJmbg() {
        return this.jmbg;
    }

    public void setImePrezime(String imePrezime) {
        this.imePrezime=imePrezime;
    }

    public void setJmbg(String jmbg) {
        this.jmbg=jmbg;
    }

    public void dodajPlatu(double plata) {
        if(brojPlata==1000){
            throw new IllegalArgumentException("Ne možete registrovati više od 1000 plata za radnika " + this.imePrezime);
        }
        this.plate[brojPlata]=plata;
        brojPlata++;
    }

    public double prosjecnaPlata() {
        double suma=0;

        if(brojPlata == 0) return 0;

        for(int i=0; i<brojPlata; i++){
            suma = suma + plate[i];
        }

        return (suma/brojPlata);
    }

    @Override
    public int compareTo(Radnik radnik) {
        int povratnaVrijednost = Double.compare(radnik.prosjecnaPlata(), this.prosjecnaPlata());
        if(povratnaVrijednost == 0) return 1;
        return povratnaVrijednost;
    }
}
