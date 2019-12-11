/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileInfo.Structure;

/**
 * IT<p>
 * Controlla
 * {@link Testo https://www.geeksforgeeks.org/check-for-balanced-parentheses-in-an-expression/}
 * <p>
 * EN<p>
 *
 * Check {@link Text https://www.geeksforgeeks.org/check-for-balanced-parentheses-in-an-expression/
 * }
 * @
 *
 *
 *
 * author ThatCommand
 */
public interface ContainerObject {

    /**
     * IT<p>
     * Ritorna il separatore della struttura/gruppo, il separatore viene
     * utilizzato per dividere i sottogruppi e valori
     * <p>
     * EN<p>
     * Return the separetor of the structure/group, the separator is used to
     * split sub-objects and values
     *
     * @param <T>
     * @param sep
     * @return
     */
    public <T> T defineSeparator(Separator sep);

    /**
     * IT<p>
     * Definisce il simbolo di apertura del gruppo
     * <p>
     * EN<p>
     * Define the symbol of the group opening
     *
     * @param <R>
     * @param sym
     * @return
     */
    public <R> R defineSymbolOpenGroup(Symbol sym);

    /**
     * IT<p>
     * Definisce il simbolo di chiusura del gruppo
     * <p>
     * EN<p>
     * Define the symbol of the group closing
     *
     * @param <R>
     * @param sym
     * @return
     */
    public <R> R defineSymbolCloseGroup(Symbol sym);

    /**
     * IT<p>
     * Definisce il simbolo di apertura e chiusura del gruppo
     * <p>
     * EN<p>
     * Define the symbol of the group opening and closing
     *
     * @param <R>
     * @param open
     * @param close
     * @return
     */
    public <R> R defineSymbolGroup(Symbol open, Symbol close);

}
