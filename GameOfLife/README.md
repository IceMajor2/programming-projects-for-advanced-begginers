# Game Of Life
A simulation, a cellular automaton first proposed by John Conway in 1970. By providing an initial board with cells (or letting the program randomly generate one) you may observe the fascinating evolution of "life" on it.

Take your time to discover interesting settings. Put a rectangle board in the `/boards/` directory filled with 0s and 1s (indicating dead or alive cells) in the TXT file. This file can then be read in the program.

## Rules
1. Any live cell with two or three live neighbours survives.
2. Any dead cell with three live neighbours becomes a live cell.
3. All other live cells die in the next generation. Similarly, all other dead cells stay dead.

## Setup
You will need Java 17 (or later) in order to run!

1. In the project's root directory, execute: `javac -d target/classes -cp src/main/java src/main/java/GameOfLife.java`. The compiled code (`*.class`) should be now in `target/classes`.
2. To open the program: `java -cp target/classes GameOfLife` :)
