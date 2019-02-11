import java.net.*;
import java.io.*;

public class Client {

	public static void main(String[] args) {
		try {
			String serverIp = "127.0.0.1";

			System.out.println("서버에 연결중입니다. 서버IP : " + serverIp);
			// 소켓을 생성하여 연결을 요청한다.
			Socket socket = new Socket(serverIp, 7777);

			// 소켓의 입력스트림을 얻는다.
			InputStream in = socket.getInputStream();
			DataInputStream din = new DataInputStream(in);

			// 소켓으로부터 받은 데이터를 출력한다.
			System.out.println("서버로부터 받은 메시지 : " + din.readUTF());
			System.out.println("연결을 종료합니다.");

			// 스트림과 소켓을 닫는다.
			din.close();
			socket.close();
			System.out.println("연결이 종료되었습니다.");

		} catch (ConnectException e) { // 서버프로그램이 실행되고 있지 않거나 서버의 전원이 꺼져 있어서 서버와의 연결에 실패할 때 발생
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
