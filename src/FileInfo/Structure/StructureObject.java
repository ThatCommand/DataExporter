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
public interface StructureObject {

    /**
     * IT<p>
     * Restituisce la stringa formata dal template in cui si vanno a rimpiazzare
     * i valori
     *
     * <p>
     * EN<p>
     * Return the template string completed with the values
     *
     * @return
     */
    public String getData();

    /**
     * IT<p>
     * Restituisce la stringa del template dell'oggetto
     * <p>
     * EN<p>
     * Return the object's template string
     *
     * @return
     */
    public String getStringTemplate();

    /**
     * IT<p>
     * Genera la stringa del template dell'oggetto
     * <p>
     * EN<p>
     * Generate the template string of the object
     */
    public void genStringTemplate();

    /**
     * IT<p>
     * Se si tratta di un oggetto di tipo container (che contiene altri
     * sott'oggetti) allora deve ritornare TRUE
     * <p>
     * EN<p>
     * If it's a container object (which contains other sub-objects) then it
     * needs to return TRUE
     *
     * @return
     */
    public boolean isContainer();

    /**
     * IT<p>
     * Se si tratta di un oggetto di tipo DataHolder (Che indica un valore con
     * il rispettivo nome dell' oggetto) allora deve ritornare TRUE
     * <p>
     * EN<p>
     * If it's a DataHolder object (which contains variables defined by a name)
     * then it needs to return TRUE
     *
     * @return
     */
    public boolean isDataHolder();

    /**
     * IT<p>
     * Se si tratta di un carattere speciale o un insieme di tali allora dever
     * ritornare TRUE
     * <p>
     * EN<p>
     * If it's a special char or a group of chars then it needs to return TRUE
     *
     * @return
     */
    public boolean isStructureSpecialChar();

    /**
     * IT<p>
     * Aggiungi un sott'oggetto
     * <p>
     * EN<p>
     * Add a sub-object
     *
     * @param so the StructureObject to add
     * @return
     */
    public StructureObject addStructureObject(StructureObject so);

    /**
     * IT<p>
     * Rimuovi un sott'oggetto
     * <p>
     * EN<p>
     * Remove a sub-object
     *
     * @param so
     * @return
     */
    public StructureObject removeStructureObject(StructureObject so);

    /**
     * IT<p>
     * Restituisce il pattern secondo il quale è costruito il blocco.
     * <p>
     * EN<p>
     * Return the pattern on which the block is built on.
     *
     * @return
     */
    public Pattern getPattern();

    /**
     * IT<p>
     * Imposta il pattern del blocco
     * <p>
     * EN<p>
     * Set the block's pattern
     *
     * @param p
     */
    public void setPattern(Pattern p);

    /**
     * IT<p>
     * Serve per le classi "sovrane" (quelle che chiamano/usano l'oggetto) per
     * sapere se la struttura dell'oggetto è stata rispettata.
     * <p>
     * EN<p>
     * This method returns TRUE if the structure of the block object it's
     * correct (matches the pattern,completation of the object-variables,...)
     *
     * @return
     */
    public boolean isAcceptable();
}
