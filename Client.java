package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client extends DataExchange {
	private int port = 5;
	private String IP;
	private Socket socket;
	private String name;
	public Client(String IP, String name) {
		this.IP = IP;
		this.name=name;
	}

	public boolean connect() {
		try {
			socket = new Socket(IP, port);
			setOis(new ObjectInputStream(socket.getInputStream()));
			setOos(new ObjectOutputStream(socket.getOutputStream()));
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	public void Stop(){
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
