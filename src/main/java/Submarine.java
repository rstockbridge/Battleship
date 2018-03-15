final class Submarine extends Ship {

    Submarine() {
        name = "Submarine";
        size = 3;
        setInitialHealth();
    }

    void printShip() {
        System.out.print("s");
    }
}