package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Host extends DataExchange {
	private ServerSocket serverSocket;
	private int port = 5;
	private String IP;
	private boolean host = false;
	private String name;
	public Host(){
	}
	public Host(String IP, String name) {
		this.IP = IP;
		this.setName(name);
	}

	public boolean createServer() {
		try {
			serverSocket = new ServerSocket(port, 8, InetAddress.getByName(IP));
			host = true;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean waitforrequest() {
		Socket socket = null;
		try {
			socket = serverSocket.accept();
			setOos(new ObjectOutputStream(socket.getOutputStream()));
			setOis(new ObjectInputStream(socket.getInputStream()));
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	public void Stop(){
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isHost() {
		return host;
	}
	public void setHost(boolean host) {
		this.host = host;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
