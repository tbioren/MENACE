import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Menace implements Player {
    private HashMap<Integer, Matchbox> gameStates;
    private HashMap<Integer, Integer> storedMoves; // Stored as gameState, the bead that was chosen

    public Menace() {
        storedMoves = new HashMap<Integer, Integer>();
        gameStates = new HashMap<Integer, Matchbox>();
        generateMatchboxes(gameStates, 000000000, Game.O_PLAYER);
    }

    public Menace(String fileName) {
        System.out.println(fileName);
        storedMoves = new HashMap<Integer, Integer>();
        gameStates = new HashMap<Integer, Matchbox>();
        File f = new File(fileName);
        Scanner scan;
        try {
            scan = new Scanner(f);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            scan = new Scanner("404");
        }
        while (scan.hasNextLine()) {
            String[] values = scan.nextLine().split(",");
            ArrayList<Integer> beads = new ArrayList<Integer>();
            for (int i = 1; i < values.length; i++) {
                for (int j = 0; j < Integer.valueOf(values[i]); j++) {
                    beads.add(i - 1);
                }
            }
            gameStates.put(Integer.valueOf(values[0]), new Matchbox(beads));
        }
        scan.close();
    }

    public void generateMatchboxes(HashMap<Integer, Matchbox> states, int currentState, int nextPlayer) {
        states.put(currentState, new Matchbox());
        for (int i = 0; i < 9; i++) {
            if (Game.getSquareValue(i, currentState) == Game.EMPTY) {
                int newGameState = Game.setSquareValue(i, nextPlayer, currentState);
                if (Game.gameEnd(newGameState) == -1) {
                    generateMatchboxes(states, newGameState, nextPlayer == Game.X_PLAYER ? Game.O_PLAYER : Game.X_PLAYER);
                }
            }
        }
    }

    // Picks a random move from the Matchbox for the current state
    public int getMove(int currentState) {
        int move = gameStates.get(currentState).pickMove();
        while(Game.getSquareValue(move, currentState) != 0) {
            move = gameStates.get(currentState).pickMove();
        }
        storedMoves.put(currentState, move); // saves move to move history
        return move;
    }

    public void inputGameResult(byte won) {
        if (won == 1) {
            for(int i=0; i < 4; i++) {
                for (int key : storedMoves.keySet()) {
                    gameStates.get(key).addBead(storedMoves.get(key));
                }
            }
        }

        else if (won == 2) {
            for (int key : storedMoves.keySet()) {
                gameStates.get(key).removeBead(storedMoves.get(key));
            }
        }

        storedMoves.clear();
    }

    public void writeToFile(String name) {
        try {
            FileWriter f = new FileWriter(name);
            f.write("");
            for(int state : gameStates.keySet()) {
                f.append(state + "," + gameStates.get(state).toString() + "\n");
            }
            f.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}