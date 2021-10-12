package me.yoursole.Game;

import java.util.ArrayList;

public class Pair {

    ArrayList<Move> whiteMoves;
    ArrayList<Move> blackMoves;


    public Pair(ArrayList<Move> a, ArrayList<Move> b){
        this.whiteMoves=a;
        this.blackMoves=b;
    }
    public Pair(){
        whiteMoves = new ArrayList<>();
        blackMoves = new ArrayList<>();
    }
    public boolean equals(Object obj) {
        return (this == obj);
    }

    public ArrayList<Move> getWhiteMoves(){
        return this.whiteMoves;
    }


    public ArrayList<Move> getBlackMoves(){
        return this.blackMoves;
    }

    public void addWhiteMove(Move a){
        this.whiteMoves.add(a);
    }

    public void addBlackMove(Move a){
        this.blackMoves.add(a);
    }

    public void addWhiteMove(ArrayList<Move> a){
        for (int i = 0; i < a.size(); i++) {
            if(a.get(i).getSize()==1){
                a.remove(i);
            }
        }

        this.whiteMoves.addAll(a);
    }

    public void addBlackMove(ArrayList<Move> a){
        for (int i = 0; i < a.size(); i++) {
            if(a.get(i).getSize()==1){
                a.remove(i);
            }
        }

        this.blackMoves.addAll(a);
    }

    public String toString(){
        return "White Moves\n"+this.whiteMoves +"\nBlack Moves\n"+this.blackMoves;
    }

}
