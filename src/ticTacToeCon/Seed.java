package ticTacToeCon;

public enum Seed {
	CP1("X"), PLAYER("O"), NO_SEED(" ");

	private String icon;

	private Seed(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return icon;
	}

}
