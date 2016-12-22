package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.Executors;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import connection.Client;
import connection.Host;
import ui.menu.MenuUI;

public class Menu extends MenuUI {

	private Menu thisMenu;
	private boolean clicked = false;
	private Client client;
	private Host host;
	
	public Menu() {
		super();
		givePBlistener();
		giveCClistener();
	}
	public void givePBlistener() {
		thisMenu = this;
		getButtonStart().addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Executors.newCachedThreadPool().execute(new Runnable() {
					@Override
					public void run() {
						if (!clicked) {
							clicked = true;
							client = new Client(getIP().getText(), getNameP().getText());
							if (!client.connect()) {
								host = new Host(getIP().getText(), getNameP().getText());
								if (host.createServer()) {
									getWaitLabel().setVisible(true);
									getCancel().setVisible(true);
									if(host.waitforrequest()){
										thisMenu.setVisible(false);
										InitGame(true);										
									}
								}else{
									JOptionPane.showMessageDialog(Menu.this, "Неверный IP-адресс");
									clicked=false;
								}
							} else {
								thisMenu.setVisible(false);
								InitGame(false);
							}
						}
					}
				});
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				getButtonStart().setIcon(new ImageIcon(getNewimg2()));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				getButtonStart().setIcon(new ImageIcon(getNewimg1()));
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});
	}
	public void giveCClistener(){
			getCancel().addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				getWaitLabel().setVisible(false);
				getCancel().setVisible(false);
				clicked=false;
				host.Stop();	
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				getButtonStart().setIcon(new ImageIcon(getNewimg2()));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				getButtonStart().setIcon(new ImageIcon(getNewimg1()));
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});
	}
	public void InitGame(boolean host) {
		getWaitLabel().setVisible(false);
		getCancel().setVisible(false);
		JFrame v = new JFrame();
		v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		v.setSize(Game.WIDTH * Game.FACTOR + 5, Game.HEIGHT * Game.FACTOR + 27);
		v.setLocationRelativeTo(null);
		v.setTitle("GalacticSnakes");
		v.add(new Game(v, thisMenu,host, getNameP().getText(), getIP().getText()));
		v.setResizable(false);
		v.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Menu().setVisible(true);
			}
		});
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
	public Host getHost() {
		return host;
	}
	public void setHost(Host host) {
		this.host = host;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}

}
