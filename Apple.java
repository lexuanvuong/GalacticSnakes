package snake2;

import snake1.W11;

public class Apple {
   public static int x;
   public static int y;
   public static int x2;
   public static int y2;
   public static int x3;
   public static int y3;
   public static int x1;
   public static int y1;
   public boolean truth=false;
   public Apple (){
	   x=(int)(Math.random()*W11.WIDTH);
	   y=(int)(Math.random()*W11.HEIGHT);
   }
   public void randomxy(){
	   while (true ){
		   x=(int)(Math.random()*W11.WIDTH);
		   y=(int)(Math.random()*W11.HEIGHT);
		   for(int i=0;i<W22.length;i++){
			   if((x==W22.snakex[i]) &&(y==W22.snakey[i]) ){
				   truth=false;
				 
			   }else{
				   truth=true;
				   break;
			   }
		   }
		   if (truth==true){
			   break;
		   }
	   }
   }
   
   public void randomxy1(){
	   while (true ){
		   x1=(int)(Math.random()*W11.WIDTH);
		   y1=(int)(Math.random()*W11.HEIGHT);
		   for(int i=0;i<W22.length;i++){
			   if((x1==W22.snakex[i]) &&(y1==W22.snakey[i]) ){
				   truth=false;
			   }else{
				   truth=true;
			   }
		   }
		   if (truth==true){
			   break;
		   }
	   }
   }
   public void randomxy2(){
	   while (true ){
		   x2=(int)(Math.random()*W11.WIDTH);
		   y2=(int)(Math.random()*W11.HEIGHT);
		   for(int i=0;i<W22.length;i++){
			   if((x2==W22.snakex[i]) &&(y2==W22.snakey[i]) ){
				   truth=false;
				 
			   }else{
				   truth=true;
				   break;
			   }
		   }
		   if (truth==true){
			   break;
		   }
	   }
   }
   public void randomxy3(){
	   while (true ){
		   x3=(int)(Math.random()*W11.WIDTH);
		   y3=(int)(Math.random()*W11.HEIGHT);
		   for(int i=0;i<W22.length;i++){
			   if((x3==W22.snakex[i]) &&(y3==W22.snakey[i]) ){
				   truth=false;
				 
			   }else{
				   truth=true;
				   break;
			   }
		   }
		   if (truth==true){
			   break;
		   }
	   }
   }
}

