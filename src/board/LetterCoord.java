package board;

public enum LetterCoord {
    A(0),
    B(1),
    C(2),
    D(3),
    E(4),
    F(5),
    G(6),
    H(7),
    I(8),
    J(9);

    private final int coord;

    private LetterCoord (int coord) {
        this.coord = coord;
    }

    public static LetterCoord getLetterValue(int val) {
        for(LetterCoord e: LetterCoord.values()) {
            if(e.coord == val) {
                return e;
            }
        }
        return null;// not found
    }
}
