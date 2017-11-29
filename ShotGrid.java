import java.util.Map;
import java.util.HashMap;

final class ShotGrid {
    private final Map<Coordinate, Boolean> shotRecord = new HashMap<>();

    void registerShotTaken(Coordinate location, boolean shotIsAHit) {
        shotRecord.put(location, shotIsAHit);
    }

    boolean previousShotAtLocation(Coordinate location) {
        return shotRecord.containsKey(location);
    }

    void print() {
        System.out.println("\n   A B C D E F G H I J");

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (j == 0) {
                    System.out.print(i + 1 + (i < 9 ? "  " : " "));
                }

                Coordinate location = new Coordinate(j, i);
                if (shotRecord.containsKey(location)) {
                    System.out.print(shotRecord.get(location) ? "X " : "O ");
                } else {
                    System.out.print("- ");
                }
            }

            System.out.println();
        }
    }
}