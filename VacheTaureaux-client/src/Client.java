import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String gatewayAddress = "localhost";
        int gatewayPort = 5001;

        try (Socket socket = new Socket(gatewayAddress, gatewayPort);
             OutputStream out = socket.getOutputStream();
             InputStream in = socket.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in));
             PrintWriter writer = new PrintWriter(out, true);
             Scanner scanner = new Scanner(System.in)
        ) {
            connectToGameServer(reader,writer);
            while (true) {
                showMenu();
                int userInput = scanner.nextInt();
                switch (userInput) {
                    case 1: {
                        writer.println("option1");
                        startGame(reader,writer);
                        break;
                    }
                    case 2:{
                        writer.println("option2");
                        String bestScore = reader.readLine();
                        System.out.println("best score is "+bestScore  );
                        break;
                    }
                    case 3:{
                        writer.println("option3");
                        socket.close();
                        System.out.println("Fin du programme");
                        System.exit(0);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void startGame(BufferedReader reader,PrintWriter writer) throws IOException {
        Scanner scanner = new Scanner(System.in);

        //initialisation du jeux
        System.out.println("donner un nom du joueur");
        String Joueur = scanner.nextLine();
        writer.println(Joueur);

        boolean gameEnded = false;
        int tentatives = 0;
        System.out.println("Deviner un nombre de quatre chiffres! , taper 'exit' pour terminer");

        while (!gameEnded) {
            tentatives++;
            String userInput = scanner.nextLine();

            if (Objects.equals(userInput, "exit")) {
                gameEnded = true;
                break;
            }

            if (userInput.length() != 4 || !userInput.matches("\\d+")) {
                System.out.println("veuiller entrer un nombre de quatre chiffres !");
                continue;
            }

            //envoie au serveur
            writer.println(userInput);

            // Recevoir la réponse du serveur et l'afficher
            String serverResponse = reader.readLine();
            System.out.println("réponse du serveur: " + serverResponse);

            if (Objects.equals(serverResponse, "4T 0V")){
                System.out.println("Bravo!!, apres "+tentatives+" tentatives");
                break;
            }
        }
        writer.println("exit");
    }

    static void connectToGameServer(BufferedReader reader,PrintWriter writer) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Connected to Gateway!. choose a server to continue");
        System.out.println("1. RMI Server");
        System.out.println("2. RPC Server");
        String userchoice = "";
        while (!Objects.equals(userchoice, "1") && !Objects.equals(userchoice, "2")) {
            userchoice = scanner.nextLine();
        }
        writer.println(userchoice);
        // Recevoir la configuration du connection
        String serverResponse = reader.readLine();
        System.out.println(serverResponse);
    }

    static void showMenu(){
        System.out.println("*** Jeu de Vache - Taureaux ***");
        System.out.println("• Option 1 : Jouer Vache-Taureau");
        System.out.println("• Option 2 : Connaître le meilleur score.");
        System.out.println("• Option 3 : Quitter.");
    }

}