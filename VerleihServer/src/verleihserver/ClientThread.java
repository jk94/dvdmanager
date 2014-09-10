/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verleihserver;

import Enumerators.LogEnum;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Jan
 */
class ClientThread extends Thread {

    private BufferedReader reader;
    private PrintWriter writer;
    private Socket s;

    public ClientThread(Socket s) {
        this.s = s;
        try {
            this.reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            this.writer = new PrintWriter(s.getOutputStream());
        } catch (IOException ex) {
            main.log(LogEnum.ERROR, ex.getMessage(), this);
        }
    }

    @Override
    public void run() {
        try {
            String s = "";
            while ((s = reader.readLine()) != null) {
                //TODO Auslesen!
                if(this.s.isConnected()){
                main.log(LogEnum.DIV, this.s.getInetAddress().toString() + " wrote: " + s, this);
                if(s.equalsIgnoreCase("exit")){
                    this.s.close();
                }
                }else{
                    main.log(LogEnum.ERROR, "User " + this.s.getInetAddress() + " disconnected!", this);
                    break;
                }
            }
        } catch (IOException ex) {
            main.log(LogEnum.ERROR, "User " + s.getInetAddress() + " disconnected!", this);
        }
    }

    public void write(String s) {
        writer.append(s + "\n").flush();
    }

}
