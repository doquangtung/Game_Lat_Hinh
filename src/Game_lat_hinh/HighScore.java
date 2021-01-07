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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class HighScore extends JFrame {
	private String tk;
	private boolean IsEasy;
	private JScrollPane sp ;
	
public HighScore(String S, boolean es) {
	this.setTitle("High Score");
	tk = S;
	IsEasy = es;
	boolean xh = true;
	
	this.setSize(500, 200);  
	this.setBackground(Color.lightGray);  
	//this.setLayout(new GridLayout(2, 1)); 
	String datas[][] = new String[7][3];
	String column[] = { "RANK", "NAME", "SCORE" };	
	datas[5][0] = "Your Rank";
	datas[5][1] = "Your Name";
	datas[5][2] = "Your Score";
	Connection connection;
	connection = ConnectionSQL.getConnection();  
	String mode;
	if (IsEasy)
		mode = "Easy";
	else
		mode = "Hard";
    try {
    Statement stmt = connection.createStatement();
    // get data 
    ResultSet rs = stmt.executeQuery("SELECT RANK() OVER(ORDER BY score desc) AS Rank, name, score FROM HighScore" + mode);
    if (rs.isBeforeFirst()) {
     	int cnt = 0;
     	while (rs.next()) {
     		if (cnt == 5) break;
     		datas[cnt][0] = String.valueOf(rs.getInt(1));  		
     		datas[cnt][1] = rs.getString(2);
     		datas[cnt][2] = String.valueOf(rs.getInt(3));  	
     		cnt ++;
     	}
     	if (cnt <= 4) for (int j = cnt; j <= 4; j++) {
     		datas[j][0] = String.valueOf(j + 1);
        	datas[j][1] = "No Info";
        	datas[j][2] = "No Info";
     	}
    int kq = 0;
    for (int j = 0; j <= 4; j++) {
    	System.out.println(datas[j][1]);
    	if (datas[j][1] == null) break;
    	if (datas[j][1].equals(tk)) {
    		datas[6][0] = datas[j][0];
    		datas[6][1] = datas[j][1];
    		datas[6][2] = datas[j][2];
    		kq = 1;
    		break;
    	}
    }
    if (kq == 0) {
    	datas[6][0] = "No Info";
    	datas[6][1] = "No Info";
    	datas[6][2] = "No Info";
    }
    }
    else  {
    	xh = false;
    }
    connection.close();
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    JTable jt = new JTable(datas, column);
    jt.setFont(new Font("Arial", Font.PLAIN, 18));
    jt.setBounds(0, 60, 500, 230);
    sp = new JScrollPane(jt); 	  
    if (xh) {
	this.add(sp);     
	this.setLocationRelativeTo(null);
	this.setResizable(false);
	this.setVisible(true); 
	} else {
		this.setVisible(false); 
		JOptionPane.showMessageDialog(null, "There is nothing in HighScore!");
	}
	}
}
