package Lesson4;

/**Вариант 2 со статическими синхронизированными методами*/
class ThreadsOrder{

    private static boolean aIsPrinted = false;
    private static boolean bIsPrinted = true;
    private static boolean cIsPrinted = true;

    static synchronized void checkAisPrinted(){
        while(!(aIsPrinted && cIsPrinted)){
            try {
                ThreadsOrder.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        aIsPrinted=true;
        bIsPrinted=true;
        cIsPrinted=false;
        System.out.print("B");
        ThreadsOrder.class.notifyAll();
    }

    static synchronized void checkBisPrinted(){
        while(!(bIsPrinted && aIsPrinted)){
            try {
                ThreadsOrder.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        aIsPrinted=false;
        bIsPrinted=true;
        cIsPrinted=true;
        ThreadsOrder.class.notifyAll();
        System.out.print("C");
    }

    static synchronized void checkCisPrinted(){
        while(!(cIsPrinted && bIsPrinted)){
            try {
                ThreadsOrder.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        aIsPrinted=true;
        bIsPrinted=false;
        cIsPrinted=true;
        System.out.print("A");
        ThreadsOrder.class.notifyAll();
    }



}
public class ThreadTask1 {
    public static void main(String[] args) {

        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    ThreadsOrder.checkCisPrinted();
//                    System.out.print("A");

                }
            }
        });
        Thread th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    ThreadsOrder.checkAisPrinted();
//                    System.out.print("B");
                }
            }
        });
        Thread th3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    ThreadsOrder.checkBisPrinted();
//                    System.out.print("C");
                }
            }
        });

        th1.start();
        th2.start();
        th3.start();

    }

}
