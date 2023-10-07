## The Unbeateable Tic-Tac-Toe

This project aims to become a Tic-Tac-Toe game that is unbeateable.

The alghoritm used for the invincibility is **Minimax**. However, this is not everything that this **Tic-Tac-Toe** gets you. You choose who plays as *X* and *O*. The options are:
* Human player
* Random moves
* AI that spots winning moves (otherwise random)
* AI that spots winning & losing moves (otherwise random)
* *The Unbeateable MINIMAX*

The program also caches the moves making the algorithms even faster.

## Play
In the project's root directory execute:
* **Option 1:**
  * Java: `javac -d target/classes -cp src/main/java src/main/java/TicTacToe.java` afterward to launch: `java -cp target/classes TicTacToe`
  * Maven: `mvn clean compile exec:java`