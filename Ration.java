import javax.swing.JLabel;

class Ration extends JLabel{
	short x, y;

	public Ration(){
		super(I.RATION);
		x=-C.SIZE;
		y=-C.SIZE;
		setBounds(x, y, C.SIZE, C.SIZE);
	}
	public void next(){
		x=C.randomCoor();
		y=C.randomCoor();
	}
	public void next(boolean demo){
		x=C.xDemoCoor();
		y=C.yDemoCoor();
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
}