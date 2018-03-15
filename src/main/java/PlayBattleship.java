final class PlayBattleship {
    public static void main(String[] args) {
        final BattleshipGame game = new BattleshipGame();

        System.out.println("\nPlayers, please place your ships!\n");

        game.placeShips();
        System.out.println("");
        game.placeShips();

        System.out.println("\n\nPlayers, let the game begin!");

        while (!game.isOver()) {
            System.out.println("");
            game.shoot();
        }

        game.printWinner();
    }
}