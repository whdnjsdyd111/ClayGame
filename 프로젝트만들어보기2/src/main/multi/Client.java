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
import main.databases.MemberDAO;
import main.multi.my_scene.MultiMyGame;
import main.multi.my_scene.MyInfinity;
import main.multi.my_scene.MyReload;
import main.multi.my_scene.MyTime;
import main.multi.oppo_scene.MultiOppoGame;
import main.multi.oppo_scene.OppoInfinity;
import main.multi.oppo_scene.OppoReload;
import main.multi.oppo_scene.OppoTime;

public class Client {
	SocketChannel socketChannel;
	private String nickname;
	private JLabel master_nickname;
	private JTextArea textArea;
	private MainFrame frame;
	private JLayeredPane joinedRoom;
	private JComboBox<String> comboBox;
	
	private MultiOppoGame oppo_scene = null;
	private MultiMyGame my_scene = null;
	
	public Client(String ip, String nickname, JLabel master_nickname, JTextArea textarea, MainFrame frame, 
			JLayeredPane joinedRoom, JComboBox<String> comboBox) {
		this.nickname = nickname;
		this.master_nickname = master_nickname;
		this.textArea = textarea;
		this.frame = frame;
		this.joinedRoom = joinedRoom;
		this.comboBox = comboBox;
		
		try {
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(true);	// 블로킹 방식 진행
			socketChannel.connect(new InetSocketAddress(ip, Server.PORT));
			// System.out.println("[연결 완료 : " + socketChannel.getRemoteAddress() + "]");
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("[서버 통신 안됨]");
			if(socketChannel.isOpen()) stopClient();
			return;			
		}
		
		sendNickname();	// 연결 성공 시 닉네임 보내기
		getNickname();	// 닉네임 보낸 후 서버측의 닉네임 받기
		
		receive();	// 서버에 데이터 받기
	}
	
