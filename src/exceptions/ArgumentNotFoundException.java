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
public class ArgumentNotFoundException extends RuntimeException {

    public ArgumentNotFoundException() {
        super("Argument is found but its value is empty");
    }

    public ArgumentNotFoundException(String argName) {
        super("Argument \"" + argName + "\"is not found");
    }
}
