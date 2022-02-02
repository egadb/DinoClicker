package com.example.projet;

import android.app.Activity;
import android.media.SoundPool;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class of achievement unlocked by automaker threshold
 */
public class Achievement implements Serializable {

    private int name;
    private boolean obtained;
    private Upgrade upgrade;
    private int amountNeeded;

    /**
     * Achievement constructor
     *
     * @param cost
     * @param bonus
     * @param image
     * @param effect
     * @param name
     * @param AutoMaker
     * @param needed
     */
    public Achievement(double cost, double bonus, int image, int effect, int name, int AutoMaker, int needed) {

        setUpgrade(new Upgrade(cost, bonus, image, effect, AutoMaker));
        this.setName(name);
        setObtained(false);
        setAmountNeeded(needed);
    }

    /**
     * Verify if achievement should be unlocked, and show and play sound if unlocked
     *
     * @param activity
     * @param soundPool
     */
    public void verifyIfObtained(final Activity activity, SoundPool soundPool) {
        if (GameManager.getInstance().autoMakers.get(getUpgrade().getAutoMaker()).getNumberInPossession() >= getAmountNeeded() && isObtained() == false) {
            GameManager.getInstance().upgrades.add(getUpgrade());
            setObtained(true);

            LayoutInflater inflater = LayoutInflater.from(activity.getApplicationContext());
            View layout = inflater.inflate(R.layout.achievement_toast, (ViewGroup) activity.findViewById(R.id.custom_toast_achievement));

            TextView text = layout.findViewById(R.id.textToastAchievement);
            String toastText = (activity.getApplicationContext().getResources().getString(R.string.custom_toast_message) + " " + activity.getString(getName()));
            text.setText(toastText);

            //PopupWindow achievements
            final PopupWindow popupWindow = new PopupWindow(layout, 1000, 210, false);
            popupWindow.setOutsideTouchable(false);
            popupWindow.setTouchable(false);

            LinearLayout linearLayout = activity.findViewById(R.id.linear2);


            //Animations for achievements
            popupWindow.setAnimationStyle(R.style.popup_animation);

            popupWindow.showAtLocation(linearLayout, Gravity.TOP, 0, 0);


            //Plays yee sound for achievements
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    soundPool.play(sampleId, 0.5f, 0.5f, 1, 0, 1f);
                }
            });
            int yee = soundPool.load(activity.getApplicationContext(), R.raw.yee, 2);

            //Delete achievement popup after 3sec
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
     * isObtained
     *
     * @return obtained
     */
    public boolean isObtained() {
        return obtained;
    }

    /**
     * setObtained
     *
     * @param obtained
     */
    public void setObtained(boolean obtained) {
        this.obtained = obtained;
    }

    /**
     * getUpgrade
     *
     * @return upgrade
     */
    public Upgrade getUpgrade() {
        return upgrade;
    }

    /**
     * setUpgrade
     *
     * @param upgrade
     */
    public void setUpgrade(Upgrade upgrade) {
        this.upgrade = upgrade;
    }

    /**
     * getAmountNeeded
     *
     * @return amountNeeded
     */
    public int getAmountNeeded() {
        return amountNeeded;
    }

    /**
     * setAmountNeeded
     *
     * @param amountNeeded
     */
    public void setAmountNeeded(int amountNeeded) {
        this.amountNeeded = amountNeeded;
    }
}
