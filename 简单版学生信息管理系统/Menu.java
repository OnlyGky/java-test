package Student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Menu extends JFrame{
	private JScrollPane scrollpane;
	 private JButton button1, button2, button3,button4,button5;
	 private JPanel panel;
public static void main(String[] args) {
	new Menu().initView();
}
private void initView() {
	 
	 this.setTitle("学生信息管理系统");
	 
	 /*id=new JLabel("学号");
	 name=new JLabel("姓名");
	 school=new JLabel("学院");
	 date=new JLabel("入学日期");*/
	 button1=new JButton("添加");
	 button2=new JButton("修改");
	 button3=new JButton("查询");
	 button4=new JButton("删除");
	 button5=new JButton("浏览");
	 panel=new JPanel();
	 panel.add(button1);
	 panel.add(button2);
	 panel.add(button3);
	 panel.add(button4);
	 panel.add(button5);
	 this.add(panel);
	 button1.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			new Add().view();
			
		}
	});
	 button2.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new Change().view();
		}
	});
	 button3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Ask();
			}
		});
	 button4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Delete();
			}
		});
	 button5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Look().view();
				
			}
		});
	 this.setSize(450, 450);
	 this.setLocation(150, 150);
	 this.setVisible(true);

	 setDefaultCloseOperation(EXIT_ON_CLOSE);
	 
}

}
