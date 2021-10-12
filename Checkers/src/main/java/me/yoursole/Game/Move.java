package me.yoursole.Game;

import java.util.ArrayList;
import java.util.Arrays;

public class Move{
    private ArrayList<BoardLocation> move;

    public Move(ArrayList<BoardLocation> in){
        this.move=in;
    }

    public Move(){
        this.move=new ArrayList<>();
    }

    public Move addStep(BoardLocation loc){
        this.move.add(loc);
        return this;
    }
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Move)) {
            return false;
        }
        Move c = (Move) o;
        return move.equals(c.move);

    }

    public BoardLocation get(int step){
        return this.move.get(step);
    }

    public ArrayList<BoardLocation> getMove(){
        return this.move;
    }

    public int getSize(){
        return this.move.size();
    }

    public Move clone(){
        //Highlighting here is wrong
        return new Move((ArrayList<BoardLocation>) this.getMove().clone());
    }

    public String toString(){
        return this.move.toString();
    }
}
