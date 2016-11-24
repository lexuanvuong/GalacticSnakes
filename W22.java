package snake2;

import snake1.W11;

public class W22 {
    public int direction =0;
    public static int length=20;
    public static int[] snakex=new int[50];
    public static int[] snakey=new int[50];
    public W22(int x0,int y0,int x1,int y1){
    	snakex[0]=x0;
    	snakey[0]=y0;
    	snakex[1]=x1;
    	snakey[1]=y1;
    }
    public void move(){
    	for(int d=length-1;d>0;d--){
    		snakex[d]=snakex[d-1];
        	snakey[d]=snakey[d-1];
    	}
    	if (direction==0) snakex[0]++;
    	if (direction==1) snakey[0]++;
    	if (direction==2) snakex[0]--;
    	if (direction==3) snakey[0]--;
    	if (snakex[0]==W11.WIDTH  ){
    		snakex[0]=0;
    	}
    	if (snakex[0]<0){
    		snakex[0]=W11.WIDTH-1;
    	}
    	if (snakey[0]==W11.HEIGHT ){
    		snakey[0]=0;
    	}
    	if (snakey[0]<0 ){
    		snakey[0]=W11.HEIGHT-1;
    	}
    }
}
