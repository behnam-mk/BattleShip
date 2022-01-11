import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static ArrayList<User> users;

    public static String status;

    public static Player firstPlayer;
    public static Player secondPlayer;

    public static HashMap<String, Integer> names;

    public Main(){
        
    }

    public static void main(String[] args) throws Exception {

        users = new ArrayList<User>();
        status = "registerMenu";

        firstPlayer = new Player();
        firstPlayer.setTurn(true);

        secondPlayer = new Player();

        names = new HashMap<String, Integer>();

        names.put("mine", 1);
        names.put("antiaircraft", 30);
        names.put("airplane", 10);
        names.put("scanner", 9);
        names.put("invisible", 20);

        //Test cases
        /*
        User u2 = new User();
        u2.setWinCount(5);
        u2.setMainScore(58);
        u2.setUsername("samie");
        u2.setPassword("1234");
        u2.setDrawCount(12);
        users.add(u2);

        User u3 = new User();
        u3.setWinCount(80);
        u3.setMainScore(23);
        u3.setUsername("shadi");
        u3.setPassword("1234");
        u3.setDrawCount(0);
        users.add(u3);

        User u4 = new User();
        u4.setWinCount(80);
        u4.setMainScore(23);
        u4.setUsername("zahra");
        u4.setPassword("1234");
        u4.setDrawCount(1);
        users.add(u4);

        User u5 = new User();
        u5.setWinCount(17);
        u5.setMainScore(30);
        u5.setUsername("ali");
        u5.setPassword("1234");
        u5.setDrawCount(80);
        users.add(u5);

        User u6 = new User();
        u6.setWinCount(17);
        u6.setMainScore(23);
        u6.setUsername("sahar");
        u6.setPassword("1234");
        u6.setDrawCount(100);
        users.add(u6);

        */

        while(true){

            switch(status){
                case "registerMenu":
                    RegisterMenu regMenu = new RegisterMenu();
                    regMenu.main();
                    break;

                case "mainMenu":
                    MainMenu mainMenu = new MainMenu();
                    mainMenu.main();
                    break;
                
                case "boardMenu":
                    BoardMenu boardMenu = new BoardMenu();
                    boardMenu.main();
                break;

                case "gameMenu":
                    GameMenu gameMenu = new GameMenu();
                    gameMenu.main();
                break;

                case "shopMenu":
                    ShopMenu shopMenu = new ShopMenu();
                    shopMenu.main();
                    break;

                case "exit":
                    return;
            }
        }
    }  
}
