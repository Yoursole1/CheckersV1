package me.yoursole.Game;

import java.util.ArrayList;

public class MoveTreeLayer {
    private ArrayList<MoveTreeNode> layer = new ArrayList<>();
    private int layerNum;

    public MoveTreeLayer(int layerNum){
        this.layerNum=layerNum;
    }

    public MoveTreeLayer addNode(MoveTreeNode n){
        this.layer.add(n);
        return this;
    }

    public MoveTreeNode getNode(int i){
        return this.layer.get(i);
    }

    public int getLayerNum(){
        return this.layerNum;
    }

    public ArrayList<MoveTreeNode> getLayer(){
        return this.layer;
    }
}
