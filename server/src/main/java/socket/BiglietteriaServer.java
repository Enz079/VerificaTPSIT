package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class BiglietteriaServer {

    public static final int port = 3000;
    public static List<Biglietto> biglietti = new ArrayList<>();
    
        public static void main(String[] args) throws IOException{
    
            System.out.println("Server in ascolto sulla porta " + port);
    
            try(ServerSocket serverSocket = new ServerSocket(port)){
    
                while (true){
    
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Nuovo client connesso: " + clientSocket.getInetAddress());
                    new ClientHandler(clientSocket, biglietti).start();
            }
        } 
        catch (IOException e){

            e.printStackTrace();
        }
    }
    
}