/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileInfo.Structure;

import exceptions.IllegalCharacterException;
import exceptions.NullDataException;
import exceptions.PatternUnmatchException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import reader.Reader;

/**
 *
 * @author ThatCommand
 */
public class Structure implements ContainerObject, DataSettings {

    BooleanProperty building = new SimpleBooleanProperty(false);

    ArrayList<StructureObject> structure = new ArrayList<>();

    public static HashMap<String, StructureObject> readed_Objects;

    Separator sep;
    Symbol open_char;
    Symbol close_char;

    public Structure() {

    }

    public Structure buildStructure() {
        building.setValue(Boolean.TRUE);
        return this;
    }

    public Structure addStructureObject(StructureObject obj) {
        structure.add(obj);
        return this;
    }

    public Structure removeStructureObject(StructureObject obj) {
        structure.remove(obj);
        return this;
    }

    /**
     * IT<p>
     * Aggiunge un nuovo gruppo alla struttura
     * <p>
     * EN<p>
     * Add new ContainerGroup to this structure
     *
     * @return
     */
    public ContainerGroup addContainerGroup() {
        ContainerGroup cg = new ContainerGroup();
        structure.add(cg);
        return cg;
    }

    public DataHolder addDataHolder() {
        DataHolder dh = new DataHolder();
        structure.add(dh);
        return dh;
    }

    @Override
    public Structure defineSeparator(Separator sep) {
        this.sep = sep;
        return this;
    }

    @Override
    public Structure defineSymbolOpenGroup(Symbol sym) {
        open_char = sym;
        return this;
    }

    @Override
    public Structure defineSymbolCloseGroup(Symbol sym) {
        close_char = sym;
        return this;
    }

    @Override
    public Structure defineSymbolGroup(Symbol open, Symbol close) {
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

    public String getStructure() {
        StringBuilder sbs = new StringBuilder();
        sbs.append("{OPEN_SYS}");
        structure.forEach(so -> {
            sbs.append(so.getData());
        });
        sbs.append("{CLOSE_SYS}");
        sbs.append("\n").append(Symbol.DEFINITION_START);
        sbs.append(getDataSettings());
        return sbs.toString();
    }

    ArrayList<String> dt_strs = new ArrayList<>();

    public void getDataSettings(Object o) {
        if (o instanceof DataSettings) {
            DataSettings so = (DataSettings) o;
            dt_strs.add(so.getDataSettings());
            if (o instanceof ContainerObject) {
                ContainerObject cgg = (ContainerObject) so;
                cgg.getObjects().forEach(obj -> {
                    getDataSettings(obj);
                });
            }
        }
    }

    @Override
    public String getDataSettings() {
        StringBuilder sb = new StringBuilder();
        this.structure.forEach(strct -> {
            getDataSettings(strct);
        });
        dt_strs.forEach(ds -> sb.append(ds).append("\n"));
        return sb.toString();
    }

    @Override
    public ArrayList<StructureObject> getObjects() {
        return structure;
    }

    @Override
    public String getHash() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void gen_Hash() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean checkHash(String hash) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    public void read(Reader r) throws IllegalCharacterException, Exception {
        if (r != null) {
            r.read();
            String definitions = r.getDefs();
            if (definitions.matches(getPattern().pattern())) {
                readed_Objects = new HashMap<>();
                Pattern general = Pattern.compile("#DEFINE:(.+) AS ([^" + Symbol.CLOSE_BLOCK + "]{2})" + Symbol.CLOSE_BLOCK);
                Matcher m_g = general.matcher(definitions);
                while (m_g.find()) {
                    String definition = m_g.group(1);
                    String type = m_g.group(2);
                    HashMap<String, String> asd = getDef(definition);
                    String hash = null;
                    char sepe = 0;
                    char op = 0;
                    char cl = 0;
                    char ass = 0;
                    for (String key : asd.keySet()) {
                        String asd_ = asd.get(key);
                        switch (key) {
                            case "HASH":
                                hash = asd_;
                                break;
                            case "SEPARATOR":
                                if (asd_ == null || asd_.isEmpty()) {
                                    sepe = (char) 000;
                                } else {
                                    sepe = asd_.charAt(0);
                                }
                                break;
                            case "OPEN":
                                if (asd_ == null || asd_.isEmpty()) {
                                    op = (char) 000;
                                } else {
                                    op = asd_.charAt(0);
                                }
                                break;
                            case "CLOSE":
                                if (asd_ == null || asd_.isEmpty()) {
                                    cl = (char) 000;
                                } else {
                                    cl = asd_.charAt(0);
                                }
                                break;
                            case "ASSIGN":
                                if (asd_ == null || asd_.isEmpty()) {
                                    ass = (char) 000;
                                } else {
                                    ass = asd_.charAt(0);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    switch (type) {
                        case "CG":
                            ContainerGroup cg = new ContainerGroup(hash, sepe, op, cl);
                            readed_Objects.put(hash, cg);
                            break;
                        case "DH":
                            DataHolder dh = new DataHolder(hash, sepe, ass);
                            readed_Objects.put(hash, dh);
                            break;
                        default:
                            break;
                    }
                }
                String datas = r.getFile();
                readData(datas);
            } else {
                throw new PatternUnmatchException();
            }
        }
    }

    public Structure readData(String data) throws Exception {
        if (data != null) {
            if (data.matches(getPattern2().pattern())) {
                ArrayList<String> internal_datas = parseData(data);
                if (internal_datas != null && internal_datas.size() > 0) {
                    for (String datar : internal_datas) {
                        Pattern pattern_2 = Pattern.compile(Symbol.OPEN_BLOCK + "([^" + Symbol.CLOSE_BLOCK + "]{2})#HC\\?([^" + Symbol.CLOSE_BLOCK + Symbol.STRING_DEFINITION + "]*)\\?" + Symbol.STRING_DEFINITION);
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
                                            structure.add(cg);
                                        }
                                    }
                                    break;
                                case "DH":
                                    if (so != null) {
                                        DataHolder dh = (DataHolder) so;
                                        if (dh.checkHash(block_hash)) {
                                            dh.readData(datar);
                                            structure.add(so);
                                        }
                                    }
                                    break;
                            }
                        } else {
                            throw new NullDataException();
                        }
                    }
                } else {
                    throw new NullDataException();
                }
            } else {
                try {
                    throw new PatternUnmatchException(this.getClass(), this.getClass().getMethod("readData", String.class));
                } catch (NoSuchMethodException | SecurityException ex) {
                    throw new Exception("Error in " + this.getClass().getName());
                }
            }
        } else {
            throw new NullDataException();
        }
        return this;
    }

