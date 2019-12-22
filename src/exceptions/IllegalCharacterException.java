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
public class IllegalCharacterException extends Exception {

    public IllegalCharacterException() {
        super("This character cannot be used in this field.");
    }

    public IllegalCharacterException(char c) {
        super("Character '" + c + "' is definied as private: it cannot be used.");
    }

    public IllegalCharacterException(String message) {
        super("Error defining character: " + message);
    }

}
