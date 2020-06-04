public class Game {
    private int games;
    private Player xPlayer;
    private Player oPlayer;
    static final int EMPTY = 0, X_PLAYER = 1, O_PLAYER = 2;

    public static void main(String[] args) {
        new Game(args);
    }

    public static void printBoard(int gameState) {
        for(int i=0; i < 9; i+= 3) {
            for(int j=0; j < 3; j++) {
                if(getSquareValue(i+j, gameState) == 1) {
                    System.out.print("X ");
                }
                else if(getSquareValue(i+j, gameState) == 2) {
                    System.out.print("O ");
                }
                else {
                    System.out.print("_ ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * 
     * @param gameState The game state to be passed
     * @return Return 0 if tie, return -1 if game is not over, retrurn player's value if player won
     */
    public static int gameEnd(int gameState) {
        // vertical
        for(int offset = 0; offset < 3; offset ++) {
            int player = getSquareValue(offset, gameState);
            if(player != 0 && player == getSquareValue(offset + 3, gameState) && player == getSquareValue(offset + 6, gameState)) {
                return player;
            }
        }

        // horizontal
        for(int offset = 0; offset <= 6; offset += 3) {
            int player = getSquareValue(offset, gameState);
            if(player != 0 && player == getSquareValue(offset + 1, gameState) && player == getSquareValue(offset + 2, gameState)) {
                return player;
            }
        }

        // diagonal
        int player = getSquareValue(0, gameState);
        if(player != 0 && player == getSquareValue(4, gameState) && player == getSquareValue(8, gameState)) {
            return player;
        }

        // diagonal
        player = getSquareValue(2, gameState);
        if(player != 0 && player == getSquareValue(4, gameState) && player == getSquareValue(6, gameState)) {
            return player;
        }

        // checks tie
        boolean allFilled = true;
        for(int i=0; i < 9 && allFilled; i++) {
            if(getSquareValue(i, gameState) == 0) {
                allFilled = false;
            }
        }

        if(allFilled) {
            return 0;
        }

        return -1;  // returns -1 if game not over
    }

    public static int getSquareValue(int square, int gameState) {
        square = 8 - square;
        return (gameState % (int) Math.pow(10, square+1) / (int) Math.pow(10, square));
    }

    public static int setSquareValue(int square, int value, int gameState) {
        int difference = value - getSquareValue(square, gameState);
        return gameState + (int)Math.pow(10, 8-square) * difference;
    }

    private void playGame() {
        int gameCounter = 0;
        for(int i=0; i < games; i++) {
            gameCounter ++;
            System.out.printf("Game %d\n", gameCounter);
            boolean gameOver = false;
            int currentState = 0;
            boolean isO = true;
            while(!gameOver) {
                printBoard(currentState);
                if(isO) {
                    int move = oPlayer.getMove(currentState);
                    currentState = setSquareValue(move, O_PLAYER, currentState);
                }
                else {
                    int move = xPlayer.getMove(currentState);
                    currentState = setSquareValue(move, X_PLAYER, currentState);
                }

                isO = !isO;
                int end = gameEnd(currentState);
                if(end != -1) {
                    gameOver = true;
                    if(end == X_PLAYER) {
                        xPlayer.inputGameResult(Player.WON);
                        oPlayer.inputGameResult(Player.LOST);
                    }

                    else if(end == O_PLAYER) {
                        oPlayer.inputGameResult(Player.WON);
                        xPlayer.inputGameResult(Player.LOST);
                    }

                    else {
                        oPlayer.inputGameResult(Player.TIE);
                        xPlayer.inputGameResult(Player.TIE);
                    }
                }
            }
        }
        xPlayer.writeToFile("xPlayer.men");
        oPlayer.writeToFile("oPlayer.men");
    }

    public Game(String[] args) {
        if(args.length == 0) {
            xPlayer = new Menace();
            oPlayer = new Menace();
            games = 1024;
        }
        else if(args.length == 1) {
            xPlayer = new Menace();
            oPlayer = new Menace();
            games = Integer.valueOf(args[0]);
        }
        else {
            games = Integer.valueOf(args[0]);
            switch(args[1]) {
                case "-f":
                    if(args.length == 4) {
                        xPlayer = new Menace(args[2]);
                        oPlayer = new Menace(args[3]);
                    }
                    else if(args.length == 3) {
                        xPlayer = new Menace(args[2]);
                        oPlayer = new Menace();
                    }
                    break;
                
                case "-mX":
                    if(args.length == 3) {
                        xPlayer = new ManualPlayer();
                        oPlayer = new Menace(args[2]);
                    }

                    else if(args.length == 2) {
                        xPlayer = new ManualPlayer();
                        oPlayer = new Menace();
                    }
                    break;

                case "-mO":
                    if(args.length == 3) {
                        oPlayer = new ManualPlayer();
                        xPlayer = new Menace(args[2]);
                    }

                    else if(args.length == 2) {
                        oPlayer = new ManualPlayer();
                        xPlayer = new Menace();
                    }
                    break;

                default:
                    System.out.println("You done screwed up!");
                    break;
            }
        }
        playGame();
    }
}