package board;

import java.util.HashMap;
import java.util.Map;

public class GameBoard {
    private Map<Coordinates, Ship> gamePieceMap = new HashMap<>();
    private char[][] matrix = new char[10][10];

    public void newGame() {
        gamePieceMap.clear();
        clearGrid();
    }

    public void setPieceCoordiates(Ship ship, Coordinates coordinates) {
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

    public void move(Coordinates coordinates) {
        int col = coordinates.col();
        int row = coordinates.row();
        String coordStr = "coordinates (" + row + ", " + col + ")";

        if (!gamePieceMap.containsKey(coordinates)) {
            System.out.println(coordStr + " is a miss.");
            return;
        }

        System.out.println(coordStr + " is a hit!");
        Ship ship = gamePieceMap.remove(coordinates);
        matrix[row][col] = '*';

        if (!gamePieceMap.containsValue(ship)) {
            System.out.println("You sunk a battleship!");
        }

        if (gamePieceMap.isEmpty()) {
            System.out.println("You win! Game over.");
        }
        printGrid();
    }

    public void printGrid () {
        for (int i=0; i < 10; i++) {
            for(int j=0; j < 10; j++) {
                System.out.print(String.valueOf(matrix[i][j]) + ' ');
            }
            System.out.println();
        }
    }

    private void clearGrid() {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                matrix[i][j] = '.';
    }

    public static void main(String[] args) {
        GameBoard grid = new GameBoard();
        grid.newGame();
        Ship ship1 = new Ship(1, 4, Orientation.V);
        Ship ship2 = new Ship(2, 3, Orientation.H);
        grid.setPieceCoordiates(ship1, new Coordinates(0,0));
        grid.setPieceCoordiates(ship2, new Coordinates(2,3));
        grid.printGrid();
        grid.move(new Coordinates(1,1));
        grid.move(new Coordinates(2,0));
        grid.move(new Coordinates(1,0));
        grid.move(new Coordinates(0,0));
        grid.move(new Coordinates(3,0));
        grid.move(new Coordinates(2, 3));
        grid.move(new Coordinates(2, 4));
        grid.move(new Coordinates(2, 5));
    }
}
