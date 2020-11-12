package main.multi;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Server {
	ServerSocketChannel serverSocketChannel;
	SocketChannel socketChannel;
	static final int PORT = 7000;
	
	public Server() {
		// 서버 시작
		try {
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(true);	// 블로킹 방식 실행
			serverSocketChannel.bind(new InetSocketAddress(PORT));
		} catch (Exception e) {
			e.printStackTrace();
			if(serverSocketChannel.isOpen()) stopServer();
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
			receive();	// 연결 후 데이터 받기
		}).start();
	}
	
	private void stopServer() {
		try {
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
					Charset charset = Charset.forName("UTF-8");
					String data = charset.decode(byteBuffer).toString();
					System.out.println("[받은 문자]\t" + data);
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
					break;
				}
			}
		}).start();
	}
	
	private void send(String data) {
		try {
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
		}
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		// 내 아이피 주소
//		172.30.1.25
//		192.168.152.1
//		192.168.74.1
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			server.send(sc.next());
		}
	}
}
