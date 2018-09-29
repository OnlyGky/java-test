package Student;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
//package Student;

import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Look extends JFrame{
	private JTextField textField;	
	private JButton button;	
	private JScrollPane jscrollpane;	
	private JPanel panel;	
	private JTable jtable;	
	Vector columnNames = null;    
	Vector rowData = null;	
	private String driver;
	private String url;
	private String user;
	private String pass;
	public void view(){		
		textField=new JTextField(20);		
		button=new JButton("关闭");		
		Connection conn=null;		
		PreparedStatement ps=null;		
		ResultSet res=null;		
		columnNames=new Vector<String>();		
		columnNames.add("学号");		
		columnNames.add("姓名");		
		columnNames.add("学院");		
		columnNames.add("入学日期");		
		rowData =new Vector<>();		
		jtable=new JTable(rowData,columnNames);		
		jscrollpane =new JScrollPane(jtable);		
		try{
			Properties prop=new Properties();
			prop.load(new FileInputStream("src\\jdbc.properties"));
			driver=prop.getProperty("driver");
			url=prop.getProperty("url");
			user=prop.getProperty("user");
			pass=prop.getProperty("password");
			
		}
		catch(IOException e1){
			e1.printStackTrace();
		}
		try {
			Class.forName(driver);
			conn=DriverManager.getConnection(url, user, pass);
			ps=conn.prepareStatement("select * from sd");
			res=ps.executeQuery();
		
			while (res.next())
	        {
	            Vector hang = new Vector();
	            hang.add(res.getString(1));
	            hang.add(res.getString(2));
	            hang.add(res.getString(3));
	            hang.add(res.getString(4));
	          
	            rowData.add(hang);
	        }
			
		}catch(SQLException e2) {
			e2.printStackTrace();
		}catch(Exception e3) {
			e3.printStackTrace();
		}finally{
            try{
                res.close();
                ps.close();
                conn.close();
                System.out.println("close ok");
            }catch (SQLException o){
                o.printStackTrace();
                System.out.println("go die 2");
            }}
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		jtable = new JTable(rowData,columnNames);
	    jscrollpane = new JScrollPane(jtable);
	     
	    this.add(jscrollpane);
	    this.setTitle("浏览学生信息");
	    this.setLayout(new GridLayout(2,5));
	   // this.add(button);
	    
	    this.setLocation(300,300);
	    this.setSize(500,300);
	    this.setVisible(true);
	    this.setResizable(false);	
}
}
