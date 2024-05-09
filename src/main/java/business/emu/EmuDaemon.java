package business.emu;

import net.sf.yad2xx.Device;
import net.sf.yad2xx.FTDIException;
import net.sf.yad2xx.FTDIInterface;

import java.util.Arrays;
import java.util.function.Supplier;

public class EmuDaemon extends Thread {
    private static final long COMMUNICATION_TIMEOUT = 1000;
    private static final byte[] DATA_CHARACTERISTICS = {7, 1, 2};
    private static final int BAUD_RATE = 300;
    private final StringBuffer buffer = new StringBuffer();

    private long startTime = 0;
    private boolean connected = false;
    private final Device device;
    private Supplier<String> callback;

    public EmuDaemon() throws FTDIException {
        System.out.println("Connected devices: " + FTDIInterface.getDevices().length);
        this.device = getEmuDevice();
        this.device.open();
        device.setDataCharacteristics(DATA_CHARACTERISTICS[0], DATA_CHARACTERISTICS[1], DATA_CHARACTERISTICS[2]);
        device.setBaudRate(BAUD_RATE);
        System.out.println("Connected to device: " + device.getDescription());
    }

    @Override
    public void run() {
        while (connected) {
            try {
                mainRoutine();
            } catch (FTDIException e) {
                throw new RuntimeException(e);
            }
            super.run();
        }
    }

    public StringBuffer getBuffer() {
        return buffer;
    }

    public void setConnected(final boolean connected) {
        this.connected = connected;
    }

    public Device getDevice() {
        return device;
    }

    public void setCallback(final Supplier<String> callback) {
        this.callback = callback;
    }

    private void mainRoutine() throws FTDIException {
        byte[] b = new byte[1];
        if (this.device.getQueueStatus() != 0) {
            readBuffer(b);
        }
        long current = System.currentTimeMillis();
        if (startTime != 0 && current - startTime > COMMUNICATION_TIMEOUT) {
            runCallback();
        }
    }

    private void runCallback() {
        startTime = 0;
        if (callback != null) {
            System.out.println(callback.get());
            this.callback = null;
        }
    }

    private void readBuffer(final byte[] b) throws FTDIException {
        startTime = System.currentTimeMillis();
        this.device.read(b);
        buffer.append(new String(b));
    }

    private static Device getEmuDevice() throws FTDIException {
        return Arrays.stream(FTDIInterface.getDevices())
                .filter(dev -> dev.getDescription().contains("NZR"))
                .findFirst().orElseThrow(RuntimeException::new);
    }
}
