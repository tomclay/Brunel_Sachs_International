package equity;

import java.util.Scanner;

/**
 * Created by tomclay on 08/12/2016.
 */

public class Accounts {


    private static Database database;
    private static String account_ID;
    private static Scanner input;

    public static void main(String[] args) {
        start();
    }

    public static synchronized void start() {
    input = new Scanner(System.in);

    System.out.println("Welcome to Brunel Sachs International. Please enter in your account number.");

    account_ID = input.nextLine();

    database = new Database(account_ID);

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
        float result = database.get_bal();
        System.out.println("Your account balance: £" + result);
        choices();
    }


    public static synchronized void deposit() {
        input = new Scanner(System.in);
        System.out.println("Please enter the amount to deposit.");
        String amount = input.nextLine();
        float result = Database.add(Float.parseFloat(amount));
        System.out.println("Operation completed successfully");
        System.out.println("Your new balance is: £" + database.accBalance);
        choices();
    }


    public static synchronized void withdraw() {
        input = new Scanner(System.in);
        System.out.println("Please enter the amount to withdraw.");
        float amount = Float.parseFloat(input.nextLine());
        float result = Database.subtract(amount);
        System.out.println("Your new balance is: £" + database.accBalance);
        choices();
    }

    // Transfer
    public static synchronized void transfer() {
        input = new Scanner(System.in);
        System.out.println("Please enter the ID of the account you wish to transfer money to.");
        String ID = input.nextLine();
        System.out.println("Please enter the amount you wish to transfer.");
        float amount = Float.parseFloat(input.nextLine());
        float dest_account_new_bal = database.wire(ID,amount);
        System.out.println("The account " + ID + " now has the balance of £" + dest_account_new_bal);
        choices();
    }

    public static synchronized void exit(){
        System.out.println("Thank you for using Brunel Sachs International. Have a jolly nice day.");
        System.exit(0);
    }
}
