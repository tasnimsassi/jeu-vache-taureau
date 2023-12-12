package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VTObjectService extends Remote {
    public void initializeGame(String playerName) throws RemoteException;

    public String validate(String userInput, String playerName, int tentatives) throws RemoteException;

    public int getBestScore() throws RemoteException;
}
