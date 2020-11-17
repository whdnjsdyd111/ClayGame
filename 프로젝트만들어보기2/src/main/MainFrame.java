package main;

import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import main.databases.SingleDatabaseDialog;
import main.mainPane.MainPane;
import main.multi.FindPasswdPane;
import main.multi.MultiLoginPane;
import main.multi.MultiRegPane;
import main.single.InfinityMode;
import main.single.ReloadMode;
import main.single.SingleGamePane;
import main.single.TimeMode;

public class MainFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CardLayout card = null;				// 카드 레이아웃을 사용하여 장면 넘기기
	public MainPane main = null;				// 메인 페인
	public SingleGamePane single = null;		// 싱글 게임 페인
	public MultiLoginPane multiLogin = null;	// 멀티 플레이 전 로그인 페인
	public MultiRegPane multiReg = null;		// 회원가입 페인
	public FindPasswdPane findpasswd = null;	// 비밀번호 찾기 페인
	public InfinityMode infinityMode = null;	// 무한 모드 레이아웃 페인
	public ReloadMode reload = null;			// 장전 모드 레이아웃 페인
	public TimeMode time = null;				// 시간 제한 모드 레이아웃 페인
	public Cursor blankCursor = null;			// 커서 
	public SingleDatabaseDialog dialog = null;	// 데이터베이스 DAO 다이알로그
	
	public MainFrame() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	// 게임 실행 시 미리 데이터베이스 드라이버 적재
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setTitle("클레이 사격 게임");		// 제목
		setResizable(false);		// 사이즈 재조정 금지
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 800);			// 사이즈
		
		Dimension frameSize = this.getSize(); // 프레임 사이즈
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 모니터 사이즈
		
		this.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2); // 화면 중앙에 띄우기

		card = new CardLayout(0, 0);	// 카드 레이아웃 초기화
		
		setLayout(card);	// 카드 레이아웃 세팅
		
		add("main", main = new MainPane(this));						// 각각의 페인을 초기화하면서 JFrame에 집어 넣기
		add("single", single = new SingleGamePane(this));
		add("multiLogin", multiLogin = new MultiLoginPane(this));
		add("multiReg", multiReg = new MultiRegPane(this));
		add("findPasswd", findpasswd = new FindPasswdPane(this));
		add("infinity", infinityMode =  new InfinityMode(this));
		add("reload", reload = new ReloadMode(this));
		add("time", time = new TimeMode(this));
		
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);	// 투명한 버퍼 이미지
		blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				cursorImg, new Point(0, 0), "blank cursor"); 		// 커서에 투명한 이미지를 넣어서 커서가 안보이도록 하기
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
}
