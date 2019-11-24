package ba.unsa.etf.rpr;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Preduzece {

    private int osnovica;
    private List<RadnoMjesto> radnaMjesta = new ArrayList();

    public Preduzece(int osnovica) throws NeispravnaOsnovica {
        if(osnovica <= 0) throw new NeispravnaOsnovica("Neispravna osnovica " + osnovica);
        this.osnovica = osnovica;
    }

    public int dajOsnovicu() {
        return this.osnovica;
    }

    public void postaviOsnovicu(int osnovica) throws NeispravnaOsnovica {
        if(osnovica <= 0) throw new NeispravnaOsnovica("Neispravna osnovica " + osnovica);
        this.osnovica=osnovica;
    }

    public void novoRadnoMjesto(RadnoMjesto radnoMjesto) {
        radnaMjesta.add(radnoMjesto);
    }

    public void zaposli(Radnik radnik, String nazivRadnogMjesta) {
        List<RadnoMjesto> filtrirano = radnaMjesta.stream().filter(radnoMjesto -> nazivRadnogMjesta.equals(radnoMjesto.getNaziv()) && radnoMjesto.getRadnik() == null).collect(Collectors.toList());

        if(filtrirano.isEmpty()){
            throw new IllegalStateException("Nijedno radno mjesto tog tipa nije slobodno");
        }

        filtrirano.get(0).setRadnik(radnik);
    }

    public void obracunajPlatu() {
        List<RadnoMjesto> filtrirani = radnaMjesta.stream().filter(radnoMjesto -> radnoMjesto.getRadnik() != null)
                .collect(Collectors.toList());

        for(RadnoMjesto rm : filtrirani){
            rm.getRadnik().dodajPlatu(osnovica * rm.getKoeficijent());
        }
    }

    public double iznosPlate() {
        List<RadnoMjesto> filtrirani = radnaMjesta.stream().filter(radnoMjesto -> radnoMjesto.getRadnik() != null)
                .collect(Collectors.toList());

        double plate = 0;

        for(RadnoMjesto rm : filtrirani){
            plate += osnovica * rm.getKoeficijent();
        }

        return plate;
    }

    public Set<Radnik> radnici() {
        Set<Radnik> radnici = new TreeSet<>();

        for(RadnoMjesto radnoMjesto : radnaMjesta){
            if(radnoMjesto.getRadnik() != null){
                radnici.add(radnoMjesto.getRadnik());
            }
        }

        return radnici;
    }

    public Map<RadnoMjesto, Integer> sistematizacija() {
        Map<RadnoMjesto, Integer> mapaRadnaMjesta = new HashMap<>();

        for(RadnoMjesto radnoMjesto : radnaMjesta){
            if(mapaRadnaMjesta.containsKey(radnoMjesto)){
                mapaRadnaMjesta.put(radnoMjesto, mapaRadnaMjesta.get(radnoMjesto) + 1);
            }
            else{
                mapaRadnaMjesta.put(radnoMjesto, 1);
            }
        }

        return mapaRadnaMjesta;
    }

    public List<Radnik> filterRadnici(Predicate<Radnik> funkcijaZaFiltriranje) {
        List<Radnik> radnici = new ArrayList<>();

        for(RadnoMjesto radnoMjesto : radnaMjesta){
            if(radnoMjesto.getRadnik() != null && funkcijaZaFiltriranje.test(radnoMjesto.getRadnik())){
                radnici.add(radnoMjesto.getRadnik());
            }
        }

        return radnici;
    }

    public List<Radnik> vecaProsjecnaPlata(double plata) {
        return filterRadnici((Radnik radnik) -> {
            return radnik.prosjecnaPlata() > plata;
        });
    }
}
