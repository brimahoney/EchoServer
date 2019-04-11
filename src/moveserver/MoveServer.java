package moveserver;

import java.net.*;
import java.io.*;
 
public class MoveServer 
{
    public static void main(String[] args) throws IOException 
    {
        if (args.length != 1) 
        {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }
         
        int portNumber = Integer.parseInt(args[0]);
        System.out.println("Starting server at port: " + portNumber); 
        try (
                ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
                Socket clientSocket = serverSocket.accept();     
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            )
        {
            String inputLine;
            System.err.println("Server ready!  Waiting for requests...");
            while ((inputLine = in.readLine()) != null) 
            {
                System.out.println("Client request recieved, sending back a move");
                out.writeObject(new ChessMove("White", "Queen", 3, 'e', 7, 'a'));
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
