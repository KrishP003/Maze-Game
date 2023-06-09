import javax.swing.*;
import java.awt.event.*; //Allows movement using key
import java.util.ArrayList;
import java.awt.Polygon;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MazeProject extends JPanel implements KeyListener
{
	JFrame frame;
	int col;
	int row;
	int size = 20;
	int shrink = 50;
	int dir = 0;
	boolean draw3D = false;
	char[][] m;
	Explorer hero;
	ArrayList<Wall> walls;
	boolean flashlight = false;

	public MazeProject()
	{

		MazeBuilder maze = new MazeBuilder();
		m = maze.getMaze();
		col = 15;
		row = 16;
		maze.setBoard();
		if(draw3D)
		{
			FloorsAndCeilings();
			createWalls();
		}
		hero = new Explorer(new Location(row,col), dir, size, Color.RED);
		frame = new JFrame("Maze Program");
		frame.add(this);
		frame.setSize(1200,950);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.addKeyListener(this);
	}


	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); //giant eraser
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.GRAY);
		g2.fillRect(0,0,frame.getWidth(),frame.getHeight());
		Graphics2D winnerStatement = (Graphics2D)g;

		if(hero.getLoc().getRow() == 13 && hero.getLoc().getCol() == 49)
		{
			winnerStatement.setFont(new Font("Century", Font.BOLD, 60));
			winnerStatement.setColor(Color.WHITE);
			winnerStatement.drawString("You Escaped the Maze!", 250, 300);
			return;
		}
		else
		{
			if(!draw3D)
			{
				Graphics2D instructions2D = (Graphics2D)g;
				instructions2D.setFont(new Font("Century", Font.PLAIN, 25));
				instructions2D.setColor(Color.WHITE);
				instructions2D.drawString("Use Arrows to move", 25, 410);
				instructions2D.drawString("Press Space to go to 3D", 26, 440);

				g2.setColor(Color.DARK_GRAY);
				for(int c = 0; c < m[0].length; c++)
					for(int r = 0; r < m.length; r++)
						if(m[r][c] == ' ')
							g2.fillRect(c * size + size, r * size + size, size, size);
						else
							g2.drawRect(c * size + size, r * size + size, size, size);
				g2.setColor(hero.getColor());
				g2.fill(hero.getRect());

				BufferedImage north2D, east2D, south2D, west2D;
				if(hero.getDir() == 0)
				{
					try
					{
						north2D = ImageIO.read(getClass().getResourceAsStream("NorthCompass.png"));
						g.drawImage(north2D, 250, 390, null);
					}
					catch(IOException e)
					{
					}
				}
				else if(hero.getDir() == 1)
				{
					try
					{
						east2D = ImageIO.read(getClass().getResourceAsStream("EastCompass.png"));
						g.drawImage(east2D, 250, 390, null);
					}
					catch(IOException e)
					{
					}
				}
				else if(hero.getDir() == 2)
				{
					try
					{
						south2D = ImageIO.read(getClass().getResourceAsStream("SouthCompass.png"));
						g.drawImage(south2D, 250, 390, null);
					}
					catch(IOException e)
					{
					}
				}
				else if(hero.getDir() == 3)
				{
					try
					{
						west2D = ImageIO.read(getClass().getResourceAsStream("WestCompass.png"));
						g.drawImage(west2D, 250, 390, null);
					}
					catch(IOException e)
					{
					}
				}
			}
			else
			{
				Graphics2D instructions3D = (Graphics2D)g;
				instructions3D.setFont(new Font("Century", Font.PLAIN, 25));
				instructions3D.setColor(Color.WHITE);
				instructions3D.drawString("Use Arrows to move", 855, 410);
				instructions3D.setFont(new Font("Century", Font.PLAIN, 24));
				instructions3D.drawString("Press Space to go back to 2D", 855, 440);
				instructions3D.setFont(new Font("Century", Font.PLAIN, 25));
				instructions3D.drawString("Press F for flashlight", 855, 470);

				for(Wall wall : walls)
				{
					g2.setPaint(wall.getColor());
					g2.fillPolygon(wall.getPolygon());
					g2.setColor(Color.BLACK);
					g2.drawPolygon(wall.getPolygon());
				}

				BufferedImage north3D, east3D, south3D, west3D;
				if(hero.getDir() == 0)
				{
					try
					{
						north3D = ImageIO.read(getClass().getResourceAsStream("NorthCompass.png"));
						g.drawImage(north3D, 855, 510, null);
					}
					catch(IOException e)
					{
					}
				}
				else if(hero.getDir() == 1)
				{
					try
					{
						east3D = ImageIO.read(getClass().getResourceAsStream("EastCompass.png"));
						g.drawImage(east3D, 855, 510, null);
					}
					catch(IOException e)
					{
					}
				}
				else if(hero.getDir() == 2)
				{
					try
					{
						south3D = ImageIO.read(getClass().getResourceAsStream("SouthCompass.png"));
						g.drawImage(south3D, 855, 510, null);
					}
					catch(IOException e)
					{
					}
				}
				else if(hero.getDir() == 3)
				{
					try
					{
						west3D = ImageIO.read(getClass().getResourceAsStream("WestCompass.png"));
						g.drawImage(west3D, 855, 510, null);
					}
					catch(IOException e)
					{
					}
				}
			}
		}
	}

	public void FloorsAndCeilings()
	{
		int heroR = hero.getLoc().getRow();
		int heroC = hero.getLoc().getCol();
		int heroD = hero.getDir();
		int dis = 4;

		if(flashlight)
			dis = 5;

		switch(heroD)
		{
			case 0:
				try
				{
					for(int n = 0; n < dis; n++)
					{
						if(m[heroR - n][heroC] != '*')
						{
							walls.add(getCeiling(n));
							walls.add(getFloor(n));
						}
						else
							n = 7;
					}
				}catch(ArrayIndexOutOfBoundsException e)
				{
				}
			break;
			case 1:
				try
				{
					for(int n = 0; n < dis; n++)
					{
						if(m[heroR][heroC + n] != '*')
						{
							walls.add(getCeiling(n));
							walls.add(getFloor(n));
						}
						else
							n = 7;
					}
				}catch(ArrayIndexOutOfBoundsException e)
				{
				}
			break;
			case 2:
				try
				{
					for(int n = 0; n < dis; n++)
					{
						if(m[heroR + n][heroC] != '*')
						{
							walls.add(getCeiling(n));
							walls.add(getFloor(n));
						}
						else
							n = 7;
					}
				}catch(ArrayIndexOutOfBoundsException e)
				{
				}
			break;
			case 3:
				try
				{
					for(int n = 0; n < dis; n++)
					{
						if(m[heroR][heroC - n] != '*')
						{
							walls.add(getCeiling(n));
							walls.add(getFloor(n));
						}
						else
							n = 7;
					}
				}catch(ArrayIndexOutOfBoundsException e)
				{
				}
			break;
		}
	}

	public void createWalls()
	{
		walls = new ArrayList<Wall>();

		int hRow = hero.getLoc().getRow();
		int hCol = hero.getLoc().getCol();
		int hDir = hero.getDir();
		int distance = 4;

		if(flashlight)
			distance = 5;

		boolean stop = false;

		switch(hDir)
		{
			//Up
			case 0:
				for(int n = 0; n < distance; n++)
				{
					try
					{
						if(m[hRow - n - 1][hCol] == '*')
						{
							walls.add(front(n));
							stop = true;
						}
						else if(m[hRow - n - 1][hCol] != '*' && (distance - n) == 1)
						{
							walls.add(front(4));
						}

						if(m[hRow - n][hCol - 1] == '*')
						{
							walls.add(getLeft(n));
						}
						else
						{
							walls.add(getLeftPath(n));
							walls.add(getFloorLeft(n));
							walls.add(getCeilingLeft(n));
						}


						if(m[hRow - n][hCol + 1] == '*')
						{
							walls.add(getRight(n));
						}
						else
						{
							walls.add(getRightPath(n));
							walls.add(getFloorRight(n));
							walls.add(getCeilingRight(n));
						}

						if(stop)
							break;

					}catch(ArrayIndexOutOfBoundsException e)
					{
					}
				}
				break;
			//Right
			case 1:
				for(int n = 0; n < distance; n++)
				{
					try
					{
						if(m[hRow][hCol + n + 1] == '*')
						{
							walls.add(front(n));
							stop = true;
						}
						else if(m[hRow][hCol + n + 1] != '*' && (distance - n) == 1)
						{
							walls.add(front(4));
						}

						if(m[hRow - 1][hCol + n] == '*')
						{
							walls.add(getLeft(n));
						}
						else
						{
							walls.add(getLeftPath(n));
							walls.add(getFloorLeft(n));
							walls.add(getCeilingLeft(n));
						}

						if(m[hRow + 1][hCol + n] == '*')
						{
							walls.add(getRight(n));
						}
						else
						{
							walls.add(getRightPath(n));
							walls.add(getFloorRight(n));
							walls.add(getCeilingRight(n));
						}

						if(stop)
							break;

					}catch(ArrayIndexOutOfBoundsException e)
					{
					}
				}
				break;
			//Down
			case 2:
				for(int n = 0; n < distance; n++)
				{
					try
					{
						if(m[hRow + n + 1][hCol] == '*')
						{
							walls.add(front(n));
							stop = true;
						}
						else if(m[hRow + n + 1][hCol] != '*' && (distance - n) == 1)
						{
							walls.add(front(4));
						}

						if(m[hRow + n][hCol + 1] == '*')
						{
							walls.add(getLeft(n));
						}
						else
						{
							walls.add(getLeftPath(n));
							walls.add(getFloorLeft(n));
							walls.add(getCeilingLeft(n));
						}

						if(m[hRow + n][hCol - 1] == '*')
						{
							walls.add(getRight(n));
						}
						else
						{
							walls.add(getRightPath(n));
							walls.add(getFloorRight(n));
							walls.add(getCeilingRight(n));
						}

						if(stop)
							break;

					}catch(ArrayIndexOutOfBoundsException e)
					{
					}
				}
				break;
			//Left
			case 3:
				for(int n = 0; n < distance; n++)
				{
					try
					{
						if(m[hRow][hCol - n - 1] == '*')
						{
							walls.add(front(n));
							stop = true;
						}
						else if(m[hRow][hCol - n - 1] != '*' && (distance - n) == 1)
						{
							walls.add(front(4));
						}

						if(m[hRow + 1][hCol - n] == '*')
						{
							walls.add(getLeft(n));
						}
						else
						{
							walls.add(getLeftPath(n));
							walls.add(getFloorLeft(n));
							walls.add(getCeilingLeft(n));
						}

						if(m[hRow - 1][hCol - n] == '*')
						{
							walls.add(getRight(n));
						}
						else
						{
							walls.add(getRightPath(n));
							walls.add(getFloorRight(n));
							walls.add(getCeilingRight(n));
						}

						if(stop)
							break;

					}catch(ArrayIndexOutOfBoundsException e)
					{
					}
				}
				break;
		}
	}

	//Ceilings
	public Wall getCeiling(int n)
	{
		int[] rowCeiling = {100 + shrink * n, 150 + shrink * n, 150 + shrink * n, 100 + shrink * n};
		int[] colCeiling = {100 + shrink * n, 150 + shrink * n, 800 - shrink * n, 850 - shrink * n};
		if(!flashlight)
			return new Wall(rowCeiling, colCeiling, shrink, "C", 200 - shrink * n, 200 - shrink * n, 200 - shrink * n);
		else
			return new Wall(rowCeiling, colCeiling, shrink, "C", 200 - 20 * n, 200 - 20 * n, 200 - 20 * n);
	}

	//Floor
	public Wall getFloor(int n)
	{
		int[] rowFloor = {700 - shrink * n, 650 - shrink * n, 650 - shrink * n, 700 - shrink * n};
		int[] colFloor = {100 + shrink * n, 150 + shrink * n, 800 - shrink * n, 850 - shrink * n};
		if(!flashlight)
			return new Wall(rowFloor, colFloor, shrink, "FL", 200 - shrink * n, 200 - shrink * n, 200 - shrink * n);
		else
			return new Wall(rowFloor, colFloor, shrink, "FL", 200 - 20 * n, 200 - 20 * n, 200 - 20 * n);
	}

	//Front
	public Wall front(int n)
	{
		int[] rowFloor = {100 + shrink * n, 700 - shrink * n, 700 - shrink * n, 100 + shrink * n};
		int[] colFloor = {100 + shrink * n, 100 + shrink * n, 850 - shrink * n, 850 - shrink * n};
		if(!flashlight)
			return new Wall(rowFloor, colFloor, shrink, "F", 200 - shrink * n, 200 - shrink * n, 200 - shrink * n);
		else
			return new Wall(rowFloor, colFloor, shrink, "F", 200 - 20 * n, 200 - 20 * n, 200 - 20 * n);
	}










	//Left Side

	//Trapezoid
	public Wall getLeft(int n)
	{
		int[] rowL = {100 + shrink * n, 150 + shrink * n, 650 - shrink * n, 700 - shrink * n};
		int[] colL = {100 + shrink * n, 150 + shrink * n, 150 + shrink * n, 100 + shrink * n};
		if(!flashlight)
			return new Wall(rowL, colL, shrink, "L", 200 - shrink * n, 200 - shrink * n, 200 - shrink * n);
		else
			return new Wall(rowL, colL, shrink, "L", 200 - 20 * n, 200 - 20 * n, 200 - 20 * n);
	}

	//Rectangle
	public Wall getLeftPath(int n)
	{
		int[] rowLPath = {150 + shrink * n, 150 + shrink * n, 650 - shrink * n, 650 - shrink * n};
		int[] colLPath = {100 + shrink * n, 150 + shrink * n, 150 + shrink * n, 100 + shrink * n};
		if(!flashlight)
			return new Wall(rowLPath, colLPath, shrink, "Lp", 200 - shrink * n, 200 - shrink * n, 200 - shrink * n);
		else
			return new Wall(rowLPath, colLPath, shrink, "Lp", 200 - 20 * n, 200 - 20 * n, 200 - 20 * n);
	}

	//Top Triangle
	public Wall getCeilingLeft(int n)
	{
		int[] rowLCeiling = {100 + shrink * n, 150 + shrink * n, 150 + shrink * n};
		int[] colLCeiling = {100 + shrink * n, 150 + shrink * n, 100 + shrink * n};
		if(!flashlight)
			return new Wall(rowLCeiling, colLCeiling, shrink, "Lc", 200 - shrink * n, 200 - shrink * n, 200 - shrink * n);
		else
			return new Wall(rowLCeiling, colLCeiling, shrink, "Lc", 200 - 20 * n, 200 - 20 * n, 200 - 20 * n);
	}

	// Bottom Triangle
	public Wall getFloorLeft(int n)
	{
		int[] rowLFloor = {700 - shrink * n, 650 - shrink * n, 650 - shrink * n};
		int[] colLFloor = {100 + shrink * n, 100 + shrink * n, 150 + shrink * n};
		if(!flashlight)
			return new Wall(rowLFloor, colLFloor, shrink, "Lf", 200 - shrink * n, 200 - shrink * n, 200 - shrink * n);
		else
			return new Wall(rowLFloor, colLFloor, shrink, "Lf", 200 - 20 * n, 200 - 20 * n, 200 - 20 * n);
	}






	//Right Side

	//Trapezoid
	public Wall getRight(int n)
	{
		int[] rowR = {100 + shrink * n, 150 + shrink * n, 650 - shrink * n, 700 - shrink * n};
		int[] colR = {850 - shrink * n, 800 - shrink * n, 800 - shrink * n, 850 - shrink * n};
		if(!flashlight)
			return new Wall(rowR, colR, shrink, "R", 200 - shrink * n, 200 - shrink * n, 200 - shrink * n);
		else
			return new Wall(rowR, colR, shrink, "R", 200 - 20 * n, 200 - 20 * n, 200 - 20 * n);
	}

	//Rectangle
	public Wall getRightPath(int n)
	{
		int[] rowRPath = {150 + shrink * n, 150 + shrink * n, 650 - shrink * n, 650 - shrink * n};
		int[] colRPath = {850 - shrink * n, 800 - shrink * n, 800 - shrink * n, 850 - shrink * n};
		if(!flashlight)
			return new Wall(rowRPath, colRPath, shrink, "Rp", 200 - shrink * n, 200 - shrink * n, 200 - shrink * n);
		else
			return new Wall(rowRPath, colRPath, shrink, "Rp", 200 - 20 * n, 200 - 20 * n, 200 - 20 * n);
	}

	//Top Triangle
	public Wall getCeilingRight(int n)
	{
		int[] rowRCeiling = {100 + shrink * n, 150 + shrink * n, 150 + shrink * n};
		int[] colRCeiling = {850 - shrink * n, 800 - shrink * n, 850 - shrink * n};
		if(!flashlight)
			return new Wall(rowRCeiling, colRCeiling, shrink, "Rc", 200 - shrink * n, 200 - shrink * n, 200 - shrink * n);
		else
			return new Wall(rowRCeiling, colRCeiling, shrink, "Rc", 200 - 20 * n, 200 - 20 * n, 200 - 20 * n);
	}

	//Bottom Triangle
	public Wall getFloorRight(int n)
	{
		int[] rowRFloor = {700 - shrink * n, 650 - shrink * n, 650 - shrink * n};
		int[] colRFloor = {850 - shrink * n, 850 - shrink * n, 800 - shrink * n};
		if(!flashlight)
			return new Wall(rowRFloor, colRFloor, shrink, "Rf", 200 - shrink * n, 200 - shrink * n, 200 - shrink * n);
		else
			return new Wall(rowRFloor, colRFloor, shrink, "Rf", 200 - 20 * n, 200 - 20 * n, 200 - 20 * n);
	}


	public void keyPressed(KeyEvent e)
	{
		//up = 38	right = 39	left = 37
		if(e.getKeyCode() == 32)
		{
			draw3D =! draw3D;
		}
		hero.move(e.getKeyCode() , m);

		if(draw3D)
		{
			if(e.getKeyCode() == 70)
			{
				flashlight =! flashlight;
			}
			createWalls();
			FloorsAndCeilings();
		}
		repaint();
	}

	public void keyReleased(KeyEvent e)
	{
	}
	public void keyTyped(KeyEvent e)
	{
	}

	public static void main(String[] args)
	{
		MazeProject app = new MazeProject();
	}
}