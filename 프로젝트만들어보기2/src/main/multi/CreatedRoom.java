package main.multi;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import main.MainFrame;
import main.common.Buttons;
import main.multi.my_scene.MultiMyGame;
import main.multi.my_scene.MyInfinity;
import main.multi.my_scene.MyReload;
import main.multi.my_scene.MyTime;

public class CreatedRoom extends JLayeredPane {
	
	MainFrame frame = null;
	Server server = null;
	MultiMyGame multi = null;
	
	public CreatedRoom(MainFrame frame, String nickname) {
		this.frame = frame;
		setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBorder(new LineBorder(Color.PINK, 5));
		textArea.setBounds(50, 50, 700, 550);
		textArea.setEnabled(false);
		add(textArea);
		
		JLabel master = new JLabel("����");
		master.setFont(new Font("�޸յձ�������", Font.BOLD, 30));
		master.setHorizontalAlignment(SwingConstants.CENTER);
		master.setBounds(875, 170, 200, 70);
		add(master);
		
		JLabel master_nickname = new JLabel(nickname);
		master_nickname.setFont(new Font("���� ���", Font.BOLD, 17));
		master_nickname.setHorizontalAlignment(SwingConstants.CENTER);
		master_nickname.setBounds(800, 250, 350, 20);
		add(master_nickname);
		
		JLabel oppo = new JLabel("����");
		oppo.setHorizontalAlignment(SwingConstants.CENTER);
		oppo.setFont(new Font("�޸յձ�������", Font.BOLD, 30));
		oppo.setBounds(875, 300, 200, 70);
		add(oppo);
		
		JLabel oppo_nickname = new JLabel();
		oppo_nickname.setHorizontalAlignment(SwingConstants.CENTER);
		oppo_nickname.setFont(new Font("���� ���", Font.BOLD, 17));
		oppo_nickname.setBounds(800, 380, 350, 20);
		add(oppo_nickname);
		
		JComboBox<String[]> comboBox = new JComboBox<>();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"�ð� ���� ���", "���� ���", "���� ���"}));
		comboBox.setBounds(825, 580, 300, 25);
		comboBox.addItemListener(e -> {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				if(e.getItem().equals("�ð� ���� ���"))
					server.send((byte) 0);
				else if(e.getItem().equals("���� ���"))
					server.send((byte) 1);
				else
					server.send((byte) 2);
			}
		});
		add(comboBox);
		
		Buttons startBtn = new Buttons(825, 500, "Game Start", e -> {
			server.send((byte) 3);
			if(comboBox.getSelectedIndex() == 0)
				multi = new MyTime(frame, server.socketChannel);
			if(comboBox.getSelectedIndex() == 1)
				multi = new MyInfinity(frame, server.socketChannel);
			if(comboBox.getSelectedIndex() == 2)
				multi = new MyReload(frame, server.socketChannel);
		});
		// startBtn.setEnabled(false);
		add(startBtn);
		
		server = new Server(frame, nickname, oppo_nickname, textArea, startBtn, comboBox);
		
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
						textArea.setText(textArea.getText() + "\n[��] " + chat);
						server.send("[" + nickname + "] " + chat);
						tf_chat.setText("");
					}
				}
			}
		});
		add(tf_chat);
		tf_chat.setColumns(10);
		
		JButton to_multi = new JButton("��");
		to_multi.setFont(new Font("����", Font.PLAIN, 65));
		to_multi.setBounds(925, 50, 100, 100);
		to_multi.addActionListener(e -> {
			server.stopServer();
			frame.card.show(frame.getContentPane(), "multi");
			frame.remove(this);
		});
		add(to_multi);
	}
}
