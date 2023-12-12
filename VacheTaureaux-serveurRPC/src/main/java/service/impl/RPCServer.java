package service.impl;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.webserver.WebServer;

import java.io.IOException;

public class RPCServer  {
    public static void main(String[] args) {
        try {
            WebServer webServer = new WebServer(8081);
            XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();

            PropertyHandlerMapping phm = new PropertyHandlerMapping();
            phm.addHandler("VTObjectServiceImpl", VTObjectServiceImpl.class);

            xmlRpcServer.setHandlerMapping(phm);

            String[] handlers = phm.getListMethods();
            System.out.println("Available Handlers:");
            for (String handler : handlers) {
                System.out.println(handler);
            }

            webServer.start();
            System.out.println("Server is running...");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlRpcException e) {
            throw new RuntimeException(e);
        }
    }
}