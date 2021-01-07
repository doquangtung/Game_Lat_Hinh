package Game_lat_hinh;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.*;
import java.util.Scanner;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login implements ActionListener {
	    private JFrame mainFrame;
	    private JLabel headerLabel;
	    private JLabel statusLabel;
	    private JPanel controlPanel;
	    private JTextField userText;
	    private JPasswordField passwordText;
	    private JButton SigninButton;
	    private JButton SignupButton;
	    public Login(){
	    	mainFrame = new JFrame("Login");
	        mainFrame.setSize(400, 300);
	        mainFrame.setLayout(new GridLayout(3, 1));
	        headerLabel = new JLabel("ĐĂNG NHẬP", JLabel.CENTER);
	        headerLabel.setFont(new Font("Arial", Font.PLAIN, 25));
	        statusLabel = new JLabel("", JLabel.CENTER);
	        statusLabel.setSize(350, 100);
	        controlPanel = new JPanel();
	        controlPanel.setLayout(new FlowLayout());
	        mainFrame.add(headerLabel);	        
	        mainFrame.add(controlPanel);
	        mainFrame.add(statusLabel);
	        mainFrame.setLocationRelativeTo(null);
	        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        mainFrame.setResizable(false);
	        mainFrame.setVisible(true);
	        headerLabel.setText("ĐĂNG NHẬP");
	        JLabel namelabel = new JLabel("TÀI KHOẢN: ", JLabel.RIGHT);
	        JLabel passwordLabel = new JLabel("MẬT KHẨU: ", JLabel.CENTER);
	        userText = new JTextField(6);
	        passwordText = new JPasswordField(6);
	        SigninButton = new JButton("SIGN IN");
	        SigninButton.setActionCommand("Signin");
	        SigninButton.addActionListener(this);
	        SignupButton = new JButton("SIGN UP");
	        SignupButton.setActionCommand("Signup");
	        SignupButton.addActionListener(this);
	        controlPanel.add(namelabel);
	        controlPanel.add(userText);
	        controlPanel.add(passwordLabel);
	        controlPanel.add(passwordText);
	        controlPanel.add(SigninButton);
	        controlPanel.add(SignupButton);
	        mainFrame.setVisible(true);
	    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("Signin".equals(e.getActionCommand())) {
			String use = userText.getText();
			String pass = new String(passwordText.getPassword());
			if ((use.equals("") || use == null)||( pass.equals("") || pass == null)) 
				statusLabel.setText("Tài khoản/ Mật khẩu không được để trống");
			else {
			Connection connection;
			connection = ConnectionSQL.getConnection();          
            try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from login where name = '"+ use + "' and pass = '"+pass+"'");
            if (rs.isBeforeFirst()) {
            	statusLabel.setText("Đăng nhập thành công");
            	mainFrame.setVisible(false);
            	if (!use.equals("admin")) {
            	String A = "";
        		try {
        		//file.createNewFile();
        		Scanner sc = new Scanner(new File("D:\\high.txt"));
        		if (sc.hasNextLine()) {
        		    A = sc.nextLine();
        		}
        	    sc.close();
        		} catch (Exception ep) {
        			System.out.println("File not found");
        		}
        	    System.out.println(Integer.parseInt(A));
        		Task _5p = new Task();
        		Timer timer = new Timer();
        	    timer.schedule(_5p, Integer.parseInt(A) * 60 * 1000 - 5*60*1000);
            	}
            	new Menu(1120, 690, true, use);
            }
            else  statusLabel.setText("Tài khoản/ Mật khẩu sai");
            connection.close();
            } catch (Exception ex) {
	            ex.printStackTrace();
	        }
			}
		}
		if ("Signup".equals(e.getActionCommand())) {
			String use = userText.getText();
			String pass = new String(passwordText.getPassword());
			if ((use.equals("") || use == null)||( pass.equals("") || pass == null)) 
				statusLabel.setText("Tài khoản/ Mật khẩu không được để trống");
			else {
			Connection connection;
			connection = ConnectionSQL.getConnection();          
            try {
            Statement stmt = connection.createStatement();
            // get data from table 'student'
            ResultSet rs = stmt.executeQuery("select * from login where name = '"+ use + "'" );
            if (rs.isBeforeFirst()) statusLabel.setText("Tài khoản đã tồn tại");
            else  {
            	Statement stmt2 = connection.createStatement();
            	stmt2.executeUpdate("INSERT INTO login(name, pass) VALUES ('" + use + "', '" + pass + "')");
            	 statusLabel.setText("Tạo tài khoản thành công");
            }
            connection.close();
            } catch (Exception ex) {
	            ex.printStackTrace();
	        }
			}
		}
		
	}
}
