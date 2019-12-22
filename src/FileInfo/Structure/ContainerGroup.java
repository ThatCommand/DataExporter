/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileInfo.Structure;

import static FileInfo.Structure.Structure.readed_Objects;
import exceptions.IllegalCharacterException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ThatCommand
 */
public class ContainerGroup implements StructureObject, ContainerObject, DataSettings {

    StringBuilder FormattedData = new StringBuilder();
    ArrayList<StructureObject> sos = new ArrayList<>();

    Separator sep = new Separator(';');
    Symbol open_char = new Symbol('[');
    Symbol close_char = new Symbol(']');
    private String group_name;

    private final static String tmp_VarName = "ContainerGroup_VARNAME";
    private final static String tmp_Holdable = "ContainerGroup_HOLDABLE";
    private StringBuilder hash = new StringBuilder();
    private final boolean readed;

    public final static String BLOCK_DEFINITION = Symbol.OPEN_BLOCK + "CG#HC?INSERT_HASH_HERE?INSERT_BLOCK_HERE" + Symbol.CLOSE_BLOCK;
    public static int lastnumbernamegroup = 0;

    /**
     * IT<p>
     * Oggetto che permette di contenere altri sotto-gruppi e/o assegnazione di
     * dati.
     * <p>
     * EN<p>
     * Object that can contain other sub-objects and/or variables
     */
    public ContainerGroup() {
        readed = false;
        setGroupName("DATA_GROUP_" + lastnumbernamegroup);
        lastnumbernamegroup++;
        gen_Hash();
    }

    public ContainerGroup(String groupName) {
        readed = false;
        setGroupName(groupName);
        gen_Hash();
    }

    public ContainerGroup(String hash, char sepatator, char open, char close) throws IllegalCharacterException {
        this.hash.append(hash);
        defineSeparator(new Separator(sepatator));
        defineSymbolOpenGroup(new Symbol(open));
        defineSymbolCloseGroup(new Symbol(close));
        readed = true;
    }

    @Override
    public final void gen_Hash() {
        hash
                .append(group_name.charAt(0))
                .append(Integer.toHexString((int) open_char.getSymbol()))
                .append(Integer.toHexString((int) close_char.getSymbol()))
                .append(Integer.toHexString((int) sep.getSymbol()))
                .append(group_name.length())
                .append(group_name.getBytes());
    }

    @Override
    public String getData() {
        //Gestisci
        checkStatus();
        if (acceptable) {
            String template = getStringTemplate();
            String finale = template.replace(tmp_VarName, group_name);
            StringBuilder sb = new StringBuilder();
            sos.forEach(so -> sb.append("\n\t").append(so.getData()));
            finale = finale.replace(tmp_Holdable, sb.toString() + "\n");
            return finale;
        }
        return null;
    }

    @Override
    public boolean isContainer() {
        return true;
    }

    @Override
    public boolean isDataHolder() {
        return false;
    }

    boolean acceptable = true;

    /**
     * IT<p>
     * Imposta il nome del gruppo
     * <p>
     * EN<p>
     * Set group's name
     *
     * @param name
     * @return
     */
    public final ContainerGroup setGroupName(String name) {
        boolean continuable = true;
        for (int i = 0; i < Symbol.protected_symbols.length; i++) {
            if (name.contains("" + Symbol.protected_symbols[i])) {
                continuable = acceptable = false;
            }
        }
        if (continuable) {
            this.group_name = name.replaceAll("\n", Symbol.SLASH_N);
        }
        return this;
    }

    public String getGroupName() {
        if (group_name != null) {
            return group_name.replaceAll(Symbol.SLASH_N, "\n");
        } else {
            return group_name;
        }
    }

    @Override
    public StructureObject addStructureObject(StructureObject so) {
        if (!isDataHolder()) {
            sos.add(so);
        }
        return this;
    }

    @Override
    public StructureObject removeStructureObject(StructureObject so) {
        if (!isDataHolder()) {
            sos.remove(so);
        }
        return this;
    }

    @Override
    public boolean isStructureSpecialChar() {
        return false;
    }

