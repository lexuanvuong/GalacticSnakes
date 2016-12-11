
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

	public Menu() {
		super("GalacticSnakes");
		BackPanel backpanel = new BackPanel();
		backpanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.insets = new Insets(1, 1, 0, 1);
		try {
			loadImages();
		} catch (IOException e) {
			e.printStackTrace();
		}
		AddStartButton();
		backpanel.add(buttonStart, constraints);
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(1, 1, 10, 1);
		backpanel.add(new JLabel("IP"), constraints);
		constraints.gridx = 1;
		IP = new JTextField(10);
		backpanel.add(IP, constraints);
		// ---------------------------------
		constraints.gridx = 0;
		constraints.gridy = 3;
		backpanel.add(new JLabel("Name"), constraints);
		constraints.gridx = 1;
		Name = new JTextField(10);
		backpanel.add(Name, constraints);
		// ---------------------------------
		add(backpanel);
		// ---------------------------------
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(250, 270);
		this.setResizable(false);
		setLocationRelativeTo(null);
	}

	public void AddStartButton() {
		newimg1 = buttonIcon1.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		newimg2 = buttonIcon2.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
		buttonStart = new JButton(new ImageIcon(newimg1));
		buttonStart.setBorder(BorderFactory.createEmptyBorder());
		buttonStart.setContentAreaFilled(false);
		thisMenu = this;
		buttonStart.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Executors.newCachedThreadPool().execute(new Runnable() {
					@Override
					public void run() {
						JFrame v = new JFrame();
						v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						v.setResizable(false);
						v.setSize(58 * 20, 45 * 20);
						v.setLocationRelativeTo(null);
						v.setTitle("GalacticSnakes");
						v.add(new GalacticSnakes(v,thisMenu, Name.getText(), IP.getText()));
						v.setVisible(true);
					}
				});
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
	}

	public void loadImages() throws IOException {
		buttonIcon2 = ImageIO.read(getClass().getResource("/images/playbutton1.png"));
		buttonIcon1 = ImageIO.read(getClass().getResource("/images/playbutton2.png"));
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
			g.drawImage(Ibackground, 0, 0, 250, 250, null);
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
}