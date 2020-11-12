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
			System.out.println("[���� �Ϸ� : " + socketChannel.getRemoteAddress() + "]");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[���� ��� �ȵ�]");
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
		
		receive();	// ������ ������ �ޱ�
	}
	
	private void stopClient() {
		try {
			System.out.println("[���� ����]");
			if(socketChannel != null && socketChannel.isOpen())
				socketChannel.close();	// ���� ����
		} catch (Exception e) {
			e.printStackTrace();
		}
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
					Charset charset = Charset.forName("UTF-8");
					String data = charset.decode(byteBuffer).toString();	// ���ڿ� ��ȯ
					System.out.println("[���� ����]\t" + data);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("[���� ��� �ȵ�]");
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
				socketChannel.write(byteBuffer);	// ������ ������ ������
				System.out.println("[������ �Ϸ�]");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[���� ��� �ȵ�]");
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
