package service;

import java.io.*;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.Objects;

public class ThreadRMI implements Runnable {
    private Socket socket;
    private int clientNumber;
    private String serverAddress = "localhost";
    private int rmiPortNumber = 5005; // Mettez le port RMI approprié ici

    public ThreadRMI(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
    }

    public void run() {
        try (
                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                PrintWriter writer = new PrintWriter(output, true)
        ) {
            writer.println("Connected to RMI game server!");
            // Connexion au serveur RMI
            VTObjectService stub = (VTObjectService) Naming.lookup("rmi://localhost:5005/VacheTaureauxGameRMI");


            while (true){
                String userOption = reader.readLine();
                switch (userOption){
                    case "option1":{
                        //initialisation
                        String joueur =  reader.readLine();
                        stub.initializeGame(joueur);

                        String clientMessage;

                        // Lire les données envoyées par le client
                        int tentatives = 0;
                        while (!Objects.equals(clientMessage = reader.readLine(), "exit")) {
                            tentatives++;
                            String response = stub.validate(clientMessage,joueur,tentatives);
                            writer.println(response);
                        }
                        break;
                    }
                    case "option2":{
                        int bestScore = stub.getBestScore();

                        System.out.println("best score is, "+bestScore);
                        writer.println(bestScore);
                        break;
                    }
                    case "option3":{
                        System.out.println("Client " + clientNumber + " disconnected.");
                        socket.close();
                        break;
                    }
                }
            }


        } catch (IOException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
