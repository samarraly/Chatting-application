package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class Server extends Thread {
	private ServerSocket welcomeSocket;
	private ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	private Server otherServer;
	private int port;
	int counter=0;
	static Server s1;
	static Server s2;
	static int id;
	public Server(int i, Server s2) {
		this.port = i;
		this.otherServer = s2;
}
	

	@Override
	public void run() {
		 
		try {
			
			welcomeSocket = new ServerSocket(port);
			System.out.println("The Server is Running on Port: " + port);
			int id = 1;
			while (true) {
				Socket connectionSocket = welcomeSocket.accept();
				System.out.println("The Server accepted a client num " + id + " " +port);
				// id++;
			     
				
				ClientThread client = new ClientThread(this, otherServer,connectionSocket, id);
				id++;
				clients.add(client);
				System.out.println("Client added");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public ArrayList getClients() {
		return clients;
	}

	public void setClients(ArrayList clients) {
		this.clients = clients;
	}

	public void setServer(Server s2) {
		// TODO Auto-generated method stub
		this.otherServer = s2;
	}

	public void setOtherServer(Server s1) {
		this.otherServer = s1;
		
	}
	public static int getid() {
		// TODO Auto-generated method stub
		return id;
	}
	
	public void setid(){
		this.id=id;
	}

	

	
}
