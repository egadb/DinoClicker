package com.example.projet;

import android.content.Context;
import android.media.SoundPool;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jjoe64.graphview.series.DataPoint;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A singleton that manage the game
 */
class GameManager implements Serializable {
    private static final GameManager ourInstance = new GameManager();
    public double time;
    public double maxDino;
    public double dinos;
    public double dinoPer100ms;
    public double bonusClick;
    public int bulkSize = 1;
    public ShopActivity activityShop = new ShopActivity();
    public SoundPool soundPool = new SoundPool.Builder().setMaxStreams(2).build();
    public ArrayList<AutoMaker> autoMakers = new ArrayList<>();
    public ArrayList<Upgrade> upgrades = new ArrayList<>();
    public ArrayList<Achievement> achievements = new ArrayList<>();
    public ArrayList<DataPoint> points = new ArrayList<>();
    private Timer timer;
    private double dinoPerSec;
    private int counter = 0;
    private int saveCounter = 0;


    /**
     * Creates new GameManager
     */
    private GameManager() {
        bonusClick = 0;
        dinos = 552255 + 5;
        setDinoPer100ms(0);

        //Tableaux des futurs automakers (initialisés plus loin)
        final int[] nameMaker = {R.string.mouseClick, R.string.maker1, R.string.maker2, R.string.maker3, R.string.maker4, R.string.maker5};
        final int[] image = {R.drawable.hand, R.drawable.upgrade1, R.drawable.upgrade2, R.drawable.upgrade3, R.drawable.upgrade4, R.drawable.upgrade5};
        final double[] price = {20, 50, 10000, 200000, 1000000, 44444444};
        final double[] prodBase = {20, 1, 60, 720, 8640, 103680};
        final int[] numberPossessed = {1, 1, 0, 0, 0, 0};
        final double[] coeffGrowth = {1.5, 1.5, 1.5, 1.5, 1.5, 1.5};


        //Tableaux des futurs achievement (initialisés plus loin)
        final int[] nameAchievement = {R.string.Achivement1, R.string.Achivement2, R.string.Achivement3, R.string.Achivement4, R.string.Achivement5, R.string.Achivement6, R.string.Achivement2};
        final double[] cost = {25, 500, 3000, 20000, 100000, 4444444, 5000};
        //bonus for type of maker 0 is in % of dps
        final double[] bonus = {5, 5, 10, 10, 10, 10, 3};
        final int[] imageUpgrade = {R.drawable.hand, R.drawable.upgrade1, R.drawable.upgrade2, R.drawable.upgrade3, R.drawable.upgrade4, R.drawable.upgrade5, R.drawable.upgrade2};
        final int[] effects = {R.string.mouseClick, R.string.maker1, R.string.maker2, R.string.maker3, R.string.maker4, R.string.maker5, R.string.maker2};
        final int[] makerNeeded = {2, 2, 10, 15, 20, 25, 3};
        final int[] typeOfMaker = {0, 1, 1, 1, 1, 1, 2};

        //Création des arrays d'automakers et achievements
        for (int i = 0; i < cost.length; i++) {
            achievements.add(new Achievement(cost[i], bonus[i], imageUpgrade[i], effects[i], nameAchievement[i], typeOfMaker[i], makerNeeded[i]));
        }

        for (int i = 0; i < nameMaker.length; i++) {
            autoMakers.add(new AutoMaker((nameMaker[i]), image[i], price[i], numberPossessed[i], prodBase[i], coeffGrowth[i]));
        }


    }

    /**
     * GetInstance
     *
     * @return ourInstance
     */
    static GameManager getInstance() {
        return ourInstance;
    }

    /**
     * Function called when the egg is clicked
     *
     * @param activity egg's activity
     */
    public void EggClick(MainActivity activity) {
        dinos = dinos + autoMakers.get(0).getProductionTotal();
        showNumber(activity);
    }

    /**
     * Calculate dinos per 100ms with automakers
     */
    public void CalculateDinoPer100ms() {
        setDinoPerSec(0);
        setDinoPer100ms(0);
        for (int i = 1; i < autoMakers.size(); i++) {
            setDinoPer100ms(getDinoPer100ms() + autoMakers.get(i).getProductionTotal() * 0.1);
        }
        setDinoPerSec(getDinoPer100ms() * 10);
    }


    /**
     * Calculate production of dinos with automakers
     */
    private void CalculateProdWithBonus() {
        for (int i = 0; i < autoMakers.size(); i++) {
            if (i != 0) {
                GameManager.getInstance().autoMakers.get(i).setProductionTotal(GameManager.getInstance().autoMakers.get(i).getProductionBase() * GameManager.getInstance().autoMakers.get(i).getNumberInPossession() * GameManager.getInstance().autoMakers.get(i).getBonusProd());
            } else {
                GameManager.getInstance().autoMakers.get(i).setProductionTotal(GameManager.getInstance().autoMakers.get(i).getNumberInPossession() + GameManager.getInstance().autoMakers.get(i).getNumberInPossession() * GameManager.getInstance().autoMakers.get(i).getBonusClick());
            }
        }

    }

