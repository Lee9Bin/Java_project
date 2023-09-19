package mini_project.omok;

public class Omok {
	public static void main(String[] args) {
		Player user = new Player("사용자", "O");
		Player computer = new Player("컴퓨터", "X");
		Board board = new Board(19);
		play(board, user, computer);
	}

	private static void play(Board board, Player user, Player computer) {

		while (true) {
			board.print();
			board.set(user);
			if (!board.check(user)) {
				board.print();
				break;
			}
			board.print();
			board.set(computer);
			if (!board.check(computer)) {
				board.print();
				break;
			}
		}

	}
}