import java.util.Scanner;

public class ManualPlayer implements Player {
    private Scanner userIn;

    public ManualPlayer() {
        userIn = new Scanner(System.in);
    }

    public int getMove(int currentState) {
        boolean isValidMove = false;
        int move = 0;
        while(!isValidMove) {
            System.out.print("What's your move? ");
            move = userIn.nextInt();
            isValidMove = (Game.getSquareValue(move, currentState) == Game.EMPTY);
        }
        return move;
    }

    public void inputGameResult(byte won) {
        if(won == 1) {
            System.out.println("Yay, you won!");
        }
        
        else if(won == 2) {
            System.out.println("You lost to a computer and you should feel bad.");
        }

        else {
            System.out.println("You tied with a computer and you should feel bad.");
        }
    }

    public void writeToFile(String sdtesa) {
    
    }
}