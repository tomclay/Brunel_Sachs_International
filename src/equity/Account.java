package equity;

import java.util.Scanner;

/**
 * Created by tomclay on 08/12/2016.
 */

public class Account {


    private static Mainframe mainframe;
    private static String account_ID;
    private static Scanner input;


    public static void main(String[] args) {
        start();
    }

    public static synchronized void start() {
    input = new Scanner(System.in);

    System.out.println("Welcome to Brunel Sachs International. Please enter in your account number:");

    account_ID = input.nextLine();

    mainframe = new Mainframe(account_ID);

    choices();

    }

    public static synchronized void choices(){

        int user_choice = 0;

        while (user_choice != 1 && user_choice != 2 && user_choice != 3  && user_choice != 4 && user_choice != 5)
        {
            System.out.println("What would you like to do?");
            System.out.println("1. See my balance");
            System.out.println("2. Deposit funds");
            System.out.println("3. Withdraw funds");
            System.out.println("4. Transfer funds");
            System.out.println("5. Exit");

            user_choice = input.nextInt();
        }

        switch (user_choice){
            case 1: balance();
                break;
            case 2: deposit();
                break;
            case 3: withdraw();
                break;
            case 4: transfer();
                break;
            case 5: exit();
                break;
            default: exit();
                break;
        }

        user_choice = 0;
    }

    public static synchronized void balance() {
        float result = mainframe.acc_lookup();
        System.out.println("Your account balance: " + result + ".");
        choices();
    }


    public static synchronized void deposit() {
        System.out.println("Please enter the amount to deposit.");
        float amount = Float.parseFloat(input.nextLine());
        float result = Mainframe.add(amount);
        System.out.println("Operation completed successfully");
        System.out.println("Your new balance is: " + result + ".");
        choices();
    }


    public static synchronized void withdraw() {
        System.out.println("Please enter the amount to withdraw.");
        float amount = Float.parseFloat(input.nextLine());
        float result = Mainframe.subtract(amount);
        System.out.println("Operation completed successfully");
        System.out.println("Your new balance is: " + result + ".");
        choices();
    }

    // Transfer
    public static synchronized void transfer() {
        System.out.println("Please enter the ID of the account you wish to transfer money to.");
        String ID = input.nextLine();
        System.out.println("Please enter the amount you wish to transfer.");
        float amount = Float.parseFloat(input.nextLine());
        Mainframe mainframe2 = new Mainframe(ID);
        mainframe.subtract(amount);
        mainframe2.add(amount);
        choices();
    }

    public static synchronized void exit(){
        System.out.println("Thank you for using Brunel Sachs International. Have a jolly nice say.");
        System.exit(0);
    }
}
