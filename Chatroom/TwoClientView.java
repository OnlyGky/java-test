package ClientView;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TwoClientView extends JFrame implements ActionListener,KeyListener,Runnable{
	private JTextArea textArea;
	private JTextField textfield,tfname;
	private JButton btnSend,btnId;
	private JLabel label;
	private JPanel jp1, jp2;
	public boolean isConnect=false;
	private Socket socket=null;
	private DataInputStream inputStream=null;
	private DataOutputStream outputStream=null;
	private JScrollPane scrollpane;
	private static TwoClientView view;
	 public JTextArea getTextArea() {
	        return textArea;
	    }

	    public DataInputStream getInputStream() {
	        return inputStream;
	    }
	    public DataOutputStream getOutputStream() {
	        return outputStream;
	    }
	    public static void main(String[] args) {
			view=new TwoClientView();
			TwoServiceView.clientviews.add(view);//添加到客户端集合中
			Thread thread=new Thread(view);
			thread.start();
		}
	    public TwoClientView(){
	    initView();
        try {
            socket = new Socket("localhost", 9090);//连接本地服务器

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	    private void initView() {
	    	textArea=new JTextArea(20,20);
	    	 textArea.setEditable(false);//设置为不可操作
	    	textfield=new JTextField(15);
	    	scrollpane=new JScrollPane(textArea);//可以自动产生滚动轴
	    	textfield.addKeyListener(this);
	    	btnSend=new JButton("发送");
	    btnSend.addActionListener(this);
	    	label=new JLabel("昵称");
	    	tfname=new JTextField(8);
	    	jp1=new  JPanel();
	    	jp2=new JPanel();
	    	jp1.add(label);
	    	jp1.add(tfname);
	    	tfname.setText("用户0");
	    	jp1.setLayout(new FlowLayout(FlowLayout.CENTER));
	    	jp2.add(textfield);
	    	jp2.add(btnSend);
	    	jp2.setLayout(new FlowLayout(FlowLayout.CENTER));
	    	
	    	this.add(jp1,BorderLayout.NORTH);
	    	this.add(scrollpane, BorderLayout.CENTER);
	    	this.add(jp2, BorderLayout.SOUTH);
	    	this.setTitle("聊天室");
	    	setSize(500, 500);
	    	setLocation(450, 150);
	    	setVisible(true);
	    	setDefaultCloseOperation(EXIT_ON_CLOSE);
	    	this.addWindowListener(new WindowAdapter() {
	    		public void windowClosing(WindowEvent e) {
	    		try {
	    			if(socket!=null) socket.close();//如果socket本身就是null关不关也就无所谓了
	    			if(inputStream!=null) inputStream.close();
	    			if(outputStream!=null) outputStream.close();
	    		}catch(IOException e1){
	    			e1.printStackTrace();
	    		}
	    		}
			});
	    }
	    public void actionPerformed(ActionEvent e) {
	    	if(e.getSource()==btnSend) {
	    		SendMsg();
	    	}
	    }
	    

	    
	    private void SendMsg() {
	    	try {
	    		String str=textfield.getText();
	    		if(!str.equals("")) {
	    			textfield.setText("");
	    			textArea.append(tfname.getText()+":\n"+str+"\n");//将内容发到自身的area上
	    			outputStream=new DataOutputStream(socket.getOutputStream());
	    			outputStream.writeUTF(tfname.getText()+"#"+str);//将内容读入文件输出流
	    			outputStream.flush();//清空缓存数据
	    		}
	    	}
	    	catch(IOException e) {
	    		e.printStackTrace();
	    	}
	    }
	    
	    @Override
	    public void keyPressed(KeyEvent arg0) {//也可使用回车键发送
	        if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
	            SendMsg();
	        }
	    }

	    @Override
	    public void keyReleased(KeyEvent arg0) {

	    }

	    @Override
	    public void keyTyped(KeyEvent arg0) {

	    }

	    @Override
	    
	   
	    public void run() {
	    	try {
	    		inputStream=new DataInputStream(socket.getInputStream());
	    		while(true) {
	    			String []s=inputStream.readUTF().split("#");
textArea.append(s[0]+":\n"+s[1]+"\n");//用于向其他用户发送信息
	    		}
	    	}
	    	catch(IOException e) {
	    		e.printStackTrace();
	    	}
	    }
}
