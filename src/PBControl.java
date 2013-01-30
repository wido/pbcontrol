import SerialPort.Port;
import Log.*;
import Game.Game;
import Game.Settings;
import Gui.*;

class PBControl {

	public static void main(String[] arg) {
	    PBControl pb = new PBControl();
	}

	public PBControl() {
	    /* Voordat we beginnen halen we eerst het OS op én de beschikbare compoorten */
	    String os = System.getProperty("os.name").toLowerCase();

        String serialPort = null;

	    /* Onder windows kiezen we standaard COM1 */
	    if (os.startsWith("windows")) {
            serialPort = "COM1";
	    }

	    /* En onder Linux ttyS0 */
	    if (os.startsWith("linux")) {
            serialPort = "/dev/ttyS0";
	    }

	    Logger.msg("Info", "Detected OS: " + os);
	    Logger.msg("Info", "Default serial port: " + serialPort);



	    /* Er zijn serieële poorten aanwezig, dus we spawnen een GUI */
	    try {

            /* Voor dat we verder gaan, laten we eerst de serieële poort selecteren */
            String[] availablePorts = Port.listPorts();

            /* Als er maar één serieële poort is, selecteren we die */
            if (availablePorts[0] != null && availablePorts[1] == null) {
                serialPort = availablePorts[0];
            } else {

                if (availablePorts != null) {
                    if (availablePorts.length > 0) {
                        SelectSerial selectSerial = new SelectSerial(availablePorts);
                        SelectSerial.run(selectSerial);

                        /* We wachten tot de thread klaar is om ons een selectie terug te geven */
                        while(!selectSerial.isReady()) {
                            try {
                                Thread.sleep(50);
                            } catch (Exception e) {}
                        }
                        serialPort = selectSerial.getSelection();
                    }
                } else {
                    Logger.msg("Warn", "No serial ports available, continuing anyway.");
                }
            }
	    } catch (java.lang.UnsatisfiedLinkError e) {
            Logger.msg("Error", "RXTX library could not be loaded, please install before running again.");
            System.exit(1);
	    } catch (Exception e) {}

	    Logger.msg("Info", "Selected serial port: " + serialPort);

	    /* Settings laden en daarbij ook twee default waarden zetten */
	    Settings settings = new Settings();
	    Logger.msg("Info", "Initializing settings");
	    settings.setRoundTime(60);
	    settings.setRespawnTime(10);
	    settings.load();
	    Logger.msg("Info", "Game settings loaded. RoundTime: (" + settings.getRoundTime() + "), RespawnTime: (" + settings.getRespawnTime() + ")");

	    Control control = new Control(settings, new Port(serialPort));

	    MainGUI maingui = new MainGUI();
	    maingui.run(maingui, control);

	}
}
