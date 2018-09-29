package ClientView;

import java.util.Vector;

public class TwoClientMannager {//用于向所有客户端发送信息的操作
private TwoClientMannager() {
}
public static Vector<TwoCharSocket> sockets=new Vector<>();
public static void sendAll(TwoCharSocket charSocket,String send) {
	for(TwoCharSocket s:sockets) {
		if(!charSocket.equals(s)) {//判断是不是自身
			s.send(send);
		}
	}
}
}
