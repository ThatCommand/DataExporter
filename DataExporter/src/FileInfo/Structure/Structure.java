/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileInfo.Structure;

import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author gabri
 */
public class Structure implements ContainerObject {

    BooleanProperty building = new SimpleBooleanProperty(false);

    ArrayList<StructureObject> structure = new ArrayList<>();

    Separator sep;
    Symbol open_char;
    Symbol close_char;

    public Structure() {

    }

    public Structure buildStructure() {
        building.setValue(Boolean.TRUE);
        return this;
    }

    /**
     * Aggiunge un nuovo gruppo alla struttura
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

}
