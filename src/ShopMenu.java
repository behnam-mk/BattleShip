public class ShopMenu extends Menu {
    
    public void main() {
        String[] command = readCommand();
        
        while(true){
            if(command == null){
                command = readCommand();
            }

            switch(command[0].trim().toLowerCase()){
                
                case "show-amount":
                if(Main.firstPlayer.isLogin()){
                    System.out.println(Main.firstPlayer.getCoinCount());
                }else{
                    System.out.println(Main.secondPlayer.getCoinCount());
                }
                break;
                
                case "buy":
                    buyFunc(command[1].trim().toLowerCase(), command[2].trim().toLowerCase());
                    break;
                
                case "back":
                    Main.status = "mainMenu";
                    return ;

                case "help":
                    System.out.println("show-amount");
                    System.out.println("buy [name] [number]");
                    System.out.println("help");
                    System.out.println("back");
                    break;

                default:
                System.out.println("invalid command");
                break;
            }
            command = readCommand();
        }
    }

    private void buyFunc(String name, String number) {
        int num;
        try {
            num = Integer.parseInt(number);
          }
          catch(Exception e) {
            System.out.println("invalid number");
            return;
          }
        
        if(checkStrings(name, num)){
            Product pro = new Product();

            if(Main.firstPlayer.isLogin()){
                if(Main.firstPlayer.products.get(name) != null){
                    pro = Main.firstPlayer.products.get(name);
                    
                    pro.setQty(pro.getQty() + num);
                    
                    pro.setUsed(false);
                    Main.firstPlayer.products.remove(name);
                    Main.firstPlayer.products.put(name, pro);
                    Main.firstPlayer.setCoinCount(Main.firstPlayer.getCoinCount() - (num * Main.names.get(name)));
                }else{
                    pro.setQty(num);
                    pro.setType(name);
                    pro.setUsed(false);

                    Main.firstPlayer.products.put(name, pro);
                    Main.firstPlayer.setCoinCount(Main.firstPlayer.getCoinCount() - (num * Main.names.get(name)));
                }
            }  
            else{
                if(Main.secondPlayer.products.get(name) != null){
                    pro = Main.secondPlayer.products.get(name);
                    
                    pro.setQty(pro.getQty() + num);
                    pro.setType(name);
                    
                    pro.setUsed(false);
                    Main.secondPlayer.products.remove(name);
                    Main.secondPlayer.products.put(name, pro);
                    Main.secondPlayer.setCoinCount(Main.secondPlayer.getCoinCount() - num * Main.names.get(name));
                }else{
                    pro.setQty(num);
                    pro.setType(name);
                    pro.setUsed(false);

                    Main.secondPlayer.products.put(name, pro);
                    Main.secondPlayer.setCoinCount(Main.secondPlayer.getCoinCount() - num * Main.names.get(name));
                }
            }
        }
    }
}
