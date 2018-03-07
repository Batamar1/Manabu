package me.manabu.adapters.models;

public class DecksDeckModel {

    private String title;
    private String description;
    private int cards;

    public DecksDeckModel(String title, String description, int cards) {
        this.title = title;
        this.description = description;
        this.cards = cards;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCards() {
        return cards;
    }

    public void setCards(int cards) {
        this.cards = cards;
    }
}
