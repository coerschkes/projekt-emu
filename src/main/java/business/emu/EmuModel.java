package business.emu;

import net.sf.yad2xx.FTDIException;

public class EmuModel {
    private static EmuModel modelInstance;
    private final EmuConnector emuConnector;

    public static EmuModel getInstance() {
        if (modelInstance == null) {
            modelInstance = new EmuModel();
        }
        return modelInstance;
    }

    private EmuModel() {
        try {
            emuConnector = new EmuConnector();
            emuConnector.connect();
        } catch (FTDIException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * todo:
     *      * activate pmode
     *      * clear buffer
     *      * read current power value from emu
     *      * retrieve buffer and store in db
     */
}
