package main.multi;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Scanner;

public class Client {
	SocketChannel socketChannel;
	
	public Client(String ip) {
		try {
			socketChannel = SocketChannel.open();
			socketChannel.connect(new InetSocketAddress(ip, 7000));
			System.out.println("[연결 완료 : " + socketChannel.getRemoteAddress() + "]");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[서버 통신 안됨]");
			if(socketChannel.isOpen()) stopClient();
			return;
		}
		
		String hostAddr = "";
		
		try {

			Enumeration<NetworkInterface> nienum = NetworkInterface.getNetworkInterfaces();
			while (nienum.hasMoreElements()) {

				NetworkInterface ni = nienum.nextElement();

				Enumeration<InetAddress> kk= ni.getInetAddresses();

				while (kk.hasMoreElements()) {

					InetAddress inetAddress = kk.nextElement();

					if (!inetAddress.isLoopbackAddress() && 

					!inetAddress.isLinkLocalAddress() && 

					inetAddress.isSiteLocalAddress()) {

						 hostAddr = inetAddress.getHostAddress().toString();
						 System.out.println(hostAddr);
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} 
		
		receive();	// 서버에 데이터 받기
	}
	
	private void stopClient() {
		try {
			System.out.println("[연결 끊음]");
			if(socketChannel != null && socketChannel.isOpen())
				socketChannel.close();	// 연결 끊기
		} catch (Exception e) {
			e.printStackTrace();
		}
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
					Charset charset = Charset.forName("UTF-8");
					String data = charset.decode(byteBuffer).toString();	// 문자열 변환
					System.out.println("[받은 문자]\t" + data);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("[서버 통신 안됨]");
					stopClient();
					break;
				}
			}
		}).start();
	}
	
	private void send(String data) {
		new Thread(() -> {
			try {
				Charset charset = Charset.forName("UTF-8");
				ByteBuffer byteBuffer = charset.encode(data);
				socketChannel.write(byteBuffer);	// 서버로 데이터 보내기
				System.out.println("[보내기 완료]");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[서버 통신 안됨]");
				stopClient();
			}
		}).start();
	}
	
	public static void main(String[] args) {
		Client client = new Client("172.30.1.25");
		
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			client.send(sc.next());
		}
	}
}
