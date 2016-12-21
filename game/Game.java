package game;

import connection.Client;
import connection.Host;
import elements.Meteorites;
import elements.Snake;
import elements.Star;
import ui.game.GameUI;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game extends GameUI implements KeyListener, Runnable {


	private int speed = 20000;
	private int speedup = 800;
	private int speedcounter = 0;
	private int StarDate = 0;
	private int steroidsTime = 0;
	private boolean hostb = false;
	private boolean newStar = false;
	private boolean newSteroids = false;
	private boolean Playerleave = false;
	private boolean speedchanger = false;
	private Thread game;
	private Menu menu;
	private JFrame jframe;
	private Host host;
	private Client client;

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public Game(JFrame jframe, Menu menu, boolean host, String Name, String IP) {
		addKeyListener(this);
		setFocusable(true);
		this.jframe = jframe;
		this.menu = menu;
		setName1player(Name);
		hostb = host;
		if (host) {
			this.host = menu.getHost();
			this.host.setGame(this);
			this.host.ExchangePlayersName(this.host.getName());
			getSnake()[0] = new Snake(WIDTH / 2, HEIGHT / 2, 6, this);
			getSnake()[1] = new Snake(WIDTH / 2, HEIGHT / 2 + 1, 6, this);
		} else {
			client = menu.getClient();
			client.setGame(this);
			client.ExchangePlayersName(client.getName());
			getSnake()[0] = new Snake(WIDTH / 2, HEIGHT / 2 + 1, 6, this);
			getSnake()[1] = new Snake(WIDTH / 2, HEIGHT / 2, 6, this);
			getSnake()[0].setDirection(2);
		}
		setStar(new Star(this));
		setMeteorites(new Meteorites(this));
		game = new Thread(this, "MMR");
		game.start();
		setTstart(System.currentTimeMillis());
		getSnake()[0].Run();
		Init();
		MainMenulistener();
		Exitlistener();
		InitializeDataExchange();
		InitialStarMeteorites();
	}

	public void InitializeDataExchange() {
		if (hostb) {
			host.Send();
			host.Receive();
		} else {
			client.Send();
			client.Receive();
		}

	}

	public void InitialStarMeteorites() {
		if (hostb) {
			getStar().Random();
			getMeteorites().Random();
			if (hostb) {
				host.Send();
			} else {
				client.Send();
			}
			newStar = true;
			newSteroids = true;
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void checkerror() {
		for (int i = 0; i <= 1; i++) {
			if (getSnake()[i].getLength() >= getSnake()[i].getSnakex().length - 10) {
				int tmpx[] = new int[getSnake()[i].getSnakex().length + 100];
				int tmpy[] = new int[getSnake()[i].getSnakey().length + 100];
				System.arraycopy(getSnake()[i].getSnakex(), 0, tmpx, 0, getSnake()[i].getSnakex().length);
				System.arraycopy(getSnake()[i].getSnakey(), 0, tmpy, 0, getSnake()[i].getSnakey().length);
				getSnake()[i].setSnakex(tmpx);
				getSnake()[i].setSnakey(tmpy);
			}
		}
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void changespeed() {
		if (speedchanger) {
			if (getPlayer1speed() <= 14950) {
				setPlayer1speed(getPlayer1speed()+50);
			}
			if (getSpeed() > 3500) {
				setSpeed(getSpeed() - speedup);
			}
			if (speedup > 50) {
				speedup -= 50;
			}
		}
	}

	public void StarUpdate() {
		if (hostb && StarDate == 3000) {
			getStar().Random();
			StarDate = 0;
			newStar = true;
		}
	}

	public void SteroidsUpdate() {
		if (hostb && steroidsTime == 8000) {
			getMeteorites().Random();
			steroidsTime = 0;
			newSteroids = true;
		}
	}

	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public void run() {
		while (isGamenotover()) {
			try {
				if (!isPlayerleave()) {
					game.sleep(1);
					if (getPlayer1speed() >= 15000) {
						setMaxspeedcolor1(true);
					} else {
						setMaxspeedcolor1(false);
					}
					if (getPlayer2speed() >= 15000) {
						setMaxspeedcolor2(true);
					} else {
						setMaxspeedcolor2(false);
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
		JOptionPane.showMessageDialog(Game.this, getName2player() + " покинул эту игру");
		if (hostb) {
			host.Stop();
		} else {
			client.Stop();
		}
		getMenu().setVisible(true);
		getMenu().setClicked(false);
		setGamenotover(false);
		getMenu().getWaitLabel().setVisible(false);
		jframe.dispose();
	}

	public void MainMenulistener() {
		getMainmenu().addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (hostb) {
					host.Stop();
				} else {
					client.Stop();
				}
				jframe.dispose();
				getMenu().getWaitLabel().setVisible(false);
				getMenu().setVisible(true);
				getMenu().setClicked(false);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				getMainmenu().setIcon(new ImageIcon(getIButtonreplay2()));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				getMainmenu().setIcon(new ImageIcon(getIButtonreplay1()));
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});
	}

	public void Exitlistener() {
		getExit().addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				getExit().setIcon(new ImageIcon(getIButtonexit2()));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				getExit().setIcon(new ImageIcon(getIButtonexit1()));
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
					getSnake()[0].setDirection(0);
				}
			}
		}
		if (key == KeyEvent.VK_DOWN) {
			if (getSnake()[0].getDirection() != 3) {
				if (getSnake()[0].getSnakex()[0] != getSnake()[0].getSnakex()[1]) {
					getSnake()[0].setDirection(1);
				}
			}
		}
		if (key == KeyEvent.VK_UP) {
			if (getSnake()[0].getDirection() != 1) {
				if (getSnake()[0].getSnakex()[0] != getSnake()[0].getSnakex()[1]) {
					getSnake()[0].setDirection(3);
				}
			}
		}
		if (key == KeyEvent.VK_LEFT) {
			if (getSnake()[0].getDirection() != 0) {
				if (getSnake()[0].getSnakey()[0] != getSnake()[0].getSnakey()[1]) {
					getSnake()[0].setDirection(2);
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
			setPlayer1speed(100);
			setPlayer2speed(100);
			setColorchanger1(false);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	public void setSteroidsDate(int steroidsDate) {
		steroidsTime = steroidsDate;
	}

	public void setStarDate(int starDate) {
		StarDate = starDate;
	}

	public int getStarDate() {
		return StarDate;
	}

	public int getSteroidsDate() {
		return steroidsTime;
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

	public boolean isPlayer1() {
		return hostb;
	}

	public void setPlayer1(boolean player1) {
		hostb = player1;
	}

	/**
	 * @return the menu
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * @param menu
	 *            the menu to set
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
