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
		// ���� ����
		try {
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(true);	// ���ŷ ��� ����
			serverSocketChannel.bind(new InetSocketAddress(PORT));
		} catch (Exception e) {
			e.printStackTrace();
			if(serverSocketChannel.isOpen()) stopServer();
		}
		
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
			receive();	// ���� �� ������ �ޱ�
		}).start();
	}
	
	private void stopServer() {
		try {
			socketChannel.close();	// ���� ä�� �ݱ�
			
			if(serverSocketChannel != null && serverSocketChannel.isOpen())
				serverSocketChannel.close();	// ����ä�� �ݱ�
			
			System.out.println("[���� ����]");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void receive() {
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
					Charset charset = Charset.forName("UTF-8");
					String data = charset.decode(byteBuffer).toString();
					System.out.println("[���� ����]\t" + data);
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
			System.out.println("[������ �Ϸ�]");
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
		}
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		// �� ������ �ּ�
//		172.30.1.25
//		192.168.152.1
//		192.168.74.1
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			server.send(sc.next());
		}
	}
}
