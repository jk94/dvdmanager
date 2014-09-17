/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contents;

import Enumerators.ClassType;

/**
 *
 * @author Jan
 */
public abstract class Sendable {

    protected String cmd = "";

    public abstract ClassType getClassType();

    public String getProtocolCommand() {
        return cmd;
    }

    /**
     *
     * @param cmd
     */
    public void setProtocolCommand(String cmd) {
        this.cmd = cmd;
    }
}
