package ba.unsa.etf.rpr;

public class NeispravnaOsnovica extends Exception {
    public NeispravnaOsnovica() {}

    public NeispravnaOsnovica(String poruka){
        super(poruka);
    }
}
