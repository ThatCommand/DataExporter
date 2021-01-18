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
import FileInfo.Structure.ContainerObject;
import FileInfo.Structure.DataHolder;
import FileInfo.Structure.Separator;
import FileInfo.Structure.Structure;
import FileInfo.Structure.Symbol;
import exceptions.IllegalCharacterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import reader.Reader;
import writer.Writer;

/**
 *
 * @author ThatCommand
 */
class RunTests {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IllegalCharacterException, Exception {
        // TODO code application logic here
        DataHolder dh = new DataHolder();
        dh
                .setVariableName("ATestNomeVar")
                .setAssignSymbol(new Symbol(':'))
                .setSeparator(new Separator(';'))
                .setData("testo; 1", 2143.56, 3575, 45f, true, (short) 120000, (long) 999999999);
        DataHolder dh2 = new DataHolder();
        dh2
                .setVariableName("BTestNomeVar2")
                .setAssignSymbol(new Symbol('='))
                //                .setSeparator(new Separator('%'))
                .setData("{CLOSE_SYS}");
        DataHolder dh3 = new DataHolder();
        dh3
                .setVariableName("CTestNomeVar3")
                .setAssignSymbol(new Symbol('§'))
                //                .setSeparator(new Separator('$'))
                .setData("testo 3");
        DataHolder dh4 = new DataHolder();
        dh4
                .setVariableName("DTestNomeVar4")
                .setAssignSymbol(new Symbol('§'))
                //                .setSeparator(new Separator('$'))
                .setData("testo 4");
        DataHolder dh5 = new DataHolder();
        dh5
                .setVariableName("ETestNomeVar5")
                .setAssignSymbol(new Symbol('§'))
                //                .setSeparator(new Separator('$'))
                .setData("testo 5");
        ContainerGroup cg2 = new ContainerGroup();
        cg2
                .setGroupName("FTestNomeGruppo2")
                .addStructureObject(dh3)
                .addStructureObject(dh5);
        ContainerGroup cg = new ContainerGroup();
        cg
                .setGroupName("GTest\nNome\nGruppo")
                .addStructureObject(dh)
                .addStructureObject(dh2)
                .addStructureObject(cg2);

        Structure s1 = new Structure();
        s1.addStructureObject(cg).addStructureObject(dh4);

        Reader r = new Reader();
        r
                .setEncoding(Encoding.ENCODINGS.UTF_16)
                .setFilename(new FileName("Example"))
                .setExtension(new Extension("Writing exemple file"))
                .setDestination("C:\\Users\\public\\Desktop\\").read();

//        String defs = r.getDefs();
//        String file = r.getFile();
//        System.out.println(file);
//        System.out.println(defs);
        Structure s_ = new Structure();

        s_.read(r);
//        ArrayList<String> asr = cg.parseData(r.getText());
//        asr.forEach(a -> System.out.println(a));

//        DataHolder nest = new DataHolder();
//        nest
//                .setAssignSymbol(new Symbol(':'))
//                .setSeparator(new Separator(';'))
//                .setPattern(dh.getPattern());
//        
//        nest.readData(r.getText());
        PrintStructure(s_);
//        System.out.println("PATTERN GROUP:\t" + cg.getPattern().toString());
//        System.out.println("DH:\t" + dh.getData());
//        System.out.println("NEST:\t" + nest.getData());
//        System.out.println(nest.toString());
    }
    static int tabs = 0;
    static Structure s;

    public static void PrintStructure(ContainerObject so) {
        so.getObjects().forEach(o -> {
            for (int i = 0; i < tabs; i++) {
                System.err.print("\t");
            }
            System.err.println(o.toString());
            if (o instanceof ContainerObject) {
                tabs++;
                PrintStructure((ContainerObject) o);
            }
        });
        tabs--;
    }

}
