package elements;
import game.Game;
public class Star {
	private int StarX;
	private int StarY;
	private int Direction = 0;
	private int MoveDelay = 0;
	private double k2;
	private Game game;

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public Star(Game game) {
		this.game = game;
		k2 = (double) (Game.HEIGHT - (int) (Game.HEIGHT * 2 / 3))
				/ ((int) (Game.WIDTH * 2 / 3) - Game.WIDTH);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void Random() {
		boolean go = true;
		Snake[] snake = new Snake[2];
		snake = game.getSnake();
		while (go) {
			switch ((int) (Math.random() * 3 + 1)) {
			case 1: {
				setStarX((int) (Math.random() * Game.WIDTH));
				setStarY((int) (Math.random() * (int) (Game.HEIGHT * 1 / 3)));
			}
				break;
			case 2: {
				setStarX((int) (Math.random() * Game.WIDTH));
				setStarY((int) (Math.random() * (int) (Game.HEIGHT * 1 / 3)
						+ (int) (Game.HEIGHT * 1 / 3)));
			}
				break;
			case 3: {
				setStarX((int) (Math.random() * (Game.WIDTH * 1 / 3) + (Game.WIDTH * 1 / 3)));
				setStarY((int) (Math.random() * (int) (Game.HEIGHT * 1 / 3) - 5
						+ (int) (Game.HEIGHT * 2 / 3)));
			}
				break;
			}
			for (int i = 0; i <= snake.length - 1; i++) {
				if ((getStarX() != snake[0].getSnakex()[i]) && (getStarY() != snake[0].getSnakey()[i])) {
					go = false;
				}
			}
		}
	}

	public int getStarX() {
		return StarX;
	}

	public int getStarY() {
		return StarY;
	}

	public synchronized void setStarX(int starX) {
		StarX = starX;
	}

	public synchronized void setStarY(int starY) {
		StarY = starY;
	}

}
