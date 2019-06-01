import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Client {

	Socket clientSocket;
	// public JTextField tx;
	// public JTextArea ta;
	DataOutputStream outToServer;
	BufferedReader inFromServer;
	BufferedReader inFromUser;
	BufferedWriter writer;
	BufferedReader reader;

	

	public Client(String ip) {
		String sentence;
		String serverSentence;
		gui g = new gui();
		g.frame.setVisible(true);
		int port = 911;

		while (true) {
			if (g.textField != null && g.textField.getText() != null
					&& g.isTextNotEmpty()) {
				port = Integer.parseInt(g.textField.getText());
				g.textField.setText("");
				g.setTextNotEmpty(false);
				g.frame.setTitle("connected to: " + port);
				break;
			}
		}

		// JFrame f=new JFrame("Client");
		// f.setSize(800,800);

		// JPanel p1=new JPanel();
		// p1.setLayout(new BorderLayout());

		// JPanel p2=new JPanel();
		// p2.setLayout(new BorderLayout());

		// tx=new JTextField();
		// p1.add(tx, BorderLayout.CENTER);

		// JButton b1=new JButton("Send");
		// p1.add(b1, BorderLayout.EAST);
		// b1.addActionListener(this);

		// ta=new JTextArea();
		// p2.add(ta, BorderLayout.CENTER);
		// p2.add(p1, BorderLayout.SOUTH);

		// f.setContentPane(p2);
		// f.setVisible(true);

		// f.setDefaultCloseOperation(EXIT_ON_CLOSE);

		try {
			clientSocket = new Socket("127.0.0.1", port);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());

			inFromServer = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			// writer= new BufferedWriter(new
			// OutputStreamWriter(clientSocket.getOutputStream()));

			// reader =new BufferedReader(new
			// InputStreamReader(clientSocket.getInputStream()));
			inFromUser = new BufferedReader(new InputStreamReader(System.in));

			while (true) {
				while (true) {
					if (g.textField != null && g.textField.getText() != null
							&& g.isTextNotEmpty()) {
						sentence = g.textField.getText();
						g.setTextNotEmpty(false);
						String[] message = g.textField.getText().split(" ");
						if (message.length > 0
								&& message[0].equalsIgnoreCase("login")) {
							if (message.length > 1) {
								g.frame.setTitle(message[1] + " port: " + port);
							}
						}
						g.textField.setText("");
						break;
					}

				}
				// if (inFromUser.ready()) {
			//	sentence = inFromUser.readLine();
				outToServer.writeBytes(sentence + '\n');
				if (sentence.equalsIgnoreCase("quit")) {
					clientSocket.close();
					g.frame.setVisible(false);
					break;
					// }
				}
				new Thread() {
					@Override
					public void run() {
						try {
							while (true) {
								String in = inFromServer.readLine();
								g.textArea.append( in + "\n");
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();

			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws NumberFormatException,
			IOException {

		// BufferedReader bf =new BufferedReader(new
		// InputStreamReader(System.in));
		// int port = Integer.parseInt(bf.readLine());
		// Client client=new Client("127.0.0.1",port);
		Client client = new Client("127.0.0.1");

	}

}
