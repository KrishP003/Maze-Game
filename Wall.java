import java.awt.Color;
import java.awt.Polygon;
public class Wall
{

	private int[] rowLocs;
	private int[] colLocs;
	private int size;
	private Color color;

	private boolean leftCeiling = false; //Lc Triangle
	private boolean rightCeiling = false; //Rc Triangle

	private boolean leftWall = false; //Lw Trapezoid
	private boolean rightWall = false; //Rw Trapezoid

	private boolean leftPath = false; //Lp Rectangle
	private boolean rightPath = false; //Rp Rectangle

	private boolean leftFloor = false; //Lf Triangle
	private boolean rightFloor = false; //Rf Triangle

	private boolean ceiling = false;
	private boolean floor = false;
	private boolean front = false;


	public Wall(int[] rLocs, int[] cLocs, int s, String type, int R, int G, int B)
	{
		rowLocs = rLocs;
		colLocs = cLocs;
		size = s;
		color = new Color(R,G,B);
		if(type.equals("Lc"))
			leftCeiling = true;
		else if(type.equals("Rc"))
			rightCeiling = true;
		else if(type.equals("L"))
			leftWall = true;
		else if(type.equals("R"))
			rightWall = true;
		else if(type.equals("Lp"))
			leftPath = true;
		else if(type.equals("Rp"))
			rightPath = true;
		else if(type.equals("Lf"))
			leftFloor = true;
		else if(type.equals("Rf"))
			rightFloor = true;
		else if(type.equals("C"))
			ceiling = true;
		else if(type.equals("FL"))
			floor = true;
		else if(type.equals("F"))
			front = true;
	}

	public Polygon getPolygon()
	{
		Polygon poly = new Polygon(colLocs, rowLocs, rowLocs.length);
		return poly;
	}
	public int getSize()
	{
		return size;
	}

	public Color getColor()
	{
		return color;
	}
}