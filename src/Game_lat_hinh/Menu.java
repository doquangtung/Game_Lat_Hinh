package Game_lat_hinh;

import java.awt.Button;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;



public class Menu implements ActionListener {
	private JFrame frame;

	private JLabel welcome; // nền khi bắt đầu game
	private ImageIcon introIcon; // nền khi vào game
	private JButton play; // kèm nền khi vào game
	private JButton user;
	private String tk;
	
	private JLabel menu0; // menu cua admin
	private JLabel menu; // phần menu tiếp theo (gồm các thành phần bên dưới)
	private JButton exit;
	private JButton sound;
	
	private JButton demo; // menu cua admin
	private JButton insertImage;
	private JButton takepass;
	private JButton del;
	private JButton setTime;
	
	private DefaultComboBoxModel Imagemode;
	private JComboBox fruitCombo;
	private JButton start;
	private JButton nump;
	private JButton highScore;
	private JButton gamemode;
	private JLabel showScore; // show điểm cao
	private JLabel[] score;
	
	private JFrame fr;
	private JLabel Label1;
	private JLabel Label2;
	private FileDialog fd;
	private JTextField nameImage;
	private JButton open;
	private JButton replace;
	private JButton apply;
	private File[] in;
	
	private Clip clip;
	private static boolean isPlaySound;

	// khởi tạo Menu ban đầu
	public Menu(int w, int h, boolean playSound, String name) {
		frame = new JFrame("Game lật hình");
		isPlaySound = playSound;
		frame.setIconImage(loadImage("/img/logo.png", 90, 90));
		frame.setSize(w, h);
		welcome = new JLabel();
		welcome.setSize(w, h);
		play = new JButton();
		play.setSize(30, 20);
		play.setActionCommand("Play");
		play.addActionListener(this);
        
		user = new JButton(name + " - SIGN OUT");
		user.setFont(new Font("Arial", Font.PLAIN, 25));
		user.setBounds(850, 20, 230, 80);
		user.setActionCommand("out");
		user.addActionListener(this);
		user.setBackground(Color.decode("#D2EDFE"));
		user.setBorder(new LineBorder(Color.decode("#D2EDFE")));
		tk = name;
		ImageIcon playIcon = new ImageIcon(loadImage("/img/play.png", 300, 120));
		play.setIcon(playIcon);
		play.setBounds(w / 2 - 150,  h / 4, 300, 120);
		sound = new JButton();
		sound.setBounds(20, 20, 78, 65);
		if (isPlaySound) {
			sound.setIcon(new ImageIcon(loadImage("/img/musicOn.png", 78, 65)));
			sound.setActionCommand("sound on");
		} else {
			sound.setIcon(new ImageIcon(loadImage("/img/musicOff.png", 78, 65)));
			sound.setActionCommand("sound off");
		}
		sound.addActionListener(this);
		sound.setBackground(Color.decode("#D2EDFE"));
		sound.setBorder(new LineBorder(Color.decode("#D2EDFE")));
		introIcon = new ImageIcon(loadImage("/img/Title.png", w, h));
		welcome.add(play);
		welcome.setIcon(introIcon);
		welcome.add(sound);
		welcome.add(user);
		frame.add(welcome);

		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		playSound("/sound/sound.wav");
		if (!isPlaySound)
			clip.stop();
	}

