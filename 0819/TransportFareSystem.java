abstract class Transport {
    String routeName;

    public Transport(String routeName) {
        this.routeName = routeName;
    }

    public abstract int calculateFare(int distance);
}

class Bus extends Transport {
    public Bus(String routeName) {
        super(routeName);
    }

    @Override
    public int calculateFare(int distance) {
        return 15 + distance * 2;
    }
}

class Taxi extends Transport {
    public Taxi(String routeName) {
        super(routeName);
    }

    @Override
    public int calculateFare(int distance) {
        return 70 + distance * 15;
    }
}

public class TransportFareSystem {
    public static void main(String[] args) {
        Transport[] transports = new Transport[] {
            new Bus("紅26"),
            new Bus("307"),
            new Taxi("台灣大車隊"),
            new Taxi("大都會車隊")
        };

        int distance = 10;

        for (Transport t : transports) {
            int fare = t.calculateFare(distance);
            System.out.println(t.routeName + " (距離 " + distance + " km) 票價: " + fare + " 元");
        }
    }
}