package ClientView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TwoCharSocket implements Runnable{//开启服务端连接
private Socket socket=null;
private DataInputStream inputStream = null;
private DataOutputStream outputStream = null;
public DataInputStream getInputStream() {
	return inputStream;
}
public DataOutputStream getOutputStream() {
    return outputStream;
}
public TwoCharSocket(Socket socket) {
	this.socket=socket;
	 try {
         inputStream = new DataInputStream(socket.getInputStream());
         outputStream = new DataOutputStream(socket.getOutputStream());
     } catch (IOException e) {
         e.printStackTrace();
     }
}
public void send(String str) {//用于将所有的信息输入服务端的输出流
	try {
		outputStream.writeUTF(str);
		outputStream.flush();
	}catch(IOException e) {
		e.printStackTrace();
	}
	
}
public void run() {
	String accept=null;
	while(true) {
		try {
			accept=inputStream.readUTF();
			TwoClientMannager.sendAll(this, accept);
		}catch(IOException e) {
			TwoClientMannager.sockets.remove(this);
		}
	}
}
}
