import javax.swing.ImageIcon;
import javax.swing.JLabel;

class Item extends JLabel{
	short x, y;

	public Item(ImageIcon image){
		super(image);
		x=C.randomCoor();
		y=C.randomCoor();
		setBounds(x, y, C.SIZE, C.SIZE);
	}
	public Item(ImageIcon image, boolean demo){
		super(image);
		x=C.xDemoCoor();
		y=C.yDemoCoor();
		setBounds(x, y, C.SIZE, C.SIZE);
	}
}

class Double extends Item{
	public Double(){
		super(I.DOUBLE);
	}
	public Double(boolean demo){
		super(I.DOUBLE, demo);
	}
}
class Invincible extends Item{
	public Invincible(){
		super(I.INVINCIBLE);
	}
	public Invincible(boolean demo){
		super(I.INVINCIBLE, demo);
	}
}
class Magnet extends Item{
	public Magnet(){
		super(I.MAGNET);
	}
	public Magnet(boolean demo){
		super(I.MAGNET, demo);
	}
}
class Speed extends Item{
	public Speed(){
		super(I.SPEED);
	}
	public Speed(boolean demo){
		super(I.SPEED, demo);
	}
}
class GodMode extends Item{
	public GodMode(){
		super(I.GOD_MODE);
	}
	public GodMode(boolean demo){
		super(I.GOD_MODE, demo);
	}
}