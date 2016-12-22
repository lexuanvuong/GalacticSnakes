package elements;
import game.Game;

public class Meteorites {
	private int[] MeteoriteX = new int[3];
	private int[] MeteoriteY = new int[3];
	private Game game;

	public Meteorites(Game game) {
		this.game = game;
	}

	public void Random() {
		boolean go = true;
		int count = 0;
		while (go) {
			MeteoriteX[0] = (int) (Math.random() * Game.WIDTH);
			MeteoriteY[0] = (int) (Math.random() * (int) (Game.HEIGHT * 1 / 3));
			MeteoriteX[1] = (int) (Math.random() * Game.WIDTH);
			MeteoriteY[1] = (int) (Math.random() * (int) (Game.HEIGHT * 1 / 3)
					+ (int) (Game.HEIGHT * 1 / 3));
			MeteoriteX[2] = (int) (Math.random() * (Game.WIDTH * 1 / 3) + (Game.WIDTH * 1 / 3));
			MeteoriteY[2] = (int) (Math.random() * (int) (Game.HEIGHT * 1 / 3) - 5
					+ (int) (Game.HEIGHT * 2 / 3));
			for (int i = 0; i <= game.getSnake().length - 1; i++) {
				count = 0;
				for (int k = 0; k <= 2; k++) {
					if ((MeteoriteX[k] != game.getSnake()[0].getSnakex()[i])
							&& (MeteoriteY[k] != game.getSnake()[0].getSnakey()[i])) {
						count++;
					}
					for (int j = 0; j <= 2; j++) {
						if ((MeteoriteX[k] != MeteoriteX[j]) && (MeteoriteY[k] != MeteoriteY[j])) {
							if (count == 3) {
								go = false;
								break;
							}
						}
					}
				}
			}
		}
	}

	public int[] getMeteoriteX() {
		return MeteoriteX;
	}

	public int[] getMeteoriteY() {
		return MeteoriteY;
	}

	public void setMeteoriteY(int[] steroidY) {
		MeteoriteY = steroidY;
	}

	public void setMeteoriteX(int[] steroidX) {
		MeteoriteX = steroidX;
	}
}
