import java.io.*;
import java.net.*;
import java.util.*;

public class SRServer {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			// 서버소켓을 생성해 777번 포트와 결합시킨다.
			serverSocket = new ServerSocket(7777);
			System.out.println("서버가 준비되었습니다.");

			socket = serverSocket.accept();

			Sender sender = new Sender(socket);
			Receiver receiver = new Receiver(socket);

			sender.start();
			receiver.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

class Sender extends Thread {
	Socket socket;
	DataOutputStream out;
	String name;

	Sender(Socket socket) {
		this.socket = socket;
		try {
			out = new DataOutputStream(socket.getOutputStream());
			name = "[" + socket.getInetAddress() + " : " + socket.getPort() + "]";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		Scanner sc = new Scanner(System.in);
		while (out != null) {
			try {
				out.writeUTF(name + sc.nextLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

class Receiver extends Thread {
	Socket socket;
	DataInputStream in;

	Receiver(Socket socket) {
		try {
			in = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (in != null) {
			try {
				System.out.println(in.readUTF());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
