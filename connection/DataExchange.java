package connection;

import game.Game;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataExchange {
	private Game game;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public DataExchange(){
	}
	public void ExchangePlayersName(String name) {
		try {
			oos.writeObject(name);
			oos.flush();
			game.setName2player((String) ois.readObject());
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void Send() {
		new Thread() {
			public void run() {
				while (game.isGamenotover()) {
					try {
						sleep(5);
						Player1Data();
					} catch (InterruptedException | IOException e) {
						game.setPlayerleave(true);
						break;
					}
				}
			}
		}.start();
	}

	public void Receive() {
		new Thread() {
			public void run() {
				while (game.isGamenotover()) {
					try {
						Player2Data();
					} catch (ClassNotFoundException | IOException e) {
						game.setPlayerleave(true);
						break;
					}
				}
			}
		}.start();
	}

	public synchronized void Player1Data() throws IOException {
		int[] arr;
		int tmp = game.getSnake()[0].getLength() * 2;
		arr = new int[(game.getSnake()[0].getLength()) * 2 + 20];
		arr[0] = game.getSnake()[0].getLength();
		System.arraycopy(game.getSnake()[0].getSnakex(), 0, arr, 1, game.getSnake()[0].getLength());
		System.arraycopy(game.getSnake()[0].getSnakey(), 0, arr, game.getSnake()[0].getLength() + 1,
				game.getSnake()[0].getLength());
		tmp++;
		arr[tmp] = game.getSnake()[0].getDirection();
		arr[tmp + 1] = 10;
		arr[tmp + 2] = game.getSpeed() / 200;
		arr[tmp + 3] = game.getStar().getStarX();
		arr[tmp + 4] = game.getStar().getStarY();
		if (game.isNewStar()) {
			arr[tmp + 1] = 5;
			game.setNewStar(false);
		}
		arr[tmp + 5] = game.getPlayer1speed();
		arr[tmp + 6] = game.getMeteorites().getMeteoriteX()[0];
		arr[tmp + 7] = game.getMeteorites().getMeteoriteY()[0];
		arr[tmp + 8] = 10;
		if (game.isNewSteroids()) {
			arr[tmp + 8] = 5;
			game.setNewSteroids(false);
		}
		arr[tmp + 9] = game.getMeteorites().getMeteoriteX()[1];
		arr[tmp + 10] = game.getMeteorites().getMeteoriteY()[1];
		arr[tmp + 11] = game.getMeteorites().getMeteoriteX()[2];
		arr[tmp + 12] = game.getMeteorites().getMeteoriteY()[2];
		oos.writeObject(arr);
	}

	public void Player2Data() throws ClassNotFoundException, IOException {
		int[] mass;
		mass = new int[game.getSnake()[1].getLength() * 2 + 50];
		mass = (int[]) ois.readObject();
		game.getSnake()[1].setLength(mass[0]);
		System.arraycopy(mass, 1, game.getSnake()[1].getSnakex(), 0, mass[0]);
		System.arraycopy(mass, mass[0] + 1, game.getSnake()[1].getSnakey(), 0, mass[0]);
		mass[0] = mass[0] * 2 + 1;
		game.getSnake()[1].setDirection(mass[mass[0]]);
		game.setPlayer2speed(mass[mass[0] + 5]);
		if (mass[mass[0] + 1] == 5) {
			if (game.isPlayer1())
				game.setStarDate(0);
			game.getStar().setStarX(mass[mass[0] + 3]);
			game.getStar().setStarY(mass[mass[0] + 4]);
		}
		if (mass[mass[0] + 8] == 5) {
			game.getMeteorites().getMeteoriteX()[0] = mass[mass[0] + 6];
			game.getMeteorites().getMeteoriteY()[0] = mass[mass[0] + 7];
			game.getMeteorites().getMeteoriteX()[1] = mass[mass[0] + 9];
			game.getMeteorites().getMeteoriteY()[1] = mass[mass[0] + 10];
			game.getMeteorites().getMeteoriteX()[2] = mass[mass[0] + 11];
			game.getMeteorites().getMeteoriteY()[2] = mass[mass[0] + 12];
		}
	}

	public ObjectOutputStream getOos() {
		return oos;
	}

	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}

	public ObjectInputStream getOis() {
		return ois;
	}

	public void setOis(ObjectInputStream ois) {
		this.ois = ois;
	}


	public Game getGame() {
		return game;
	}


	public void setGame(Game game) {
		this.game = game;
	}
}
