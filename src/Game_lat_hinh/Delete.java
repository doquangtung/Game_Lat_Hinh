package Game_lat_hinh;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class Delete {
	private JFrame del;
	private JLabel labeln;
	private JLabel labels;
	private JScrollPane dsAnh;
	private JButton dButton;
	public Delete() {
	del = new JFrame("Delete");  
	del.setSize(500, 300);  
	del.setLocation(200, 200);  
	del.setBackground(Color.lightGray);  
	del.setLayout(new GridLayout(2, 1)); 
	labeln = new JLabel("", JLabel.CENTER);
	labeln.setLayout(new FlowLayout());
    labels = new JLabel("", JLabel.CENTER);
	labels.setFont(new Font("Arial", Font.PLAIN, 25));
	DefaultComboBoxModel Imode = new DefaultComboBoxModel();
	File folder = new File("D:\\java\\PROJECT_I\\src");
	File[] listOfFiles = folder.listFiles();
	 //&& s != "HighScore" && s !="icon" && s != "icon" && s != "sound"
	  for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	String s = fileEntry.getName();
                if (!s.equals("Game_lat_hinh") && !s.equals("HighScore") && !s.equals("icon") && !s.equals("img") && !s.equals("sound")) {
                	Imode.addElement(s);                	
                }
	        }
	  }
	JComboBox fruitCombo = new JComboBox(Imode);
    fruitCombo.setSelectedIndex(0);
    dsAnh = new JScrollPane(fruitCombo);
    dsAnh.setFont(new Font("Arial", Font.PLAIN, 25));
    fruitCombo.setFont(new Font("Arial", Font.PLAIN, 25));
        dButton = new JButton("Delete");
        dButton.setFont(new Font("Arial", Font.PLAIN, 25));
        dButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fruitCombo.getSelectedIndex() != - 1) {
                String message = "Delete file " + fruitCombo.getItemAt(
                        fruitCombo.getSelectedIndex());
                int select = JOptionPane.showOptionDialog(null, message, "delete",
        				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
        				null, null);
        		if (select == 0) {
        			File dele = new File("D:\\java\\PROJECT_I\\src\\" + fruitCombo.getItemAt(
                            fruitCombo.getSelectedIndex()));
        			System.out.println(dele.getAbsolutePath());
        				if (!dele.exists()) {
        					labels.setText("Thư mục không tồn tại.");
        				} else {
        						String files[] = dele.list();
        						for (String temp : files) {
        							File fileDelete = new File(dele, temp);
                                    fileDelete.delete();    
        						}
        						
        						if (dele.list().length == 0) {
        							dele.delete();
        							labels.setText("delete successfully!");
        						} 
        					}
        			dele.delete();
        		} 
        		}
            }
        });
    
    labeln.add(dsAnh);
    labeln.add(dButton);
    del.add(labeln);
    del.add(labels);
	del.setLocationRelativeTo(null);
	del.setResizable(false);
	del.setVisible(true);  
} 
}


