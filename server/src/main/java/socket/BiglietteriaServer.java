package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BiglietteriaServer {

    public static final int port = 3000;
    public static List<Integer> biglietti = new ArrayList<>();

    public static Map<String,Integer> disponibilita = new HashMap<>();
    
        
            public static void main(String[] args) throws IOException{
        
                System.out.println("Server in ascolto sulla porta " + port);
        
                try(ServerSocket serverSocket = new ServerSocket(port)){
        
                    while (true){
        
                        Socket clientSocket = serverSocket.accept();
                        System.out.println("Nuovo client connesso: " + clientSocket.getInetAddress());
                        new ClientHandler(clientSocket, biglietti, disponibilita).start();
            }
        } 
        catch (IOException e){

            e.printStackTrace();
        }
    }
    
}