package Lesson4;

import java.io.*;

public class ShortFile {

    public static void main(String[] args) throws IOException {
        try (FileInputStream in = new FileInputStream("license.txt")) {

            byte[] arr = new byte[64];
            int x;
            while ((x = in.read(arr)) != -1) {
                System.out.print(new String(arr, 0, x));
            }
        }
    }
}
