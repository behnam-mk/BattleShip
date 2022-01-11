import java.util.Scanner;

public class Menu {
    
    Scanner cmd_Scanner;

    public static void main(String[] args){

    }

    public Menu(){
        cmd_Scanner = new Scanner(System.in);
    }

    public String[] readCommand(){ 

        String[] command = cmd_Scanner.nextLine().split(" ");

        if(command == null)
            System.out.println("command is null");

        else if(command.length > 3 || command.length < 1 || command.length == 2 ){
            System.out.println("invalid command"); 
            return null;
        }

        return command;
    }

    public String[] readCommand(String s){ 

        String[] command = cmd_Scanner.nextLine().split(" ");

        if(s.equals("mainMenu")){
            if(command == null)
                System.out.println("command is null");

            else if(command.length > 2 || command.length < 1 ){
                System.out.println("invalid command"); 
                return null;
            }
        }else if(s.equals("boardMenu")){
            if(command == null)
                System.out.println("command is null");

            else if(command.length > 4 || command.length < 1 ){
                System.out.println("invalid command"); 
                return null;
            }
        }
        return command;
    }

    public boolean checkStrings(String usrName, String pass){

        if(usrName == null || pass == null){
            System.out.println("ivalid command"); 
            return false;
        }
       
        usrName = usrName.toLowerCase();

        for(int i=0; i < usrName.length(); i++){
            if ((usrName.charAt(i) >= 'a' && usrName.charAt(i) <= 'z') || (usrName.charAt(i) >= '0' && usrName.charAt(i) <= '9') || usrName.charAt(i) == '_')
                continue;
            else{
                System.out.println("\nusername format is invalid."); 
                return false;
            }
        }

        for(int i=0; i < pass.length(); i++){
            if ((pass.charAt(i) >= 'a' && pass.charAt(i) <= 'z') || (pass.charAt(i) >= '0' && pass.charAt(i) <= '9') || pass.charAt(i) == '_')
                continue;
            else{
                System.out.println("\npassword format is invalid."); 
                return false;
            }
        }
        return true;
    }

    public boolean checkStrings(String usrName){

        if(usrName == null ){
            System.out.println("ivalid command"); 
            return false;
        }
       
        usrName = usrName.toLowerCase();

        for(int i=0; i < usrName.length(); i++){
            if ((usrName.charAt(i) >= 'a' && usrName.charAt(i) <= 'z') || (usrName.charAt(i) >= '0' && usrName.charAt(i) <= '9') || usrName.charAt(i) == '_')
                continue;
            else{
                System.out.println("\nusername format is invalid."); 
                return false;
            }
        }
        return true;
    }

    public boolean checkStrings(String name, int number){

        if(name == null ){
            System.out.println("ivalid command"); 
            return false;
        }
       
        name = name.toLowerCase();

        if(!name.equals("mine") && !name.equals("antiaircraft") && !name.equals("airplane") && !name.equals("scanner") && !name.equals("invisible")){
            System.out.println("there is no product with this name."); 
            return false;
        }

        if(number <= 0 ){
            System.out.println("invalid number");
            return false;
        }

        int coins;
        if(Main.firstPlayer.isLogin())
            coins = Main.firstPlayer.getCoinCount();
        else
            coins = Main.secondPlayer.getCoinCount();

        if(Main.names.get(name)*number > coins){
            System.out.println("you don\'t have enough money"); 
            return false;
        }
        return true;
    }

    public void printBoard(Player p) {
        for(int i=0; i < 10; i++){
            for(int j=0; j < 10; j++){
                if(p.board[i][j] == null)
                    System.out.print("|  ");
                else 
                    System.out.print("|" + p.board[i][j]);
            }
            System.out.print("|\n");
        }
    }

    public void printRivalBoard(Player p) {
        for(int i=0; i < 10; i++){
            for(int j=0; j < 10; j++){
                if(p.rivalsBoard[i][j] == null)
                    System.out.print("|  ");
                else 
                    System.out.print("|" + p.rivalsBoard[i][j]);
            }
            System.out.print("|\n");
        }
    }
}
