package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler extends Thread {

    private final Socket socket;
    public static List<Biglietto> biglietti;
    private String username;
    private Biglietto gold;
    private Biglietto pit;
    private Biglietto parterre;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket, List<Biglietto> biglietti){
                
        this.socket = socket;
        biglietti = new ArrayList<>();
   }

    @Override
    public void run() {
        try {
            gold.setDisponibilita(10);
            pit.setDisponibilita(30);
            parterre.setDisponibilita(60);
            
            biglietti.add(gold);
            biglietti.add(pit);
            biglietti.add(parterre);

            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
        
            this.out.println("WELCOME");

            boolean loggedIn = false;

            while(!loggedIn){
 
                String line = this.in.readLine(); // LOGIN <username>

                if (line == null){
                
                    break;
                }

                if (line.startsWith("LOGIN ")){

                    this.username = line.substring(6).trim();

                    if (!this.username.isEmpty()) {

                        loggedIn = true;
                        this.out.println("OK");
                    }
                    else {

                        this.out.println("ERR ");
                    }
                } 
                else{

                    this.out.println("ERR");
                }
            }

            String command;
            while ((command = in.readLine()) != null) {

                switch (command) {
                    case "N":
                        this.out.println("AVAIL" + "Gold " + biglietti);
                        
                        break;
                
                    default: this.out.println("Comando non valido");
                        break;
                }
            }
        } 
        catch (IOException e){

            e.printStackTrace();
        }

    }
}
