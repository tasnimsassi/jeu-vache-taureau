package service;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Objects;

public class ThreadRPC implements Runnable {
    private Socket socket;
    private int clientNumber;
    private XmlRpcClient rpcClient;

    public ThreadRPC(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;

        // Initialize the XML-RPC client
        initializeXmlRpcClient();
    }

    private void initializeXmlRpcClient() {
        try {
            XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
            config.setServerURL(new URL("http://localhost:8081/xmlrpc"));
            rpcClient = new XmlRpcClient();
            rpcClient.setConfig(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try (
                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                PrintWriter writer = new PrintWriter(output, true)
        ) {
            writer.println("Connected to RPC game server!");
            while (true) {
                String userOption = reader.readLine();
                switch (userOption) {
                    case "option1": {
                        String joueur = reader.readLine();
                        rpcClient.execute("VTObjectServiceImpl.initializeGame", new Object[]{joueur});

                        String clientMessage;
                        int tentatives=0;
                        while (!Objects.equals(clientMessage = reader.readLine(), "exit")) {
                            tentatives++;
                            String response = (String) rpcClient.execute("VTObjectServiceImpl.validate", new Object[]{clientMessage,joueur,tentatives});
                            writer.println(response);
                        }
                        break;
                    }
                    case "option2": {
                        int response = (int) rpcClient.execute("VTObjectServiceImpl.getBestScore", new Object[]{});
                        System.out.println("fetched: "+response);
                        writer.println(response);
                        break;
                    }
                    case "option3": {
                        System.out.println("Client " + clientNumber + " disconnected.");
                        socket.close();
                        break;
                    }
                }
            }
        } catch (IOException | XmlRpcException e) {
            throw new RuntimeException(e);
        }
    }
}