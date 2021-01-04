import java.util.Scanner; // have to import Scanner to use it

// Maze runner class to run the maze
public class MazeRunner {

	public static void main(String args[])
	{
		// ints to hold the number of rows and columns in the maze
		int rows = 0;
		int columns = 0;
		
		// making a scanner to take in the rows and columns from the user
		Scanner scan = new Scanner(System.in);
		
		// prompting the user for and taking in the number of rows
		System.out.print("Enter the number of desired rows: ");
		rows = scan.nextInt();
		
		// prompting the user for and taking in the number of columns
		System.out.print("Enter the number of desired columns: ");
		columns = scan.nextInt();
		
		// making the maze using the user specified rows and columns
		Maze testMaze = new Maze(rows,columns);
		
		// calling the create maze method
		testMaze.createMaze();
		
		// printing the maze
		testMaze.print();
	}
}
