package Log;

public class Logger {

    static String[] lines = new String[255];
    static int arrayLength = 0;

    public Logger() {}

    public static void msg(String facility, String msg) {
        System.out.println(facility + ": " + msg);
        lines[arrayLength++] = facility + ": " + msg;
    }

    public static String[] getLines() {
        return lines;
    }

    public static void clearLines() {
        lines = new String[255];
        arrayLength = 0;
    }

}