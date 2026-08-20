interface DeliveryMethod {
    double calculateFee();
    String getEstimate();
}

class HomeDelivery implements DeliveryMethod {
    @Override
    public double calculateFee() {
        return 100.0;
    }

    @Override
    public String getEstimate() {
        return "宅配：約 1-2 個工作天送達";
    }
}

class StorePickup implements DeliveryMethod {
    @Override
    public double calculateFee() {
        return 60.0;
    }

    @Override
    public String getEstimate() {
        return "超商取貨：約 2-3 個工作天送達門市";
    }
}

class SelfPickup implements DeliveryMethod {
    @Override
    public double calculateFee() {
        return 0.0;
    }

    @Override
    public String getEstimate() {
        return "自取：隨時可至實體門市取貨";
    }
}

class OrderService {
    private DeliveryMethod deliveryMethod;

    public OrderService(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public void processOrder() {
        System.out.println(deliveryMethod.getEstimate());
        System.out.println("運費為: $" + deliveryMethod.calculateFee());
    }
}

public class DeliveryStrategySystem {
    public static void main(String[] args) {
        OrderService orderService = new OrderService(new HomeDelivery());
        orderService.processOrder();

        System.out.println("--------------------");

        orderService.setDeliveryMethod(new StorePickup());
        orderService.processOrder();

        System.out.println("--------------------");

        orderService.setDeliveryMethod(new SelfPickup());
        orderService.processOrder();
    }
}