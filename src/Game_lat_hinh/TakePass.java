package Game_lat_hinh;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class TakePass {
	private JFrame take;
	private JButton find;
	private JTextField name;
	private JLabel dn;
	private JLabel labels;
	private JScrollPane sp;
	public TakePass() {
	take = new JFrame("TAKE PASS");  
	find = new JButton("find"); 
	find.setFont(new Font("Arial", Font.PLAIN, 25));
	JLabel namelabel = new JLabel("NAME: ", JLabel.RIGHT);
	dn = new JLabel("", JLabel.CENTER);
	dn.setLayout(new FlowLayout()); 
	//dn.setBounds(0, 10, 500, 100); 
	namelabel.setFont(new Font("Arial", Font.PLAIN, 25));
	labels = new JLabel("", JLabel.CENTER);
	labels.setFont(new Font("Arial", Font.PLAIN, 25));
	//labels.setBounds(0, 110, 500, 90);
	JTextField name = new JTextField(6);
	name.setFont(new Font("Arial", Font.PLAIN, 25));
	take.setSize(500, 300);  
	take.setBackground(Color.lightGray);  
	take.setLayout(new GridLayout(3, 1)); 
	String datas[][] = new String[100][3];
	String column[] = { "NO", "NAME", "PASS" };	    	           
	Connection connection;
	connection = ConnectionSQL.getConnection();  
    try {
    Statement stmt = connection.createStatement();
    // get data 
    ResultSet rs = stmt.executeQuery("select * from login" );	                
    if (rs.isBeforeFirst()) {
    	int cnt = 0;
    	while (rs.next()) {
    		datas[cnt][0] = String.valueOf(cnt+1);
    		
    		datas[cnt][1] = rs.getString(1);
    		datas[cnt][2] = rs.getString(2);
    		cnt ++;
    	}
        
    } else labels.setText("chua co player nao dang ki tai khoan");
    	connection.close();
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    JTable jt = new JTable(datas, column);
     //jt.setBounds(0, 200, 500, 490); 	 
    sp = new JScrollPane(jt); 	    
	dn.add(namelabel);
	dn.add(name);
	dn.add(find);
	take.add(dn);
	take.add(sp);
	take.add(labels);		
	find.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	String S = name.getText();
        	Connection connection;
    		connection = ConnectionSQL.getConnection();  
            try {
            Statement stmt = connection.createStatement();
            // get data 
            ResultSet rs = stmt.executeQuery("select pass from login where name = '"+ S + "'" );
            if (rs.isBeforeFirst()) {
            	while (rs.next()) labels.setText("pass: " + rs.getString(1));
            } else labels.setText("ko ton tai usename nay");
            	connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            }
    });          
	take.setLocationRelativeTo(null);
	take.setResizable(false);
	take.setVisible(true); 
	}
}
