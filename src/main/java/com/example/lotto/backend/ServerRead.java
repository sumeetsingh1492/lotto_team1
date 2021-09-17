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

                    try {
                        if(Integer.parseInt(code) == 0){

                            String lottoCode = server.generateLottoCode();

                            String[] response = {"0", lottoCode};

                            server.sendMessage(response);

                        }
                    } catch (NumberFormatException e) {
                    } catch (IOException e) {
                    }

                }

                if (o instanceof String[]) {
                    String[] code = (String[] ) o;

                    try {
                        if(Integer.parseInt(code[0]) == 1){

                            //database.store
                            server.printDebug("Value stored: "+code[1]);

                        }
                    } catch (NumberFormatException e) {

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
