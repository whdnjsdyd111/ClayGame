package main.single;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import main.MainFrame;
import main.common.LabelBtn;
import main.common.MouseShape;

public class SingleGamePane extends JLayeredPane implements MouseShape {
	
	MainFrame frame = null;
	
	public SingleGamePane(MainFrame frame) {
		setLayout(null);
		this.frame = frame;
		
		JLabel game_title = new JLabel("Ŭ���� ��� ����");		// ���� Ÿ��Ʋ ��
		game_title.setBounds(450, 100, 300, 50);
		game_title.setFont(new Font("�޸�����ü", Font.BOLD, 40));
		add(game_title);
		
		JLabel infinity = new LabelBtn(125, 250, "���� ���", new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.card.show(frame.getContentPane(), "infinity");
				frame.infinityMode.startGame();
			}
		});
		infinity.setBounds(125, 250, 250, 100);
		infinity.setFont(new Font("�޸տ�ü", Font.BOLD, 35));
		add(infinity);
		
		JLabel time = new LabelBtn(485, 250, "�ð� ���� ���", new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.card.show(frame.getContentPane(), "time");
				frame.time.startGame();
			}
		});
		time.setSize(300, 100);
		time.setLocation(450, 250);
		time.setFont(new Font("�޸տ�ü", Font.BOLD, 35));
		add(time);
		
		JLabel reload = new LabelBtn(875, 250, "���� ���", new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.card.show(frame.getContentPane(), "reload");
				frame.reload.startGame();
			}
		});
		reload.setFont(new Font("�޸տ�ü", Font.BOLD, 35));
		add(reload);
		
		JLabel to_main = new LabelBtn(285, 480, "��������", new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.card.show(frame.getContentPane(), "main");
			}
		});
		to_main.setFont(new Font("�޸տ�ü", Font.PLAIN, 35));
		add(to_main);
		
		JLabel end = new LabelBtn(680, 480, "������", new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		end.setFont(new Font("�޸տ�ü", Font.BOLD, 35));
		add(end);
	}
	
}
