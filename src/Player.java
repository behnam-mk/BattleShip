import java.util.ArrayList;
import java.util.HashMap;

public class Player extends User {
    
    int gameScore;

    String[][] board;
    int[][] codeBoard;
    String[][] rivalsBoard;
    int gainCoin;

    boolean win;;

    HashMap<String, Product> products;

    private boolean turn;
    private boolean login;

    ArrayList<AntiAircraft> antiAircrafts = new ArrayList<AntiAircraft>();


    public int getGameScore() {
        return this.gameScore;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    public boolean isLogin() {
        return this.login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public boolean isTurn() {
        return this.turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public Player(){
        win = false;
        products = new HashMap<String, Product>();

        board = new String[10][10];
        codeBoard = new int[10][10];
        rivalsBoard = new String[10][10];

        this.turn = false;
        this.login = false;
        gameScore = 0;

        for(int i=0; i<10; i++)
            for(int j=0; j<10; j++)
                codeBoard[i][j] = 0;
    }

}
