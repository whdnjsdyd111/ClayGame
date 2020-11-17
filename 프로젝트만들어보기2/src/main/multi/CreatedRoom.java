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
		
		JTextArea textArea = new JTextArea();	// 채팅창 텍스트 어레이
		textArea.setFont(new Font("2D Coding", Font.BOLD, 15));
		textArea.setEnabled(false);
		JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(50, 50, 700, 550);
		scroll.setBorder(new LineBorder(Color.DARK_GRAY, 3));	// 채팅이 많아아지면 스크롤이 가능하도록 
		add(scroll);
		
		JLabel master = new JLabel("Host");	// 방장 라벨
		master.setFont(new Font("Consolas", Font.BOLD, 30));
		master.setHorizontalAlignment(SwingConstants.CENTER);
		master.setBounds(875, 170, 200, 70);
		add(master);
		
		JLabel master_nickname = new JLabel(nickname);	// 닉네임 라벨
		master_nickname.setFont(new Font("2D Coding", Font.BOLD, 17));
		master_nickname.setHorizontalAlignment(SwingConstants.CENTER);
		master_nickname.setBounds(800, 250, 350, 20);
		add(master_nickname);
		
		JLabel oppo = new JLabel("Opponent");	// 상대방 라벨
		oppo.setHorizontalAlignment(SwingConstants.CENTER);
		oppo.setFont(new Font("Consolas", Font.BOLD, 30));
		oppo.setBounds(875, 300, 200, 70);
		add(oppo);
		
		JLabel oppo_nickname = new JLabel();	// 상대방 닉네임 라벨
		oppo_nickname.setHorizontalAlignment(SwingConstants.CENTER);
		oppo_nickname.setFont(new Font("2D Coding", Font.BOLD, 17));
		oppo_nickname.setBounds(800, 380, 350, 20);
		add(oppo_nickname);
		
		JComboBox<String> comboBox = new JComboBox<>();	// 모드 선택 콤보 박스
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"시간 제한 모드", "무한 모드", "장전 모드"}));
		comboBox.setBounds(825, 580, 300, 25);
		comboBox.addItemListener(e -> {	// 모드를 선택할 때 마다 해당 메뉴의 종류를 클라이언트에게 전달
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
		
		MyButton startBtn = new MyButton(825, 500, "Game Start", null);		// 게임 시작 버튼 
		startBtn.setEnabled(false);
		add(startBtn);
		
		server = new Server(frame, nickname, oppo_nickname, textArea, startBtn, comboBox, multi);	// 서버 초기화
		
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
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {	// 엔터일 경우
					if(tf_chat.getText().equals(""))	// 아무 채팅을 치지 않았을 때 리턴
						return;
					else {
						String chat = tf_chat.getText();	// 채팅 텍스트 필드를 가져와서 텍스트 어레이에 추가를 한 후 상대방에게 보내기
						textArea.append("\n[나] " + chat);
						server.send("[" + nickname + "] " + chat);
						tf_chat.setText("");
						textArea.setCaretPosition(textArea.getDocument().getLength());
					}
				}
			}
		});
		add(tf_chat);
		
		MyButton to_multi = new MyButton(925, 50, "◁", e -> {	// 방을 나가는 버튼
			server.stopServer();
			frame.card.show(frame.getContentPane(), "multi");
			frame.remove(this);
		});
		to_multi.setFont(new Font("굴림", Font.PLAIN, 65));
		to_multi.setBounds(925, 50, 100, 100);
		add(to_multi);
	}
}
