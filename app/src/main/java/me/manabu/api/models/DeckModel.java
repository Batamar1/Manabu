package me.manabu.api.models;

import com.google.gson.annotations.SerializedName;

public class DeckModel {

    private String name;
    @SerializedName("public")
    private int isPublic;

    private CardModel id1;
    private CardModel id2;


    public CardModel getId1() {
        return id1;
    }

    public void setId1(CardModel id1) {
        this.id1 = id1;
    }

    public CardModel getId2() {
        return id2;
    }

    public void setId2(CardModel id2) {
        this.id2 = id2;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }
}