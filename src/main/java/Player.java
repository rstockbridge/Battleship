import java.util.Scanner;

final class Player {
    private Ship[] listOfShips;
    private ShipGrid shipGrid;
    private ShotGrid shotGrid;
    private String name;

    Player(final String inputName) {
        createListOfShips();
        shipGrid = new ShipGrid();
        shotGrid = new ShotGrid();
        name = inputName;
    }

    private void createListOfShips() {
        listOfShips = new Ship[5];

        listOfShips[0] = new AircraftCarrier();
        listOfShips[1] = new Battleship();
        listOfShips[2] = new Cruiser();
        listOfShips[3] = new Submarine();
        listOfShips[4] = new Destroyer();
    }

    Ship[] getListOfShips() {
        return listOfShips;
    }

    String getName() {
        return name;
    }

    void putShipOnGrid(Ship ship) {
        final ShipPlacement shipPlacement = new ShipPlacement();
        shipPlacement.setShip(ship);

        loadValidShipPlacement(shipGrid, shipPlacement);
        shipGrid.addShip(shipPlacement);
    }

    void printShipGrid() {
        shipGrid.print();
    }

    Coordinate takeShot() {
        Coordinate location;
        boolean shotIsValid;

        do {
            location = getInputLocation("shot");
            final ValidationResult shotLocationValidation = validateShotLocation(location);
            shotIsValid = shotLocationValidation.isValid();

            if (!shotIsValid) {
                System.out.println(shotLocationValidation.getMessage());
            }
        } while (!shotIsValid);

        return location;
    }

    ShotResult registerShotReceived(Coordinate location) {
        return shipGrid.registerShotReceived(location);
    }

    void registerShotTaken(Coordinate location, boolean shotIsAHit) {
        shotGrid.registerShotTaken(location, shotIsAHit);
    }

    void printShotGrid() {
        shotGrid.print();
    }

    boolean anyShipsAlive() {
        return shipGrid.anyShipsAlive();
    }

    private void loadValidShipPlacement(final ShipGrid shipGrid, final ShipPlacement shipPlacement) {
        boolean directionIsValid;
        Direction direction;

        do {
            shipPlacement.setLocation(getInputLocation(shipPlacement.getShip().getName()));

            System.out.format("Enter the direction for your %s: ", shipPlacement.getShip().getName());

            final Scanner scanner = new Scanner(System.in);
            final String directionText = scanner.nextLine();
            final ValidationResult directionTextValidation = validateDirectionText(directionText);

            if (directionTextValidation.isValid()) {
                direction = convertStringToDirection(directionText);
                shipPlacement.setDirection(direction);

                final ValidationResult shipPlacementValidation = shipGrid.validateShipPlacement(shipPlacement);
                directionIsValid = shipPlacementValidation.isValid();

                if (!directionIsValid) {
                    System.out.println(shipPlacementValidation.getMessage());
                }
            } else {
                directionIsValid = false;
                System.out.println(directionTextValidation.getMessage());
            }
        } while (!directionIsValid);
    }

    private Coordinate getInputLocation(String item) {
        boolean locationTextIsValid;
        Coordinate location = null;

        do {
            System.out.format("\nEnter the location for your %s: ", item);

            final Scanner scanner = new Scanner(System.in);
            final String locationText = scanner.nextLine();

            final ValidationResult locationTextValidation = validateLocationText(locationText);
            locationTextIsValid = locationTextValidation.isValid();

            if (locationTextIsValid) {
                location = convertStringToCoordinate(locationText);
            } else {
                System.out.println(locationTextValidation.getMessage());
            }

        } while (!locationTextIsValid);

        return location;
    }

    private ValidationResult validateLocationText(final String locationText) {
        if (locationText.length() < 2 || locationText.length() > 3) {
            return ValidationResult.invalidResult("The location must be either 2 or 3 characters long.");
        } else {
            if (locationText.charAt(0) < 'A' || locationText.charAt(0) > 'J') {
                return ValidationResult.invalidResult("The x-coordinate must be between 'A' and 'J'.");
            }
            if (Integer.parseInt(locationText.substring(1)) < 1 || Integer.parseInt(locationText.substring(1)) > 10) {
                return ValidationResult.invalidResult("The y-coordinate must be between 1 and 10.");
            } else {
                return ValidationResult.validResult();
            }
        }
    }

    private ValidationResult validateDirectionText(final String directionText) {
        if (directionText.equals("U") || directionText.equals("D") || directionText.equals("L") || directionText.equals("R")) {
            return ValidationResult.validResult();
        } else {
            return ValidationResult.invalidResult("The direction must be 'U', 'D', 'L', or 'R'.\n");
        }
    }

    private Coordinate convertStringToCoordinate(final String location) {
        final int x = location.charAt(0) - 'A';
        final int y = Integer.parseInt(location.substring(1)) - 1;

        return new Coordinate(x, y);
    }

    private Direction convertStringToDirection(final String direction) {
        switch (direction) {
            case "U":
                return Direction.UP;
            case "D":
                return Direction.DOWN;
            case "L":
                return Direction.LEFT;
            case "R":
                return Direction.RIGHT;
            default:
                throw new IllegalStateException("This line should be unreachable.");
        }
    }

    private ValidationResult validateShotLocation(final Coordinate location) {
        if (!shotGrid.previousShotAtLocation(location)) {
            return ValidationResult.validResult();
        } else {
            return ValidationResult.invalidResult("You have already taken a shot here.\n");
        }
    }
}