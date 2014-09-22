/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.common.datacontents;

import de.codekings.common.Enumerators.ClassType;
import java.security.PublicKey;

/**
 *
 * @author Jan
 */
public class CKPublicKey extends Sendable {

    private final PublicKey pubKey;

    public CKPublicKey(PublicKey p) {
        this.pubKey = p;
    }

    public PublicKey getPublicKey() {
        return pubKey;
    }

    @Override
    public ClassType getClassType() {
        return ClassType.T_PUBLICKEY;
    }

}
