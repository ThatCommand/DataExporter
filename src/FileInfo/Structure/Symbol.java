/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileInfo.Structure;

/**
 * IT<p>
 * Identifica un carattere come simbolo speciale per la struttura
 * semplificandone la gestione in caso di molteplici combinazioni di caratteri.
 * <p>
 * EN<p>
 * Identify a character as a special symbol for the structure making easier the
 * reading in case of multiple characters
 *
 * @author ThatCommand
 */
public class Symbol {

    char c;

    public final static char[] protected_symbols = {'{', '}', '[', ']', '(', ')', '"', '\'', 027, 127, 001, 004};
    public final static char STRING_DEFINITION = '"';
    public final static char OPEN_BLOCK = (char) 001;
    public final static char CLOSE_BLOCK = (char) 004;
    /**
     * IT<p>
     * Inizia la definizione dl tipo di dato del tipo oggetto:<br>
     * la costruzione diventa composta:
     * <p>
     * Le definizioni dei tipi di dati vanno inseriti prima dell'inizio di un
     * dato,<br>
     * per esempio se si vuole salvare una string "ciao" ci vogliono il simbolo
     * DLE+(4 cartatteri identificatori) che identificano il tipo di dato:
     * <p>
     * <b>{@link #DLE DLE} +{@link #NAK NAK} +{@link #DC3 DC3}+{@link #DC2 DC2}+{@link #DC4 DC4}</b>
     * //per le stringhe<br>più successivamente la stringa
     * <p>
     * che si semplifica in:
     * <ul>
     * <li>DLE = definizione di un dato</li>
     * <li>NAK = dichiarazione del dato di tipo Object</li>
     *
     * </ul>
     * <p>
     * EN<p>
     * Custom char -- needed to define objects (String, Date, Long, Integer,...)
     */
    public final static char DLE = (char) 020;//Definizione oggetto

    /**
     * IT<p>
     * Per la definizione di variabili basilari quali:<br>
     * <ul>
     * <li>int = &nbsp;{@link #SYN SYN}+{@link #DC1 DC1}
     * <li>double = &nbsp;{@link #SYN SYN}+ {@link #DC2 DC2}
     * <li>float = &nbsp;{@link #SYN SYN}+{@link #DC4 DC4}
     * <li>boolean = &nbsp;{@link #SYN SYN}+{@link #DC3 DC3}
     * </ul>
     * <P>
     * Queste definizioni sono composte sempre dal carattere {@link #SYN SYN}
     * più il loro simbolo base.
     *
     * <p>
     * EN<p>
     * Custom char -- needed to define basic variables
     * (int,double,char,long,...)
     */
    public final static char SYN = (char) 026;
    /**
     * IT<p>
     * Definizione base di una variabile "int"
     *
     * <p>
     * EN<p>
     * Base definition of "int"
     */
    public final static char DC1 = (char) 021;//int -- se in un oggetto il significato è: corto
    /**
     * IT<p>
     * Definizione base di una variabile "double"
     *
     * <p>
     * EN<p>
     * Base definition of "double"
     */
    public final static char DC2 = (char) 022;//double  -- se in un oggetto il significato è: lungo
    /**
     * IT<p>
     * Definizione base di una variabile "boolean"
     *
     * <p>
     * EN<p>
     * Base definition of "boolean"
     */
    public final static char DC3 = (char) 023;//boolean -- se in un oggetto il significato è: può avere diversi tipi 
    /**
     * IT<p>
     * Definizione base di una variabile "float"
     *
     * <p>
     * EN<p>
     * Base definition of "float"
     */
    public final static char DC4 = (char) 024;//float  -- se in un oggetto il significato è: variabile lunga
    /**
     * IT<p>
     * Definizione della variabile a tipo "Object"
     *
     * <p>
     * EN<p>
     * Definition of "Object"
     */
    public final static char NAK = (char) 025;//Oggetto  -- se in un oggetto il significato è: sott'oggetto
    /**
     * IT<p>
     * Nullo
     *
     * <p>
     * EN<p>
     * Null
     */
    public final static char NUL = (char) 000;//  -- se in un oggetto il significato è: non prosegue la definizione (nel caso siano stati usati abbastanza caratteri per dare una definizione al dato)

