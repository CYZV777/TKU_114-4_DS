interface FCS_PricingPolicy {
    double calculatePrice(double originalPrice);
}

class FCS_RegularPricing implements FCS_PricingPolicy {
    @Override
    public double calculatePrice(double originalPrice) {
        return originalPrice;
    }
}

class FCS_VipDiscountPricing implements FCS_PricingPolicy {
    @Override
    public double calculatePrice(double originalPrice) {
        return originalPrice * 0.85;
    }
}

class FCS_ThresholdDiscountPricing implements FCS_PricingPolicy {
    @Override
    public double calculatePrice(double originalPrice) {
        if (originalPrice >= 2000) {
            return originalPrice - 300;
        }
        return originalPrice;
    }
}

interface FCS_NotificationChannel {
    boolean send(String message);
}

class FCS_EmailNotification implements FCS_NotificationChannel {
    @Override
    public boolean send(String message) {
        System.out.println("[Email] 發送成功: " + message);
        return true;
    }
}

class FCS_SmsNotification implements FCS_NotificationChannel {
    @Override
    public boolean send(String message) {
        System.out.println("[SMS] 發送成功: " + message);
        return true;
    }
}

class FCS_ConsoleNotification implements FCS_NotificationChannel {
    @Override
    public boolean send(String message) {
        System.out.println("[Console] 顯示通知: " + message);
        return true;
    }
}

class FCS_CheckoutResult {
    private String orderId;
    private double originalPrice;
    private double finalPrice;
    private boolean notificationStatus;

    public FCS_CheckoutResult(String orderId, double originalPrice, double finalPrice, boolean notificationStatus) {
        this.orderId = orderId;
        this.originalPrice = originalPrice;
        this.finalPrice = finalPrice;
        this.notificationStatus = notificationStatus;
    }

    public void display() {
        System.out.println("訂單編號: " + orderId);
        System.out.println("原價: $" + originalPrice);
        System.out.println("結帳金額: $" + finalPrice);
        System.out.println("通知發送狀態: " + (notificationStatus ? "成功" : "失敗"));
    }
}

class FCS_CheckoutService {
    private FCS_PricingPolicy pricingPolicy;
    private FCS_NotificationChannel notificationChannel;

    public FCS_CheckoutService(FCS_PricingPolicy pricingPolicy, FCS_NotificationChannel notificationChannel) {
        this.pricingPolicy = pricingPolicy;
        this.notificationChannel = notificationChannel;
    }

    public void setPricingPolicy(FCS_PricingPolicy pricingPolicy) {
        this.pricingPolicy = pricingPolicy;
    }

    public void setNotificationChannel(FCS_NotificationChannel notificationChannel) {
        this.notificationChannel = notificationChannel;
    }

    public FCS_CheckoutResult checkout(String orderId, double originalPrice) {
        double finalPrice = pricingPolicy.calculatePrice(originalPrice);
        String message = "訂單 " + orderId + " 結帳完成，金額為 $" + finalPrice;
        boolean status = notificationChannel.send(message);
        return new FCS_CheckoutResult(orderId, originalPrice, finalPrice, status);
    }
}

public class FlexibleCheckoutSystem {
    public static void main(String[] args) {
        FCS_PricingPolicy regular = new FCS_RegularPricing();
        FCS_PricingPolicy vip = new FCS_VipDiscountPricing();
        FCS_PricingPolicy threshold = new FCS_ThresholdDiscountPricing();

        FCS_NotificationChannel email = new FCS_EmailNotification();
        FCS_NotificationChannel sms = new FCS_SmsNotification();
        FCS_NotificationChannel console = new FCS_ConsoleNotification();

        FCS_CheckoutService service = new FCS_CheckoutService(regular, email);

        System.out.println("--- 測試 1: 原價 + Email ---");
        FCS_CheckoutResult r1 = service.checkout("ORD001", 1000);
        r1.display();
        System.out.println("\n--- 測試 2: 原價 + SMS ---");
        service.setNotificationChannel(sms);
        FCS_CheckoutResult r2 = service.checkout("ORD002", 1500);
        r2.display();

        System.out.println("\n--- 測試 3: VIP 八五折 + Email ---");
        service.setPricingPolicy(vip);
        service.setNotificationChannel(email);
        FCS_CheckoutResult r3 = service.checkout("ORD003", 2000);
        r3.display();

        System.out.println("\n--- 測試 4: VIP 八五折 + Console ---");
        service.setNotificationChannel(console);
        FCS_CheckoutResult r4 = service.checkout("ORD004", 3000);
        r4.display();
        System.out.println("\n--- 測試 5: 滿 2000 折 300 + SMS ---");
        service.setPricingPolicy(threshold);
        service.setNotificationChannel(sms);
        FCS_CheckoutResult r5 = service.checkout("ORD005", 2500);
        r5.display();

        System.out.println("\n--- 測試 6: 滿 2000 折 300 + Console ---");
        service.setNotificationChannel(console);
        FCS_CheckoutResult r6 = service.checkout("ORD006", 1800);
        r6.display();
    }
}