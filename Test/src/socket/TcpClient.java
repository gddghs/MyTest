package socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TcpClient {
	public static void main(String[] args) {
		try {
			Socket s = new Socket(InetAddress.getByName("127.0.0.1"), 9333);
			InputStream in = s.getInputStream();
			OutputStream out = s.getOutputStream();

			BufferedReader brKey = new BufferedReader(new InputStreamReader(System.in));// 键盘输入
			DataOutputStream dos = new DataOutputStream(out);
			BufferedReader brNet = new BufferedReader(new InputStreamReader(in));

			while (true) {
				String strWord = brKey.readLine();
				byte[] arr = strWord.getBytes();
				int len = arr.length+4;
				dos.writeInt(len);
				//dos.write(arr);
				dos.writeBytes(strWord);
				if (strWord.equalsIgnoreCase("quit"))
					break;
				else
					System.out.println(brNet.readLine());
			}
			dos.close();
			brNet.close();
			brKey.close();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
