package Lesson4;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class AppendFiles {
    public static void main(String[] args) throws IOException {
        String PATH = "./Texts";
        File path = new File(PATH);

        String[] filnames = path.list();

        ArrayList<FileInputStream> fl = new ArrayList<>();

        for (int i = 0; i < filnames.length; i++) {
            FileInputStream in = new FileInputStream(PATH + "/"+filnames[i]);
            fl.add(in);
        }

        File resFile = new File("result.txt");
        if( !resFile.exists())
            resFile.createNewFile();
        FileOutputStream out = new FileOutputStream(resFile);

        Enumeration<FileInputStream> something = Collections.enumeration(fl);
        SequenceInputStream in = new SequenceInputStream(something);
        int x;
        while ((x=in.read())!=-1){
            out.write((char)x);
        }

        in.close();
        out.close();

    }
}
