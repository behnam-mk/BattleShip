public class User{
    
    private String username;
    private String password;
    private int mainScore;
    private int coinCount;

    private int winCount;
    private int loosCount;
    private int drawCount;

    private int allShipsCount;

    private int s1_ShipsCount;
    private int s2_ShipsCount;
    private int s3_ShipsCount;
    private int s4_ShipsCount;

    public User(){
        username = "";
        password = "";
        mainScore = 0;
        
        coinCount = 50;
        
        winCount = 0;
        loosCount = 0;
        drawCount = 0;
        allShipsCount = 10;
        s1_ShipsCount = 4;
        s2_ShipsCount = 3;
        s3_ShipsCount = 2;
        s4_ShipsCount = 1;

    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMainScore() {
        return this.mainScore;
    }

    public void setMainScore(int mainScore) {
        this.mainScore = mainScore;
    }

    public int getCoinCount() {
        return this.coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public int getWinCount() {
        return this.winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getLoosCount() {
        return this.loosCount;
    }

    public void setLoosCount(int loosCount) {
        this.loosCount = loosCount;
    }

    public int getDrawCount() {
        return this.drawCount;
    }

    public void setDrawCount(int drawCount) {
        this.drawCount = drawCount;
    }

    public int getAllShipsCount() {
        return this.allShipsCount;
    }

    public void setAllShipsCount(int allShipsCount) {
        this.allShipsCount = allShipsCount;
    }

    public int getS1_ShipsCount() {
        return this.s1_ShipsCount;
    }

    public void setS1_ShipsCount(int s1_ShipsCount) {
        this.s1_ShipsCount = s1_ShipsCount;
    }

    public int getS2_ShipsCount() {
        return this.s2_ShipsCount;
    }

    public void setS2_ShipsCount(int s2_ShipsCount) {
        this.s2_ShipsCount = s2_ShipsCount;
    }

    public int getS3_ShipsCount() {
        return this.s3_ShipsCount;
    }

    public void setS3_ShipsCount(int s3_ShipsCount) {
        this.s3_ShipsCount = s3_ShipsCount;
    }

    public int getS4_ShipsCount() {
        return this.s4_ShipsCount;
    }

    public void setS4_ShipsCount(int s4_ShipsCount) {
        this.s4_ShipsCount = s4_ShipsCount;
    }
    
}