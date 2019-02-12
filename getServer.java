import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {

	public static void main(String[] args) {

		ServerSocket serverSocket = null;

		try {
			//서버소켓을 생성해 777번 포트와 결합시킨다.
			serverSocket = new ServerSocket(7777);
			System.out.println(getTime() + "서버가 준비되었습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		while(true) {
			// 서버소켓
			try {
				System.out.println(getTime() + "연결요청을 기다립니다.");

        //요청대기시간을 5초로 설정한다.
        //5초동안 접속요청이 없으면 SocketTimeoutException이 발생한다.
        serverSocket.setSoTimeout(5 * 1000);
        Socket socket = serverSocket.accept();
				System.out.println(getTime() + socket.getInetAddress() + "로부터 연결요청이 들어왔습니다.");
				System.out.println("getPort() : " + socket.getPort());
				System.out.println("getLocalPort() : " + socket.getLocalPort());

				//소켓의 출력스트림을 얻는다.
				OutputStream out = socket.getOutputStream();
				DataOutputStream dout = new DataOutputStream(out);

				//원격 소켓에 데이터를 보낸다.
				dout.writeUTF("[Notice] Test Message1 from Server");
				System.out.println(getTime() + "데이터를 전송했습니다.");

				//스트림과 소켓 닫아준다.
				dout.close();
				socket.close();

			} catch (SocketTimeoutException e) {
				System.out.println("지정된 시간동안 접속요청이 없어서 서버를 종료합니다.");
        System.exit(0);
			} catch (IOException e){
        e.printStackTrace();
      }

		}
	}

	//현재 시간을 문자열로 변환해주는 메소드
	private static String getTime() {
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date());
	}

}
