package socket;

public class Biglietto {

    private int disponibilita;

    public Biglietto(int disponibilita) {
      
        this.disponibilita = disponibilita;
    }

    public int getDisponibilita() {
        return disponibilita;
    }

    public int setDisponibilita(int x){
        
        return disponibilita = x;
    }
}