	public void stopClient() {	// 클라이언트 멈추는 메소드
		try {
			// System.out.println("[연결 끊음]");
			if(socketChannel != null && socketChannel.isOpen())
				socketChannel.close();	// 연결 끊기
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
					
					// 서버 비정상적으로 종료됐을 때 IOException 발생
					int readByteCount = socketChannel.read(byteBuffer);
					
					// 서버 정상적으로 Socket close를 호출 했을 경우
					if(readByteCount == -1)
						throw new IOException();
					
					byteBuffer.flip();
					
					// 게임 종류 받았을 때 해당 모드로 콤보박스 바꿔주기
					if(byteBuffer.get(0) == 0) {
						comboBox.setSelectedIndex(0);
						continue;
					} else if(byteBuffer.get(0) == 1) {
						comboBox.setSelectedIndex(1);
						continue;
					} else if(byteBuffer.get(0) == 2) {
						comboBox.setSelectedIndex(2);
						continue;
					} else if(byteBuffer.get(0) == -1) {
						// System.out.println("[게임 시작]");
						// 게임 시작한 다는 것을 받아서 서버 측에 서버 게임 시작알림을 받았다고 알려주기
						sendGameStart();
						
						// 상대방 화면 띄우기
						if(comboBox.getSelectedIndex() == 0) {
							oppo_scene = new OppoTime(frame);
						} else if(comboBox.getSelectedIndex() == 1) {
							oppo_scene = new OppoInfinity(frame);
						} else if(comboBox.getSelectedIndex() == 2) {
							oppo_scene = new OppoReload(frame);
						}
						
						// 게임 정보 얻는 쓰레드를 실행하는 메소드 실핸
						receiveGameInfo();
						
						if(comboBox.getSelectedIndex() == 0) {
							my_scene = new MyTime(frame, socketChannel, oppo_scene);
						} else if(comboBox.getSelectedIndex() == 1) {
							my_scene = new MyInfinity(frame, socketChannel, oppo_scene);
						} else if(comboBox.getSelectedIndex() == 2) {
							my_scene = new MyReload(frame, socketChannel, oppo_scene);
						}
						
						// 게임 정보 쓰레드를 실행 후 내 화면을 띄우고 receive의 쓰레드 멈추기
						break;
					}
					
					Charset charset = Charset.forName("UTF-8");
					String data = charset.decode(byteBuffer).toString();	// 문자열 변환
					// System.out.println("[데이터 받음]");
					textArea.append("\n" + data);
					textArea.setCaretPosition(textArea.getDocument().getLength());
				} catch (Exception e) {
					e.printStackTrace();
					// System.out.println("[서버 통신 안됨]");
					stopClient();
					new AlertDialog(frame, AlertDialog.MSG_NET);
					break;
				}
			}
		}).start();
	}
	
	public void send(String data) {	// 채팅 보내기
		try {
			Charset charset = Charset.forName("UTF-8");
			ByteBuffer byteBuffer = charset.encode(data);
			socketChannel.write(byteBuffer);	// 서버로 데이터 보내기
			// System.out.println("[보내기 완료]");
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("[서버 통신 안됨]");
			stopClient();
			new AlertDialog(frame, AlertDialog.MSG_NET);
		}
	}
	
	public void sendGameStart() {	// 게임 시작을 하겠다는 것을 알려주는 메소드
		try {
			ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[] { (byte) -1 });
			socketChannel.write(byteBuffer);
			
			// System.out.println("[클라이언트 게임 시작 보내기 완료]");
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("[서버 통신 안됨]");
			stopClient();
			new AlertDialog(frame, AlertDialog.MSG_NET);
		}
	}
	
	private void getNickname() {	// 닉네임을 받는 메소드
		while(true) {
			try {
				ByteBuffer byteBuffer = ByteBuffer.allocate(100);
				
				// 서버 비정상적으로 종료됐을 때 IOException 발생
				int readByteCount = socketChannel.read(byteBuffer);			
				
				// 서버 정상적으로 Socket close를 호출 했을 경우
				if(readByteCount == -1)
					throw new IOException();
				
				byteBuffer.flip();
				Charset charset = Charset.forName("UTF-8");
				String data = charset.decode(byteBuffer).toString();	// 문자열 변환
				// System.out.println("[닉네임 받음]");
				master_nickname.setText(data);
				break;
			} catch (Exception e) {
				e.printStackTrace();
				// System.out.println("[서버 통신 안됨]");
				stopClient();
				new AlertDialog(frame, AlertDialog.MSG_NET);
				break;
			}
		}
	}
	
	private void sendNickname() {	// 닉네임을 보내는 메소드
		try {
			Charset charset = Charset.forName("UTF-8");
			ByteBuffer byteBuffer = charset.encode(nickname);
			socketChannel.write(byteBuffer);	// 서버로 데이터 보내기
			// System.out.println("[닉네임 보내기 완료]");
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("[서버 통신 안됨]");
			stopClient();
			new AlertDialog(frame, AlertDialog.MSG_NET);
		}
	}
	
	private void receiveGameInfo() {	// 게임 정보를 받는 메소드
		// System.out.println("[게임 정보 받기 시작]");
		
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
					// System.out.println("클라이언트 데이터 받음");

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
					// System.out.println("[서버 통신 안됨]");
					stopClient();
					new AlertDialog(frame, AlertDialog.MSG_NET);
					break;
				}
			}
			
			if(comboBox.getSelectedIndex() == 0 || comboBox.getSelectedIndex() == 2) {
				if(my_scene.game_score.getText().compareTo(oppo_scene.game_score.getText()) > 0) {
					textArea.append("\n나의 승리");
					textArea.append("\n" + MemberDAO.getInstance().updatePVP("win"));
				} else if(my_scene.game_score.getText().compareTo(oppo_scene.game_score.getText()) < 0) {
					textArea.append("\n나의 패배");
					textArea.append("\n" + MemberDAO.getInstance().updatePVP("lose"));
				} else {
					textArea.append("\n무승부");
					textArea.append("\n" + MemberDAO.getInstance().updatePVP("draw"));
				}
			} else {
				if(my_scene.score > 0) {
					textArea.append("\n나의 승리");
					textArea.append("\n" + MemberDAO.getInstance().updatePVP("win"));
				} else if(my_scene.score == 0) {
					textArea.append("\n나의 패배");
					textArea.append("\n" + MemberDAO.getInstance().updatePVP("lose"));
				}
			}

			receive();
		}).start();
	}
}
