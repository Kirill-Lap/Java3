package Lesson7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class Logistic {

    public static void main(String[] args) {
        final int SHIPCOUNT = 3;
        Ship[] fleet = new Ship[SHIPCOUNT];
        Route silkPath  = new Route (new SeaRoute(300), new Channel(50,2), new SeaRoute(500));
        ArrayList<Port> shanghaiBay = new ArrayList<>();
        shanghaiBay.add( new Port(new Clothes(), 700));
        shanghaiBay.add( new Port(new Food(), 900));
        shanghaiBay.add( new Port(new Fuel(), 500));
        CountDownLatch cdl = new CountDownLatch(SHIPCOUNT);
        MegaPort rotterdam = new MegaPort();

        for (int i = 0; i < fleet.length; i++) {
            fleet[i]= new Ship(500, silkPath, shanghaiBay, rotterdam, cdl);
            new Thread(fleet[i]).start();
        }

        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        rotterdam.info();

    }
}



class Ship implements Runnable {
    private int payload;
    private int currPayload;
    private static int shipNumber=0;
    private Cargo cargo = null;
    private String name;
    private Route route;
    private MegaPort unloadPort;
    ArrayList<Port> portOfOrigin;
    CountDownLatch cdl;


    public Ship(int payload, Route route, ArrayList<Port> portOfOrigin, MegaPort unloadPort, CountDownLatch cdl ) {
        this.payload = payload;
        name = "Корабль №" + (shipNumber+1);
        shipNumber++;
        this.currPayload=0;
        this.route = route;
        this.portOfOrigin = portOfOrigin;
        this.unloadPort = unloadPort;
        this.cdl = cdl;
    }

    public boolean loadCargo(Cargo cargo, int amount ){
        if (this.cargo != null) {
            if (this.cargo.getClass() == cargo.getClass() && (currPayload+amount) <=payload){
                currPayload+=amount;
                System.out.println("В " + name + " погружено " + amount + " товаара " + cargo.getName() + " загрузка " + currPayload);
            }
        } else {
            this.cargo = cargo;
            currPayload = amount;
        }
        return currPayload == payload;
    }

    public int unloadCargo(int amount){
        int unloadedAmount;
        if (amount < currPayload) {
            currPayload-=amount;
            System.out.println("Из " + name + " выгружено " + amount + " товаара " + cargo.getName());
            unloadedAmount =  amount;
        } else {
            System.out.println("Из " + name + " выгружено " + currPayload + " товаара " + cargo.getName());
            unloadedAmount = currPayload;
            currPayload = 0;
            cargo = null;
        }

        return unloadedAmount;
    }

    public String getName() {
        return name;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public int getCurrPayload() {
        return currPayload;
    }

    private int amountInPorts() {
        int res=0;
        for (int i = 0; i < portOfOrigin.size(); i++) {
            res +=portOfOrigin.get(i).getCargoAmount();
            portOfOrigin.get(i).info();
        }
        return res;
    }

    private Port getNotEmptyFreePort(){
        Port portToLoad = null;
        for (int i = 0; i < portOfOrigin.size(); i++) {
            if (portOfOrigin.get(i).getCargoAmount() > 0) {
                if (!portOfOrigin.get(i).isBusy()){
                    return portOfOrigin.get(i);
                }
                portToLoad = portOfOrigin.get(i);
            }
        }
        return portToLoad;
    }

    @Override
    public void run() {

        while (amountInPorts()>0) {
            for (int i = 0; i < route.getRoute().size(); i++) {
                route.getRoute().get(route.getRoute().size()-i-1).go(this);
            }
            Port freePort = getNotEmptyFreePort();
            if (freePort != null) {
                freePort.loadShip(this);
            } else {
                break;
            }
            for (int i = 0; i < route.getRoute().size(); i++) {
                route.getRoute().get(i).go(this);
            }
            unloadPort.unloadShip(this);
        }
        cdl.countDown();
    }
}

abstract class Cargo{
    private String name;

