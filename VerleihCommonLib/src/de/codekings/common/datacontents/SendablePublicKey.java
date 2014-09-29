/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.common.datacontents;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.codekings.common.Enumerators.ClassType;
import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import sun.security.rsa.RSAPublicKeyImpl;

/**
 *
 * @author Jan
 */
@JsonDeserialize(as = SendablePublicKey.class)
public class SendablePublicKey extends Sendable {

    private byte[] pubKey;

    public SendablePublicKey(byte[] p) {
        super(ClassType.T_PUBLICKEY);
        this.pubKey = p;
    }

    public SendablePublicKey() {
        super(ClassType.T_PUBLICKEY);
    }

    public SendablePublicKey(byte[] pubKey, ClassType t) {
        super(t);
        this.pubKey = pubKey;
    }

    public byte[] getPubKey() {
        return pubKey;
    }

    public PublicKey generatePublicKey() {
        try {
            RSAPublicKeyImpl r = new RSAPublicKeyImpl(pubKey);
            return r;
        } catch (InvalidKeyException e) {
            return null;
        }
    }

    public void setPubKey(byte[] pubKey) {
        this.pubKey = pubKey;
    }

    public void insertPublicKey(PublicKey p) {
        byte[] b = p.getEncoded();
        this.pubKey = b;
    }
}
