package it.itispaleocapa.zinif;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.LinkedList;

import org.junit.Test;


public class TestRistorante {
    @Test
    public void ricercaPPerClienteTest(){
        Ristorante r = new Ristorante(100);
        Cliente c = new Cliente("mario");
        Cliente c1 = new Cliente("simone");
        r.addCliente(c).addCliente(c1);
        LocalDate q = LocalDate.now();
        LocalDate dq = LocalDate.of(2023, 6, 7);
        r.addPrenotazione(c, q, dq, 100);
        LinkedList<Prenotazione> p = r.searchPrenotazioniBasedOn(r.ricercaCliente("mario"));
        assertEquals(100, p.get(0).getQuantiCoperti());
        System.out.println(r.toString());
    }
}
