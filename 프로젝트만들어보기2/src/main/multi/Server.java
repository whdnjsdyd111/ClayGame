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
import main.multi.oppo_scene.MultiOppoGame;
import main.multi.oppo_scene.OppoInfinity;
import main.multi.oppo_scene.OppoTime;

public class Server {
	ServerSocketChannel serverSocketChannel;
	SocketChannel socketChannel;
	private MainFrame frame;
	private String nickname;
	private JLabel oppo_nickname;
	private JTextArea textArea;
	private JButton startBtn;
	private JComboBox<String[]> comboBox;
	static final int PORT = 7000;
	private MultiOppoGame oppo_scene = null;
	
	public Server(MainFrame frame, String nickname, JLabel oppo_nickname, JTextArea textArea, JButton startBtn, JComboBox<String[]> comboBox) {
		// 서버 시작
		this.frame = frame;
		this.nickname = nickname;
		this.oppo_nickname = oppo_nickname;
		this.textArea = textArea;
		this.startBtn = startBtn;
		this.comboBox = comboBox;
		
		try {
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(true);	// 블로킹 방식 실행
			serverSocketChannel.bind(new InetSocketAddress(PORT));
		} catch (Exception e) {
			e.printStackTrace();
			if(serverSocketChannel.isOpen()) stopServer();
			return;
		}
		
		System.out.println("[서버 시작]");
		connect();
	}
	
	private void connect() {
		new Thread(() -> {
			while(true) {
				try {
					socketChannel = serverSocketChannel.accept();
					System.out.println("[상대방 연결 수락]");
					// 상대방을 수락해 주고 끝내기
					break;
				} catch (Exception e) {
					e.printStackTrace();
					if(serverSocketChannel.isOpen()) stopServer();
					break;
				}
			}
			System.out.println("[연결 허용 멈춤]");
			getNickname();
			sendNickname();
			startBtn.setEnabled(true);
			send((byte) comboBox.getSelectedIndex());
			receive();	// 연결 후 데이터 받기
		}).start();
	}
	
	public void stopServer() {
		try {
			if(socketChannel != null && socketChannel.isOpen())
				socketChannel.close();	// 소켓 채널 닫기
			
			if(serverSocketChannel != null && serverSocketChannel.isOpen())
				serverSocketChannel.close();	// 서버채널 닫기
			
			System.out.println("[서버 멈춤]");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void receive() {
		new Thread(() -> {
			while(true) {
				try {
					ByteBuffer byteBuffer = ByteBuffer.allocate(100);
					
					// 상대방 측 비정상 종료 시 IOException 발생
					int readByteCount = socketChannel.read(byteBuffer);	// 데이터 받기
					
					// 상대방 측 정상적으로 SocketChannel close 하였을 경우
					if(readByteCount == -1)
						throw new IOException();
					
					System.out.println("[요청 처리 : " + socketChannel.getRemoteAddress() + 
							" : " + Thread.currentThread().getName() + "]");
					
					byteBuffer.flip();	// 문자열로 변환
					
					if(byteBuffer.get(0) == 0) {
						// 게임 시작을 클라이언트에 알려 준후 클라이언트가 게임을 시작했다는 것을 받고 IntBuffer 시작
//						if(comboBox.getSelectedIndex() == 0)
//							oppo_scene = new OppoTime(frame);
//						if(comboBox.getSelectedIndex() == 1)
//							oppo_scene = new OppoInfinity(frame);
//						if(comboBox.getSelectedIndex() == 2)
//							oppo_scene = new OppoInfinity(frame);
//						receiveGameInfo();
//						
						break;
					}
					
					Charset charset = Charset.forName("UTF-8");
					String data = charset.decode(byteBuffer).toString();
					
					System.out.println("[데이터 받음] " + data);
					textArea.setText(textArea.getText() + "\n" + data);
				} catch (Exception e) {
					e.printStackTrace();
					try {
						System.out.println("[상대방과 통신 두절 : " + socketChannel.getRemoteAddress() + 
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
	
	public void send(String data) {
		try {
			if(socketChannel == null)
				return;
			
			Charset charset = Charset.forName("UTF-8");
			ByteBuffer byteBuffer = charset.encode(data);
			socketChannel.write(byteBuffer);
			
			System.out.println("[보내기 완료]");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				System.out.println("[상대방과 통신 두절 : " + socketChannel.getRemoteAddress() + 
						" : " + Thread.currentThread().getName() + "]");				
				socketChannel.close();
				connect();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			oppout();
		}
	}
	
	public void send(byte data) {
		try {
			if(socketChannel == null)
				return;

			ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[] { data });
			socketChannel.write(byteBuffer);
			
			System.out.println("[게임 종류 보내기 완료]");
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				System.out.println("[상대방과 통신 두절 : " + socketChannel.getRemoteAddress() + 
						" : " + Thread.currentThread().getName() + "]");				
				socketChannel.close();
				connect();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			oppout();
		}
	}
	
	private void getNickname() {
		while(true) {
			try {
				ByteBuffer byteBuffer = ByteBuffer.allocate(100);
				
				// 상대방 측 비정상 종료 시 IOException 발생
				int readByteCount = socketChannel.read(byteBuffer);	// 데이터 받기
				
				// 상대방 측 정상적으로 SocketChannel close 하였을 경우
				if(readByteCount == -1)
					throw new IOException();
				
				System.out.println("[요청 처리 : " + socketChannel.getRemoteAddress() + 
						" : " + Thread.currentThread().getName() + "]");
				
				byteBuffer.flip();	// 문자열로 변환
				Charset charset = Charset.forName("UTF-8");
				String data = charset.decode(byteBuffer).toString();
				
				System.out.println("[상대방 닉네임 받음]");
				oppo_nickname.setText(data);
				textArea.setText(textArea.getText() + "\n" + data + "님이 입장하셨습니다.");
				break;
			} catch (Exception e2) {
				e2.printStackTrace();
				try {
					System.out.println("[상대방과 통신 두절 : " + socketChannel.getRemoteAddress() + 
							" : " + Thread.currentThread().getName() + "]");						
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
	
	private void sendNickname() {
		try {
			Charset charset = Charset.forName("UTF-8");
			ByteBuffer byteBuffer = charset.encode(nickname);
			socketChannel.write(byteBuffer);
			
			System.out.println("[닉네임 보내기 완료]");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				System.out.println("[상대방과 통신 두절 : " + socketChannel.getRemoteAddress() + 
						" : " + Thread.currentThread().getName() + "]");				
				socketChannel.close();
				connect();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			oppout();
		}
	}
	
	private void oppout() {
		textArea.setText(textArea.getText() + "\n" + oppo_nickname.getText() + "님이 나가셨습니다.");
		oppo_nickname.setText("");
		startBtn.setEnabled(false);
	}
	
	private void receiveGameInfo() {
		System.out.println("[게임 정보 받기 시작]");
		new Thread(() -> {
			while(true) {
				try {
					ByteBuffer byteBuffer = ByteBuffer.allocate(100);
					
					// 서버 비정상적으로 종료됐을 때 IOException 발생
					int readByteCount = socketChannel.read(byteBuffer);
					
					// 서버 정상적으로 Socket close를 호출 했을 경우
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
					try {
						System.out.println("[상대방과 통신 두절 : " + socketChannel.getRemoteAddress() + 
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
}
