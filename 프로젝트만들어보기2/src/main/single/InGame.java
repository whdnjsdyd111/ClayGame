package main.single;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import main.MainFrame;
import main.common.MyButton;
import main.common.GameScene;
import main.common.MouseShape;
import main.common.Plate;

public abstract class InGame extends JLayeredPane implements GameScene, MouseShape {
	// �̱� ������ ����� ������� ���� �߻� Ŭ����, GameScene�� MouseShape �������̽��� ����
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	MainFrame frame = null;		// ���� ������
	Set<Plate> claies = null;	// �÷���Ʈ�� ������ Set
	JLabel game_time = null;	// ���� �ð� ��
	JLabel game_score = null;	// ���� ���� ��
	JButton to_single = null;	// �̱� �޴��� ���ư��� ��ư 
	JButton restart = null;		// ������� ���� ��ư
	int score = 0;				// ���ھ� 0���� ����
	boolean isEnd = false;		// ������ �������� ������ �Ҹ���
	
	Runnable time_start = null;		//  ���� ���� Runnable
	Runnable create_clay = null;		// �÷���Ʈ ���� Runnable
	ThreadGroup claies_group = null;	// �÷���Ʈ �׷�

	private Image image = new ImageIcon(MainFrame.class.getResource("resource/glass_land.jpg")).getImage();	// ���� ��濡 ���� �̹���
	
	public InGame(MainFrame frame) {
		this.frame = frame;
		
		setLayout(null);	// ���̾ƿ� �η� �ʱ�ȭ
		
		game_time = new JLabel("00:00");		// ���� �ð� ��
		game_time.setBounds(400, 25, 200, 50);	// ���� �ð�  ��ġ ����
		game_time.setFont(new Font("Consolas", Font.BOLD, 40));	// ���� �ð� ��Ʈ
		add(game_time);	// ���� �ð� �� �߰�
		
		game_score = new JLabel("Score " + score);	// ���� ���� ��
		game_score.setBounds(650, 25, 200, 50);		// ��ġ ����
		game_score.setFont(new Font("Consolas", Font.BOLD, 40));	// ���� ���� �� ��Ʈ ����
		add(game_score);	// ���� ���� �� �߰�
		
		claies = Collections.synchronizedSet(new HashSet<Plate>());	// �÷���Ʈ�� ������ Set �ʱ�ȭ
		
		setMouseShape(this);	// ���콺�� ����ٴϴ� ���� ��� �� �߰�
		
		to_single = new MyButton(450, 200, "Menu", e -> {	// �̱� �޴��� ���ư��� ��ư �ʱ�ȭ
			frame.card.show(frame.getContentPane(), "single");
			hideMenu();
			frame.setCursor(Cursor.getDefaultCursor());	// ���콺 Ŀ���� �ٽ� ����Ʈ Ŀ���� �ǵ�����
		});
		to_single.setLocation(250, 350);	// ��ġ ����
		
		restart = new MyButton(600, 200, "Restart", e -> {	// ����� ��ư �ʱ�ȭ
			hideMenu();
			startGame();
		});
		restart.setLocation(650, 350);	// ��ġ ����
		add(to_single);
		add(restart);	// �� ��ư �߰�
		hideMenu();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}
	

	@Override
	public void showMenu() {
		to_single.setVisible(true);
		restart.setVisible(true);
		frame.setCursor(Cursor.getDefaultCursor());
	}
	
	@Override
	public void hideMenu() {
		to_single.setVisible(false);
		restart.setVisible(false);
		frame.setCursor(frame.blankCursor);
	}
}