	// menu chứa các lựa chọn: exit, start, sound, highScore,...
	public void menu2() {
		menu0 = new JLabel();
		menu0.setSize(1120, 690);
		menu0.setIcon(new ImageIcon(loadImage("/img/blue.png", 1120, 690)));
		if (tk.equals("admin")) {
			// nút test
			demo = new JButton("TEST");
			demo.setFont(new Font("Arial", Font.PLAIN, 25));
			demo.setBounds(450, 250, 200, 100);
			demo.setActionCommand("demo");
			demo.addActionListener(this);
			demo.setBackground(Color.decode("#D2EDFE"));
			demo.setBorder(new LineBorder(Color.decode("#D2EDFE")));
			// nút exit
			exit = new JButton();
			exit.setBounds(900, 511, 78, 65);
			exit.setIcon(new ImageIcon(loadImage("/img/exitRight.png", 78, 65)));
			exit.setActionCommand("exit");
			exit.addActionListener(this);
			exit.setBackground(Color.decode("#D2EDFE"));
			exit.setBorder(new LineBorder(Color.decode("#D2EDFE")));
			// nút sound
			sound = new JButton();
			sound.setBounds(20, 20, 78, 65);
			if (isPlaySound) {
				sound.setIcon(new ImageIcon(loadImage("/img/musicOn.png", 78, 65)));
				sound.setActionCommand("sound on");
			} else {
				sound.setIcon(new ImageIcon(loadImage("/img/musicOff.png", 78, 65)));
				sound.setActionCommand("sound off");
			}
			sound.addActionListener(this);
			sound.setBackground(Color.decode("#D2EDFE"));
			sound.setBorder(new LineBorder(Color.decode("#D2EDFE")));
			// nút ktra mk
			takepass = new JButton("Take Pass");
			takepass.setBounds(130, 511, 150, 65);
			takepass.setFont(new Font("Arial", Font.PLAIN, 25));
			takepass.setActionCommand("pass");
			takepass.addActionListener(this);
			takepass.setBackground(Color.decode("#D2EDFE"));
			takepass.setBorder(new LineBorder(Color.decode("#D2EDFE")));
			// nút set time
			setTime = new JButton("Set Time");
			setTime.setBounds(310, 511, 140, 65);
			setTime.setFont(new Font("Arial", Font.PLAIN, 25));
			setTime.setActionCommand("time");
			setTime.addActionListener(this);
			setTime.setBackground(Color.decode("#D2EDFE"));
			setTime.setBorder(new LineBorder(Color.decode("#D2EDFE")));
			// nút Them anh
			insertImage = new JButton("Insert Image");
			//Imagemode = new JButton("DOREMON");
			insertImage.setBounds(480, 511, 190, 65);
			insertImage.setFont(new Font("Arial", Font.PLAIN, 25));			
			insertImage.setActionCommand("insert");
			insertImage.addActionListener(this);
			insertImage.setBackground(Color.decode("#D2EDFE"));
			insertImage.setBorder(new LineBorder(Color.decode("#D2EDFE")));
			//Nút del
			del = new JButton("DELETE");
			del.setBounds(710, 511, 150, 65);
			del.setFont(new Font("Arial", Font.PLAIN, 25));		
			del.setActionCommand("del");
			del.addActionListener(this);
			del.setBackground(Color.decode("#D2EDFE"));
			del.setBorder(new LineBorder(Color.decode("#D2EDFE")));
			menu0.add(sound);
			menu0.add(demo);
			menu0.add(exit);
			menu0.add(insertImage);
			menu0.add(takepass);
			menu0.add(del);
			menu0.add(setTime);
			welcome.setVisible(false);
			frame.remove(welcome);
			frame.add(menu0);
		} else {
		gameMenu();		
		// gỡ welcome, cài menu
		welcome.setVisible(false);
		frame.remove(welcome);
		frame.add(menu);
		}
	}
	public void gameMenu() {
		menu = new JLabel();
		menu.setSize(1120, 690);
		menu.setIcon(new ImageIcon(loadImage("/img/blue.png", 1120, 690)));
		// nút start
				start = new JButton("START");
				start.setFont(new Font("Arial", Font.PLAIN, 25));
				start.setBounds(450, 250, 200, 100);
				start.setActionCommand("start");
				start.addActionListener(this);
				start.setBackground(Color.decode("#D2EDFE"));
				start.setBorder(new LineBorder(Color.decode("#D2EDFE")));
				// nút so ng cho
				nump = new JButton("1 PLAYER");
				nump.setBounds(480, 370, 140, 70);
				nump.setFont(new Font("Arial", Font.PLAIN, 25));
			    nump.setActionCommand("1p");
				nump.addActionListener(this);
				nump.setBackground(Color.decode("#D2EDFE"));
				nump.setBorder(new LineBorder(Color.decode("#D2EDFE")));
				// nút exit
				exit = new JButton();
				exit.setBounds(850, 511, 78, 65);
				exit.setIcon(new ImageIcon(loadImage("/img/exitRight.png", 78, 65)));
				if (tk.equals("admin")) exit.setActionCommand("exita");
				else exit.setActionCommand("exit");
				exit.addActionListener(this);
				exit.setBackground(Color.decode("#D2EDFE"));
				exit.setBorder(new LineBorder(Color.decode("#D2EDFE")));
				// nút sound
				sound = new JButton();
				sound.setBounds(20, 20, 78, 65);
				if (isPlaySound) {
					sound.setIcon(new ImageIcon(loadImage("/img/musicOn.png", 78, 65)));
					sound.setActionCommand("sound on");
				} else {
					sound.setIcon(new ImageIcon(loadImage("/img/musicOff.png", 78, 65)));
					sound.setActionCommand("sound off");
				}
				sound.addActionListener(this);
				sound.setBackground(Color.decode("#D2EDFE"));
				sound.setBorder(new LineBorder(Color.decode("#D2EDFE")));
				// nút game mode
				gamemode = new JButton("GAME MODE: EASY");
				gamemode.setBounds(150, 511, 250, 65);
				gamemode.setFont(new Font("Arial", Font.PLAIN, 25));
				gamemode.setActionCommand("easy");
				gamemode.addActionListener(this);
				gamemode.setBackground(Color.decode("#D2EDFE"));
				gamemode.setBorder(new LineBorder(Color.decode("#D2EDFE")));
				// nút Imagemode
				DefaultComboBoxModel Imagemode = new DefaultComboBoxModel();
				File folder = new File("D:\\java\\PROJECT_I\\src");
		    	File[] listOfFiles = folder.listFiles();
		    	 //&& s != "HighScore" && s !="icon" && s != "icon" && s != "sound"
		    	  for (final File fileEntry : folder.listFiles()) {
		    	        if (fileEntry.isDirectory()) {
		    	        	String s = fileEntry.getName();
		                    if (!s.equals("Game_lat_hinh") && !s.equals("HighScore") && !s.equals("icon") && !s.equals("img") && !s.equals("sound")) {
		                    	Imagemode.addElement(s);
		                    }
		    	        }
		    	  }
				fruitCombo = new JComboBox(Imagemode);
				fruitCombo.setSelectedIndex(0);
				JScrollPane fruitListScrollPane = new JScrollPane(fruitCombo);
				
				//Imagemode = new JButton("DOREMON");
				fruitListScrollPane.setBounds(450, 511, 200, 65);
				fruitListScrollPane.setFont(new Font("Arial", Font.PLAIN, 25));
				fruitCombo.setFont(new Font("Arial", Font.PLAIN, 25));
				//Imagemode.setActionCommand("doremon");
				//Imagemode.addActionListener(this);
				fruitListScrollPane.setBackground(Color.decode("#D2EDFE"));
				fruitListScrollPane.setBorder(new LineBorder(Color.decode("#D2EDFE")));
				// nút high score
				highScore = new JButton();
				highScore.setBounds(700, 511, 100, 65);
				highScore.setIcon(new ImageIcon(loadImage("/img/trophy.png", 100, 65)));
				highScore.setActionCommand("highScore off");
				highScore.addActionListener(this);
				highScore.setBackground(Color.decode("#D2EDFE"));
				highScore.setBorder(new LineBorder(Color.decode("#D2EDFE")));

				menu.add(sound);
				menu.add(start);
				menu.add(nump);
				//menu.add(Imagemode);
				menu.add(fruitListScrollPane);
				menu.add(gamemode);
				menu.add(highScore);
				menu.add(exit);
				showScore = new JLabel();
				showScore.setBackground(Color.white);
				showScore.setOpaque(true);
				score = new JLabel[9];
				score[0] = new JLabel("",JLabel.LEFT);
				score[0].setBounds(435, 20, 230, 20);
				showScore.setLayout(new GridLayout(9, 1));
				showScore.add(score[0]);
				int h = 40;
				for (int i = 1; i <= 6; i++) {
					score[i] = new JLabel("",JLabel.LEFT);
					score[i].setBounds(435, h, 230, 20);
					showScore.add(score[i]);
					h = h + 20;
				}
				score[7] = new JLabel("YOUR RANK",JLabel.LEFT);
				score[7].setBounds(435, h, 230, 20);
				score[8] = new JLabel("",JLabel.LEFT);
				score[8].setBounds(435, h+20, 230, 20);
				showScore.add(score[7]);
				showScore.add(score[8]);
				showScore.setBounds(435, 20, 230, 220);
				showScore.setVisible(false);
				menu.add(showScore);
	}
	private void insert() {
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
	private void settime() {
		File file = new File("D:\\high.txt");
			try {
				file.createNewFile();
				String[] options = {"1","10", "15", "30", "45", "60", "90", "120"};
		        String n = (String)JOptionPane.showInputDialog(null, "Set a end time (minutes):", 
		                "Set time", JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		            FileWriter fw = new FileWriter("D:\\high.txt");
					fw.write(n);
					fw.close();    		
			} catch (Exception e) {
				System.out.println("File not found");
		}
}	
	private boolean isNumeric(final String cs) {
        if (cs.isEmpty()) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
	private void showHighScore() {
		showScore.setVisible(true);
		Connection connection;
		connection = ConnectionSQL.getConnection();  
		String mode;
		if (gamemode.getActionCommand() == "easy")
			mode = "Easy";
		else
			mode = "Hard";
        try {
        Statement stmt = connection.createStatement();
        // get data 
        ResultSet rs = stmt.executeQuery("SELECT RANK() OVER(ORDER BY score desc) AS Rank, name, score FROM HighScore" + mode);
        if (rs.isBeforeFirst()) {
        	score[0].setText("Rank          Name           Score");
       	 int i = 1;
       	String name[] = new String[10];
       	int rank[] = new int[10];
       	int scorei[] = new int[10];
       	 while (rs.next()) {
       		 if (i > 5) break;
       		    rank[i] = rs.getInt(1);
                name[i] = rs.getString(2);
                scorei[i] = rs.getInt(3);     
                score[i].setText("" + rank[i] + "              " + name[i] + "              " + scorei[i]);
                i++;
            }
       	 if (i<5) for (int j = i; j<=5;j++) score[j].setText("");
        int kq = 0;
        for (int j = 1; j <= 5; j++) {
        	System.out.println(name[j]);
        	if (name[j] == null) break;
        	if (name[j].equals(tk)) {
        		score[8].setText("" + rank[j] + "              " + name[j] + "              " + scorei[j]);
        		kq = 1;
        		break;
        	}
        }
        if (kq == 0) score[8].setText("You don't have rank!");
        }
        else  {
        	score[0].setText("Chưa có người chơi nào vào BXH");
        	for (int i =1; i<=6;i++) score[i].setText("");
        	
        }
        connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}

	private Image loadImage(String s, int w, int h) {
		BufferedImage i = null; // doc anh duoi dang Buffered Image
		try {
			i = ImageIO.read(Menu.class.getResource(s));
		} catch (Exception e) {
			System.out.println("Duong dan anh k hop le!");
		}

		Image dimg = i.getScaledInstance(w, h, Image.SCALE_SMOOTH); // thay doi kich thuoc anh
		return dimg;
	}
	

	private void playSound(String link) {
		try {
			URL url = this.getClass().getResource(link);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if ("Play".equals(e.getActionCommand())) {
			menu2();
		}
		if ("demo".equals(e.getActionCommand())) {
			gameMenu();
			menu0.setVisible(false);
			//frame.remove(menu0);
			frame.add(menu);
		}
		if ("exita".equals(e.getActionCommand())) {
			menu.setVisible(false);
			frame.remove(menu);
			menu0.setVisible(true);
		}
		if ("exit".equals(e.getActionCommand())) {
			clip.stop();
			frame.setVisible(false);
			new Login();
		}
		
		if ("time".equals(e.getActionCommand())) {
			settime();
		}

		if ("sound on".equals(e.getActionCommand())) {
			sound.setIcon(new ImageIcon(loadImage("/img/musicOff.png", 78, 65)));
			sound.setActionCommand("sound off");
			isPlaySound = false;
			clip.stop();
		}

		if ("sound off".equals(e.getActionCommand())) {
			sound.setIcon(new ImageIcon(loadImage("/img/musicOn.png", 78, 65)));
			sound.setActionCommand("sound on");
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			isPlaySound = true;
		}

		if ("start".equals(e.getActionCommand())) {
			if (tk.equals("admin")) frame.remove(menu0);
			String data = "";
            if (fruitCombo.getSelectedIndex() != -1) {
                data = "" + fruitCombo.getItemAt(
                        fruitCombo.getSelectedIndex());
            }
			clip.stop();
            if (nump.getActionCommand() == "1p") {
            	boolean isHard;
            	if (gamemode.getActionCommand() == "easy")
    				isHard = false;
    			else
    				isHard = true;
            	new Logic(0, 100, isPlaySound, isHard, data, tk);
            }
			else
				new Logic(Integer.parseInt(gamemode.getActionCommand()), 100, 100, isPlaySound, data, tk);
            //System.out.println(data);
			//new Logic(0, 100, 100, isPlaySound, isHard, data, tk);
			frame.setVisible(false);
		}
		if ("out".equals(e.getActionCommand())) {
			clip.stop();
			frame.setVisible(false);
			System.exit(0);
		}

		if ("highScore off".equals(e.getActionCommand())) {
			showHighScore();
			highScore.setActionCommand("highScore on");
		}
		
		if ("highScore on".equals(e.getActionCommand())) {
			showScore.setVisible(false);
			highScore.setActionCommand("highScore off");
		}
		
		if ("easy".equals(e.getActionCommand())) {
			gamemode.setText("GAME MODE: HARD");
			gamemode.setActionCommand("hard");
		}

		if ("hard".equals(e.getActionCommand())) {
			gamemode.setText("GAME MODE: EASY");
			gamemode.setActionCommand("easy");
		}
		if ("1p".equals(e.getActionCommand())) {
			nump.setText("2 PLAYER");
			nump.setActionCommand("2p");
			gamemode.setText("2 x 4");
			gamemode.setActionCommand("1");
		}
		if ("1".equals(e.getActionCommand())) {
			gamemode.setText("3 x 6");
			gamemode.setActionCommand("3");
		}
		if ("3".equals(e.getActionCommand())) {
			gamemode.setText("4 x 10");
			gamemode.setActionCommand("7");
		}
		if ("7".equals(e.getActionCommand())) {
			gamemode.setText("2 x 4");
			gamemode.setActionCommand("1");
		}

		if ("2p".equals(e.getActionCommand())) {
			nump.setText("1 PLAYER");
			nump.setActionCommand("1p");
			gamemode.setText("GAME MODE: EASY");
			gamemode.setActionCommand("easy");
		}
		if ("insert".equals(e.getActionCommand())) {
			insert();
		} 
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
		if ("pass".equals(e.getActionCommand())) {
			new TakePass(); 
		} 
		if ("del".equals(e.getActionCommand())) {
			new Delete();
	}
	}
}