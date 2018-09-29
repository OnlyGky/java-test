package ClientView;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;



public class TwoServiceView extends JFrame implements ActionListener{
	private JButton btnOpen,btnStop;
	private JLabel label;
	private TwoService service=null;
	public static ArrayList<TwoClientView> clientviews=new ArrayList<>();
	private static TwoServiceView view;
	public static TwoServiceView getView() {
		return view;
	}
	public static void main(String[] args) {
		view=new TwoServiceView();
	}
	public TwoServiceView() {
		initView();
	}
	private void initView() {
		btnOpen=new JButton("打开服务器");
		btnStop=new JButton("关闭服务器");
		btnStop.setEnabled(false);
		btnOpen.addActionListener(this);
		btnStop.addActionListener(this);
		label=new JLabel("服务器停止工作");
		add(label);
		add(btnOpen);
		add(btnStop);
		setTitle("服务器");
		setLayout(new GridLayout(3, 1,0,10));
		setSize(300, 300);
		setLocation(450, 450);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnOpen) {
			open();
		}else {
			stop();
		}
	}
	public void open() {
		service=new TwoService();
		Thread thread=new Thread(service);
		thread.start();
		label.setText("服务器正在运行");
		btnOpen.setEnabled(false);
		btnStop.setEnabled(true);
	}
	public void stop() {
		label.setText("服务器已关闭");
		btnOpen.setEnabled(true);
		btnStop.setEnabled(false);
		try {
			synchronized (TwoClientMannager.sockets) {//使用sockets作为同步监视器，任何线程进入同步代码块必须先
				for(TwoCharSocket socket:TwoClientMannager.sockets) {//获得对sockets的锁定
					socket.getInputStream().close();
					socket.getOutputStream().close();
				}
				TwoClientMannager.sockets.removeAllElements();//删除集合中的所有客户端信息
			}
			for(TwoClientView view: clientviews) {
				view.getInputStream().close();
				view.getOutputStream().close();
			}
			service.getserviceSocket().close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