    @Override
    public final ContainerGroup defineSeparator(Separator sep) throws IllegalCharacterException {
        if (sep != null) {
            if (!String.valueOf(Symbol.protected_symbols).contains("" + sep.getSymbol())) {
                this.sep = sep;
            } else {
                acceptable = false;
                throw new IllegalCharacterException(sep.getSeparatorChar());
            }
        } else {

        }
        return this;
    }

    @Override
    public final ContainerGroup defineSymbolOpenGroup(Symbol sym) {
        open_char = sym;
        return this;
    }

    @Override
    public final ContainerGroup defineSymbolCloseGroup(Symbol sym) {
        close_char = sym;
        return this;
    }

    @Override
    public ContainerGroup defineSymbolGroup(Symbol open, Symbol close) {
        open_char = open;
        close_char = close;
        return this;
    }

    public Symbol getOpenSymbol() {
        return open_char;
    }

    public Symbol getCloseSymbol() {
        return close_char;
    }

    @Override
    public String getStringTemplate() {
        genStringTemplate();
        gen_Hash();
        return BLOCK_DEFINITION.replaceAll("INSERT_BLOCK_HERE", FormattedData.toString()).replaceAll("INSERT_HASH_HERE", hash.toString());
    }

    @Override
    public void genStringTemplate() {
        checkStatus();
        FormattedData = new StringBuilder();
        if (acceptable) {
            FormattedData
                    .append(Symbol.STRING_DEFINITION)
                    .append((group_name != null && !group_name.trim().isEmpty() ? group_name : tmp_VarName))
                    .append(Symbol.STRING_DEFINITION)
                    .append(getOpenChar())
                    .append(tmp_Holdable)
                    .append(getCloseChar());
        }
    }

    public ContainerGroup checkStatus() {
        ArrayList<StructureObject> temps = new ArrayList<>();
        sos.forEach(so -> {
            if (!so.isAcceptable()) {
                temps.add(so);
            }
        });
        sos.removeAll(temps);
        if (group_name != null && !group_name.trim().isEmpty()) {
            for (int i = 0; i < Symbol.protected_symbols.length; i++) {
                if (group_name.contains("" + Symbol.protected_symbols[i])) {
                    acceptable = false;
                }
            }
        }
        return this;
    }

    Pattern p;

    @Override
    public Pattern getPattern() {
        StringBuilder pattern_logic = new StringBuilder();

        pattern_logic
                .append(Symbol.OPEN_BLOCK)
                .append("[^")
                .append(Symbol.CLOSE_BLOCK)
                .append("]{2}#HC\\?([^")
                .append(Symbol.CLOSE_BLOCK)
                .append(Symbol.STRING_DEFINITION)
                .append("]*)\\?")
                .append(Symbol.STRING_DEFINITION)
                .append("([^")
                .append(Symbol.CLOSE_BLOCK)
                .append("]*)")
                .append(Symbol.STRING_DEFINITION)
                .append(getOpenChar());
        if (!readed) {
            sos.forEach(so -> {
                if (!so.isStructureSpecialChar()) {
                    pattern_logic.append(so.getPattern() != null ? so.getPattern().pattern() : null);
                }
            });
        } else {
            pattern_logic.append("(.*)");
        }
        pattern_logic
                .append(getCloseChar())
                .append(Symbol.CLOSE_BLOCK);
        p = Pattern.compile(pattern_logic.toString(), Pattern.DOTALL);
        return p;
    }

    @Override
    public void setPattern(Pattern p) {
        this.p = p;
    }

    @Override
    public boolean isAcceptable() {
        checkStatus();
        return acceptable;
    }

    @Override
    public boolean checkHash(String hash) {
        return this.hash.toString().equals(hash);
    }

