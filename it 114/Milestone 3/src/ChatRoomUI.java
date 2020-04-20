import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class ChatRoomUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JLabel GreetingLabel;
	
	private JTextField title;
	
	private JTextField titleServer, titlePort;
	
	private JButton login, logout, UserOnline;

	private boolean connected;
	
	private MainClient client;

	private int defaultPort;
	private String defaultHost;
	private JLayeredPane layeredPane;
	private JTextArea AreaChat;
	private JLabel EnterLabel;
	private JTextArea textArea;
	private JLabel lblNewLabel_2;

	
	ChatRoomUI(String host, int port) {

		super("Chat MainClient");
		defaultPort = port;
		defaultHost = host;
		
		
		JPanel NPanel = new JPanel(new GridLayout(3,1));
		
		JPanel SP = new JPanel(new GridLayout(1,5, 1, 3));
		
		titleServer = new JTextField(host);
		titlePort = new JTextField("" + port);
		titlePort.setHorizontalAlignment(SwingConstants.RIGHT);

		SP.add(new JLabel("Server Address:  "));
		SP.add(titleServer);
		SP.add(new JLabel("Port Number:  "));
		SP.add(titlePort);
		SP.add(new JLabel(""));
	
		NPanel.add(SP);

		
		GreetingLabel = new JLabel("Welcome IT114 to Sabbir's Chatroom", SwingConstants.CENTER);
		NPanel.add(GreetingLabel);
		getContentPane().add(NPanel, BorderLayout.NORTH);
		JPanel CPanel = new JPanel(new GridLayout(1,1));
		JScrollPane scllPane = new JScrollPane();
		CPanel.add(scllPane);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBackground(SystemColor.menu);
		scllPane.setViewportView(layeredPane);
		
		AreaChat = new JTextArea();
		AreaChat.setBackground(SystemColor.info);
		AreaChat.setEditable(false);
		AreaChat.setBounds(0, 0, 372, 381);
		layeredPane.add(AreaChat);
		title = new JTextField("");
		title.setHorizontalAlignment(SwingConstants.LEFT);
		title.setBounds(0, 402, 302, 71);
		layeredPane.add(title);
		title.setBackground(SystemColor.controlLtHighlight);
		
				
				login = new JButton("Login");
				login.setBounds(312, 420, 86, 35);
				layeredPane.add(login);
				
				JLabel EnterUsernameLabel = new JLabel("Enter Username Below and Text to Chat");
				EnterUsernameLabel.setBounds(0, 379, 276, 23);
				layeredPane.add(EnterUsernameLabel);
				
				EnterLabel = new JLabel("Press Enter button to send message");
				EnterLabel.setBounds(0, 471, 302, 23);
				layeredPane.add(EnterLabel);
				logout = new JButton("Logout");
				logout.setVerticalAlignment(SwingConstants.TOP);
				logout.setBounds(429, 445, 125, 23);
				layeredPane.add(logout);
				logout.addActionListener(this);
				logout.setEnabled(false);		
				UserOnline = new JButton("Online Users");
				UserOnline.setVerticalAlignment(SwingConstants.TOP);
				UserOnline.setBounds(429, 402, 125, 23);
				layeredPane.add(UserOnline);
				UserOnline.addActionListener(this);
				UserOnline.setEnabled(false);		
				
				textArea = new JTextArea();
				textArea.setBackground(SystemColor.info);
				textArea.setBounds(392, 38, 182, 343);
				layeredPane.add(textArea);
				
				lblNewLabel_2 = new JLabel("Guidelines");
				lblNewLabel_2.setBounds(451, 15, 72, 13);
				layeredPane.add(lblNewLabel_2);
				login.addActionListener(this);
		title.requestFocus();
		getContentPane().add(CPanel, BorderLayout.CENTER);

		JPanel SPanel = new JPanel();
		getContentPane().add(SPanel, BorderLayout.SOUTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 600);
		setVisible(true);

	}

	
	void append(String str) {
		AreaChat.append(str);
		AreaChat.setCaretPosition(AreaChat.getText().length() - 1);
	}
	
	void connectionFailed() {
		login.setEnabled(true);
		logout.setEnabled(false);
		UserOnline.setEnabled(false);
		GreetingLabel.setText("Enter your username below");
		title.setText(" ");
		
		titlePort.setText("" + defaultPort);
		titleServer.setText(defaultHost);
		
		titleServer.setEditable(false);
		titlePort.setEditable(false);
		
		title.removeActionListener(this);
		connected = false;
	}
		
	

	
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if(o == logout) {
			client.sendMessage(new MainMessage(MainMessage.LOGOUT, ""));
			return;
		}
		
		if(o == UserOnline) {
			client.sendMessage(new MainMessage(MainMessage.ONLINEUSERS, ""));				
			return;
		}

		
		if(connected) {
			
			client.sendMessage(new MainMessage(MainMessage.MESSAGE, title.getText()));				
			title.setText("");
			return;
		}
		

		if(o == login) {
		
			String username = title.getText().trim();
			
			if(username.length() == 0)
				return;
			
			String server = titleServer.getText().trim();
			if(server.length() == 0)
				return;
			
			String portNumber = titlePort.getText().trim();
			if(portNumber.length() == 0)
				return;
			int port = 0;
			try {
				port = Integer.parseInt(portNumber);
			}
			catch(Exception en) {
				return;  
			}

			
			client = new MainClient(server, port, username, this);
			
			if(!client.start()) 
				return;
			title.setText("");
			GreetingLabel.setText("Welcome to Sabbir's Chatroom");
			connected = true;
			
			
			login.setEnabled(false);
			
			logout.setEnabled(true);
			UserOnline.setEnabled(true);
		
			titleServer.setEditable(false);
			titlePort.setEditable(false);
			
			title.addActionListener(this);
		}

	}


	public static void main(String[] args) {
		new ChatRoomUI("localhost", 1500);
	}
}
