public class Location
{
	private int row;
	private int column;

	public Location(int r , int c)
	{
		row = r;
		column = c;
	}

	public int getRow()
	{
		return row;
	}

	public int getCol()
	{
		return column;
	}

	public void setRow(int a)
	{
		row += a;
	}

	public void setCol(int b)
	{
		column += b;
	}
}