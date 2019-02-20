package Lesson6;

public class TestMain {

    public static void main(String[] args) {
//        Integer[] data = {1,2,3,4,5,6,7,8,9};
        Integer[] data = {2,3,5,6,7,8,9};

        ArrayMethods arrMeth = new ArrayMethods();
        try {
            int[] res = arrMeth.method1(data, 4);
            for (int i : res) {
                System.out.println(i);

            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        Integer[] data1 = {1,1,1,1,4,4,4,411};
        System.out.println(arrMeth.method2(data1, 1,4));
    }


}