    public Cargo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Clothes extends Cargo{
    public Clothes() {
        super("Одежда") ;
    }
}

class Food extends Cargo {
    public Food() {
        super("Еда");
    }
}

class Fuel extends Cargo {
    public Fuel() {
        super("Топливо");
    }
}


class Route {
    private ArrayList<RouteStages> route;

    public ArrayList<RouteStages> getRoute() {
        return route;
    }

    public Route(RouteStages... route) {
        this.route = new ArrayList<>(Arrays.asList(route));
    }
}

class Port  {
    private static int portNumber = 0;
    private Cargo cargo;
    private int cargoAmount;
    private String name;
    private final long LOADINGTIME=1000;
    private final int LOADINGAMOUNT = 100;
    private AtomicBoolean isBusy;

    public Port(Cargo cargo, int cargoAmount) {
        name = "Порт №" + (portNumber+1);
        portNumber++;
        this.cargo = cargo;
        this.cargoAmount = cargoAmount;
        isBusy = new AtomicBoolean(false);
    }

    public int getCargoAmount() {
        return cargoAmount;
    }

    public boolean isBusy() {
        return isBusy.get();
    }

    public void info(){
        System.out.println("\t в" + name + " груз " + cargo.getName() + " " + cargoAmount);
    }

    public synchronized void loadShip(Ship ship) {
        boolean isFull=false;
        isBusy.set(true);
        while (!isFull && cargoAmount>0) {
            try {
                Thread.sleep(LOADINGTIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isFull = (cargoAmount >= LOADINGAMOUNT) ? ship.loadCargo(cargo, LOADINGAMOUNT) : ship.loadCargo(cargo, cargoAmount);
            cargoAmount-=LOADINGAMOUNT;
        }
        isBusy.set(false);
    }

}

class MegaPort {
    private ConcurrentHashMap<Cargo,Integer> cargoStorage;
    private final long UNLOADINGTIME=500;
    private final int UNLOADINGAMOUNT = 100;
    private String description;

    public MegaPort() {
        cargoStorage = new ConcurrentHashMap<>();
        description = "Большой разгрузочный порт";
    }

    public synchronized void unloadShip(Ship ship) {
        int leftInShip = ship.getCurrPayload();
        System.out.println(ship.getName()+ " прибыл на разгрузку");
        while (leftInShip >0) {
            try {
                Thread.sleep(UNLOADINGTIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (cargoStorage.containsKey(ship.getCargo())){
                cargoStorage.put (ship.getCargo(), cargoStorage.get(ship.getCargo()) + ship.unloadCargo(UNLOADINGAMOUNT));
            } else {
                cargoStorage.put (ship.getCargo(), ship.unloadCargo(UNLOADINGAMOUNT));
            }
            leftInShip = ship.getCurrPayload();
        }
        System.out.println(ship.getName()+ " закончил разгрузку в " + description);
    }

    public void info(){
        for (Cargo key:  cargoStorage.keySet()) {
            System.out.println(key.getName() + " " + cargoStorage.get(key));
        }
    }


}
abstract class RouteStages{
    protected String description;
    protected int length;
    protected long timeToCross;
    public abstract void go(Ship ship);
}



class SeaRoute extends RouteStages {

    public SeaRoute(int length) {
        this.length = length;
        this.timeToCross = (long) length*10;
        this.description = "Морской путь длинной " + length +  " миль";
    }

    @Override
    public void go(Ship ship) {
        try {
            System.out.println(ship.getName() + " начал " + description);
            Thread.sleep(timeToCross);
            System.out.println(ship.getName() + " закончил " + description);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Channel extends RouteStages {
    private int capacity;
    private Semaphore semaphore;
    public Channel(int length, int capacity) {
        this.length = length;
        this.timeToCross = (long) length*50;
        this.description = "Канал длинной " + length +  " миль";
        this.capacity = capacity;
        semaphore = new Semaphore(capacity);
    }

    @Override
    public void go(Ship ship) {

        try {
            System.out.println(ship.getName() + " подошёл к " + description);
            semaphore.acquire();
            System.out.println(ship.getName() + " начал " + description);
            Thread.sleep(timeToCross);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(ship.getName() + " закончил " + description);
            semaphore.release();
        }

    }
}

