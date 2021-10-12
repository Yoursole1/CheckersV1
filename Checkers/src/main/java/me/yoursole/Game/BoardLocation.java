package me.yoursole.Game;


import java.util.Arrays;

public class BoardLocation {
    private int[] location = new int[2];

    public BoardLocation(int x, int y){
        if(x<8&&x>-1&&y<8&&y>-1){
            this.location[0]=x;
            this.location[1]=y;
        }else{
            throw new IllegalArgumentException("Board Location not within board Dimensions (0-7)");
        }
    }
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BoardLocation)) {
            return false;
        }
        BoardLocation c = (BoardLocation) o;
        return Arrays.compare(location, c.location) == 0;

    }
    public int[] getLocation(){
        return this.location;
    }

    public int getX(){
        return this.location[0];
    }

    public int getY(){
        return this.location[1];
    }

    public int getArrayX(){return 7-this.location[1];}

    public int getArrayY(){return this.location[0];}

    public String toString(){
        return Arrays.toString(this.location);
    }
}
