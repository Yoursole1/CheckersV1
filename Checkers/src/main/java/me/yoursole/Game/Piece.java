package me.yoursole.Game;

public class Piece {
    private PieceType pieceType;

    public Piece(PieceType type){
        this.pieceType=type;
    }

    public PieceType getPieceType(){
        return this.pieceType;
    }

    public Piece clone(){
        return new Piece(pieceType);
    }

    public void QueenPiece(){
        this.pieceType=(this.pieceType==PieceType.BLANK)?PieceType.BLANK:
                ((this.pieceType==PieceType.WHITE)?PieceType.WHITE_QUEEN:PieceType.BLACK_QUEEN);
    }
    public boolean equals(Object obj) {
        return (this == obj);
    }
    public String toString(){
        return (this.pieceType.equals(PieceType.BLACK)?
                String.valueOf(Character.toChars(Integer.parseInt("26AB", 16))):
                (this.pieceType.equals(PieceType.WHITE)?
                        String.valueOf(Character.toChars(Integer.parseInt("26AA", 16))):
                        (this.pieceType.equals(PieceType.WHITE_QUEEN)?
                                String.valueOf(Character.toChars(Integer.parseInt("29F2", 16))):
                                (this.pieceType.equals(PieceType.BLACK_QUEEN)?
                                        String.valueOf(Character.toChars(Integer.parseInt("29F3", 16))):
                                        "--"))));
    }

}
