import java.util.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;

public class ThreadServer implements Runnable {

	ServerSocket serverSocket;
	Thread[] threadArr;

	public static void main(String[] args) {
		// 5개의 쓰레드를 생성하는 서버를 생성
		ThreadServer server = new ThreadServer(5);
		server.start();
	}

	public ThreadServer(int i) {
		try {
			// 서버소켓을 생성해 777번 포트와 결합시킨다.
			serverSocket = new ServerSocket(7777);
			System.out.println(getTime() + "서버가 준비되었습니다.");

			threadArr = new Thread[i];

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void start() {
		for (int i = 0; i < threadArr.length; i++) {
			threadArr[i] = new Thread(this);
			threadArr[i].start();
		}

	}

	@Override
	public void run() {
		while (true) {
			// 서버소켓
			try {
				System.out.println(getTime() + "연결요청을 기다립니다.");

				Socket socket = serverSocket.accept();
				System.out.println(getTime() + socket.getInetAddress() + "로부터 연결요청이 들어왔습니다.");
				System.out.println("getPort() : " + socket.getPort());
				System.out.println("getLocalPort() : " + socket.getLocalPort());

				// 소켓의 출력스트림을 얻는다.
				OutputStream out = socket.getOutputStream();
				DataOutputStream dout = new DataOutputStream(out);

				// 원격 소켓에 데이터를 보낸다.
				dout.writeUTF("[Notice] Test Message1 from Server");
				System.out.println(getTime() + "데이터를 전송했습니다.");

				// 스트림과 소켓 닫아준다.
				dout.close();
				socket.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	// 현재 시간을 문자열로 변환해주는 메소드
	private static String getTime() {
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date());
	}

}