    private String exctractData(String dt) {
        if (dt.matches(getPattern2().pattern())) {
            Matcher m = getPattern2().matcher(dt);
            if (m.find()) {
                String s = m.group(1);
                return s;
            } else {
                throw new NullDataException();
            }
        } else {
            throw new PatternUnmatchException();
        }
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
        } else {
            throw new NullDataException();
        }
    }

    /**
     * Questo hash map è formato da:
     *
     * HashCode,
     *
     * @return
     */
    private HashMap<String, String> getData(String s) {

        return null;
    }

    private HashMap<String, String> getDef(String s) {

        HashMap<String, String> hm = new HashMap<>();

        String[] ses = s.split(" AND ");
        Pattern general = Pattern.compile("\\[([^:]*):(.*)\\]");

        for (String sub_s : ses) {
            Matcher m_g = general.matcher(sub_s);
            if (m_g.find()) {
                String Name = m_g.group(1);
                String value = m_g.group(2);
                hm.put(Name, value);
            } else {
                throw new NullDataException();
            }
        }

        return hm;
    }

    public Pattern getPattern() {
        p = Pattern.compile(Symbol.DEFINITION_START
                + "(#DEFINE:([^"
                + Symbol.CLOSE_BLOCK
                + "])* AS (.*){2}"
                + Symbol.CLOSE_BLOCK
                + "[\n])*", Pattern.DOTALL);
        return p;
    }

    public Pattern getPattern2() {
        p2 = Pattern.compile("\\{OPEN_SYS\\}(.*)\\{CLOSE_SYS\\}", Pattern.DOTALL);
        return p2;
    }

    private Pattern p;
    private Pattern p2;

    public void setPattern(Pattern p) {
        this.p = p;
    }

    public void setPattern2(Pattern p2) {
        this.p2 = p2;
    }

    public boolean checkPattern(String text) {
        boolean asd = text.matches(getPattern().pattern());
        System.out.println("DEPENDENCIES MACTHES:" + asd);
        return asd;
    }

    public boolean checkPattern2(String text) {
        boolean asd = text.matches(getPattern2().pattern());
        System.out.println("STRUCTURE MATCHES:" + asd);
        return asd;
    }
}
