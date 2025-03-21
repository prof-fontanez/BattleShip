package board;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * A simple prototype as to what a Battle Ship game will look like using a {@link java.util.Map} to store the coordinates
 * for the game pieces (ships).
 * <p/>
 * This code doesn't do detail error checking, and it only has structures in place for one set of pieces. A real game will
 * require two game boards, where players will call shots on the opponent's board. This means that there needs to be a
 * game controller class to restrict access to an opponent being able to see his opponent's board. The game manager will
 * receive both players' inputs (coordinates) to set the pieces on the board, and the coordinates when a move is made.
 * Then, the game manager will announce to the player making a move whether the shot missed or hit a ship, whether a ship
 * has sunk, and whether the game ended.
 * <p/>
 * Additionally, this prototype lacks controls to keep starting new games, or to terminate a game session.
 */
public class GameBoard {
    private final Map<Coordinates, Ship> gamePieceMap = new HashMap<>();
    private static final Map<Integer, Coordinates> shots = new HashMap<>();
    private final char[][] matrix = new char[10][10];

    /**
     * As the method name implies, to be called to start a new game.
     * <p/>
     * Any remaining coordinates and ships in the map are removed and the game board cleared.
     */
    public void newGame() {
        gamePieceMap.clear();
        clearGrid();
    }

    /**
     * Places a ship on the board. Calculates all the coordinates based on the ship's size and orientation on the
     * board. The row (for horizontal orientation) or column (for vertical) value will be incremented by one N times,
     * where N is the size of the ship. For example, for coordinates (0, 0) and VERTICAL orientation, the rest of the
     * coordinates for a ship of size 4 will be (1, 0), (2, 0), and (3, 0).
     *
     * @param ship The game piece
     * @param coordinates The coordinates of the HEAD of the piece.
     */
    public void setPieceCoordinates(Ship ship, Coordinates coordinates) {
        if (gamePieceMap.containsKey(coordinates)) {
            System.out.println("Point already taken by another ship. Try another set of coordinates");
            return;
        }
        gamePieceMap.put(coordinates, ship);
        int size = ship.size();
        Orientation orientation = ship.orientation();
        int row = coordinates.row();
        int col = coordinates.col();

        matrix[row][col] = (char)(ship.id()+'0');
        switch (orientation) {
            case V -> {
                for (int i = 0; i < size - 1; i++) {
                    Coordinates newCoord = new Coordinates(++row, col);
                    gamePieceMap.put(newCoord, ship);
                    matrix[row][col] = (char)(ship.id()+'0');
                }
            }
            case H -> {
                for (int i = 0; i < size - 1; i++) {
                    Coordinates newCoord = new Coordinates(row, ++col);
                    gamePieceMap.put(newCoord, ship);
                    matrix[row][col] = (char)(ship.id()+'0');
                }
            }
        }
    }

    /**
     * See if a move is either a hit or a miss.
     * <p/>
     * For a hit, it also checks to see if the ship that was hit is sunk, and whether the game is over.
     *
     * @param coordinates The coordinates given by the player for that turn
     */
    public void move(Coordinates coordinates) {
        int col = coordinates.col();
        int row = coordinates.row();
        String coordStr = "coordinates (" + row + ", " + col + ")";

        if (!gamePieceMap.containsKey(coordinates)) {
            System.out.println(coordStr + " is a miss.");
            matrix[row][col] = 'o';
            return;
        }

        System.out.println(coordStr + " is a hit!");
        Ship ship = gamePieceMap.remove(coordinates);
        matrix[row][col] = '*';

        if (!gamePieceMap.containsValue(ship)) {
            System.out.println("You sunk a battleship!");
        }
        printGrid();
    }

    public boolean evaluateBoard() {
        boolean gameOver = gamePieceMap.isEmpty();
        if (gameOver) {
            System.out.println("You win! Game over.");
        }
        return gameOver;
    }

    public void generateShots () {
        int key = 1;
        for (int i=0; i < 10; i++) {
            for(int j=0; j < 10; j++) {
                shots.put(key++, new Coordinates(i, j));
            }
        }
    }

    /**
     * Prints out the grid to the console (only needed for troubleshooting).
     * Do not use for real gameplay.
     */
    public void printGrid () {
        for (int i=0; i < 10; i++) {
            for(int j=0; j < 10; j++) {
                System.out.print(String.valueOf(matrix[i][j]) + ' ');
            }
            System.out.println();
        }
    }

    /*
     * Initializes the grid for a new game.
     */
    private void clearGrid() {
        Arrays.stream(matrix).forEach(row -> IntStream.range(0, 10).forEach(col -> row[col] = '.'));
    }

    @SuppressWarnings("BusyWait")
    public static void main(String[] args) {
        GameBoard grid = new GameBoard();
        grid.newGame();
        Ship ship1 = new Ship(1, 4, Orientation.V);
        Ship ship2 = new Ship(2, 3, Orientation.H);
        grid.setPieceCoordinates(ship1, new Coordinates(0,0));
        grid.setPieceCoordinates(ship2, new Coordinates(2,3));
        grid.printGrid();
        grid.generateShots();

        do {
            Coordinates shot = shots.remove(Utilities.generateRandomNumber(1, 100));
            if(shot == null) {
                continue;
            }
            grid.move(shot);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } while (!grid.evaluateBoard());
    }
}
