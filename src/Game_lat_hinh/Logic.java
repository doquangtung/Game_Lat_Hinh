package Game_lat_hinh;


import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.event.AncestorListener;

public class Logic extends JFrame implements ActionListener{
	int count = 0, id; //lan click thu ? + id cua lan click
	int preX, preY, X, Y; //toa do cua lan click trc va hien tai
	int level = 0, hit = 0; //level + so lan click trung
	int sizeX[] = { 2,  2,  3,  3,  4,  4,   4,   4,   5,  5};  //so hang anh moi man
	int sizeY[] = { 3,  4,  4,  6,  6,  7,   8,  10,  10,  12}; //so cot anh moi man
	int TIME[] =  {10, 20, 30, 50, 60, 80, 100, 120, 140, 150}; // tg moi man
	int TIMEbonus[] = {1, 2, 3, 5, 6, 8, 10, 12, 14, 15};  //tg bonus moi man
	int maxTime = 0, time = 0;  // max time moi man
	int maxXY = 100;
	int m, n ;//so cot/hang
	private JProgressBar progressTime;
	private JButton bt[][] = new JButton[maxXY][maxXY]; //cac button anh
	private boolean tick[][] = new boolean[maxXY][maxXY]; //danh dau anh
	private int anh[][] = new int[maxXY][maxXY]; //dia chi login cua anh
	private JButton score_bt, score_bt2, sound, exit;
	private JPanel pn, pn2;
	private String use;
	private String Imode;
	Container cn; //cn chua cac button anh
	Timer timer, timer2, show;
	public Clip clip; 
	private boolean isPlaySound;
	private boolean IsHard;  //do kho
	private boolean IsOne = true;  //do kho
	JLabel turnP;
	int turn = 0;
	
	
	public Logic(int k, int score, boolean playSound, boolean gamemode, String imagemode, String name) {
		isPlaySound = playSound;
		if (isPlaySound) playSound("/sound/Nhac.wav");
		this.setTitle("Game Lật Hình");
		IsHard = gamemode;
		level = k;
		cn = init(k, score);
		use = name;
		Imode = imagemode;	
		Show();
		timer = new Timer(400, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				open(k);
				timer.stop();
			}
		});
		
		timer2 = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				time ++;
				progressTime.setValue(maxTime - time);
				if (maxTime == time) {
					timer2.stop();
					if (isPlaySound) clip.stop();
					setHighScore();
					showDialogNewGame("Hết thời gian.\n" +
							"Điểm: " + score_bt.getText() + "\n" +
							"Bạn có muốn chơi lại không?", "Thông báo");					
				}
			}
		});
	}
	public Logic(int k, int score1, int score2, boolean playSound, String imagemode, String name) {
		isPlaySound = playSound;
		if (isPlaySound) playSound("/sound/Nhac.wav");
		this.setTitle("Game Lật Hình");
		level = k;
		IsOne = false;
		cn = init(k, score1, score2);
		//Show();
		use = name;
		Imode = imagemode;
		timer = new Timer(400, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				open(k);
				timer.stop();
			}
		});
		/*
		timer2 = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				time ++;
				progressTime.setValue(maxTime - time);
				if (maxTime == time) {
					timer2.stop();
					if (isPlaySound) clip.stop();
					//setHighScore();
					String winner;
					if (Integer.parseInt(score_bt.getText()) > Integer.parseInt(score_bt2.getText()) )
					winner = "Người chơi 1 thắng! ";
					else if (Integer.parseInt(score_bt.getText()) > Integer.parseInt(score_bt2.getText()) )
					winner = "Người chơi 2 thắng! ";	
					else winner = "Hòa! ";	
					showDialogNewGame("Hết thời gian.\n" +
							 winner + "\n" +
							"Bạn có muốn chơi lại không?", "Thông báo");					
				}
			}
		});
		*/
	}
	
	public void Show() {
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++){
				bt[i][j].setIcon(getIcon(anh[i][j]));
		}
		show = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < m; i++)
					for (int j = 0; j < n; j++){
						bt[i][j].setIcon(getIcon(0));
					}			
				show.stop();
			}
		});
		show.start();
	}
	
	public void open(int k) {
		if (id == anh[X][Y]) {
		    bt[preX][preY].setIcon(getIcon(-1));
			anh[X][Y] = anh[preX][preY] = 0;
			tick[X][Y] = tick[preX][preY] = false;
			bt[X][Y].setBorder(null);
			bt[preX][preY].setBorder(null);
			showMatrix();
			bt[X][Y].setIcon(getIcon(-1));
			if (IsOne) {
			score_bt.setText(String.valueOf(Integer.parseInt(score_bt.getText()) + 100));
			} else {
				if (turn == 0) 
					score_bt.setText(String.valueOf(Integer.parseInt(score_bt.getText()) + 100));
				else score_bt2.setText(String.valueOf(Integer.parseInt(score_bt2.getText()) + 100));
				turn = 1 - turn;
				turnP.setText("Turn " + (String.valueOf(turn+1)));
			}
			if (!IsHard) time = time-TIMEbonus[k]*10; // thay doi tg game
			hit++;
			if (hit == m * n / 2) {
				timer.stop();
				if (IsOne) timer2.stop();
				if (isPlaySound) clip.stop();
				nextGame();
			}
		}else {
			bt[preX][preY].setIcon(getIcon(0));
			bt[X][Y].setIcon(getIcon(0));
			if (IsOne) {
			score_bt.setText(String.valueOf(Integer.parseInt(score_bt.getText()) - 10));
			} else {
				if (turn == 0) 
					score_bt.setText(String.valueOf(Integer.parseInt(score_bt.getText()) - 10));
				else score_bt2.setText(String.valueOf(Integer.parseInt(score_bt2.getText()) - 10));
				turn = 1 - turn;
				turnP.setText("Turn " + (String.valueOf(turn+1)));
			}
		}
	}
	
	public Container init(int k, int score) {
		m = sizeX[k];
		n = sizeY[k];
		maxTime = TIME[k] * 10 ;
		time = 0;
		Container cn = this.getContentPane();
		pn = new JPanel();
		pn.setLayout(new GridLayout(m, n));
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++){
				bt[i][j] = new JButton();
				pn.add(bt[i][j]);
				bt[i][j].setActionCommand(i + " " + j);
				bt[i][j].addActionListener(this);
				bt[i][j].setBackground(Color.black);
				anh[i][j] = (int) (Math.random() * 2 + 1);
				bt[i][j].setIcon(getIcon(0));
				tick[i][j] = true;
			}
		pn2 = new JPanel();
		pn2.setLayout(new FlowLayout());
		score_bt = new JButton(String.valueOf(score));
		score_bt.setFont(new Font("UTM Nokia", 1, 20));
		score_bt.setBackground(Color.white);
		 JLabel score_lb; 
		score_lb = new JLabel("Score: ");
		score_lb.setFont(new Font("UTM Nokia", 1, 20));
		sound = new JButton();
		sound.setBounds(1, 1, 40, 40);
		if (isPlaySound) {
			sound.setIcon(new ImageIcon(loadImage("/img/musicOn.png", 40, 40)));
			sound.setActionCommand("sound on");
		} else {
			sound.setIcon(new ImageIcon(loadImage("/img/musicOff.png", 40, 40)));
			sound.setActionCommand("sound off");
		}
		sound.addActionListener(this);
		sound.setBackground(Color.decode("#D2EDFE"));
		sound.setBorder(new LineBorder(Color.decode("#D2EDFE")));
		// nút exit
		exit = new JButton();
		exit.setBounds(900, 511, 78, 65);
		exit.setIcon(new ImageIcon(loadImage("/img/exitRight.png", 40, 40)));
		exit.setActionCommand("exit");
		exit.addActionListener(this);
		exit.setBackground(Color.decode("#D2EDFE"));
		exit.setBorder(new LineBorder(Color.decode("#D2EDFE")));
		pn2.add(sound);
		pn2.add(score_lb);
		pn2.add(score_bt); 
		pn2.add(exit);
		progressTime = new JProgressBar(0, maxTime);
		progressTime.setValue(maxTime);
		progressTime.setForeground(Color.orange);		
		createMatrix();
		showMatrix();
		cn.add(pn);
		cn.add(progressTime, "North");
		cn.add(pn2, "South");
		this.setVisible(true);
		this.setSize(n * 120, m * 165 + 90);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		setResizable(false);
		return cn;
	}
	public Container init(int k, int score1, int score2) {
		m = sizeX[k];
		n = sizeY[k];
		maxTime = TIME[k] * 10 ;
		time = 0;
		Container cn = this.getContentPane();
		pn = new JPanel();
		pn.setLayout(new GridLayout(m, n));
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++){
				bt[i][j] = new JButton();
				pn.add(bt[i][j]);
				bt[i][j].setActionCommand(i + " " + j);
				bt[i][j].addActionListener(this);
				bt[i][j].setBackground(Color.black);
				anh[i][j] = (int) (Math.random() * 2 + 1);
				bt[i][j].setIcon(getIcon(0));
				tick[i][j] = true;
			}
		pn2 = new JPanel();
		pn2.setLayout(new FlowLayout());
		score_bt = new JButton(String.valueOf(score1));
		score_bt.setFont(new Font("UTM Nokia", 1, 20));
		score_bt.setBackground(Color.white);
		 JLabel score_lb = new JLabel("Score1: ");
		score_lb.setFont(new Font("UTM Nokia", 1, 20));
		sound = new JButton();
		//sound.setBounds(1, 1, 40, 40);
		if (isPlaySound) {
			sound.setIcon(new ImageIcon(loadImage("/img/musicOn.png", 40, 40)));
			sound.setActionCommand("sound on");
		} else {
			sound.setIcon(new ImageIcon(loadImage("/img/musicOff.png", 40, 40)));
			sound.setActionCommand("sound off");
		}
		turnP = new JLabel("Turn " + (String.valueOf(turn+1)));
		turnP.setFont(new Font("UTM Nokia", 1, 20));
		sound.addActionListener(this);
		sound.setBackground(Color.decode("#D2EDFE"));
		sound.setBorder(new LineBorder(Color.decode("#D2EDFE")));
		// nút exit
		exit = new JButton();
	    exit.setBounds(900, 511, 78, 65);
	    exit.setIcon(new ImageIcon(loadImage("/img/exitRight.png", 40, 40)));
		exit.setActionCommand("exit");
		exit.addActionListener(this);
		exit.setBackground(Color.decode("#D2EDFE"));
		exit.setBorder(new LineBorder(Color.decode("#D2EDFE")));

		pn2.add(sound);
		pn2.add(turnP);
		pn2.add(score_lb);
		pn2.add(score_bt);   
		JLabel score_lb2 = new JLabel("Score2: ");
		score_lb2.setFont(new Font("UTM Nokia", 1, 20));
		score_bt2 = new JButton(String.valueOf(score2));
		score_bt2.setFont(new Font("UTM Nokia", 1, 20));
		score_bt2.setBackground(Color.white);
		pn2.add(score_lb2);
		pn2.add(score_bt2);
		pn2.add(exit);
		//progressTime = new JProgressBar(0, maxTime);
		//progressTime.setValue(maxTime);
		//progressTime.setForeground(Color.orange);		
		createMatrix();
		showMatrix();
		cn.add(pn);
		//cn.add(progressTime, "North");
		cn.add(pn2, "South");
		this.setVisible(true);
		if (k == 1)
		this.setSize(n * 125, m * 165 + 70);
		else this.setSize(n * 120, m * 165 + 70);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		setResizable(false);
		return cn;
	}
	public void createMatrix() {
		int N = m * n;
		int b[] = new int [m * n];
		int c[] = new int [m * n];
		for (int i = 0; i < N; i++) {
			b[i] = i + 1;
			if (b[i] > N / 2)
				b[i] -= (N / 2);
			c[i] = (int) (Math.random() * 1000000);
		}
		for (int i = 0; i < N - 1; i++)
			for (int j = i + 1; j < N; j++)
				if (c[i] > c[j]) {
					int tmp = b[i];
					b[i] = b[j];
					b[j] = tmp;
					tmp = c[i];
					c[i] = c[j];
					c[j] = tmp;
				}
		N = 0;
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				anh[i][j] = b[N++];
	}
	
	public void showMatrix() {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				System.out.printf("%3d", anh[i][j]);
			System.out.println();
		}
		System.out.println("-----------------\n");
	}
	
	private Icon getIcon(int index) {
		Image image;
		int width = 120, height = 165;
		if (index < 1 || index > 30)
		image = new ImageIcon(getClass().getResource("/icon/icon" + index + ".jpg")).getImage();
		else 
		//image = new ImageIcon(getClass().getResource("/" + Imode +"/" + index + ".jpg")).getImage();
		image = loadImage ("D:\\java\\PROJECT_I\\src\\" + Imode + "\\icon" + index + ".jpg", width, height);
		Icon icon = new ImageIcon(image.getScaledInstance(width, height, image.SCALE_SMOOTH));
		return icon;
	}
	
	public void newGame() {
		this.dispose();
		if (IsOne)
		new Logic(0, 100, isPlaySound, IsHard, Imode, use);
		else new Logic(level, 100, 100, isPlaySound, Imode, use);
	}
	
	public void nextGame() {
		this.dispose();
		if (IsOne)
		new Logic(level + 1, Integer.parseInt(score_bt.getText()) + (maxTime - time)/50, isPlaySound, IsHard, Imode, use);
		else {
			//new Logic(level + 1, Integer.parseInt(score_bt.getText()), Integer.parseInt(score_bt2.getText()), isPlaySound, IsHard, Imode, use);
			String winner;
			if (Integer.parseInt(score_bt.getText()) > Integer.parseInt(score_bt2.getText()) )
			winner = "Người chơi 1 thắng! ";
			else if (Integer.parseInt(score_bt2.getText()) > Integer.parseInt(score_bt.getText()) )
			winner = "Người chơi 2 thắng! ";	
			else winner = "Hòa! ";	
			showDialogNewGame("Kết quả.\n" +
					 winner + "\n" +
					"Bạn có muốn chơi lại không?", "Thông báo");
		}
	}
	
	public void showDialogNewGame(String message, String title) {
		int select = JOptionPane.showOptionDialog(null, message, title,
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				null, null);
		if (select == 0) {
			newGame();
		} else {
			this.dispose();
			new Menu(1120, 690, true , use);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("sound on".equals(e.getActionCommand())) {
			sound.setIcon(new ImageIcon(loadImage("/img/musicOff.png", 40, 40)));
			sound.setActionCommand("sound off");
			isPlaySound = false;
			clip.stop();
		}
		else
		if ("sound off".equals(e.getActionCommand())) {
			sound.setIcon(new ImageIcon(loadImage("/img/musicOn.png", 40, 40)));
			sound.setActionCommand("sound on");
			if (!isPlaySound) {
				playSound("/sound/Nhac.wav");
				isPlaySound = true;
			}else {
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			isPlaySound = true;}
		}
		else if ("exit".equals(e.getActionCommand())) {
			this.dispose();
			new Menu(1120, 690, true , use);
		}
		else {
		if (IsOne) timer2.start();
		// TODO Auto-generated method stub
		int i, j;
		String s = e.getActionCommand();
		System.out.println(s);
		int k = s.indexOf(" ");
		i = Integer.parseInt(s.substring(0, k));
		j = Integer.parseInt(s.substring (k + 1, s.length()));
		System.out.println("i = " + i + "j = " +j);
		if (tick[i][j]) {
			if (count == 0) {
				bt[i][j].setIcon(getIcon(anh[i][j]));
				id = anh[i][j];
				preX = i;
				preY = j;
			}else {
				bt[i][j].setIcon(getIcon(anh[i][j]));
				X = i; Y = j;
				timer.start(); 
			}
			count = 1 - count;
		}
		}
	}
	private void playSound(String link) {
		try {
			URL url = this.getClass().getResource(link);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.loop(clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	private Image loadImage(String s, int w, int h) {
		BufferedImage i = null; // doc anh duoi dang Buffered Image
		File f = null;
		try {
			if (s.charAt(0) == 'D') {
			f = new File (s);
			i = ImageIO.read(f);}
			else i = ImageIO.read(Menu.class.getResource(s));
		} catch (Exception e) {
			System.out.println("Duong dan anh k hop le!");
			System.out.println(s);
		}

		Image dimg = i.getScaledInstance(w, h, Image.SCALE_SMOOTH); // thay doi kich thuoc anh
		return dimg;

	}
	private void setHighScore() {
		Connection connection;
		connection = ConnectionSQL.getConnection();          
        try {
        Statement stmt = connection.createStatement();
        // get data from table 'student'
        String mode;
        if (IsHard) {
        	mode = "Hard";
        } else mode = "Easy";
        ResultSet rs = stmt.executeQuery("select * from HighScore" + mode + " where name = '"+ use + "'" );
        if (rs.isBeforeFirst()) {
        	boolean next = rs.next();
        	if(rs.getInt(2) < Integer.parseInt(score_bt.getText()))
        	stmt.executeUpdate("update HighScore" + mode + " set score = " + Integer.parseInt(score_bt.getText()) + " where name = '" + use + "'");
        }
        else  {
        	stmt.executeUpdate("INSERT INTO HighScore" + mode + " (name, score) VALUES ('" + use + "', '" + Integer.parseInt(score_bt.getText()) + "')");
        }
        connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

	}

}
