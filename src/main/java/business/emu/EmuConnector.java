package business.emu;


import net.sf.yad2xx.FTDIException;

public class EmuConnector {
    private final EmuDaemon daemon;

    public EmuConnector() throws FTDIException {
        this.daemon = new EmuDaemon();
    }

    public void printBuffer() {
        System.out.println(this.getBufferValue());
    }

    public String getBufferValue() {
        return this.daemon.getBuffer().toString();
    }

    public void clearBuffer() {
        this.daemon.getBuffer().setLength(0);
        System.out.println("buffer cleared");
    }

    public void disconnect() throws FTDIException {
        this.daemon.setConnected(false);
        this.daemon.getDevice().write(EmuRequest.END_COMMUNICATION.getByteArray());
        this.daemon.getDevice().close();
        System.out.println("End of device communication");

    }

    public void connect() throws FTDIException {
        this.daemon.setConnected(true);
        this.daemon.start();
        this.daemon.getDevice().write(EmuRequest.START_COMMUNICATION.getByteArray());
        this.daemon.setCallback(() -> "Connection Successful");
        System.out.println("Establishing connection to device..");
    }

    public void sendProgrammingMode() throws FTDIException {
        this.daemon.getDevice().write(EmuRequest.PROGRAMMING_MODE.getByteArray());
        System.out.println("Programming Mode");
    }

    public void sendRequest(final Measurement measurement) throws FTDIException {
        this.daemon.getDevice().write(measurement.getRequest());
        System.out.println("Request " + measurement.getObis() + " " + measurement);
    }
}