    public ContainerGroup readData(String data) {
        if (data != null && data.matches(getPattern().pattern())) {
            Pattern pattern_1 = Pattern.compile(Symbol.OPEN_BLOCK
                    + "CG#HC\\?([^"
                    + Symbol.CLOSE_BLOCK
                    + Symbol.STRING_DEFINITION
                    + "]*)\\?" + Symbol.STRING_DEFINITION);
            Pattern pattern = Pattern.compile("\\?\"(.*?)\"");
            Matcher matcher = pattern.matcher(data);
            Matcher match_1 = pattern_1.matcher(data);
            ArrayList<String> internal_datas = parseData(data);
            if (matcher.find()) {//nome variabile
                setGroupName(matcher.group(1));
            }
            if (match_1.find()) {
                hash = new StringBuilder(match_1.group(1));
            }
            if (internal_datas != null && internal_datas.size() > 0) {
                for (String datar : internal_datas) {
                    Pattern pattern_2 = Pattern.compile(Symbol.OPEN_BLOCK + "(.*?)#HC\\?([^" + Symbol.CLOSE_BLOCK + Symbol.STRING_DEFINITION + "]*)\\?" + Symbol.STRING_DEFINITION);
                    Matcher matcher_2 = pattern_2.matcher(datar);
                    if (matcher_2.find()) {
                        String block_name = matcher_2.group(1);
                        String block_hash = matcher_2.group(2);
                        StructureObject so = readed_Objects.get(block_hash);
                        switch (block_name) {
                            case "CG":
                                if (so != null) {
                                    ContainerGroup cg = (ContainerGroup) so;
                                    if (cg.checkHash(block_hash)) {
                                        cg.readData(datar);
                                        sos.add(cg);
                                    }
                                }
                                break;
                            case "DH":
                                if (so != null) {
                                    DataHolder dh = (DataHolder) so;
                                    if (dh.checkHash(block_hash)) {
                                        dh.readData(datar);
                                        sos.add(so);
                                    }
                                }
                                break;
                        }
                    }
                }
            }
        }
        return this;
    }

    private String getOpenChar() {
        char c = open_char.getSymbol();
        if (c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}') {
            return "\\" + c;
        }
        return "" + c;
    }

    private String getCloseChar() {
        char c = close_char.getSymbol();
        if (c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}') {
            return "\\" + c;
        }
        return "" + c;
    }

    public String exctractData(String dt) {
        if (dt.matches(p.pattern())) {
            Pattern pattern = Pattern.compile(Symbol.STRING_DEFINITION + getOpenChar() + "(.*)" + getCloseChar());
            Matcher m = pattern.matcher(dt);
            if (m.find()) {
                String s = m.group(1);
                return s;
            }
        }
        return null;
    }

    private ArrayList<String> parseData(String dt) {
        dt = exctractData(dt);
//        dt = dt.substring(1, dt.length() - 1);
        if (dt != null && !dt.isEmpty()) {
            ArrayList<String> arr = new ArrayList<>();
            Stack<Character> open_close = new Stack<>();
            boolean ans = true;
            boolean final_closed = false;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < dt.length(); i++) {
                boolean closed = false;
                Character my_char = dt.charAt(i);
                if (my_char == Symbol.OPEN_BLOCK) {
                    open_close.push(my_char);
                    if (final_closed) {
                        sb = new StringBuilder();
                    }
                } else if (my_char == Symbol.CLOSE_BLOCK) {
                    if (!open_close.isEmpty() && open_close.peek() == Symbol.OPEN_BLOCK) {
                        open_close.pop();
                        closed = true;
                    } else {
                        ans = false;
                    }
                }
                final_closed = open_close.size() == 0;
                if (sb != null) {
                    sb.append(my_char);
                }
                if (final_closed) {
                    arr.add(sb != null ? sb.toString() : null);
                    sb = null;
                }

            }
            if (ans) {
                return arr;
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    public String getDataSettings() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("#DEFINE:")
                .append("[HASH:")
                .append(hash.toString())
                .append("]")
                .append(" AND [SEPARATOR:")
                .append(sep != null ? sep.getData() : Symbol.NUL)
                .append("]")
                .append(" AND [OPEN:")
                .append(open_char.getSymbol())
                .append("] AND [CLOSE:")
                .append(close_char.getSymbol())
                .append("] AS CG")
                .append(Symbol.CLOSE_BLOCK);
        return sb.toString();
    }

    @Override
    public ArrayList<StructureObject> getObjects() {
        return sos;
    }

    @Override
    public String getHash() {
        return hash.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"").append(getGroupName()).append("\"");
        return sb.toString();
    }

}
