import java.util.ArrayList;
import java.util.Collections;

public class RegisterMenu extends Menu{

    public void main() {

        String[] command = readCommand();
        
        while(true){            

            if(command == null){
                command = readCommand();
            }

            switch(command[0].trim().toLowerCase()){
                case "exit":
                    System.out.println("program ended");
                    Main.status = "exit";
                    return;
                case "register":
                    registerFunc(command[1].trim().toLowerCase(), command[2].trim().toLowerCase());
                //System.out.println("usernam is:  " + App.users.get(0).getUsername() + "\npassword is: " + App.users.get(0).getPassword());

                    break;

                case "help":
                    System.out.println("register [username] [password]");
                    System.out.println("login [username] [password]");
                    System.out.println("remove [username] [password]");
                    System.out.println("list_users");
                    System.out.println("help");
                    System.out.println("exit");
                    break;

                case "list_users":
                    printUserList();
                    break;

                case "login":
                    if(loginFunc(command[1].trim().toLowerCase(), command[2].trim().toLowerCase())){
                        //going to main menu
                        Main.status = "mainMenu";
                        return;
                    }else
                        break;
                
                case "remove":
                    removeFunc(command[1].trim().toLowerCase(), command[2].trim().toLowerCase());
                    break;

                default:
                System.out.println("invalid command");
                break;
            }
            command = readCommand();
        }
    }

    private void removeFunc(String usrname, String password) {
                
        if(checkStrings(usrname, password)){
            for(int i=0; i < Main.users.size(); i++){
                if(Main.users.get(i).getUsername().equals(usrname)){
                    if(Main.users.get(i).getPassword().equals(password)){
                        Main.users.remove(i);   
                        System.out.println("removed " + usrname + " successfuly");       
                    }else 
                    System.out.println("incorrect password");
                    return;
                }
                else if(Main.users.size() -1 == i){
                    System.out.println("no user exists with this username"); 
                    return;
                }
            }
        }
    }

    private void printUserList() {
        ArrayList<String> names = new ArrayList<String>();
        for(int i=0; i < Main.users.size(); i++)
            names.add(Main.users.get(i).getUsername());

        Collections.sort(names);

        for(int i=0; i < names.size(); i++)
            System.out.println(names.get(i));
    }

    private boolean loginFunc(String usrname, String password) {

        int index = 100000;

        if(Main.users.size() == 0){
            System.out.println("no user exists with this username"); 
            return false;
        }

        if(checkStrings(usrname, password)){
            for(int i=0; i < Main.users.size(); i++){
                if(Main.users.get(i).getUsername().equals(usrname)){
                    index = i;          
                    break;
                }
            }
            if(index == 100000){
                System.out.println("no user exists with this username"); 
                return false;
            }
            if(Main.users.get(index).getPassword().equals(password)){
                System.out.println("login successful");
                Main.firstPlayer.setLogin(true);
                Main.secondPlayer.setLogin(false);
                
                if(Main.firstPlayer.getUsername().length() < 1) 
                    Main.firstPlayer.setUsername(usrname);
                else
                    Main.secondPlayer.setUsername(usrname);

                return true;
            }else
                System.out.println("incorrect password"); 
        }
        return false;
    }

    private void registerFunc(String usrname, String password) {

        if(checkStrings(usrname, password)){

            for(int i=0; i < Main.users.size(); i++){
                if(Main.users.get(i).getUsername().equals(usrname)){
                    System.out.println("a user exists with this username"); 
                    return;
                }
            }

            User usr = new User();
            usr.setUsername(usrname);
            usr.setPassword(password);

            Main.users.add(usr);
            System.out.println("register successful");            
        }
    }
    
}
