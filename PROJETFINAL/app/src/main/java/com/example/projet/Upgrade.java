package com.example.projet;

import java.io.Serializable;

/**
 * Upgrade unlock by achievements. Contains all attributes concerning in game bonus.
 */
public class Upgrade implements Serializable {

    private double cost;
    private double bonus;
    private int image;
    private int text;
    private int autoMaker;


    /**
     * Upgrade constructor
     *
     * @param cost      cost of upgrade
     * @param bonus     bonus of upgrade
     * @param image     image of upgrade
     * @param text      text of upgrade
     * @param AutoMaker AutoMaker of upgrade
     */
    public Upgrade(double cost, double bonus, int image, int text, int AutoMaker) {
        this.setCost(cost);
        this.setBonus(bonus);
        this.setImage(image);
        this.setText(text);
        this.setAutoMaker(AutoMaker);
    }

    /**
     * Set bonus for player when bought
     *
     * @param upgrade the upgrade that is bought
     */
    public void buy(Upgrade upgrade) {

        if (GameManager.getInstance().dinos >= cost) {
            //GameManager.getInstance().autoMakers.get(upgrade.autoMaker).setBonusProd(GameManager.getInstance().autoMakers.get(upgrade.autoMaker).getBonusProd()+upgrade.bonus);
            if (upgrade.getAutoMaker() != 0) {
                GameManager.getInstance().autoMakers.get(upgrade.getAutoMaker()).setBonusProd(GameManager.getInstance().autoMakers.get(upgrade.getAutoMaker()).getBonusProd() * bonus);
            } else {
                //GameManager.getInstance().autoMakers.get(0).setBonusClick(GameManager.getInstance().autoMakers.get(0).getBonusClick()+(bonus/100)*GameManager.getInstance().dinoPer100ms);
                GameManager.getInstance().bonusClick = GameManager.getInstance().bonusClick + (bonus / 100);
            }
            GameManager.getInstance().dinos -= cost;
        }
    }

    /**
     * getCost
     *
     * @return cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * setCost
     *
     * @param cost
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * getBonus
     *
     * @return bonus
     */
    public double getBonus() {
        return bonus;
    }

    /**
     * setBonus
     *
     * @param bonus
     */
    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    /**
     * getImage
     *
     * @return image
     */
    public int getImage() {
        return image;
    }

    /**
     * setImage
     *
     * @param image
     */
    public void setImage(int image) {
        this.image = image;
    }

    /**
     * getText
     *
     * @return text
     */
    public int getText() {
        return text;
    }

    /**
     * setText
     *
     * @param text
     */
    public void setText(int text) {
        this.text = text;
    }

    /**
     * getAutoMaker
     *
     * @return autoMaker
     */
    public int getAutoMaker() {
        return autoMaker;
    }

    /**
     * setAutoMaker
     *
     * @param autoMaker
     */
    public void setAutoMaker(int autoMaker) {
        this.autoMaker = autoMaker;
    }
}
