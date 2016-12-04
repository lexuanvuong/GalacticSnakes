
public class Snake  {

	private int Direction = 0;
	private int Length;
	private int[] Snakex = new int[50];
	private int[] Snakey = new int[50];

	public Snake() {

	}
	
	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public Snake(int x, int y, int length) {
		Snakex[0] = x;
		Snakey[0] = y;
		this.setLength(length);
	}
        
	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void move() {
		for (int d = getLength() - 1; d > 0; d--) {
			getSnakex()[d] = getSnakex()[d - 1];
			getSnakey()[d] = getSnakey()[d - 1];
		}
		if (getDirection() == 0)
			getSnakex()[0]++;
		if (getDirection() == 1)
			getSnakey()[0]++;
		if (getDirection() == 2)
			getSnakex()[0]--;
		if (getDirection() == 3)
			getSnakey()[0]--;
		if (getSnakey()[0] < 27) {
			if (getSnakex()[0] == GalacticSnakes.WIDTH)
				getSnakex()[0] = 0;
			if (getSnakex()[0] < 0)
				getSnakex()[0] = GalacticSnakes.WIDTH - 1;
			if (getSnakey()[0] < 0) {
				if (getSnakex()[0] <= 13) {
					getSnakey()[0] = Math.round(getSnakex()[0] * 18 / 19 + 27);
				} else {
					if (getSnakex()[0] >= 44) {
						getSnakey()[0] = (-18 * getSnakex()[0]) / 19 + 1557 / 19;
					} else {
						getSnakey()[0]=40;
					}
				}
			}
		} else {
			if((getSnakey()[0]>39) && (getSnakex()[0]>13) && (getSnakex()[0]<44)){
				getSnakey()[0]=0;
			}
			if (getSnakey()[0] > Math.round(getSnakex()[0] * 18 / 19 + 27)) {
				if (getDirection() == 2)
					getSnakex()[0] = (1557- 19* getSnakey()[0]) / (18)-1;
				if (getDirection() == 1)
					getSnakey()[0] = 0;
			}
			if (getSnakey()[0] >= (-18 * getSnakex()[0]) / 19 + 1557 / 19) {
				if (getDirection() == 0) {
					getSnakex()[0] = ((19 * getSnakey()[0]) - (27 * 19)) / 18+1;
				}
				if (getDirection() == 1) {
					getSnakey()[0] = 0;
				}
			}
		}
	}
        public int getDirection(){
            return Direction;
        }
        public int getLength(){
            return Length;
        }


    public void setSnakex(int[] Snakex) {
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
    public void setSnakey(int[] Snakey) {
        this.Snakey = Snakey;
    }
	public void setLength(int length) {
		Length = length;
	}



}
