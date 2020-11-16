package main.multi;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import main.MainFrame;
import main.common.MyButton;
import main.multi.my_scene.MultiMyGame;

public class CreatedRoom extends JLayeredPane {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	MainFrame frame = null;
	Server server = null;
	MultiMyGame multi = null;
	
	public CreatedRoom(MainFrame frame, String nickname) {
		this.frame = frame;
		setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("2D Coding", Font.BOLD, 15));
		textArea.setEnabled(false);
		JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(50, 50, 700, 550);
		scroll.setBorder(new LineBorder(Color.DARK_GRAY, 3));
		add(scroll);
		
		JLabel master = new JLabel("Host");
		master.setFont(new Font("Consolas", Font.BOLD, 30));
		master.setHorizontalAlignment(SwingConstants.CENTER);
		master.setBounds(875, 170, 200, 70);
		add(master);
		
		JLabel master_nickname = new JLabel(nickname);
		master_nickname.setFont(new Font("2D Coding", Font.BOLD, 17));
		master_nickname.setHorizontalAlignment(SwingConstants.CENTER);
		master_nickname.setBounds(800, 250, 350, 20);
		add(master_nickname);
		
		JLabel oppo = new JLabel("Opponent");
		oppo.setHorizontalAlignment(SwingConstants.CENTER);
		oppo.setFont(new Font("Consolas", Font.BOLD, 30));
		oppo.setBounds(875, 300, 200, 70);
		add(oppo);
		
		JLabel oppo_nickname = new JLabel();
		oppo_nickname.setHorizontalAlignment(SwingConstants.CENTER);
		oppo_nickname.setFont(new Font("2D Coding", Font.BOLD, 17));
		oppo_nickname.setBounds(800, 380, 350, 20);
		add(oppo_nickname);
		
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"시간 제한 모드", "무한 모드", "장전 모드"}));
		comboBox.setBounds(825, 580, 300, 25);
		comboBox.addItemListener(e -> {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				if(e.getItem().equals("시간 제한 모드"))
					server.send((byte) 0);
				else if(e.getItem().equals("무한 모드"))
					server.send((byte) 1);
				else
					server.send((byte) 2);
			}
		});
		add(comboBox);
		
		MyButton startBtn = new MyButton(825, 500, "Game Start", null);
		startBtn.setEnabled(false);
		add(startBtn);
		
		server = new Server(frame, nickname, oppo_nickname, textArea, startBtn, comboBox, multi);
		
		JTextField tf_chat = new JTextField();
		tf_chat.setBorder(new LineBorder(Color.BLACK, 3));
		tf_chat.setBounds(50, 600, 700, 30);
		tf_chat.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(tf_chat.getText().equals(""))
						return;
					else {
						String chat = tf_chat.getText();
						textArea.append("\n[나] " + chat);
						server.send("[" + nickname + "] " + chat);
						tf_chat.setText("");
						textArea.setCaretPosition(textArea.getDocument().getLength());
					}
				}
			}
		});
		add(tf_chat);
		
		MyButton to_multi = new MyButton(925, 50, "◁", e -> {
			server.stopServer();
			frame.card.show(frame.getContentPane(), "multi");
			frame.remove(this);
		});
		to_multi.setFont(new Font("굴림", Font.PLAIN, 65));
		to_multi.setBounds(925, 50, 100, 100);
		add(to_multi);
	}
}