    /**
     * IT<p>
     * Definizione di caratteri speciali che devono essere sostituiti nell
     * variabili, ad esempio "\n" o "\r" o "\t" possono essere sostituiti per
     * evitare errori di lettura del dato e abilitare il salvataggio
     * human-readable (con ritorni a campo)
     *
     * <p>
     * EN<p>
     * Custom char -- needed to define some special chars
     */
    public final static char ETX = (char) 003;
    /**
     * IT<p>
     * Simbolo "&#172;" che rappresenta il "\r" o "\n" o "\t" se posto alla fine
     * del codice di rappresentazione.
     *
     * <p>
     * EN<p>
     * Symbol "&#172;" which represent "\r" or "\n" or "\t"
     */
    public final static char RETURN_SYMBOL = (char) 172;
    /**
     * IT<p>
     * Rappresenta "\n"
     *
     * <p>
     * EN<p>
     * Represent "\n"
     */
    public final static String SLASH_N = "" + ETX + DC4 + DC3 + DC1 + RETURN_SYMBOL;
    /**
     * IT<p>
     * Rappresenta "\r"
     *
     * <p>
     * EN<p>
     * Represent "\r"
     */
    public final static String SLASH_R = "" + ETX + DC4 + DC3 + DC2 + RETURN_SYMBOL;
    /**
     * IT<p>
     * Rappresenta "\t"
     *
     * <p>
     * EN<p>
     * Represent "\t"
     */
    public final static String SLASH_T = "" + ETX + DC4 + DC3 + DC3 + RETURN_SYMBOL;
    /**
     * IT<p>
     * Definizione di tipo oggetto Stringa<p>
     * {@link #NAK NAK} +{@link #DC3 DC3}+{@link #DC2 DC2}+{@link #DC4 DC4}
     *
     * <p>
     * EN<p>
     * Represent String obj
     */
    public final static String DATA_STRING = "" + NAK + DC3 + DC2 + DC4;
    /**
     * IT<p>
     * Definizione di tipo oggetto Character<p>
     * {@link #NAK NAK} +{@link #DC3 DC3}+{@link #DC1 DC1}+{@link #DC4 DC4}
     * <p>
     * EN<p>
     * Represent Character obj
     */
    public final static String DATA_CHAR = "" + NAK + DC3 + DC1 + DC4;
    /**
     * IT<p>
     * Definizione di tipo oggetto Date<p>
     * {@link #NAK NAK} +{@link #DC4 DC4}+{@link #DC1 DC1}+{@link #DC4 DC4}
     * <p>
     * EN<p>
     * Represent Date obj
     */
    public final static String DATA_DATE = "" + NAK + DC4 + DC1 + DC4;
    /**
     * IT<p>
     * Definizione di tipo oggetto Long<p>
     * {@link #NAK NAK} +{@link #DC2 DC2}+{@link #DC2 DC2}+{@link #DC2 DC2}
     * <p>
     * EN<p>
     * Represent Long obj
     */
    public final static String DATA_LONG = "" + NAK + DC2 + DC2 + DC2;
    /**
     * IT<p>
     * Definizione di tipo oggetto Short<p>
     * {@link #NAK NAK} +{@link #DC1 DC1}+{@link #DC1 DC1}+{@link #DC1 DC1}
     * <p>
     * EN<p>
     * Represent Short obj
     */
    public final static String DATA_SHORT = "" + NAK + DC1 + DC1 + DC1;
    /**
     * IT<p>
     * Definizione di tipo oggetto Integer<p>
     * {@link #NAK NAK} +{@link #DC1 DC1}+{@link #NUL NUL}
     * <p>
     * EN<p>
     * Represent Integer obj
     */
    public final static String DATA_INTEGER = "" + NAK + DC1 + NUL;
    /**
     * IT<p>
     * Definizione di tipo oggetto Double<p>
     * {@link #NAK NAK} +{@link #DC2 DC2}+{@link #NUL NUL}
     * <p>
     * EN<p>
     * Represent Double obj
     */
    public final static String DATA_DOUBLE = "" + NAK + DC2 + NUL;
    /**
     * IT<p>
     * Definizione di tipo oggetto Boolean<p>
     * {@link #NAK NAK} +{@link #DC3 DC3}+{@link #NUL NUL}
     * <p>
     * EN<p>
     * Represent Boolean obj
     */
    public final static String DATA_BOOLEAN = "" + NAK + DC3 + NUL;
    /**
     * IT<p>
     * Definizione di tipo oggetto Float<p>
     * {@link #NAK NAK} +{@link #DC4 DC4}+{@link #NUL NUL}
     * <p>
     * EN<p>
     * Represent Float obj
     */
    public final static String DATA_FLOAT = "" + NAK + DC4 + NUL;
    /**
     * IT<p>
     * Definizione di tipo oggetto Object<p>
     * {@link #NAK NAK} +{@link #NAK NAK}+{@link #NUL NUL}
     * <p>
     * EN<p>
     * Represent generic Object
     */
    public final static String DATA_OBJECT = "" + NAK + NAK + NUL;

    public Symbol(int character) {
        c = (char) character;
    }

    public Symbol(char character) {
        c = character;
    }

    public char getSymbol() {
        return c;
    }
}
