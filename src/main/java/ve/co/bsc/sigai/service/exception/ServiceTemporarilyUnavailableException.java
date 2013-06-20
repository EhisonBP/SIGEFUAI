/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ve.co.bsc.sigai.service.exception;

/**
 *
 * @author User
 */
public class ServiceTemporarilyUnavailableException extends RuntimeException {

    /**
     * Creates a new instance of <code>ServiceTemporarilyUnavailableException</code> without detail message.
     */
    public ServiceTemporarilyUnavailableException() {
    }


    /**
     * Constructs an instance of <code>ServiceTemporarilyUnavailableException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ServiceTemporarilyUnavailableException(String msg) {
        super(msg);
    }
}
