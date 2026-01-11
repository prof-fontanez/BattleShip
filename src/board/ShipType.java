package board;

public enum ShipType {
    CARRIER(5),
    BATTLESHIP(4),
    CRUISER(3),
    SUBMARINE(3),
    DESTROYER(2);

    private final int size;
    private final String name;

        private ShipType(int size) {
        this.size = size;
        name = this.name().toLowerCase();
        }

        public int getSize() {
            return size;
        }

        public String getPieceName() {
            return name;
        }
}
