package com.example.lotto.client;

import com.example.lotto.LottoApplication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable{
    int portNumber = 12345;
    Thread t;
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    LottoApplication mainApp;
    String address;


    public Client(LottoApplication mainApp, String address){

        this.mainApp = mainApp;
        t = new Thread(this,"threadC");
        t.start();
        this.address = address;
    }

    @Override
    public void run() {
        while (true) {
            try {
                clientSocket = new Socket(address, portNumber);

                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream((clientSocket.getInputStream()));

                while (true) {
                    Object o =  in.readObject();

                    //checks if response is String
                    if (o instanceof String) {
                        String code = (String ) o;

                    }

                    //checks if response is Array of String
                    if (o instanceof String[]) {
                        String[] code = (String[] ) o;

                        //checks if it's the lotto code
                        try {
                            if(Integer.parseInt(code[0]) == 0){

                                mainApp.setLottoCode(code[1]);

                           }
                        } catch (NumberFormatException e) {
                        }

                    }
                }

            } catch (UnknownHostException e) {
                System.err.println("Host Unknown");
            } catch (IOException e) {
                System.err.println("I/O");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //used to sen message to the server
    public synchronized void sendMessage(Object code){
        try {

            out.writeObject(code);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


/**response
 *  code 0 = lotteryCodeResponse
 *
 *
 *
 * request
 *
 * code 0 = lotteryCodeRequest
 *
 * */

