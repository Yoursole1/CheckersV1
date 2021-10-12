package me.yoursole.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Board {
    /*
    private Position boardPosition = new Position(new Piece[][]{
            {new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK)}
            ,{new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK)}
            ,{new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLACK),new Piece(PieceType.BLANK),new Piece(PieceType.BLACK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK)}
            ,{new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK)}
            ,{new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLACK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK)}
            ,{new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.WHITE_QUEEN),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK)}
            ,{new Piece(PieceType.BLANK),new Piece(PieceType.BLACK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK)}
            ,{new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK)}
    },true);

     */

    private Position boardPosition = new Position(new Piece[][]{
    {new Piece(PieceType.BLACK),new Piece(PieceType.BLANK),new Piece(PieceType.BLACK),new Piece(PieceType.BLANK),new Piece(PieceType.BLACK),new Piece(PieceType.BLANK),new Piece(PieceType.BLACK),new Piece(PieceType.BLANK)}
    ,{new Piece(PieceType.BLANK),new Piece(PieceType.BLACK),new Piece(PieceType.BLANK),new Piece(PieceType.BLACK),new Piece(PieceType.BLANK),new Piece(PieceType.BLACK),new Piece(PieceType.BLANK),new Piece(PieceType.BLACK)}
    ,{new Piece(PieceType.BLACK),new Piece(PieceType.BLANK),new Piece(PieceType.BLACK),new Piece(PieceType.BLANK),new Piece(PieceType.BLACK),new Piece(PieceType.BLANK),new Piece(PieceType.BLACK),new Piece(PieceType.BLANK)}
    ,{new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK)}
    ,{new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK),new Piece(PieceType.BLANK)}
    ,{new Piece(PieceType.BLANK),new Piece(PieceType.WHITE),new Piece(PieceType.BLANK),new Piece(PieceType.WHITE),new Piece(PieceType.BLANK),new Piece(PieceType.WHITE),new Piece(PieceType.BLANK),new Piece(PieceType.WHITE)}
    ,{new Piece(PieceType.WHITE),new Piece(PieceType.BLANK),new Piece(PieceType.WHITE),new Piece(PieceType.BLANK),new Piece(PieceType.WHITE),new Piece(PieceType.BLANK),new Piece(PieceType.WHITE),new Piece(PieceType.BLANK)}
    ,{new Piece(PieceType.BLANK),new Piece(PieceType.WHITE),new Piece(PieceType.BLANK),new Piece(PieceType.WHITE),new Piece(PieceType.BLANK),new Piece(PieceType.WHITE),new Piece(PieceType.BLANK),new Piece(PieceType.WHITE)}
    },true);

    public Board() throws CloneNotSupportedException {
        startGame();
    }

    private void startGame() throws CloneNotSupportedException {
        Scanner scan = new Scanner(System.in);
        while(this.boardPosition.whiteMoves()>0&&this.boardPosition.blackMoves()>0){
            System.out.println(this.getBoardPosition());
            System.out.println(this.boardPosition.getTurn()?"It is your turn!\n":"It is the computer's turn!");

            //human turn
            if(this.boardPosition.getTurn()){
                System.out.println("Enter your move");
                String input = scan.nextLine();

                String[] moves = input.split("->");
                Move move = new Move();
                for(String mv : moves){
                    move.addStep(new BoardLocation(Integer.parseInt(mv.split(",")[0]),Integer.parseInt(mv.split(",")[1])));
                }
                if(!this.boardPosition.completeMove(move)){
                    System.out.println("Invalid Move, try again");
                    System.out.println(this.boardPosition.getLegalMoves().whiteMoves);
                }

            }
            //computer turn
            else{
                this.boardPosition.completeMove(choseMove());
                System.out.println(this.boardPosition);
            }
            this.boardPosition.updateMoves();
        }
        System.out.println((this.boardPosition.whiteMoves()>0)?"You won!":"Computer won ):");

    }

    private Move choseMove() throws CloneNotSupportedException {
        MoveTree tree = new MoveTree();
        int A = 0;
        int B = 0;
        int C = 0;
        int D = 0;
        int E = 0;
        int F = 0;
        int G = 0;
        int H = 0;
        for (int i = 0; i < 8; i++) { tree.addLayer(new MoveTreeLayer(i)); }


        for(Move move0 : this.boardPosition.getLegalMoves().blackMoves){
            //------
            Position a = new Position(this.boardPosition.clone());
            //------

            a.completeMove(move0);

            tree.getLayer(0).addNode(new MoveTreeNode(move0,A,-1,a.whiteMoves()-a.blackMoves()));
            A++;

            for(Move move1 : a.getLegalMoves().blackMoves){
                Position b = new Position((Position) a.clone());
                b.completeMove(move1);

                tree.getLayer(1).addNode(new MoveTreeNode(move1,B,A,b.whiteMoves()-b.blackMoves()));
                B++;


                for(Move move2 : b.getLegalMoves().blackMoves){
                    Position c = new Position((Position) b.clone());
                    c.completeMove(move2);

                    tree.getLayer(2).addNode(new MoveTreeNode(move2,C,B,c.whiteMoves()-c.blackMoves()));
                    C++;


                    for(Move move3 : c.getLegalMoves().blackMoves){
                        Position d = new Position((Position) c.clone());
                        d.completeMove(move3);

                        tree.getLayer(3).addNode(new MoveTreeNode(move3,D,C,d.whiteMoves()-d.blackMoves()));
                        D++;


                        for(Move move4 :  d.getLegalMoves().blackMoves){
                            Position e = new Position((Position) d.clone());
                            e.completeMove(move4);

                            tree.getLayer(4).addNode(new MoveTreeNode(move4,E,D,e.whiteMoves()-e.blackMoves()));
                            E++;


                            for(Move move5 : e.getLegalMoves().blackMoves){
                                Position f = new Position((Position) e.clone());
                                f.completeMove(move5);

                                tree.getLayer(5).addNode(new MoveTreeNode(move5,F,E,f.whiteMoves()-f.blackMoves()));
                                F++;


                                for(Move move6 : f.getLegalMoves().blackMoves){
                                    Position g = new Position((Position) f.clone());
                                    g.completeMove(move6);

                                    tree.getLayer(6).addNode(new MoveTreeNode(move6,G,F,g.whiteMoves()-g.blackMoves()));
                                    G++;


                                    for(Move move7 : g.getLegalMoves().blackMoves){
                                        Position h = new Position((Position) g.clone());
                                        g.completeMove(move7);

                                        tree.getLayer(7).addNode(new MoveTreeNode(move7,H,G,h.whiteMoves()-h.blackMoves()));
                                        H++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        HashMap<Integer,MoveTreeLayer> familyNodes = new HashMap<>();
        for(MoveTreeNode node : tree.getLayer(7).getLayer()){
            if(familyNodes.get(node.getParentid())==null){
                familyNodes.put(node.getParentid(),new MoveTreeLayer(-1){{addNode(node);}});
            }else{
                familyNodes.put(node.getParentid(),familyNodes.get(node.getParentid()).addNode(node));
            }
        }

        HashMap<Integer, Integer> advantageToFamily = new HashMap<>();
        for (int i : familyNodes.keySet()) {
            ArrayList<MoveTreeNode> nodes = familyNodes.get(i).getLayer();
            int ad=0;

            for(MoveTreeNode node : nodes){
                ad+=node.getAdvantage();
            }
            advantageToFamily.put(ad,i);
        }

        int max = 0;
        MoveTreeLayer nodes = new MoveTreeLayer(-1);
        for(int score : advantageToFamily.keySet()){
            max= Math.max(score, max);
            nodes = familyNodes.get(advantageToFamily.get(max));
        }

        for(MoveTreeNode node : tree.treeLayers.get(6).getLayer()){
            if(node.getId()==nodes.getLayer().get(0).getParentid()){
                for(MoveTreeNode node1 : tree.treeLayers.get(5).getLayer()){
                    if(node1.getId()==node.getParentid()){
                        for(MoveTreeNode node2 : tree.treeLayers.get(4).getLayer()){
                            if(node2.getId()==node1.getParentid()){
                                for(MoveTreeNode node3 : tree.treeLayers.get(3).getLayer()){
                                    if(node3.getId()==node2.getParentid()){
                                        for(MoveTreeNode node4 : tree.treeLayers.get(2).getLayer()){
                                            if(node4.getId()==node3.getParentid()){
                                                for(MoveTreeNode node5 : tree.treeLayers.get(1).getLayer()){
                                                    if(node5.getId()==node4.getParentid()){
                                                        for(MoveTreeNode node6 : tree.treeLayers.get(0).getLayer()){
                                                            if(node6.getId()==node5.getParentid()){
                                                                System.out.println(node6.getMove());
                                                                return node6.getMove();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        return null;
    }




    public Position getBoardPosition(){
        return this.boardPosition;
    }


}
