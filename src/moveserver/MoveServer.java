package moveserver;

import java.net.*;
import java.io.*;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
 
public class MoveServer 
{
    int portNumber;
    ScheduledExecutorService moveSendExecutor;
    ServerSocket serverSocket;
    Socket clientSocket;
    ObjectOutputStream out;
    BufferedReader in;
    Random random = new Random();
    
    public MoveServer(int port)
    {
        portNumber = port;
        System.out.println("Starting server at port: " + portNumber); 
        try
        {
            serverSocket = new ServerSocket(portNumber);
            System.out.println("Ready to start communicating at port: " + portNumber); 
            
            clientSocket = serverSocket.accept();     
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            moveSendExecutor = Executors.newScheduledThreadPool(2);
        }
        catch (IOException e) 
        {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
    
    public void startCommunication()
    {
        System.out.println("Server ready!  Waiting for requests...");
        
        try
        {
            String inputLine;
            if ((inputLine = in.readLine()) != null) 
            {
                System.out.println("Read data: " + inputLine);
                System.out.println("Client request recieved, starting move sending");
                moveSendExecutor.scheduleWithFixedDelay(getMoveSendTask(), 5000l, 5000l, TimeUnit.MILLISECONDS);
            }
        } 
        catch (IOException ioe) 
        {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(ioe.getMessage());
        }        
    }

    private Runnable getMoveSendTask()
    {
        Runnable task = new Runnable()
        {
            @Override
            public void run() 
            {
                char[] files = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
                char fromFile = files[random.nextInt(8)];
                char toFile = files[random.nextInt(8)];
                String color = random.nextBoolean() ? "White" : "Black";
                ChessMove move = new ChessMove(color, "Queen", random.nextInt(8), 'e', random.nextInt(8), 'a');
                System.out.println("sending back a move");
                try
                {
                    out.writeObject(move);   
                }
                catch(IOException ioe)
                {
                    System.out.println("Exception caught when trying to send move");
                    System.out.println(ioe.getMessage());
                }
            }
        };
        return task;
    }
    
    public static void main(String[] args)
    {
        if (args.length != 1) 
        {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }
            
        MoveServer server = new MoveServer(Integer.parseInt(args[0]));
        server.startCommunication();
    }
}
