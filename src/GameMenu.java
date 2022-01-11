public class GameMenu extends Menu {

    boolean winStatus;
    boolean drawStstus;

    public void main() {
        String[] command = readCommand("boardMenu");
        winStatus = Main.firstPlayer.win;
        drawStstus = false;

        while(!winStatus){            

            if(command == null){
                command = readCommand("boardMenu");
            }
            switch(command[0].trim().toLowerCase()){
                case "show-turn":
                    if(Main.firstPlayer.isTurn())
                        System.out.println(Main.firstPlayer.getUsername() + "\'s turn");
                    else
                        System.out.println(Main.secondPlayer.getUsername() + "\'s turn");
                    break;
                
                case "bomb":
                    if(command.length != 2){
                        System.out.println("invalid command");
                    }
                    putBombFunc(command[1].trim().toLowerCase());
                    winStatus = checkWinner();
                    break;

                case "put-airplane":
                    if(command.length != 3 ){
                        System.out.println("invalid command");
                    }
                    putAirplaneFunc(command[1].trim().toLowerCase(), command[2].trim().toLowerCase());
                    winStatus = checkWinner();

                    break;
                
                case "scanner":
                    if(command.length != 2){
                        System.out.println("invalid command");
                    }
                    putScannerFunc(command[1].trim().toLowerCase());
                    break;

                case "forfeit":
                    forfeitFunc();
                    Main.status = "registerMenu";
                    return;

                case "show-my-board":
                    Player p;
                    if(Main.firstPlayer.isTurn())
                        p = Main.firstPlayer;
                    else 
                        p = Main.secondPlayer;

                    printBoard(p);
                    break;

                case "show-rival-board":
                    Player pl;
                    if(Main.firstPlayer.isTurn())
                        pl = Main.firstPlayer;
                    else 
                        pl = Main.secondPlayer;

                    printRivalBoard(pl);
                    break;

                case "help":
                    System.out.println("show-turn");
                    System.out.println("bomb [x],[y]");
                    System.out.println("put-airplane [x],[y] [-h|-v]");
                    System.out.println("scanner [x],[y]");
                    System.out.println("forfeit");
                    System.out.println("show-my-board");
                    System.out.println("show-rival-board");
                    System.out.println("help");
                    break;

                default:
                    System.out.println("invalid command");
                break;
            }
            command = readCommand("boardMenu");
        }
    }

    private void putBombFunc(String xandy) {
        Player player;
        Player rival;

        if(Main.firstPlayer.isTurn()){
            player = Main.firstPlayer;
            rival = Main.secondPlayer;
        }
        else{
            player = Main.secondPlayer;
            rival = Main.firstPlayer;
        }

        String[] xystr = xandy.split(",");
        int y = Integer.parseInt(xystr[0]);
        int x = Integer.parseInt(xystr[1]);

        x = x - 1;
        y = y - 1;

        if(xystr.length != 2 ){
            System.out.println("invalid command");
            return;
        }

        if(x+1 < 1 || x+1 > 10 || y+1 < 1 || y+1 > 10){
            System.out.println("wrong coordination");
            return;
        }

        if(rival.board[x][y] == null){
            player.rivalsBoard[x][y] = "XX";
            System.out.println("the bomb fell into sea");
            System.out.println("turn completed");

            player.setTurn(false);
            rival.setTurn(true);
            return;
        }

        if(rival.board[x][y].charAt(0) == 'D'){
            System.out.println("this place has alredy destroyed");
            return;
        }
        
        if(rival.board[x][y].charAt(0) == 'S' || rival.board[x][y].toLowerCase().charAt(0) == 'i'){
            rival.board[x][y] = "D" + rival.board[x][y].charAt(1);
            player.rivalsBoard[x][y] = "DX";
            player.setGameScore(player.getGameScore() + 1);
            if(checkDestroy(x, y, rival, player, rival.board[x][y].charAt(1))){
                System.out.println("the rival ship " + rival.board[x][y].charAt(1) + " was destroyed");
                return;
            }
            System.out.println("the rival\'s ship was damaged");
            return;
        }

        if(rival.board[x][y] == "Mm"){
            rival.board[x][y] = "MX";
            player.setGameScore(player.getGameScore() - 1);
            player.rivalsBoard[x][y] = "MX";
            rival.rivalsBoard[x][y] = "DX";
            System.out.println("you destroyed the rival\'s mine");
            if(player.board[x][y] == null){
                System.out.println("turn completed");

                player.setTurn(false);
                rival.setTurn(true);
                return;
            }
            if(player.board[x][y].charAt(0) == 'S' || player.board[x][y].charAt(0) == 'I'){
                player.board[x][y] = "D" + player.board[x][y].charAt(1);
                checkDestroy(x, y, player, rival, player.board[x][y].charAt(1));
            }
            System.out.println("turn completed");

            player.setTurn(false);
            rival.setTurn(true);
            return;
        }

        if(rival.board[x][y] == "MX"){
            System.out.println("this place has already destroyed");
            return;
        }

    }

    private boolean checkWinner() {

        Player player;
        Player rival;

        boolean playerStat = true;
        boolean rivalStat = false;

        if(Main.firstPlayer.isTurn()){
            player = Main.firstPlayer;
            rival = Main.secondPlayer;
        }
        else{
            player = Main.secondPlayer;
            rival = Main.firstPlayer;
        }

        for(int i=0; i < 10 && playerStat; i++){
            for(int j = 0; j < 10; j++){
                if(player.board[i][j] == null)
                    continue;
                if(player.board[i][j].charAt(0) == 'S' || player.board[i][j].toLowerCase().charAt(0) == 'i'){
                    player.win = false;
                    playerStat = false;
                    break;
                }
            }
        }

        for(int i=0; i < 10 && rivalStat; i++){
            for(int j = 0; j < 10; j++){
                
                if(rival.board[i][j] == null)
                    continue;
                if(rival.board[i][j].charAt(0) == 'S' || rival.board[i][j].toLowerCase().charAt(0) == 'i'){
                    rival.win = false;
                    rivalStat = false;
                    break;
                }
            }
        }
        if(!rivalStat && !playerStat)
            return false;
        
        if(rivalStat && playerStat){
            drawStstus = true;
            System.out.println("draw");

            for(int i=0; i < Main.users.size(); i++){
                if(Main.users.get(i).getUsername().equals(rival.getUsername())){
                    Main.users.get(i).setCoinCount(Main.users.get(i).getCoinCount() + rival.getGameScore() + 25);
                    Main.users.get(i).setDrawCount(Main.users.get(i).getDrawCount() + 1);
                    Main.users.get(i).setMainScore(Main.users.get(i).getMainScore() + rival.getGameScore());
                    break;
                }
            }
            for(int i=0; i < Main.users.size(); i++){
                if(Main.users.get(i).getUsername().equals(player.getUsername())){
                    Main.users.get(i).setCoinCount(Main.users.get(i).getCoinCount() + player.getGameScore() + 25);
                    Main.users.get(i).setDrawCount(Main.users.get(i).getDrawCount() + 1);
                    Main.users.get(i).setMainScore(Main.users.get(i).getMainScore() + player.getGameScore());
                    break;
                }
            }
            return true;
        }

        if(rival.win){
            System.out.print(rival.getUsername() + " is winner");
            for(int i=0; i < Main.users.size(); i++){
                if(Main.users.get(i).getUsername().equals(rival.getUsername())){
                    Main.users.get(i).setCoinCount(Main.users.get(i).getCoinCount() + rival.getGameScore() + 50);
                    Main.users.get(i).setWinCount(Main.users.get(i).getWinCount() + 1);
                    Main.users.get(i).setMainScore(Main.users.get(i).getMainScore() + rival.getGameScore());
                    break;
                }
            }
            for(int i=0; i < Main.users.size(); i++){
                if(Main.users.get(i).getUsername().equals(player.getUsername())){
                    Main.users.get(i).setCoinCount(Main.users.get(i).getCoinCount() + player.getGameScore());
                    Main.users.get(i).setLoosCount(Main.users.get(i).getLoosCount() + 1);
                    Main.users.get(i).setMainScore(Main.users.get(i).getMainScore() + player.getGameScore());
                    break;
                }
            }
        }
        else{
            System.out.print(player.getUsername() + " is winner");
            for(int i=0; i < Main.users.size(); i++){
                if(Main.users.get(i).getUsername().equals(player.getUsername())){
                    Main.users.get(i).setCoinCount(Main.users.get(i).getCoinCount() + player.getGameScore() + 50);
                    Main.users.get(i).setWinCount(Main.users.get(i).getWinCount() + 1);
                    break;
                }
            }
            for(int i=0; i < Main.users.size(); i++){
                if(Main.users.get(i).getUsername().equals(rival.getUsername())){
                    Main.users.get(i).setCoinCount(Main.users.get(i).getCoinCount() + rival.getGameScore());
                    Main.users.get(i).setLoosCount(Main.users.get(i).getLoosCount() + 1);
                    break;
                }
            }
        }
        return true;
    }

    private void putAirplaneFunc(String xandy, String horv) {
        Player player;
        Player rival;

        if(Main.firstPlayer.isTurn()){
            player = Main.firstPlayer;
            rival = Main.secondPlayer;
        }
        else{
            player = Main.secondPlayer;
            rival = Main.firstPlayer;
        }

        String[] xystr = xandy.split(",");
        int y = Integer.parseInt(xystr[0]);
        int x = Integer.parseInt(xystr[1]);

        if(xystr.length != 2 ){
            System.out.println("invalid command");
            return;
        }

        if(x < 1 || x > 10 || y < 1 || y > 10){
            System.out.println("wrong coordination");
            return;
        }

        if(horv.length() != 2 || horv.charAt(0) != '-'){
                System.out.println("invalid direction");
                return;
        }     
        if(horv.charAt(1) != 'h' && horv.charAt(1) != 'v'){
            System.out.println("invalid direction");
            return;
        }  

        if(player.products.get("airplane") == null){
            System.out.println("you don\'t have this type of ship");
            return;
        }

        if(player.products.get("airplane").getQty() < 1){
            System.out.println("you don\'t have this type of ship");
            return;
        }

        x = x - 1;
        y = y - 1;
        boolean anyHit = false;
        if(horv.charAt(1) == 'h'){
            if(x + 2 > 10 || y + 5 > 10){
                System.out.println("off the board");
                return;
            }
            for(int i=x ; i < x + 2 && !anyHit; i++){
                for(int j=y ; j < y + 5 && !anyHit; j++){
                    if(rival.board[i][j] == null)
                        continue;
                    if(rival.board[i][j].charAt(0) == 'S' || rival.board[i][j].toLowerCase().charAt(0) == 'i' || rival.board[i][j].charAt(0) == 'M'){
                        anyHit = true;
                        break;
                    }
                }
            }
        }else{
            if(x + 5 > 10 || y + 2 > 10){
                System.out.println("off the board");
                return;
            }
            for(int i = x; i < x + 5 && !anyHit; i++){
                for(int j = y; j < y + 2 && !anyHit; j++){
                    if(rival.board[i][j] == null)
                        continue;
                    if(rival.board[i][j].charAt(0) == 'S'|| rival.board[i][j].toLowerCase().charAt(0) == 'i' || rival.board[i][j].toLowerCase().charAt(0) == 'M'){
                        anyHit = true;
                        break;
                    }
                }
            }
        }
        if(!anyHit){
            System.out.println("target not found");
            player.products.get("airplane").setQty(player.products.get("airplane").getQty() -1);
            player.setTurn(false);
            rival.setTurn(true);
            System.out.println("turn completed");
            return;
        }
    
        int count = 0;
        if(horv.charAt(1) == 'h'){
            for(int i=x; i < x + 2; i++){
                for(int j=y; j < y + 5; j++){
                    if(checkCollision(i, j, rival)){
                        player.products.get("airplane").setQty(player.products.get("airplane").getQty() -1);
                        player.setTurn(false);
                        rival.setTurn(true);
                        System.out.println("turn completed");
                        return;
                    }
                    if(rival.board[i][j] == null)
                        continue;

                    if(rival.board[i][j].charAt(0) == 'S' || rival.board[i][j].toLowerCase().charAt(0) == 'i'){
                        rival.board[i][j] = "D" + rival.board[i][j].charAt(1);
                        player.rivalsBoard[i][j] = "DX";
                        player.setGameScore(player.getGameScore() + 1);
                        player.products.get("airplane").setQty(player.products.get("airplane").getQty() -1);
                        if(checkDestroy(i, j, rival, player, rival.board[i][j].charAt(1))){
                            count++;
                        }
                    }

                    if(rival.board[i][j] == "Mm"){
                        player.products.get("airplane").setQty(player.products.get("airplane").getQty() -1);
                        rival.board[i][j] = "MX";
                        player.setGameScore(player.getGameScore() - 1);
                        player.rivalsBoard[i][j] = "MX";
                        rival.rivalsBoard[i][j] = "DX";
                        System.out.println("you destroyed the rival\'s mine");
                        if(player.board[i][j] == null){
                            System.out.println("turn completed");

                            player.setTurn(false);
                            rival.setTurn(true);
                            return;
                        }
                        if(player.board[i][j].charAt(0) == 'S' || player.board[j][i].charAt(0) == 'I'){
                            player.board[i][j] = "D" + player.board[j][i].charAt(1);
                            checkDestroy(i, j, player, rival, player.board[i][j].charAt(1));
                        }

                        System.out.println(count + " pieces of rival\'s ships was demaged");

                        System.out.println("turn completed");

                        player.setTurn(false);
                        rival.setTurn(true);
                        return;
                    }
                }
            }
        }else{
            for(int i = x; i < x + 5; i++){
                for(int j = y; j < y + 2; j++){
                    if(checkCollision(i, j, rival)){
                        player.products.get("airplane").setQty(player.products.get("airplane").getQty() -1);
                        player.setTurn(false);
                        rival.setTurn(true);
                        System.out.println("turn completed");
                        return;
                    }
                    if(rival.board[i][j] == null)
                        continue;
                    if(rival.board[i][j].charAt(0) == 'S' || rival.board[i][j].toLowerCase().charAt(0) == 'i'){
                        rival.board[i][j] = "D" + rival.board[i][j].charAt(1);
                        player.rivalsBoard[i][j] = "DX";
                        player.setGameScore(player.getGameScore() + 1);
                        player.products.get("airplane").setQty(player.products.get("airplane").getQty() -1);
                        if(checkDestroy(i, j, rival, player, rival.board[i][j].charAt(1))){
                            count++;
                        }
                    }
                    if(rival.board[i][j] == "Mm"){
                        player.products.get("airplane").setQty(player.products.get("airplane").getQty() -1);
                        rival.board[i][j] = "MX";
                        player.setGameScore(player.getGameScore() - 1);
                        player.rivalsBoard[i][j] = "MX";
                        rival.rivalsBoard[i][j] = "DX";
                        System.out.println("you destroyed the rival\'s mine");
                        if(player.board[i][j] == null){
                            System.out.println("turn completed");

                            player.setTurn(false);
                            rival.setTurn(true);
                            return;
                        }
                        if(player.board[i][j].charAt(0) == 'S' || player.board[i][j].charAt(0) == 'I'){
                            player.board[i][j] = "D" + player.board[i][j].charAt(1);
                            checkDestroy(i, j, player, rival, player.board[i][j].charAt(1));
                        }

                        System.out.println(count + " pieces of rival\'s ships was demaged");
                        
                        System.out.println("turn completed");

                        player.setTurn(false);
                        rival.setTurn(true);
                        return;
                    }
                }
            }
        }

        System.out.println(count + "pieces of rival\'s ships was demaged");
        player.setTurn(false);
        rival.setTurn(true);
        System.out.println("turn completed");
    }

    private boolean checkDestroy(int i, int j, Player rival, Player player, char c) {
        int randCode = rival.codeBoard[i][j];
        int goal = Integer.parseInt(String.valueOf(c));
        int count = 0;

        for(int k=0; k < 10; k++){
            for(int l=0; l < 10; l++){
                if(rival.codeBoard[k][l] == randCode){
                    if(rival.board[k][l].charAt(0) == 'D')
                        count++;
                }   
            }
        }
        if(count == goal){
            for(int k=0; k < 10; k++){
                for(int l=0; l < 10; l++){
                    if(rival.codeBoard[k][l] == randCode){
                        rival.board[k][l] = "D" + c;
                        player.rivalsBoard[k][l] = "D" + c;
                    }   
                }
            }
            return true;
        }
        
        return false;
    }

    private boolean checkCollision(int i, int j, Player rival) {
        if(rival.antiAircrafts.size() <= 0)
            return false;
        for(int k=0; k < rival.antiAircrafts.size(); k++){
            if(rival.antiAircrafts.get(k).getDirection() == 'h'){
                for(int g=rival.antiAircrafts.get(k).getStart() - 1; g < rival.antiAircrafts.get(k).getStart() + 2; g++){
                    if(rival.antiAircrafts.get(k).getStart() - 1 == i && g == j){
                        System.out.println("the rival\'s antiaircraft destroyed your airplane");
                        rival.antiAircrafts.remove(k);
                        rival.products.get("antiaircraft").setQty(rival.products.get("antiaircraft").getQty() - 1);
                        return true;
                    }
                }
            }else{
                for(int g=rival.antiAircrafts.get(k).getStart() - 1; g < rival.antiAircrafts.get(k).getStart() + 2; g++){
                    if(rival.antiAircrafts.get(k).getStart() - 1 == j && g == i){
                        System.out.println("the rival\'s antiaircraft destroyed your airplane");
                        rival.antiAircrafts.remove(k);
                        rival.products.get("antiaircraft").setQty(rival.products.get("antiaircraft").getQty() - 1);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void forfeitFunc() {
        Player player;
        Player rival;

        if(Main.firstPlayer.isTurn()){
            player = Main.firstPlayer;
            rival = Main.secondPlayer;
        }
        else{
            player = Main.secondPlayer;
            rival = Main.firstPlayer;
        }

        System.out.println(player.getUsername() + " is forfeited");
        System.out.println(rival.getUsername() + " is winner");

        for(int i=0; i < Main.users.size(); i++){
            if(Main.users.get(i).getUsername().equals(player.getUsername())){
                Main.users.get(i).setLoosCount(Main.users.get(i).getLoosCount() + 1);
                Main.users.get(i).setMainScore(Main.users.get(i).getMainScore() - 1);
                Main.users.get(i).setCoinCount(Main.users.get(i).getCoinCount() - 50);
                if(Main.users.get(i).getCoinCount() < 0)
                    Main.users.get(i).setCoinCount(0);
            }
            else if(Main.users.get(i).getUsername().equals(rival.getUsername())){
                Main.users.get(i).setWinCount(Main.users.get(i).getWinCount() + 1);
                Main.users.get(i).setMainScore(Main.users.get(i).getMainScore() + 2);
                Main.users.get(i).setCoinCount(Main.users.get(i).getCoinCount() + player.getGameScore());
            }
        }
        winStatus = true;
    }

    private void putScannerFunc(String xandy) {
        Player player;
        Player rival;

        if(Main.firstPlayer.isTurn()){
            player = Main.firstPlayer;
            rival = Main.secondPlayer;
        }
        else{
            player = Main.secondPlayer;
            rival = Main.firstPlayer;
        }
        
        String[] xystr = xandy.split(",");
        int y = Integer.parseInt(xystr[0]);
        int x = Integer.parseInt(xystr[1]);

        if(xystr.length != 2){
            System.out.println("invalid command");
            return;
        }

        if(x < 1 || x > 10 || y < 1 || y > 10){
            System.out.println("wrong coordination");
            return;
        }

        if(x + 3 > 10 || y + 3 > 10){
            System.out.println("off the board");
            return;
        }

        if(player.products.get("scanner") == null){
            System.out.println("you don\'t have enough scanner");
            return;
        }

        if(player.products.get("scanner") == null){
            System.out.println("you don\'t have enough scanner");
            return;
        }

        if(player.products.get("scanner").getQty() < 1){
            System.out.println("you don\'t have enough scanner");
            return;
        }

        for(int i=x-1; i < x+2; i++){
            for(int j=y-1; j < y+2; j++){
                if(rival.board[i][j] == null)
                    System.out.print("|  ");
                else if(rival.board[i][j].charAt(0) == 'S'){
                    System.out.print("|SX");
                }else 
                    System.out.print("|  ");
            }
            System.out.print("|\n");
        }
    }
    
}
