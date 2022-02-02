package com.example.projet;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Activity of shop to buy automakers
 */
public class ShopActivity extends Activity {

    public final static int REQUESTCODE = 42;

    static private RecyclerView mainRecyclerView;
    private Button button;
    private int choix;
    private boolean once;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        GameManager.getInstance().activityShop = this;

        mainRecyclerView = findViewById(R.id.ok);

        //Recycler view
        final Adapter adapter = new Adapter(this, GameManager.getInstance().autoMakers);
        mainRecyclerView.setAdapter(adapter);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Initialize button
        button = findViewById(R.id.buttonx1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                once = false;
                //Makes the bulk button
                if (choix == 2 && once == false) {
                    button.setText("Buy x100");
                    choix = 0;

                    GameManager.getInstance().bulkSize = 100;
                    GameManager.getInstance().CalculateBulkCost(100);
                    once = true;
                }

                if (choix == 1 && once == false) {
                    button.setText("Buy x10");

                    GameManager.getInstance().bulkSize = 10;
                    GameManager.getInstance().CalculateBulkCost(10);
                    choix = 2;
                    once = true;

                }
                if (choix == 0 && once == false) {

                    button.setText("Buy x1");

                    GameManager.getInstance().bulkSize = 1;
                    GameManager.getInstance().CalculateBulkCost(1);
                    choix = 1;
                    once = true;

                }

                adapter.notifyDataSetChanged();
            }

        });
        //set text in bulk button even if you go out of shop and come back
        if (GameManager.getInstance().bulkSize == 100) {
            button.setText("buy x100");
            choix = 0;
        } else if (GameManager.getInstance().bulkSize == 10) {
            button.setText("buy x10");
            choix = 2;
        } else if (GameManager.getInstance().bulkSize == 1) {
            button.setText("buy x1");
            choix = 1;
        }
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    //Recycler view for makers
    private class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
        private ArrayList<AutoMaker> mDataset;
        private AutoMaker position;

        // Provide a suitable constructor (depends on the kind of dataset)
        public Adapter(Context context, ArrayList<AutoMaker> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            // create a new view
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);


            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element


            if (position != 0) {
                holder.textViewName.setText(getString(mDataset.get(position).getName()) + "\n" + (GameManager.getInstance().format(mDataset.get(position).getProductionTotal())) + " dps");
            } else {
                holder.textViewName.setText(getString(mDataset.get(position).getName()) + "\n" + (GameManager.getInstance().format(mDataset.get(position).getProductionTotal())) + " per click");
            }

            holder.imageView.setImageResource(mDataset.get(position).getImage());
            holder.textViewPrice.setText(GameManager.getInstance().format(mDataset.get(position).getPriceForNext()));
            holder.textViewAmount.setText(Integer.toString(mDataset.get(position).getNumberInPossession()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        mDataset.get(holder.getAdapterPosition()).buy(mDataset.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                        GameManager.getInstance().CalculateBulkCost(GameManager.getInstance().bulkSize);
                        notifyDataSetChanged();

                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), "STOP SPAMMING", Toast.LENGTH_LONG);
                    }

                }
            });
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public ImageView imageView;
            public TextView textViewName;
            public TextView textViewPrice;
            public TextView textViewAmount;


            public MyViewHolder(View itemView) {
                super(itemView);


                textViewName = itemView.findViewById(R.id.name);
                textViewPrice = itemView.findViewById(R.id.cost);
                textViewAmount = itemView.findViewById(R.id.textAmount);
                imageView = itemView.findViewById(R.id.image);

            }
        }
    }
}