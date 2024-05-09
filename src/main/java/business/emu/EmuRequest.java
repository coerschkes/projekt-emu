package business.emu;

public enum EmuRequest {
    END_COMMUNICATION(new byte[]{0x01, 0x42, 0x30, 0x03}),
    START_COMMUNICATION(new byte[]{0x2F, 0x3F, 0x21, 0x0D, 0x0A}),
    PROGRAMMING_MODE(new byte[]{0x06, 0x30, 0x30, 0x31, 0x0D, 0x0A});

    private final byte[] byteArray;

    EmuRequest(final byte[] byteArray) {
        this.byteArray = byteArray;
    }

    public byte[] getByteArray() {
        return byteArray;
    }
}
