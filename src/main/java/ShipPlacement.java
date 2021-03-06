final class ShipPlacement {
    private Ship ship;
    private Coordinate location;
    private Direction direction;

    void setShip(Ship inputShip) {
        ship = inputShip;
    }

    void setLocation(Coordinate inputLocation) {
        location = inputLocation;
    }

    void setDirection(Direction inputDirection) {
        direction = inputDirection;
    }

    Ship getShip() {
        return ship;
    }

    Coordinate getLocation() {
        return location;
    }

    Direction getDirection() {
        return direction;
    }
}