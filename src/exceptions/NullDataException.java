/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author gabri
 */
public class NullDataException extends RuntimeException {

    public NullDataException() {
        super("Received data is null -- not checkable");
    }

}
