package ru.job4j.inheritance;

public class PizzaExtraCheese extends Pizza {
    private static final String NEW_NAME = " + extra cheese";

    @Override
    public String name() {
        return super.name() + NEW_NAME;
    }
}
