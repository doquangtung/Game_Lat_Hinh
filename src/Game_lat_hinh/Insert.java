package Game_lat_hinh;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Insert implements ActionListener {
	private JFrame fr;
	private JLabel Label1;
	private JLabel Label2;
	private FileDialog fd;
	private JTextField nameImage;
	private JButton open;
	private JButton replace;
	private JButton apply;
	private File[] in;
    public Insert() {
	fr = new JFrame("Insert Image");  
	Label1 = new JLabel("", JLabel.CENTER); 
	Label2 = new JLabel(""); 
	Label2.setLayout(new FlowLayout()); 
	Label1.setFont(new Font("Arial", Font.PLAIN, 25));
	open = new JButton("select");  
	open.setFont(new Font("Arial", Font.PLAIN, 25));
	open.setActionCommand("open");
	replace = new JButton("replace");  
	replace.setFont(new Font("Arial", Font.PLAIN, 25));
	replace.setActionCommand("replace");
	JLabel namelabel = new JLabel("NAME: ", JLabel.RIGHT);
	namelabel.setFont(new Font("Arial", Font.PLAIN, 25));
	nameImage = new JTextField(6);
	nameImage.setFont(new Font("Arial", Font.PLAIN, 25));
	//JButton Image1;  
	apply = new JButton("apply"); 
	apply.setFont(new Font("Arial", Font.PLAIN, 25));
	fd = new FileDialog(fr, "Open", FileDialog.LOAD);
	fd.setMultipleMode(true);
	fr.setSize(500, 300);  
	fr.setLocation(200, 200);  
	fr.setBackground(Color.lightGray);  
	fr.setLayout(new GridLayout(2, 1)); 
	//Image1 = new JButton();
	//Image1.setSize(300, 400);		 
	Label2.add(namelabel);
	Label2.add(nameImage);
	Label2.add(open);  
	Label2.add(replace);
	Label2.add(apply);
	fr.add(Label2);
	fr.add(Label1); 
	//fr.add(Image1);
	open.addActionListener(this);  
	apply.addActionListener(this);  
	replace.addActionListener(this);  
	fr.setLocationRelativeTo(null);
	fr.setResizable(false);
	fr.setVisible(true);  
}
    public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	if ("open".equals(e.getActionCommand())) {
		fd.show();  
		  if (fd.getFile() == null)  
		  {  
		   Label1.setText("You have not select");  
		  } else  
		  {  
		    in = new File [30];
			in = fd.getFiles ();	
			Label1.setText("You have selected " + in.length + " images");  
		  }
	}
	if ("apply".equals(e.getActionCommand())) {
		if (in == null) Label1.setText("You have not select");  
		else {
			if (in.length < 30) Label1.setText("Chưa đủ 30 ảnh. Đã có " + in.length + "/30 ảnh");  
			else {
		String S = nameImage.getText();
		if (S.equals("")) Label1.setText("Không được để trống tên");
		else {
		File file = new File("D:\\java\\PROJECT_I\\src\\"+ S);
		  if (!file.exists()) {
		      if (file.mkdir()) {
		          System.out.println("Directory is created!");
		      } else {
		          System.out.println("Failed to create directory!");
		      }
		  }
		  InputStream inStream= null;
		   OutputStream outStream = null;
		  
		   //for(int i = 0; i <= in.length - 1; i ++) System.out.println("i = " + i +" name: " + in[i].toString());
		   
		   try {
			   //File []  getFiles () 
			   for(int i = 0; i <= in.length - 1; i ++) {
			   if (i == 30) {
				   //Label1.setText("Thừa ảnh. Bỏ từ ảnh "+ in[i].getName());  
                   JOptionPane.showMessageDialog(fr, "Thừa ảnh. Bỏ từ ảnh "+ in[i].getName());
			       break;
			   }
		       inStream = new FileInputStream(in[i]);
		       outStream = new FileOutputStream(new File("D:\\java\\PROJECT_I\\src\\"+ S+"\\icon"+(i+1)+".jpg"));

		       int length;
		       byte[] buffer = new byte[2097152];

		       // copy the file content in bytes
		       while ((length = inStream.read(buffer)) > 0) {
		           outStream.write(buffer, 0, length);
		       }
			   }
		       System.out.println("File is copied successful!");
		       
		   } catch (IOException ex) {
		       ex.printStackTrace();
		   }		   
		   fr.setVisible(false);
		}}}
	}
	if ("replace".equals(e.getActionCommand())) {
		if (in == null) Label1.setText("You have not select");  
		else {
			if (in.length > 30) Label1.setText("Thừa ảnh");  
			else {
			fd.setDirectory("D:\\java\\PROJECT_I\\src\\");
			fd.show();  
			File[] in2;
			    in2 = new File [30];
				in2 = fd.getFiles ();
		   File tmp = new File(in2[0].getParent());
		   String tv = tmp.getName();
		   int kq = 0;
		   File folder = new File("D:\\java\\PROJECT_I\\src");
			 //&& s != "HighScore" && s !="icon" && s != "icon" && s != "sound"
			  for (final File fileEntry : folder.listFiles()) {
			        if (fileEntry.isDirectory()) {
			        	String s = fileEntry.getName();
		                if (!s.equals("Game_lat_hinh") && !s.equals("HighScore") && !s.equals("icon") && !s.equals("img") && !s.equals("sound")) {
		                	if (s.equals(tv)) kq = 1;                	
		                }
			        }
			  }
		   if (kq == 0) Label1.setText("File đã chọn không trong thư viện" );
		   else {
           if (in2.length != in.length ) Label1.setText("Số lượng chọn sai . Đã chọn" + in2.length + "/" + in.length + " ảnh");  
           else {
		   InputStream inStream= null;
		   OutputStream outStream = null;
		  
		   //for(int i = 0; i <= in.length - 1; i ++) System.out.println("i = " + i +" name: " + in[i].toString());
		   
		   try {
			   //File []  getFiles () 
			   for(int i = 0; i <= in2.length - 1; i ++) {		
		       inStream = new FileInputStream(in[i]);
		       outStream = new FileOutputStream(new File(in2[i].getAbsolutePath()));
			   in2[i].delete();
		       int length;
		       byte[] buffer = new byte[2097152];

		       // copy the file content in bytes
		       while ((length = inStream.read(buffer)) > 0) {
		           outStream.write(buffer, 0, length);
		       }
			   }
		       System.out.println("File is copied successful!");
		       
		   } catch (IOException ex) {
		       ex.printStackTrace();
		   }		   
		   fr.setVisible(false);
           }}}}
}
}
}
