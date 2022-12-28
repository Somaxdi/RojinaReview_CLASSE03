package rojinaReview.model.beans;

import rojinaReview.model.utilities.Articolo;

import java.util.ArrayList;

public class Recensione extends Articolo {
    static public String[] fieldsName = {"Id","Testo","Titolo","Voto","Data Caricamento"};

    /* Attributes */

    public float voto;

    /* Constructor */

    public Recensione() {
    }

    public Recensione(ArrayList<Commento> commenti,
                      String immagine,
                      java.sql.Date dataCaricamento,
                      float voto,
                      Videogioco gioco,
                      int id,
                      String testo,
                      String giornalista,
                      String titolo,
                      String immagineGiornalista) {

        super(  commenti,
                immagine,
                dataCaricamento,
                gioco,
                id,
                testo,
                titolo,
                giornalista,
                immagineGiornalista);
        this.voto = voto;
    }

    /* Getter and Setter */

    public float getVoto() {
        return voto;
    }

    public void setVoto(float voto) {
        this.voto = voto;
    }
}
