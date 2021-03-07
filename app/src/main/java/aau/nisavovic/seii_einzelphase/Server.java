package aau.nisavovic.seii_einzelphase;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Server extends Thread {

    private String matrikelnummer;
    private String result;
    private String server;
    private int portNumber;

    public Server(String matrikelnummer) {
        this.matrikelnummer = matrikelnummer;
        this.result = "";
        this.server = "se2-isys.aau.at";
        this.portNumber = 53212;
    }

    public void run() {
        try {
            Socket socket = new Socket(this.server, this.portNumber);

            // used to send data to the server
            DataOutputStream channelToServer = new DataOutputStream(socket.getOutputStream());

            // used to read the answer from the server
            BufferedReader answerFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // actually send the Matrikelnummer to the server
            // Add \n to matrikelnummer when sending to server so server accepts the message.
            channelToServer.writeBytes(this.matrikelnummer + '\n');

            // read the answer from the server and save it in our result variable
            this.result = answerFromServer.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMatrikelnummer() {
        return matrikelnummer;
    }

    public void setMatrikelnummer(String matrikelnummer) {
        this.matrikelnummer = matrikelnummer;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
