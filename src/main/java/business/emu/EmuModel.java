package business.emu;

import business.emu.daemon.EmuConnector;
import business.emu.daemon.EmuDevice;
import business.emu.daemon.EmuInterface;
import net.sf.yad2xx.FTDIException;

import java.util.concurrent.CompletableFuture;

public class EmuModel {
    private static EmuModel modelInstance;
    private final EmuInterface emuInterface;

    public static EmuModel getInstance() {
        if (modelInstance == null) {
            modelInstance = new EmuModel();
        }
        return modelInstance;
    }

    private EmuModel() {
        try {
            emuInterface = new EmuConnector(new EmuDevice());
            emuInterface.connect();
            emuInterface.activateProgrammingMode();
        } catch (FTDIException e) {
            throw new RuntimeException(e);
        }
    }

    public CompletableFuture<String> readMeasurement() {
        return emuInterface.getCurrentPower();
    }
}
