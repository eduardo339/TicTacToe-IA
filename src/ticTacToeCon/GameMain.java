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

	private int turn;
	
	private int cellsWin[][] = new int[3][3];
	

	/** Constructor de la clase */
	public GameMain() {
		// Prepara la inicializaci�n del juego una vez.
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

			// Imprime el mensaje si el juego termin�.
			if (currentState == State.CP1_WON) {
				System.out.println("Las 'X' ganan\nAdios.");
			} else if (currentState == State.PLAYER_WON) {
				System.out.println("Las 'O' ganan\nAdios.");
			} else if (currentState == State.DRAW) {
				System.out.println("Es un EMPATE!!");
			}
			// Cambio de turno.
			currentPlayer = (currentPlayer == Seed.CP1) ? Seed.PLAYER : Seed.CP1;
		} while (currentState == State.PLAYING); // Repite hasta el final del juego
	}

	/** Metodo que prepara la inicializaci�n del juego una sola vez. */
	public void initGame() {
		for (int i = 0; i < cellsWin.length; i++) {
			for (int j = 0; j < cellsWin.length; j++) {
				cellsWin[i][j] = 0;
			}
		}
		turn = 1;
		board = new Board(); // Instancia de un nuevo tablero.
	}

	/** Metodo que resetea todos los valores del juego. */
	public void newGame() {
		board.newGame(); // Borra los contenidos de la tabla.
		currentPlayer = Seed.CP1; // Las cruces inician primero.
		currentState = State.PLAYING; // Listo para jugar.
	}

	/**
	 * El jugador actual hace un movimiento.
	 */
	public void stepGame() {
		System.out.println(currentPlayer);
		System.out.println(turn);
		if (currentPlayer == Seed.CP1) {
			MoveIA();
		} else {
			boolean validInput = false; // Vandera que valida el do-while.

			do {
				String icon = currentPlayer.getIcon();
				System.out.print("Jugador '" + icon + "',haz un movimiento (fila[1-3] columna[1-3]): ");

				int row = in.nextInt() - 1;
				int col = in.nextInt() - 1;

				if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
						&& board.cells[row][col].content == Seed.NO_SEED) {
					// Actualiza las celdas[][] y actualiza el estado del juego.
					cellsWin[row][col] = 5; //25
					currentState = board.stepGame(currentPlayer, row, col);
					validInput = true; // Vandera verificada.

				} else {
					System.out.println(
							"La celda [" + (row + 1) + "," + (col + 1) + "] no es v�lida. Intenta otra vez...");
				}
			} while (!validInput); // Repite mientras la vandera sea verdadera.
			
			Zzz();
			turn++;
		}
	}

	public void MoveIA() {
		switch (turn) {
		case 1:
			System.out.println("Maquina pensando.......");
			System.out.println("Maquina tira: ");
			currentState = board.stepGame(currentPlayer, 0, 0);
			cellsWin[0][0] = 4; //16
			turn++;
			Zzz();
			break;

		case 3:
			System.out.println("Maquina pensando.......");
			System.out.println("Maquina tira: ");
			if(cellsWin[1][1] == 0) {
				currentState = board.stepGame(currentPlayer, 1, 1);
				cellsWin[1][1] = 4;
			}else if (board.cells[2][2].content == Seed.NO_SEED) {
				currentState = board.stepGame(currentPlayer, 2, 2);
				cellsWin[2][2] = 4;
			}else {
				currentState = board.stepGame(currentPlayer, 0, 2);
				cellsWin[0][2] = 4;
			}
			turn++;
			Zzz();
			break;

		case 5:
			Posswin();
			turn++;
			Zzz();
			break;
		case 7:
			Posswin();
			turn++;
			Zzz();
			break;
			
		case 9:
			for (int i = 0; i < cellsWin.length; i++) {
				for (int j = 0; j < cellsWin.length; j++) {
					if(board.cells[i][j].content == Seed.NO_SEED){
						currentState = board.stepGame(currentPlayer, i, j);
						cellsWin[i][j] = 4;
					}
				}
			}
			turn++;
			Zzz();
			break;

		}

	}
	
	public void Posswin() {
		System.out.println("Maquina pensando.......");
		System.out.println("Maquina tira: ");
		
		if(cellsWin[0][2] * cellsWin[2][2] == 16 && board.cells[1][2].content == Seed.NO_SEED) {
			currentState = board.stepGame(currentPlayer, 1, 2);
			cellsWin[1][2] = 4;
		}
		else if(cellsWin[2][2] * cellsWin[2][0] == 16 && board.cells[2][1].content == Seed.NO_SEED) {
			currentState = board.stepGame(currentPlayer, 2, 1);
			cellsWin[2][1] = 4;
		}//
		else if(cellsWin[2][1] * cellsWin[1][1] == 16 && board.cells[0][1].content == Seed.NO_SEED) {
			currentState = board.stepGame(currentPlayer, 0, 1);
			cellsWin[0][1] = 4;
		}
		else if(cellsWin[1][1] * cellsWin[1][2] == 16 && board.cells[1][0].content == Seed.NO_SEED) {
			currentState = board.stepGame(currentPlayer, 1, 0);
			cellsWin[1][0] = 4;
		}//
		else if(cellsWin[0][0] * cellsWin[0][2] == 16 && board.cells[0][1].content == Seed.NO_SEED) {
			currentState = board.stepGame(currentPlayer, 0, 1);
			cellsWin[0][1] = 4;
		}
		else if (cellsWin[0][0] * cellsWin[2][0] == 16 && board.cells[1][0].content == Seed.NO_SEED) {
			currentState = board.stepGame(currentPlayer, 1, 0);
			cellsWin[1][0] = 4;
		}
		else if(cellsWin[1][1] * cellsWin[0][1] == 25 && board.cells[2][1].content == Seed.NO_SEED) {
			currentState = board.stepGame(currentPlayer, 2, 1);
			cellsWin[2][1] = 4;
		}
		else if(cellsWin[1][1] * cellsWin[1][0] == 25 && board.cells[1][2].content == Seed.NO_SEED) {
			currentState = board.stepGame(currentPlayer, 1, 2);
			cellsWin[1][2] = 4;
		}
		else if(cellsWin[1][1] * cellsWin[2][1] == 25 && board.cells[0][1].content == Seed.NO_SEED) {
			currentState = board.stepGame(currentPlayer, 0, 1);
			cellsWin[0][1] = 4;
		}
		else if(cellsWin[1][1] * cellsWin[1][2] == 25 && board.cells[1][0].content == Seed.NO_SEED) {
			currentState = board.stepGame(currentPlayer, 1, 0);
			cellsWin[1][0] = 4;
		}
		else if(cellsWin[1][1] * cellsWin[0][2] == 25 && board.cells[2][0].content == Seed.NO_SEED) {
			currentState = board.stepGame(currentPlayer, 2, 0);
			cellsWin[2][0] = 4;
		}
		else if(cellsWin[1][1] * cellsWin[2][0] == 25 && board.cells[0][2].content == Seed.NO_SEED) {
			currentState = board.stepGame(currentPlayer, 0, 2);
			cellsWin[0][2] = 4;
		}
		else if(cellsWin[0][2] * cellsWin[2][2] == 25 && board.cells[1][2].content == Seed.NO_SEED) {
			currentState = board.stepGame(currentPlayer, 1, 2);
			cellsWin[1][2] = 4;
		}
		else if(cellsWin[2][2] * cellsWin[2][0] == 25 && board.cells[2][1].content == Seed.NO_SEED) {
			currentState = board.stepGame(currentPlayer, 2, 1);
			cellsWin[2][1] = 4;
		}
		else if(cellsWin[0][2] * cellsWin[2][2] == 16 && board.cells[1][2].content == Seed.NO_SEED) {
			currentState = board.stepGame(currentPlayer, 1, 2);
			cellsWin[1][2] = 4;
		}
		else if(cellsWin[2][2] * cellsWin[2][0] == 16 && board.cells[2][1].content == Seed.NO_SEED) {
			currentState = board.stepGame(currentPlayer, 2, 1);
			cellsWin[2][1] = 4;
		}
		else if(board.cells[0][2].content == Seed.NO_SEED) {
			currentState = board.stepGame(currentPlayer, 0, 2);
			cellsWin[0][2] = 4;
		}else if(board.cells[1][2].content == Seed.NO_SEED){
			currentState = board.stepGame(currentPlayer, 1, 2);
			cellsWin[1][2] = 4;
		}else if(board.cells[2][1].content == Seed.NO_SEED){
			currentState = board.stepGame(currentPlayer, 2, 1);
			cellsWin[2][1] = 4;
		}else if(board.cells[2][0].content == Seed.NO_SEED){
			currentState = board.stepGame(currentPlayer, 2, 0);
			cellsWin[2][0] = 4;
		}
		
		
		
	}
	
	public void Zzz() {
		for (int i = 0; i < cellsWin.length; i++) {
			for (int j = 0; j < cellsWin.length; j++) {
				System.out.print(cellsWin[i][j]);
			}
			System.out.println();
		}
	}

	/** Metodo main */
	public static void main(String[] args) {
		new GameMain(); // Llamada al constructor.
	}
}