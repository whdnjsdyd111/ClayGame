package main.multi;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextArea;

import main.MainFrame;
import main.multi.my_scene.MultiMyGame;
import main.multi.my_scene.MyInfinity;
import main.multi.my_scene.MyTime;
import main.multi.oppo_scene.MultiOppoGame;
import main.multi.oppo_scene.OppoInfinity;
import main.multi.oppo_scene.OppoReload;
import main.multi.oppo_scene.OppoTime;

public class Client {
	SocketChannel socketChannel;
	private String nickname;
	private JLabel master_nickname;
	private JTextArea textarea;
	private MainFrame frame;
	private JLayeredPane joinedRoom;
	private JComboBox<String[]> comboBox;
	
	private MultiOppoGame oppo_scene = null;
	private MultiMyGame my_scene = null;
	
	public Client(String ip, String nickname, JLabel master_nickname, JTextArea textarea, MainFrame frame, 
			JLayeredPane joinedRoom, JComboBox<String[]> comboBox) {
		this.nickname = nickname;
		this.master_nickname = master_nickname;
		this.textarea = textarea;
		this.frame = frame;
		this.joinedRoom = joinedRoom;
		this.comboBox = comboBox;
		
		try {
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(true);	// ���ŷ ��� ����
			socketChannel.connect(new InetSocketAddress(ip, Server.PORT));
			System.out.println("[���� �Ϸ� : " + socketChannel.getRemoteAddress() + "]");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[���� ��� �ȵ�]");
			if(socketChannel.isOpen()) stopClient();
			return;			
		}
		
		sendNickname();
		getNickname();
		
		receive();	// ������ ������ �ޱ�
	}
	
	public void stopClient() {
		try {
			System.out.println("[���� ����]");
			if(socketChannel != null && socketChannel.isOpen())
				socketChannel.close();	// ���� ����
		} catch (Exception e) {
			e.printStackTrace();
		}
		frame.card.show(frame.getContentPane(), "multi");
		frame.remove(joinedRoom);
	}
	
	private void receive() {
		new Thread(() -> {
			while(true) {
				try {
					ByteBuffer byteBuffer = ByteBuffer.allocate(100);
					
					// ���� ������������ ������� �� IOException �߻�
					int readByteCount = socketChannel.read(byteBuffer);
					
					// ���� ���������� Socket close�� ȣ�� ���� ���
					if(readByteCount == -1)
						throw new IOException();
					
					byteBuffer.flip();
					
					if(byteBuffer.get(0) == 0) {
						comboBox.setSelectedIndex(0);
						continue;
					} else if(byteBuffer.get(0) == 1) {
						comboBox.setSelectedIndex(1);
						continue;
					} else if(byteBuffer.get(0) == 2) {
						comboBox.setSelectedIndex(2);
						continue;
					} else if(byteBuffer.get(0) == 3) {
						System.out.println("[���� ����]");
						sendGameStart();
						
						if(comboBox.getSelectedIndex() == 0) {
							oppo_scene = new OppoTime(frame);
							//my_scene = new MyTime(frame, socketChannel);
						} else if(comboBox.getSelectedIndex() == 1) {
							oppo_scene = new OppoInfinity(frame);
							//my_scene = new MyInfinity(frame, socketChannel);
						} else if(comboBox.getSelectedIndex() == 2) {
							oppo_scene = new OppoReload(frame);
							//my_scene = new MyReload(frame, socketChannel);
						}
						
						receiveGameInfo();
						break;
					}
					
					Charset charset = Charset.forName("UTF-8");
					String data = charset.decode(byteBuffer).toString();	// ���ڿ� ��ȯ
					System.out.println("[������ ����]");
					textarea.setText(textarea.getText() + "\n" + data);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("[���� ��� �ȵ�]");
					stopClient();
					new AlertDialog(frame, AlertDialog.MSG_NET);
					break;
				}
			}
		}).start();
	}
	
	public void send(String data) {
		try {
			Charset charset = Charset.forName("UTF-8");
			ByteBuffer byteBuffer = charset.encode(data);
			socketChannel.write(byteBuffer);	// ������ ������ ������
			System.out.println("[������ �Ϸ�]");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[���� ��� �ȵ�]");
			stopClient();
			new AlertDialog(frame, AlertDialog.MSG_NET);
		}
	}
	
	public void sendGameStart() {
		try {
			ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[] { (byte) 0 });
			socketChannel.write(byteBuffer);
			
			System.out.println("[Ŭ���̾�Ʈ ���� ���� ������ �Ϸ�]");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[���� ��� �ȵ�]");
			stopClient();
			new AlertDialog(frame, AlertDialog.MSG_NET);
		}
	}
	
	private void getNickname() {
		while(true) {
			try {
				ByteBuffer byteBuffer = ByteBuffer.allocate(100);
				
				// ���� ������������ ������� �� IOException �߻�
				int readByteCount = socketChannel.read(byteBuffer);			
				
				// ���� ���������� Socket close�� ȣ�� ���� ���
				if(readByteCount == -1)
					throw new IOException();
				
				byteBuffer.flip();
				Charset charset = Charset.forName("UTF-8");
				String data = charset.decode(byteBuffer).toString();	// ���ڿ� ��ȯ
				System.out.println("[�г��� ����]");
				master_nickname.setText(data);
				break;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[���� ��� �ȵ�]");
				stopClient();
				new AlertDialog(frame, AlertDialog.MSG_NET);
				break;
			}
		}
	}
	
	private void sendNickname() {
		try {
			Charset charset = Charset.forName("UTF-8");
			ByteBuffer byteBuffer = charset.encode(nickname);
			socketChannel.write(byteBuffer);	// ������ ������ ������
			System.out.println("[�г��� ������ �Ϸ�]");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[���� ��� �ȵ�]");
			stopClient();
			new AlertDialog(frame, AlertDialog.MSG_NET);
		}
	}
	
	private void receiveGameInfo() {
		System.out.println("[���� ���� �ޱ� ����]");
		new Thread(() -> {
			while(true) {
				try {
					ByteBuffer byteBuffer = ByteBuffer.allocate(100);
					
					// ���� ������������ ������� �� IOException �߻�
					int readByteCount = socketChannel.read(byteBuffer);
					
					// ���� ���������� Socket close�� ȣ�� ���� ���
					if(readByteCount == -1)
						throw new IOException();

					byteBuffer.flip();
					IntBuffer intBuffer = byteBuffer.asIntBuffer();
					int[] data = new int[intBuffer.capacity()];
					intBuffer.get(data);
					
					if(data[0] == -1) {
						oppo_scene.endGame(data[1]);
					} else if(data.length == 2)
						oppo_scene.create_clay(data[0], data[1]);
					else if(data.length == 3)
						oppo_scene.receiveMousePoint(data[0], data[1]);
						
					
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("[���� ��� �ȵ�]");
					stopClient();
					new AlertDialog(frame, AlertDialog.MSG_NET);
					break;
				}
			}
		}).start();
	}
}
