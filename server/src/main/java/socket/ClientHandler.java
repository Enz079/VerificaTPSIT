package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler extends Thread {

    private final Socket socket;
    Map<String,Integer> disponibilita; 
    private String username;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket, Map<String,Integer> disponibilita){
                
        this.socket = socket;
        
        disponibilita = new HashMap<>();

        disponibilita.put("Gold", 10);
        disponibilita.put("Pit", 30);
        disponibilita.put("Parterre", 60);
   }

    @Override
    public void run() {
        try {
            
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

                        this.out.println("ERR LOGINREQUIRED");
                    }
                } 
                else{

                    this.out.println("ERR LOGINREQUIRED");
                }
            }

            String command;
            while ((command = in.readLine()) != null) {

                switch (command) {
                    case "N":
                        this.out.print("AVAIL");
                        for (Map.Entry<String, Integer> x : disponibilita.entrySet()) {
                            this.out.println(x.getKey() + ": " + x.getValue());
                        }
                        break;
                
                    case "BUY":

                        String biglietto = in.readLine().toLowerCase();
                        int qta;

                        if (biglietto.startsWith("gold")) {
                            
                            qta = Integer.parseInt(biglietto.substring(5).trim());

                            Integer valore = disponibilita.get("gold");
                            int nuovoValore = valore - qta;
                            disponibilita.put("Gold", nuovoValore);
                        }
                        else if(biglietto.startsWith("pit")) {
                            
                            qta = Integer.parseInt(biglietto.substring(4).trim());
                            Integer valore = disponibilita.get("pit");
                            int nuovoValore = valore - qta;
                            disponibilita.put("Pit", nuovoValore);
                        }
                        else{
                            
                            qta = Integer.parseInt(biglietto.substring(9).trim());
                            Integer valore = disponibilita.get("Parterre");
                            int nuovoValore = valore - qta;
                            disponibilita.put("Parterre", nuovoValore);
                        }
                        break;

                    case "QUIT":

                        this.out.println("BYE");
                        socket.close();

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
