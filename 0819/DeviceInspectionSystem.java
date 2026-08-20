public class DeviceInspectionSystem {

    abstract static class Device {
        String name;

        public Device(String name) {
            this.name = name;
        }

        public abstract void runDiagnostic();
    }

    static class Laptop extends Device {
        public Laptop(String name) {
            super(name);
        }

        @Override
        public void runDiagnostic() {
            System.out.println(name + " 正在進行系統硬碟與記憶體檢測...");
        }
    }

    static class Printer extends Device {
        public Printer(String name) {
            super(name);
        }

        @Override
        public void runDiagnostic() {
            System.out.println(name + " 正在檢測墨水存量與進紙狀態...");
        }

        public void cleanPrintHead() {
            System.out.println("-> " + name + " 執行噴頭清潔完成！");
        }
    }

    static class Router extends Device {
        public Router(String name) {
            super(name);
        }

        @Override
        public void runDiagnostic() {
            System.out.println(name + " 正在檢測網路封包與連線延遲...");
        }
    }

    public static void main(String[] args) {
        Device[] devices = new Device[] {
            new Laptop("ASUS 筆電"),
            new Printer("Epson 印表機"),
            new Router("TP-Link 路由器"),
            new Printer("Canon 印表機")
        };

        for (Device d : devices) {
            d.runDiagnostic();

            if (d instanceof Printer printer) {
                printer.cleanPrintHead();
            }
        }
    }
}