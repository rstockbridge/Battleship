abstract class Ship {
    String name;
    int size;
    int health;

    String getName() {
        return name;
    }

    int getSize() {
        return size;
    }

    void setInitialHealth() {
        health = size;
    }

    void registerHit() {
        health--;
    }

    boolean isAlive() {
        return health > 0;
    }

    abstract void printShip();
}