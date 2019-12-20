/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplejavawebserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author thetkhine
 */
public class SimpleJavaWebServer
{

    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException
    {
        serverSocket = new ServerSocket(9090);  // Start, listen on port 80
        while (true)
        {
            try
            {
                Socket s = serverSocket.accept();  // Wait for a client to connect
                ClientProcessorThread thread = new ClientProcessorThread(s);  // Handle the client in a separate thread
                thread.start();
            } catch (Exception x)
            {
                System.out.println(x);
            }
        }
    }

}
