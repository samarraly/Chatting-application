
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.ImageIcon;
public class gui implements ActionListener{
	public JFrame frame;
	public JTextField textField;
	public JTextArea textArea;
	public JButton btnNewButton;
	private String in;
	private boolean isTextNotEmpty = false;
	public boolean isTextNotEmpty() {
		return isTextNotEmpty;
	}

	public void setTextNotEmpty(boolean isTextEmpty) {
		this.isTextNotEmpty = isTextEmpty;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui window = new gui();
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
	public gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
	
		frame.setForeground(new Color(169, 169, 169));
		frame.setBackground(new Color(112, 128, 144));
		frame.setBounds(100, 100, 450, 496);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textArea = new JTextArea();
		textArea.setToolTipText("Message Area");
		textArea.setFont(new Font("Lucida Sans", Font.BOLD | Font.ITALIC, 15));
		textArea.setBackground(Color.PINK);
		textArea.setEditable(false);
		textArea.setBounds(0, 0, 444, 428);
		frame.getContentPane().add(textArea);
		
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.BOLD, 14));
		textField.setForeground(new Color(128, 0, 0));
		textField.setBackground(Color.WHITE);
		textField.setToolTipText("Enter your msg here");
		textField.setBounds(0, 429, 309, 32);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		btnNewButton = new JButton("Send");
		btnNewButton.setToolTipText("Send Input to the server");
		btnNewButton.setBackground(Color.LIGHT_GRAY);
		
		btnNewButton.setForeground(new Color(0, 100, 0));
		btnNewButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		btnNewButton.addActionListener(this);
		btnNewButton.setActionCommand("send");
		btnNewButton.setBounds(321, 428, 123, 32);
		frame.getContentPane().add(btnNewButton);
	}
	
	public String getUserIn() {
		return in;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if("send".equals(e.getActionCommand())) {
			in = textField.getText();
			isTextNotEmpty = true;
		}
	}
}
	


