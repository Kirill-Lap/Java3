package Lesson1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.*;

public class MainClass {
    public static void main(String[] args) {

        String[] arr = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
        ArrMethods<String> am = new ArrMethods<>();
        arr = am.swapMethod(arr,2,3);

        System.out.println(am.covertArray(arr));



        Orange[] oranges1 = new Orange[10];
        Orange[] oranges2 = new Orange[8];
        Apple[] apples1 = new Apple[12];
        Apple[] apples2 = new Apple[15];

        for (int i=0; i<oranges1.length; i++) oranges1[i] = new Orange();
        for (int i=0; i<oranges2.length; i++) oranges2[i] = new Orange();
        for (int i=0; i<apples1.length; i++) apples1[i] = new Apple();
        for (int i=0; i<apples2.length; i++) apples2[i] = new Apple();

        Box<Orange>[] boxes_or = new Box[2]; /** Здесь ругается, что Unchecked assignment?!?!?!*/
        boxes_or[0] = new Box<>();
        for( Orange o: oranges1) boxes_or[0].addToTheBox(o);
//        boxes_or[0].addToTheBox(apples1[0]);  // не даёт подать на вход яблоки вместо апельсинов

        boxes_or[1] = new Box<>(oranges2);

        Box<Apple>[] boxes_ap = new Box[2];

        boxes_ap[0] = new Box<>(apples1);
        boxes_ap[1] = new Box<>(apples2);


        for (Box b:boxes_or) {
            System.out.println(b.getWeight());
        }
        for (Box b:boxes_ap) {
            System.out.println(b.getWeight());
        }

        System.out.println(boxes_or[0].compareBoxes(boxes_or[1]));
        System.out.println(boxes_or[0].compareBoxes(boxes_ap[1])); //сравнивает любые коробки*/
        boxes_or[0].transferToAnotherBox(boxes_or[1]);              //перекладывает только однотипные*/
        boxes_ap[0].transferToAnotherBox(boxes_ap[1]);

        for (Box b:boxes_or) {
            System.out.println(b.getWeight());
        }
        for (Box b:boxes_ap) {
            System.out.println(b.getWeight());
        }


//        boxes_or[1].transferToAnotherBox(boxes_ap[1]); // выдает ошибку


    }



}

class ArrMethods<T> {

    public T[] swapMethod(T[] arr, int i, int j ){
//        List<T> arrList = new ArrayList<T>(Arrays.asList(arr));
        System.out.println(Arrays.asList(arr));
        Collections.swap(Arrays.asList(arr), i, j);
        return arr;
    }

    public ArrayList<T> covertArray(T[] arr) {
        ArrayList<T> res = new ArrayList<T>(Arrays.asList(arr));
        return res;
    }
}

abstract class Fruits {
    private float weight;

    public Fruits(float weight) {
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }

}

class Orange extends Fruits {

    public Orange() {
        super(1.5f);
    }

}

class Apple extends Fruits {

    public Apple() {
        super(1.0f);
    }

}


class Box <T extends Fruits> {
    private Collection<T> box;

    public Box() {
        this.box = new ArrayList<>();
    }

    public Box(T[] fruits) {
        this.box = new ArrayList<>();
        this.box.addAll(Arrays.asList(fruits));
    }


    public float getWeight(){
        float res=0.0f;
        for (T t: box)
            res+=t.getWeight();

        return res;
    }


    public  void addToTheBox(T... fruits) {
        this.box.addAll(Arrays.asList(fruits));
    }

    public void addToTheBox(Collection<T> fruits) {
        this.box.addAll(fruits);
    }

    public boolean compareBoxes(Box<?> toCompare) {
        return this.getWeight() == toCompare.getWeight();
    }

    public void transferToAnotherBox(Box<T> anotherBox) {
        anotherBox.addToTheBox(this.box);
        this.box.clear();
    }


}
