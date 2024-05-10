package business.emu.daemon;


import net.sf.yad2xx.FTDIException;

import java.util.concurrent.CompletableFuture;

public class EmuConnector implements EmuInterface {
    private final EmuDaemon daemon;

    public EmuConnector(final EmuDevice emuDevice) throws FTDIException {
        this.daemon = new EmuDaemon(emuDevice);
    }

    @Override
    public void connect() {
        this.daemon.setConnected(true);
        this.daemon.start();
        System.out.println("Establishing connection to device..");
        this.daemon.executeCommand(EmuCommand.START_COMMUNICATION);
    }

    @Override
    public void disconnect() {
        System.out.println("Closing connection..");
        this.daemon.executeCommand(EmuCommand.END_COMMUNICATION);
    }

    @Override
    public void activateProgrammingMode() {
        System.out.println("Activating programming mode..");
        this.daemon.executeCommand(EmuCommand.PROGRAMMING_MODE);
    }

    @Override
    public CompletableFuture<String> getCurrentPower() {
        System.out.println("Retrieving power..");
        return this.daemon.executeCommand(EmuCommand.POWER);
    }
}
