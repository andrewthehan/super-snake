import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.Timer;

class Enemy implements ActionListener{
	JLabel background;
	Part headPart;
	ArrayList<Part> body;
	Timer moveT;
	boolean moved;

	public Enemy(JLabel background){
		this.background=background;
		moveT=new Timer(((SFrame)background.getParent().getParent().getParent().getParent()).controller.speed*2, this);
		body=new ArrayList();
		moved=false;
		spawn();
	}
	public void nextEnemy(){
		die();
		spawn();
	}
	private void addPart(){
		Part part=new Part(headPart.x, headPart.y, I.PART_ENEMY);
		body.add(part);
		background.add(part);
	}
	private void spawn(){
		headPart=new Part(C.randomCoor(), C.randomCoor(), I.PART_ENEMY);
		body.add(headPart);
		background.add(headPart);
		addPart();
		addPart();
		addPart();
		addPart();
		moveT.start();
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

		moveT.stop();
	}
	public void actionPerformed(ActionEvent e){
		moved=false;
		Part tailPart=body.get(4);
		body.remove(tailPart);
		body.add(1, tailPart);
		tailPart.x=headPart.x;
		tailPart.y=headPart.y;
		if(C.random((short)0, (short)1)==0){
			if(((SFrame)background.getParent().getParent().getParent().getParent()).controller.snake.headPart.x>headPart.x){
				headPart.x+=C.SIZE;
				moved=true;
			}
			else if(((SFrame)background.getParent().getParent().getParent().getParent()).controller.snake.headPart.x<headPart.x){
				headPart.x-=C.SIZE;
				moved=true;
			}
		}
		if(!moved){
			if(((SFrame)background.getParent().getParent().getParent().getParent()).controller.snake.headPart.y>headPart.y)
				headPart.y+=C.SIZE;
			else if(((SFrame)background.getParent().getParent().getParent().getParent()).controller.snake.headPart.y<headPart.y)
				headPart.y-=C.SIZE;
			else if(((SFrame)background.getParent().getParent().getParent().getParent()).controller.snake.headPart.x>headPart.x)
				headPart.x+=C.SIZE;
			else if(((SFrame)background.getParent().getParent().getParent().getParent()).controller.snake.headPart.x<headPart.x)
				headPart.x-=C.SIZE;
		}
		background.repaint();
	}
}