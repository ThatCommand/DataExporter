/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileInfo;

/**
 *
 * @author ThatCommand
 */
public class FileName {

    protected String NAME;
    protected String originalNAME;

    public FileName(String name) {
        NAME = name;
        originalNAME = name;
        init();
    }

    public final void init() {
        if (NAME != null) {
            NAME = NAME.replaceAll("\\*", "").replaceAll("\"", "").replaceAll("/", "").replaceAll("\\\\", "")
                    .replaceAll("\\[", "").replaceAll("\\]", "")
                    .replaceAll(":", "").replaceAll(";", "").replaceAll("|", "").replaceAll(",", "");
            if (NAME.trim().isEmpty()) {
                NAME = null;
            } else if (NAME.contains(".")) {
                NAME = NAME.substring(0, NAME.lastIndexOf("."));
            }
        }
    }

    public boolean isValid() {
        return NAME != null;
    }

    public String getNAME() {
        return NAME;
    }

}
