package com.example.lotto.backend;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerRead implements Runnable {
    Socket clientSocket;
    String name;
    Thread thread;
    Server server;

    ServerRead(Socket clientSocket, String name, Server server){
        this.clientSocket= clientSocket;
        this.name = name;
        thread = new Thread(this,name);
        thread.start();
        this.server = server;
    }


    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            while (true){
                Object o = in.readObject();
                if (o instanceof String ) {

                    String code = (String) o;

                    server.printDebug(code);


                    if(code == "requestLottoCode"){

                        String lottoCode = server.generateLottoCode();

                        String[] response = {"lottoCodeResponse", lottoCode};

                        server.sendMessage(response);

                    }

                }




            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
