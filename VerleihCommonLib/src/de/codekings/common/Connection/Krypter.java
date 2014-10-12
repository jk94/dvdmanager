/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.common.Connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;

/**
 *
 * @author Jan
 */
public class Krypter {

    private KeyPair keys;
    private PublicKey foreignPubkey = null;

    public Krypter() {

    }

    public void generateKeyPair() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            keys = kpg.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
        }
    }

    public void saveKeyPair() {
        if (keys != null) {
            File configOrdner = new File("config");
            if (configOrdner.exists()) {
                if (configOrdner.isDirectory()) {
                    File publicKey = new File("config/public.key");
                    File privateKey = new File("config/private.key");
                    try {
                        ObjectOutputStream oospublic = new ObjectOutputStream(new FileOutputStream(publicKey));
                        ObjectOutputStream oosprivate = new ObjectOutputStream(new FileOutputStream(privateKey));

                        oospublic.writeObject(keys.getPublic());
                        oosprivate.writeObject(keys.getPrivate());

                        oosprivate.close();
                        oospublic.close();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } else {
                configOrdner.mkdir();
                saveKeyPair();
            }
        }
    }

    public boolean loadKeyPair() {
        File configOrdner = new File("config");
        if (configOrdner.exists()) {
            if (configOrdner.isDirectory()) {
                File publicKey = new File("config/public.key");
                File privateKey = new File("config/private.key");
                try {
                    ObjectInputStream inputPublic = new ObjectInputStream(new FileInputStream(publicKey));
                    ObjectInputStream inputPrivate = new ObjectInputStream(new FileInputStream(privateKey));

                    PublicKey pk = (PublicKey) inputPublic.readObject();
                    PrivateKey prk = (PrivateKey) inputPrivate.readObject();

                    inputPublic.close();
                    inputPrivate.close();

                    keys = new KeyPair(pk, prk);
                    System.out.println(pk.toString());
                    System.out.println(prk.toString());
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                    return false;
                }
                return true;
            }
        } else {
            Logger log = Logger.getLogger("Krypter");
            log.log(Level.WARNING, "Ordner 'config' oder die Dateien für "
                    + "Private und Public Key wurden nicht gefunden!");
        }
        return false;
    }

    public KeyPair getKeys() {
        return keys;
    }

    public PublicKey getForeignPublicKey() {
        return foreignPubkey;
    }

    public void setForeignPublicKey(PublicKey key) {
        this.foreignPubkey = key;
    }

    public boolean hasForeignPublicKey() {
        return foreignPubkey != null;
    }

    public static OutputStream encryptOutputStream(OutputStream os, PublicKey publicKey) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(new SecureRandom());
        Key key = kg.generateKey();

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.WRAP_MODE, publicKey);

        byte[] wKey = cipher.wrap(key);
        os.write(wKey);

        cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return new CipherOutputStream(os, cipher);
    }

    public static InputStream decryptInputStream(InputStream is, PrivateKey privateKey) throws Exception {
        byte[] wKey = new byte[256];
        Cipher cipher = Cipher.getInstance("RSA");
        is.read(wKey, 0, 256);

        cipher.init(Cipher.UNWRAP_MODE, privateKey);
        Key key = cipher.unwrap(wKey, "AES", Cipher.SECRET_KEY);

        cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new CipherInputStream(is, cipher);
    }

}
