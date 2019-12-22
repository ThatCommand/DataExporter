/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import java.lang.reflect.Method;

/**
 *
 * @author gabri
 */
public class PatternUnmatchException extends RuntimeException {

    public PatternUnmatchException(Class c, Method m) {
        super("Data pattern doesn't match for the class \"" + c.getCanonicalName() + "\" in the method \"" + m.getName() + "\"");
    }

    public PatternUnmatchException() {
        super("Data pattern mismatch with the block's base pattern");
    }

    public PatternUnmatchException(String message) {
        super("Pattern mismatches: " + message);
    }
}
