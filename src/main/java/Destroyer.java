final class Destroyer extends Ship {

	Destroyer() {
		name = "Destroyer";
		size = 2;
		setInitialHealth();
	}

	void printShip() {
		System.out.print("d");
	}
}