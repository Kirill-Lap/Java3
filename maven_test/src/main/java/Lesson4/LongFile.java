package Lesson4;

import java.io.*;
import java.util.ArrayList;
/**Вариант 1 - печатает по 20 строк текста. Проблема, что символ \r, если он есть, заменяется на \n*/
public class LongFile {
    public static void main(String[] args) throws IOException {
        BufferedReader br = null;
        FileReader fr = null;

        fr = new FileReader("install.txt.en");

        br = new BufferedReader(fr);

        long t = System.currentTimeMillis();

        String currStr;
        ArrayList<String> page = new ArrayList<>();
        int l = 0;
        while ((currStr = br.readLine()) != null) {
            if (l < 20) {
                page.add(currStr);
                page.add("\n");
                l++;
            } else {
                StringBuilder builder = new StringBuilder();
                for (String s : page) {
                    builder.append(s);
                }
                System.out.println(builder.toString());
                page.clear();
                l = 0;
            }
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        if (!page.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (String s : page) {
                builder.append(s);
            }
            System.out.println(builder.toString());
        }


        System.out.println(System.currentTimeMillis()-t);
        br.close();
        fr.close();
    }
}
