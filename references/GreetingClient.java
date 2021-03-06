/*
 * GreetingClient.java
 * CMSC137 Sample Code for TCP Socket Programming
 */

import java.net.*;
import java.io.*;
import java.util.Scanner;


public class GreetingClient{
    public static void main(String [] args){
        try{
            String serverName = args[0]; //get IP address of server from first param
            int port = Integer.parseInt(args[1]); //get port from second param
            // String message = args[2]; //get message from the third param

            /* Open a ClientSocket and connect to ServerSocket */
            System.out.println("Connecting to " + serverName + " on port " + port);
            
			//creating a new socket for client and binding it to a port
            Socket server = new Socket(serverName, port);
            listenToServer(server);
                
            
            while(true) {
                System.out.println("CHOOSE:\n[0] HOST \n[1] CLIENT");
                Scanner scanner = new Scanner(System.in);
                System.out.println("enter option");
                int opt = Integer.parseInt(scanner.nextLine());

                switch(opt){
                    case 0: //HOST
                        //Automatically send a CREATE_LOBBY PACKET and CONNECT_PACKET

                        sendPacket(); //modufy na lang, like yung parameters

                        System.out.println("[0] CHAT\n");
                        System.out.println("enter option");
                        int opt1 = Integer.parseInt(scanner.nextLine());
                        if(opt1==2){ 
                            sendPacket();
                            System.out.println("entered chat\n");
                        }

                        break;
                    case 1: //CLIENT
                        System.out.println("CHOOSE:\n[0] CONNECT\n");
                        System.out.println("enter option");
                        int opt2 = Integer.parseInt(scanner.nextLine());

                        if(opt2==0){
                            sendPacket();
                            System.out.println("CONNECTED TO A LOOBY\n");

                            System.out.println("CHOOSE:\n[0] DISCONNECT\n[2] CHAT\n");
                            System.out.println("enter option");
                            int opt3 = Integer.parseInt(scanner.nextLine());

                            if(opt3==0){
                                sendPacket();
                                System.out.println("DISCONNECTED\n");
                            }

                            if(opt3==1){
                                sendPacket();
                                System.out.println("entered chat\n");
                            }
                        }

                        break;
                    default:
                        break;
                }

           }
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Cannot find (or disconnected from) Server");
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Usage: java GreetingClient <server ip> <port no.> '<your message to the server>'");
        }

    }

    // public void send(String message) {
    //     
    // }   

    public static void sendPacket (){
            
        System.out.println("PACKET SENT TO SERVER\n");

        // SEND SOMETHING TO SERVER
        // Scanner scanner = new Scanner(System.in);
        // String message = scanner.nextLine();

        // OutputStream outToServer = server.getOutputStream();
        // DataOutputStream out = new DataOutputStream(outToServer);
        // out.writeUTF("Client " + server.getLocalSocketAddress()+" says: " +message);
    }

    public static void listenToServer(Socket server) {
        Thread thread = new Thread(){
            public void run(){
                while(true) {
                    try{
                        InputStream inFromServer = server.getInputStream();
                        DataInputStream in = new DataInputStream(inFromServer);
                        System.out.println("Server says " + in.readUTF());
                    } catch(SocketTimeoutException s){
                        System.out.println("Socket timed out!");
                    } catch(IOException e){
                        e.printStackTrace();
                        System.out.println("Input/Output Error!");
                    }
                }        
            }
        };

        thread.start();
        
              
            
    }

}
