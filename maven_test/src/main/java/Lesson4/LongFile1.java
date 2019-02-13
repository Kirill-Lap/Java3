package Lesson4;

import java.io.*;
/** Второй вариант - он более правильный с учётом того, что здесь распознаются \n и \r*/

public class LongFile1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = null;
        FileReader fr = null;

        fr = new FileReader("install.txt.en");

        br = new BufferedReader(fr);

        long t = System.currentTimeMillis();

        int x;
        char[] buf = new char[1800];
        while ((x=br.read(buf))!=-1)  {
            if (x==1800)
                System.out.print(buf);
            else {
                for (int i=0; i<x; i++) {
                    System.out.print(buf[i]);
                }
            }
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

        }

        System.out.println(System.currentTimeMillis()-t);
        br.close();
        fr.close();
    }

}
