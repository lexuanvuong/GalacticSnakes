package ui.menu;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuUI extends JFrame {

    private  JButton buttonStart,cancel ;
    private BufferedImage buttonIcon1, buttonIcon2;
    private Image newimg1, newimg2;
    private JTextField IP, NameP;
    private BackPanel backpanel;
    private GridBagConstraints constraints;
    private JLabel WaitLabel;
    public MenuUI() {
        super("GalacticSnakes");
        setMenuLayout();
              try {
            loadImages();
        } catch (IOException e) {
            e.printStackTrace();
        }  
        AddPlayButton();      
        AddIPfield();
        AddNamefield();
        AddWaitLabel();
        AddCancelButton();
        add(backpanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        this.setResizable(false);
        setLocationRelativeTo(null);
    }
    
    public void setMenuLayout() {
        backpanel = new BackPanel();
        backpanel.setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
    }
    public void AddPlayButton() {
        newimg1 = buttonIcon1.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
        newimg2 = buttonIcon2.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
        buttonStart = new JButton(new ImageIcon(newimg1));
        buttonStart.setBorder(BorderFactory.createEmptyBorder());
        buttonStart.setContentAreaFilled(false);
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
        NameP = new JTextField(15);
        backpanel.add(NameP, constraints);
    }

    public void AddWaitLabel() {
        constraints.gridx = 1;
        constraints.gridy = 5;
        WaitLabel = new JLabel("Waiting for Player2....");
        backpanel.add(WaitLabel, constraints);
        WaitLabel.setVisible(false);
    }
    
   
    public void AddCancelButton(){
    	constraints.gridx = 1;
        constraints.gridy = 4;
        cancel=new JButton("Cancel");
        cancel.setVisible(false);
        backpanel.add(cancel, constraints);
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
    /**
     * @return the buttonStart
     */
    public JButton getButtonStart() {
        return buttonStart;
    }

    /**
     * @param buttonStart the buttonStart to set
     */
    public void setButtonStart(JButton buttonStart) {
        this.buttonStart = buttonStart;
    }

    /**
     * @return the newimg1
     */
    public Image getNewimg1() {
        return newimg1;
    }

    /**
     * @param newimg1 the newimg1 to set
     */
    public void setNewimg1(Image newimg1) {
        this.newimg1 = newimg1;
    }

    /**
     * @return the newimg2
     */
    public Image getNewimg2() {
        return newimg2;
    }

    /**
     * @param newimg2 the newimg2 to set
     */
    public void setNewimg2(Image newimg2) {
        this.newimg2 = newimg2;
    }

    /**
     * @return the IP
     */
    public JTextField getIP() {
        return IP;
    }

    /**
     * @param IP the IP to set
     */
    public void setIP(JTextField IP) {
        this.IP = IP;
    }

    /**
     * @return the Name
     */
    public JTextField getNameP() {
        return NameP;
    }

    /**
     * @param Name the Name to set
     */
    public void setNameP(JTextField Name) {
        this.NameP = Name;
    }

    /**
     * @return the WaitLabel
     */
    public JLabel getWaitLabel() {
        return WaitLabel;
    }

    /**
     * @param WaitLabel the WaitLabel to set
     */
    public void setWaitLabel(JLabel WaitLabel) {
        this.WaitLabel = WaitLabel;
    }
        
    public JButton getCancel() {
		return cancel;
	}

	public void setCancel(JButton cancel) {
		this.cancel = cancel;
	}


}
