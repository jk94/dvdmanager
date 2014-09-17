package de.codekings.common.Connection;


import de.codekings.common.log.LogInitialiser;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jan
 */
public class Hasher {
    
    private static final Logger log = Logger.getLogger(Hasher.class.getSimpleName());
    private static final Hasher thishasher = new Hasher();

    private Hasher() {
        LogInitialiser.initialiseLog(log, "Hasher");
    }
    
    public static Hasher getInstance(){
        return thishasher;
    }

    public String ToMD5(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("UTF-8"));

            //converting byte array to Hexadecimal String
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }

            digest = sb.toString();

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            log.log(Level.SEVERE, ex.getMessage());
        }
        return digest;
    }

}
