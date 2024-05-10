package business.emu.daemon;

import net.sf.yad2xx.FTDIException;

import java.util.*;
import java.util.concurrent.CompletableFuture;

class EmuDaemon extends Thread {
    private static final long COMMUNICATION_TIMEOUT = 200;

    private final StringBuffer buffer = new StringBuffer();
    private final Queue<EmuCommand> commandQueue = new LinkedList<>();
    private final Map<EmuCommand, CompletableFuture<String>> futureMap = new HashMap<>();
    private final EmuDevice emuDevice;

    private long startTime = 0;
    private boolean connected = false;
    private EmuCommand currentCommand;

    EmuDaemon(final EmuDevice emuDevice) {
        this.emuDevice = emuDevice;
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

    CompletableFuture<String> executeCommand(final EmuCommand command) {
        final CompletableFuture<String> future = new CompletableFuture<>();
        futureMap.put(command, future);
        commandQueue.add(command);
        return future;
    }

    void setConnected(final boolean connected) {
        this.connected = connected;
    }

    private void mainRoutine() throws FTDIException {
        if (isReading()) {
            readBuffer();
        }
        if (isCompleting()) {
            runCallback();
        }
        if (isIdle()) {
            executeCommand();
        }
    }

    private void executeCommand() throws FTDIException {
        currentCommand = Objects.requireNonNull(commandQueue.poll());
        emuDevice.write(currentCommand.getByteArray());
        if (currentCommand == EmuCommand.END_COMMUNICATION) {
            setConnected(false);
            emuDevice.close();
        }
    }

    private void runCallback() {
        startTime = 0;
        if (currentCommand != null) {
            System.out.println(currentCommand.getCallbackMessage());
            futureMap.get(currentCommand).complete(this.buffer.toString());
            futureMap.remove(currentCommand);
            this.buffer.setLength(0);
            this.currentCommand = null;
        }
    }

    private void readBuffer() throws FTDIException {
        startTime = System.currentTimeMillis();
        buffer.append(new String(emuDevice.read()));
    }

    private boolean isReading() throws FTDIException {
        return emuDevice.hasData();
    }

    private boolean isCompleting() {
        return startTime != 0 && System.currentTimeMillis() - startTime > COMMUNICATION_TIMEOUT;
    }

    private boolean isIdle() {
        return startTime == 0 && currentCommand == null && !commandQueue.isEmpty();
    }
}
