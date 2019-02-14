package Lesson4;

/**Вариант 1 - с одним синхронизированным методом и енумом*/

enum Letter {
    A,B,C
}

class WaltzMethod {
    private int counter = 0;

    public synchronized void printABC(Letter l){
        while ( l.ordinal() != counter%3) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.print(l);
        counter++;
        notifyAll();
    }
}


class T1 extends Thread {
    private Letter l;
    private WaltzMethod wm;

    public T1(Letter l, WaltzMethod wm) {
        this.l = l;
        this.wm = wm;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            wm.printABC(this.l);
        }
    }
}


public class ThreadTask {

    public static void main(String[] args) {

        WaltzMethod wm = new WaltzMethod();

        T1 t1 = new T1(Letter.A, wm);
        T1 t2 = new T1(Letter.B, wm);
        T1 t3 = new T1(Letter.C, wm);
        t1.start();
        t2.start();
        t3.start();

    }
}
