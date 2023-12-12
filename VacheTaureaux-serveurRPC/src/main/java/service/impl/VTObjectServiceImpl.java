package service.impl;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcHandler;
import org.apache.xmlrpc.XmlRpcRequest;
import service.PlayerRecord;
import service.VTObjectService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class VTObjectServiceImpl implements VTObjectService {

    private static String randomNumber;
    private static HashMap<String, Integer> scores = new HashMap<>();

    @Override
    public int initializeGame(String playerName) {
        int generated = new Random().nextInt (10000);
        randomNumber = String.format ("%04d", generated);
        System.out.println("generated: "+randomNumber+" for player "+playerName);
        return 1;
    }

    @Override
    public String validate(String userInput, String playerName, int tentatives) {
        if (randomNumber == null) {
            // Handle the case when the game is not initialized
            return "Game not initialized yet";
        }
        int bulls = 0;
        int cows = 0;

        for (int i = 0; i < 4; i++) {
            char userDigit = userInput.charAt(i);

            char randomDigit = randomNumber.charAt(i);

            if (userDigit == randomDigit) {
                bulls++;
            } else if (randomNumber.contains(String.valueOf(userDigit))) {
                cows++;
            }
        }

        String result = bulls + "T " + cows + "V";
        if (bulls == 4) {
            scores.put(playerName,tentatives);
        }
        return result;
    }

    @Override
    public int getBestScore() throws RemoteException {
        int maxScore = Integer.MAX_VALUE; // Initialisation avec une valeur maximale

        for (Integer score : scores.values()) {
            maxScore = Math.min(maxScore, score); // Trouver le score minimum dans la liste
        }

        return maxScore;
    }
}
