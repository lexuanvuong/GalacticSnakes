package galacticsnakes;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataExchange {
	private GalacticSnakes galacticsnakes;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public DataExchange(GalacticSnakes galacticsnakes) {
		this.galacticsnakes = galacticsnakes;
		oos = galacticsnakes.getOos();
		ois = galacticsnakes.getOis();
	}

	public void Send() {
		new Thread() {
			public void run() {
				while (galacticsnakes.isGamenotover()) {
					try {
						sleep(5);
						Player1Data();
					} catch (InterruptedException | IOException e) {
						galacticsnakes.setPlayerleave(true);
						break;
					}
				}
			}
		}.start();
	}

	public void Receive() {
		new Thread() {
			public void run() {
				while (galacticsnakes.isGamenotover()) {
					try {
						Player2Data();
					} catch (ClassNotFoundException | IOException e) {
						galacticsnakes.setPlayerleave(true);
						break;
					}
				}
			}
		}.start();
	}

	public synchronized void Player1Data() throws IOException {
		int[] arr;
		int tmp = galacticsnakes.getSnake()[0].getLength() * 2;
		arr = new int[(galacticsnakes.getSnake()[0].getLength()) * 2 + 20];
		arr[0] = galacticsnakes.getSnake()[0].getLength();
		System.arraycopy(galacticsnakes.getSnake()[0].getSnakex(), 0, arr, 1, galacticsnakes.getSnake()[0].getLength());
		System.arraycopy(galacticsnakes.getSnake()[0].getSnakey(), 0, arr, galacticsnakes.getSnake()[0].getLength() + 1,
				galacticsnakes.getSnake()[0].getLength());
		tmp++;
		arr[tmp] = galacticsnakes.getSnake()[0].getDirection();
		arr[tmp + 1] = 10;
		arr[tmp + 2] = galacticsnakes.getSpeed() / 200;
		arr[tmp + 3] = galacticsnakes.getStar().getStarX();
		arr[tmp + 4] = galacticsnakes.getStar().getStarY();
		if (galacticsnakes.isNewStar()) {
			arr[tmp + 1] = 5;
			// if(!galacticsnakes.isPlayer1())
			// arr[tmp + 5] = 0;
			galacticsnakes.setNewStar(false);
		}
		arr[tmp + 6] = galacticsnakes.getSteroids().getSteroidX()[0];
		arr[tmp + 7] = galacticsnakes.getSteroids().getSteroidY()[0];
		arr[tmp + 8] = 10;
		if (galacticsnakes.isNewSteroids()) {
			arr[tmp + 8] = 5;
			galacticsnakes.setNewSteroids(false);
		}
		arr[tmp + 9] = galacticsnakes.getSteroids().getSteroidX()[1];
		arr[tmp + 10] = galacticsnakes.getSteroids().getSteroidY()[1];
		arr[tmp + 11] = galacticsnakes.getSteroids().getSteroidX()[2];
		arr[tmp + 12] = galacticsnakes.getSteroids().getSteroidY()[2];
		oos.writeObject(arr);
	}

	public void Player2Data() throws ClassNotFoundException, IOException {
		int[] mass;
		mass = new int[galacticsnakes.getSnake()[1].getLength() * 2 + 50];
		mass = (int[]) ois.readObject();
		galacticsnakes.getSnake()[1].setLength(mass[0]);
		System.arraycopy(mass, 1, galacticsnakes.getSnake()[1].getSnakex(), 0, mass[0]);
		System.arraycopy(mass, mass[0] + 1, galacticsnakes.getSnake()[1].getSnakey(), 0, mass[0]);
		mass[0] = mass[0] * 2 + 1;
		galacticsnakes.getSnake()[1].setDirection(mass[mass[0]]);
		// speedster = mass[mass[0] + 2];
		if (mass[mass[0] + 1] == 5) {
			if (galacticsnakes.isPlayer1())
				galacticsnakes.setStarDate(0);
			galacticsnakes.getStar().setStarX(mass[mass[0] + 3]);
			galacticsnakes.getStar().setStarY(mass[mass[0] + 4]);
		}
		if (mass[mass[0] + 8] == 5) {
			galacticsnakes.getSteroids().getSteroidX()[0] = mass[mass[0] + 6];
			galacticsnakes.getSteroids().getSteroidY()[0] = mass[mass[0] + 7];
			galacticsnakes.getSteroids().getSteroidX()[1] = mass[mass[0] + 9];
			galacticsnakes.getSteroids().getSteroidY()[1] = mass[mass[0] + 10];
			galacticsnakes.getSteroids().getSteroidX()[2] = mass[mass[0] + 11];
			galacticsnakes.getSteroids().getSteroidY()[2] = mass[mass[0] + 12];
		}
	}
}
