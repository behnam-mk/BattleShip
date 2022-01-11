public class BoardMenu extends Menu {

    public void main() {
        String[] command = readCommand("boardMenu");
        
        while(true){            

            if(command == null){
                command = readCommand("boardMenu");
            }
            switch(command[0].trim().toLowerCase()){
                case "put":
                    if(command.length != 4){
                        System.out.println("invalid command");
                    }
                    putShipFunc(command[1].trim().toLowerCase(), command[2].trim().toLowerCase(), command[3].trim().toLowerCase());
                    break;
                
                case "put-mine":
                    if(command.length != 2){
                        System.out.println("invalid command");
                    }
                    putMineFunc(command[1].trim().toLowerCase());
                    break;

                case "invisible":
                    if(command.length != 2){
                        System.out.println("invalid command");
                    }
                    putInvisibleFunc(command[1].trim().toLowerCase());
                    break;
                
                case "put-antiaircraft":
                    if(command.length != 3){
                        System.out.println("invalid command");
                    }
                    putAntiaircraftFunc(command[1].trim().toLowerCase(), command[2].trim().toLowerCase());
                    break;

                case "finish-arranging":
                    if(Main.firstPlayer.isTurn()){
                        if(Main.firstPlayer.getAllShipsCount() == 0){
                            Main.firstPlayer.setTurn(false);
                            Main.secondPlayer.setTurn(true);
                            System.out.println("turn completed.");
                            break;
                        }else{
                            System.out.println("you must put all ships on the board");
                            break;
                        }
                    }
                    else{
                        if(Main.secondPlayer.getAllShipsCount() == 0){
                            Main.secondPlayer.setTurn(false);
                            Main.firstPlayer.setTurn(true);
                            System.out.println("turn completed.");
                            Main.status = "gameMenu";
                            return;
                        }else{
                            System.out.println("you must put all ships on the board");
                            break;
                        }
                    }

                case "show-my-board":
                    Player p;
                    if(Main.firstPlayer.isTurn())
                        p = Main.firstPlayer;
                    else 
                        p = Main.secondPlayer;

                    printBoard(p);
                    break;

                case "help":
                    System.out.println("put s[a] [x],[y] [-h|-v]");
                    System.out.println("put-mine [x],[y]");
                    System.out.println("put-antiaircraft [s] [-h|-v]");
                    System.out.println("invisible [x],[y]");
                    System.out.println("finish-arranging");
                    System.out.println("show-my-board");
                    System.out.println("help");
                    break;

                default:
                    System.out.println("invalid command");
                break;
            }
            command = readCommand("boardMenu");
        }
    }

    private void putAntiaircraftFunc(String s, String horv) {
        Player player;

        if(Main.firstPlayer.isTurn())
            player = Main.firstPlayer;
        else
            player = Main.secondPlayer;
        
        int start = Integer.parseInt(s);

        if(start > 10 || start < 1){
            System.out.println("invalid coordination");
            return;
        }

        if(start-1 + 3 > 10 ){
            System.out.println("off the board");
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

        if(player.products.get("antiaircraft") == null){
            System.out.println("you don\'t have enough antiaircraft");
            return;
        }

        if(player.products.get("antiaircraft").getQty() < 1){
            System.out.println("you don\'t have enough antiaircraft");
            return;
        }

        if(horv.charAt(1) == 'h'){
            for(int i=start-1; i < start + 2; i++){
                if(player.board[start-1][i] == null)
                    player.board[start-1][i] = "AA";
            }
        }else{
            for(int j=start-1; j < start + 2; j++){
                if(player.board[j][start - 1] == null)
                    player.board[j][start - 1] = "AA";
            }
        }
        player.products.get("antiaircraft").setQty(player.products.get("antiaircraft").getQty()-1);

        AntiAircraft aa = new AntiAircraft();
        aa.setStart(start);
        aa.setDirection(horv.charAt(1));

        player.antiAircrafts.add(aa);
    }

    private void putInvisibleFunc(String xandy) {
        Player player;

        if(Main.firstPlayer.isTurn())
            player = Main.firstPlayer;
        else
            player = Main.secondPlayer;
        
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

        if(player.products.get("invisible") == null){
            System.out.println("you don\'t have enough invisible");
            return;
        }

        if(player.products.get("invisible").getQty() < 1){
            System.out.println("you don\'t have enough invisible");
            return;
        }
           
        if(player.board[x-1][y-1] == null || player.board[x-1][y-1].toLowerCase().charAt(0) != 's'){
            System.out.println("there no ship on this place on the board");
            return;
        }else if(player.board[x-1][y-1].toLowerCase().charAt(0) == 'l'){
            System.out.println("this place has already made invisible");
            return;
        }
        else{
            player.products.get("invisible").setQty(player.products.get("invisible").getQty() - 1);
            player.board[x-1][y-1] = "I".concat(String.valueOf(player.board[x-1][y-1].charAt(1)));
        }
    }

    private void putMineFunc(String xandy) {
        Player player;

        if(Main.firstPlayer.isTurn())
            player = Main.firstPlayer;
        else
            player = Main.secondPlayer;
        
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

        if(player.products.get("mine") == null){
            System.out.println("you don\'t have enough mine");
            return;
        }

        if(player.products.get("mine").getQty() < 1){
            System.out.println("you don\'t have enough mine");
            return;
        }
           
        if(player.board[x-1][y-1] != null){
            System.out.println("collision with the other ship or mine on the board");
            return;
        }else{
            player.products.get("mine").setQty(player.products.get("mine").getQty() - 1);
            player.board[x-1][y-1] = "Mm";
        }

    }

    private void putShipFunc(String ship, String xandy, String horv) {        
        Player player;

        if(Main.firstPlayer.isTurn())
            player = Main.firstPlayer;
        else
            player = Main.secondPlayer;

        int a = Integer.parseInt(ship.substring(1));

        String[] xystr = xandy.split(",");
        int y = Integer.parseInt(xystr[0]);
        int x = Integer.parseInt(xystr[1]);

        if(ship.length() != 2 || xystr.length != 2 ){
            System.out.println("invalid command");
            return;
        }
        if(a > 4 || a < 1 ){
            System.out.println("invalid ship number");
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

        if(horv.charAt(1) == 'h'){
            if(y-1 + a > 10){
                System.out.println("off the board");
                return;
            }
            for(int i=y-1; i < y-1+a; i++){
                if(player.board[x-1][i] != null){
                    System.out.println("collision with the other ship or mine on the board");
                    return;
                }
            }
        }else{
            if(x-1 + a > 10){
                System.out.println("off the board");
                return;
            }
            for(int j=x-1; j < x-1+a; j++){
                if(player.board[j][y-1] != null){
                    System.out.println("collision with the other ship or mine on the board");
                    return;
                }
            }
        }

        if(a == 1 && player.getS1_ShipsCount() < 1){
            System.out.println("you don\'t have this type of ship");
            return;
        }else if(a == 2 && player.getS2_ShipsCount() < 1){
            System.out.println("you don\'t have this type of ship");
            return;
        }else if(a == 3 && player.getS3_ShipsCount() < 1){
            System.out.println("you don\'t have this type of ship");
            return;
        }else if(a == 4 && player.getS4_ShipsCount() < 1){
            System.out.println("you don\'t have this type of ship");
            return;
        }

        //eventully, now we can save info (Ships) in player board and codeBoard array ;D
        
        int randCode = (int) (Math.random()*100001) + 1;
        if(horv.charAt(1) == 'h')
            for(int i=y-1; i < y-1+a; i++){
                player.board[x-1][i] = "S".concat(String.valueOf(a));
                player.codeBoard[x-1][i] = randCode;
            }
        else
            for(int j=x-1; j < x-1+a; j++){
                player.board[j][y-1] = "S".concat(String.valueOf(a));
                player.codeBoard[j][y-1] = randCode;
            }

        switch(a){
            case 1:
                player.setS1_ShipsCount(player.getS1_ShipsCount() - 1);
                break;
            case 2:
                player.setS2_ShipsCount(player.getS2_ShipsCount() - 1);
                break;
            case 3:
                player.setS3_ShipsCount(player.getS3_ShipsCount() - 1);
                break;
            case 4:
                player.setS4_ShipsCount(player.getS4_ShipsCount() - 1);
                break;
            default:
            break;
        }
        player.setAllShipsCount(player.getAllShipsCount() - 1);
    } 
}
