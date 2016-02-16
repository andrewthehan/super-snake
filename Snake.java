import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.Timer;

class Snake extends JComponent implements KeyListener{
	JLabel background;
	ImageIcon gameMode;
	ArrayList<Part> body;
	Part headPart;
	String direction, newDirection;

	public Snake(JLabel background){
		this.background=background;
		body=new ArrayList();
		headPart=null;
		direction="";
		newDirection="";
	}
	public void spawn(){
		gameMode=(ImageIcon)background.getIcon();
		headPart=new Part((short)300, (short)300, I.PART_HEAD);
		body.add(headPart);
		background.add(headPart);

		Part part=new Part(headPart.x, (short)(headPart.y+C.SIZE), I.PART_SNAKE);
		body.add(part);
		background.add(part);

		addPart();
		addPart();
		addPart();

		direction="up";
		newDirection="up";

		headPart.addKeyListener(this);
		headPart.requestFocus();
	}
	public void die(){
		while(body.size()>0){
			Part part=body.get(0);
			background.remove(part);
			body.remove(part);
			part.setIcon(null);
			part=null;
		}
		headPart=null;

		direction="";
		newDirection="";
	}
	public void addPart(){
		Part lastPart=body.get(body.size()-1);
		Part secondToLastPart=body.get(body.size()-2);
		Part part=new Part((short)(2*lastPart.x-secondToLastPart.x), (short)(2*lastPart.y-secondToLastPart.y), I.PART_SNAKE);
		body.add(part);
		background.add(part);
	}
	private void consume(){
		((SFrame)background.getParent().getParent().getParent().getParent()).controller.duration+=50*(body.size()-5);
		while(body.size()>5){
			Part part=body.get(body.size()-1);
			background.remove(part);
			body.remove(part);
			part.setIcon(null);
			part=null;
		}
		((SFrame)background.getParent().getParent().getParent().getParent()).controller.updateMultiplier();
		((SFrame)background.getParent().getParent().getParent().getParent()).controller.updateDuration();
		((SFrame)background.getParent().getParent().getParent().getParent()).controller.updateSpeed();
	}
	public void turn(){
		direction=(newDirection.equals("up") && !direction.equals("down"))?newDirection:
					(newDirection.equals("right") && !direction.equals("left"))?newDirection:
					(newDirection.equals("down") && !direction.equals("up"))?newDirection:
					(newDirection.equals("left") && !direction.equals("right"))?newDirection:
					direction;
	}
	public void pause(){
		if(background.getIcon()==I.S_PAUSE){
			background.setIcon(gameMode);
			((SFrame)background.getParent().getParent().getParent().getParent()).controller.moveT.start();
			((SFrame)background.getParent().getParent().getParent().getParent()).controller.itemT.start();
			if(((SFrame)background.getParent().getParent().getParent().getParent()).controller.enemy1Exists)
				((SFrame)background.getParent().getParent().getParent().getParent()).controller.enemy1.moveT.start();
			if(((SFrame)background.getParent().getParent().getParent().getParent()).controller.enemy2Exists)
				((SFrame)background.getParent().getParent().getParent().getParent()).controller.enemy2.moveT.start();
			((SFrame)background.getParent().getParent().getParent().getParent()).controller.durationL.setBounds(435, 565, 1000, 20);
			((SFrame)background.getParent().getParent().getParent().getParent()).bMute.setVisible(false);
		}
		else{
			background.setIcon(I.S_PAUSE);
			((SFrame)background.getParent().getParent().getParent().getParent()).controller.moveT.stop();
			((SFrame)background.getParent().getParent().getParent().getParent()).controller.itemT.stop();
			if(((SFrame)background.getParent().getParent().getParent().getParent()).controller.enemy1Exists)
				((SFrame)background.getParent().getParent().getParent().getParent()).controller.enemy1.moveT.stop();
			if(((SFrame)background.getParent().getParent().getParent().getParent()).controller.enemy2Exists)
				((SFrame)background.getParent().getParent().getParent().getParent()).controller.enemy2.moveT.stop();
			((SFrame)background.getParent().getParent().getParent().getParent()).controller.durationL.setBounds(403, 565, 1000, 20);
			((SFrame)background.getParent().getParent().getParent().getParent()).bMute.setVisible(true);
		}
	}
	public void keyPressed(KeyEvent e){
		int key=e.getKeyCode();
		newDirection=(key==KeyEvent.VK_UP)?"up":
						(key==KeyEvent.VK_RIGHT)?"right":
						(key==KeyEvent.VK_DOWN)?"down":
						(key==KeyEvent.VK_LEFT)?"left":
						"";
		if(key==KeyEvent.VK_SPACE && body.size()>5 && background.getIcon()!=I.S_CLASSIC_SNAKE)
			consume();
		else if(key==KeyEvent.VK_P){
			if(background.getIcon()==I.S_PAUSE)
				((SFrame)background.getParent().getParent().getParent().getParent()).bPauseMainMenu.setVisible(false);
			else
				((SFrame)background.getParent().getParent().getParent().getParent()).bPauseMainMenu.setVisible(true);
			pause();
		}
		else if(background.getIcon()==I.S_SANDBOX){
			if(key==KeyEvent.VK_F1){
				Item itemD=new Double();
				background.add(itemD);
				((SFrame)background.getParent().getParent().getParent().getParent()).controller.itemList.add(itemD);
			}
			else if(key==KeyEvent.VK_F2){
				Item itemI=new Invincible();
				background.add(itemI);
				((SFrame)background.getParent().getParent().getParent().getParent()).controller.itemList.add(itemI);
			}
			else if(key==KeyEvent.VK_F3){
				Item itemM=new Magnet();
				background.add(itemM);
				((SFrame)background.getParent().getParent().getParent().getParent()).controller.itemList.add(itemM);
			}
			else if(key==KeyEvent.VK_F4){
				Item itemS=new Speed();
				background.add(itemS);
				((SFrame)background.getParent().getParent().getParent().getParent()).controller.itemList.add(itemS);
			}
			else if(key==KeyEvent.VK_F5){
				Item itemGM=new GodMode();
				background.add(itemGM);
				((SFrame)background.getParent().getParent().getParent().getParent()).controller.itemList.add(itemGM);
			}
		}
	}
	public void keyReleased(KeyEvent e){
	}
	public void keyTyped(KeyEvent e){
	}
}