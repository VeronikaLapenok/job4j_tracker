package ru.job4j.inheritance;

public class PizzaExtraCheeseExtraTomato extends PizzaExtraCheese {
    private static final String NEW_NAME = " + extra tomato";

    @Override
    public String name() {
        return super.name() + NEW_NAME;
    }
}
