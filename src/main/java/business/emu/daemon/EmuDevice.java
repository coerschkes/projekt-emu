package business.emu.daemon;

import net.sf.yad2xx.Device;
import net.sf.yad2xx.FTDIException;
import net.sf.yad2xx.FTDIInterface;

import java.util.Arrays;

public class EmuDevice {
    private static final byte[] DATA_CHARACTERISTICS = {7, 1, 2};
    private static final int BAUD_RATE = 300;
    private final Device device;

    public EmuDevice() throws FTDIException {
        System.out.println("Connected devices: " + FTDIInterface.getDevices().length);
        device = getEmuDevice();
        device.open();
        device.setDataCharacteristics(DATA_CHARACTERISTICS[0], DATA_CHARACTERISTICS[1], DATA_CHARACTERISTICS[2]);
        device.setBaudRate(BAUD_RATE);
        System.out.println("Connected to device: " + device.getDescription());
    }

    public void write(final byte[] data) throws FTDIException {
        device.write(data);
    }

    public void close() throws FTDIException {
        device.close();
    }

    public byte[] read() throws FTDIException {
        final byte[] byteBuffer = new byte[1];
        device.read(byteBuffer);
        return byteBuffer;
    }

    public boolean hasData() throws FTDIException {
        return device.getQueueStatus() != 0;
    }

    private static Device getEmuDevice() throws FTDIException {
        return Arrays.stream(FTDIInterface.getDevices())
                .filter(dev -> dev.getDescription().contains("NZR"))
                .findFirst().orElseThrow(RuntimeException::new);
    }

}
