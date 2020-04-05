import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

public class UI {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI window = new UI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel UsernameLabel = new JLabel("Username");
		UsernameLabel.setBounds(10, 10, 61, 13);
		frame.getContentPane().add(UsernameLabel);
		
		textField = new JTextField();
		textField.setBounds(60, 7, 126, 16);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton ConnectButton = new JButton("Connect");
		ConnectButton.setBounds(192, 6, 85, 21);
		frame.getContentPane().add(ConnectButton);
		
		JLabel OnlineUserLabel = new JLabel("Online Users");
		OnlineUserLabel.setBounds(348, 10, 78, 13);
		frame.getContentPane().add(OnlineUserLabel);
		
		JTextArea OnlineUserArea = new JTextArea();
		OnlineUserArea.setEditable(false);
		OnlineUserArea.setBounds(334, 35, 92, 218);
		frame.getContentPane().add(OnlineUserArea);
		
		JTextArea ChatArea = new JTextArea();
		ChatArea.setLineWrap(true);
		ChatArea.setEditable(false);
		ChatArea.setBounds(10, 33, 314, 161);
		frame.getContentPane().add(ChatArea);
		
		JTextArea SendArea = new JTextArea();
		SendArea.setBounds(10, 204, 233, 49);
		frame.getContentPane().add(SendArea);
		
		JButton SendButtton = new JButton("SEND");
		SendButtton.setBounds(253, 218, 71, 21);
		frame.getContentPane().add(SendButtton);
	}
}
