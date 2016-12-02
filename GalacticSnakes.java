import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GalacticSnakes extends JPanel implements KeyListener, Runnable {
	public static final int FACTOR = 20;
	public static final int WIDTH = 58;
	public static final int HEIGHT = 45;
	private Socket socket;
	private String ip;
	private int port;
	private ServerSocket serverSocket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Scanner sc = new Scanner(System.in);
	private BufferedImage Ibackground;
	private int speed = 20000;
	private int speedup = 800;
	private int speedster;
	private int speedcounter = 0;
	private int col22;
	private int player1speed = 100;
	private int player2speed = 100;
	private int appledate = 0;
	private int afkdate = 0;
	private String name1player;
	private String name2player;
	private boolean accepted = false;
	private boolean Player1 = false;
	private boolean ranpple = false;
	private boolean ranafk = false;
	private boolean gamenotover = true;
	private boolean maxspeedcolor1 = false;
	private boolean maxspeedcolor2 = false;
	private boolean colorchanger1 = false;
	private boolean Playerleave = false;
	private double tstart, tend;
	private boolean speedchanger = false;
	private Font fontPanel;
	private Thread GalacticSnakes;
	private Star star;
    private Steroids steroids;
	private Clip clip;
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

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public GalacticSnakes() {
		System.out.print("Please input the IP: ");
		ip = sc.next();
		port = 5;
		System.out.print("Please Enter your name :");
		name1player = sc.next();
		loadImages();
		addKeyListener(this);
		setFocusable(true);
		star = new Star(this);
                steroids= new Steroids(this);
		if (!connect()) {
			createServer();
		}
		if (!accepted) {
			waitforrequest();
		}
		try {
			oos.writeObject(name1player);
			oos.flush();
			name2player = (String) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		// play("MRDUDES.wav");
		GalacticSnakes = new Thread(this, "MMR");
		GalacticSnakes.start();
		tstart = System.currentTimeMillis();
		Runsnake1();
		DataSender();
		DataReceiver();
		if (Player1) {
			star.Random();
			steroids.Random();
			try {
				SendData();
			} catch (IOException e) {
				e.printStackTrace();
			}
			ranpple = true;
			ranafk = true;
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void main(String[] args) {
		JFrame v = new JFrame();
		v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		v.setResizable(false);
		v.setSize(WIDTH * FACTOR, HEIGHT * FACTOR);
		v.setLocationRelativeTo(null);
		v.setTitle("GalacticSnakes");
		v.add(new GalacticSnakes());
		v.setVisible(true);
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void createServer() {
		try {
			serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
			getSnake()[0] = new Snake(30, 30, 6);
			getSnake()[1] = new Snake(15, 15, 6);
			Player1 = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void waitforrequest() {
		Socket socket = null;
		try {
			socket = serverSocket.accept();
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
			accepted = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public boolean connect() {
		try {
			socket = new Socket(ip, port);
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			getSnake()[0] = new Snake(15, 15, 6);
			getSnake()[1] = new Snake(30, 30, 6);
			accepted = true;
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void DataSender() {
		new Thread() {
			public void run() {
				while (gamenotover) {
					try {
						sleep(5);
						SendData();
					} catch (InterruptedException | IOException e) {
						
						Playerleave = true;
						break;
					}
				}
			}
		}.start();
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void DataReceiver() {
		new Thread() {
			public void run() {
				while (gamenotover) {
					try {
						ReceiveData();
					} catch (ClassNotFoundException | IOException e) {
						
						Playerleave=true;
						break;
					}
				}
			}
		}.start();
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void Runsnake1() {
		new Thread() {
			public void run() {
				while (gamenotover) {
					try {
						sleep(speed / 200);
						SnakeMoving1();
					} catch (InterruptedException e) {
						break;
					}
				}
			}
		}.start();
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
//	public void Runsnake2() {
//		new Thread() {
//			public void run() {
//				while (gamenotover) {
//					try {
//						sleep(speedster);
//						SnakeMoving2();
//					} catch (InterruptedException e) {
//						break;
//					}
//
//				}
//			}
//		}.start();
//	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void SnakeMoving1() {
		getSnake()[0].move();
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
//	public void SnakeMoving2() {
//		s[1].move();
//	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void play(String name) {
		new Thread() {
			public void run() {
				try {
					File Clap = new File(name);
					clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(Clap));
					clip.isActive();
					clip.start();
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					e.printStackTrace();
				}
			}
		}.start();
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
			if (speed > 3500) {
				speed = speed - speedup;
			}
			if (speedup > 50)
				speedup -= 50;
			try {
				SendData();
			} catch (IOException e) {
				Playerleave = true;
			}
		}
	}
        
	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public synchronized void SendData() throws IOException {
		int[] arr;
		int tmp = getSnake()[0].getLength() * 2;
		arr = new int[(getSnake()[0].getLength()) * 2 + 20];
		arr[0] = getSnake()[0].getLength();
		System.arraycopy(getSnake()[0].getSnakex(), 0, arr, 1, getSnake()[0].getLength());
		System.arraycopy(getSnake()[0].getSnakey(), 0, arr, getSnake()[0].getLength() + 1, getSnake()[0].getLength());
		tmp++;
		arr[tmp] = getSnake()[0].getDirection();
		arr[tmp + 1] = 10;
		arr[tmp + 2] = speed / 200;
		arr[tmp + 3] = getStar().getStarX();
		arr[tmp + 4] = getStar().getStarY();
		if (ranpple) {
			arr[tmp + 1] = 5;
			ranpple = false;
		}
		arr[tmp + 5] = player1speed;
		arr[tmp + 6] = getSteroids().getSteroidX()[0];
		arr[tmp + 7] = getSteroids().getSteroidY()[0];
		arr[tmp + 8] = 10;
		if (ranafk) {
			arr[tmp + 8] = 5;
			ranafk = false;
		}
		arr[tmp + 9] = getSteroids().getSteroidX()[1];
		arr[tmp + 10] = getSteroids().getSteroidY()[1];
		arr[tmp + 11] = getSteroids().getSteroidX()[2];
		arr[tmp + 12] = getSteroids().getSteroidY()[2];
		oos.writeObject(arr);
	}
	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void ReceiveData() throws ClassNotFoundException, IOException {
		int[] mass;
		mass = new int[getSnake()[1].getLength() * 2 + 50];
		mass = (int[]) ois.readObject();
		snake[1].setLength(mass[0]);
		System.arraycopy(mass, 1, getSnake()[1].getSnakex(), 0, mass[0]);
		System.arraycopy(mass, mass[0] + 1, getSnake()[1].getSnakey(), 0, mass[0]);
		mass[0] = mass[0] * 2 + 1;
		snake[1].setDirection(mass[mass[0]]) ;
		speedster = mass[mass[0] + 2];
		player2speed = mass[mass[0] + 5];
		if (mass[mass[0] + 1] == 5) {
			star.setStarX(mass[mass[0] + 3]);
			star.setStarY(mass[mass[0] + 4]);
		}
		if (mass[mass[0] + 8] == 5) {
			steroids.getSteroidX()[0] = mass[mass[0] + 6];
			steroids.getSteroidY()[0] = mass[mass[0] + 7];
			steroids.getSteroidX()[1] = mass[mass[0] + 9];
			steroids.getSteroidY()[1] = mass[mass[0] + 10];
			steroids.getSteroidX()[2] = mass[mass[0] + 11];
			steroids.getSteroidY()[2] = mass[mass[0] + 12];
		}
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void run() {
		while (gamenotover) {
			try {
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
				if (Player1 && appledate == 3000) {
					getStar().Random();
					appledate = 0;
					ranpple = true;
				}
				if (Player1 && afkdate == 8000) {
					getSteroids().Random();
					afkdate = 0;
					ranafk = true;
				}
				appledate++;
				speedcounter++;
				afkdate++;
				if (speedcounter == 4) {
					changespeed();
					speedcounter = 0;
				}
				if ((getStar().getStarX() == getSnake()[0].getSnakex()[0]) && (getStar().getStarY() == getSnake()[0].getSnakey()[0])) {
					snake[0].setLength(getSnake()[0].getLength() + 2);
					getStar().Random();
					appledate = 0;
					ranpple = true;
				}
				for (int i = 0; i <= 2; i++) {
					if ((getSteroids().getSteroidX()[i] == getSnake()[0].getSnakex()[0]) && (getSteroids().getSteroidY()[i] == getSnake()[0].getSnakey()[0])) {
						getSteroids().Random();
						afkdate = 0;
						if (getSnake()[0].getLength() > 2)
							snake[0].setLength(getSnake()[0].getLength() - 2);
						ranafk = true;
					}
				}
				checkerror();
				repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void paint(Graphics g) {
		if (!Playerleave) {
			fontPanel = new Font("Times New Roman", Font.PLAIN, 25);
			g.setFont(fontPanel);
			g.drawImage(Ibackground, 0, 0, FACTOR * WIDTH, FACTOR * HEIGHT, null);
			g.drawImage(IStar, getStar().getStarX() * FACTOR, getStar().getStarY() * FACTOR, FACTOR, FACTOR, null);
			g.drawImage(ISteroid, (getSteroids().getSteroidX()[0] * FACTOR), getSteroids().getSteroidY()[0] * FACTOR, FACTOR, FACTOR, null);
			g.drawImage(ISteroid, (getSteroids().getSteroidX()[1] * FACTOR), getSteroids().getSteroidY()[1] * FACTOR, FACTOR, FACTOR, null);
			g.drawImage(ISteroid, (getSteroids().getSteroidX()[2] * FACTOR), getSteroids().getSteroidY()[2] * FACTOR, FACTOR, FACTOR, null);
			if (getSnake()[0].getDirection() == 0) {
				g.drawImage(IHeadright, getSnake()[0].getSnakex()[0] * FACTOR, getSnake()[0].getSnakey()[0] * FACTOR, FACTOR, FACTOR, null);
			} else {
				if (getSnake()[0].getDirection() == 1) {
					g.drawImage(IHeaddown, getSnake()[0].getSnakex()[0] * FACTOR, getSnake()[0].getSnakey()[0] * FACTOR, FACTOR, FACTOR, null);
				} else {
					if (getSnake()[0].getDirection() == 2) {
						g.drawImage(IHeadleft, getSnake()[0].getSnakex()[0] * FACTOR, getSnake()[0].getSnakey()[0] * FACTOR, FACTOR, FACTOR, null);
					} else {
						if (getSnake()[0].getDirection() == 3) {
							g.drawImage(IHeadup, getSnake()[0].getSnakex()[0] * FACTOR, getSnake()[0].getSnakey()[0] * FACTOR, FACTOR, FACTOR,
									null);
						}
					}
				}
			}
			if (!maxspeedcolor1) {
				if (getSnake()[0].getLength() == getSnake()[1].getLength()) {
					for (int d = 1; d < getSnake()[0].getLength(); d++) {
						g.drawImage(IBody1, getSnake()[0].getSnakex()[d] * FACTOR, getSnake()[0].getSnakey()[d] * FACTOR, FACTOR, FACTOR, null);
					}
				} else {
					if (getSnake()[0].getLength() > getSnake()[1].getLength()) {
						for (int d = 1; d < getSnake()[0].getLength(); d++) {
							g.drawImage(IBodyRed, getSnake()[0].getSnakex()[d] * FACTOR, getSnake()[0].getSnakey()[d] * FACTOR, FACTOR, FACTOR,
									null);
						}
					} else {
						for (int d = 1; d < getSnake()[0].getLength(); d++) {
							g.drawImage(IBodyWhite, getSnake()[0].getSnakex()[d] * FACTOR, getSnake()[0].getSnakey()[d] * FACTOR, FACTOR, FACTOR,
									null);
						}
					}
				}
			} else {
				if (colorchanger1) {
					for (int d = 1; d < getSnake()[0].getLength(); d++) {
						g.drawImage(IBodyYellow, getSnake()[0].getSnakex()[d] * FACTOR, getSnake()[0].getSnakey()[d] * FACTOR, FACTOR, FACTOR,
								null);
					}
					colorchanger1 = false;
				} else {
					for (int d = 1; d < getSnake()[0].getLength(); d++) {
						g.drawImage(IBodyOrange, getSnake()[0].getSnakex()[d] * FACTOR, getSnake()[0].getSnakey()[d] * FACTOR, FACTOR, FACTOR,
								null);
					}
					colorchanger1 = true;
				}
			}
			if (getSnake()[1].getDirection() == 0) {
				g.drawImage(IHeadright, getSnake()[1].getSnakex()[0] * FACTOR, getSnake()[1].getSnakey()[0] * FACTOR, FACTOR, FACTOR, null);
			} else {
				if (getSnake()[1].getDirection() == 1) {
					g.drawImage(IHeaddown, getSnake()[1].getSnakex()[0] * FACTOR, getSnake()[1].getSnakey()[0] * FACTOR, FACTOR, FACTOR, null);
				} else {
					if (getSnake()[1].getDirection() == 2) {
						g.drawImage(IHeadleft, getSnake()[1].getSnakex()[0] * FACTOR, getSnake()[1].getSnakey()[0] * FACTOR, FACTOR, FACTOR, null);
					} else {
						if (getSnake()[1].getDirection() == 3) {
							g.drawImage(IHeadup, getSnake()[1].getSnakex()[0] * FACTOR, getSnake()[1].getSnakey()[0] * FACTOR, FACTOR, FACTOR,
									null);
						}
					}
				}
			}
			if (!maxspeedcolor2) {
				if (getSnake()[0].getLength() == getSnake()[1].getLength()) {
					for (int d = 1; d < getSnake()[1].getLength(); d++) {
						g.drawImage(IBody1, getSnake()[1].getSnakex()[d] * FACTOR, getSnake()[1].getSnakey()[d] * FACTOR, FACTOR, FACTOR, null);
					}
				} else {
					if (getSnake()[0].getLength() > getSnake()[1].getLength()) {
						for (int d = 1; d < getSnake()[1].getLength(); d++) {
							g.drawImage(IBodyWhite, getSnake()[1].getSnakex()[d] * FACTOR, getSnake()[1].getSnakey()[d] * FACTOR, FACTOR, FACTOR,
									null);
						}
					} else {
						for (int d = 1; d < getSnake()[1].getLength(); d++) {
							g.drawImage(IBodyRed, getSnake()[1].getSnakex()[d] * FACTOR, getSnake()[1].getSnakey()[d] * FACTOR, FACTOR, FACTOR,
									null);
						}
					}
				}
			} else {
				if (colorchanger1) {
					for (int d = 1; d < getSnake()[1].getLength(); d++) {
						g.drawImage(IBodyYellow, getSnake()[1].getSnakex()[d] * FACTOR, getSnake()[1].getSnakey()[d] * FACTOR, FACTOR, FACTOR,
								null);
					}
					colorchanger1 = false;
				} else {
					for (int d = 1; d < getSnake()[1].getLength(); d++) {
						g.drawImage(IBodyOrange, getSnake()[1].getSnakex()[d] * FACTOR, getSnake()[1].getSnakey()[d] * FACTOR, FACTOR, FACTOR,
								null);
					}
					colorchanger1 = true;
				}
			}
			// Snake
			g.setColor(Color.BLUE);
			g.drawLine(0, 539, 389, 900);
			g.drawLine(0, 540, 390, 900);
			g.drawLine(1150, 539, 749, 900);
			g.drawLine(1150, 540, 750, 900);
			g.drawLine(278, 800, 872, 800);
			g.setColor(Color.ORANGE);
			g.drawString(name1player, 20, 850);
			g.drawString(name2player, 1010, 850);
			g.setColor(Color.WHITE);
			g.drawString("Length: " + getSnake()[0].getLength(), 20, 800);
			g.drawString("Length: " + getSnake()[1].getLength(), 960, 800);
			g.setColor(Color.CYAN);
			fontPanel = new Font("Times New Roman", Font.PLAIN, 50);
			if (player1speed == 15000) {
				col22 = (int) (Math.random() * 255);
				g.setColor(color(255, col22, 70));
				g.drawString("Скорость света", 0, 750);
			} else {
				g.drawString("MS : " + player1speed, 20, 750);
			}
			if (player2speed == 15000) {
				col22 = (int) (Math.random() * 255);
				g.setColor(color(col22, 255, 70));
				g.drawString("Скорость света", 950, 750);
			} else {
				g.setColor(Color.CYAN);
				g.drawString("MS : " + player2speed, 970, 750);
			}
			tend = System.currentTimeMillis();
			if ((tend - tstart) / 1000 >= 50) {
				col22 = (int) (Math.random() * 255);
				g.setColor(color(col22, 255, 70));
			} else {
				g.setColor(Color.LIGHT_GRAY);
				tend = System.currentTimeMillis();
			}
			if ((tend - tstart) / 1000 >= 60) {
				fontPanel = new Font("Times New Roman", Font.PLAIN, 50);
				g.setFont(fontPanel);
				g.setColor(Color.RED);
				g.drawString("game over ", 450, 350);
				if (getSnake()[0].getLength() == getSnake()[1].getLength()) {
					g.drawString("no winner ", 450, 400);
				} else {
					if (getSnake()[0].getLength() > getSnake()[1].getLength()) {
						g.drawString(name1player + " is winner", 450, 400);
					} else {
						g.drawString(name2player + " is winner", 450, 400);
					}
				}
				g.drawString("Time: " + 60.000, 450, 850);
				// msbackground.clip.stop();
				// play("papich2.wav");
				gamenotover = false;
			} else {
				g.drawString("Time: " + (((tend - tstart) / 1000)), 500, 850);
			}
		} else {
			fontPanel = new Font("Times New Roman", Font.PLAIN, 50);
			g.setFont(fontPanel);
			g.setColor(Color.RED);
			g.drawString(name2player + "  Has Left ", 450, 350);
		}
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
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
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			speed = 20000;
			speedup = 800;
			speedchanger = false;
			player1speed = 100;
			player2speed = 100;
			colorchanger1 = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

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
}
