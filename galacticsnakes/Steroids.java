package galacticsnakes;

public class Steroids {
	private int[] SteroidX = new int[3];
	private int[] SteroidY = new int[3];
	private GalacticSnakes galacticsnakes;

	public Steroids(GalacticSnakes galacticsnakes) {
		this.galacticsnakes = galacticsnakes;
	}

	public void Random() {
		boolean go = true;
		int count = 0;
		while (go) {
			SteroidX[0] = (int) (Math.random() * GalacticSnakes.WIDTH);
			SteroidY[0] = (int) (Math.random() * (int) (GalacticSnakes.HEIGHT * 1 / 3));
			SteroidX[1] = (int) (Math.random() * GalacticSnakes.WIDTH);
			SteroidY[1] = (int) (Math.random() * (int) (GalacticSnakes.HEIGHT * 1 / 3)
					+ (int) (GalacticSnakes.HEIGHT * 1 / 3));
			SteroidX[2] = (int) (Math.random() * (GalacticSnakes.WIDTH * 1 / 3) + (GalacticSnakes.WIDTH * 1 / 3));
			SteroidY[2] = (int) (Math.random() * (int) (GalacticSnakes.HEIGHT * 1 / 3) - 5
					+ (int) (GalacticSnakes.HEIGHT * 2 / 3));
			for (int i = 0; i <= galacticsnakes.getSnake().length - 1; i++) {
				count = 0;
				for (int k = 0; k <= 2; k++) {
					if ((SteroidX[k] != galacticsnakes.getSnake()[0].getSnakex()[i])
							&& (SteroidY[k] != galacticsnakes.getSnake()[0].getSnakey()[i])) {
						count++;
					}
					for (int j = 0; j <= 2; j++) {
						if ((SteroidX[k] != SteroidX[j]) && (SteroidY[k] != SteroidY[j])) {
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

	public int[] getSteroidX() {
		return SteroidX;
	}

	public int[] getSteroidY() {
		return SteroidY;
	}

	public void setSteroidY(int[] steroidY) {
		SteroidY = steroidY;
	}

	public void setSteroidX(int[] steroidX) {
		SteroidX = steroidX;
	}
}
