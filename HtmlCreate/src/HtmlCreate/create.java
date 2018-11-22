package HtmlCreate;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class create extends JFrame implements ActionListener{

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		create frame = new create("Create html");
		frame.setVisible(true);
	}
	public JTextField text1;
	public JTextField text2;
	public JTextArea area1;

	 create(String title) {
		setTitle(title);
		setBounds(100, 100, 300, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel top = new JPanel();
		JLabel label1 = new JLabel("Create html");
		top.add(label1);

		JLabel label2 = new JLabel("File name");
		top.add(label2);	//1行目
		label2.setHorizontalAlignment(JLabel.CENTER);
		text1 = new JTextField(10);
		top.add(text1);	//1行目
		JLabel label3 = new JLabel("Title");
		label3.setHorizontalAlignment(JLabel.LEFT);
		top.add(label3);	//2行目
		text2 = new JTextField(10);
		top.add(text2);	//2行目
		JLabel label4 = new JLabel("Contents");
		top.add(label4);	//3行目
		label4.setHorizontalAlignment(JLabel.LEFT);
		area1 = new JTextArea();
		area1.setPreferredSize(new Dimension(180,40));
		area1.setLineWrap(true);
		area1.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		top.add(area1);
		JLabel label5 = new JLabel("Title");
		top.add(label5);	//4行目

		JButton btn = new JButton("Push");
	    btn.addActionListener(this);
	    top.add(btn);
	    Container contentPane = getContentPane();
	    contentPane.add(top, BorderLayout.CENTER);
	  }


	public void actionPerformed(ActionEvent e) {
		fileTest4 filetest = new fileTest4();
		filetest.setfileTest4(text1.getText(),text2.getText(),area1.getText());
	}


}