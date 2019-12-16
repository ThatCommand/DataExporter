/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileInfo.Structure;

import java.sql.Date;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * IT<p>
 * Data Holder<p>
 * per salvare dichiarare in ordine:
 * <br> {@link FileInfo.Structure.DataHolder#setVariableName}
 * <br> {@link #setAssignSymbol}
 * <br> {@link #setSeparator} (nel caso si voglia salvare più di un valore)<br>
 * {@link #setData}
 * <p>
 * Per richiedere il valore basta chiamare il metodo {@link #getData}
 * <p>
 * EN<p>
 * Data Holder<p>
 * to save declare in order:
 * <br> {@link FileInfo.Structure.DataHolder#setVariableName}
 * <br> {@link #setAssignSymbol}
 * <br> {@link #setSeparator} (nel caso si voglia salvare più di un valore)<br>
 * {@link #setData}
 * <p>
 * To get the fianl value call the method {@link #getData}
 *
 * @author ThatCommand
 */
public class DataHolder implements StructureObject, DataSettings {

    Object[] stored_data;
    String name;
    StringBuilder FormattedData = new StringBuilder();
    Symbol assign_symb = new Symbol('=');
    Symbol open_data_container = new Symbol('(');
    Symbol close_data_container = new Symbol(')');
    Separator multiple_separator;

    private final static String tmp_VarName = "DataHolder_VARNAME";
    private final static String tmp_MultipleVar = "DataHolder_MULTIPLEVAR";
    private final static String tmp_SingleVar = "DataHolder_VAR";
    private StringBuilder hash = new StringBuilder();

    public final static String BLOCK_DEFINITION = Symbol.OPEN_BLOCK + "DH#HC?INSERT_HASH_HERE?INSERT_BLOCK_HERE" + Symbol.CLOSE_BLOCK;

    public static int last_automated_assigned_name = 0;

    public DataHolder(String holder_name) {
        name = holder_name.replaceAll("\n", Symbol.SLASH_N);
        gen_Hash();
    }

    /**
     * IT<p>
     * Oggetto che può essere salvato sottoforma di dato scritto
     * <p>
     * EN<p>
     * Object that can be saved in text format
     */
    public DataHolder() {
        last_automated_assigned_name++;
        name = "DataHolder_" + last_automated_assigned_name;
        gen_Hash();
    }

    public DataHolder(String hash, char sepatator, char assign) {
        super();
        this.hash.append(hash);
        multiple_separator = new Separator(sepatator);
        assign_symb = new Symbol(assign);
    }

    @Override
    public final void gen_Hash() {
        hash
                .append(name.charAt(0))
                .append(Integer.toHexString((int) open_data_container.getSymbol()))
                .append(Integer.toHexString((int) close_data_container.getSymbol()))
                .append(Integer.toHexString((int) assign_symb.getSymbol()))
                .append(name.length())
                .append(name.getBytes());
    }

    @Override
    public String getData() {
        checkStatus();
        if (acceptable) {
            String template = getStringTemplate();
            String finale = template.replace(tmp_VarName, name);
            if (finale.contains(tmp_MultipleVar)) {
                StringBuilder vars = new StringBuilder();
                for (Object st_dt : stored_data) {
                    if (st_dt instanceof HandableDataObject) {
                        vars.append(((HandableDataObject) st_dt).getFormattedData().replaceAll(multiple_separator.getData(), ""));
                        vars.append(multiple_separator.getSeparatorChar());
                    } else if (st_dt instanceof String) {
                        vars.append(Symbol.DLE).append(Symbol.DATA_STRING);
                        vars.append(((String) st_dt).replaceAll(multiple_separator.getData(), "").replaceAll("\n", Symbol.SLASH_N));
                        vars.append(multiple_separator.getSeparatorChar());
                    } else if (st_dt instanceof Integer) {
                        vars.append(Symbol.DLE).append(Symbol.DATA_INTEGER);
                        vars.append(((int) st_dt));
                        vars.append(multiple_separator.getSeparatorChar());
                    } else if (st_dt instanceof Double) {
                        vars.append(Symbol.DLE).append(Symbol.DATA_DOUBLE);
                        vars.append(((double) st_dt));
                        vars.append(multiple_separator.getSeparatorChar());
                    } else if (st_dt instanceof Float) {
                        vars.append(Symbol.DLE).append(Symbol.DATA_FLOAT);
                        vars.append(((float) st_dt)).append("f");
                        vars.append(multiple_separator.getSeparatorChar());
                    } else if (st_dt instanceof Short) {
                        vars.append(Symbol.DLE).append(Symbol.DATA_SHORT);
                        vars.append(((short) st_dt));
                        vars.append(multiple_separator.getSeparatorChar());
                    } else if (st_dt instanceof Long) {
                        vars.append(Symbol.DLE).append(Symbol.DATA_LONG);
                        vars.append(((long) st_dt));
                        vars.append(multiple_separator.getSeparatorChar());
                    } else if (st_dt instanceof Boolean) {
                        vars.append(Symbol.DLE).append(Symbol.DATA_BOOLEAN);
                        vars.append(((boolean) st_dt));
                        vars.append(multiple_separator.getSeparatorChar());
                    } else if (st_dt instanceof Object) {
                        vars.append(Symbol.DLE).append(Symbol.DATA_OBJECT);
                        vars.append(((Object) st_dt).toString());
                        vars.append(multiple_separator.getSeparatorChar());
                    }
                }
                vars.deleteCharAt(vars.length() - 1);
                finale = finale.replaceAll(tmp_MultipleVar, vars.toString());
            } else if (finale.contains(tmp_SingleVar)) {
                for (Object st_dt : stored_data) {
                    if (st_dt instanceof HandableDataObject) {
                        finale = finale.replaceAll(tmp_SingleVar, ((HandableDataObject) st_dt).getFormattedData());
                        break;
                    } else if (st_dt instanceof String) {
                        finale = Symbol.DLE + Symbol.DATA_STRING + finale.replaceAll(tmp_SingleVar, (String) st_dt).replaceAll("\n", Symbol.SLASH_N);
                        break;
                    } else if (st_dt instanceof Integer) {
                        finale = Symbol.DLE + Symbol.DATA_INTEGER + finale.replaceAll(tmp_SingleVar, "" + (int) st_dt);
                        break;
                    } else if (st_dt instanceof Double) {
                        finale = Symbol.DLE + Symbol.DATA_DOUBLE + finale.replaceAll(tmp_SingleVar, "" + (double) st_dt);
                        break;
                    } else if (st_dt instanceof Float) {
                        finale = Symbol.DLE + Symbol.DATA_FLOAT + finale.replaceAll(tmp_SingleVar, (float) st_dt + "f");
                        break;
                    } else if (st_dt instanceof Short) {
                        finale = Symbol.DLE + Symbol.DATA_SHORT + finale.replaceAll(tmp_SingleVar, "" + (short) st_dt);
                        break;
                    } else if (st_dt instanceof Long) {
                        finale = Symbol.DLE + Symbol.DATA_LONG + finale.replaceAll(tmp_SingleVar, "" + (long) st_dt);
                        break;
                    } else if (st_dt instanceof Boolean) {
                        finale = Symbol.DLE + Symbol.DATA_BOOLEAN + finale.replaceAll(tmp_SingleVar, "" + (boolean) st_dt);
                        break;
                    } else if (st_dt instanceof Object) {
                        finale = Symbol.DLE + Symbol.DATA_OBJECT + finale.replaceAll(tmp_SingleVar, ((Object) st_dt).toString());
                        break;
                    }
                }
            }

//            boolean correct = finale.matches(getPattern().pattern());//Check if final string matches pattern
            return finale;
        }
        return null;
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
                    .append((name != null && !name.trim().isEmpty() ? name : tmp_VarName))
                    .append(Symbol.STRING_DEFINITION)
                    .append(assign_symb.getSymbol());
            FormattedData.append((multiple_separator != null ? open_data_container.getSymbol() : ""));
            FormattedData.append((multiple_separator != null ? tmp_MultipleVar : tmp_SingleVar));
            FormattedData.append((multiple_separator != null ? close_data_container.getSymbol() : ""));
        }
    }

    @Override
    public boolean isContainer() {
        return false;
    }

    @Override
    public boolean isDataHolder() {
        return true;
    }

    @Override
    public boolean isStructureSpecialChar() {
        return false;
    }

    /**
     * IT<p>
     * Non viene usato
     * <p>
     * EN<p>
     * Useless here
     *
     * @param so
     * @return
     */
    @Override
    public StructureObject addStructureObject(StructureObject so) {
        return this;
    }

    /**
     * IT<p>
     * Non viene usato
     * <p>
     * EN<p>
     * Useless here
     *
     * @param so
     * @return
     */
    @Override
    public StructureObject removeStructureObject(StructureObject so) {
        return this;
    }

    boolean acceptable = true;

    /**
     * IT<p>
     * Imposta il nome della variabile
     * <p>
     * EN<p>
     * Set the variable name
     *
     * @param name
     * @return
     */
    public DataHolder setVariableName(String name) {
        boolean continuable = true;
        for (int i = 0; i < Symbol.protected_symbols.length; i++) {
            if (name.contains("" + Symbol.protected_symbols[i])) {
                continuable = acceptable = false;
            }
        }
        if (continuable) {
            this.name = name.replaceAll("\n", Symbol.SLASH_N);
        }
        return this;
    }

    /**
     * IT<p>
     * Imposta i valori della variabile, nel caso di multipli valori ricordarsi
     * di impostare un valore tramite il metodo {@link setSeparator} altrimenti
     * tutti i valori tranne il primo nella lista verranno ignorati.
     * <p>
     * EN<p>
     * Set the variable values, in case of multiple values it is necessary to
     * set the separator trought te method {@link #setSeparator} otherwise all
     * the values after the first are ignored.
     *
     * @param values
     * @return
     */
    public DataHolder setData(Object... values) {
        this.stored_data = values;
        return this;
    }

    /**
     * IT<p>
     * Simbolo che serve per dichiarare i valori:
     * <p>
     * es.<br>
     * nome_var (simbolo di assegnazione) valori<br>
     * nome = valore
     * <p>
     * EN<p>
     * Symbol which declares the initialization of the values:
     * <p>
     * eg.<br>
     * var_name (assign symbol) values<br>
     * name = value
     *
     * @param assign_simbol
     * @return
     */
    public DataHolder setAssignSymbol(Symbol assign_simbol) {
        if (!String.valueOf(Symbol.protected_symbols).contains("" + assign_simbol.getSymbol())) {
            assign_symb = assign_simbol;
        } else {
            acceptable = false;
        }
        return this;
    }

    /**
     * IT<p>
     * Se != da null allora questo oggetto DataHolder viene considerato con
     * un'array di dati assegnati
     * <p>
     * EN<p>
     * If its not null then this object is considered as an'array of differnet
     * objects
     *
     * @param multiple_data_holding
     * @return
     */
    public DataHolder setSeparator(Separator multiple_data_holding) {
        if (!String.valueOf(Symbol.protected_symbols).contains("" + multiple_data_holding.getSymbol())) {
            multiple_separator = multiple_data_holding;
        } else {
            acceptable = false;
        }
        return this;
    }

    public DataHolder checkStatus() {
        if (!acceptable) {
            acceptable = true;
            if (multiple_separator != null || String.valueOf(Symbol.protected_symbols).contains("" + multiple_separator.getSymbol())) {
                acceptable = false;
            }
            if (assign_symb != null || String.valueOf(Symbol.protected_symbols).contains("" + assign_symb.getSymbol())) {
                acceptable = false;
            }
            for (int i = 0; i < Symbol.protected_symbols.length; i++) {
                if (name.contains("" + Symbol.protected_symbols[i])) {
                    acceptable = false;
                }
            }
        }
        return this;
    }

    @Override
    public boolean isAcceptable() {
        checkStatus();
        return acceptable;
    }

    @Override
    public Pattern getPattern() {
        p = Pattern.compile(Symbol.OPEN_BLOCK
                + "[^"
                + Symbol.CLOSE_BLOCK
                + "]{2}#"
                + "HC\\?([^"
                + Symbol.CLOSE_BLOCK
                + "]*)\\?"
                + Symbol.STRING_DEFINITION
                + "([^"
                + Symbol.CLOSE_BLOCK
                + "]*)"
                + Symbol.STRING_DEFINITION
                + assign_symb.getSymbol()
                + (multiple_separator != null
                        ? "\\(([^" + Symbol.CLOSE_BLOCK + "]*)\\)"
                        : "([^" + Symbol.CLOSE_BLOCK + "]*)")//.{2}#"(.*)"=[\(](.*[^]*)[\)]
                + Symbol.CLOSE_BLOCK
        );
        return p;
    }

    Pattern p;

    @Override
    public void setPattern(Pattern p) {
        this.p = p;
    }

    /**
     * IT<p>
     * Legge una stringa dopo avere impostato i parametri a quest'oggetto:<br>
     * {@link #setPattern(java.util.regex.Pattern) setPattern}<br>
     * {@link #setAssignSymbol(FileInfo.Structure.Symbol) setAssignSymbol}<br>
     * e nel caso ci siano più valori:
     * {@link #setSeparator(FileInfo.Structure.Separator) setSeparator}
     * <p>
     * EN<p>
     * Read a string after this parameters have been setted:<br>
     * {@link #setPattern(java.util.regex.Pattern) setPattern}<br>
     * {@link #setAssignSymbol(FileInfo.Structure.Symbol) setAssignSymbol}<br>
     * e nel caso ci siano più valori:
     * {@link #setSeparator(FileInfo.Structure.Separator) setSeparator}
     *
     * @param data
     * @return
     */
    public DataHolder readData(String data) {
        if (data != null && data.matches(p.pattern())) {
            Pattern pattern_1 = Pattern.compile(Symbol.OPEN_BLOCK
                    + "DH#HC\\?([^"
                    + Symbol.CLOSE_BLOCK
                    + "]*)\\?");
            Pattern pattern = Pattern.compile("\\?\"(.*?)\"");
            Pattern pattern_2 = Pattern.compile(this.assign_symb.getSymbol() + "\\((.*?)\\)" + Symbol.CLOSE_BLOCK);
            Matcher match_1 = pattern_1.matcher(data);
            Matcher matcher = pattern.matcher(data);
            Matcher match_2 = pattern_2.matcher(data);
            if (matcher.find()) {//nome variabile
                setVariableName(matcher.group(1));
            }
            if (match_1.find()) {
                hash = new StringBuilder(match_1.group(1));
            }
            if (match_2.find()) {
                String str_dt = match_2.group(1);
                String[] arr = (multiple_separator != null ? str_dt.split(multiple_separator.getData()) : new String[]{str_dt});
                ArrayList<Object> lista = new ArrayList<>();
                for (String selected_data : arr) {
                    String datas = selected_data;
                    boolean continue_ = true;
                    int selected_char = 0;
                    StringBuilder data_type = new StringBuilder();
                    if (datas.charAt(selected_char) == Symbol.DLE) {
                        datas = datas.replaceAll("" + Symbol.DLE, "");
                    } else {
                        continue_ = false;
                    }
                    while (continue_) {
                        switch (datas.charAt(selected_char)) {
                            case Symbol.NAK:
                                data_type.append(datas.charAt(selected_char));
                                selected_char++;
                                break;
                            case Symbol.SYN:
                                data_type.append(datas.charAt(selected_char));
                                selected_char++;
                                break;
                            case Symbol.DC1:
                            case Symbol.DC2:
                            case Symbol.DC3:
                            case Symbol.DC4:
                                if (selected_char > 0 && (data_type.toString() == null ? "" + Symbol.SYN == null : data_type.toString().equals("" + Symbol.SYN))) {
                                    continue_ = false;
                                }
                                data_type.append(datas.charAt(selected_char));
                                selected_char++;
                                break;
                            case Symbol.NUL:
                                continue_ = false;
                                data_type.append(datas.charAt(selected_char));
                                break;
                            default:
                                continue_ = false;
                                break;
                        }
                    }
                    String dt = datas.replace(data_type.toString(), "");
                    switch (data_type.toString()) {
                        case Symbol.DATA_BOOLEAN:
                            lista.add(Boolean.valueOf(dt));
                            break;
                        case Symbol.DATA_CHAR:
                            lista.add(dt != null ? dt.charAt(0) : (char) 000);
                            break;
                        case Symbol.DATA_DATE:
                            lista.add(Date.valueOf(dt));
                            break;
                        case Symbol.DATA_DOUBLE:
                            lista.add(Double.parseDouble(dt));
                            break;
                        case Symbol.DATA_FLOAT:
                            lista.add(Float.parseFloat(dt));
                            break;
                        case Symbol.DATA_INTEGER:
                            lista.add(Integer.parseInt(dt));
                            break;
                        case Symbol.DATA_LONG:
                            lista.add(Long.parseLong(dt));
                            break;
                        case Symbol.DATA_OBJECT:
                            lista.add((Object) dt + " | UNKNOWN OBJ");
                            break;
                        case Symbol.DATA_SHORT:
                            lista.add(Short.parseShort(dt));
                            break;
                        case Symbol.DATA_STRING:
                            lista.add(dt);
                            break;
                        default:
                            break;
                    }
                }
                stored_data = new Object[lista.size()];
                for (int i = 0; i < lista.size(); i++) {
                    stored_data[i] = lista.get(i);
                }
            }
        }
        return this;
    }

    @Override
    public String toString() {
        String text_to_out = name + ":";
        if (stored_data != null) {
            for (Object stored_data1 : stored_data) {
                text_to_out += "\n\t" + stored_data1 + "\t- " + stored_data1.getClass();
            }
        } else {
            return getClass().getName() + "@" + Integer.toHexString(hashCode());
        }
        return text_to_out;
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
                .append(multiple_separator != null ? multiple_separator.getData() : Symbol.NUL)
                .append("]")
                .append(" AND [ASSIGN:")
                .append(assign_symb.getSymbol())
                .append("] AS DH")
                .append(Symbol.CLOSE_BLOCK);
        return sb.toString();
    }

    @Override
    public String getHash() {
        return hash.toString();
    }

    @Override
    public boolean checkHash(String hash) {
        return this.hash.toString().equals(hash);
    }

}
