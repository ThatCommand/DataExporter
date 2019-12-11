/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileInfo.Structure;

import java.util.regex.Pattern;

/**
 *
 * @author gabri
 */
public interface StructureObject {

    /**
     * Restituisce la stringa formata dal template in cui si vanno a rimpiazzare
     * i valori
     *
     *
     * Return the template string completed with the values
     *
     * @return
     */
    public String getData();

    /**
     * Restituisce la stringa del template dell'oggetto
     *
     * Return the object's template string
     *
     * @return
     */
    public String getStringTemplate();

    /**
     * Genera la stringa del template dell'oggetto
     *
     * Generate the template string of the object
     */
    public void genStringTemplate();

    /**
     * Se si tratta di un oggetto di tipo container (che contiene altri
     * sott'oggetti) allora deve ritornare TRUE
     *
     * If it's a container object (which contains other sub-objects) then it
     * needs to return TRUE
     *
     * @return
     */
    public boolean isContainer();

    /**
     * Se si tratta di un oggetto di tipo DataHolder (Che indica un valore con
     * il rispettivo nome dell' oggetto) allora deve ritornare TRUE
     *
     * If it's a DataHolder object (which contains variables defined by a name)
     * then it needs to return TRUE
     *
     * @return
     */
    public boolean isDataHolder();

    /**
     * Se si tratta di un carattere speciale o un insieme di tali allora dever
     * ritornare TRUE
     *
     * @return
     */
    public boolean isStructureSpecialChar();

    /**
     * Aggiungi un sott'oggetto
     *
     * @param so
     * @return
     */
    public StructureObject addStructureObject(StructureObject so);

    /**
     * Rimuovi un sott'oggetto
     *
     * @param so
     * @return
     */
    public StructureObject removeStructureObject(StructureObject so);

    /**
     * Restituisce il pattern secondo il quale è costruito il blocco.
     *
     * @return
     */
    public Pattern getPattern();

    /**
     * Imposta il pattern del blocco
     *
     * @param p
     */
    public void setPattern(Pattern p);

    /**
     * Serve per le classi "sovrane" (quelle che chiamano/usano l'oggetto) per
     * sapere se la struttura dell'oggetto è stata rispettata.
     *
     * @return
     */
    public boolean isAcceptable();
}
