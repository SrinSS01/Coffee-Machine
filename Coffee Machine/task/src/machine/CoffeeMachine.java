package machine;

import java.util.Scanner;

public class CoffeeMachine {
    public static final Scanner sc = new Scanner(System.in);
    int water = 400, milk = 540, beans = 120, cups = 9, money = 550;
    static State state = State.CHOOSE_ACTION;
    private boolean execute(String userInput) {
        switch (state) {
            case CHOOSE_ACTION: {
                System.out.println("Write action (buy, fill, take, remaining, exit):");
                switch (userInput) {
                    case "buy": {
                        state = State.CHOOSE_VARIANT;
                        break;
                    }
                    case "fill": {
                        state = State.FILL_WATER;
                        break;
                    }
                    case "take": {
                        System.out.println(take());
                        break;
                    }
                    case "remaining": {
                        System.out.println(this);
                        break;
                    }
                    case "exit": return true;
                }
                break;
            }
            case CHOOSE_VARIANT: {
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
                switch (userInput) {
                    case "1": {
                        make(CoffeeType.ESPRESSO);
                        break;
                    }
                    case "2": {
                        make(CoffeeType.LATTE);
                        break;
                    }
                    case "3": {
                        make(CoffeeType.CAPPUCCINO);
                        break;
                    }
                    case "back":
                }
                state = State.CHOOSE_ACTION;
                break;
            }
            case FILL_WATER: {
                System.out.println("Write how many ml of water you want to add:");
                water += Integer.parseInt(userInput);
                state = State.FILL_MILK;
                break;
            }
            case FILL_MILK: {
                System.out.println("Write how many ml of milk you want to add:");
                milk += Integer.parseInt(userInput);
                state = State.FILL_BEANS;
                break;
            }
            case FILL_BEANS: {
                System.out.println("Write how many grams of coffee beans you want to add:");
                beans += Integer.parseInt(userInput);
                state = State.ADD_CUPS;
                break;
            }
            case ADD_CUPS: {
                System.out.println("Write how many disposable cups of coffee you want to add:");
                cups += Integer.parseInt(userInput);
                state = State.CHOOSE_ACTION;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        return String.format(
                "The coffee machine has:\n%d ml of water\n%d ml of milk\n%d g of coffee beans\n%d disposable cups\n$%d of money",
                water, milk, beans, cups, money
        );
    }
    void make(CoffeeType type) {
        if (water < type.water) {
            System.out.println("Sorry, not enough water!");
            return;
        }
        if (milk < type.milk) {
            System.out.println("Sorry, not enough milk!");
            return;
        }
        if (beans < type.beans) {
            System.out.println("Sorry, not enough beans!");
            return;
        }
        System.out.println("I have enough resources, making you a coffee!");
        water -= type.water;
        milk -= type.milk;
        beans -= type.beans;
        money += type.money;
        --cups;
    }
    public String take() {
        var moneyReturn = String.format("I gave you $%d", money);
        money = 0;
        return moneyReturn;
    }
    enum CoffeeType {
        ESPRESSO(250, 0, 16, 4),
        LATTE(350, 75, 20, 7),
        CAPPUCCINO(200, 100, 12, 6);
        final int water, milk, beans, money;

        CoffeeType(int water, int milk, int beans, int money) {
            this.water = water;
            this.milk = milk;
            this.beans = beans;
            this.money = money;
        }
    }
    enum State {
        CHOOSE_ACTION, CHOOSE_VARIANT, FILL_WATER, FILL_MILK, FILL_BEANS, ADD_CUPS
    }
    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        do {
            if (coffeeMachine.execute(sc.next())) return;
        } while (true);
    }
}
