package Lesson6;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayMethods {
    public int[] method1(Integer[] arr, Integer marker) throws RuntimeException {
        ArrayList<Integer> al = new ArrayList<>();
        al.addAll(Arrays.asList(arr));
        int[] res;
        if (al.contains(marker)) {
            int index = al.lastIndexOf(marker);

            res = new int[al.size()-index-1];
            int j=0;
            for (int i = index + 1; i < al.size(); i++) {
                res[j]=al.get(i);
                j++;
            }
        } else {
            throw new RuntimeException("No " + marker + " in array");
        }
        return res;
    }

    public boolean method2(Integer[] data, Integer marker1, Integer marker2) {
        return Arrays.asList(data).contains(marker1) && Arrays.asList(data).contains(marker2);
    }

}
