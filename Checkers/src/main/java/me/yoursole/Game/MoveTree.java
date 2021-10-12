package me.yoursole.Game;


import java.util.ArrayList;
import java.util.LinkedList;

public class MoveTree {
    protected ArrayList<MoveTreeLayer> treeLayers;

    public MoveTree(){
        treeLayers=new ArrayList<>();
    }
    public void addLayer(MoveTreeLayer layer){
        this.treeLayers.add(layer);
    }
    public MoveTreeLayer getLayer(int i){
        return this.treeLayers.get(i);
    }
}
