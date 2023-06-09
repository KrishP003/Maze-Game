import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Polygon;
public class Explorer
{
	private Location position;
	private int direction; // N = 0, E = 1, S = 2, W = 3
	private int size;
	private Color color;

	public Explorer(Location loc, int dir, int s, Color col)
	{
		position = loc;
		direction = dir;
		size = s;
		color = col;
	}

	public void move(int key , char[][] maze)
	{
		int row = getLoc().getRow();
		int col = getLoc().getCol();

		try
		{
			if(key == 38)
			{
				//Up
				if(direction == 0)
					if(maze[row - 1][col] == ' ')
						getLoc().setRow(-1);
				//Right
				if(direction == 1)
					if(maze[row][col + 1] == ' ')
						getLoc().setCol(1);
				//Down
				if(direction == 2)
					if(maze[row + 1][col] == ' ')
						getLoc().setRow(1);
				//Left
				if(direction == 3)
					if(maze[row][col - 1] == ' ')
						getLoc().setCol(-1);
			}
		}catch(ArrayIndexOutOfBoundsException e)
		{

		}

		if(key == 39)
		{
			if(direction == 3)
				direction = 0;
			else
				direction++;
		}

		if(key == 37)
		{
			if(direction == 0)
				direction = 3;
			else
				direction--;
		}
	}

	public Location getLoc()
	{
		return position;
	}

	public int getSize()
	{
		return size;
	}

	public int getDir()
	{
		return direction;
	}

	public Color getColor()
	{
		return color;
	}

	public Rectangle getRect()
	{
		int row = getLoc().getRow();
		int col = getLoc().getCol();
		return new Rectangle(col * size + size, row * size + size, size, size);
	}
}