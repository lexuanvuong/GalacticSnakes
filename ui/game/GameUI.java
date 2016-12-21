package ui.game;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import elements.Meteorites;
import elements.Snake;
import elements.Star;

public class GameUI extends JPanel {
	public static final int FACTOR = 15;
	public static final int WIDTH = 65;
	public static final int HEIGHT = 45;
	private double tstart, tend;
	private Font fontPanel;
	private Star star;
	private Meteorites meteorites;
	private Snake[] snake = new Snake[2];
	private BufferedImage IStar;
	private BufferedImage IHeadup;
	private BufferedImage IHeadleft;
	private BufferedImage IHeadright;
	private BufferedImage IHeaddown;
	private BufferedImage IBody1;
	private BufferedImage ISteroid;
	private BufferedImage IBodyRed;
	private BufferedImage IBodyWhite;
	private BufferedImage IBodyYellow;
	private BufferedImage IBodyOrange;
	private BufferedImage IFinishGame;
	private BufferedImage IButtonreplay1, IButtonreplay2;
	private BufferedImage IButtonexit1, IButtonexit2;
	private BufferedImage Ibackground;
	private int randomColor;
	private boolean gamenotover = true;
	private double k1;
	private double k2;
	private int b1;
	private double b2;
	private boolean maxspeedcolor1 = false;
	private boolean maxspeedcolor2 = false;
	private boolean colorchanger1 = false;
	private int player1speed=100;
	private int player2speed=100;
	private String name1player;
	private String name2player;
	private JButton mainmenu;
	private JButton exit;
	public GameUI() {
		
	}
	public void Init(){
		loadImages();
		LoadButtonsImages();
		CreateLines();
		AddMainMenuButton();
		AddExitButton();
	}
	public Color color(int red, int green, int blue) {
		return new Color(red, green, blue);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (IsGameFinished()) {
			drawFinishGame(g);
		} else {
			g.drawImage(Ibackground, 0, 0, FACTOR * WIDTH, FACTOR * HEIGHT, null);// Background
			drawStar(g);
			drawSteroids(g);
			// Snake
			drawSnakeHead1Player(g);
			drawSnakeBody1Player(g);
			drawSnakeHead2Player(g);
			drawSnakeBody2Player(g);
			drawBorder(g);
			Font fontPanel = new Font("Times New Roman", Font.PLAIN, 20);
			g.setFont(fontPanel);
			drawSpeedColor1Player(g);
			drawSpeedColor2Player(g);
			drawTimer(g);
			drawPlayersName(g);
			drawSnakesLength(g);
		}

	}

