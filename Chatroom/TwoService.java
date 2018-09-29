package ClientView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;


public class TwoService implements Runnable{//本类用来开启服务端连接
	private ServerSocket serversocket=null;
	public ServerSocket getserviceSocket() {
		return serversocket;
	}
public void run() {
	try {
		serversocket=new ServerSocket(9090);//建立与客户端的连接
		while(true) {
			Socket socket=serversocket.accept();
			JOptionPane.showMessageDialog(TwoServiceView.getView(), "客户端连接端口", "TIP", JOptionPane.INFORMATION_MESSAGE);
			TwoCharSocket chatsocket=new TwoCharSocket(socket);
			TwoClientMannager.sockets.add(chatsocket);//每建立起一个连接就将它添加到连接集合中去
			Thread thread=new Thread(chatsocket);//启动线程不断接收客户端信息
			thread.start();//开启服务端连接通道
		}
	}catch(IOException e) {
		e.printStackTrace();
		System.out.println("服务器关闭");
	}
}
}
