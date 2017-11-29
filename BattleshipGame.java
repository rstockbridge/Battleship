import java.io.Console;

final class BattleshipGame {
	private final Player player1 = new Player("Player 1");
	private final Player player2 = new Player("Player 2");

	private Player playerWithTurn = player1;
	private Player playerWithoutTurn = player2;

	Player getPlayer1() {
		return player1;
	}

	Player getPlayer2() {
		return player2;
	}

	void placeShips() {
		System.out.format("\n%s:\n", playerWithTurn.getName());

		for(final Ship ship : playerWithTurn.getListOfShips()) {
			playerWithTurn.putShipOnGrid(ship);
			playerWithTurn.printShipGrid();
		}

		switchTurns();
	}

	void shoot() {
    	System.out.format("\n%s:\n", playerWithTurn.getName());

		final Coordinate location = playerWithTurn.takeShot();
		final ShotResult shotResult = playerWithoutTurn.registerShotReceived(location);
	
		System.out.format("Shot is a %s!\n", (shotResult.isHit() ? "hit" : "miss"));
		if(shotResult.isShipSunk()) {
			System.out.format("%s is sunk!\n", shotResult.getSunkShipName());
		}

		playerWithTurn.registerShotTaken(location, shotResult.isHit());
    	playerWithTurn.printShotGrid();

    	switchTurns();
	}

	private void switchTurns() {
		final Player temp = playerWithTurn;
		playerWithTurn = playerWithoutTurn;
		playerWithoutTurn = temp;
	}

	boolean isOver() {
		return !player1.anyShipsAlive() || !player2.anyShipsAlive();
	}

	void printWinner() {
		System.out.format("\n\nGame over, %s won!\n\n", getWinningPlayer().getName());
	}

	private Player getWinningPlayer() {
		return getPlayer1().anyShipsAlive() ? getPlayer1() : getPlayer2();
	}
}