package Student;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Ask extends JFrame{
	JLabel jlnumber = new JLabel("学号：");
    JLabel jlname = new JLabel("姓名：");
    JLabel jlbirthday = new JLabel("入学日期：");
    JLabel jldepartment = new JLabel("学院：");
     
    JTextField jtnumber = new JTextField("",20);
    JTextField jname = new JTextField("",20);
    JTextField jbirthday =new JTextField("",20);
    JTextField jdepartment =new JTextField("",20);
     
    JButton buttonask = new JButton("查询");
    JButton buttonreturn = new JButton("返回");
     
     
    public Ask() {
        JPanel jpnumber = new JPanel();
        JPanel jpname = new JPanel();
       
        JPanel jpbirthday = new JPanel();
        JPanel jpdepartment = new JPanel();
        JPanel jpforbutton = new JPanel(new GridLayout(1,1));
         
        jpnumber.add(jlnumber);
        jpnumber.add(jtnumber);
         
        jpname.add(jlname);
        jpname.add(jname);
        
        jpdepartment.add(jldepartment);
        jpdepartment.add(jdepartment);
         
        jpbirthday.add(jlbirthday);
        jpbirthday.add(jbirthday);
         
      
        jpforbutton.add(buttonask);
        jpforbutton.add(buttonreturn);
         
        buttonask.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Connection conn = null;
                ResultSet res = null;
                Statement stat = null;
                 
                String sql = "SELECT id,name,schol,date from sd;";
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                     
                }catch(Exception d){
                    System.out.println("jdbc fall");
                    d.printStackTrace();
                }
                try{
                    conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test?characterEncoding=utf8&serverTimezone=GMT","root","980204");
                    stat=conn.createStatement();
                    res=stat.executeQuery(sql);
                    while (res.next())
                    {
                        if (res.getString(1).equals(jtnumber.getText()))
                        {
                            jname.setText(res.getString(2));
                           
                            jdepartment.setText(res.getString(3));
                            jbirthday.setText(res.getString(4));
 
                            break;
                        }
                    }
                }catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                 
                 
            }
                finally{
                    try{
                        conn.close();
                    }catch(SQLException ar){
                        ar.printStackTrace();
                    }
             
                }}}
             
                );
         
        buttonreturn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                     new Menu();
            }          
        });
         
         
        this.setTitle("查询学生信息");
        this.setLayout(new GridLayout(9,1));
        this.add(jpnumber);
        this.add(jpname);
     
        this.add(jpbirthday);
        this.add(jpdepartment);
        this.add(jpforbutton);
        this.setLocation(400,300);
        this.setSize(350,300);
        this.setVisible(true);
    }
}
