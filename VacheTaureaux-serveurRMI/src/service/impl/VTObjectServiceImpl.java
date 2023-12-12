package service.impl;

import service.VTObjectService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Random;

public class VTObjectServiceImpl extends UnicastRemoteObject implements VTObjectService {

    private String randomNumber;
    HashMap <String, Integer> scores = new HashMap<>();

    public VTObjectServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public void initializeGame(String playerName) throws RemoteException {
        int generated = new Random().nextInt (10000);
        this.randomNumber = String.format ("%04d", generated);
        System.out.println("generated: "+randomNumber+" for player "+playerName);
        //todo save new player
    }

    @Override
    public String validate(String userInput, String playerName, int tentatives) throws RemoteException {
        int bulls = 0;
        int cows = 0;

        for (int i = 0; i < 4; i++) {
            char userDigit = userInput.charAt(i);
            char randomDigit = this.randomNumber.charAt(i);

            if (userDigit == randomDigit) {
                bulls++;
            } else if (this.randomNumber.contains(String.valueOf(userDigit))) {
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
