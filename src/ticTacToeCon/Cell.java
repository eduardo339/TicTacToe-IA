package ticTacToeCon;

public class Cell {
	
	Seed content;
	
	int row, col;
	
	//Constructor que inicializa las celdas.
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
		this.content = Seed.NO_SEED;
	}
	
	// Resetea el contenido de las celdas.
	public void newGame() {
		this.content = Seed.NO_SEED;
	}
	
	//Dibuja la celda.
	public void paint() {
		String icon = this.content.getIcon();
		System.out.print(icon);
	}
}
