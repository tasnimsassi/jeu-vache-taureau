package service.impl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class serveurRMI {

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        LocateRegistry.createRegistry(5005);
        VTObjectServiceImpl distanceObject = new VTObjectServiceImpl();
        Naming.rebind("rmi://localhost:5005/VacheTaureauxGameRMI", distanceObject);
        System.out.println("rmi server is running! registry on port 5005");
    }

}
