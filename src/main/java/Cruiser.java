final class Cruiser extends Ship {

    Cruiser() {
        name = "Cruiser";
        size = 3;
        setInitialHealth();
    }

    void printShip() {
        System.out.print("c");
    }
}