// Maze class to create a character-based maze
public class Maze {

	// MazeTile class that stores info for each tile
	public class MazeTile
	{
		// create boolean variables to tell if a cell has a west wall or south wall, the north and east wall will be the south and west walls of other cells or manually printed
		private boolean westWall;
		private boolean southWall;
		
		public MazeTile()
		{
			// at the beginning all the maze tiles have all their walls
			westWall = true;
			southWall = true;
		}
		
		// getter(accessor) methods for the west wall and south wall
		public boolean getWestWall()
		{
			return westWall;
		}
		public boolean getSouthWall()
		{
			return southWall;
		}
		
		// setter(mutator) methods for hte west wall and south wall
		public void setWestWall(boolean westWallValue)
		{
			westWall = westWallValue;
		}
		public void setSouthWall(boolean southWallValue)
		{
			southWall = southWallValue;
		}
		
		// print method prints out the west and south wall for the maze tile if it has those
		public void print()
		{
			// print west wall first, if there is one, or else it would be the east border if you printed the south wall first
			if(westWall == true)
				System.out.print("|");
			else
				System.out.print(" ");
			
			// print south wall after printing west wall
			if(southWall == true)
				System.out.print("_");
			else
				System.out.print(" ");
		}
	}
	
	// ints to hold the number of rows and columns in the maze
	private int rows;
	private int columns;
	// int holding the total number of tiles in the maze
	private int totalTiles;
	// array of maze tiles holding the info for all the tiles in the maze
	private MazeTile[] tiles; 
	
	// default constructor
	public Maze()
	{
		// rows, columns, and total tiles will be 0
		rows = 0;
		columns = 0;
		totalTiles = 0;
	}
	
	// overloading constructor
	public Maze(int rows, int columns)
	{
		// rows and columns will be the values passed
		this.rows = rows;
		this.columns = columns;
		
		// total tiles is the number of rows times the number of columns
		totalTiles = rows*columns;
		
		// make an array of MazeTiles to hold the info regarding the walls of each tile
		tiles = new MazeTile[totalTiles]; // initialize the size to the total number of tiles in the maze
		
		// make a new maze tile for each maze tile in the array
		for(int i = 0; i < totalTiles; i++)
		{
			tiles[i] = new MazeTile();
		}
		
		// the beginning of the maze doesn't have a west wall at the top left corner
		tiles[0].setWestWall(false);
		
		// the end of the maze doesn't have a south wall at the bottom right corner
		tiles[totalTiles-1].setSouthWall(false);
	}
	
