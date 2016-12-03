
public class Star  {
	private int StarX;
	private int StarY;
	private GalacticSnakes galacticsnakes;

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public Star(GalacticSnakes galacticsnakes) {
		this.galacticsnakes = galacticsnakes;
	}
	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void Random() {
		boolean go = true;
                Snake[] snake=new Snake[2];
                snake=galacticsnakes.getSnake();
		while (go) {
			switch ((int) (Math.random() * 3 + 1)) {
			case 1: {
				setStarX((int) (Math.random() * 53));
				setStarY((int) (Math.random() * 13));
			}
				break;
			case 2: {
				setStarX((int) (Math.random() * 58));
				setStarY((int) (Math.random() * 12 + 14));
			}
				break;
			case 3: {
				setStarX((int) (Math.random() * 19 + 19));
				setStarY((int) (Math.random() * 12 + 27));
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
	public void setStarX(int starX) {
		StarX = starX;
	}
	public void setStarY(int starY) {
		StarY = starY;
	}
        
}
