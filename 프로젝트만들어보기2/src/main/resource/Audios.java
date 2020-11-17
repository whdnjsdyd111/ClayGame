package main.resource;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audios {
	
	public static final String SHOOT = "shoot.wav";	// �ѽ�� �Ҹ� wav
	public static final String PERCUSSION = "percussion.wav";	// �ݹ� �Ҹ� wav
	public static final String RELOAD = "reload.wav";	// ������ �Ҹ� wav
	

	public static void audio(String fileName) {	// ���� �̸��� ��� �Ҹ����� �޼ҵ�
		Thread thread = new Thread() {
			public void run() {
				Clip clip = null;
				try {
					clip = AudioSystem.getClip();	// Ŭ���� �ʱ�ȭ�ϰ� 
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource(fileName));	// ����� ��Ʈ������ ���� �̸����� ���
					clip.open(inputStream);	// �ش� Ŭ���� ����� ��Ʈ���� �־ ���۽ñ��
					clip.start();
				} catch (Exception e) {
					e.printStackTrace();
				}

				while (true) {
					try { Thread.sleep(10); } catch (InterruptedException ie2) {}	// �ż��� üũ�ؼ� Ŭ���� �����ٸ� 
					if (clip != null && !clip.isRunning()) {	// Ŭ�� close ���ֱ�
						clip.close();
						break;
					}
				}
			}
		};
		thread.setPriority(Thread.MAX_PRIORITY);	// �ش� �������� �켱 ���� �ƽ��� ���� (�Ҹ��� �ʰԳ��� �ȵǱ� ������)
		thread.start();	// ������ ����
	}

}
