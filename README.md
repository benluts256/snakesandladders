Snake and LaddersA desktop-based implementation of the classic Snake and Ladders board game built with Java and Swing.
This project follows the MVC (Model-View-Controller) architectural pattern to ensure clean, maintainable, and scalable code.


🚀 FeaturesClassic Gameplay:
Standard 100-tile board with randomized snakes and ladders.Multiplayer Support:
Add multiple players to a single session.
Real-time Game State: Tracking of player positions, turns, and game phases (Setup, Playing, Finished).
Interactive UI: 
Graphical board representation and a side panel for game controls and status updates.
Dice Logic: Built-in dice simulator for fair movement.

🏗️ ArchitectureThe project is structured into three main packages to separate concerns:
Model (com.snakesladders.model): Handles the game logic, player data, and board state (GameState, Player, Dice).
View (com.snakesladders.view): Manages the GUI components using Java Swing (MainWindow, BoardPanel, SidePanel).
Controller (com.snakesladders.controller): Acts as the bridge between the Model and View to process user inputs (GameController).

🛠️ Technologies UsedJava JDK 21+Maven (Dependency management and build automation)Swing 
(Java's GUI widget toolkit)NetBeans IDE📥 Installation & SetupClone the repository:


Bashgit clone https://github.com/benluts256/snakesandladders.git
Open in IDE:Open NetBeans (or VS Code/IntelliJ).
Select File > Open Project and navigate to the cloned folder.
Build the project:Right-click the project and select Clean and Build.
Run the game:Run the Main.java file located in com.snakesladders.controller.


In the original version, the game only moved forward. By implementing a Stack (LIFO - Last-In, First-Out), we introduced the ability to store a history of game states. Each time a player moves, their previous position is "pushed" onto the stack. This allows for a highly efficient Undo functionality, where we can "pop" the most recent state to revert the board.

From a Data Structures perspective, this demonstrates:

    Constant Time Complexity: Both O(1) push and pop operations ensure the "Undo" feature is instantaneous.

    Memory Efficiency: We only store the necessary integers representing player positions, keeping the space complexity lean at O(k), where k is the number of moves made.

    Professional Logic: It showcases an understanding of how to manage temporal data, a critical skill in software engineering for features like backtracking, undo-redo, and navigation history.
Game BoardSetup Dialog🤝 ContributingThis is a student project created for learning purposes.  


Feel free to fork the repository and submit pull requests for:
Adding animations for player movement.Implementing custom board sizes.Adding sound effects.
