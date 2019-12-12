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
public class Extension {

    public final String FULL_NAME;

    public Extension(String full_name) {
        FULL_NAME = full_name;
    }

    public String getExtension() {
        String based = FULL_NAME.replaceAll("_", " ").replaceAll("\\\\", " ").replaceAll("/", " ");
        String[] splitted = based.split(" ");
        StringBuilder sb = new StringBuilder(".");
        if (splitted.length >= 3) {
            for (int i = 0; i < splitted.length; i++) {
                if (splitted[i].length() > 3) {
                    char c = splitted[i].charAt(0);
                    sb.append(c);
                }
            }
        } else if (splitted.length == 2 && splitted[0].length() >= 2) {
            char c = splitted[0].charAt(0);
            char c1 = splitted[0].charAt(1);
            char c2 = splitted[1].charAt(0);
            sb.append(c).append(c1).append(c2);
        } else if (splitted.length == 1 && splitted[0].length() >= 3) {
            char c = splitted[0].charAt(0);
            char c1 = splitted[0].charAt(1);
            char c2 = splitted[0].charAt(2);
            sb.append(c).append(c1).append(c2);
        } else if (splitted.length < 1) {
            sb.append("base");
        }

        return sb.length() > 1 ? sb.toString() : null;
    }
}
