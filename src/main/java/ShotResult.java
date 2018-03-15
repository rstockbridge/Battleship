final class ShotResult {
    static ShotResult hitNotSunk() {
        return new ShotResult(true, false, null);
    }

    static ShotResult hitAndSunk(String inputSunkShipName) {
        return new ShotResult(true, true, inputSunkShipName);
    }

    static ShotResult notHit() {
        return new ShotResult(false, false, null);
    }

    private boolean isHit;
    private boolean isShipSunk;
    private String sunkShipName;

    private ShotResult(boolean inputIsHit, boolean inputIsShipSunk, String inputSunkShipName) {
        isHit = inputIsHit;
        isShipSunk = inputIsShipSunk;
        sunkShipName = inputSunkShipName;
    }

    boolean isHit() {
        return isHit;
    }

    boolean isShipSunk() {
        if (!isHit) {
            throw new IllegalArgumentException("Missed shots can't sink ships.");
        }

        return isShipSunk;
    }

    String getSunkShipName() {
        if (!isHit) {
            throw new IllegalArgumentException("Missed shots can't sink ships.");
        } else {
            if (!isShipSunk) {
                throw new IllegalArgumentException("Shot didn't sink ship.");
            } else {
                return sunkShipName;
            }
        }
    }
}