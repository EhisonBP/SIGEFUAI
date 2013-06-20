/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ve.co.bsc.util.bpm;

import org.apache.log4j.Logger;

/**
 *
 * @author User
 */
public class ProcessLogger {

    private static final Logger logger = Logger.getLogger(ProcessLogger.class);

    public static void logException(Exception e) {
        logger.error("Error procesando proceso", e);
    }

    public static void logDebug(String message) {
        logger.debug(message);
    }

    public static void logInfo(String message) {
        logger.info(message);
    }

}
