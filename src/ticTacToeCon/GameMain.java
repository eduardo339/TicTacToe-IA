package ticTacToeCon;

import java.util.Scanner;

/**
 * Clase main del TicTacToe
 */
public class GameMain {
	// Define properties

	/** Tablero del juego */
	private Board board;

	/** Estado actual del juego */
	private State currentState;

	/** Jugador actual */
	private Seed currentPlayer;

	private static Scanner in = new Scanner(System.in);

	/** Constructor de la clase */
	public GameMain() {
		// Prepara la inicialización del juego una vez.
		initGame();

		// Resetea el tablero del juego, el estado actual y el jugador actual.
		newGame();

		// Juega el juego una vez.
		do {
			// Un jugador realiza un movimiento.
			// El vector celdas[][] se actualiza y se cambia de turno.
			stepGame();
			
			// Se redibuja el tablero.
			board.paint();
			
			// Imprime el mensaje si el juego terminó.
			if (currentState == State.CROSS_WON) {
				System.out.println("Las 'X' ganan\nAdios.");
			} else if (currentState == State.NOUGHT_WON) {
				System.out.println("Las 'O' ganan\\nAdios.");
			} else if (currentState == State.DRAW) {
				System.out.println("Es un EMPATE!!");
			}
			// Cambio de turno.
			currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
		} while (currentState == State.PLAYING); // Repite hasta el final del juego
	}

	/** Metodo que prepara la inicialización del juego una sola vez. */
	public void initGame() {
		board = new Board(); // Instancia de un nuevo tablero.
	}

	/** Metodo que resetea todos los valores del juego. */
	public void newGame() {
		board.newGame(); // Borra los contenidos de la tabla.
		currentPlayer = Seed.CROSS; // Las cruces inician primero.
		currentState = State.PLAYING; // Listo para jugar.
	}

	/**
	 * El jugador actual hace un movimiento.
	 */
	public void stepGame() {
		boolean validInput = false; // Vandera que valida el do-while.
		
		do {
			String icon = currentPlayer.getIcon();
			System.out.print("Jugador '" + icon + "',haz un movimiento (fila[1-3] columna[1-3]): ");
			
			int row = in.nextInt() - 1; 
			int col = in.nextInt() - 1;
			
			if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
					&& board.cells[row][col].content == Seed.NO_SEED) {
				// Actualiza las celdas[][] y actualiza el estado del juego.
				currentState = board.stepGame(currentPlayer, row, col);
				validInput = true; // Vandera verificada.
			
			} else {
				System.out.println("La celda [" + (row + 1) + "," + (col + 1) + "] no es válida. Intenta otra vez...");
			}
		} while (!validInput); // Repite mientras la vandera sea verdadera.
	}

	/** Metodo main */
	public static void main(String[] args) {
		new GameMain(); // Llamada al constructor.
	}
}