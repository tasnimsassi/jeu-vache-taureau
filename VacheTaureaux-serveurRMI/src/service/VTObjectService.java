package service;

import java.lang.reflect.Array;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface VTObjectService extends Remote {
    public void initializeGame(String playerName) throws RemoteException;

    public String validate(String userInput, String playerName, int tentatives) throws RemoteException;

    public int getBestScore() throws RemoteException;
}
