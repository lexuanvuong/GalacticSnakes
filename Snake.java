package elements;
import game.Game;
public class Snake {
	private Game game;
	private int Direction = 0;
	private int Length;
	private int[] Snakex = new int[100];
	private int[] Snakey = new int[100];

	public Snake() {

	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public Snake(int x, int y, int length, Game game) {
		Snakex[0] = x;
		Snakey[0] = y;
		this.setLength(length);
		this.game = game;

	}
	public void EatBody(){
		for (int i = 1; i < Length; i++) {
			if ((Snakex[0] ==Snakex[i]) && (Snakey[0] == Snakey[i])) {
				Length = Length - (Length - i);
			}
		}
	}
	public void EatStar() {
		if ((game.getStar().getStarX() == game.getSnake()[0].getSnakex()[0])
				&& (game.getStar().getStarY() == game.getSnake()[0].getSnakey()[0])) {
			game.getSnake()[0].setLength(game.getSnake()[0].getLength() + 2);
			game.getStar().Random();
			game.setStarDate(0);
			game.setNewStar(true);
		}
	}
	
	public void EatSteroids() {
		for (int i = 0; i <= 2; i++) {
			if ((game.getMeteorites().getMeteoriteX()[i] == game.getSnake()[0].getSnakex()[0])
					&& (game.getMeteorites().getMeteoriteY()[i] == game.getSnake()[0].getSnakey()[0])) {
				game.getMeteorites().Random();
				game.setSteroidsDate(0);
				if (game.getSnake()[0].getLength() > 2)
					game.getSnake()[0].setLength(game.getSnake()[0].getLength() - 2);
				game.setNewSteroids(true);
			}
		}
	}

	public void Run() {
		new Thread() {
			public void run() {
				while (game.isGamenotover()) {
					try {
						sleep(game.getSpeed() / 200);
						move();
						EatBody();
						EatStar();
						EatSteroids();
					} catch (InterruptedException e) {
						break;
					}
				}
			}
		}.start();
	}

	public void Check() {
		new Thread() {
			public void run() {
				while (game.isGamenotover()) {
					try {
						sleep(20);
						EatStar();
						EatSteroids();
					} catch (InterruptedException e) {
						break;
					}
				}
			}
		}.start();
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void move() {
		for (int d = getLength() - 1; d > 0; d--) {
			getSnakex()[d] = getSnakex()[d - 1];
			getSnakey()[d] = getSnakey()[d - 1];
		}
		if (Direction == 0)
			getSnakex()[0]++;
		if (Direction == 1)
			getSnakey()[0]++;
		if (Direction == 2)
			getSnakex()[0]--;
		if (Direction == 3)
			getSnakey()[0]--;
		CollideBorder();
	}

	public void CollideBorder() {
		if (getSnakey()[0] < (int) (Game.HEIGHT * 2 / 3)) {
			if (getSnakex()[0] == Game.WIDTH)
				getSnakex()[0] = 0;
			if (getSnakex()[0] < 0)
				getSnakex()[0] = Game.WIDTH - 1;
			if (getSnakey()[0] < 0) {
				if (getSnakex()[0] <= (int) (Game.WIDTH * 1 / 3) - 6) {
					getSnakey()[0] = (int) (Math.round(getSnakex()[0] * game.getK1() + game.getB1())
							- 1);
				} else {
					if (getSnakex()[0] >= (int) (Game.WIDTH * 2 / 3) + 6) {
						getSnakey()[0] = (int) (getSnakex()[0] * game.getK2() + game.getB2()) - 1;
					} else {
						getSnakey()[0] = Game.HEIGHT
								- (Game.HEIGHT - (int) Game.HEIGHT * 2 / 3) * 1 / 4 - 2;
					}
				}
			}
		} else {
			if ((getSnakey()[0] > Game.HEIGHT
					- (Game.HEIGHT - (int) Game.HEIGHT * 2 / 3) * 1 / 4 - 2)
					&& (getSnakex()[0] > (int) (Game.WIDTH * 1 / 3) - 80)
					&& (getSnakex()[0] < (int) (Game.WIDTH * 2 / 3) + 80)) {
				getSnakey()[0] = 0;
			}
			if (getSnakey()[0] > Math.round(getSnakex()[0] * game.getK1() + game.getB1()) - 1) {
				if (Direction == 2)
					getSnakex()[0] = (int) (((getSnakey()[0] - game.getB2()) / game.getK2()) - 1);
				if (Direction == 1)
					getSnakey()[0] = 0;
			}
			if (getSnakey()[0] >= (getSnakex()[0] * game.getK2() + game.getB2()) - 1) {
				if (Direction == 0) {
					getSnakex()[0] = (int) (((getSnakey()[0] - game.getB1()) / game.getK1()) + 1);
				}
				if (Direction == 1) {
					getSnakey()[0] = 0;
				}
			}
		}
	}

	public int getDirection() {
		return Direction;
	}

	public int getLength() {
		return Length;
	}

	public synchronized void  setSnakex(int[] Snakex) {
		this.setSnakex(Snakex);
	}

	public void setDirection(int Direction) {
		this.Direction = Direction;
	}

	public int[] getSnakex() {
		return Snakex;
	}

	public int[] getSnakey() {
		return Snakey;
	}

	public synchronized void setSnakey(int[] Snakey) {
		this.Snakey = Snakey;
	}

	public void setLength(int length) {
		Length = length;
	}

}
