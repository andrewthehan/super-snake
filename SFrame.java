import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

class SFrame extends JFrame implements ActionListener, MouseListener{
	Container cP;
	JLabel background;
	JButton b1, b2, b3, b4, b5, bPrevious, bNext, bMute, bPauseMainMenu;
	AudioClip a1, a2, a3, a4, aPlaying;
	byte rulesPage;
	boolean isMute;
	MouseListener muteML;

	JFrame confirm;
	Container confirmCP;
	JLabel confirmBackground;
	ActionListener confirmAL;
	JButton bOk, bCancel;

	JFrame gameOver;
	Container gameOverCP;
	JLabel gameOverBackground;
	ActionListener gameOverAL;
	JButton bPlayAgain, bMainMenu;

	JFrame highScore;
	Container highScoreCP;
	JLabel highScoreBackground;
	ActionListener highScoreAL;
	JButton bOkHS;
	JTextField name;
	JLabel[] hSScoreL, hSNameL;
	Font font=new Font("Lucida Console", Font.PLAIN, 24);

	File highscores;
	BufferedReader br;
	int hSScore[];
	String hSName[];
	FileWriter fw;
	BufferedWriter bw;

	Controller controller;

	DemoSnake demo;

	public SFrame(){
		setSize(C.WINDOW_WIDTH+6, C.WINDOW_HEIGHT+28);
		Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dim.width-getSize().width)/2, (dim.height-getSize().height)/2);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Super Snake");
		setIconImage(Toolkit.getDefaultToolkit().getImage(I.class.getResource("Images/god_mode.png")));

		cP=getContentPane();
		cP.setLayout(null);

		background=new JLabel();
		background.setBounds(0, 0, C.WINDOW_WIDTH, C.WINDOW_HEIGHT);
		cP.add(background);

		b1=new JButton();
		b1.setBounds(C.B_X, C.B1_Y, C.B_WIDTH, C.B_HEIGHT);
		b1.addActionListener(this);
		background.add(b1);

		b2=new JButton();
		b2.setBounds(C.B_X, C.B2_Y, C.B_WIDTH, C.B_HEIGHT);
		b2.addActionListener(this);
		background.add(b2);

		b3=new JButton();
		b3.setBounds(C.B_X, C.B3_Y, C.B_WIDTH, C.B_HEIGHT);
		b3.addActionListener(this);
		background.add(b3);

		b4=new JButton();
		b4.setBounds(C.B_X, C.B4_Y, C.B_WIDTH, C.B_HEIGHT);
		b4.addActionListener(this);
		background.add(b4);

		b5=new JButton();
		b5.setBounds(C.B_X, C.B5_Y, C.B_WIDTH, C.B_HEIGHT);
		b5.addActionListener(this);
		background.add(b5);

		constructMute();

		bNext=new JButton(I.B_NEXT);
		bNext.setBounds(C.B_NEXT_X, C.B_NEXT_PREVIOUS_Y, C.B_SMALL_WIDTH, C.B_SMALL_HEIGHT);
		bNext.addActionListener(this);
		bNext.setVisible(false);
		background.add(bNext);

		bPrevious=new JButton(I.B_PREVIOUS);
		bPrevious.setBounds(C.B_PREVIOUS_X, C.B_NEXT_PREVIOUS_Y, C.B_SMALL_WIDTH, C.B_SMALL_HEIGHT);
		bPrevious.addActionListener(this);
		bPrevious.setVisible(false);
		background.add(bPrevious);

		a1=Applet.newAudioClip(A.class.getResource(A.S1));
		a2=pplet.newAudioClip(A.class.getResource(A.S2));
		a3=Applet.newAudioClip(A.class.getResource(A.S3));
		a4=Applet.newAudioClip(A.class.getResource(A.S4));

		rulesPage=0;

		isMute=false;

		constructConfirm();
		constructGameOver();
		constructHighScore();

		bPauseMainMenu=new JButton(I.B_MAIN_MENU);
		bPauseMainMenu.setBounds(C.B_PAUSE_MAIN_MENU_X, C.B_PAUSE_MAIN_MENU_Y, C.B_SMALL_WIDTH, C.B_SMALL_HEIGHT);
		bPauseMainMenu.setVisible(false);
		bPauseMainMenu.addActionListener(gameOverAL);
		background.add(bPauseMainMenu);

		controller=new Controller(background);

		demo=new DemoSnake(background);

		setTitle();
		play(a2);

		setVisible(true);
	}
	private void constructMute(){
		muteML=new MouseListener(){
			@Override
			public void mousePressed(MouseEvent e){
				bMute.setIcon(isMute?I.B_SOUND_OFF_CLICK:I.B_SOUND_ON_CLICK);
				if(controller.snake.headPart!=null)
					controller.snake.headPart.requestFocus();
			}
			@Override
			public void mouseReleased(MouseEvent e){
				bMute.setIcon(isMute?I.B_SOUND_OFF:I.B_SOUND_ON);
				if(controller.snake.headPart!=null)
					controller.snake.headPart.requestFocus();
			}
			@Override
			public void mouseExited(MouseEvent e){
			}
			@Override
			public void mouseEntered(MouseEvent e){
			}
			@Override
			public void mouseClicked(MouseEvent e){
				soundToggle();
			}
		};

		bMute=new JButton(I.B_SOUND_ON);
		bMute.setBounds(C.B_MUTE_X, C.B_MUTE_Y, C.B_MUTE_WIDTH, C.B_MUTE_HEIGHT);
		bMute.addMouseListener(muteML);
		background.add(bMute);
	}
	private void constructGameOver(){
		gameOver=new JFrame();
		gameOver.setSize(C.SMALL_WINDOW_WIDTH, C.SMALL_WINDOW_HEIGHT);
		gameOver.setUndecorated(true);
		Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
		gameOver.setLocation((short)((dim.width-gameOver.getSize().width)*.5), (short)((dim.height-gameOver.getSize().height)*.5));

		gameOver.setAlwaysOnTop(true);

		gameOverCP=gameOver.getContentPane();
		gameOverCP.setLayout(null);

		gameOverBackground=new JLabel(I.S_GAME_OVER);
		gameOverBackground.setBounds(0, 0, C.SMALL_WINDOW_WIDTH, C.SMALL_WINDOW_HEIGHT);
		gameOverCP.add(gameOverBackground);

		bPlayAgain=new JButton(I.B_PLAY_AGAIN);
		bPlayAgain.setBounds(C.B_SMALL_LEFT_X, C.B_SMALL_Y, C.B_SMALL_WIDTH, C.B_SMALL_HEIGHT);
		gameOverBackground.add(bPlayAgain);

		bMainMenu=new JButton(I.B_MAIN_MENU);
		bMainMenu.setBounds(C.B_SMALL_RIGHT_X, C.B_SMALL_Y, C.B_SMALL_WIDTH, C.B_SMALL_HEIGHT);
		gameOverBackground.add(bMainMenu);

		gameOverAL=new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				JButton b=(JButton)e.getSource();
				if(b==bPlayAgain){
					controller.reset();
					if(controller.snake.gameMode==I.S_SUPER_SNAKE)
						controller.startSuperSnake();
					else if(controller.snake.gameMode==I.S_CLASSIC_SNAKE)
						controller.startClassicSnake();
					else if(controller.snake.gameMode==I.S_SANDBOX)
						controller.startSandbox();
				}
				else if(b==bMainMenu || b==bPauseMainMenu)
					controller.mainMenu();
				gameOver.setVisible(false);
			}
		};
		bPlayAgain.addActionListener(gameOverAL);
		bMainMenu.addActionListener(gameOverAL);
	}
	private void constructConfirm(){
		confirm=new JFrame();
		confirm.setSize(C.SMALL_WINDOW_WIDTH, C.SMALL_WINDOW_HEIGHT);
		confirm.setUndecorated(true);
		Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
		confirm.setLocation((short)((dim.width-confirm.getSize().width)*.5), (short)((dim.height-confirm.getSize().height)*.5));

		confirm.setAlwaysOnTop(true);

		confirmCP=confirm.getContentPane();
		confirmCP.setLayout(null);

		confirmBackground=new JLabel(I.S_CONFIRM);
		confirmBackground.setBounds(0, 0, C.SMALL_WINDOW_WIDTH, C.SMALL_WINDOW_HEIGHT);
		confirmCP.add(confirmBackground);

		bOk=new JButton(I.B_OK);
		bOk.setBounds(C.B_SMALL_LEFT_X, C.B_SMALL_Y, C.B_SMALL_WIDTH, C.B_SMALL_HEIGHT);
		confirmBackground.add(bOk);

		bCancel=new JButton(I.B_CANCEL);
		bCancel.setBounds(C.B_SMALL_RIGHT_X, C.B_SMALL_Y, C.B_SMALL_WIDTH, C.B_SMALL_HEIGHT);
		confirmBackground.add(bCancel);

		confirmAL=new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				JButton b=(JButton)e.getSource();
				if(b==bOk)
					System.exit(0);
				else if(b==bCancel)
					confirm.setVisible(false);
			}
		};
		bOk.addActionListener(confirmAL);
		bCancel.addActionListener(confirmAL);
	}
	private void constructHighScore(){
		highScore=new JFrame();
		highScore.setSize(C.SMALL_WINDOW_WIDTH, C.SMALL_WINDOW_HEIGHT);
		highScore.setUndecorated(true);
		Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
		highScore.setLocation((short)((dim.width-highScore.getSize().width)*.5), (short)((dim.height-highScore.getSize().height)*.5));

		highScore.setAlwaysOnTop(true);

		highScoreCP=highScore.getContentPane();
		highScoreCP.setLayout(null);

		highScoreBackground=new JLabel(I.S_HIGH_SCORE);
		highScoreBackground.setBounds(0, 0, C.SMALL_WINDOW_WIDTH, C.SMALL_WINDOW_HEIGHT);
		highScoreCP.add(highScoreBackground);

		bOkHS=new JButton(I.B_OK);
		bOkHS.setBounds(C.B_OK_HS_X, C.B_OK_HS_Y, C.B_SMALL_WIDTH, C.B_SMALL_HEIGHT);
		highScoreBackground.add(bOkHS);

		highScoreAL=new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				JButton b=(JButton)e.getSource();
				if(b==bOkHS){
					updateHS();
					highScore.setVisible(false);
				}
			}
		};

		bOkHS.addActionListener(highScoreAL);

		name=new JTextField(10);
		name.setBounds(C.NAME_X, C.NAME_Y, C.NAME_WIDTH, C.NAME_HEIGHT);
		highScoreBackground.add(name);

		inputHSList();

		hSNameL=new JLabel[10];
		hSScoreL=new JLabel[10];

		for(byte i=0; i<10; i++){
			hSNameL[i]=new JLabel(hSName[i]);
			hSScoreL[i]=new JLabel(hSScore[i]+"");
			hSNameL[i].setBounds(C.HIGH_SCORE_NAME_X, C.HIGH_SCORE_START_Y+(C.HIGH_SCORE_NEXT_Y*i), 200, 30);
			hSScoreL[i].setBounds(C.HIGH_SCORE_SCORE_X, C.HIGH_SCORE_START_Y+(C.HIGH_SCORE_NEXT_Y*i), 200, 30);
			hSNameL[i].setFont(font);
			hSScoreL[i].setFont(font);
			background.add(hSNameL[i]);
			background.add(hSScoreL[i]);
		}
		removeHSLabel();
	}
	private void rules(String turn){
		if(turn.equals("previous")){
			rulesPage--;
			if(rulesPage==3)
				bNext.setEnabled(true);
		}
		else if(turn.equals("next")){
			rulesPage++;
			if(rulesPage==4)
				bNext.setEnabled(false);
		}
		if(rulesPage>=0)
			background.setIcon(I.S_RULES[rulesPage]);
		else{
			rulesPage=0;
			bPrevious.setVisible(false);
			bNext.setVisible(false);
			setTitle();
		}
	}
	public void play(AudioClip a){
		a1.stop();
		a2.stop();
		a3.stop();
		a4.stop();
		if(!isMute && a!=null){
			a.loop();
			aPlaying=a;
		}
		else if(!isMute && a==null){
			short temp=C.random((short)1, (short)3);
			if(temp==1){
				a1.loop();
				aPlaying=a1;
			}
			else if(temp==2){
				a3.loop();
				aPlaying=a3;
			}
			else if(temp==3){
				a4.loop();
				aPlaying=a4;
			}
		}
	}

	public void addHSLabel(){
		for(byte i=0; i<10; i++){
			hSNameL[i].setText(hSName[i]);
			hSScoreL[i].setText(hSScore[i]+"");
			hSNameL[i].setVisible(true);
			hSScoreL[i].setVisible(true);
		}
	}
	public void removeHSLabel(){
		for(byte i=0; i<10; i++){
			hSNameL[i].setVisible(false);
			hSScoreL[i].setVisible(false);
		}
	}
	private void soundToggle(){
		if(isMute){
			isMute=false;
			play(aPlaying);
			bMute.setIcon(I.B_SOUND_ON);
		}
		else{
			isMute=true;
			play(null);
			bMute.setIcon(I.B_SOUND_OFF);
		}
	}
	private void inputHSList(){
		highscores=new File("highscores.snake");
		highscores.setReadOnly();
		hSScore=new int[10];
		hSName=new String[10];
		if(!highscores.exists()){
			for(byte i=0; i<10; i++){
				hSScore[i]=0;
				hSName[i]="BLANK";
			}
			try{
				highscores.createNewFile();
			}catch(IOException e){
				System.out.println(e);
			}
			outputHSList();
		}
		else{
			br=null;
			try{
				String currentLine;
				br=new BufferedReader(new FileReader(highscores));
				for(byte i=0; (currentLine=br.readLine())!=null; i++){
					hSScore[i]=Integer.parseInt(currentLine.substring(currentLine.indexOf(" ")+1));
					hSName[i]=currentLine.substring(0, currentLine.indexOf(" "));
				}
			}catch(IOException e){
				System.out.println(e);
			}finally{
				try{
					if(br!=null)
						br.close();
				}catch(IOException e){
					System.out.println(e);
				}
			}
		}
	}
	private void outputHSList(){
		sortHS();
		highscores.setWritable(true);
		try{
			fw=new FileWriter(highscores.getName());
			bw=new BufferedWriter(fw);
			for(byte i=0; i<10; i++){
				bw.write(hSName[i]+" "+hSScore[i]);
				bw.newLine();
			}
			bw.close();
		}catch(IOException e){
			System.out.println(e);
		}
		highscores.setReadOnly();
	}
	public void sortHS(){
		for(byte i=0; i<9; i++){
			for(byte j=(byte)(i+1); j<10; j++){
				if(hSScore[i]<hSScore[j]){
					int intTemp=hSScore[i];
					hSScore[i]=hSScore[j];
					hSScore[j]=intTemp;
					String stringTemp=hSName[i];
					hSName[i]=hSName[j];
					hSName[j]=stringTemp;
				}
			}
		}
	}
	public void updateHS(){
		hSScore[9]=controller.score;
		String nameString=name.getText();
		if(nameString.length()==0)
			nameString="BLANK";
		else
			for(byte i=0; i<nameString.length(); i++)
				if(nameString.charAt(i)==32)
					nameString=nameString.substring(0, i)+"_"+nameString.substring(i+1);
		if(nameString.length()>12)
			nameString=nameString.substring(0, 12);
		hSName[9]=nameString;
		name.setText("");
		outputHSList();
	}

	public void setTitle(){
		background.setIcon(I.S_TITLE);

		b1.setVisible(true);
		b2.setVisible(true);
		b3.setVisible(true);
		b4.setVisible(true);
		b5.setVisible(true);

		b1.setIcon(I.B_PLAY);
		b2.setIcon(I.B_RULES);
		b3.setIcon(I.B_HIGH_SCORES);
		b4.setIcon(I.B_OPTIONS);
		b5.setIcon(I.B_EXIT);
	}
	private void setPlay(){
		background.setIcon(I.S_PLAY);

		b1.setVisible(true);
		b2.setVisible(true);
		b3.setVisible(true);
		b4.setVisible(false);
		b5.setVisible(true);

		b1.setIcon(I.B_SUPER_SNAKE);
		b1.addMouseListener(this);
		b2.setIcon(I.B_CLASSIC_SNAKE);
		b2.addMouseListener(this);
		b3.setIcon(I.B_SANDBOX);
		b3.addMouseListener(this);
		b5.setIcon(I.B_BACK);

		demo.startSuper();
	}
	private void setSuperSnake(){
		b1.setVisible(false);
		b2.setVisible(false);
		b3.setVisible(false);
		b4.setVisible(false);
		b5.setVisible(false);

		controller.startSuperSnake();
	}
	private void setClassicSnake(){
		b1.setVisible(false);
		b2.setVisible(false);
		b3.setVisible(false);
		b4.setVisible(false);
		b5.setVisible(false);

		controller.startClassicSnake();
	}
	private void setSandbox(){
		b1.setVisible(false);
		b2.setVisible(false);
		b3.setVisible(false);
		b4.setVisible(false);
		b5.setVisible(false);

		controller.startSandbox();
	}
	private void setRules(){
		background.setIcon(I.S_RULES[rulesPage]);

		b1.setVisible(false);
		b2.setVisible(false);
		b3.setVisible(false);
		b4.setVisible(false);
		b5.setVisible(false);

		bNext.setVisible(true);
		bPrevious.setVisible(true);
		bNext.setEnabled(true);
	}
	private void setHighScores(){
		background.setIcon(I.S_HIGH_SCORES);

		b1.setVisible(false);
		b2.setVisible(false);
		b3.setVisible(false);
		b4.setVisible(false);
		b5.setVisible(true);

		addHSLabel();

		b5.setIcon(I.B_BACK);
	}
	private void setOptions(){
		background.setIcon(I.S_OPTIONS);

		b1.setVisible(true);
		b2.setVisible(true);
		b3.setVisible(false);
		b4.setVisible(false);
		b5.setVisible(true);

		b1.setIcon(I.B_MUSIC);
		b2.setIcon(I.B_CREDITS);
		b5.setIcon(I.B_BACK);
	}
	private void setMusic(){
		background.setIcon(I.S_MUSIC);

		b1.setVisible(true);
		b2.setVisible(true);
		b3.setVisible(true);
		b4.setVisible(true);
		b5.setVisible(true);

		b1.setIcon(I.B_S1);
		b2.setIcon(I.B_S2);
		b3.setIcon(I.B_S3);
		b4.setIcon(I.B_S4);
		b5.setIcon(I.B_BACK);
	}
	private void setCredits(){
		background.setIcon(I.S_CREDITS);

		b1.setVisible(false);
		b2.setVisible(false);
		b3.setVisible(false);
		b4.setVisible(false);
		b5.setVisible(true);

		b5.setIcon(I.B_BACK);
	}
	private void setExit(){
		confirm.setVisible(true);
	}

	public void actionPerformed(ActionEvent e){
		if(!confirm.isVisible()){
			JButton b=(JButton)e.getSource();
			if(background.getIcon()==I.S_TITLE){
				if(b==b1)
					setPlay();
				else if(b==b2)
					setRules();
				else if(b==b3)
					setHighScores();
				else if(b==b4)
					setOptions();
				else if(b==b5)
					setExit();
			}
			else if(background.getIcon()==I.S_PLAY){
				if(b==b1)
					setSuperSnake();
				else if(b==b2)
					setClassicSnake();
				else if(b==b3)
					setSandbox();
				else if(b==b5)
					setTitle();
				b1.removeMouseListener(this);
				b2.removeMouseListener(this);
				b3.removeMouseListener(this);
				demo.stop();
			}
			else if(background.getIcon()==I.S_HIGH_SCORES){
				if(b==b5){
					setTitle();
					removeHSLabel();
				}
			}
			else if(background.getIcon()==I.S_OPTIONS){
				if(b==b1)
					setMusic();
				else if(b==b2)
					setCredits();
				else if(b==b5)
					setTitle();
			}
			else if(background.getIcon()==I.S_MUSIC){
				if(b==b1)
					play(a1);
				else if(b==b2)
					play(a2);
				else if(b==b3)
					play(a3);
				else if(b==b4)
					play(a4);
				else if(b==b5)
					setOptions();
			}
			else if(background.getIcon()==I.S_CREDITS){
				if(b==b5)
					setOptions();
			}
			else{
				if(b==bPrevious)
					rules("previous");
				else if(b==bNext)
					rules("next");
			}
		}
	}
	public void mouseEntered(MouseEvent e){
		JButton b=(JButton)e.getSource();
		demo.stop();
		if(b==b1)
			demo.startSuper();
		else if(b==b2)
			demo.startClassic();
		else if(b==b3)
			demo.startSandbox();
	}
	public void mouseExited(MouseEvent e){
		demo.stop();
		demo.startGeneric();
	}
	public void mousePressed(MouseEvent e){
	}
	public void mouseReleased(MouseEvent e){
	}
	public void mouseClicked(MouseEvent e){
	}
}