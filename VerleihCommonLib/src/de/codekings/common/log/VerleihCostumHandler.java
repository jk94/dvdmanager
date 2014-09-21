/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codekings.common.log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 *
 * @author Jan
 */
public class VerleihCostumHandler extends Formatter {

    private static final DateFormat df = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss.SSS");

    @Override
    public String format(LogRecord lr) {
        StringBuilder builder = new StringBuilder(1000);
        builder.append(df.format(new Date(lr.getMillis())));
        builder.append(" - ");
        String[] klassenname = lr.getSourceClassName().split(".", 3);
        builder.append(klassenname[klassenname.length-1]);
        builder.append(".");
        builder.append(lr.getSourceMethodName()).append("] - ");
        builder.append("[").append(lr.getLevel()).append("] - ");
        builder.append(formatMessage(lr));
        builder.append("\n");
        return builder.toString();
    }

    @Override
    public synchronized String formatMessage(LogRecord lr) {
        return super.formatMessage(lr);
    }

    @Override
    public String getTail(Handler hndlr) {
        return super.getTail(hndlr);
    }

    @Override
    public String getHead(Handler hndlr) {
        return super.getHead(hndlr);
    }

}
