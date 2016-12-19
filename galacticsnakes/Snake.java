package galacticsnakes;

public class Snake {
	private GalacticSnakes galacticsnakes;
	private int Direction = 0;
	private int Length;
	private int[] Snakex = new int[50];
	private int[] Snakey = new int[50];

	public Snake() {

	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public Snake(int x, int y, int length, GalacticSnakes galacticsnakes) {
		Snakex[0] = x;
		Snakey[0] = y;
		this.setLength(length);
		this.galacticsnakes = galacticsnakes;

	}

	public void EatStar() {
		if ((galacticsnakes.getStar().getStarX() == galacticsnakes.getSnake()[0].getSnakex()[0])
				&& (galacticsnakes.getStar().getStarY() == galacticsnakes.getSnake()[0].getSnakey()[0])) {
			galacticsnakes.getSnake()[0].setLength(galacticsnakes.getSnake()[0].getLength() + 2);
			galacticsnakes.getStar().Random();
			galacticsnakes.setStarDate(0);
			galacticsnakes.setNewStar(true);
		}
	}

	public void EatSteroids() {
		for (int i = 0; i <= 2; i++) {
			if ((galacticsnakes.getSteroids().getSteroidX()[i] == galacticsnakes.getSnake()[0].getSnakex()[0])
					&& (galacticsnakes.getSteroids().getSteroidY()[i] == galacticsnakes.getSnake()[0].getSnakey()[0])) {
				galacticsnakes.getSteroids().Random();
				galacticsnakes.setSteroidsDate(0);
				if (galacticsnakes.getSnake()[0].getLength() > 2)
					galacticsnakes.getSnake()[0].setLength(galacticsnakes.getSnake()[0].getLength() - 2);
				galacticsnakes.setNewSteroids(true);
			}
		}
	}

	public void Run() {
		new Thread() {
			public void run() {
				while (galacticsnakes.isGamenotover()) {
					try {
						sleep(galacticsnakes.getSpeed() / 200);
						move();
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
				while (galacticsnakes.isGamenotover()) {
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
		if (getSnakey()[0] < (int) (GalacticSnakes.HEIGHT * 2 / 3)) {
			if (getSnakex()[0] == GalacticSnakes.WIDTH)
				getSnakex()[0] = 0;
			if (getSnakex()[0] < 0)
				getSnakex()[0] = GalacticSnakes.WIDTH - 1;
			if (getSnakey()[0] < 0) {
				if (getSnakex()[0] <= (int) (GalacticSnakes.WIDTH * 1 / 3) - 6) {
					getSnakey()[0] = (int) (Math.round(getSnakex()[0] * galacticsnakes.getK1() + galacticsnakes.getB1())
							- 1);
				} else {
					if (getSnakex()[0] >= (int) (GalacticSnakes.WIDTH * 2 / 3) + 6) {
						getSnakey()[0] = (int) (getSnakex()[0] * galacticsnakes.getK2() + galacticsnakes.getB2()) - 1;
					} else {
						getSnakey()[0] = GalacticSnakes.HEIGHT
								- (GalacticSnakes.HEIGHT - (int) GalacticSnakes.HEIGHT * 2 / 3) * 1 / 4 - 2;
					}
				}
			}
		} else {
			if ((getSnakey()[0] > GalacticSnakes.HEIGHT
					- (GalacticSnakes.HEIGHT - (int) GalacticSnakes.HEIGHT * 2 / 3) * 1 / 4 - 2)
					&& (getSnakex()[0] > (int) (GalacticSnakes.WIDTH * 1 / 3) - 80)
					&& (getSnakex()[0] < (int) (GalacticSnakes.WIDTH * 2 / 3) + 80)) {
				getSnakey()[0] = 0;
			}
			if (getSnakey()[0] > Math.round(getSnakex()[0] * galacticsnakes.getK1() + galacticsnakes.getB1()) - 1) {
				if (Direction == 2)
					getSnakex()[0] = (int) (((getSnakey()[0] - galacticsnakes.getB2()) / galacticsnakes.getK2()) - 1);
				if (Direction == 1)
					getSnakey()[0] = 0;
			}
			if (getSnakey()[0] >= (getSnakex()[0] * galacticsnakes.getK2() + galacticsnakes.getB2()) - 1) {
				if (Direction == 0) {
					getSnakex()[0] = (int) (((getSnakey()[0] - galacticsnakes.getB1()) / galacticsnakes.getK1()) + 1);
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
