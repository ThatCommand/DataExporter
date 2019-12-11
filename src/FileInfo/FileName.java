/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileInfo;

/**
 *
 * @author gabri
 */
public class FileName {

    protected String NAME;

    public FileName(String name) {
        NAME = name;
        init();
    }

    public final void init() {
        if (NAME != null) {
            NAME = NAME.replaceAll("\\*", "").replaceAll("\\.", "").replaceAll("\"", "").replaceAll("/", "").replaceAll("\\\\", "")
                    .replaceAll("\\[", "").replaceAll("\\]", "")
                    .replaceAll(":", "").replaceAll(";", "").replaceAll("|", "").replaceAll(",", "");
            if (NAME.trim().isEmpty()) {
                NAME = null;
            }
        }
    }
    
    public boolean isValid(){
        return NAME!=null;
    }

    public String getNAME() {
        return NAME;
    }

    
    
}
