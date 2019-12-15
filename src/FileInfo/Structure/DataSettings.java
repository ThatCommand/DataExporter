/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileInfo.Structure;

/**
 *
 * @author gabri
 */
public interface DataSettings {

    public String getHash();

    void gen_Hash();
    
    public boolean checkHash(String hash);

    public String getDataSettings();
}
