package com.crystalplanet.obsidianpoker.app.model;

public class Chips implements Comparable<Chips> {

    private int amount;

    public Chips(int amount) {
        this.amount = amount;
    }

    public Chips negate() {
        return new Chips(-amount);
    }

    public Chips add(Chips other) {
        return new Chips(amount + other.amount);
    }

    public Chips substract(Chips other) {
        return add(other.negate());
    }

    public Chips split(int parts) {
        return new Chips((int) Math.floor((double) amount/parts));
    }

    @Override
    public String toString() {
        return (amount < 0 ? "- " : "") + "$" + Math.abs(amount);
    }

    @Override
    public int compareTo(Chips other) {
        return amount - other.amount;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof Chips)) return false;
        Chips otherChips = (Chips)other;
        return amount == otherChips.amount;
    }
}
