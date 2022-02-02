package com.example.projet;

import java.io.Serializable;

/**
 * AutoMaker - > an object that automatically creates dinos.
 */
public class AutoMaker implements Serializable {

    private int name;
    private int image;
    private double price;
    private double priceForNext;
    private int numberInPossession;
    private double productionBase;
    private double productionTotal;
    private double coeffGrowth;
    private double bonusProd;
    private double bonusClick;

    /**
     * AutoMaker Constructor
     *
     * @param name1           name
     * @param image1          image
     * @param price1          price
     * @param numberPossessed number in possession
     * @param prodBase        base production
     * @param coeffgrow       coefficient of growth
     */
    public AutoMaker(int name1, int image1, double price1, int numberPossessed, double prodBase, double coeffgrow) {

        this.setName(name1);
        this.setImage(image1);
        this.setPriceForNext(price1);
        this.setPrice(price1);
        this.setNumberInPossession(numberPossessed);
        this.setProductionBase(prodBase);
        this.setProductionTotal(0);
        if (name1 == R.string.mouseClick) {
            this.setProductionTotal(1);
        }
        this.setCoeffGrowth(coeffgrow);
        this.setBonusProd(1);
        this.setBonusClick(1);

    }

    /**
     * Fonction pour acheter AutoMaker
     *
     * @param autoMaker -> L'automaker en question
     * @param position  -> Sa posistion dans la list du GameManager
     */
    public void buy(AutoMaker autoMaker, int position) {
        if (autoMaker.getPriceForNext() <= GameManager.getInstance().dinos) {
            GameManager.getInstance().autoMakers.get(position).setNumberInPossession(GameManager.getInstance().autoMakers.get(position).getNumberInPossession() + GameManager.getInstance().bulkSize);
            GameManager.getInstance().dinos -= autoMaker.getPriceForNext();
            // GameManager.getInstance().autoMakers.get(position).setPriceForNext((GameManager.getInstance().autoMakers.get(position).getPrice()*(Math.pow(GameManager.getInstance().autoMakers.get(position).getCoeffGrowth(), 0.8*GameManager.getInstance().autoMakers.get(position).getNumberInPossession()))));
            if (position != 0) {
                GameManager.getInstance().autoMakers.get(position).setProductionTotal(GameManager.getInstance().autoMakers.get(position).getProductionBase() * GameManager.getInstance().autoMakers.get(position).getNumberInPossession() * GameManager.getInstance().autoMakers.get(position).getBonusProd());
            } else {
                GameManager.getInstance().autoMakers.get(position).setProductionTotal(GameManager.getInstance().autoMakers.get(position).getNumberInPossession() + (GameManager.getInstance().autoMakers.get(position).getNumberInPossession() * GameManager.getInstance().autoMakers.get(position).getBonusClick()));
            }
        }
    }


    /**
     * getName
     *
     * @return name
     */
    public int getName() {
        return name;
    }

    /**
     * setName
     *
     * @param name
     */
    public void setName(int name) {
        this.name = name;
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
     * getPrice
     *
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * setPrice
     *
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * getNumberInPossession
     *
     * @return numberInPossession
     */
    public int getNumberInPossession() {
        return numberInPossession;
    }

    /**
     * setNumberInPossession
     *
     * @param numberInPossession
     */
    public void setNumberInPossession(int numberInPossession) {
        this.numberInPossession = numberInPossession;
    }

    /**
     * getPriceForNext
     *
     * @return PriceForNext
     */
    public double getPriceForNext() {
        return priceForNext;
    }

    /**
     * setPriceForNext
     *
     * @param priceForNext
     */
    public void setPriceForNext(double priceForNext) {
        this.priceForNext = priceForNext;
    }

    /**
     * getProductionBase
     *
     * @return productionBase
     */
    public double getProductionBase() {
        return productionBase;
    }

    /**
     * setProductionBase
     *
     * @param productionBase
     */
    public void setProductionBase(double productionBase) {
        this.productionBase = productionBase;
    }

    /**
     * getProductionTotal
     *
     * @return productionTotal
     */
    public double getProductionTotal() {
        return productionTotal;
    }

    /**
     * setProductionTotal
     *
     * @param productionTotal
     */
    public void setProductionTotal(double productionTotal) {
        this.productionTotal = productionTotal;
    }

    /**
     * getCoeffGrowth
     *
     * @return coeffGrowth
     */
    public double getCoeffGrowth() {
        return coeffGrowth;
    }

    /**
     * setCoeffGrowth
     *
     * @param coeffGrowth
     */
    public void setCoeffGrowth(double coeffGrowth) {
        this.coeffGrowth = coeffGrowth;
    }

    /**
     * getBonusProd
     *
     * @return bonusProd
     */
    public double getBonusProd() {
        return bonusProd;
    }

    /**
     * setBonusProd
     *
     * @param bonusProd
     */
    public void setBonusProd(double bonusProd) {
        this.bonusProd = bonusProd;
    }

    /**
     * getBonusClick
     *
     * @return bonusClick
     */
    public double getBonusClick() {
        return bonusClick;
    }

    /**
     * setBonusClick
     *
     * @param bonusClick
     */
    public void setBonusClick(double bonusClick) {
        this.bonusClick = bonusClick;
    }
}