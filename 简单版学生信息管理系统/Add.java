package Student;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Add extends JFrame{
	 private JLabel id,name,school,date;
	 private JTextField fieldid,fieldname,fieldschool,fielddate;
	 private JButton button1, button2;
	 private JPanel panelid,panelbutton,panelname,panelschool,paneldate;
	 private String driver;
		private String url;
		private String user;
		private String pass;
	 public void view() {
		 id=new JLabel("学号");
		 name=new JLabel("姓名");
		 school=new JLabel("学院");
		 date=new JLabel("入学日期");
		 button1=new JButton("确认");
		 button2=new JButton("重置");
		 fieldid=new JTextField("",20);
		 fielddate=new JTextField("",20);
		 fieldname=new JTextField("",20);
		 fieldschool=new JTextField("",20);
		 panelbutton=new JPanel(new GridLayout(1,1));
		 panelid=new JPanel();
		 panelname=new JPanel();
		 panelschool=new JPanel();
		 paneldate=new JPanel();
		 panelbutton.add(button1);
		 panelbutton.add(button2);
		
		 panelid.add(id);
		 panelid.add(fieldid);
		 panelname.add(name);
		 panelname.add(fieldname);
		 panelschool.add(school);
		 panelschool.add(fieldschool);
		 paneldate.add(date);
		 paneldate.add(fielddate);
		 
		 button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn=null;
				String sql="insert into sd(id,name,schol,date)"+"values(?,?,?,?)";
         
				try {
				Properties props = new Properties();// 加载文件属性
				props.load(new FileInputStream("src\\jdbc.properties"));// 创建一个文件输入流
				driver = props.getProperty("driver");// 获取数据库驱动
				url = props.getProperty("url");// 获取想要操作的数据库及其信息
				user = props.getProperty("user");// 获取用户名
				pass = props.getProperty("password");// 获取密码
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				try {
					Class.forName(driver);
					System.out.println("数据库连接成功");
					conn = DriverManager.getConnection(url, user, pass); 
					PreparedStatement ps=conn.prepareStatement(sql);
					ps.setString(1,fieldid.getText());
                    ps.setString(2,fieldname.getText());
                    ps.setString(3,fieldschool.getText());
                    ps.setString(4,fielddate.getText());
                  
 
                    ps.executeUpdate();
				}catch(Exception e2){
					e2.printStackTrace();
				}finally {
					try{
                        conn.close();
                        System.out.println("MySQL 关闭成功");
                    }catch (SQLException c){
                        System.out.println("MySQL 关闭失败 ");
                        c.printStackTrace();
                    }
				}
			}
		});
		 button2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fieldid.setText("");
				fieldname.setText("");
				fieldschool.setText("");
				fielddate.setText("");
				
			}
		});
		 
		 
		 this.setLayout(new GridLayout(9,1));
		 this.add(panelid);
		 this.add(panelname);
		 
		 this.add(panelschool);
		 this.add(paneldate);
		 this.add(panelbutton);
		 this.setSize(350, 300);
		 this.setLocation(300, 150);
		 this.setVisible(true);

		 setDefaultCloseOperation(EXIT_ON_CLOSE);
	 } 
}
