package business.emu;

public enum Measurement {

    POWER("1.7.1", new byte[]{1, 82, 49, 2, 49, 46, 55, 46, 49, 40, 41, 3}, "W"),
    APPARENT_POWER("9.7.1", new byte[]{1, 82, 49, 2, 57, 46, 55, 46, 49, 40, 41, 3}, "VA"),
    /*
     * induktive Verbraucher (Drosselspule, Transformator, Asynchronmotor)
     * Aufbau von magnetischem Feld. Die Spannung eilt dem Strom voraus -->
     * Phasenverschiebungswinkel > 0 !!!
     * Beispiele: Laptop, MP3-Player/Radio, Schneidegeraet
     */
    INDUCTIVE_REACTIVE_POWER("3.7.1", new byte[]{1, 82, 49, 2, 51, 46, 55, 46, 49, 40, 41, 3}, "VAR"),
    /*
     * kapazitive Verbraucher (Kondensatormotoren, Erdkabel)
     * Aufbau von elektrischem Feld. Der Strom eilt der Spannung voraus -->
     * Phasenverschiebungswinkel < 0 !!!
     * Beispiele: Monitor, Ventilator, Halogenlampe, Laptop, Pumpe
     */
    CAPACITIVE_REACTIVE_POWER("4.7.1", new byte[]{1, 82, 49, 2, 52, 46, 55, 46, 49, 40, 41, 3}, "VAR"),
    POWER_FACTOR("13.7", new byte[]{1, 82, 49, 2, 49, 51, 46, 55, 40, 41, 3}, "cos Phi"),
    SERIAL_NUMBER("96.1.0", new byte[]{1, 82, 49, 2, 57, 54, 46, 49, 46, 48, 40, 41, 3}, "Seriennummer"),
    TEXT("128.128", new byte[]{1, 82, 49, 2, 49, 50, 56, 46, 49, 50, 56, 40, 41, 3}, "Text");

    private final byte[] request;
    private final String obis;
    private final String unit;

    Measurement(String obis, byte[] request, String unit) {
        this.obis = obis;
        this.request = request;
        this.unit = unit;
    }

    public byte[] getRequest() {
        return request;
    }

    public String getObis() {
        return obis;
    }

    public String getUnit() {
        return unit;
    }
}
