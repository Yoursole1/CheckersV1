package me.yoursole.Game;

public class MoveTreeNode {
    private Move m;
    private int id;
    private int parentid;
    private int advantage;

    public MoveTreeNode(Move m, int id, int parentid, int advantage){
        this.m=m;
        this.id = id;
        this.parentid = parentid;
        this.advantage= advantage;
    }

    public Move getMove(){
        return this.m;
    }
    public int getId(){
        return this.id;
    }
    public int getParentid(){
        return this.parentid;
    }
    public int getAdvantage(){
        return this.advantage;
    }
}
