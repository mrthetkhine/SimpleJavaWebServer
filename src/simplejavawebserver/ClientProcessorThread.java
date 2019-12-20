/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simplejavawebserver;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 *
 * @author Lenovo
 */
public class ClientProcessorThread extends Thread{

    Socket client;
    InputStream in;
    OutputStream out;

    String statusOk = "HTTP/1.1 200 OK\r\n";
    String contentType = "content-type:text/html\r\n";

    DataOutputStream dout ;
    DataInputStream din ;

    String httpMethod;
    String requestedResourceUrl;
    String httpVersion ;

    ClientProcessorThread(Socket clientSocket)
    {
        this.client = clientSocket;

        try
        {
            din = new DataInputStream( client.getInputStream());
            dout =new DataOutputStream( client.getOutputStream());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        this.readHttpHeader();
    }

    public void readHttpHeader()
    {
        try
        {
            din = new DataInputStream( client.getInputStream());
            String firstLine = din.readLine();
            String[] requestLine= firstLine.split(" ");

            httpMethod = requestLine[0];
            requestedResourceUrl = requestLine[1];
            httpVersion = requestLine[2];

            System.out.println("Resource "+ requestedResourceUrl );
            System.out.println( firstLine );
            //din.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    byte[] getResource(String resource) throws IOException
    {
        String pathStr = "";
        if(resource.equals("/"))
        {
            pathStr = "/index.html";
        }
        else
        {
            pathStr = resource;
        }
        Path path = Paths.get( Config.serverRoot + pathStr);
        byte[] data = Files.readAllBytes(path);
        return data;
    }

    public String getContentType()
    {
        String type ;
        if(requestedResourceUrl.equals("/"))
        {
            type = "content-type:text/html\r\n";
        }
        else
        {
             int indexOfDot = requestedResourceUrl.indexOf(".");
             String extension = requestedResourceUrl.substring( indexOfDot+1);
             type = "content-type:" + ContentTypeMapping.getConentTypeMapper().getContentType(extension) +"\r\n";
        }
        return type;
    }
    @Override
    public void run()
    {
        byte[] requestedResource =new byte[1] ;
        try
        {
            requestedResource = this.getResource(requestedResourceUrl);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dout.close();
                din.close();
                this.client.close();
            }
            catch(Exception ee)
            {
            }
            return;
        }

        try
        {
            dout.write( statusOk.getBytes());
            dout.write( getContentType().getBytes() );
            dout.write("\r\n".getBytes());

            dout.write( requestedResource );
            dout.write("\r\n".getBytes());

            dout.flush();
            
            dout.close();
            din.close();
            this.client.close();
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dout.close();
                din.close();
                this.client.close();
            }
            catch(Exception ee)
            {
            }

        }
        
    }

}
