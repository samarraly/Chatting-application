package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientThread implements Runnable {

	private Socket connectionSocket;
	private DataOutputStream outToClient;
	private BufferedReader inFromClient;
	private String clientSentence;
	private String capitalizedSentence;
	private Thread thread;
	private int id;
	private Server server;
	private Server otherServer;
	private String name = null;

	// private ArrayList clientList;
	public ClientThread(Server server, Server otherServer,
			Socket connectionSocket, int id) {
		this.connectionSocket = connectionSocket;
		this.id = id;
		this.server = server;
		this.otherServer = otherServer;

		thread = new Thread(this);
		try {
			inFromClient = new BufferedReader(new InputStreamReader(
					connectionSocket.getInputStream()));
			outToClient = new DataOutputStream(connectionSocket.getOutputStream());

			thread.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {

		try {
			while (true) {
				clientSentence = inFromClient.readLine();
				String[] tokens = clientSentence.split(" ");
				String cmd = tokens[0];
				System.out.println("From Client: " + clientSentence);
				if (!clientSentence.isEmpty()) {
					if (clientSentence.equalsIgnoreCase("quit")) {
						boolean remove=false;
						 ArrayList<ClientThread> clients11111 = server.getClients();
					     ArrayList<ClientThread> clients22222 = otherServer.getClients();
						for (int i =0 ;i<clients11111.size();i++){
							if(this.getUserName().equalsIgnoreCase(clients11111.get(i).getUserName())){
								this.setName("");
								remove=true;
								break;
							}
							if (!remove){
								for (int j =0 ;i<clients22222.size();i++){
									if(this.getUserName().equalsIgnoreCase(clients22222.get(j).getUserName())){
										this.setName("");
										remove=true;
										break;
									}
							}
						}
						
						
						if(remove==true){
						connectionSocket.close();
						return;
						}
					}
					}
					if (cmd.equalsIgnoreCase("login")) {
						// handleLoginMsg(outToClient, tokens);
						handleLoginMsg(tokens);
					}
					if (cmd.equalsIgnoreCase("pm")) {
						handlePrivateMessage(tokens);
						// System.out.println(cmd);
						// handlePrivateMessage(tokens);
					}
					if (cmd.equalsIgnoreCase("memberlist")) {
						handlegetmemberlist(tokens);
					}
					// capitalizedSentence = clientSentence.toUpperCase() +
					// '\n';
					// outToClient.writeBytes(capitalizedSentence);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getUserName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	private void handleLoginMsg(String[] tokens) throws Exception {
     boolean login =true;
     ArrayList<ClientThread> clients1111 = server.getClients();
     ArrayList<ClientThread> clients2222 = otherServer.getClients();
   
     for (int i = 0; i < clients1111.size(); i++) {
			
			// if they both belong to the same server
			if (tokens[1].equalsIgnoreCase(clients1111.get(i).getUserName())){
				this.outToClient.writeBytes("this name is already used" + '\n');
				login=false;
				break;
				
				}
			}
			if (login==true) {

		
				for (int j = 0; j< clients2222.size(); j++) {
					if (tokens[1].equalsIgnoreCase(clients2222.get(j).getUserName())) {
							this.outToClient.writeBytes("this name is already used" + '\n');
							login=false;
							break;
							
						} 
							}
				
						}
			if(login ==false){
				return;
			}
		
		
		if(login=true){
		if (tokens.length == 2 ) {
			String name = tokens[1]; // client's name
			String msg = "Logged in succsessfuly\n";
			this.outToClient.writeBytes(msg);
			this.name = name;

			System.out.println("Logged in sa7");
		}
		
		ArrayList<ClientThread> clients11 = server.getClients();
		
		for (int i = 0; i < clients11.size(); i++) {
				// if they both belong to the same server
			 
			   	if (!(this.getUserName().equalsIgnoreCase(clients11.get(i).getUserName()))){
					
					
					this.outToClient.writeBytes(clients11.get(i).getUserName() + '\n');
					}
	
		}
			ArrayList<ClientThread> clients222 = otherServer.getClients();
			for (int i = 0; i < clients222.size(); i++) {
				 
				   if (!(this.getUserName().equalsIgnoreCase(clients222.get(i).getUserName()))){
						this.outToClient.writeBytes(clients222.get(i).getUserName() + '\n');
				
				}
			}
		}
	}
	
	private void handlegetmemberlist(String[] tokens) throws IOException{
		ArrayList<ClientThread> clients1 = server.getClients();
	
		for (int i = 0; i < clients1.size(); i++) {
			  if (!(this.getUserName().equalsIgnoreCase(clients1.get(i).getUserName()))){
					this.outToClient.writeBytes(clients1.get(i).getUserName() + '\n');
					}
		}
			ArrayList<ClientThread> clients22 = otherServer.getClients();
			for (int i = 0; i < clients22.size(); i++) {
				  if (!(this.getUserName().equalsIgnoreCase(clients22.get(i).getUserName()))){
						this.outToClient.writeBytes(clients22.get(i).getUserName() + '\n');	
				}
			}
			
		
		
	}
	
	
	

	public int getId() {
		return id;
	}

	private void handlePrivateMessage(String[] tokens) throws IOException {
		String sendTo = tokens[1];
		System.out.println(sendTo);
		String bdy = "";
        int TTL=2;
		for (int i = 2; i < tokens.length; i++) {
			bdy = bdy + " " + tokens[i];
			System.out.println("bdy" + bdy);
		}
		boolean found=false;
		ArrayList<ClientThread> clientss1 = server.getClients();
		for(int k=0 ;k<clientss1.size();k++){
			if(this.getUserName().equalsIgnoreCase(clientss1.get(k).getUserName())){
		      found=true;
		
      
		ArrayList<ClientThread> clients = server.getClients();
		boolean flag = false;
		for (int i = 0; i < clients.size(); i++) {
			if (sendTo.equalsIgnoreCase(clients.get(i).getUserName())) {
				// if they both belong to the same server
				TTL--;
				try {
					 if (TTL==0){
						 this.outToClient.writeBytes("TTL = "+TTL+" Cannot sending the message "+'\n');
						}
					else{
					clients.get(i).outToClient.writeBytes("From "+this.getUserName()+" to "+clients.get(i).getUserName()+" TTL "+ TTL +":" +bdy + '\n');
					flag = true;
					 }
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
   
		}
		
		if (!flag) {

			ArrayList<ClientThread> clients2 = otherServer.getClients();
			System.out.println(clients2.size());
			for (int i = 0; i < clients2.size(); i++) {
				if (sendTo.equalsIgnoreCase(clients2.get(i).getUserName())) {
					
					try {
						TTL--;
						if (TTL==0){
							 this.outToClient.writeBytes("TTL = "+TTL+" Cannot sending the message "+'\n');
							}
						
						else{
						clients2.get(i).outToClient.writeBytes("From "+this.getUserName()+" to "+clients2.get(i).getUserName()+" TTL "+ TTL +":" +bdy + '\n');
						flag = true;
						}
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						// sit
						e.printStackTrace();
					}
				}

			

		}
       }
		
		}
		}
		if (!found){
			
			ArrayList<ClientThread> clientss2 = otherServer.getClients();
			for(int m=0 ;m<clientss1.size();m++){
				if(this.getUserName().equalsIgnoreCase(clientss1.get(m).getUserName())){
			      found=true;
			
	      
			ArrayList<ClientThread> clients = server.getClients();
			boolean flag = false;
			for (int i = 0; i < clients.size(); i++) {
				if (sendTo.equalsIgnoreCase(clients.get(i).getUserName())) {
					// if they both belong to the same server
					
					try {
						TTL--;
						if (TTL==0){
							 this.outToClient.writeBytes("TTL = "+TTL+" Cannot sending the message "+'\n');
							}
						
						else {
						clients.get(i).outToClient.writeBytes("From "+this.getUserName()+" to "+clients.get(i).getUserName()+" TTL "+ TTL +":" +bdy + '\n');
						flag = true;
						}
					
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
	  
			}
			 
			if (!flag) {

				ArrayList<ClientThread> clients2 = otherServer.getClients();
				System.out.println(clients2.size());
				for (int i = 0; i < clients2.size(); i++) {
					if (sendTo.equalsIgnoreCase(clients2.get(i).getUserName())) {
						
						try {
							TTL--;
							if (TTL==0){
								 this.outToClient.writeBytes("TTL = "+TTL+" Cannot sending the message "+'\n');
								}
						else {
							clients2.get(i).outToClient.writeBytes("From "+this.getUserName()+" to "+clients2.get(i).getUserName()+" TTL "+ TTL +":" +bdy + '\n');
							flag = true;
							}
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							// sit
							e.printStackTrace();
						}
					}

				

			}
	       }
			
			}
			}
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