	public void drawStar(Graphics g) {
		g.drawImage(IStar, getStar().getStarX() * FACTOR, getStar().getStarY() * FACTOR, FACTOR, FACTOR, null);
	}

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void drawSteroids(Graphics g) {
		g.drawImage(ISteroid, (getMeteorites().getMeteoriteX()[0] * FACTOR), getMeteorites().getMeteoriteY()[0] * FACTOR,
				FACTOR, FACTOR, null);
		g.drawImage(ISteroid, (getMeteorites().getMeteoriteX()[1] * FACTOR), getMeteorites().getMeteoriteY()[1] * FACTOR,
				FACTOR, FACTOR, null);
		g.drawImage(ISteroid, (getMeteorites().getMeteoriteX()[2] * FACTOR), getMeteorites().getMeteoriteY()[2] * FACTOR,
				FACTOR, FACTOR, null);
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void drawSnakeHead1Player(Graphics g) {
		if (getSnake()[0].getDirection() == 0) {
			g.drawImage(IHeadright, getSnake()[0].getSnakex()[0] * FACTOR, getSnake()[0].getSnakey()[0] * FACTOR,
					FACTOR, FACTOR, null);
		} else if (getSnake()[0].getDirection() == 1) {
			g.drawImage(IHeaddown, getSnake()[0].getSnakex()[0] * FACTOR, getSnake()[0].getSnakey()[0] * FACTOR, FACTOR,
					FACTOR, null);
		} else if (getSnake()[0].getDirection() == 2) {
			g.drawImage(IHeadleft, getSnake()[0].getSnakex()[0] * FACTOR, getSnake()[0].getSnakey()[0] * FACTOR, FACTOR,
					FACTOR, null);
		} else if (getSnake()[0].getDirection() == 3) {
			g.drawImage(IHeadup, getSnake()[0].getSnakex()[0] * FACTOR, getSnake()[0].getSnakey()[0] * FACTOR, FACTOR,
					FACTOR, null);
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------
	public void drawSnakeBody1Player(Graphics g) {
		if (!maxspeedcolor1) {
			if (getSnake()[0].getLength() == getSnake()[1].getLength()) {
				for (int d = 1; d < getSnake()[0].getLength(); d++) {
					g.drawImage(IBody1, getSnake()[0].getSnakex()[d] * FACTOR, getSnake()[0].getSnakey()[d] * FACTOR,
							FACTOR, FACTOR, null);
				}
			} else if (getSnake()[0].getLength() > getSnake()[1].getLength()) {
				for (int d = 1; d < getSnake()[0].getLength(); d++) {
					g.drawImage(IBodyRed, getSnake()[0].getSnakex()[d] * FACTOR, getSnake()[0].getSnakey()[d] * FACTOR,
							FACTOR, FACTOR, null);
				}
			} else {
				for (int d = 1; d < getSnake()[0].getLength(); d++) {
					g.drawImage(IBodyWhite, getSnake()[0].getSnakex()[d] * FACTOR,
							getSnake()[0].getSnakey()[d] * FACTOR, FACTOR, FACTOR, null);
				}
			}
		} else if (colorchanger1) {
			for (int d = 1; d < getSnake()[0].getLength(); d++) {
				g.drawImage(IBodyYellow, getSnake()[0].getSnakex()[d] * FACTOR, getSnake()[0].getSnakey()[d] * FACTOR,
						FACTOR, FACTOR, null);
			}
			colorchanger1 = false;
		} else {
			for (int d = 1; d < getSnake()[0].getLength(); d++) {
				g.drawImage(IBodyOrange, getSnake()[0].getSnakex()[d] * FACTOR, getSnake()[0].getSnakey()[d] * FACTOR,
						FACTOR, FACTOR, null);
			}
			colorchanger1 = true;
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------
	public void drawSnakeHead2Player(Graphics g) {
		if (getSnake()[1].getDirection() == 0) {
			g.drawImage(IHeadright, getSnake()[1].getSnakex()[0] * FACTOR, getSnake()[1].getSnakey()[0] * FACTOR,
					FACTOR, FACTOR, null);
		} else if (getSnake()[1].getDirection() == 1) {
			g.drawImage(IHeaddown, getSnake()[1].getSnakex()[0] * FACTOR, getSnake()[1].getSnakey()[0] * FACTOR, FACTOR,
					FACTOR, null);
		} else if (getSnake()[1].getDirection() == 2) {
			g.drawImage(IHeadleft, getSnake()[1].getSnakex()[0] * FACTOR, getSnake()[1].getSnakey()[0] * FACTOR, FACTOR,
					FACTOR, null);
		} else if (getSnake()[1].getDirection() == 3) {
			g.drawImage(IHeadup, getSnake()[1].getSnakex()[0] * FACTOR, getSnake()[1].getSnakey()[0] * FACTOR, FACTOR,
					FACTOR, null);
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------
	public void drawSnakeBody2Player(Graphics g) {
		if (!maxspeedcolor2) {
			if (getSnake()[0].getLength() == getSnake()[1].getLength()) {
				for (int d = 1; d < getSnake()[1].getLength(); d++) {
					g.drawImage(IBody1, getSnake()[1].getSnakex()[d] * FACTOR, getSnake()[1].getSnakey()[d] * FACTOR,
							FACTOR, FACTOR, null);
				}
			} else if (getSnake()[0].getLength() > getSnake()[1].getLength()) {
				for (int d = 1; d < getSnake()[1].getLength(); d++) {
					g.drawImage(IBodyWhite, getSnake()[1].getSnakex()[d] * FACTOR,
							getSnake()[1].getSnakey()[d] * FACTOR, FACTOR, FACTOR, null);
				}
			} else {
				for (int d = 1; d < getSnake()[1].getLength(); d++) {
					g.drawImage(IBodyRed, getSnake()[1].getSnakex()[d] * FACTOR, getSnake()[1].getSnakey()[d] * FACTOR,
							FACTOR, FACTOR, null);
				}
			}
		} else if (colorchanger1) {
			for (int d = 1; d < getSnake()[1].getLength(); d++) {
				g.drawImage(IBodyYellow, getSnake()[1].getSnakex()[d] * FACTOR, getSnake()[1].getSnakey()[d] * FACTOR,
						FACTOR, FACTOR, null);
			}
			colorchanger1 = false;
		} else {
			for (int d = 1; d < getSnake()[1].getLength(); d++) {
				g.drawImage(IBodyOrange, getSnake()[1].getSnakex()[d] * FACTOR, getSnake()[1].getSnakey()[d] * FACTOR,
						FACTOR, FACTOR, null);
			}
			colorchanger1 = true;
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------
	public void drawBorder(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawLine(0, (int) HEIGHT * FACTOR * 2 / 3, (int) WIDTH * FACTOR * 1 / 3, HEIGHT * FACTOR);
		g.drawLine(WIDTH * FACTOR, (int) HEIGHT * FACTOR * 2 / 3, (int) WIDTH * FACTOR * 2 / 3, HEIGHT * FACTOR);
		g.drawLine((int) WIDTH * FACTOR * 1 / 3 - 80,
				HEIGHT * FACTOR - (HEIGHT * FACTOR - (int) HEIGHT * FACTOR * 2 / 3) * 1 / 4,
				(int) WIDTH * FACTOR * 2 / 3 + 80,
				HEIGHT * FACTOR - (HEIGHT * FACTOR - (int) HEIGHT * FACTOR * 2 / 3) * 1 / 4);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------
	public void drawSpeedColor1Player(Graphics g) {
		if (player1speed == 15000) {
			randomColor = (int) (Math.random() * 255);
			g.setColor(color(255, randomColor, 70));
			g.drawString("скорость света", 0,
					(int) (HEIGHT * FACTOR * 2 / 3) + (HEIGHT * FACTOR - (int) HEIGHT * FACTOR * 2 / 3) * 2 / 4);
		} else {
			g.setColor(Color.CYAN);
			g.drawString("MS : " + player1speed, 20,
					(int) (HEIGHT * FACTOR * 2 / 3) + (HEIGHT * FACTOR - (int) HEIGHT * FACTOR * 2 / 3) * 2 / 4);
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------
	public void drawSpeedColor2Player(Graphics g) {
		if (player2speed == 15000) {
			randomColor = (int) (Math.random() * 255);
			g.setColor(color(randomColor, 255, 70));
			g.drawString("скорость света",
					(int) ((((int) (HEIGHT * 2 / 3) + (HEIGHT - (int) HEIGHT * 2 / 3) * 2 / 4 - b2) / k2) + 2) * FACTOR,
					(int) (HEIGHT * FACTOR * 2 / 3) + (HEIGHT * FACTOR - (int) HEIGHT * FACTOR * 2 / 3) * 2 / 4);
		} else {
			g.setColor(Color.CYAN);
			g.drawString("MS : " + player2speed,
					(int) ((((int) (HEIGHT * 2 / 3) + (HEIGHT - (int) HEIGHT * 2 / 3) * 2 / 4 - b2) / k2) + 4) * FACTOR,
					(int) (HEIGHT * FACTOR * 2 / 3) + (HEIGHT * FACTOR - (int) HEIGHT * FACTOR * 2 / 3) * 2 / 4);
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------
	public void drawTimer(Graphics g) {
		tend = System.currentTimeMillis();
		if ((tend - tstart) / 1000 >= 50) {
			randomColor = (int) (Math.random() * 255);
			g.setColor(color(randomColor, 255, 70));
		} else {
			g.setColor(Color.LIGHT_GRAY);
			tend = System.currentTimeMillis();
		}
		g.drawString("Time: " + (((tend - tstart) / 1000)), WIDTH * FACTOR / 2 - 50,
				HEIGHT * FACTOR - (HEIGHT * FACTOR - (int) HEIGHT * FACTOR * 2 / 3) * 1 / 4 * 1 / 2);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------
	public void drawFinishGame(Graphics g) {
		fontPanel = new Font("Times New Roman", Font.PLAIN, 150);
		g.setFont(fontPanel);
		g.drawImage(IFinishGame, 0, 0, FACTOR * WIDTH, FACTOR * HEIGHT, null);
		fontPanel = new Font("Times New Roman", Font.PLAIN, 60);
		g.setFont(fontPanel);
		g.setColor(Color.BLACK);
		if (getSnake()[0].getLength() == getSnake()[1].getLength()) {
			g.drawString(" Draw ", WIDTH * FACTOR / 2 - 85, 15 * FACTOR);
			fontPanel = new Font("Times New Roman", Font.PLAIN, 30);
			g.setFont(fontPanel);
			g.drawString("YourLength: " + getSnake()[0].getLength(), WIDTH * FACTOR / 2 - 85, 21 * FACTOR);
		} else if (getSnake()[0].getLength() > getSnake()[1].getLength()) {
			g.drawString(" You've won ", WIDTH * FACTOR / 2 - 150, 15 * FACTOR);
			fontPanel = new Font("Times New Roman", Font.PLAIN, 30);
			g.setFont(fontPanel);
			g.drawString("YourLength: " + getSnake()[0].getLength(), WIDTH * FACTOR / 2 - 85, 21 * FACTOR);
		} else {
			g.drawString(" You've won(NO)", WIDTH * FACTOR / 2 - 170, 15 * FACTOR);
			fontPanel = new Font("Times New Roman", Font.PLAIN, 30);
			g.setFont(fontPanel);
			g.drawString("YourLength: " + getSnake()[0].getLength(), WIDTH * FACTOR / 2 - 85, 21 * FACTOR);
		}
		setGamenotover(false);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------
	public void drawPlayersName(Graphics g) {
		g.setColor(Color.ORANGE);
		g.drawString(name1player, 30,
				(int) (HEIGHT * FACTOR * 2 / 3) + ((HEIGHT * FACTOR - (int) HEIGHT * FACTOR * 2 / 3) * 4 / 5));
		g.drawString(name2player,
				(int) ((((int) (HEIGHT * 2 / 3) + ((HEIGHT - (int) HEIGHT * 2 / 3) * 4 / 5) - b2) / k2) + 12) * FACTOR,
				(int) (HEIGHT * FACTOR * 2 / 3) + ((HEIGHT * FACTOR - (int) HEIGHT * FACTOR * 2 / 3) * 4 / 5));
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------
	public void drawSnakesLength(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("Length: " + getSnake()[0].getLength(), 20,
				((int) (HEIGHT * FACTOR * 2 / 3) + (HEIGHT * FACTOR - (int) HEIGHT * FACTOR * 2 / 3) * 3 / 4) - 20);
		g.drawString("Length: " + getSnake()[1].getLength(),
				(int) ((((int) (HEIGHT * 2 / 3) + (HEIGHT - (int) HEIGHT * 2 / 3) * 3 / 4 - b2) / k2) + 10) * FACTOR,
				((int) (HEIGHT * FACTOR * 2 / 3) + (HEIGHT * FACTOR - (int) HEIGHT * FACTOR * 2 / 3) * 3 / 4) - 20);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------
	public boolean IsGameFinished() {
		tend = System.currentTimeMillis();
		if ((tend - tstart) / 1000 >= 60) {
			mainmenu.setVisible(true);
			exit.setVisible(true);
			return true;
		} else {
			return false;
		}
	}

	public void CreateLines() {
		k1 = (double) (HEIGHT - (int) (HEIGHT * 2 / 3)) / (int) (WIDTH * 1 / 3);
		b1 = (int) (HEIGHT * 2 / 3);
		k2 = (double) (HEIGHT - (int) (HEIGHT * 2 / 3)) / ((int) (WIDTH * 2 / 3) - WIDTH);
		b2 = Math.round(HEIGHT * 2 / 3) - WIDTH * k2;
	}
	public void AddMainMenuButton() {
		mainmenu = new JButton(new ImageIcon(IButtonreplay1));
		mainmenu.setBorder(BorderFactory.createEmptyBorder());
		mainmenu.setContentAreaFilled(false);
		setLayout(null);
		mainmenu.setBounds(WIDTH * FACTOR / 2 - 70, 400, 150, 50);
		add(mainmenu);
		mainmenu.setVisible(false);
	}
	public void AddExitButton() {
		exit = new JButton(new ImageIcon(IButtonexit1));
		exit.setBorder(BorderFactory.createEmptyBorder());
		exit.setContentAreaFilled(false);
		exit.setBounds(WIDTH * FACTOR / 2 - 70, 500, 150, 50);
		add(exit);
		exit.setVisible(false);
	}
	public void loadImages() {
		try {
			Ibackground = ImageIO.read(getClass().getResourceAsStream("/images/qwe.jpg"));
			IStar = ImageIO.read(getClass().getResourceAsStream("/images/Superstar.png"));
			IHeadup = ImageIO.read(getClass().getResourceAsStream("/images/upmouth.png"));
			IHeaddown = ImageIO.read(getClass().getResourceAsStream("/images/downmouth.png"));
			IHeadleft = ImageIO.read(getClass().getResourceAsStream("/images/leftmouth.png"));
			IHeadright = ImageIO.read(getClass().getResourceAsStream("/images/rightmouth.png"));
			IBody1 = ImageIO.read(getClass().getResourceAsStream("/images/snakeimage.png"));
			ISteroid = ImageIO.read(getClass().getResourceAsStream("/images/ability4.png"));
			IBodyRed = ImageIO.read(getClass().getResourceAsStream("/images/REd.png"));
			IBodyWhite = ImageIO.read(getClass().getResourceAsStream("/images/White.png"));
			IBodyOrange = ImageIO.read(getClass().getResourceAsStream("/images/Orange.png"));
			IBodyYellow = ImageIO.read(getClass().getResourceAsStream("/images/Yellow.png"));
			IFinishGame = ImageIO.read(getClass().getResourceAsStream("/images/66793518-generic-wallpapers.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void LoadButtonsImages() {
		try {
			IButtonreplay1 = ImageIO.read(getClass().getResource("/images/button_main-menu.png"));
			IButtonreplay2 = ImageIO.read(getClass().getResource("/images/button_main-menu (1).png"));
			IButtonexit2 = ImageIO.read(getClass().getResource("/images/button_exit (3).png"));
			IButtonexit1 = ImageIO.read(getClass().getResource("/images/button_exit.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public double getK1() {
		return k1;
	}

	public void setK1(double k1) {
		this.k1 = k1;
	}

	public double getK2() {
		return k2;
	}

	public void setK2(double k2) {
		this.k2 = k2;
	}

	public int getB1() {
		return b1;
	}

	public void setB1(int b1) {
		this.b1 = b1;
	}

	public double getB2() {
		return b2;
	}

	public void setB2(double b2) {
		this.b2 = b2;
	}

	public Meteorites getMeteorites() {
		return meteorites;
	}

	public void setMeteorites(Meteorites meteorites) {
		this.meteorites = meteorites;
	}

	public Snake[] getSnake() {
		return snake;
	}

	public void setSnake(Snake[] snake) {
		this.snake = snake;
	}

	public Star getStar() {
		return star;
	}

	public void setStar(Star star) {
		this.star = star;
	}
	public boolean isGamenotover() {
		return gamenotover;
	}
	public void setGamenotover(boolean gamenotover) {
		this.gamenotover = gamenotover;
	}
	public BufferedImage getIButtonreplay2() {
		return IButtonreplay2;
	}
	public void setIButtonreplay2(BufferedImage iButtonreplay2) {
		IButtonreplay2 = iButtonreplay2;
	}
	public BufferedImage getIButtonreplay1() {
		return IButtonreplay1;
	}
	public void setIButtonreplay1(BufferedImage iButtonreplay1) {
		IButtonreplay1 = iButtonreplay1;
	}
	public JButton getMainmenu() {
		return mainmenu;
	}
	public void setMainmenu(JButton mainmenu) {
		this.mainmenu = mainmenu;
	}
	public BufferedImage getIButtonexit1() {
		return IButtonexit1;
	}
	public void setIButtonexit1(BufferedImage iButtonexit1) {
		IButtonexit1 = iButtonexit1;
	}
	public BufferedImage getIButtonexit2() {
		return IButtonexit2;
	}
	public void setIButtonexit2(BufferedImage iButtonexit2) {
		IButtonexit2 = iButtonexit2;
	}
	public JButton getExit() {
		return exit;
	}
	public void setExit(JButton exit) {
		this.exit = exit;
	}
	public String getName1player() {
		return name1player;
	}
	public void setName1player(String name1player) {
		this.name1player = name1player;
	}
	public double getTstart() {
		return tstart;
	}
	public void setTstart(double tstart) {
		this.tstart = tstart;
	}
	public double getTend() {
		return tend;
	}
	public void setTend(double tend) {
		this.tend = tend;
	}
	public boolean isMaxspeedcolor1() {
		return maxspeedcolor1;
	}
	public void setMaxspeedcolor1(boolean maxspeedcolor1) {
		this.maxspeedcolor1 = maxspeedcolor1;
	}
	public int getPlayer1speed() {
		return player1speed;
	}
	public void setPlayer1speed(int player1speed) {
		this.player1speed = player1speed;
	}
	public int getPlayer2speed() {
		return player2speed;
	}
	public void setPlayer2speed(int player2speed) {
		this.player2speed = player2speed;
	}
	public boolean isMaxspeedcolor2() {
		return maxspeedcolor2;
	}
	public void setMaxspeedcolor2(boolean maxspeedcolor2) {
		this.maxspeedcolor2 = maxspeedcolor2;
	}
	public boolean isColorchanger1() {
		return colorchanger1;
	}
	public void setColorchanger1(boolean colorchanger1) {
		this.colorchanger1 = colorchanger1;
	}
	public String getName2player() {
		return name2player;
	}
	public void setName2player(String name2player) {
		this.name2player = name2player;
	}
}