	// method to create the maze by knocking down walls
	public void createMaze()
	{		
		// create a disjoint set of size totalTiles
		DisjSets disjointSet = new DisjSets(totalTiles);
		
		// int to hold number of different sets since find should return the same thing when all tiles are connected
		int differentSets = 1; // starts at 1 for loop to run at least once
		
		// loop that lasts as long as all tiles aren't connected
		while(differentSets!=0)
		{
			// reset negative finds back to 0
			differentSets = 0;
			
			// loop to find the number of negative finds
			for(int i = 0; i < totalTiles-1; i++)
			{
				if(disjointSet.find(i)!=disjointSet.find(i+1))
				{
					differentSets++;
				}
			}
						
			// all tiles are connected, no need to knock down walls anymore
			if(differentSets==0)
			{
				break;
			}
			
			// if all tiles aren't connected, randomly generate walls to knock down as long as the tiles it's connecting aren't connected
			for(;;)
			{
				int randomTile = (int) (Math.random() * totalTiles); // randomly generates which tile's wall should be knocked down
				int randomWall = (int) (Math.random() * 4); // randomly generates which wall should be knocked down
				// For randomWall:
				// 0 corresponds to the north wall
				// 1 corresponds with the east wall
				// 2 corresponds with the south wall
				// 3 corresponds with the west wall
				
				// if the wall to be knocked down is a border wall, don't knock it down and regenerate a random wall and random tile
				if((randomWall == 0) && randomTile<columns) // north border
				{

				}
				else if(randomWall == 2 && (randomTile>=(totalTiles-columns))) // south border
				{

				}
				else if(randomWall == 1 && (randomTile%columns==(columns-1))) // east border
				{

				}
				else if(randomWall == 3 && (randomTile%columns==0)) // west border
				{

				}
				// else, if the tiles the walls are connecting aren't already connected, knock it down
				else
				{
					// if the wall is the north wall and the tile above the random tile isn't connected with the random tile, knock down the wall and break
					if(randomWall == 0 && disjointSet.find(randomTile)!=disjointSet.find(randomTile-columns))
					{
						// destroy north wall of random tile by destroying south wall of the tile above it, then union the roots of the sets
						tiles[randomTile-columns].setSouthWall(false);
						disjointSet.union(disjointSet.find(randomTile), disjointSet.find(randomTile-columns));
						break;
					}
					// else if the wall is the east wall and the tile to the right of the random tile isn't connected with the random tile, knock down the wall and break
					else if(randomWall == 1 && disjointSet.find(randomTile)!=disjointSet.find(randomTile+1))
					{
						// destroy east wall of random tile by destroying west wall of the tile to the right of it, the union the roots of the sets
						tiles[randomTile+1].setWestWall(false);
						disjointSet.union(disjointSet.find(randomTile), disjointSet.find(randomTile+1));
						break;
					}
					// else if the wall is the south wall and the tile below the random tile isn't connected with the random tile, knock down the wall and break
					else if(randomWall == 2 && disjointSet.find(randomTile)!=disjointSet.find(randomTile+columns))
					{
						// destroy south wall of random tile, then union the roots of the sets
						tiles[randomTile].setSouthWall(false);
						disjointSet.union(disjointSet.find(randomTile), disjointSet.find(randomTile+columns));
						break;
					}
					// else if the wall is the west wall and the tile to the left of the random tile isn't connected with the random tile, knock down the wall and break
					else if(randomWall == 3 && disjointSet.find(randomTile)!=disjointSet.find(randomTile-1))
					{
						// destroy west wall of random tile, then union the roots of the sets
						tiles[randomTile].setWestWall(false);
						disjointSet.union(disjointSet.find(randomTile), disjointSet.find(randomTile-1));
						break;
					}
					// if the tiles are connected, don't knock down the wall and regenerate a random wall and random tile
					else
					{
					}
				}
			}			
		}
	}
	
	// print method to print the maze
	public void print()
	{
		// print the top of the maze
		System.out.print("   ");  // the top left corner of the maze is open so add appropriate spaces
		
		// add columns-1 underscores on top because the first column of the top row shouldn't have an underscore since it's open
		for(int i = 1; i < columns; i++)
		{
			System.out.print("_ ");
		}
		// print a new line at the end of the top of the maze
		System.out.print("\n");

		// counter to keep track of the current tile we are on
		int currentTile = 0;
		// for every row and every column print the wall for each tile
		for(int i = 0; i < rows; i++)
		{
			// after finding out the row we're on, loop through every tile in the column and print their walls
			for(int j = 0; j < columns; j++)
			{
				// if the current tile has a west wall, print it or print a space if not
				if(tiles[currentTile].getWestWall())
					System.out.print("|");
				else
					System.out.print(" ");
				
				// if the current tile has an south wall, print it or print a space
				if(tiles[currentTile].getSouthWall())
					System.out.print("_");
				else
					System.out.print(" ");
				
				// increment the current tile so you can move on to the next tile
				currentTile++;
			}
			// print the east border manually at the end of each row except for last row since the east wall of the bottom right tile should be open
			if(i!=(rows-1))
				System.out.println("|"); 
			
		}
		
		// print a new line so it's easier to see the south border of the maze
		System.out.println();
		
	}
}
