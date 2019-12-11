/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import FileInfo.Encoding;
import FileInfo.Extension;
import FileInfo.FileName;
import FileInfo.Structure.ContainerGroup;
import FileInfo.Structure.DataHolder;
import FileInfo.Structure.Separator;
import FileInfo.Structure.Symbol;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.management.openmbean.InvalidKeyException;
import reader.Reader;
import writer.Writer;

/**
 *
 * @author gabri
 */
public class RunTests {
    
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    
    public void encrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }
    
    public void decrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }
    
    public class CryptoException extends Exception {
        
        public CryptoException() {
        }
        
        public CryptoException(String message, Throwable throwable) {
            super(message, throwable);
        }
    }
    
    public void doCrypto(int cipherMode, String key, File inputFile,
            File outputFile) throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);
            
            FileOutputStream outputStream;
            try (FileInputStream inputStream = new FileInputStream(inputFile)) {
                byte[] inputBytes = new byte[(int) inputFile.length()];
                inputStream.read(inputBytes);
                byte[] outputBytes = cipher.doFinal(inputBytes);
                outputStream = new FileOutputStream(outputFile);
                outputStream.write(outputBytes);
            }
            outputStream.close();
            
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        } catch (java.security.InvalidKeyException ex) {
            Logger.getLogger(RunTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        DataHolder dh = new DataHolder();
        dh
                .setVariableName("TestNomeVar")
                .setAssignSymbol(new Symbol(':'))
                .setSeparator(new Separator(';'))
                .setData("testo 1", 2143.56, 3575, 45f, true, (short) 120000, (long) 300);
        DataHolder dh2 = new DataHolder();
        dh2
                .setVariableName("TestNomeVar2")
                .setAssignSymbol(new Symbol('='))
                .setSeparator(new Separator('%'))
                .setData("testo 2");
        DataHolder dh3 = new DataHolder();
        dh3
                .setVariableName("TestNomeVar3")
                .setAssignSymbol(new Symbol('§'))
                .setSeparator(new Separator('$'))
                .setData("testo 3");
        DataHolder dh4 = new DataHolder();
        dh4
                .setVariableName("TestNomeVar4")
                .setAssignSymbol(new Symbol('§'))
                .setSeparator(new Separator('$'))
                .setData("testo 4");
        DataHolder dh5 = new DataHolder();
        dh5
                .setVariableName("TestNomeVar5")
                .setAssignSymbol(new Symbol('§'))
                .setSeparator(new Separator('$'))
                .setData("testo 5");
        ContainerGroup cg2 = new ContainerGroup();
        cg2
                .setGroupName("TestNomeGruppo2")
                .addStructureObject(dh3)
                .addStructureObject(dh5);
        ContainerGroup cg = new ContainerGroup();
        cg
                .setGroupName("Test\nNome\nGruppo")
                .addStructureObject(dh)
                .addStructureObject(dh2)
                .addStructureObject(cg2)
                .addStructureObject(dh4);
        Writer w = new Writer();
        File f
                = w
                .setEncoding(Encoding.ENCODINGS.UTF_16)
                .setFilename(new FileName("esempio"))
                .setText(cg.getData())
                .setExtension(new Extension("esempio di scrittura file"))
                .setDestination("C:\\Users\\gabri\\Desktop\\").write();
//            RunTests rt=new RunTests();
//            rt.encrypt("BAZZUORD90123456", f,new File(f.getAbsolutePath()+".encp"));
//            rt.decrypt("BAZZUORD90123456", new File(f.getAbsolutePath()+".encp"),new File(f.getAbsolutePath()+".decp"));

        Reader r = new Reader();
        r
                .setEncoding(Encoding.ENCODINGS.UTF_16).
                setFilename(new FileName("esempio"))
                .setExtension(new Extension("esempio di scrittura file"))
                .setDestination("C:\\Users\\gabri\\Desktop\\").read();
        
        ArrayList<String> asr = cg.parseData(r.getText());
        asr.forEach(a -> System.out.println(a));
        
//        DataHolder nest = new DataHolder();
//        nest
//                .setAssignSymbol(new Symbol(':'))
//                .setSeparator(new Separator(';'))
//                .setPattern(dh.getPattern());
//        
//        nest.readData(r.getText());
        
        System.out.println("PATTERN GROUP:\t" + cg.getPattern().toString());
//        System.out.println("DH:\t" + dh.getData());
//        System.out.println("NEST:\t" + nest.getData());
//        System.out.println(nest.toString());
    }
    
}
