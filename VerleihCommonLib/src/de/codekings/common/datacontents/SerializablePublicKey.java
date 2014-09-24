/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.codekings.common.datacontents;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import sun.security.rsa.RSAPublicKeyImpl;
import sun.security.x509.AlgorithmId;

/**
 *
 * @author Jan
 */
public class SerializablePublicKey extends RSAPublicKeyImpl{

    public SerializablePublicKey(BigInteger bi, BigInteger bi1) throws InvalidKeyException {
        super(bi, bi1);
    }

    public SerializablePublicKey(byte[] bytes) throws InvalidKeyException {
        super(bytes);
    }

    public AlgorithmId getAlgid() {
        return algid;
    }

    public void setAlgid(AlgorithmId algid) {
        this.algid = algid;
    }

    public byte[] getEncodedKey() {
        return encodedKey;
    }

    public void setEncodedKey(byte[] encodedKey) {
        this.encodedKey = encodedKey;
    }
    
    
    
    
    
}
