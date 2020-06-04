public interface Player {
    public static final byte WON = 1, LOST = 2, TIE = 0;
    int getMove(int currentState);
    void inputGameResult(byte won);
    void writeToFile(String sdf);
}