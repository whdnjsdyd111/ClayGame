package main.multi;

import javax.swing.JLayeredPane;

import main.MainFrame;
import main.common.Buttons;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

public class MultiGamePane extends JLayeredPane {
	MainFrame frame = null;
	private JTextField tf_ip;
	
	public MultiGamePane(MainFrame frame, String nickname) {
		this.frame = frame;
		setLayout(null);
		
		JLabel ip_label = new JLabel(" IP 설정");
		ip_label.setFont(new Font("돋움", Font.BOLD, 25));
		ip_label.setBounds(350, 100, 100, 50);
		add(ip_label);
		
		JLabel nick_label = new JLabel(" 닉네임");
		nick_label.setFont(new Font("돋움", Font.BOLD, 25));
		nick_label.setBounds(350, 200, 100, 50);
		add(nick_label);
		
		tf_ip = new JTextField();
		tf_ip.setText("127.0.0.1");
		tf_ip.setBounds(550, 115, 200, 30);
		add(tf_ip);
		tf_ip.setColumns(10);
		
		JLabel nick_label_1 = new JLabel(nickname);
		nick_label_1.setFont(new Font("돋움", Font.BOLD, 20));
		nick_label_1.setBounds(550, 200, 300, 50);
		add(nick_label_1);
		
		Buttons create_room = new Buttons(400, 350, "방 만들기", e -> {
			CreatedRoom create = new CreatedRoom(frame, nickname);
			if(create.server.serverSocketChannel.isOpen() == true) {
				frame.add("createdRoom", create);
				frame.card.show(frame.getContentPane(), "createdRoom");
			} else
				new AlertDialog(frame, AlertDialog.MSG_ADDR);
		});
		add(create_room);
		
		Buttons join_room = new Buttons(400, 450, "참여하기", e -> {
			JoinedRoom join = new JoinedRoom(frame, tf_ip.getText(), nickname);
			if(join.client.socketChannel.isConnected() == true) {
				frame.add("joinedRoom", join);
				frame.card.show(frame.getContentPane(), "joinedRoom");
			} else
				new AlertDialog(frame, AlertDialog.MSG_NET);
		});
		add(join_room);
		
		Buttons to_main = new Buttons(400, 550, "메인으로", e -> {
			frame.card.show(frame.getContentPane(), "main");
			frame.remove(this);
		});
		add(to_main);
	}
}
