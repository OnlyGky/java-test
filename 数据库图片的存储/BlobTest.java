package Blob;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class BlobTest {
JFrame jf=new JFrame("图片管理");
private static Connection conn;
private static PreparedStatement insert;
private static PreparedStatement  query;
private static PreparedStatement  queryall;

private DefaultListModel<ImageHolder>imagemodel
	=new DefaultListModel();  
private JList<ImageHolder>imageList=new JList<>(imagemodel);//将已经存在数据库的图片信息存储
private JTextField filePath=new JTextField(26);
private JButton browserBn=new JButton("..");
private JButton uploadBn=new JButton("上传");

private JLabel imageLabel=new JLabel();
JFileChooser chooser=new JFileChooser(".");
ExtensionFileFilter filter=new ExtensionFileFilter();
static {
	try {
		Properties props=new Properties();
		props.load(new FileInputStream("src\\jdbc.properties"));
		String driver=props.getProperty("driver");
		String url=props.getProperty("url");
		String user=props.getProperty("user");
		String pass=props.getProperty("password");
		Class.forName(driver);
		conn=DriverManager.getConnection(url,user,pass);
		insert=conn.prepareStatement("insert into img_table"+ " values(null,?,?)",
				Statement.RETURN_GENERATED_KEYS);
		query=conn.prepareStatement("select img_data from img_table"+" where img_id=?");
		queryall=conn.prepareStatement("select img_id, "+" img_name from img_table");
	}catch(Exception e) {
		e.printStackTrace();
	}
}
public void init()throws SQLException {
	filter.addExtension("jpg");
	filter.addExtension("jpeg");
	filter.addExtension("gif");
	filter.addExtension("png");
	filter.setDescription("图片文件(*.jpg,*.jpeg,*.gif,*.png)");
	chooser.addChoosableFileFilter(filter);
	chooser.setAcceptAllFileFilterUsed(false);
	fillListModel();//提取数据库中的所有已存在图片
	filePath.setEditable(false);//设置JtextField不可修改
	imageList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	JPanel jp=new JPanel();
	jp.add(filePath);
	jp.add(browserBn);
	browserBn.addActionListener(event->{
		int result=chooser.showDialog(jf, "浏览图片文件上传");
		if(result==JFileChooser.APPROVE_OPTION) {
			filePath.setText(chooser.getSelectedFile().getPath());
		}
	});
	jp.add(uploadBn);
	uploadBn.addActionListener(avt->{
		if(filePath.getText().trim().length()>0) {
			upload(filePath.getText());
			filePath.setText("");
		}
	});
	JPanel left=new JPanel();
	left.setLayout(new BorderLayout());
	left.add(new JScrollPane(imageLabel),BorderLayout.CENTER);
	left.add(jp,BorderLayout.SOUTH);
	jf.add(left);
	imageList.setFixedCellWidth(160);
	jf.add(new JScrollPane(imageList),BorderLayout.EAST);
	imageList.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount()>=2) {
				ImageHolder cur=(ImageHolder)imageList.getSelectedValue();
			
			try {
				showimage(cur.getId());
			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		}
	});
	jf.setSize(620, 400);
	jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	jf.setVisible(true);
}
public void fillListModel()throws SQLException{//查找img_table的内容，填充ListMODEL
	try (
		ResultSet rs=queryall.executeQuery())
	{
		imagemodel.clear();
		while(rs.next()) {
			imagemodel.addElement(new ImageHolder(rs.getInt(1),rs.getString(2)));
			
		}
	}
}
public void upload(String fileName) {
	String imageName=fileName.substring(fileName.lastIndexOf('\\')+1,fileName.lastIndexOf('.'));
	File f=new File(fileName);
	try(
			InputStream is=new FileInputStream(f))
	{
		insert.setString(1, imageName);//设置图片名参数
		insert.setBinaryStream(2,is,(int)f.length());//设置二进制流参数
		int affect=insert.executeUpdate();
		if(affect==1) {//更新ListModel，会让JList显示最新图片列表
			fillListModel();
		}
	}catch(Exception e) {
		e.printStackTrace();
	}
}
public void showimage(int id)throws SQLException {
	// TODO Auto-generated method stub
query.setInt(1, id);
try(ResultSet rs=query.executeQuery()){
	if(rs.next()) {
		Blob imgBlob=rs.getBlob(1);//取出Blob列里的数据
		ImageIcon icon=new ImageIcon(imgBlob.getBytes(1L, (int)imgBlob.length()));
		imageLabel.setIcon(icon);//在界面上显示图片
	}
}
}
public static void main(String[] args)throws SQLException {
	try {
		new BlobTest().init();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
