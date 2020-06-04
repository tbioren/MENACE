import java.util.ArrayList;

public class Matchbox {
    private ArrayList<Integer> moves;

    public Matchbox() {
        moves = new ArrayList<Integer>();
        for(int i=0; i < 8; i++) {
            for(int j=0; j < 9; j++) {
                moves.add(j);
            }
        }
    }

    public Matchbox(ArrayList<Integer> beads) {
        moves = beads;
    }

    public int pickMove() {
        return moves.get((int) (Math.random()*moves.size()));
    }

    public void addBead(int bead) {
        moves.add(bead);
    }

    public void removeBead(int bead) {
        if(moves.indexOf(bead) != moves.lastIndexOf(bead)) {
            moves.remove(moves.indexOf(bead));
        }
    }

    public String toString() {
        String ret = "";
        int[] counts = new int[9];
        for(int i : moves) {
            counts[i] ++;
        }
        for(int i=0; i < counts.length - 1; i++) {
            ret += counts[i] + ",";
        }
        return ret + counts[counts.length-1];
    }
}