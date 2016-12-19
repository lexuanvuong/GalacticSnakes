package galacticsnakes;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Menu extends JFrame {
	private JButton buttonStart = new JButton("OK");
	private BufferedImage buttonIcon1, buttonIcon2;
	private Image newimg1, newimg2;
	private JTextField IP, Name;
	private Menu thisMenu;
	private BackPanel backpanel;
	private GridBagConstraints constraints;
	private JLabel WaitLabel;
	private boolean clicked = false;

	public Menu() {
		super("GalacticSnakes");
		backpanel = new BackPanel();
		backpanel.setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		try {
			loadImages();
		} catch (IOException e) {
			e.printStackTrace();
		}
		AddPlayButton();
		AddIPfield();
		AddNamefield();
		AddWaitLabel();
		add(backpanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 500);
		this.setResizable(false);
		setLocationRelativeTo(null);
	}

	public void AddPlayButton() {
		newimg1 = buttonIcon1.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
		newimg2 = buttonIcon2.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
		buttonStart = new JButton(new ImageIcon(newimg1));
		buttonStart.setBorder(BorderFactory.createEmptyBorder());
		buttonStart.setContentAreaFilled(false);
		thisMenu = this;
		buttonStart.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (!clicked) {
					Executors.newCachedThreadPool().execute(new Runnable() {
						@Override
						public void run() {
							WaitLabel.setVisible(true);
							JFrame v = new JFrame();
							v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							v.setSize(GalacticSnakes.WIDTH * GalacticSnakes.FACTOR + 5,
									GalacticSnakes.HEIGHT * GalacticSnakes.FACTOR + 27);
							v.setLocationRelativeTo(null);
							v.setTitle("GalacticSnakes");
							v.add(new GalacticSnakes(v, thisMenu, Name.getText(), IP.getText()));
							v.setResizable(false);
							v.setVisible(true);
						}
					});
					clicked = true;
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				buttonStart.setIcon(new ImageIcon(newimg2));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				buttonStart.setIcon(new ImageIcon(newimg1));
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.insets = new Insets(1, 1, 30, 1);
		backpanel.add(buttonStart, constraints);
	}

	public void AddIPfield() {
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(1, 1, 10, 1);
		backpanel.add(new JLabel("IP"), constraints);
		constraints.gridx = 1;
		IP = new JTextField(15);
		backpanel.add(IP, constraints);
	}

	public void AddNamefield() {
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.insets = new Insets(1, 1, 20, 1);
		backpanel.add(new JLabel("Name"), constraints);
		constraints.gridx = 1;
		Name = new JTextField(15);
		backpanel.add(Name, constraints);
	}

	public void AddWaitLabel() {
		constraints.gridx = 1;
		constraints.gridy = 4;
		WaitLabel = new JLabel("Waiting for Player2....");
		backpanel.add(WaitLabel, constraints);
		WaitLabel.setVisible(false);
	}

	public void loadImages() throws IOException {
		buttonIcon2 = ImageIO.read(getClass().getResource("/images/GalSnakes20.png"));
		buttonIcon1 = ImageIO.read(getClass().getResource("/images/GalSnakes1.png"));
	}

	public class BackPanel extends JPanel {
		private BufferedImage Ibackground;

		public BackPanel() {
			try {
				Ibackground = ImageIO.read(getClass().getResourceAsStream("/images/66793518-generic-wallpapers.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(Ibackground, 0, 0, 600, 500, null);
		}
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

	public JLabel getWaitLabel() {
		return WaitLabel;
	}

	public void setWaitLabel(JLabel waitLabel) {
		WaitLabel = waitLabel;
	}
}
