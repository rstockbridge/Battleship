import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

final class ShipGrid {
    private final Map<Ship, Set<Coordinate>> shipLocations = new HashMap<>();
    
    ValidationResult validateShipPlacement(final ShipPlacement shipPlacement) {
        final boolean locationIsOnGrid = locationIsOnGrid(shipPlacement);
        final boolean locationIsFree = locationIsFree(shipPlacement);

        if (locationIsOnGrid && locationIsFree) {
            return ValidationResult.validResult();
        } else {
            if (!locationIsOnGrid) {
                return ValidationResult.invalidResult("Ship placement is not on the grid.");
            } else {
                return ValidationResult.invalidResult("Ship placement is not free.");
            }
        }
    }

    private boolean locationIsOnGrid(final ShipPlacement shipPlacement) {
        final int shipSize = shipPlacement.getShip().getSize();
        final Coordinate location = shipPlacement.getLocation();
        final Direction direction = shipPlacement.getDirection();

        switch (direction) {
            case UP:
                return location.getY() - shipSize >= 0;
            case DOWN:
                return location.getY() + shipSize <= 9;
            case LEFT:
                return location.getX() - shipSize >= 0;
            case RIGHT:
                return location.getX() + shipSize <= 9;
            default:
                throw new IllegalStateException("This line should be unreachable.");
        }
    }

    private boolean locationIsFree(final ShipPlacement shipPlacement) {
        final int shipSize = shipPlacement.getShip().getSize();
        final Coordinate location = shipPlacement.getLocation();
        final Direction direction = shipPlacement.getDirection();

        boolean locationIsFree = true;

        switch (direction) {
            case UP:
                for (int j = location.getY(); j > location.getY() - shipSize; j--) {
                    for (Set<Coordinate> shipCoordinates : shipLocations.values()) {
                        if (shipCoordinates.contains(new Coordinate(location.getX(), j))) {
                            locationIsFree = false;
                        }
                    }
                }
                break;
            case DOWN:
                for (int j = location.getY(); j < location.getY() + shipSize; j++) {
                    for (Set<Coordinate> shipCoordinates : shipLocations.values()) {
                        if (shipCoordinates.contains(new Coordinate(location.getX(), j))) {
                            locationIsFree = false;
                        }
                    }
                }
                break;
            case LEFT:
                for (int i = location.getX(); i > location.getX() - shipSize; i--) {
                    for (Set<Coordinate> shipCoordinates : shipLocations.values()) {
                        if (shipCoordinates.contains(new Coordinate(i, location.getY()))) {
                            locationIsFree = false;
                        }
                    }
                }
                break;
            case RIGHT:
                for (int i = location.getX(); i < location.getX() + shipSize; i++) {
                    for (Set<Coordinate> shipCoordinates : shipLocations.values()) {
                        if (shipCoordinates.contains(new Coordinate(i, location.getY()))) {
                            locationIsFree = false;
                        }
                    }
                }
                break;
        }

        return locationIsFree;
    }

    void addShip(final ShipPlacement shipPlacement) {
        final Ship ship = shipPlacement.getShip();
        final Coordinate location = shipPlacement.getLocation();
        final Direction direction = shipPlacement.getDirection();

        final Set<Coordinate> setOfLocations = new HashSet<>();

        switch (direction) {
            case UP:
                for (int j = location.getY(); j > location.getY() - ship.getSize(); j--) {
                    setOfLocations.add(new Coordinate(location.getX(), j));
                }
                break;
            case DOWN:
                for (int j = location.getY(); j < location.getY() + ship.getSize(); j++) {
                    setOfLocations.add(new Coordinate(location.getX(), j));
                }
                break;
            case LEFT:
                for (int i = location.getX(); i > location.getX() - ship.getSize(); i--) {
                    setOfLocations.add(new Coordinate(i, location.getY()));
                }
                break;
            case RIGHT:
                for (int i = location.getX(); i < location.getX() + ship.getSize(); i++) {
                    setOfLocations.add(new Coordinate(i, location.getY()));
                }
                break;
        }

        shipLocations.put(ship, setOfLocations);
    }

    ShotResult registerShotReceived(Coordinate location) {
        for (Ship ship : shipLocations.keySet()) {
            if (shipLocations.get(ship).contains(location)) {
                ship.registerHit();

                if (ship.isAlive()) {
                    return ShotResult.hitNotSunk();
                } else {
                    return ShotResult.hitAndSunk(ship.getName());
                }
            }
        }

        return ShotResult.notHit();
    }

    boolean anyShipsAlive() {
        for (Ship ship : shipLocations.keySet()) {
            if (ship.isAlive()) {
                return true;
            }
        }

        return false;
    }

    void print() {
        System.out.println("\n   A B C D E F G H I J");

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (j == 0) {
                    System.out.print(i + 1 + (i < 9 ? "  " : " "));
                }

                boolean printedShip = false;

                for (Ship ship : shipLocations.keySet()) {
                    if (shipLocations.get(ship).contains(new Coordinate(j, i))) {
                        ship.printShip();
                        System.out.print(" ");
                        printedShip = true;
                    }
                }
                if (!printedShip) {
                    System.out.print("- ");
                }
            }

            System.out.println();
        }
    }
}