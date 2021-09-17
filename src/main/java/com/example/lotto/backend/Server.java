package com.example.lotto.backend;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

public class Server{

    private ServerSocket serverSocket ;
    int portNumber;

    private Socket playerOne;


    private ArrayList<String> listCodes = new ArrayList<>();


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
    public String generateLottoCode() throws IOException {

        /*Random rand = new Random(); //instance of random class
        int int_random = rand.nextInt(34);*/

        URL url = new URL("http://www.randomnumberapi.com/api/v1.0/random?min=1&max=59&count=1");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        String response = "null";

        if (conn.getResponseCode() == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String strCurrentLine = "";
            int i = 0;
            while ((strCurrentLine = br.readLine()) != null) {
                response = strCurrentLine;
            }
        }

        conn.disconnect();



        return (response.replace("[","")).replace("]","" );
    }

    public void printDebug(String code){

        System.out.println(code);

    }

    public synchronized void addToCodesList(String value){

        listCodes.add(value);

    }

    public synchronized String getCodesList(){

        String god_list = "";

        for (String itm: listCodes) {

            god_list += (itm + "|");

        }

        return god_list;

    }

    public synchronized void resetCodesList(){

        listCodes.clear();

    }



    public static void main(String[] args){
        new Server();
    }





}
