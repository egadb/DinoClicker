package com.example.projet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Main Activity of the project
 */
public class MainActivity extends AppCompatActivity {

    TextView counter;
    TextView counterPerSecond;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize counter
        counter = findViewById(R.id.counter);
        counter.setText("0 dinosaurs");

        //Initialize counter per sec
        counterPerSecond = findViewById(R.id.counterPerSecond);

        //Initialize buttons
        Button egg = findViewById(R.id.eggButton);
        egg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameManager.getInstance().EggClick(MainActivity.this);
                counter.setText(GameManager.getInstance().format(GameManager.getInstance().dinos) + " dinosaurs");
            }
        });
        final Button buttonShop = findViewById(R.id.shopButton);
        buttonShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameManager.getInstance().activityShop.getClass());
                startActivity(intent);

            }
        });
        final Button upgrade = findViewById(R.id.upgrade);
        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpgradesActivity.class);
                startActivity(intent);
            }
        });

        //Loads from previous save
        load();

        GameManager.getInstance().revenu(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.graph) {
            Intent intent = new Intent(MainActivity.this, ProgressGraph.class);

            startActivity(intent);
        }
        if (item.getItemId() == R.id.about) {
            Intent intent = new Intent(MainActivity.this, About.class);

            startActivity(intent);
        }

        return true;
    }

    /**
     * Save files with data from game
     */
    public void save() {
        ObjectOutputStream objectOut = null;
        try {

            FileOutputStream fileOut = this.openFileOutput("AutoMakerSave", Activity.MODE_PRIVATE);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(GameManager.getInstance().autoMakers);
            fileOut.getFD().sync();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOut != null) {
                try {
                    objectOut.close();
                } catch (IOException e) {
                    // do nowt
                }
            }
        }

        objectOut = null;
        try {

            FileOutputStream fileOut = this.openFileOutput("AchievementSave", Activity.MODE_PRIVATE);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(GameManager.getInstance().achievements);
            fileOut.getFD().sync();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOut != null) {
                try {
                    objectOut.close();
                } catch (IOException e) {
                    // do nowt
                }
            }
        }

        objectOut = null;
        try {

            FileOutputStream fileOut = this.openFileOutput("TotalDinos", Activity.MODE_PRIVATE);
            DataOutputStream dos = new DataOutputStream(fileOut);
            dos.writeDouble(GameManager.getInstance().dinos);
            dos.close();
            fileOut.getFD().sync();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOut != null) {
                try {
                    objectOut.close();
                } catch (IOException e) {
                    // do nowt
                }
            }
        }

        objectOut = null;
        try {

            FileOutputStream fileOut = this.openFileOutput("BonusClick", Activity.MODE_PRIVATE);
            DataOutputStream dos = new DataOutputStream(fileOut);
            dos.writeDouble(GameManager.getInstance().bonusClick);
            dos.close();
            fileOut.getFD().sync();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOut != null) {
                try {
                    objectOut.close();
                } catch (IOException e) {
                    // do nowt
                }
            }
        }
    }

    /**
     * Loads save from previous game
     */
    private void load() {
        //Loads automakers
        ObjectInputStream objectIn = null;
        FileInputStream fileIn = null;
        Object object = null;
        File fileForSize = new File("AutoMakerSave");
        try {


            fileIn = this.getApplicationContext().openFileInput("AutoMakerSave");
            objectIn = new ObjectInputStream(fileIn);
            object = objectIn.readObject();

        } catch (FileNotFoundException e) {
            // Do nothing
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (objectIn != null || fileForSize.length() > 0) {
                try {

                    GameManager.getInstance().autoMakers = (ArrayList<AutoMaker>) object;


                    objectIn.close();
                } catch (IOException e) {
                    // do nowt
                }
            }
        }
        //Loads achievements
        objectIn = null;
        fileIn = null;
        object = null;
        fileForSize = new File("AchievementSave");
        try {
            fileIn = this.getApplicationContext().openFileInput("AchievementSave");
            objectIn = new ObjectInputStream(fileIn);
            object = objectIn.readObject();

        } catch (FileNotFoundException e) {
            // Do nothing
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (objectIn != null || fileForSize.length() > 0) {
                try {
                    GameManager.getInstance().achievements = (ArrayList<Achievement>) object;
                    objectIn.close();
                } catch (IOException e) {
                    // do nowt
                }
            }
        }

        //Loads dinos
        DataInputStream dataIn = null;
        fileIn = null;
        double dinos = 0;
        fileForSize = new File("TotalDinos");
        try {

            fileIn = this.getApplicationContext().openFileInput("TotalDinos");
            dataIn = new DataInputStream(fileIn);
            dinos = dataIn.readDouble();

        } catch (FileNotFoundException e) {
            // Do nothing
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectIn != null || fileForSize.length() > 0) {
                try {

                    GameManager.getInstance().dinos = dinos;

                    objectIn.close();
                } catch (IOException e) {
                    // do nowt
                }
            }
        }

        //Loads bonus click
        dataIn = null;
        fileIn = null;
        double bonusClick = 0;
        fileForSize = new File("BonusClick");

        try {

            fileIn = this.getApplicationContext().openFileInput("BonusClick");
            dataIn = new DataInputStream(fileIn);
            bonusClick = dataIn.readDouble();

        } catch (FileNotFoundException e) {
            // Do nothing
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectIn != null || fileForSize.length() > 0) {
                try {
                    GameManager.getInstance().bonusClick = bonusClick;

                    objectIn.close();
                } catch (IOException e) {
                    // do nowt
                }
            }
        }
    }


    /**
     * Updates the mainActivity according to data from GameManager
     */
    public void update() {
        counter.setText(GameManager.getInstance().format(GameManager.getInstance().dinos) + " dinosaurs");
        counterPerSecond.setText(GameManager.getInstance().format(GameManager.getInstance().getDinoPerSec()) + " dinos/sec");


        //Animations that fucked everything
    /*if(GameManager.getInstance().getDinoPerSec() > 0)
    {
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup container = (ViewGroup) inflater.inflate(R.layout.click_toast,null);
        TextView text = container.findViewById(R.id.textView2);
        text.setText("DINOS");
        FrameLayout linearLayout = findViewById(R.id.linear);


        final PopupWindow popupWindow = new PopupWindow(container,300,100,false);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setTouchable(false);
        popupWindow.setAttachedInDecor(false);



        popupWindow.setAnimationStyle(R.style.dinoGang);

        popupWindow.setElevation(1);

        popupWindow.showAtLocation(linearLayout,Gravity.TOP, (int) ((Math.random()*800)-400), 100);




        final Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        popupWindow.dismiss();
                    }
                });
            }
        },3000);
    }*/
    }

}
