/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileInfo.Structure;

import java.util.ArrayList;
import java.util.HashMap;
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
        structure.forEach(so -> {
            sbs.append(so.getData());
        });
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

    public void read(Reader r) {
        r.read();
        readed_Objects = new HashMap<>();
        String definitions = r.getDefs();
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
                switch (key) {
                    case "HASH":
                        hash = asd.get(key);
                        break;
                    case "SEPARATOR":
                        sepe = asd.get(key).charAt(0);
                        break;
                    case "OPEN":
                        op = asd.get(key).charAt(0);
                        break;
                    case "CLOSE":
                        cl = asd.get(key).charAt(0);
                        break;
                    case "ASSIGN":
                        ass = asd.get(key).charAt(0);
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
        System.out.println("QUI");
        readed_Objects.keySet().forEach(k -> {
            System.out.print(k + "\t->\t");
            System.out.println(readed_Objects.get(k).toString());
        });
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
            }
        }

        return hm;
    }
}
