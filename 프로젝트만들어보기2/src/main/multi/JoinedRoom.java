package main.multi;

import java.awt.Color;
import java.awt.Font;
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

public class JoinedRoom extends JLayeredPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	MainFrame frame = null;
	Client client;
	
	public JoinedRoom(MainFrame frame, String ip, String nickname) {
		this.frame = frame;
		setLayout(null);
		
		JTextArea textArea = new JTextArea();	// 채팅창 텍스트 어레이
		textArea.setFont(new Font("2D Coding", Font.BOLD, 15));
		textArea.setEnabled(false);
		JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(50, 50, 700, 550);
		scroll.setBorder(new LineBorder(Color.DARK_GRAY, 3));	// 채팅이 많아아지면 스크롤이 가능하도록 
		add(scroll);
		
		JLabel master = new JLabel("Host");		// 방장 라벨
		master.setFont(new Font("Consolas", Font.BOLD, 30));
		master.setHorizontalAlignment(SwingConstants.CENTER);
		master.setBounds(875, 170, 200, 70);
		add(master);
		
		JLabel master_nickname = new JLabel();	// 방장 닉네임 라벨
		master_nickname.setFont(new Font("돋움", Font.BOLD, 17));
		master_nickname.setHorizontalAlignment(SwingConstants.CENTER);
		master_nickname.setBounds(800, 250, 350, 20);
		add(master_nickname);
		
		JLabel oppo = new JLabel("You");	// 나를 가리키는 라벨
		oppo.setHorizontalAlignment(SwingConstants.CENTER);
		oppo.setFont(new Font("Consolas", Font.BOLD, 30));
		oppo.setBounds(875, 300, 200, 70);
		add(oppo);
		
		JLabel oppo_nickname = new JLabel(nickname);	// 나의 닉네임 라벨
		oppo_nickname.setHorizontalAlignment(SwingConstants.CENTER);
		oppo_nickname.setFont(new Font("돋움", Font.BOLD, 17));
		oppo_nickname.setBounds(800, 380, 350, 20);
		add(oppo_nickname);
		
		JComboBox<String> comboBox = new JComboBox<>();	// 모드 선택 콤보박스
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"시간 제한 모드", "무한 모드", "장전 모드"}));
		comboBox.setBounds(825, 580, 300, 25);
		comboBox.setEnabled(false);
		add(comboBox);
		
		client = new Client(ip, nickname, master_nickname, textArea, frame, this, comboBox);	// 클라이언트 생성
		
		
		JTextField tf_chat = new JTextField();	// 채팅 텍스트 필드
		tf_chat.setBorder(new LineBorder(Color.BLACK, 3));
		tf_chat.setBounds(50, 600, 700, 30);
		tf_chat.addKeyListener(new KeyListener() {	// 키 리스너 등록
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {	// 키를 눌렀을 때
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {	// 누른 키가 엔터 일 경우
					if(tf_chat.getText().equals(""))	// 아무 채팅을 치지 않았다면 리턴
						return;
					else {
						String chat = tf_chat.getText();	// 채팅 내용 가져와서
						textArea.append("\n[나] " + chat);	// 텍스트 어레이에 추가 후
						client.send("[" + nickname + "] " + chat);	// 상대방에게 채팅 내용 보내기
						tf_chat.setText("");
						textArea.setCaretPosition(textArea.getDocument().getLength());	// 채팅창 스크롤 맨 아래로 이동
					}
				}
			}
		});
		add(tf_chat);
		tf_chat.setColumns(10);
		
		MyButton to_multi = new MyButton(925, 50, "◁", e -> {	// 방을 나가는 버튼
			client.stopClient();
			frame.card.show(frame.getContentPane(), "multi");
			frame.remove(this);
		});
		to_multi.setFont(new Font("굴림", Font.PLAIN, 65));
		to_multi.setBounds(925, 50, 100, 100);
		add(to_multi);
		
		MyButton startBtn = new MyButton(825, 500, "Game Start", e -> {	// 시작 버튼 방장이 시작 가능
			
		});
		startBtn.setEnabled(false);
		add(startBtn);
	}
}
