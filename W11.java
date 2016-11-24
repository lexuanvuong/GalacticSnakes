package snake1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import snake2.Apple;
import snake2.W22;

public class W11 extends JPanel implements ActionListener {
	public static final int SCALE = 15;
	public static final int WIDTH = 50;
	public static final int HEIGHT = 50;
	public static final int SPEED = 20;
	public boolean ha = false;
	public boolean hi = false;
	public int count = 0;
	public int count1 = 0;
	public int count2 = 0;
	public int ln = 0;
	public int time;
	public int time1;
	public boolean fortime = false;
	int col11, col22, col33;

	{
		col11 = (int) (Math.random() * 250);
		col22 = (int) (Math.random() * 250);
		col33 = (int) (Math.random() * 250);
	}

	int col1, col2, col3;
	W22 s = new W22(30, 30, 29, 30);
	Timer t = new Timer(50, this);
	Apple a = new Apple();

	public W11() {

		t.start();
		addKeyListener(new Keyboard());
		setFocusable(true);
		a.randomxy();
		a.randomxy2();
		a.randomxy3();
	}

	public void check() {
		if (W22.length >= W22.snakex.length) {
			int tmpx[] = new int[W22.snakex.length + 100];
			int tmpy[] = new int[W22.snakey.length + 100];
			System.arraycopy(W22.snakex, 0, tmpx, 0, W22.snakex.length);
			System.arraycopy(W22.snakey, 0, tmpy, 0, W22.snakey.length);
			W22.snakex = tmpx;
			W22.snakey = tmpy;
		}
	}

	public void paint(Graphics g) {
		Font fontPanel = new Font("Arial Black", Font.PLAIN, 15);
		g.setColor(color(240, 248, 255));
		g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
		g.setColor(color(255, 255, 255));
		g.fillRect(0, SCALE * HEIGHT, SCALE * WIDTH, 150);
		g.setColor(color(209, 226, 49));
		g.fillOval(0, SCALE * HEIGHT + 10, 150, 100);
		g.setColor(color(209, 226, 49));
		g.fillOval(600, SCALE * HEIGHT + 10, 150, 100);
		if (count != 3) {
			g.setColor(color(255, 36, 0));
			g.fillRect(1 * SCALE, 0 * SCALE, SCALE, SCALE);
		}
		if (count != 3) {
			g.setColor(color(127, 255, 0));
			g.fillRect(a.x * SCALE, a.y * SCALE, SCALE, SCALE);
			g.setColor(color(127, 255, 0));
			g.fillRect(a.x2 * SCALE, a.y2 * SCALE, SCALE, SCALE);
			g.setColor(color(127, 255, 0));
			g.fillRect(a.x3 * SCALE, a.y3 * SCALE, SCALE, SCALE);
		}
		col1 = (int) (Math.random() * 250);
		col2 = (int) (Math.random() * 250);
		col3 = (int) (Math.random() * 250);
		g.setColor(color(col1, col2, col3));
		g.fillRect(s.snakex[0] * SCALE, s.snakey[0] * SCALE, SCALE, SCALE);
		if (ha == true || hi == true) {
			col11 = (int) (Math.random() * 250);
			col22 = (int) (Math.random() * 250);
			col33 = (int) (Math.random() * 250);
			for (int d = 1; d < s.length; d++) {
				if (fortime == true) {
					col22 = (int) (Math.random() * 255);
					col33 = (int) (Math.random() * 50 + 50);
					g.setColor(color(255, col22, 70));
				} else {
					g.setColor(color(col11, col22, col33));
				}
				g.fillOval(s.snakex[d] * SCALE, s.snakey[d] * SCALE, SCALE, SCALE);
			}
			ha = false;
		} else {
			for (int d = 1; d < s.length; d++) {
				g.setColor(color(col11, col22, col33));
				g.fillOval(s.snakex[d] * SCALE, s.snakey[d] * SCALE, SCALE, SCALE);
			}
		}
		g.setFont(fontPanel);
		g.setColor(color(199, 21, 133));
		g.drawString("Length: " + s.length, 25, WIDTH * SCALE + 60);
		g.setColor(color(199, 21, 133));
		g.drawString("Time: " + time / 10 + "." + time1, 640, WIDTH * SCALE + 60);
		if (time == 300) {
			g.setColor(color(0, 0, 0));
			g.drawString("GAME OVER ", (WIDTH * SCALE) / 2 - 50, WIDTH * SCALE + 60);
		}
	}

	public Color color(int red, int green, int blue) {
		return new Color(red, green, blue);
	}

	public static void main(String[] agrs) {
		JFrame v = new JFrame();
		v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		v.setResizable(false);
		v.setSize(WIDTH * SCALE + 7, HEIGHT * SCALE + 150);
		v.setLocationRelativeTo(null);
		v.add(new W11());
		v.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		if (time > 250) {
			hi = true;
			fortime = true;
		}
		if (time1 == 10) {
			time1 = 0;
		}
		time++;
		time1++;
		if (count1 == 80) {
			count = 0;
			count1 = 0;
		}
		if (count2 == 30) {
			hi = false;
			count2 = 0;
		}
		s.move();
		for (int i = 1; i < W22.length; i++) {
			if ((W22.snakex[0] == W22.snakex[i]) && (W22.snakey[0] == W22.snakey[i])) {
				W22.length = W22.length - (W22.length - i);
			}
		}
		if (((Apple.x == W22.snakex[0]) && (Apple.y == W22.snakey[0]))
				|| ((Apple.x2 == W22.snakex[0]) && (Apple.y2 == W22.snakey[0]))
				|| ((Apple.x3 == W22.snakex[0]) && (Apple.y3 == W22.snakey[0]))) {
			if (count != 3) {
				ln = W22.length;
				W22.length = W22.length + 5;
				check();
				a.randomxy();
				a.randomxy2();
				a.randomxy3();
				count++;
				for (int i = ln - 1; i < W22.length; i++) {
					W22.snakex[i] = 600;
				}
				ha = true;
			}
			if (count == 3) {
				a.randomxy1();
			}
		}
		if (count == 3) {
			count1++;
		}
		if (count == 3) {
			if ((Apple.x1 == W22.snakex[0]) && (Apple.y1 == W22.snakey[0])) {
				ln = W22.length;
				W22.length = W22.length + 30;
				check();
				count = 0;
				hi = true;
				for (int i = ln - 1; i < W22.length; i++) {
					W22.snakex[i] = 600;
				}
			}
		}
		if (hi == true) {
			count2++;
		}
		if ((count == 0) && hi == true) {
			count2++;
		}
		if (time == 900) {
			time1 = 0;
			t.stop();
		}
		repaint();
	}

	private class Keyboard extends KeyAdapter {
		public void keyPressed(KeyEvent kEvt) {
			int key = kEvt.getKeyCode();

			if (s.direction != 2) {
				if (W22.snakey[0] != W22.snakey[1])
					if (key == KeyEvent.VK_RIGHT) {
						s.direction = 0;
					}
			}

			if (s.direction != 3) {
				if (W22.snakex[0] != W22.snakex[1]) {
					if (key == KeyEvent.VK_DOWN) {
						s.direction = 1;
					}
				}
			}

			if (s.direction != 1) {
				if (W22.snakex[0] != W22.snakex[1]) {
					if (key == KeyEvent.VK_UP) {
						s.direction = 3;
					}
				}
			}
			if (s.direction != 0) {
				if (W22.snakey[0] != W22.snakey[1]) {
					if (key == KeyEvent.VK_LEFT) {
						s.direction = 2;
					}
				}
			}
		}
	}
}