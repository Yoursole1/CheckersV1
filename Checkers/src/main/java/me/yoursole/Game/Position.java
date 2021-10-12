package me.yoursole.Game;

import java.util.*;

public class Position implements Cloneable{
    private Piece[][] position;
    private Pair legalMoves;
    private boolean turn; //true == white, false == black

    public Position(Piece[][] position,boolean turn){
        //board size must be 8
        if(position.length==8&&position[0].length==8){
            this.position=position;
            legalMoves = generateLegalMoves();
            this.turn=turn;
        }else{
            throw new IllegalArgumentException("Position size must be 8x8");
        }
    }

    @Override
    public Position clone() throws CloneNotSupportedException {
        return new Position(this.position.clone(),this.turn);
    }

    /**
     * critical for making imaginary positions in the tree search
     * @param position
     */
    public Position(Position position){
        this.position=position.getPosition().clone();
        legalMoves = generateLegalMoves();
        turn = position.getTurn();
    }


    public boolean getTurn(){
        return this.turn;
    }
    public void setTurn(boolean turn){
        this.turn=turn;
    }
    public void removePiece(BoardLocation location){
        this.position[location.getArrayX()][location.getArrayY()]=new Piece(PieceType.BLANK);
    }

    public void addPiece(BoardLocation location, PieceType type){
        this.position[location.getArrayX()][location.getArrayY()]=new Piece(type);
    }

    public Piece[][] getPosition(){
        return this.position;
    }

    public Piece getPieceAt(BoardLocation location){
        return this.position[location.getArrayX()][location.getArrayY()];
    }

    public int whiteMoves(){
        return this.legalMoves.whiteMoves.size();
    }

    public int blackMoves(){
        return this.legalMoves.blackMoves.size();
    }

    public boolean completeMove(Move move){
        if(turn){ //whites move
            if(legalMoves.whiteMoves.contains(move)){
                this.turn= false;
                ArrayList<BoardLocation> mv=move.getMove();
                if(Math.abs(mv.get(0).getX()-mv.get(1).getX())==1){

                    this.addPiece(move.get(1),this.getPieceAt(move.get(0)).getPieceType());
                    if(mv.get(mv.size()-1).getY()==7){
                        this.addPiece(mv.get(mv.size()-1),PieceType.WHITE_QUEEN);
                    }
                    this.removePiece(move.get(0));

                }else{
                    this.addPiece(mv.get(mv.size()-1),this.getPieceAt(move.get(0)).getPieceType());
                    if(mv.get(mv.size()-1).getY()==7){
                        this.addPiece(mv.get(mv.size()-1),PieceType.WHITE_QUEEN);
                    }
                    this.removePiece(mv.get(0));
                    for (int i = 0; i < mv.size(); i++) {
                        BoardLocation loc = mv.get(i);
                        BoardLocation locB = (i<mv.size()-1)?mv.get(i+1):null;

                        BoardLocation locC = (locB!=null)?new BoardLocation((loc.getX()+locB.getX())/2,(loc.getY()+locB.getY())/2):null;
                        if(locC!=null){
                            this.removePiece(locC);
                        }

                    }
                }
                return true;
            }
        }else{
            if(legalMoves.blackMoves.contains(move)){
                this.turn= true;
                ArrayList<BoardLocation> mv=move.getMove();
                if(Math.abs(mv.get(0).getX()-mv.get(1).getX())==1){
                    this.addPiece(move.get(1),this.getPieceAt(move.get(0)).getPieceType());
                    if(mv.get(mv.size()-1).getY()==7){
                        this.addPiece(mv.get(mv.size()-1),PieceType.BLACK_QUEEN);
                    }
                    this.removePiece(move.get(0));

                }else{
                    this.addPiece(mv.get(mv.size()-1),this.getPieceAt(move.get(0)).getPieceType());
                    if(mv.get(mv.size()-1).getY()==7){
                        this.addPiece(mv.get(mv.size()-1),PieceType.BLACK_QUEEN);
                    }
                    this.removePiece(mv.get(0));
                    for (int i = 0; i < mv.size(); i++) {
                        BoardLocation loc = mv.get(i);
                        BoardLocation locB = (i<mv.size()-1)?mv.get(i+1):null;

                        BoardLocation locC = (locB!=null)?new BoardLocation((loc.getX()+locB.getX())/2,(loc.getY()+locB.getY())/2):null;
                        if(locC!=null){
                            this.removePiece(locC);
                        }

                    }

                }
                return true;
            }
        }
        return false;
    }
    public boolean equals(Object obj) {
        return (this == obj);
    }

    public Pair getLegalMoves(){
        return legalMoves;
    }

