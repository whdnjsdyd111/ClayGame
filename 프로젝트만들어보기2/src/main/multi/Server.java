package main.multi;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import main.MainFrame;
import main.databases.MemberDAO;
import main.multi.my_scene.MultiMyGame;
import main.multi.my_scene.MyInfinity;
import main.multi.my_scene.MyReload;
import main.multi.my_scene.MyTime;
import main.multi.oppo_scene.MultiOppoGame;
import main.multi.oppo_scene.OppoInfinity;
import main.multi.oppo_scene.OppoReload;
import main.multi.oppo_scene.OppoTime;

public class Server {
	ServerSocketChannel serverSocketChannel;
	SocketChannel socketChannel;
	private MainFrame frame;
	private String nickname;
	private JLabel oppo_nickname;
	private JTextArea textArea;
	private JButton startBtn;
	private JComboBox<String> comboBox;
	static final int PORT = 7000;
	
	private MultiOppoGame oppo_scene = null;
	private MultiMyGame my_scene = null;
	
	public Server(MainFrame frame, String nickname, JLabel oppo_nickname, JTextArea textArea, JButton startBtn, 
			JComboBox<String> comboBox, MultiMyGame my_scene) {
		// ���� ����
		this.frame = frame;
		this.nickname = nickname;
		this.oppo_nickname = oppo_nickname;
		this.textArea = textArea;
		this.startBtn = startBtn;
		this.comboBox = comboBox;
		this.my_scene = my_scene;
		
		try {
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(true);	// ���ŷ ��� ����
			serverSocketChannel.bind(new InetSocketAddress(PORT));	// ���� ���ε�
		} catch (Exception e) {
			e.printStackTrace();
			if(serverSocketChannel.isOpen()) stopServer();
			return;
		}
		
		startBtn.addActionListener(e -> {	// ������ �Ͽ��� �� �����ϰڴٴ� ���� �˸��� ���� ���濡�� byte 1 ������
			send((byte) -1);
		});
		
		System.out.println("[���� ����]");
		connect();
	}
	
	private void connect() {
		new Thread(() -> {
			while(true) {
				try {
					socketChannel = serverSocketChannel.accept();
					System.out.println("[���� ���� ����]");
					// ������ ������ �ְ� ������
					break;
				} catch (Exception e) {
					e.printStackTrace();
					if(serverSocketChannel.isOpen()) stopServer();
					break;
				}
			}
			System.out.println("[���� ��� ����]");
			getNickname();	// ���濡�Լ� �г����� ���
			sendNickname();	// �г��� ��� ���濡�� �ڽ��� �г��� ������
			startBtn.setEnabled(true);	// ��ŸƮ ��ư Ȱ��ȭ
			send((byte) comboBox.getSelectedIndex());	// ���� ���õ� �޺� �ڽ��� �ε��� ������
			receive();	// ���� �� ������(��ȭ) �ޱ�
		}).start();
	}
	
