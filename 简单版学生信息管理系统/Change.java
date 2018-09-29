package Student;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.mysql.cj.protocol.Resultset;

public class Change extends JFrame{
	private JLabel id,name,school,date;
	 private JTextField fieldid,fieldname,fieldschool,fielddate;
	 private JButton button1, button2;
	 private JPanel panelid,panelbutton,panelname,panelschool,paneldate;
	 private String driver;
		private String url;
		private String user;
		private String pass;
	 void view() {
		id=new JLabel("学号");
		 name=new JLabel("姓名");
		 school=new JLabel("学院");
		 date=new JLabel("入学日期");
		 button1=new JButton("修改");
		 button2=new JButton("返回");
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
				
				String sid =fieldid.getText();
				String sname=fieldname.getText();
				String sschool=fieldschool.getText();
				String sdate=fielddate.getText();
				
				Connection conn=null;
				ResultSet res=null;
				Statement stat=null;
				String sql="select id,name,schol,date from sd";
				try {
				Properties props=new Properties();
				props.load(new FileInputStream("src\\jdbc.properties"));
				driver=props.getProperty("driver");
				url=props.getProperty("url");
				user=props.getProperty("user");
				pass=props.getProperty("password");
				Class.forName(driver);
				conn=DriverManager.getConnection(url, user, pass);
				stat=conn.createStatement();
				res=stat.executeQuery(sql);
				while(res.next()) {
					if(res.getString(1).equals(fieldid.getText())) {
						try {
							Class.forName(driver);
						}
						catch(Exception d) {
							System.out.println("jdbc fall");
							d.printStackTrace();
						}
						String sql1="update sd set name='"+sname
								+"' where id='"+fieldid.getText()+"'";
						String sql2="update sd set schol='"+sschool
								+"' where id='"+fieldid.getText()+"'";
						String sql3="update sd set date='"+sdate
								+"' where id='"+fieldid.getText()+"'";
						try {
							conn=DriverManager.getConnection(url, user, pass);
							stat=conn.createStatement();
							stat.executeUpdate(sql1);
							stat.executeUpdate(sql2);
							stat.executeUpdate(sql3);
						}
						catch(SQLException g) {
							System.out.println("数据库更新失败");
							g.printStackTrace();
						}finally {
							stat.close();
							conn.close();
						}
					}
				}
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				finally {
					try {
						conn.close();
					}catch(SQLException ar) {
						ar.printStackTrace();
					}
				}
			}
		});
		 button2.addActionListener(new ActionListener(){
	            public void actionPerformed(ActionEvent e){
	                      Menu menu=new Menu();    
	            }          
	        });
		 this.setTitle("修改学生信息");
		 this.setLayout(new GridLayout(9,1));
		 this.add(panelid);
		 this.add(panelname);
		 this.add(panelschool);
		 this.add(paneldate);
		 this.add(panelbutton);
		 this.setLocation(400,300);
	        this.setSize(350,300);
	        this.setVisible(true);
	        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}

