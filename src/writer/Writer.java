/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package writer;

import FileInfo.Encoding.ENCODINGS;
import FileInfo.Extension;
import FileInfo.FileName;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author gabri
 */
public class Writer {

    String destination;
    Extension e;
    FileName f;
    ENCODINGS enc;

    File selectedFile = null;
    String text;

    boolean error = false;

    public Writer() {

    }

    public Writer setDestination(String ds) {
        destination = ds;
        return this;
    }

    public Writer setEncoding(ENCODINGS en) {
        enc = en;
        return this;
    }

    public Writer setFilename(FileName fn) {
        f = fn;
        return this;
    }

    public Writer setFilename(String fn) {
        f = new FileName(fn);
        return this;
    }

    public Writer setExtension(Extension ex) {
        e = ex;
        return this;
    }

    public Writer setText(String text) {
        this.text = text;
        return this;
    }

    public File write() {
        PrintWriter writer;
        if (destination != null) {
            selectedFile = new File(destination + "/" + (f.isValid() ? f.getNAME() : ErrorThrown("Invalid name format")) + e.getExtension());
            try {
                if (!error) {
                    writer = new PrintWriter(selectedFile.getAbsoluteFile(), enc.getData());
                    writer.print(text);
                    writer.close();
                }
            } catch (FileNotFoundException | UnsupportedEncodingException ex) {
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
