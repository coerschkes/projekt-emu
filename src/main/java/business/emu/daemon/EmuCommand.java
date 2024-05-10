package business.emu.daemon;

enum EmuCommand {
    END_COMMUNICATION(new byte[]{0x01, 0x42, 0x30, 0x03}, "Connection closed"),
    START_COMMUNICATION(new byte[]{0x2F, 0x3F, 0x21, 0x0D, 0x0A}, "Connection successful"),
    PROGRAMMING_MODE(new byte[]{0x06, 0x30, 0x30, 0x31, 0x0D, 0x0A}, "Programming mode enabled"),
    POWER(Measurement.POWER.getRequest(), "Power measurement received");

    private final byte[] byteArray;
    private final String callbackMessage;

    EmuCommand(final byte[] byteArray, String callbackMessage) {
        this.byteArray = byteArray;
        this.callbackMessage = callbackMessage;
    }

    byte[] getByteArray() {
        return byteArray;
    }

    String getCallbackMessage() {
        return callbackMessage;
    }
}