    /**
     * Calculate the bulk cost for items in the ShopActivity
     *
     * @param numberWanted
     */
    public void CalculateBulkCost(int numberWanted) {

        for (int i = 0; i < autoMakers.size(); i++) {

            GameManager.getInstance().autoMakers.get(i).setPriceForNext(GameManager.getInstance().autoMakers.get(i).getPrice() * ((Math.pow(GameManager.getInstance().autoMakers.get(i).getCoeffGrowth(), GameManager.getInstance().autoMakers.get(i).getNumberInPossession()) * (Math.pow(GameManager.getInstance().autoMakers.get(i).getCoeffGrowth(), numberWanted) - 1)) / (GameManager.getInstance().autoMakers.get(i).getCoeffGrowth() - 1)));
        }

    }

    /**
     * Calculate the bonus for each click on the egg with automakers
     */
    private void CalculateBonusClick() {
        autoMakers.get(0).setBonusClick(bonusClick * dinoPer100ms);

    }

    /**
     * Try to see if a new achievement is unlocked and calls the appropriate function
     *
     * @param activity
     */
    private void TestAchievement(ShopActivity activity) {

        if (activity != null) {
            for (int i = 0; i < achievements.size(); i++) {
                achievements.get(i).verifyIfObtained(activity, soundPool);

            }
        }
    }


    /**
     * The main function of the program that is called frequently the calculate everything
     *
     * @param activity
     */
    //Function that is called every 100ms
    public void revenu(final MainActivity activity) {

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (maxDino <= dinos) {
                            maxDino = dinos;
                        }
                        time++;
                        counter++;
                        saveCounter++;
                        if (counter == 10) {

                            points.add(new DataPoint(time / 10, dinos));

                            counter = 0;
                        }


                        //Saves every 10 seconds
                        if (saveCounter == 100) {
                            try {

                                activity.save();
                            } catch (Exception e) {

                            }
                            saveCounter = 0;
                        }

                        CalculateBonusClick();

                        //Check achievements
                        if (activityShop != null) {
                            TestAchievement(activityShop);
                        }

                        CalculateProdWithBonus();
                        CalculateDinoPer100ms();

                        CalculateBulkCost(bulkSize);

                        //Adds dinos
                        dinos = dinos + getDinoPer100ms();

                        //Shows changes on mainActivity
                        activity.update();


                    }
                });
            }
        }, 10, 100);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId, 0.5f, 0.5f, 1, -1, 0.8f);
            }
        });
        int dinosong = soundPool.load(activity.getApplicationContext(), R.raw.dinosong, 1);
    }


    /**
     * Format a double to a String with a certain pattern
     *
     * @param number
     * @return String
     */
    public String format(double number) {
        DecimalFormat dformater;
        if (number < 1000000000) {
            dformater = new DecimalFormat("0.00");
            return dformater.format(number);
        } else {
            dformater = new DecimalFormat("0.######E0");
            return dformater.format(number);
        }
    }

    /**
     * Format number that appears when egg is clicked
     *
     * @param number
     * @return
     */
    public String formatClick(double number) {
        DecimalFormat dformater;
        if (number < 1000000000) {
            dformater = new DecimalFormat("0.00");
            return dformater.format(number);
        } else {
            dformater = new DecimalFormat("0.######E0");
            return dformater.format(number);
        }
    }

    /**
     * getDinoPer100ms
     *
     * @return dinoPer100ms
     */
    public double getDinoPer100ms() {
        return dinoPer100ms;
    }

    /**
     * setDinoPer100ms
     *
     * @param dinoPer100ms
     */
    public void setDinoPer100ms(double dinoPer100ms) {
        this.dinoPer100ms = dinoPer100ms;
    }

    /**
     * Shows number on egg for each click
     *
     * @param activity egg's activity
     */
    public void showNumber(final MainActivity activity) {
        LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup container = (ViewGroup) inflater.inflate(R.layout.click_toast, null);
        TextView text = container.findViewById(R.id.textView2);
        text.setText(formatClick(GameManager.getInstance().autoMakers.get(0).getProductionTotal()));
        FrameLayout linearLayout = activity.findViewById(R.id.linear);


        final PopupWindow popupWindow = new PopupWindow(container, 300, 100, false);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setTouchable(false);

        //Show popup window at random spot in the center of the screen
        popupWindow.showAtLocation(linearLayout, Gravity.CENTER, (int) ((Math.random() * 400) - 150), (int) ((Math.random() * 300)));

        //Delete popup window after 3sec
        final Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow.dismiss();
                    }
                });
            }
        }, 3000);


    }

    /**
     * setDinos
     *
     * @param dinos1
     */
    public void setDinos(double dinos1) {
        this.dinos = dinos1;
    }

    /**
     * getDinoPerSec
     *
     * @return dinoPerSec
     */
    public double getDinoPerSec() {
        return dinoPerSec;
    }

    /**
     * setDinoPerSec
     *
     * @param dinoPerSec
     */
    public void setDinoPerSec(double dinoPerSec) {
        this.dinoPerSec = dinoPerSec;
    }

}
