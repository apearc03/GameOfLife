# Conway's game of life

Conway's game of life is a cellular automaton simulation in which cells are either alive or dead. There are a set of rules that decide the
state of each cell.

1) Any live cell with fewer than two live neighbors dies, as if caused by underpopulation.
2) Any live cell with more than three live neighbors dies, as if by overcrowding.
3) Any live cell with two or three live neighbors lives on to the next generation.
4) Any dead cell with exactly three live neighbors becomes a live cell.


# Algorithm efficiency

My original algorithm for calculating the state of cells involved using a double for loop to create a boolean value in a two dimensional array(The placeholder). This value represented the cell's state, true would equal alive and false would equal dead.
This involved going through every single cell on the board.
I would then loop through every cell once again in the same render call and draw each cell with a texture dependent on the placeholder value.
I also rendered textures to the screen for all the dead cells.
These two factors combined lead to poor performance when the cell sizes were reduced, increasing the number of total cells.

Below is a gif showing the speed of the original program with cells set to 3 units of width and height.

<img src="https://media.giphy.com/media/1naw4yHWDKEzNGMRjH/giphy.gif" />


I came up with a solution to improve performance greatly. First off, I realised I only had to loop through the "active cells" these are the alive cells and their 8 surrounding cells.
All other cells have to be dead if they are neighboured with an active cell.
With the use of a set to hold active cells and avoid duplicates, I was able to get rid of the first double for loop and just go through the active cells to evaluate the placeholder values.
Another change was to avoid rendering dead cells, I simply left the dead cells black. This greatly increased the draw speed.

Below is a gif showing the speed of the second version with cells set to 3 units width and height. The pattern used is the same as in the previous example.

<img src="https://media.giphy.com/media/1sx4rzIPBgNbKHSNfw/giphy.gif" />


# Dependencies

Java Runtime Environment

# Running and packaging

Download or clone the repository.

Navigate to the directory.

To run the game:

gradlew desktop:run

This compiles your core and desktop project, and runs the desktop starter. The working directory is the project's assets folder!

To package the game:

gradlew desktop:dist

This will create a runnable JAR file located in the desktop/build/libs/ folder. It contains all necessary code as well as all art assets. This can be run by double clicking or via the command line with java -jar jar-file-name.jar. Must have a JVM installed for this to work. The JAR will work on Windows, Linux and Mac OS X!

# Controls

Create or use one of the default patterns and then start the simulation with the play button. The state of the board can be paused at any time with the stop button and also reset.

