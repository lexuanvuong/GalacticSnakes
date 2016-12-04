
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
			SteroidX[0] = (int) (Math.random() * 53);
			SteroidY[0] = (int) (Math.random() * 13);
			SteroidX[1] = (int) (Math.random() * 58);
			SteroidY[1] = (int) (Math.random() * 12 + 14);
			SteroidX[2] = (int) (Math.random() * 19 + 19);
			SteroidY[2] = (int) (Math.random() * 12 + 27);
			for (int i = 0; i <= galacticsnakes.getSnake().length - 1; i++) {
				count = 0;
				for (int k = 0; k <= 2; k++) {
					if ((SteroidX[k] != galacticsnakes.getSnake()[0].getSnakex()[i]) && (SteroidY[k] != galacticsnakes.getSnake()[0].getSnakey()[i])) {
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
}
