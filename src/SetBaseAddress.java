import Log.Logger;
import SerialPort.Port;

class SetBaseAddress {
    public static void main(String[] args) {
        int newBase = 0;
        if (args.length == 1) {
            newBase = Integer.parseInt(args[0]);
        }
        
        for (int i = 0; i < 256; i++) {
            Port port = new Port("/dev/ttyUSB0", 4800, i);
            port.open();
            port.setBaseAddress(newBase);
            port.close();
            try {
                Thread.sleep(100);
            } catch (Exception e) {}
        }
    }
}