	public void stopServer() {	// ���� ���ߴ� �޼ҵ�
		try {
			if(socketChannel != null && socketChannel.isOpen())
				socketChannel.close();	// ���� ä�� �ݱ�
			
			if(serverSocketChannel != null && serverSocketChannel.isOpen())
				serverSocketChannel.close();	// ����ä�� �ݱ�
			
			System.out.println("[���� ����]");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void receive() {	// ������ �޴� �޼ҵ�
		new Thread(() -> {
			while(true) {
				try {
					ByteBuffer byteBuffer = ByteBuffer.allocate(100);
					
					// ���� �� ������ ���� �� IOException �߻�
					int readByteCount = socketChannel.read(byteBuffer);	// ������ �ޱ�
					
					// ���� �� ���������� SocketChannel close �Ͽ��� ���
					if(readByteCount == -1)
						throw new IOException();
					
					System.out.println("[��û ó�� : " + socketChannel.getRemoteAddress() + 
							" : " + Thread.currentThread().getName() + "]");
					
					byteBuffer.flip();	// ���ڿ��� ��ȯ
					
					if(byteBuffer.get(0) == -1) {
						// ���� ������ Ŭ���̾�Ʈ�� �˷� ���� Ŭ���̾�Ʈ�� ������ �����ߴٴ� ���� �ް� IntBuffer ����
						
						if(comboBox.getSelectedIndex() == 0)
							oppo_scene = new OppoTime(frame);
						if(comboBox.getSelectedIndex() == 1)
							oppo_scene = new OppoInfinity(frame);
						if(comboBox.getSelectedIndex() == 2)
							oppo_scene = new OppoReload(frame);
						
						receiveGameInfo();
						
						if(comboBox.getSelectedIndex() == 0)
							my_scene = new MyTime(frame, socketChannel, oppo_scene);
						if(comboBox.getSelectedIndex() == 1)
							my_scene = new MyInfinity(frame, socketChannel, oppo_scene);
						if(comboBox.getSelectedIndex() == 2)
							my_scene = new MyReload(frame, socketChannel, oppo_scene);
						
						
						break;
					}
					
					Charset charset = Charset.forName("UTF-8");
					String data = charset.decode(byteBuffer).toString();
					
					System.out.println("[������ ����] " + data);
					textArea.append("\n" + data);
					textArea.setCaretPosition(textArea.getDocument().getLength());
				} catch (Exception e) {
					e.printStackTrace();
					try {
						System.out.println("[����� ��� ���� : " + socketChannel.getRemoteAddress() + 
								" : " + Thread.currentThread().getName() + "]");						
						socketChannel.close();
						connect();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					oppout();
					break;
				}
			}
		}).start();
	}
	
	public void send(String data) {	// ��ȭ ������ �޼ҵ�
		try {
			if(socketChannel == null)
				return;
			
			Charset charset = Charset.forName("UTF-8");
			ByteBuffer byteBuffer = charset.encode(data);
			socketChannel.write(byteBuffer);
			
			// System.out.println("[������ �Ϸ�]");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				// System.out.println("[����� ��� ���� : " + socketChannel.getRemoteAddress() + 
				// 		" : " + Thread.currentThread().getName() + "]");				
				socketChannel.close();
				connect();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			oppout();
		}
	}
	
	public void send(byte data) {	// ���� ���� ������ �޼ҵ�
		try {
			if(socketChannel == null)
				return;

			ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[] { data });
			socketChannel.write(byteBuffer);
			
			// System.out.println("[���� ���� ������ �Ϸ�]");
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				// System.out.println("[����� ��� ���� : " + socketChannel.getRemoteAddress() + 
				// 		" : " + Thread.currentThread().getName() + "]");				
				socketChannel.close();
				connect();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			oppout();
		}
	}
	
	private void getNickname() {	// �г��� �޴� �޼ҵ�
		while(true) {
			try {
				ByteBuffer byteBuffer = ByteBuffer.allocate(100);
				
				// ���� �� ������ ���� �� IOException �߻�
				int readByteCount = socketChannel.read(byteBuffer);	// ������ �ޱ�
				
				// ���� �� ���������� SocketChannel close �Ͽ��� ���
				if(readByteCount == -1)
					throw new IOException();
				
				// System.out.println("[��û ó�� : " + socketChannel.getRemoteAddress() + 
				// 		" : " + Thread.currentThread().getName() + "]");
				
				byteBuffer.flip();	// ���ڿ��� ��ȯ
				Charset charset = Charset.forName("UTF-8");
				String data = charset.decode(byteBuffer).toString();
				
				// System.out.println("[���� �г��� ����]");
				oppo_nickname.setText(data);
				textArea.setText(textArea.getText() + "\n" + data + "���� �����ϼ̽��ϴ�.");
				break;
			} catch (Exception e2) {
				e2.printStackTrace();
				try {
					// System.out.println("[����� ��� ���� : " + socketChannel.getRemoteAddress() + 
					// 		" : " + Thread.currentThread().getName() + "]");						
					socketChannel.close();
					connect();
				} catch (Exception e3) {
					e3.printStackTrace();
					break;
				}
				oppout();
				break;
			}
		}
	}
	
	private void sendNickname() {	// �г��� ������ �޼ҵ�
		try {
			Charset charset = Charset.forName("UTF-8");
			ByteBuffer byteBuffer = charset.encode(nickname);
			socketChannel.write(byteBuffer);
			
			// System.out.println("[�г��� ������ �Ϸ�]");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				// System.out.println("[����� ��� ���� : " + socketChannel.getRemoteAddress() + 
				// 		" : " + Thread.currentThread().getName() + "]");				
				socketChannel.close();
				connect();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			oppout();
		}
	}
	
	private void oppout() {	// ���� ������ �� �˷��ִ� �޼ҵ�
		textArea.setText(textArea.getText() + "\n" + oppo_nickname.getText() + "���� �����̽��ϴ�.");
		oppo_nickname.setText("");
		startBtn.setEnabled(false);
	}
	
	private void receiveGameInfo() {	// ���� ������ �޴� �޼ҵ�
		// System.out.println("[���� ���� �ޱ� ����]");
		
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
					// System.out.println("���� ������ ����");
					
					if(data[0] == -1) {
						oppo_scene.endGame(data[1]);
						break;
					}
					else if(data[0] == -2)
						oppo_scene.reload();
					else  if(data.length == 2)
						oppo_scene.create_clay(data[0], data[1]);
					else if(data.length == 3)
						oppo_scene.receiveMousePoint(data[0], data[1]);
						
				} catch (Exception e) {
					e.printStackTrace();
					try {
						// System.out.println("[����� ��� ���� : " + socketChannel.getRemoteAddress() + 
						// 		" : " + Thread.currentThread().getName() + "]");						
						socketChannel.close();
						connect();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					oppout();
					break;
				}
			}
			
			if(comboBox.getSelectedIndex() == 0 || comboBox.getSelectedIndex() == 2) {
				if(my_scene.game_score.getText().compareTo(oppo_scene.game_score.getText()) > 0) {
					textArea.append("\n���� �¸�");
					textArea.append("\n" + MemberDAO.getInstance().updatePVP("win"));
				} else if(my_scene.game_score.getText().compareTo(oppo_scene.game_score.getText()) < 0) {
					textArea.append("\n���� �й�");
					textArea.append("\n" + MemberDAO.getInstance().updatePVP("lose"));
				} else {
					textArea.append("\n���º�");
					textArea.append("\n" + MemberDAO.getInstance().updatePVP("draw"));
				}
			} else {
				if(my_scene.score > 0) {
					textArea.append("\n���� �¸�");
					textArea.append("\n" + MemberDAO.getInstance().updatePVP("win"));
				} else if(my_scene.score == 0) {
					textArea.append("\n���� �й�");
					textArea.append("\n" + MemberDAO.getInstance().updatePVP("lose"));
				}
			}

			receive();
		}).start();
	}
}
