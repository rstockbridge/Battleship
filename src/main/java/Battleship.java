final class Battleship extends Ship {

    Battleship() {
        name = "Battleship";
        size = 4;
        setInitialHealth();
    }

    void printShip() {
        System.out.print("b");
    }
}