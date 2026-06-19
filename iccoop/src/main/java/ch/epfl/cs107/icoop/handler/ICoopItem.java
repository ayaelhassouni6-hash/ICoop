package ch.epfl.cs107.icoop.handler;

import ch.epfl.cs107.play.areagame.handler.InventoryItem;

public enum ICoopItem implements InventoryItem {


    SWORD("Sword", "icoop/sword.icon"),
    FIRE_KEY("FireKey", "icoop/key_red"),
    WATER_KEY("WaterKey", "icoop/key_blue"),
    FIRE_STAFF("FireStaff", "icoop/staff_fire.icon"),
    WATER_STAFF("WaterStaff", "icoop/staff_water.icon"),
    EXPLOSIVE("Explosive", "icoop/explosive");

    private final String itemName;
    private final String spriteName;

    ICoopItem(String name, String spriteName) {
        this.itemName = name;
        this.spriteName = spriteName;
    }



    @Override
    public String getName() {
        return this.itemName;
    }


    @Override
    public int getPocketId() {
        return 0;
    }


    public String getSpriteName() {
        return this.spriteName;
    }
}