/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileInfo.Structure;

import java.util.regex.Pattern;

/**
 *
 * @author ThatCommand
 */
public class Separator extends Symbol implements StructureObject {

    /**
     * Escape character
     */
    public final static char ESCAPE_CHAR = '';
    /**
     * Character "&#127;" DEL
     */
    public final static char DEL_CHAR = (char) 127;

    public Separator(int character) {
        super(character);
    }

    public Separator(char character) {
        super(character);
    }

    public char getSeparatorChar() {
        return c;
    }

    public Separator getSeparator() {
        return this;
    }

    @Override
    public String getData() {
        return "" + c;
    }

    @Override
    public boolean isContainer() {
        return false;
    }

    @Override
    public boolean isDataHolder() {
        return false;
    }

    @Override
    public boolean isStructureSpecialChar() {
        return true;
    }

    @Override
    public StructureObject addStructureObject(StructureObject so) {
        return this;
    }

    @Override
    public StructureObject removeStructureObject(StructureObject so) {
        return this;
    }

    @Override
    public String getStringTemplate() {
        return getData();
    }

    @Override
    public void genStringTemplate() {

    }

    @Override
    public Pattern getPattern() {
        return null;
    }

    @Override
    public void setPattern(Pattern p) {

    }

    boolean acceptable = true;

    public Separator checkStatus() {
        if (String.valueOf(Symbol.protected_symbols).contains("" + c)) {
            acceptable = false;
        }
        return this;
    }

    @Override
    public boolean isAcceptable() {
        checkStatus();
        return acceptable;
    }


}
