import java.io.*;
public class MazeBuilder
{
	private char[][] maze = new char[18][50];

	public void setBoard()
	{
		File fileName = new File("Maze.txt");
		try
		{
			int row = 0;
			BufferedReader input = new BufferedReader(new FileReader(fileName));
			String text;
			while((text = input.readLine()) != null)
			{
				for(int col = 0; col < text.length(); col++)
					maze[row][col] = text.charAt(col);
				row++;
			}
		}catch(IOException e)
		{
			System.out.println("File not found.");
		}
	}

	public char[][] getMaze()
	{
		return maze;
	}
}