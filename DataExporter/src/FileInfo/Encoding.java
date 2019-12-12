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
public class Encoding {

    public static enum ENCODINGS {
        UTF_8,UTF_16,UTF_32;
        
        public String getData(){
            String name=this.name().replaceAll("_", "-");
            return name;
        }
    };
}
