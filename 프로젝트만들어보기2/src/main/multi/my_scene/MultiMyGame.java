package main.multi.my_scene;

import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Font;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.SocketChannel;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;

import main.MainFrame;
import main.common.MyButton;
import main.common.GameScene;
import main.common.MouseShape;
import main.common.Plate;
import main.multi.AlertDialog;

public abstract class MultiMyGame extends Dialog implements GameScene, MouseShape {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	MainFrame frame = null;
	Set<Plate> claies = null;
	JLabel game_time = null;
	JLabel game_score = null;
	JButton to_room = null;
	int score = 0;
	boolean isEnd = false;
	
	Runnable time_start = null;
	Runnable create_clay = null;
	ThreadGroup claies_group = null;
	SocketChannel socketChannel;
	
	public MultiMyGame(MainFrame frame, SocketChannel socketChannel) {
		super(frame, "내 화면", true);
		this.frame = frame;
		this.socketChannel = socketChannel;
		setBounds(100, 200, 900, 800);
		setLayout(null);
		
		
		
		game_time = new JLabel("00:00");		// 게임 타이틀 라벨
		game_time.setBounds(250, 50, 300, 50);
		game_time.setFont(new Font("Consolas", Font.BOLD, 40));
		add(game_time);
		
		game_score = new JLabel("Score " + score);
		game_score.setBounds(500, 50, 200, 50);
		game_score.setFont(new Font("Consolas", Font.BOLD, 40));
		add(game_score);
		
		claies = Collections.synchronizedSet(new HashSet<Plate>());
		
		setMouseShape(this);
		
		to_room = new MyButton(300, 400, "Into Room", e -> {
			frame.setCursor(Cursor.getDefaultCursor());
			dispose();
		});
		add(to_room);
		hideMenu();
	}
	
	abstract public void endGame();
	
	@Override
	public void hideMenu() {
		to_room.setVisible(false);
		frame.setCursor(frame.blankCursor);
	}
	
	@Override
	public void showMenu() {
		to_room.setVisible(true);
		frame.setCursor(Cursor.getDefaultCursor());
	}
	
	protected void sendMousePoint(int x, int y) {
		try {
			IntBuffer intBuffer = IntBuffer.wrap(new int[] { x, y, 0 });
			ByteBuffer byteBuffer = ByteBuffer.allocate(intBuffer.capacity() * 4);
			for (int i = 0; i < intBuffer.capacity(); i++) {
				byteBuffer.putInt(intBuffer.get(i));
			}
			byteBuffer.flip();
			socketChannel.write(byteBuffer);
			
			System.out.println("[마우스 포인터 전송]");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				System.out.println("[상대방과 통신 두절 : " + socketChannel.getRemoteAddress() + 
						" : " + Thread.currentThread().getName() + "]");				
				socketChannel.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			isEnd = true;
			frame.setCursor(Cursor.getDefaultCursor());
			frame.remove(this);
			new AlertDialog(frame, AlertDialog.MSG_NET1);
		}
	}
	
	protected void sendClayPoint(int height, int x) {
		try {
			IntBuffer intBuffer = IntBuffer.wrap(new int[] { height, x });
			ByteBuffer byteBuffer = ByteBuffer.allocate(intBuffer.capacity() * 4);
			for (int i = 0; i < intBuffer.capacity(); i++) {
				byteBuffer.putInt(intBuffer.get(i));
			}
			byteBuffer.flip();
			socketChannel.write(byteBuffer);
			
			System.out.println("[플레이트 높이 전송]");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				System.out.println("[상대방과 통신 두절 : " + socketChannel.getRemoteAddress() + 
						" : " + Thread.currentThread().getName() + "]");				
				socketChannel.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			isEnd = true;
			frame.setCursor(Cursor.getDefaultCursor());
			frame.remove(this);
			new AlertDialog(frame, AlertDialog.MSG_NET1);
		}
	}
}
