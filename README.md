# Conway's game of life

Conway's game of life is a cellular automaton simulation in which cells are either alive or dead. There are a set of rules that decide the
state of each cell.

1) Any live cell with fewer than two live neighbors dies, as if caused by underpopulation.
2) Any live cell with more than three live neighbors dies, as if by overcrowding.
3) Any live cell with two or three live neighbors lives on to the next generation.
4) Any dead cell with exactly three live neighbors becomes a live cell.

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

Create a pattern on the board and then start the simulation with the play button. The state of the board can be paused at any time with the stop button and also reset.

# To do improvements

A better algorithm for each generation which only iterates through the "active cells" rather than every cell. Active cells are any alive cells and their 8 surrounding cells.
