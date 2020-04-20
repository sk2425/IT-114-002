import java.net.*;
import java.io.*;
import java.util.*;


public class MainClient  {

	
	private ObjectInputStream sInput;		
	private ObjectOutputStream sOutput;		
	private Socket socket;

	
	private ChatRoomUI cg;
	
	
	private String server, username;
	private int port;

	
	MainClient(String server, int port, String username) {
		
		this(server, port, username, null);
	}

	
	MainClient(String server, int port, String username, ChatRoomUI cg) {
		this.server = server;
		this.port = port;
		this.username = username;
		// save if we are in GUI mode or not
		this.cg = cg;
	}
	
	
	public boolean start() {
		
		try {
			socket = new Socket(server, port);
		} 
		
		catch(Exception ec) {
			display("Error connectiong to server:" + ec);
			return false;
		}
		
		String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
		display(msg);
	
		
		try
		{
			sInput  = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException eIO) {
			display("Exception creating new Input/output Streams: " + eIO);
			return false;
		}

		 
		new ListenFromServer().start();
	
		
		try
		{
			sOutput.writeObject(username);
		}
		catch (IOException eIO) {
			display("Exception doing login : " + eIO);
			disconnect();
			return false;
		}
		
		return true;
	}

	
	private void display(String msg) {
		if(cg == null)
			System.out.println(msg);      
		else
			cg.append(msg + "\n");		
	}
	
	
	void sendMessage(MainMessage msg) {
		try {
			sOutput.writeObject(msg);
		}
		catch(IOException e) {
			display("Exception writing to server: " + e);
		}
	}

	
	private void disconnect() {
		try { 
			if(sInput != null) sInput.close();
		}
		catch(Exception e) {} 
		try {
			if(sOutput != null) sOutput.close();
		}
		catch(Exception e) {} 
        try{
			if(socket != null) socket.close();
		}
		catch(Exception e) {} 
		
	
		if(cg != null)
			cg.connectionFailed();
			
	}
	
	public static void main(String[] args) {
		//
		int portNumber = 1500;
		String serverAddress = "localhost";
		String userName = "Anonymous";

	
		switch(args.length) {
			
			case 3:
				serverAddress = args[2];
		
			case 2:
				try {
					portNumber = Integer.parseInt(args[1]);
				}
				catch(Exception e) {
					System.out.println("Invalid port number.");
					System.out.println("Usage is: > java MainClient [username] [portNumber] [serverAddress]");
					return;
				}
			
			case 1: 
				userName = args[0];
			
			case 0:
				break;
	
			default:
				System.out.println("Usage is: > java MainClient [username] [portNumber] {serverAddress]");
			return;
		}

		MainClient client = new MainClient(serverAddress, portNumber, userName);
	
		if(!client.start())
			return;
		
		
		Scanner scan = new Scanner(System.in);
		
		while(true) {
			System.out.print("> ");
			
			String msg = scan.nextLine();
			
			if(msg.equalsIgnoreCase("LOGOUT")) {
				client.sendMessage(new MainMessage(MainMessage.LOGOUT, ""));
				
				break;
			}
			
			else if(msg.equalsIgnoreCase("ONLINEUSERS")) {
				client.sendMessage(new MainMessage(MainMessage.ONLINEUSERS, ""));				
			}
			else {				
				client.sendMessage(new MainMessage(MainMessage.MESSAGE, msg));
			}
		}
	
		client.disconnect();	
	}

	
	class ListenFromServer extends Thread {

		public void run() {
			while(true) {
				try {
					String msg = (String) sInput.readObject();
					
					if(cg == null) {
						System.out.println(msg);
						System.out.print("> ");
					}
					else {
						cg.append(msg);
					}
				}
				catch(IOException e) {
					display("Server has close the connection: " + e);
					if(cg != null) 
						cg.connectionFailed();
					break;
				}
				
				catch(ClassNotFoundException e2) {
				}
			}
		}
	}
}
