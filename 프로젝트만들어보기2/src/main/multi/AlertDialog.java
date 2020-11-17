package main.multi;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import main.common.MyButton;

public class AlertDialog extends Dialog {
	
	// 경고창 다이알로그
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String MSG1 = "이미 존재하는 아이디입니다.";
	public static final String MSG2 = "아이디는 영어와 숫자만 가능합니다.";
	public static final String MSG3 = "아이디는 6자 이상에서 18자 이하만 가능합니다.";
	public static final String MSG_EMPTY = "입력하지 않은 사항이 있습니다.";
	public static final String MSG_PW = "<html><body>암호는 최소 하나의 문자, 숫자 및 특수문자로<br>8자 이상 입력해주십시오.</body></html>";
	public static final String MSG_NICK = "닉네임은 2자 이상에서 10자 이하만 가능합니다.";
	public static final String MSG_ID = "존재하지 않는 아이디입니다.";
	public static final String MSG_NOT_MATCH = "두 비밀번호는 일치해야 합니다.";
	public static final String MSG_FAIL = "아이디 또는 비밀번호가 틀렸습니다.";
	public static final String MSG_NET = "통신이 안되거나 방장이 나갔습니다.";
	public static final String MSG_NET1 = "상대방이 나갔습니다.";
	public static final String MSG_ADDR = "이미 사용 중인 IP 주소입니다.";
	
	public AlertDialog(JFrame frame, String msg) {
		super(frame, "경고창", true);
		setBounds(0, 0, 450, 350);
		setResizable(false);
		setLayout(null);
		
		Dimension frameSize = this.getSize(); // 프레임 사이즈
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 모니터 사이즈
		
		this.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2); // 화면 중앙
		
		JLabel msgLabel = new JLabel(msg);	// 메시지 라벨 초기화
		msgLabel.setFont(new Font("돋움", Font.BOLD, 15));
		msgLabel.setBounds(10, 100, 430, 50);
		msgLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(msgLabel);
		
		MyButton btn = new MyButton(75, 275, "OK", e -> {	// 경고창 끄는 버튼
			dispose();
		});
		add(btn);
		
		setVisible(true);
	}
}
