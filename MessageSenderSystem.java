interface MessageSender {
    void send(String receiver, String message);
}

class EmailSender implements MessageSender {
    @Override
    public void send(String receiver, String message) {
        System.out.println("寄送 Email 給 " + receiver + ": " + message);
    }
}

class SmsSender implements MessageSender {
    @Override
    public void send(String receiver, String message) {
        System.out.println("發送簡訊給 " + receiver + ": " + message);
    }
}

class ConsoleSender implements MessageSender {
    @Override
    public void send(String receiver, String message) {
        System.out.println("控制台輸出給 " + receiver + ": " + message);
    }
}

public class MessageSenderSystem {
    public static void notify(MessageSender sender, String receiver, String message) {
        if (receiver == null || receiver.trim().isEmpty() || message == null || message.trim().isEmpty()) {
            System.out.println("錯誤：收件者或訊息內容不得為空白！");
            return;
        }
        sender.send(receiver, message);
    }

    public static void main(String[] args) {
        MessageSender email = new EmailSender();
        MessageSender sms = new SmsSender();
        MessageSender console = new ConsoleSender();

        notify(email, "maggie@example.com", "你的驗證碼是 0613");
        notify(sms, "0912345678", "訂單已出貨");
        notify(console, "Admin", "系統正常運行中");

        notify(email, "", "測試空收件者");
        notify(sms, "0912345678", "   ");
    }
}