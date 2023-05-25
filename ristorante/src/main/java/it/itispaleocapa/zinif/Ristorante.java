package it.itispaleocapa.zinif;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.time.LocalDate;

public class Ristorante {
    private HashMap<Integer, Prenotazione> prenotazioni;
    private HashMap<String, Cliente>clienti;
    private Cliente bestCliente;
    private int coperti,prenotazioniTotali,maxP,maxC;
    private LocalDate giornoMigliore;
    Ristorante(int coperti){
        prenotazioni = new HashMap<>();
        clienti = new HashMap<>();
        this.coperti = coperti;
        prenotazioniTotali = 0;
    }
    @Override
    public String toString() {
        String message = "Ristorante da Zini, prenotazioni:\n";
        for (Prenotazione p: prenotazioni.values()) {
            message+=p.toString()+"\n\n";
        }
        return message;
    }
    //aggiungi cliente------------------------
    public Ristorante addCliente(String nome){
        Cliente c = new Cliente(nome);
        clienti.put(nome, c);
        return this;
    }
    public Ristorante addCliente(Cliente c){
        clienti.put(c.getNome(), c);
        return this;
    }
    //----------------------------------------

    //modifica cliente-----------------------------------------
    public void modifyCliente(Cliente c, String nome){
        c.setNome(nome);
    }
    public void modifyCliente(String nome, String nuovoNome){
        clienti.get(nome).setNome(nuovoNome);
    }
    //---------------------------------------------------------

    //elimina cliente----------------------
    public void deleteCliente(Cliente c){
        clienti.remove(c.getNome(), c);
    }
    public void deleteCliente(String nome){
        Cliente c = clienti.get(nome);
        clienti.remove(nome, c);
    }
    //-------------------------------------

    //aggiungi prenotazione/ prenota-----------------------------------------------------------------
    public Ristorante addPrenotazione(Prenotazione p){
        prenotazioni.put(prenotazioniTotali, p);
        prenotazioniTotali++;
        return this;
    }
    public Ristorante addPrenotazione(Cliente c, LocalDate quando, LocalDate perQuando, int coperti){
        Prenotazione p = new Prenotazione(c, quando, perQuando, coperti, prenotazioniTotali);
        prenotazioni.put(prenotazioniTotali, p);
        prenotazioniTotali++;
        c.setCopertiPrenotati(coperti);
        c.setPrenotazioniEffettuate();
        return this;
    }
    //-----------------------------------------------------------------------------------------------

    //modifica prenotazione-------------------------------------------------------------------------------------------
    public void modifyPrenotazione(Prenotazione p, Cliente c, LocalDate quando, LocalDate perQuando, int coperti){
        p.setCliente(c);
        p.setQuando(perQuando);
        p.setPerQuando(perQuando);
        p.setQuantiCoperti(coperti);
    }
    public void modifyPrenotazione(int idPrenotazione, Cliente c, LocalDate quando, LocalDate perQuando, int coperti){
        Prenotazione p = prenotazioni.get(idPrenotazione);
        p.setCliente(c);
        p.setQuando(perQuando);
        p.setPerQuando(perQuando);
        p.setQuantiCoperti(coperti);
    }
    //----------------------------------------------------------------------------------------------------------------

    //elimina prenotazione-------------------------------
    public void deletePrenotazione(Prenotazione p){
        prenotazioni.remove(p.getId(), p);
        prenotazioniTotali--;
    }
    public void deletePrenotazione(int idPrenotazione){
        Prenotazione p = prenotazioni.get(idPrenotazione);
        prenotazioni.remove(idPrenotazione, p);
        prenotazioniTotali--;
    }
    //----------------------------------------------------

    //ricerca cliente--------------------------
    public Cliente ricercaCliente(String nome){
        return clienti.get(nome);
    }
    //-----------------------------------------
    //ricerca prenotazioni per cliente----------------------------------------
    public LinkedList<Prenotazione> searchPrenotazioniBasedOn(Cliente c){
        LinkedList<Prenotazione> ps = new LinkedList<>();
        prenotazioni.values().stream()
                    .filter(x -> x.getCliente().equals(c))
                    .forEach(ps::add);
        return ps;
    }
    //------------------------------------------------------------------------

    //ricerca prenotazioni per data------------------------------------------
    public LinkedList<Prenotazione> searchPrenotazioniQuando(LocalDate d){
        LinkedList<Prenotazione> ps = new LinkedList<>();
        prenotazioni.values().stream()
                    .filter(x -> x.getQuando().equals(d))
                    .forEach(ps::add);
        return ps;
    }
    public LinkedList<Prenotazione> searchPrenotazioniPerQuando(LocalDate d){
        LinkedList<Prenotazione> ps = new LinkedList<>();
        prenotazioni.values().stream()
                    .filter(x -> x.getPerQuando().equals(d))
                    .forEach(ps::add);
        return ps;
    }
    //-----------------------------------------------------------------------

    //ricerca coperti per data----------------------------------------------
    public int searchCopertiBasedOn(LocalDate d){
        LinkedList<Integer> inters = new LinkedList<>();
        prenotazioni.values().stream()
                    .filter(x -> x.getQuando().equals(d))
                    .forEach(x -> inters.add(x.getQuantiCoperti()));
        int copertiTot = inters.stream().mapToInt(Integer::intValue).sum();
        return copertiTot;
    }
    //---------------------------------------------------------------------
    
    //ricerca coperti in range-------------------------------------------------------------
    public int searchCopertiInRange(LocalDate from, LocalDate to){
        LinkedList<Integer> inters = new LinkedList<>();
        prenotazioni.values().stream()
                    .filter(x -> x.getQuando().isAfter(from) && x.getQuando().isBefore(to))
                    .forEach(x -> inters.add(x.getQuantiCoperti()));
        int copertiTot = inters.stream().mapToInt(Integer::intValue).sum();
        return copertiTot;
    }
    //-------------------------------------------------------------------------------------

    //giorno con maggior numero di coperti-------------------------
    public LocalDate searchMaxCoperti(){
        maxP = prenotazioni.get(0).getQuantiCoperti();
        prenotazioni.values().stream()
                             .forEach(x -> {
                                if (x.getQuantiCoperti()>maxP) {
                                    maxP = x.getQuantiCoperti();
                                    giornoMigliore = x.getQuando();        
                                }
                             }
                             );
                             
        return giornoMigliore;
    }
    //-------------------------------------------------------------
    
    //cliente con il maggior numero di coperti prenotati-------
    public Cliente searchMaxPrenotati(){
        maxC = 0;
        clienti.values().stream()
                        .forEach(x ->{
                            if(x.getCopertiPrenotati()>maxC){
                                maxC = x.getCopertiPrenotati();
                                bestCliente = x;
                            }
                        });
        return bestCliente;
    }
    //---------------------------------------------------------

    //elenco di cliente ordinati per numero di prenotazioni--------
    public LinkedList<Cliente> clientiList(){
        LinkedList<Cliente> c = new LinkedList<>(clienti.values());
        Collections.sort(c);
        return c;
    }
    //-------------------------------------------------------------
}
