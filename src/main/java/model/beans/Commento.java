package model.beans;

import java.util.Date;

public class Commento {

    /* Attributes */

    private java.sql.Date data;
    private String testo;
    private String utente;


    /* Constructor */

    public Commento() {

    }

    public Commento(String testo, String utente, java.sql.Date data) {
        this.testo = testo;
        this.utente = utente;
        this.data = data;
    }

    /* Getter & Setter */

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getUtente() {
        return utente;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }

    public java.sql.Date getData() {
        return data;
    }

    public void setData(java.sql.Date data) {
        this.data = data;
    }
}
