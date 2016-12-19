package galacticsnakes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GalacticSnakes extends JPanel implements KeyListener, Runnable {
	public static final int FACTOR = 15;
	public static final int WIDTH = 65;
	public static final int HEIGHT = 45;
	private Socket socket;
	private String IP;
	private int port;
	private ServerSocket serverSocket;
	private ObjectOutputStream Oos;
	private ObjectInputStream Ois;
	private Scanner sc = new Scanner(System.in);
	private BufferedImage Ibackground;
	private int speed = 20000;
	private int speedup = 800;
	private int speedcounter = 0;
	private int randomColor;
	private int player1speed = 100;
	private int player2speed = 100;
	private int StarDate = 0;
	private int steroidsTime = 0;
	private double k1;
	private double k2;
	private int b1;
	private double b2;
	private String name1player;
	private String name2player;
	private boolean accepted = false;
	private boolean Player1 = false;
	private boolean newStar = false;
	private boolean newSteroids = false;
	private boolean gamenotover = true;
	private boolean maxspeedcolor1 = false;
	private boolean maxspeedcolor2 = false;
	private boolean colorchanger1 = false;
	private boolean Playerleave = false;
	private boolean CorrectIP = true;
	private double tstart, tend;
	private boolean speedchanger = false;
	private Font fontPanel;
	private Thread GalacticSnakes;
	private Star star;
	private Steroids steroids;
	private DataExchange gamedata;
	private Menu menu;
	private Clip clip;
	private JButton mainmenu;
	private JButton exit;
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
	private JFrame jframe;
	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public GalacticSnakes(JFrame jframe, Menu menu, String Name, String IP) {
		this.jframe = jframe;
		this.menu = menu;
		name1player = Name;
		this.IP = IP;
		port = 5;
		loadImages();
		addKeyListener(this);
		setFocusable(true);
		star = new Star(this);
		steroids = new Steroids(this);
		if (!connect()) {
			createServer();
		}
		if (CorrectIP) {
			if (!accepted) {
				waitforrequest();
			}
			ExchangePlayersName();
			// playMusic("Flash.wav");
			GalacticSnakes = new Thread(this, "MMR");
			GalacticSnakes.start();
			tstart = System.currentTimeMillis();
			snake[0].Run();
			// snake[0].Check();

			LoadButtonsImages();
			AddMainMenuButton();
			AddExitButton();
			InitializeDataExchange();
			InitialStarSteroids();
			CreateLines();
		}
	}

	public void InitializeDataExchange() {
		gamedata = new DataExchange(this);
		gamedata.Send();
		gamedata.Receive();
	}

	public void InitialStarSteroids() {
		if (Player1) {
			star.Random();
			steroids.Random();
			gamedata.Send();
			newStar = true;
			newSteroids = true;
		}
	}

	public void ExchangePlayersName() {
		try {
			Oos.writeObject(name1player);
			Oos.flush();
			name2player = (String) Ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void createServer() {
		try {
			serverSocket = new ServerSocket(port, 8, InetAddress.getByName(IP));
			getSnake()[0] = new Snake(WIDTH/2, HEIGHT/2, 6, this);
			getSnake()[1] = new Snake(WIDTH/2, HEIGHT/2+1, 6, this);
			// System.out.println("Server Has been Created");
			Player1 = true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(menu, "Неверный IP-адрес.");
			CorrectIP = false;
		}
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void waitforrequest() {
		Socket socket = null;
		try {
			socket = serverSocket.accept();
			Oos = new ObjectOutputStream(socket.getOutputStream());
			Ois = new ObjectInputStream(socket.getInputStream());
			menu.setVisible(false);
			accepted = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public boolean connect() {
		try {
			socket = new Socket(IP, port);
			Ois = new ObjectInputStream(socket.getInputStream());
			Oos = new ObjectOutputStream(socket.getOutputStream());
			getSnake()[0] = new Snake(WIDTH/2, HEIGHT/2+1, 6, this);
			getSnake()[1] = new Snake(WIDTH/2, HEIGHT/2, 6, this);
			getSnake()[0].setDirection(2);
			accepted = true;
			menu.setVisible(false);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public Color color(int red, int green, int blue) {
		return new Color(red, green, blue);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void checkerror() {
		for (int i = 0; i <= 1; i++) {
			if (getSnake()[i].getLength() >= getSnake()[i].getSnakex().length - 10) {
				int tmpx[] = new int[getSnake()[i].getSnakex().length + 100];
				int tmpy[] = new int[getSnake()[i].getSnakey().length + 100];
				System.arraycopy(getSnake()[i].getSnakex(), 0, tmpx, 0, getSnake()[i].getSnakex().length);
				System.arraycopy(getSnake()[i].getSnakey(), 0, tmpy, 0, getSnake()[i].getSnakey().length);
				snake[i].setSnakex(tmpx);
				snake[i].setSnakey(tmpy);
			}
		}
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void changespeed() {
		if (speedchanger) {
			if (player1speed <= 14950) {
				player1speed += 50;
			}
			if (getSpeed() > 3500) {
				setSpeed(getSpeed() - speedup);
			}
			if (speedup > 50)
				speedup -= 50;
		}
	}

	public void CreateLines() {
		k1 = (double) (HEIGHT - (int) (HEIGHT * 2 / 3)) / (int) (WIDTH * 1 / 3);
		b1 = (int) (HEIGHT * 2 / 3);
		k2 = (double) (HEIGHT - (int) (HEIGHT * 2 / 3)) / ((int) (WIDTH * 2 / 3) - WIDTH);
		b2 = Math.round(HEIGHT * 2 / 3) - WIDTH * k2;
	}

	public void StarUpdate() {
		if (Player1 && StarDate == 3000) {
			getStar().Random();
			StarDate = 0;
			newStar = true;
		}
	}

	public void SteroidsUpdate() {
		if (Player1 && steroidsTime == 8000) {
			getSteroids().Random();
			steroidsTime = 0;
			newSteroids = true;
		}
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void run() {
		while (isGamenotover()) {
			try {
				if (!isPlayerleave()) {
					GalacticSnakes.sleep(1);
					if (player1speed >= 15000) {
						maxspeedcolor1 = true;
					} else {
						maxspeedcolor1 = false;
					}
					if (player2speed >= 15000) {
						maxspeedcolor2 = true;
					} else {
						maxspeedcolor2 = false;
					}
					StarUpdate();
					StarDate++;
					SteroidsUpdate();
					speedcounter++;
					steroidsTime++;
					if (speedcounter == 4) {
						changespeed();
						speedcounter = 0;
					}
					checkerror();
					repaint();
				} else {
					PlayerLeft();
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void PlayerLeft() {
		JOptionPane.showMessageDialog(GalacticSnakes.this, name2player + " покинул игру");
		try {
			if (Player1)
				serverSocket.close();
			else
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		menu.setVisible(true);
		menu.setClicked(false);
		setGamenotover(false);
		menu.getWaitLabel().setVisible(false);
		jframe.dispose();
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void paintComponent(Graphics g) {
		if (CorrectIP) {
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
				fontPanel = new Font("Times New Roman", Font.PLAIN, 20);
				g.setFont(fontPanel);
				drawSpeedColor1Player(g);
				drawSpeedColor2Player(g);
				drawTimer(g);
				drawPlayersName(g);
				drawSnakesLength(g);
			}
		} else {
			jframe.dispose();
			menu.getWaitLabel().setVisible(false);
			menu.setClicked(false);
		}
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void drawStar(Graphics g) {
		g.drawImage(IStar, getStar().getStarX() * FACTOR, getStar().getStarY() * FACTOR, FACTOR, FACTOR, null);
	}

	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void drawSteroids(Graphics g) {
		g.drawImage(ISteroid, (getSteroids().getSteroidX()[0] * FACTOR), getSteroids().getSteroidY()[0] * FACTOR,
				FACTOR, FACTOR, null);
		g.drawImage(ISteroid, (getSteroids().getSteroidX()[1] * FACTOR), getSteroids().getSteroidY()[1] * FACTOR,
				FACTOR, FACTOR, null);
		g.drawImage(ISteroid, (getSteroids().getSteroidX()[2] * FACTOR), getSteroids().getSteroidY()[2] * FACTOR,
				FACTOR, FACTOR, null);
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void drawSnakeHead1Player(Graphics g) {
		if (getSnake()[0].getDirection() == 0) {
			g.drawImage(IHeadright, getSnake()[0].getSnakex()[0] * FACTOR, getSnake()[0].getSnakey()[0] * FACTOR,
					FACTOR, FACTOR, null);
		} else {
			if (getSnake()[0].getDirection() == 1) {
				g.drawImage(IHeaddown, getSnake()[0].getSnakex()[0] * FACTOR, getSnake()[0].getSnakey()[0] * FACTOR,
						FACTOR, FACTOR, null);
			} else {
				if (getSnake()[0].getDirection() == 2) {
					g.drawImage(IHeadleft, getSnake()[0].getSnakex()[0] * FACTOR, getSnake()[0].getSnakey()[0] * FACTOR,
							FACTOR, FACTOR, null);
				} else {
					if (getSnake()[0].getDirection() == 3) {
						g.drawImage(IHeadup, getSnake()[0].getSnakex()[0] * FACTOR,
								getSnake()[0].getSnakey()[0] * FACTOR, FACTOR, FACTOR, null);
					}
				}
			}
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
			} else {
				if (getSnake()[0].getLength() > getSnake()[1].getLength()) {
					for (int d = 1; d < getSnake()[0].getLength(); d++) {
						g.drawImage(IBodyRed, getSnake()[0].getSnakex()[d] * FACTOR,
								getSnake()[0].getSnakey()[d] * FACTOR, FACTOR, FACTOR, null);
					}
				} else {
					for (int d = 1; d < getSnake()[0].getLength(); d++) {
						g.drawImage(IBodyWhite, getSnake()[0].getSnakex()[d] * FACTOR,
								getSnake()[0].getSnakey()[d] * FACTOR, FACTOR, FACTOR, null);
					}
				}
			}
		} else {
			if (colorchanger1) {
				for (int d = 1; d < getSnake()[0].getLength(); d++) {
					g.drawImage(IBodyYellow, getSnake()[0].getSnakex()[d] * FACTOR,
							getSnake()[0].getSnakey()[d] * FACTOR, FACTOR, FACTOR, null);
				}
				colorchanger1 = false;
			} else {
				for (int d = 1; d < getSnake()[0].getLength(); d++) {
					g.drawImage(IBodyOrange, getSnake()[0].getSnakex()[d] * FACTOR,
							getSnake()[0].getSnakey()[d] * FACTOR, FACTOR, FACTOR, null);
				}
				colorchanger1 = true;
			}
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------
	public void drawSnakeHead2Player(Graphics g) {
		if (getSnake()[1].getDirection() == 0) {
			g.drawImage(IHeadright, getSnake()[1].getSnakex()[0] * FACTOR, getSnake()[1].getSnakey()[0] * FACTOR,
					FACTOR, FACTOR, null);
		} else {
			if (getSnake()[1].getDirection() == 1) {
				g.drawImage(IHeaddown, getSnake()[1].getSnakex()[0] * FACTOR, getSnake()[1].getSnakey()[0] * FACTOR,
						FACTOR, FACTOR, null);
			} else {
				if (getSnake()[1].getDirection() == 2) {
					g.drawImage(IHeadleft, getSnake()[1].getSnakex()[0] * FACTOR, getSnake()[1].getSnakey()[0] * FACTOR,
							FACTOR, FACTOR, null);
				} else {
					if (getSnake()[1].getDirection() == 3) {
						g.drawImage(IHeadup, getSnake()[1].getSnakex()[0] * FACTOR,
								getSnake()[1].getSnakey()[0] * FACTOR, FACTOR, FACTOR, null);
					}
				}
			}
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
			} else {
				if (getSnake()[0].getLength() > getSnake()[1].getLength()) {
					for (int d = 1; d < getSnake()[1].getLength(); d++) {
						g.drawImage(IBodyWhite, getSnake()[1].getSnakex()[d] * FACTOR,
								getSnake()[1].getSnakey()[d] * FACTOR, FACTOR, FACTOR, null);
					}
				} else {
					for (int d = 1; d < getSnake()[1].getLength(); d++) {
						g.drawImage(IBodyRed, getSnake()[1].getSnakex()[d] * FACTOR,
								getSnake()[1].getSnakey()[d] * FACTOR, FACTOR, FACTOR, null);
					}
				}
			}
		} else {
			if (colorchanger1) {
				for (int d = 1; d < getSnake()[1].getLength(); d++) {
					g.drawImage(IBodyYellow, getSnake()[1].getSnakex()[d] * FACTOR,
							getSnake()[1].getSnakey()[d] * FACTOR, FACTOR, FACTOR, null);
				}
				colorchanger1 = false;
			} else {
				for (int d = 1; d < getSnake()[1].getLength(); d++) {
					g.drawImage(IBodyOrange, getSnake()[1].getSnakex()[d] * FACTOR,
							getSnake()[1].getSnakey()[d] * FACTOR, FACTOR, FACTOR, null);
				}
				colorchanger1 = true;
			}
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
			g.drawString("Скорость света", 0,
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
			g.drawString("Скорость света",
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
			g.drawString(" Draw ",WIDTH * FACTOR / 2 - 85, 15 * FACTOR);
			fontPanel = new Font("Times New Roman", Font.PLAIN, 30);
			g.setFont(fontPanel);
			g.drawString("YourLength: " + getSnake()[0].getLength(), WIDTH * FACTOR / 2 - 85, 21 * FACTOR);
		} else {
			if (getSnake()[0].getLength() > getSnake()[1].getLength()) {
				g.drawString(" You've won ", WIDTH * FACTOR / 2 - 150, 15 * FACTOR);
				fontPanel = new Font("Times New Roman", Font.PLAIN, 30);
				g.setFont(fontPanel);
				g.drawString("YourLength: " + getSnake()[0].getLength(), WIDTH * FACTOR / 2 - 85, 21 * FACTOR);
			} else {
				g.drawString(" You've won(NO)", WIDTH * FACTOR / 2 -170, 15 * FACTOR);
				fontPanel = new Font("Times New Roman", Font.PLAIN, 30);
				g.setFont(fontPanel);
				g.drawString("YourLength: " + getSnake()[0].getLength(), WIDTH * FACTOR / 2 - 85, 21 * FACTOR);
			}
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
		} else
			return false;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------------------
	public void loadImages() {// IBody1 IBodyblast
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

	public void AddMainMenuButton() {
		mainmenu = new JButton(new ImageIcon(IButtonreplay1));
		mainmenu.setBorder(BorderFactory.createEmptyBorder());
		mainmenu.setContentAreaFilled(false);
		setLayout(null);
		mainmenu.setBounds(WIDTH * FACTOR / 2 - 70, 400, 150, 50);
		add(mainmenu);
		mainmenu.setVisible(false);
		mainmenu.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					if (Player1)
						serverSocket.close();
					else
						socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				jframe.dispose();
				menu.getWaitLabel().setVisible(false);
				menu.setVisible(true);
				menu.setClicked(false);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				mainmenu.setIcon(new ImageIcon(IButtonreplay2));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				mainmenu.setIcon(new ImageIcon(IButtonreplay1));
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});
	}

	public void AddExitButton() {
		exit = new JButton(new ImageIcon(IButtonexit1));
		exit.setBorder(BorderFactory.createEmptyBorder());
		exit.setContentAreaFilled(false);
		exit.setBounds(WIDTH * FACTOR / 2 - 70, 500, 150, 50);
		add(exit);
		exit.setVisible(false);
		exit.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				exit.setIcon(new ImageIcon(IButtonexit2));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				exit.setIcon(new ImageIcon(IButtonexit1));
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public void keyPressed(KeyEvent k) {
		int key = k.getKeyCode();
		if (key == KeyEvent.VK_RIGHT) {
			if (getSnake()[0].getDirection() != 2) {
				if (getSnake()[0].getSnakey()[0] != getSnake()[0].getSnakey()[1]) {
					snake[0].setDirection(0);
				}
			}
		}
		if (key == KeyEvent.VK_DOWN) {
			if (getSnake()[0].getDirection() != 3) {
				if (getSnake()[0].getSnakex()[0] != getSnake()[0].getSnakex()[1]) {
					snake[0].setDirection(1);
				}
			}
		}
		if (key == KeyEvent.VK_UP) {
			if (getSnake()[0].getDirection() != 1) {
				if (getSnake()[0].getSnakex()[0] != getSnake()[0].getSnakex()[1]) {
					snake[0].setDirection(3);
				}
			}
		}
		if (key == KeyEvent.VK_LEFT) {
			if (getSnake()[0].getDirection() != 0) {
				if (getSnake()[0].getSnakey()[0] != getSnake()[0].getSnakey()[1]) {
					snake[0].setDirection(2);
				}
			}
		}
		if (key == KeyEvent.VK_SPACE) {
			speedchanger = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent k) {
		int key1 = k.getKeyCode();
		if (key1 == KeyEvent.VK_SPACE) {
			setSpeed(20000);
			speedup = 800;
			speedchanger = false;
			player1speed = 100;
			player2speed = 100;
			colorchanger1 = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
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

	public Steroids getSteroids() {
		return steroids;
	}

	public void setSteroids(Steroids steroids) {
		this.steroids = steroids;
	}

	public boolean isGamenotover() {
		return gamenotover;
	}

	public void setGamenotover(boolean gamenotover) {
		this.gamenotover = gamenotover;
	}

	public boolean isPlayerleave() {
		return Playerleave;
	}

	public void setPlayerleave(boolean playerleave) {
		Playerleave = playerleave;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isNewStar() {
		return newStar;
	}

	public void setNewStar(boolean newStar) {
		this.newStar = newStar;
	}

	public boolean isNewSteroids() {
		return newSteroids;
	}

	public void setNewSteroids(boolean newSteroids) {
		this.newSteroids = newSteroids;
	}

	public int getPlayer1speed() {
		return player1speed;
	}

	public void setPlayer1speed(int player1speed) {
		this.player1speed = player1speed;
	}

	public ObjectOutputStream getOos() {
		return Oos;
	}

	public void setOos(ObjectOutputStream Oos) {
		this.Oos = Oos;
	}

	public ObjectInputStream getOis() {
		return Ois;
	}

	public void setOis(ObjectInputStream Ois) {
		this.Ois = Ois;
	}

	public int getPlayer2speed() {
		return player2speed;
	}

	public void setPlayer2speed(int player2speed) {
		this.player2speed = player2speed;
	}

	public int getSteroidsDate() {
		return steroidsTime;
	}

	public void setSteroidsDate(int steroidsDate) {
		steroidsTime = steroidsDate;
	}

	public boolean isPlayer1() {
		return Player1;
	}

	public void setPlayer1(boolean player1) {
		Player1 = player1;
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

	public int getStarDate() {
		return StarDate;
	}

	public void setStarDate(int starDate) {
		StarDate = starDate;
	}
}
