import java.util.ArrayList;
import java.util.Collections;

public class MainMenu extends Menu {
    
    public void main() {
        String[] command = readCommand("mainMenu");
        
        while(true){
            if(command == null){
                command = readCommand("mainMenu");
            }

            switch(command[0].trim().toLowerCase()){
                case "logout":
                    System.out.println("logout successful");
                    
                    if(Main.firstPlayer.isLogin()){
                        Main.firstPlayer.setLogin(false);
                        //App.firstPlayer.setUsername("");
                    }
                    else{
                        Main.secondPlayer.setLogin(false);
                        Main.secondPlayer.setUsername("");
                    }

                    Main.status = "registerMenu";
                    return ;

                case "help":
                    System.out.println("new_game [username]");
                    System.out.println("scoreboard");
                    System.out.println("list_users");
                    System.out.println("shop");
                    System.out.println("help");
                    System.out.println("logout");
                    break;

                case "shop":
                    Main.status = "shopMenu";
                    return;
                
                case "list_users":
                    printUserList();
                    break;

                case "new_game":
                    if(command[1].trim().toLowerCase().equals(Main.firstPlayer.getUsername())){
                        System.out.println("you must choose another player to start a game.");
                        break;
                    }
                    if(newGameFunc(command[1].trim().toLowerCase())){
                        Main.status = "boardMenu";
                        return;
                    }
                    break;

                case "scoreboard":
                    showScoreboard(Main.users);
                break;


                default:
                System.out.println("invalid command");
                break;
            }
            command = readCommand("mainMenu");
        }
    }

    private void showScoreboard(ArrayList<User> list) {
        
        //such a lunatic bubble sort :|
        
        User temp;
        boolean sorted = false;
    
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < list.size()-1; i++) {
                if (list.get(i).getMainScore() < list.get(i+1).getMainScore()) {
                    temp = list.get(i);
                    list.set(i, list.get(i + 1));
                    list.set(i + 1, temp);
                    sorted = false;
                }else if(list.get(i).getMainScore() == list.get(i+1).getMainScore()){
                    if(list.get(i).getWinCount() < list.get(i+1).getWinCount()){
                        temp = list.get(i);
                        list.set(i, list.get(i + 1));
                        list.set(i + 1, temp);
                        sorted = false;
                    }else if(list.get(i).getWinCount() == list.get(i+1).getWinCount()){
                        if(list.get(i).getDrawCount() < list.get(i+1).getDrawCount()){
                            temp = list.get(i);
                            list.set(i, list.get(i + 1));
                            list.set(i + 1, temp);
                            sorted = false;
                        }else if(list.get(i).getDrawCount() == list.get(i+1).getDrawCount()){
                            if(list.get(i).getLoosCount() > list.get(i+1).getLoosCount()){
                                temp = list.get(i);
                                list.set(i, list.get(i + 1));
                                list.set(i + 1, temp);
                                sorted = false;
                            }else if(list.get(i).getLoosCount() == list.get(i+1).getLoosCount()){
                                if(list.get(i).getUsername().compareTo(list.get(i+1).getUsername()) < 0){
                                    temp = list.get(i);
                                    list.set(i, list.get(i + 1));
                                    list.set(i + 1, temp);
                                    sorted = false;
                                }
                            }
                        }
                    }
                }
            }
        }

        for(int j=0; j < list.size(); j++)
            System.out.println(list.get(j).getUsername() + " " + list.get(j).getMainScore() + " " + list.get(j).getWinCount() + " " + list.get(j).getDrawCount() + " " + list.get(j).getLoosCount());
    }

    private boolean newGameFunc(String usrname) {
        if(checkStrings(usrname)){
            for(int i=0; i < Main.users.size(); i++){
                if(Main.users.get(i).getUsername().equals(usrname)){
                    if(Main.secondPlayer.getUsername().length() < 1)
                        Main.secondPlayer.setUsername(usrname);
                    else if(!Main.firstPlayer.getUsername().equals(usrname)){
                        System.out.println("you must select first player ");
                        return false;
                    }
                    System.out.println("new game started successfuly between " + Main.firstPlayer.getUsername() + " and " + usrname);           
                    return true;
                }
                else if(Main.users.size() -1 == i){
                    System.out.println("no user exists with this username"); 
                    return false;
                }
            }
        }
        return false;
    }

    private void printUserList() {
        ArrayList<String> names = new ArrayList<String>();
        for(int i=0; i < Main.users.size(); i++)
            names.add(Main.users.get(i).getUsername());

        Collections.sort(names);

        for(int i=0; i < names.size(); i++)
            System.out.println(names.get(i));
    }
}