    private boolean hasNext(BoardLocation location, PieceType type){
        if(type.equals(PieceType.BLANK))
            return false;
        if(type.equals(PieceType.WHITE)){
            if(location.getX()+2<8&&location.getY()+2<8){
                if(this.getPieceAt(new BoardLocation(location.getX()+1, location.getY()+1)).getPieceType().equals(PieceType.BLACK)||
                        this.getPieceAt(new BoardLocation(location.getX()+1, location.getY()+1)).getPieceType().equals(PieceType.BLACK_QUEEN)){
                    if(this.getPieceAt(new BoardLocation(location.getX()+2, location.getY()+2)).getPieceType().equals(PieceType.BLANK)){
                        return true;
                    }
                }
            }if(location.getX()-2>-1&&location.getY()+2<8){
                if(this.getPieceAt(new BoardLocation(location.getX()-1, location.getY()+1)).getPieceType().equals(PieceType.BLACK)||
                        this.getPieceAt(new BoardLocation(location.getX()-1, location.getY()+1)).getPieceType().equals(PieceType.BLACK_QUEEN)){
                    if(this.getPieceAt(new BoardLocation(location.getX()-2, location.getY()+2)).getPieceType().equals(PieceType.BLANK)){
                        return true;
                    }
                }
            }
        }else if(type.equals(PieceType.BLACK)){
            if(location.getX()-2>-1&&location.getY()-2>-1){
                if(this.getPieceAt(new BoardLocation(location.getX()-1, location.getY()-1)).getPieceType().equals(PieceType.WHITE)||
                        this.getPieceAt(new BoardLocation(location.getX()-1, location.getY()-1)).getPieceType().equals(PieceType.WHITE_QUEEN)){
                    if(this.getPieceAt(new BoardLocation(location.getX()-2, location.getY()-2)).getPieceType().equals(PieceType.BLANK)){
                        return true;
                    }
                }
            }if(location.getX()+2<8&&location.getY()-2>-1){
                if(this.getPieceAt(new BoardLocation(location.getX()+1, location.getY()-1)).getPieceType().equals(PieceType.WHITE)||
                        this.getPieceAt(new BoardLocation(location.getX()+1, location.getY()-1)).getPieceType().equals(PieceType.WHITE_QUEEN)){
                    if(this.getPieceAt(new BoardLocation(location.getX()+2, location.getY()-2)).getPieceType().equals(PieceType.BLANK)){
                        return true;
                    }
                }
            }
        }else if(type.equals(PieceType.WHITE_QUEEN)){

            if(location.getX()+2<8&&location.getY()+2<8){
                if(this.getPieceAt(new BoardLocation(location.getX()+1, location.getY()+1)).getPieceType().equals(PieceType.BLACK)||
                        this.getPieceAt(new BoardLocation(location.getX()+1, location.getY()+1)).getPieceType().equals(PieceType.BLACK_QUEEN)){
                    if(this.getPieceAt(new BoardLocation(location.getX()+2, location.getY()+2)).getPieceType().equals(PieceType.BLANK)){
                        return true;
                    }
                }
            }if(location.getX()-2>-1&&location.getY()+2<8){
                if(this.getPieceAt(new BoardLocation(location.getX()-1, location.getY()+1)).getPieceType().equals(PieceType.BLACK)||
                        this.getPieceAt(new BoardLocation(location.getX()-1, location.getY()+1)).getPieceType().equals(PieceType.BLACK_QUEEN)){
                    if(this.getPieceAt(new BoardLocation(location.getX()-2, location.getY()+2)).getPieceType().equals(PieceType.BLANK)){
                        return true;
                    }
                }
            }if(location.getX()-2>-1&&location.getY()-2>-1){
                if(this.getPieceAt(new BoardLocation(location.getX()-1, location.getY()-1)).getPieceType().equals(PieceType.BLACK)||
                        this.getPieceAt(new BoardLocation(location.getX()-1, location.getY()-1)).getPieceType().equals(PieceType.BLACK_QUEEN)){
                    if(this.getPieceAt(new BoardLocation(location.getX()-2, location.getY()-2)).getPieceType().equals(PieceType.BLANK)){
                        return true;
                    }
                }
            }if(location.getX()+2<8&&location.getY()-2>-1){
                if(this.getPieceAt(new BoardLocation(location.getX()+1, location.getY()-1)).getPieceType().equals(PieceType.BLACK)||
                        this.getPieceAt(new BoardLocation(location.getX()+1, location.getY()-1)).getPieceType().equals(PieceType.BLACK_QUEEN)){
                    if(this.getPieceAt(new BoardLocation(location.getX()+2, location.getY()-2)).getPieceType().equals(PieceType.BLANK)){
                        return true;
                    }
                }
            }
        }else if(type.equals(PieceType.BLACK_QUEEN)){
            if(location.getX()-2>-1&&location.getY()-2>-1){
                if(this.getPieceAt(new BoardLocation(location.getX()-1, location.getY()-1)).getPieceType().equals(PieceType.WHITE)||
                        this.getPieceAt(new BoardLocation(location.getX()-1, location.getY()-1)).getPieceType().equals(PieceType.WHITE_QUEEN)){
                    if(this.getPieceAt(new BoardLocation(location.getX()-2, location.getY()-2)).getPieceType().equals(PieceType.BLANK)){
                        return true;
                    }
                }
            }if(location.getX()+2<8&&location.getY()-2>-1){
                if(this.getPieceAt(new BoardLocation(location.getX()+1, location.getY()-1)).getPieceType().equals(PieceType.WHITE)||
                        this.getPieceAt(new BoardLocation(location.getX()+1, location.getY()-1)).getPieceType().equals(PieceType.WHITE_QUEEN)){
                    if(this.getPieceAt(new BoardLocation(location.getX()+2, location.getY()-2)).getPieceType().equals(PieceType.BLANK)){
                        return true;
                    }
                }
            }if(location.getX()+2<8&&location.getY()+2<8){
                if(this.getPieceAt(new BoardLocation(location.getX()+1, location.getY()+1)).getPieceType().equals(PieceType.WHITE)||
                        this.getPieceAt(new BoardLocation(location.getX()+1, location.getY()+1)).getPieceType().equals(PieceType.WHITE_QUEEN)){
                    if(this.getPieceAt(new BoardLocation(location.getX()+2, location.getY()+2)).getPieceType().equals(PieceType.BLANK)){
                        return true;
                    }
                }
            }if(location.getX()-2>-1&&location.getY()+2<8){
                if(this.getPieceAt(new BoardLocation(location.getX()-1, location.getY()+1)).getPieceType().equals(PieceType.WHITE)||
                        this.getPieceAt(new BoardLocation(location.getX()-1, location.getY()+1)).getPieceType().equals(PieceType.WHITE_QUEEN)){
                    if(this.getPieceAt(new BoardLocation(location.getX()-2, location.getY()+2)).getPieceType().equals(PieceType.BLANK)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
    private ArrayList<Move> search(ArrayList<Move> input, PieceType type, RecursionBias biases) {
        BoardLocation location = input.get(input.size()-1).get(input.get((input.size()-1)).getSize()-1);
        if(this.hasNext(location,type)){


            //White checker takes to the right
            if(biases.getBiases()[1]&&type.equals(PieceType.WHITE)&&location.getX()+2<8&&location.getY()+2<8&&(this.getPieceAt(new BoardLocation(location.getX()+1,location.getY()+1)).getPieceType().equals(PieceType.BLACK)||
                    this.getPieceAt(new BoardLocation(location.getX()+1,location.getY()+1)).getPieceType().equals(PieceType.BLACK_QUEEN))&&
                    this.getPieceAt(new BoardLocation(location.getX()+2,location.getY()+2)).getPieceType().equals(PieceType.BLANK)){
                input.add(input.get(input.size()-1).clone().addStep(new BoardLocation(location.getX()+2,location.getY()+2)));

                //This keeps the left side from running if the right returns
                ArrayList<Move> tmp = (search(input,type,new RecursionBias(true, false, false, false)));
                System.out.println(tmp.addAll(search(input,type,new RecursionBias(false, true, false, false)))+"--"+location+"--"+"left");
                return tmp;
            }
            //White checker takes to the left
            if(biases.getBiases()[0]&&type.equals(PieceType.WHITE)&&location.getX()-2>-1&&location.getY()+2<8&&(this.getPieceAt(new BoardLocation(location.getX()-1,location.getY()+1)).getPieceType().equals(PieceType.BLACK)||
                    this.getPieceAt(new BoardLocation(location.getX()-1,location.getY()+1)).getPieceType().equals(PieceType.BLACK_QUEEN))&&
                    this.getPieceAt(new BoardLocation(location.getX()-2,location.getY()+2)).getPieceType().equals(PieceType.BLANK)){
                input.add(input.get(input.size()-1).clone().addStep(new BoardLocation(location.getX()-2,location.getY()+2)));


                ArrayList<Move> tmp = (search(input,type,new RecursionBias(true, false, false, false)));
                System.out.println(tmp.addAll(search(input,type,new RecursionBias(false, true, false, false)))+"--"+location+"--"+"right");
                return tmp;
            }
        }
        return input;
    }

     */
    private ArrayList<Move> search(ArrayList<Move> input, PieceType type) {

        Move latest = input.get(input.size()-1).clone();
        BoardLocation location = input.get(input.size()-1).get(input.get((input.size()-1)).getSize()-1);

        if(this.hasNext(location,type)){


            //White checker takes to the right
            if(type.equals(PieceType.WHITE)&&location.getX()+2<8&&location.getY()+2<8&&(this.getPieceAt(new BoardLocation(location.getX()+1,location.getY()+1)).getPieceType().equals(PieceType.BLACK)||
                    this.getPieceAt(new BoardLocation(location.getX()+1,location.getY()+1)).getPieceType().equals(PieceType.BLACK_QUEEN))&&
                    this.getPieceAt(new BoardLocation(location.getX()+2,location.getY()+2)).getPieceType().equals(PieceType.BLANK)){


                input.add(latest.clone().addStep(new BoardLocation(location.getX()+2,location.getY()+2)));

                //This keeps the left side from running if the right returns
                ArrayList<Move> tmp = (search(input,type));
                input.addAll(tmp);
            }
            //White checker takes to the left
            if(type.equals(PieceType.WHITE)&&location.getX()-2>-1&&location.getY()+2<8&&(this.getPieceAt(new BoardLocation(location.getX()-1,location.getY()+1)).getPieceType().equals(PieceType.BLACK)||
                    this.getPieceAt(new BoardLocation(location.getX()-1,location.getY()+1)).getPieceType().equals(PieceType.BLACK_QUEEN))&&
                    this.getPieceAt(new BoardLocation(location.getX()-2,location.getY()+2)).getPieceType().equals(PieceType.BLANK)){



                //input.add(input.get(input.size()-1).clone().addStep(new BoardLocation(location.getX()-2,location.getY()+2)));
                input.add(latest.clone().addStep(new BoardLocation(location.getX()-2,location.getY()+2)));

                ArrayList<Move> tmp = (search(input,type));
                input.addAll(tmp);
            }
            if(type.equals(PieceType.BLACK)&&location.getX()+2<8&&location.getY()-2>-1&&(this.getPieceAt(new BoardLocation(location.getX()+1,location.getY()-1)).getPieceType().equals(PieceType.WHITE)||
                    this.getPieceAt(new BoardLocation(location.getX()+1,location.getY()-1)).getPieceType().equals(PieceType.WHITE_QUEEN))&&
                    this.getPieceAt(new BoardLocation(location.getX()+2,location.getY()-2)).getPieceType().equals(PieceType.BLANK)){



                //input.add(input.get(input.size()-1).clone().addStep(new BoardLocation(location.getX()-2,location.getY()+2)));
                input.add(latest.clone().addStep(new BoardLocation(location.getX()+2,location.getY()-2)));

                ArrayList<Move> tmp = (search(input,type));
                input.addAll(tmp);
            }
            if(type.equals(PieceType.BLACK)&&location.getX()-2>-1&&location.getY()-2>-1&&(this.getPieceAt(new BoardLocation(location.getX()-1,location.getY()-1)).getPieceType().equals(PieceType.WHITE)||
                    this.getPieceAt(new BoardLocation(location.getX()-1,location.getY()-1)).getPieceType().equals(PieceType.WHITE_QUEEN))&&
                    this.getPieceAt(new BoardLocation(location.getX()-2,location.getY()-2)).getPieceType().equals(PieceType.BLANK)){



                //input.add(input.get(input.size()-1).clone().addStep(new BoardLocation(location.getX()-2,location.getY()+2)));
                input.add(latest.clone().addStep(new BoardLocation(location.getX()-2,location.getY()-2)));

                ArrayList<Move> tmp = (search(input,type));
                input.addAll(tmp);
            }

            //----------QUEENS
            if(type.equals(PieceType.WHITE_QUEEN)&&location.getX()+2<8&&location.getY()+2<8&&(this.getPieceAt(new BoardLocation(location.getX()+1,location.getY()+1)).getPieceType().equals(PieceType.BLACK)||
                    this.getPieceAt(new BoardLocation(location.getX()+1,location.getY()+1)).getPieceType().equals(PieceType.BLACK_QUEEN))&&
                    this.getPieceAt(new BoardLocation(location.getX()+2,location.getY()+2)).getPieceType().equals(PieceType.BLANK)){


                boolean a = true;
                for(Move move : input){
                    for(BoardLocation loc : move.getMove())
                        if(loc.getX()==location.getX()+2&&loc.getY()==location.getY()+2)
                            a=false;
                }

                if(a){
                    input.add(latest.clone().addStep(new BoardLocation(location.getX()+2,location.getY()+2)));

                    //This keeps the left side from running if the right returns
                    ArrayList<Move> tmp = (search(input,type));
                    input.addAll(tmp);
                }

            }
            //White checker takes to the left
            if(type.equals(PieceType.WHITE_QUEEN)&&location.getX()-2>-1&&location.getY()+2<8&&(this.getPieceAt(new BoardLocation(location.getX()-1,location.getY()+1)).getPieceType().equals(PieceType.BLACK)||
                    this.getPieceAt(new BoardLocation(location.getX()-1,location.getY()+1)).getPieceType().equals(PieceType.BLACK_QUEEN))&&
                    this.getPieceAt(new BoardLocation(location.getX()-2,location.getY()+2)).getPieceType().equals(PieceType.BLANK)){



                boolean a = true;
                for(Move move : input){
                    for(BoardLocation loc : move.getMove())
                        if(loc.getX()==location.getX()-2&&loc.getY()==location.getY()+2)
                            a=false;
                }
                //input.add(input.get(input.size()-1).clone().addStep(new BoardLocation(location.getX()-2,location.getY()+2)));
                if(a){
                    input.add(latest.clone().addStep(new BoardLocation(location.getX()-2,location.getY()+2)));

                    ArrayList<Move> tmp = (search(input,type));
                    input.addAll(tmp);
                }

            }
            if(type.equals(PieceType.WHITE_QUEEN)&&location.getX()+2<8&&location.getY()-2>-1&&(this.getPieceAt(new BoardLocation(location.getX()+1,location.getY()-1)).getPieceType().equals(PieceType.BLACK)||
                    this.getPieceAt(new BoardLocation(location.getX()+1,location.getY()-1)).getPieceType().equals(PieceType.BLACK_QUEEN))&&
                    this.getPieceAt(new BoardLocation(location.getX()+2,location.getY()-2)).getPieceType().equals(PieceType.BLANK)){



                boolean a = true;
                for(Move move : input){
                    for(BoardLocation loc : move.getMove())
                        if(loc.getX()==location.getX()+2&&loc.getY()==location.getY()-2)
                            a=false;
                }
                //input.add(input.get(input.size()-1).clone().addStep(new BoardLocation(location.getX()-2,location.getY()+2)));
                if(a){
                    input.add(latest.clone().addStep(new BoardLocation(location.getX()+2,location.getY()-2)));

                    ArrayList<Move> tmp = (search(input,type));
                    input.addAll(tmp);
                }

            }
            if(type.equals(PieceType.WHITE_QUEEN)&&location.getX()-2>-1&&location.getY()-2>-1&&(this.getPieceAt(new BoardLocation(location.getX()-1,location.getY()-1)).getPieceType().equals(PieceType.BLACK)||
                    this.getPieceAt(new BoardLocation(location.getX()-1,location.getY()-1)).getPieceType().equals(PieceType.BLACK_QUEEN))&&
                    this.getPieceAt(new BoardLocation(location.getX()-2,location.getY()-2)).getPieceType().equals(PieceType.BLANK)){



                boolean a = true;
                for(Move move : input){
                    for(BoardLocation loc : move.getMove()) {
                        if (loc.getX() == location.getX() - 2 && loc.getY() == location.getY() - 2)
                            a = false;
                    }
                }
                if(a){
                    //input.add(input.get(input.size()-1).clone().addStep(new BoardLocation(location.getX()-2,location.getY()+2)));
                    input.add(latest.clone().addStep(new BoardLocation(location.getX()-2,location.getY()-2)));

                    ArrayList<Move> tmp = (search(input,type));
                    input.addAll(tmp);
                }

            }



            //QUEENS B
            if(type.equals(PieceType.BLACK_QUEEN)&&location.getX()+2<8&&location.getY()+2<8&&(this.getPieceAt(new BoardLocation(location.getX()+1,location.getY()+1)).getPieceType().equals(PieceType.WHITE)||
                    this.getPieceAt(new BoardLocation(location.getX()+1,location.getY()+1)).getPieceType().equals(PieceType.WHITE_QUEEN))&&
                    this.getPieceAt(new BoardLocation(location.getX()+2,location.getY()+2)).getPieceType().equals(PieceType.BLANK)){


                boolean a = true;
                for(Move move : input){
                    for(BoardLocation loc : move.getMove())
                        if(loc.getX()==location.getX()+2&&loc.getY()==location.getY()+2)
                            a=false;
                }

                if(a){
                    input.add(latest.clone().addStep(new BoardLocation(location.getX()+2,location.getY()+2)));

                    //This keeps the left side from running if the right returns
                    ArrayList<Move> tmp = (search(input,type));
                    input.addAll(tmp);
                }

            }
            //White checker takes to the left
            if(type.equals(PieceType.BLACK_QUEEN)&&location.getX()-2>-1&&location.getY()+2<8&&(this.getPieceAt(new BoardLocation(location.getX()-1,location.getY()+1)).getPieceType().equals(PieceType.WHITE)||
                    this.getPieceAt(new BoardLocation(location.getX()-1,location.getY()+1)).getPieceType().equals(PieceType.WHITE_QUEEN))&&
                    this.getPieceAt(new BoardLocation(location.getX()-2,location.getY()+2)).getPieceType().equals(PieceType.BLANK)){


                boolean a = true;
                for(Move move : input){
                    for(BoardLocation loc : move.getMove())
                        if(loc.getX()==location.getX()-2&&loc.getY()==location.getY()+2)
                            a=false;
                }

                if(a){
                    //input.add(input.get(input.size()-1).clone().addStep(new BoardLocation(location.getX()-2,location.getY()+2)));
                    input.add(latest.clone().addStep(new BoardLocation(location.getX()-2,location.getY()+2)));

                    ArrayList<Move> tmp = (search(input,type));
                    input.addAll(tmp);
                }

            }
            if(type.equals(PieceType.BLACK_QUEEN)&&location.getX()+2<8&&location.getY()-2>-1&&(this.getPieceAt(new BoardLocation(location.getX()+1,location.getY()-1)).getPieceType().equals(PieceType.WHITE)||
                    this.getPieceAt(new BoardLocation(location.getX()+1,location.getY()-1)).getPieceType().equals(PieceType.WHITE_QUEEN))&&
                    this.getPieceAt(new BoardLocation(location.getX()+2,location.getY()-2)).getPieceType().equals(PieceType.BLANK)){



                boolean a = true;
                for(Move move : input){
                    for(BoardLocation loc : move.getMove())
                        if(loc.getX()==location.getX()+2&&loc.getY()==location.getY()-2)
                            a=false;
                }

                if(a){
                    //input.add(input.get(input.size()-1).clone().addStep(new BoardLocation(location.getX()-2,location.getY()+2)));
                    input.add(latest.clone().addStep(new BoardLocation(location.getX()+2,location.getY()-2)));

                    ArrayList<Move> tmp = (search(input,type));
                    input.addAll(tmp);
                }

            }
            if(type.equals(PieceType.BLACK_QUEEN)&&location.getX()-2>-1&&location.getY()-2>-1&&(this.getPieceAt(new BoardLocation(location.getX()-1,location.getY()-1)).getPieceType().equals(PieceType.WHITE)||
                    this.getPieceAt(new BoardLocation(location.getX()-1,location.getY()-1)).getPieceType().equals(PieceType.WHITE_QUEEN))&&
                    this.getPieceAt(new BoardLocation(location.getX()-2,location.getY()-2)).getPieceType().equals(PieceType.BLANK)){



                boolean a = true;
                for(Move move : input){
                    for(BoardLocation loc : move.getMove())
                        if(loc.getX()==location.getX()-2&&loc.getY()==location.getY()-2)
                            a=false;
                }

                if(a){
                    //input.add(input.get(input.size()-1).clone().addStep(new BoardLocation(location.getX()-2,location.getY()+2)));
                    input.add(latest.clone().addStep(new BoardLocation(location.getX()-2,location.getY()-2)));

                    ArrayList<Move> tmp = (search(input,type));
                    input.addAll(tmp);
                }

            }

        }
        return input;
    }

    @org.jetbrains.annotations.NotNull
    @org.jetbrains.annotations.Contract("_ -> param1")
    private ArrayList<Move> removeDuplicateMoves(ArrayList<Move> in){
        Set<Move> set = new LinkedHashSet<>(in);
        in.clear();
        in.addAll(set);
        return in;
    }
    /*
    private ArrayList<ArrayList<BoardLocation>> search(ArrayList<ArrayList<BoardLocation>> input, PieceType type){
        BoardLocation location = input.get(input.size()-1).get(input.get(input.size()-1).size()-1); //gets the last index of last array
        //System.out.println(location+" -- "+this.hasNext(location,type));
        if(this.hasNext(location,type)){
            if(type.equals(PieceType.WHITE)){

                if(location.getX()+2<8&&location.getY()+2<8){
                    if(this.getPieceAt(new BoardLocation(location.getX()+1,location.getY()+1)).getPieceType().equals(PieceType.BLACK)||
                            this.getPieceAt(new BoardLocation(location.getX()+1,location.getY()+1)).getPieceType().equals(PieceType.BLACK_QUEEN)){

                        if(this.getPieceAt(new BoardLocation(location.getX()+2,location.getY()+2)).getPieceType().equals(PieceType.BLANK)){
                            //jump diag forward right

                            ArrayList<BoardLocation> a = input.get(input.size()-1);
                            a.add(new BoardLocation(location.getX()+2,location.getY()+2));

                            input.add(a);
                            System.out.println(input);
                            return search(input,type);

                        }
                    }

                }
                if(location.getX()-2<8&&location.getY()+2<8){
                    if(this.getPieceAt(new BoardLocation(location.getX()-1,location.getY()+1)).getPieceType().equals(PieceType.BLACK)||
                            this.getPieceAt(new BoardLocation(location.getX()-1,location.getY()+1)).getPieceType().equals(PieceType.BLACK_QUEEN)){
                        if(this.getPieceAt(new BoardLocation(location.getX()-2,location.getY()+2)).getPieceType().equals(PieceType.BLANK)){
                            //jump diag forward left

                        }
                    }
                }
            }
        }
        return input;
    }

     */

    /**
     * Get all legal Moves
     * @return
     */
    public void updateMoves(){
        this.legalMoves=generateLegalMoves();
    }
    private Pair generateLegalMoves(){
        Pair legalMoves = new Pair();


        for (int i = 0; i < position.length; i++) {
            for (int j = 0; j < position[0].length; j++) {
                if(this.getPieceAt(new BoardLocation(j,i)).getPieceType().equals(PieceType.WHITE)){


                    if(j+1<=7&&i+1<=7&&this.getPieceAt(new BoardLocation(j+1,i+1)).getPieceType().equals(PieceType.BLANK)){
                        int finalJ = j;
                        int finalI = i;
                        legalMoves.addWhiteMove(
                                new Move(){{
                                    addStep(new BoardLocation(finalJ, finalI));
                                    addStep(new BoardLocation(finalJ+1, finalI+1));
                                }}
                        );
                    }
                    if(j-1>=0&&i+1<=7&&this.getPieceAt(new BoardLocation(j-1,i+1)).getPieceType().equals(PieceType.BLANK)){
                        int finalJ = j;
                        int finalI = i;
                        legalMoves.addWhiteMove(
                                new Move(){{
                                    addStep(new BoardLocation(finalJ, finalI));
                                    addStep(new BoardLocation(finalJ-1, finalI+1));
                                }}
                        );
                    }





                    /*
                    int finalI1 = i;
                    int finalJ1 = j;
                    search(new ArrayList<>(){{add(new ArrayList<>(){{add(new BoardLocation(finalJ1, finalI1));}});}},this.getPieceAt(new BoardLocation(finalJ1,finalI1)).getPieceType());
                     */
                    final int finalI = i;
                    final int finalJ = j;
                    //System.out.println(search(new ArrayList<Move>(){{add(new Move(){{addStep(new BoardLocation(finalJ, finalI));}});}},this.getPieceAt(new BoardLocation(finalJ, finalI)).getPieceType(),new RecursionBias(true, false, false, false)));
                    //System.out.println(search(new ArrayList<Move>(){{add(new Move(){{addStep(new BoardLocation(finalJ, finalI));}});}},this.getPieceAt(new BoardLocation(finalJ, finalI)).getPieceType(),new RecursionBias(false, true, false, false)));
                    legalMoves.addWhiteMove(removeDuplicateMoves(search(new ArrayList<>() {{
                        add(new Move() {{
                            addStep(new BoardLocation(finalJ, finalI));
                        }});
                    }},this.getPieceAt(new BoardLocation(finalJ, finalI)).getPieceType())));

                }else if(this.getPieceAt(new BoardLocation(j,i)).getPieceType().equals(PieceType.BLACK)){


                    if(j+1<=7&&i-1>-1&&this.getPieceAt(new BoardLocation(j+1,i-1)).getPieceType().equals(PieceType.BLANK)){
                        int finalJ = j;
                        int finalI = i;
                        legalMoves.addBlackMove(
                                new Move(){{
                                    addStep(new BoardLocation(finalJ, finalI));
                                    addStep(new BoardLocation(finalJ+1, finalI-1));
                                }}
                        );
                    }
                    if(j-1>=0&&i-1>-1&&this.getPieceAt(new BoardLocation(j-1,i-1)).getPieceType().equals(PieceType.BLANK)){
                        int finalJ = j;
                        int finalI = i;
                        legalMoves.addBlackMove(
                                new Move(){{
                                    addStep(new BoardLocation(finalJ, finalI));
                                    addStep(new BoardLocation(finalJ-1, finalI-1));
                                }}
                        );
                    }


                    final int finalI = i;
                    final int finalJ = j;
                    //System.out.println(search(new ArrayList<Move>(){{add(new Move(){{addStep(new BoardLocation(finalJ, finalI));}});}},this.getPieceAt(new BoardLocation(finalJ, finalI)).getPieceType(),new RecursionBias(true, false, false, false)));
                    //System.out.println(search(new ArrayList<Move>(){{add(new Move(){{addStep(new BoardLocation(finalJ, finalI));}});}},this.getPieceAt(new BoardLocation(finalJ, finalI)).getPieceType(),new RecursionBias(false, true, false, false)));
                    legalMoves.addBlackMove(removeDuplicateMoves(search(new ArrayList<>() {{
                        add(new Move() {{
                            addStep(new BoardLocation(finalJ, finalI));
                        }});
                    }},this.getPieceAt(new BoardLocation(finalJ, finalI)).getPieceType())));



                }else if(this.getPieceAt(new BoardLocation(j,i)).getPieceType().equals(PieceType.WHITE_QUEEN)){

                    if(j+1<=7&&i-1>-1&&this.getPieceAt(new BoardLocation(j+1,i-1)).getPieceType().equals(PieceType.BLANK)){
                        int finalJ = j;
                        int finalI = i;
                        legalMoves.addWhiteMove(
                                new Move(){{
                                    addStep(new BoardLocation(finalJ, finalI));
                                    addStep(new BoardLocation(finalJ+1, finalI-1));
                                }}
                        );
                    }
                    if(j-1>=0&&i-1>-1&&this.getPieceAt(new BoardLocation(j-1,i-1)).getPieceType().equals(PieceType.BLANK)){
                        int finalJ = j;
                        int finalI = i;
                        legalMoves.addWhiteMove(
                                new Move(){{
                                    addStep(new BoardLocation(finalJ, finalI));
                                    addStep(new BoardLocation(finalJ-1, finalI-1));
                                }}
                        );
                    }
                    if(j+1<=7&&i+1<=7&&this.getPieceAt(new BoardLocation(j+1,i+1)).getPieceType().equals(PieceType.BLANK)){
                        int finalJ = j;
                        int finalI = i;
                        legalMoves.addWhiteMove(
                                new Move(){{
                                    addStep(new BoardLocation(finalJ, finalI));
                                    addStep(new BoardLocation(finalJ+1, finalI+1));
                                }}
                        );
                    }
                    if(j-1>=0&&i+1<=7&&this.getPieceAt(new BoardLocation(j-1,i+1)).getPieceType().equals(PieceType.BLANK)){
                        int finalJ = j;
                        int finalI = i;
                        legalMoves.addWhiteMove(
                                new Move(){{
                                    addStep(new BoardLocation(finalJ, finalI));
                                    addStep(new BoardLocation(finalJ-1, finalI+1));
                                }}
                        );
                    }

                    final int finalI = i;
                    final int finalJ = j;
                    legalMoves.addWhiteMove(removeDuplicateMoves(search(new ArrayList<>() {{
                        add(new Move() {{
                            addStep(new BoardLocation(finalJ, finalI));
                        }});
                    }},this.getPieceAt(new BoardLocation(finalJ, finalI)).getPieceType())));


                }else if(this.getPieceAt(new BoardLocation(j,i)).getPieceType().equals(PieceType.BLACK_QUEEN)){

                    if(j+1<=7&&i-1>-1&&this.getPieceAt(new BoardLocation(j+1,i-1)).getPieceType().equals(PieceType.BLANK)){
                        int finalJ = j;
                        int finalI = i;
                        legalMoves.addBlackMove(
                                new Move(){{
                                    addStep(new BoardLocation(finalJ, finalI));
                                    addStep(new BoardLocation(finalJ+1, finalI-1));
                                }}
                        );
                    }
                    if(j-1>=0&&i-1>-1&&this.getPieceAt(new BoardLocation(j-1,i-1)).getPieceType().equals(PieceType.BLANK)){
                        int finalJ = j;
                        int finalI = i;
                        legalMoves.addBlackMove(
                                new Move(){{
                                    addStep(new BoardLocation(finalJ, finalI));
                                    addStep(new BoardLocation(finalJ-1, finalI-1));
                                }}
                        );
                    }
                    if(j+1<=7&&i+1<=7&&this.getPieceAt(new BoardLocation(j+1,i+1)).getPieceType().equals(PieceType.BLANK)){
                        int finalJ = j;
                        int finalI = i;
                        legalMoves.addBlackMove(
                                new Move(){{
                                    addStep(new BoardLocation(finalJ, finalI));
                                    addStep(new BoardLocation(finalJ+1, finalI+1));
                                }}
                        );
                    }
                    if(j-1>=0&&i+1<=7&&this.getPieceAt(new BoardLocation(j-1,i+1)).getPieceType().equals(PieceType.BLANK)){
                        int finalJ = j;
                        int finalI = i;
                        legalMoves.addBlackMove(
                                new Move(){{
                                    addStep(new BoardLocation(finalJ, finalI));
                                    addStep(new BoardLocation(finalJ-1, finalI+1));
                                }}
                        );
                    }

                    final int finalI = i;
                    final int finalJ = j;
                    legalMoves.addBlackMove(removeDuplicateMoves(search(new ArrayList<>() {{
                        add(new Move() {{
                            addStep(new BoardLocation(finalJ, finalI));
                        }});
                    }},this.getPieceAt(new BoardLocation(finalJ, finalI)).getPieceType())));


                }
            }
        }

        return legalMoves;
    }

    public String toString(){
        return Arrays.toString(this.position[0]) +"\n"+ Arrays.toString(this.position[1]) +"\n"+ Arrays.toString(this.position[2]) +"\n"+ Arrays.toString(this.position[3]) +"\n"
                + Arrays.toString(this.position[4]) +"\n"+ Arrays.toString(this.position[5]) +"\n"+ Arrays.toString(this.position[6]) +"\n"+ Arrays.toString(this.position[7]);
    }


}
