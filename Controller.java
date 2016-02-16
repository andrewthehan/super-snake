import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.Timer;

class Controller implements ActionListener{
	DecimalFormat scoreDF=new DecimalFormat("Score: 0");
	DecimalFormat durationDF=new DecimalFormat("Item Duration: 0.00s");
	DecimalFormat multiplierDF=new DecimalFormat("Multiplier: 0x");
	DecimalFormat consumeDF=new DecimalFormat("Consume(s) Left: 0");
	JLabel background;
	ArrayList<Item> itemList;
	ActionListener itemAL;
	int score;
	short speed, duration;
	JLabel scoreL, multiplierL, consumeL, durationL;
	Snake snake;
	Ration ration;
	Enemy enemy1, enemy2;
	Timer moveT, itemT, doubleT, invincibleT, magnetT, speedT;
	boolean isDouble, isInvincible, isMagnet, isSpeed, enemy1Exists, enemy2Exists;
	Font font=new Font("Arial", Font.PLAIN, 16);

	public Controller(JLabel background){
		this.background=background;
		snake=new Snake(background);
		itemList=new ArrayList();
		itemAL=new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				spawnItem();
			}
		};
		itemT=new Timer(10000, itemAL);
		ration=new Ration();

		score=0;
		speed=100;
		duration=1000;

		moveT=new Timer(speed, this);

		isDouble=false;
		isInvincible=false;
		isMagnet=false;
		isSpeed=false;
		constructItemTimers();

		scoreL=new JLabel(scoreDF.format(score));
		scoreL.setFont(font);
		scoreL.setBounds(18, 15, 1000, 20);
		scoreL.setVisible(false);
		background.add(scoreL);

		multiplierL=new JLabel(multiplierDF.format(snake.body.size()));
		multiplierL.setFont(font);
		multiplierL.setBounds(18, 565, 1000, 20);
		multiplierL.setVisible(false);
		background.add(multiplierL);

		durationL=new JLabel(durationDF.format(duration/1000.));
		durationL.setFont(font);
		durationL.setBounds(435, 565, 1000, 20);
		durationL.setVisible(false);
		background.add(durationL);

		consumeL=new JLabel(consumeDF.format(speed/10));
		consumeL.setFont(font);
		consumeL.setBounds(440, 15, 1000, 20);
		consumeL.setVisible(false);
		background.add(consumeL);

		enemy1=null;
		enemy2=null;
		enemy1Exists=false;
		enemy2Exists=false;
	}
	public void updateScore(){
		score+=100*snake.body.size();
		scoreL.setText(scoreDF.format(score));
	}
	public void updateMultiplier(){
		multiplierL.setText(multiplierDF.format(snake.body.size()));
	}
	public void updateDuration(){
		doubleT.setInitialDelay(duration);
		invincibleT.setInitialDelay(duration);
		magnetT.setInitialDelay(duration);
		speedT.setInitialDelay(duration);

		durationL.setText(durationDF.format(duration*.001));
	}
	public void updateSpeed(){
		speed-=10;
		moveT.setDelay(speed);
		consumeL.setText(consumeDF.format(speed*.1));
		if(speed*.1==7){
			enemy1=new Enemy(background);
			enemy1Exists=true;
		}
		else if(speed*.1==4){
			enemy2=new Enemy(background);
			enemy2Exists=true;
		}
		if(enemy1Exists)
			enemy1.moveT.setDelay(speed*2);
		if(enemy2Exists)
			enemy2.moveT.setDelay(speed*2);
	}
	private void constructItemTimers(){
		doubleT=new Timer(duration, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				isDouble=false;
				if(!isInvincible && !isMagnet && !isSpeed)
					snake.headPart.setIcon(I.PART_HEAD);
				doubleT.stop();
			}
		});
		invincibleT=new Timer(duration, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				isInvincible=false;
				if(!isDouble && !isMagnet && !isSpeed)
					snake.headPart.setIcon(I.PART_HEAD);
				invincibleT.stop();
			}
		});
		magnetT=new Timer(duration, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				isMagnet=false;
				if(!isDouble && !isInvincible && !isSpeed)
					snake.headPart.setIcon(I.PART_HEAD);
				magnetT.stop();
			}
		});
		speedT=new Timer(duration, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				isSpeed=false;
				if(!isDouble && !isInvincible && !isMagnet)
					snake.headPart.setIcon(I.PART_HEAD);
				moveT.setDelay(speed);
				speedT.stop();
			}
		});
	}
	private void spawnItem(){
		short type=C.random((short)1, (short)100);
		Item item=(type>=1 && type<=5)?new GodMode(): //5%
					(type>=6 && type<=35)?new Speed(): //30%
					(type>=36 && type<=65)?new Double(): //30%
					(type>=66 && type<=80)?new Invincible(): //15%
					(type>=81 && type<=100)?new Magnet(): //20%
					null;
		background.add(item);
		itemList.add(item);

		if(itemList.size()>5){
			Item firstItem=itemList.get(0);
			background.remove(firstItem);
			itemList.remove(firstItem);
			firstItem.setIcon(null);
			firstItem=null;
		}
	}
	private void collision(){
		if(ration.x==snake.headPart.x && ration.y==snake.headPart.y)
			getRation();
		for(byte i=0; i<itemList.size(); i++)
				if(itemList.get(i).x==snake.headPart.x && itemList.get(i).y==snake.headPart.y)
					getItem(itemList.get(i));
		if(snake.headPart.y<C.UP_BORDER || snake.headPart.x+C.SIZE>C.RIGHT_BORDER || snake.headPart.y+C.SIZE>C.DOWN_BORDER || snake.headPart.x<C.LEFT_BORDER){
			if(isInvincible)
				otherSide();
			else
				gameOver();
		}
		if(!isInvincible){
			for(short i=1; i<snake.body.size(); i++)
				if(snake.body.get(i).x==snake.headPart.x && snake.body.get(i).y==snake.headPart.y)
					gameOver();
			if(enemy1Exists && touchEnemy(enemy1)){
				enemy1.nextEnemy();
				if(duration>1499)
					duration-=(duration/3);
				else
					duration=1000;
				updateDuration();
			}
			if(enemy2Exists && touchEnemy(enemy2)){
				enemy2.nextEnemy();
				if(duration>1499)
					duration-=(duration/3);
				else
					duration=1000;
				updateDuration();
			}
		}
	}
	private void otherSide(){
		if(snake.direction.equals("up"))
			snake.headPart.y=C.DOWN_BORDER-C.SIZE;
		else if(snake.direction.equals("right"))
			snake.headPart.x=C.LEFT_BORDER;
		else if(snake.direction.equals("down"))
			snake.headPart.y=C.UP_BORDER;
		else if(snake.direction.equals("left"))
			snake.headPart.x=C.RIGHT_BORDER-C.SIZE;
	}
	private void getRation(){
		updateScore();
		snake.addPart();
		if(isDouble)
			snake.addPart();
		updateMultiplier();
		ration.next();
	}
	private void getItem(Item item){
		if(item instanceof Double){
			doubleT.stop();
			snake.headPart.setIcon(I.SNAKE_DOUBLE);
			isDouble=true;
			doubleT.start();
		}
		else if(item instanceof Invincible){
			invincibleT.stop();
			snake.headPart.setIcon(I.SNAKE_INVINCIBLE);
			isInvincible=true;
			invincibleT.start();
		}
		else if(item instanceof Magnet){
			magnetT.stop();
			snake.headPart.setIcon(I.SNAKE_MAGNET);
			isMagnet=true;
			magnetT.start();
		}
		else if(item instanceof Speed){
			speedT.stop();
			snake.headPart.setIcon(I.SNAKE_SPEED);
			isSpeed=true;
			moveT.setDelay((byte)(speed*.5));
			speedT.start();
		}
		else if(item instanceof GodMode){
			snake.headPart.setIcon(I.GOD_MODE);
			doubleT.stop();
			invincibleT.stop();
			magnetT.stop();
			speedT.stop();
			isDouble=true;
			isInvincible=true;
			isMagnet=true;
			isSpeed=true;
			doubleT.start();
			invincibleT.start();
			magnetT.start();
			moveT.setDelay((byte)(speed*.5));
			speedT.start();
		}
		background.remove(item);
		itemList.remove(item);
		item.setIcon(null);
		item=null;
	}
	private boolean touchEnemy(Enemy enemy){
		if(snake.headPart.x==enemy.headPart.x && snake.headPart.y==enemy.headPart.y)
			return true;
		return false;
	}
	private void gameOver(){
		moveT.stop();
		itemT.stop();
		snake.pause();
		snake.headPart.removeKeyListener(snake);
		((SFrame)background.getParent().getParent().getParent().getParent()).sortHS();
		if(snake.gameMode==I.S_SUPER_SNAKE && score>((SFrame)background.getParent().getParent().getParent().getParent()).hSScore[9])
			((SFrame)background.getParent().getParent().getParent().getParent()).highScore.setVisible(true);
		((SFrame)background.getParent().getParent().getParent().getParent()).gameOver.setVisible(true);
		((SFrame)background.getParent().getParent().getParent().getParent()).highScore.toFront();
	}
	public void reset(){
		snake.die();
		score=0;
		updateScore();
		duration=1000;
		updateDuration();
		speed=110;
		updateSpeed();
		moveT.stop();
		moveT.setDelay(speed);
		itemT.stop();
		ration.x=-C.SIZE;
		ration.y=-C.SIZE;
		while(itemList.size()>0){
			Item item=itemList.get(0);
			itemList.remove(item);
			item.setIcon(null);
			item=null;
		}
		if(enemy1Exists){
			enemy1.die();
			enemy1=null;
			enemy1Exists=false;
		}
		if(enemy2Exists){
			enemy2.die();
			enemy2=null;
			enemy2Exists=false;
		}
		isDouble=false;
		isInvincible=false;
		isMagnet=false;
		isSpeed=false;
	}
	public void mainMenu(){
		scoreL.setVisible(false);
		multiplierL.setVisible(false);
		durationL.setVisible(false);
		consumeL.setVisible(false);
		reset();
		((SFrame)background.getParent().getParent().getParent().getParent()).bPauseMainMenu.setVisible(false);
		((SFrame)background.getParent().getParent().getParent().getParent()).setTitle();
		((SFrame)background.getParent().getParent().getParent().getParent()).play(((SFrame)background.getParent().getParent().getParent().getParent()).a2);
	}
	public void startSuperSnake(){
		durationL.setBounds(435, 565, 1000, 20);
		((SFrame)background.getParent().getParent().getParent().getParent()).demo.stop();
		background.setIcon(I.S_SUPER_SNAKE);
		((SFrame)background.getParent().getParent().getParent().getParent()).bMute.setVisible(false);
		snake.spawn();
		updateMultiplier();
		background.add(ration);
		ration.next();
		moveT.start();
		itemT.start();
		scoreL.setVisible(true);
		multiplierL.setVisible(true);
		durationL.setVisible(true);
		consumeL.setVisible(true);
		((SFrame)background.getParent().getParent().getParent().getParent()).play(null);
	}
	public void startClassicSnake(){
		durationL.setBounds(435, 565, 1000, 20);
		((SFrame)background.getParent().getParent().getParent().getParent()).demo.stop();
		background.setIcon(I.S_CLASSIC_SNAKE);
		((SFrame)background.getParent().getParent().getParent().getParent()).bMute.setVisible(false);
		snake.spawn();
		updateMultiplier();
		background.add(ration);
		ration.next();
		moveT.start();
		scoreL.setVisible(true);
		multiplierL.setVisible(true);
		((SFrame)background.getParent().getParent().getParent().getParent()).play(null);
	}
	public void startSandbox(){
		durationL.setBounds(435, 565, 1000, 20);
		((SFrame)background.getParent().getParent().getParent().getParent()).demo.stop();
		background.setIcon(I.S_SANDBOX);
		((SFrame)background.getParent().getParent().getParent().getParent()).bMute.setVisible(false);
		snake.spawn();
		updateMultiplier();
		background.add(ration);
		ration.next();
		moveT.start();
		scoreL.setVisible(true);
		multiplierL.setVisible(true);
		durationL.setVisible(true);
		consumeL.setVisible(true);
		((SFrame)background.getParent().getParent().getParent().getParent()).play(null);
	}
	public void actionPerformed(ActionEvent e){
		if(!snake.direction.equals(snake.newDirection))
			snake.turn();
		Part tailPart=snake.body.get(snake.body.size()-1);
		snake.body.remove(tailPart);
		snake.body.add(1, tailPart);
		tailPart.x=snake.headPart.x;
		tailPart.y=snake.headPart.y;
		if(snake.direction.equals("up"))
			snake.headPart.y-=C.SIZE;
		else if(snake.direction.equals("right"))
			snake.headPart.x+=C.SIZE;
		else if(snake.direction.equals("down"))
			snake.headPart.y+=C.SIZE;
		else if(snake.direction.equals("left"))
			snake.headPart.x-=C.SIZE;
		if(isMagnet){
			if(ration.y<snake.headPart.y)
				ration.y+=C.SIZE;
			else if(ration.y>snake.headPart.y)
				ration.y-=C.SIZE;
			if(ration.x<snake.headPart.x)
				ration.x+=C.SIZE;
			else if(ration.x>snake.headPart.x)
				ration.x-=C.SIZE;
		}
		collision();
		background.repaint();
	}
}