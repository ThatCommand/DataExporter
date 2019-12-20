/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reader;

import FileInfo.Encoding;
import FileInfo.Extension;
import FileInfo.FileName;
import FileInfo.Structure.Symbol;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author ThatCommand
 */
public class Reader {

    String destination;
    Extension e;
    FileName f;
    Encoding.ENCODINGS enc;

    File selectedFile = null;
    String text = "";
    StringBuilder defs = new StringBuilder();
    StringBuilder file = new StringBuilder();
    boolean error = false;

    public Reader() {

    }

    public Reader setDestination(String ds) {
        destination = ds;
        return this;
    }

    public Reader setEncoding(Encoding.ENCODINGS en) {
        enc = en;
        return this;
    }

    public Reader setFilename(FileName fn) {
        f = fn;
        return this;
    }

    public Reader setFilename(String fn) {
        f = new FileName(fn);
        return this;
    }

    public Reader setExtension(Extension ex) {
        e = ex;
        return this;
    }

    public String getText() {
        return text;
    }

    public String getFile() {
        return file.toString().replaceAll("\n|\t|\r", "");
    }

    public String getDefs() {
        return defs.toString();
    }

    public File read() {
        BufferedReader fr;
        file = new StringBuilder();
        defs = new StringBuilder();
        text = "";
        if (destination != null) {
            selectedFile = new File(destination + "/" + (f.isValid() ? f.getNAME() : ErrorThrown("Invalid name format")) + e.getExtension());
            try {
                fr = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile), enc.getData()));
                String line;
                boolean is_definition = false;
                while ((line = fr.readLine()) != null) {
                    if (!line.trim().replaceAll("\n|\r|\t", "").equals("")) {
                        if (!is_definition) {
                            if (!line.equals(Symbol.DEFINITION_START.replaceAll("\n", ""))) {
                                file.append(line).append("\n");
                            } else {
                                defs.append(line).append("\n");
                                is_definition = true;
                            }
                        } else {
                            defs.append(line).append("\n");
                        }
                    }
                    text += line;
                }

            } catch (FileNotFoundException | UnsupportedEncodingException ex) {
                ErrorThrown(ex.getMessage());
            } catch (IOException ex) {
                ErrorThrown(ex.getMessage());
            }
        }
        return selectedFile;
    }

    public String ErrorThrown(String s) {
        error = true;
        return s;
    }
}
