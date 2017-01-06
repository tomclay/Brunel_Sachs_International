package com.brunel.sachs.Server;

import java.io.IOException;
import java.util.logging.*;

/**
 * Created by Tom Clay ESQ.
 */

public class transactionLogging {

    /**
     * This class creates the logger, and provides an interface for the application
     * to add entries to the logging file.
     */

    static Logger logger;
    public Handler fileHandler;
    Formatter plainText;

    /**
     * This method prepares the logger for use, sets the location of the log file, and
     * sets formatting.
     *
     * @throws IOException
     */

    private transactionLogging() throws IOException {

        // Initialise the logger
        logger = Logger.getLogger(transactionLogging.class.getName());
        logger.setLevel(Level.INFO);
        // Initialise the fileHandler
        fileHandler = new FileHandler("/Users/tomclay/IdeaProjects/Brunel_Sachs_International/log.txt", true);
        // Initialise formatter, set formatting, and handler. Keep simple formatting
        plainText = new SimpleFormatter();
        fileHandler.setFormatter(plainText);
        logger.addHandler(fileHandler);
    }

    /**
     * This method ensures all classes can add to the same log file.
     *
     * @return
     */

    private static Logger getLogger() {
        if (logger == null) {
            try {
                new transactionLogging();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logger;
    }

    /**
     * This method provides access to the logger
     *
     * @param level The type of message E.G. Error, or infomration
     * @param msg The message itself
     */

    public static void log(Level level, String msg) {
        getLogger().log(level, msg);
    }
}
