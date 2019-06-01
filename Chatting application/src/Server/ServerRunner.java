package Server;

public class ServerRunner {
	static Server s1;
	static Server s2;
	public static void main(String[] args) {
		s2 = new Server(1502,s1);
		s1 = new Server(1501,s2);
		
		s2.setOtherServer(s1);
		s1.setServer(s2);
		s1.start();
		s2.start();
	}
}
