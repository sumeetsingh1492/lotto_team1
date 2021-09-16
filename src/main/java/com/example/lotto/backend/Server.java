package com.example.lotto.backend;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class Server{

    private ServerSocket serverSocket ;
    int portNumber;

    private Socket playerOne;


    public ObjectOutputStream outOne, outTwo;

    public Server(){


        portNumber=12345;

        try{

            serverSocket = new ServerSocket(portNumber);

            playerOne = serverSocket.accept();
            new ServerRead(playerOne,"one", this);
            outOne = new ObjectOutputStream(playerOne.getOutputStream());
            sendMessage("Connected");


        } catch (UnknownHostException e) {
            System.err.println("Host Unknown" );
        }catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    //sends message to client
    public void sendMessage(Object code) throws IOException{

        outOne.writeObject(code);

    }


    //generate lotto code
    public String generateLottoCode() {

        Random rand = new Random(); //instance of random class
        int int_random = rand.nextInt(34);

        return String.valueOf(int_random);
    }

    public void printDebug(String code){

        System.out.println(code);

    }

    public static void main(String[] args){
        new Server();
    }





}
