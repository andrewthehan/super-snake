import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.Timer;

class DemoSnake implements ActionListener{
	JLabel background;
	Part headPart;
	ArrayList<Item> itemList;
	ArrayList<Part> body;
	Timer moveT, itemT, doubleT, invincibleT, magnetT, speedT;
	byte speed;
	boolean moved, isDouble, isInvincible, isMagnet, isSpeed;
	Ration ration;

	public DemoSnake(JLabel background){
		this.background=background;
		speed=50;
		moveT=new Timer(speed, this);
		constructItem();

		body=new ArrayList();
		moved=false;

		ration=new Ration();
		background.add(ration);
	}
	private void constructItem(){
		itemList=new ArrayList();
		itemT=new Timer(0, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				short type=C.random((short)1, (short)100);
				Item item=(type>=1 && type<=5)?new GodMode(true): //5%
							(type>=6 && type<=35)?new Speed(true): //30%
							(type>=36 && type<=65)?new Double(true): //30%
							(type>=66 && type<=80)?new Invincible(true): //15%
							(type>=81 && type<=100)?new Magnet(true): //20%
							null;
				background.add(item);
				itemList.add(item);

				if(itemT.getDelay()==5000 && itemList.size()>5){
					Item firstItem=itemList.get(0);
					background.remove(firstItem);
					itemList.remove(firstItem);
					firstItem.setIcon(null);
					firstItem=null;
				}
			}
		});
		doubleT=new Timer(1000, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				isDouble=false;
				if(!isInvincible && !isMagnet && !isSpeed)
					headPart.setIcon(I.PART_HEAD);
				doubleT.stop();
			}
		});
		invincibleT=new Timer(1000, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				isInvincible=false;
				if(!isDouble && !isMagnet && !isSpeed)
					headPart.setIcon(I.PART_HEAD);
				invincibleT.stop();
			}
		});
		magnetT=new Timer(1000, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				isMagnet=false;
				if(!isDouble && !isInvincible && !isSpeed)
					headPart.setIcon(I.PART_HEAD);
				magnetT.stop();
			}
		});
		speedT=new Timer(1000, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				isSpeed=false;
				if(!isDouble && !isInvincible && !isMagnet)
					headPart.setIcon(I.PART_HEAD);
				moveT.setDelay(speed);
				speedT.stop();
			}
		});
	}
	private void start(boolean hover, short item){
		moveT.setDelay(speed*(hover?1:2));
		if(item!=0){
			itemT.setDelay(item);
			itemT.start();
		}
		spawn();
		ration.next(true);
	}
	public void startGeneric(){
		start(false, (short)0);
	}
	public void startSuper(){
		start(true, (short)5000);
	}
	public void startClassic(){
		start(true, (short)0);
	}
	public void startSandbox(){
		start(true, (short)1000);
	}
	public void stop(){
		ration.x=-C.SIZE;
		ration.y=-C.SIZE;
		die();
		itemT.stop();
		while(itemList.size()>0){
			Item item=itemList.get(0);
			itemList.remove(item);
			item.setIcon(null);
			item=null;
		}
	}
	private void addPart(){
		Part part=new Part(headPart.x, headPart.y, I.PART_SNAKE);
		body.add(part);
		background.add(part);
	}
	private void getItem(Item item){
		if(item instanceof Double){
			doubleT.stop();
			headPart.setIcon(I.SNAKE_DOUBLE);
			isDouble=true;
			doubleT.start();
		}
		else if(item instanceof Invincible){
			invincibleT.stop();
			headPart.setIcon(I.SNAKE_INVINCIBLE);
			isInvincible=true;
			invincibleT.start();
		}
		else if(item instanceof Magnet){
			magnetT.stop();
			headPart.setIcon(I.SNAKE_MAGNET);
			isMagnet=true;
			magnetT.start();
		}
		else if(item instanceof Speed){
			speedT.stop();
			headPart.setIcon(I.SNAKE_SPEED);
			isSpeed=true;
			moveT.setDelay((byte)(speed*.5));
			speedT.start();
		}
		else if(item instanceof GodMode){
			headPart.setIcon(I.GOD_MODE);
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
	private void collision(){
		if(ration.x==headPart.x && ration.y==headPart.y)
			ration.next(true);
		for(byte i=0; i<itemList.size(); i++)
			if(itemList.get(i).x==headPart.x && itemList.get(i).y==headPart.y)
				getItem(itemList.get(i));
	}
	private void spawn(){
		headPart=new Part(C.xDemoCoor(), C.yDemoCoor(), I.PART_HEAD);
		body.add(headPart);
		background.add(headPart);
		addPart();
		addPart();
		addPart();
		addPart();
		moveT.start();
	}
	private void die(){
		moveT.stop();
		while(body.size()>0){
			Part part=body.get(0);
			background.remove(part);
			body.remove(part);
			part.setIcon(null);
			part=null;
		}
		headPart=null;
		background.repaint();
	}
	public void actionPerformed(ActionEvent e){
		moved=false;
		Part tailPart=body.get(4);
		body.remove(tailPart);
		body.add(1, tailPart);
		tailPart.x=headPart.x;
		tailPart.y=headPart.y;
		if(C.random((short)0, (short)1)==0){
			if(ration.x>headPart.x){
				headPart.x+=C.SIZE;
				moved=true;
			}
			else if(ration.x<headPart.x){
				headPart.x-=C.SIZE;
				moved=true;
			}
		}
		if(!moved){
			if(ration.y>headPart.y)
				headPart.y+=C.SIZE;
			else if(ration.y<headPart.y)
				headPart.y-=C.SIZE;
			else if(ration.x>headPart.x)
				headPart.x+=C.SIZE;
			else if(ration.x<headPart.x)
				headPart.x-=C.SIZE;
		}
		background.repaint();
		if(isMagnet){
			if(ration.y<headPart.y)
				ration.y+=C.SIZE;
			else if(ration.y>headPart.y)
				ration.y-=C.SIZE;
			if(ration.x<headPart.x)
				ration.x+=C.SIZE;
			else if(ration.x>headPart.x)
				ration.x-=C.SIZE;
		}
		collision();
	}
}