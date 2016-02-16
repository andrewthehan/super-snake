import javax.swing.ImageIcon;
import javax.swing.JLabel;

class Part extends JLabel{
	short x, y;

	public Part(short x, short y, ImageIcon image){
		super(image);
		this.x=x;
		this.y=y;
		setBounds(x, y, C.SIZE, C.SIZE);
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
}