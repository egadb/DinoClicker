package com.example.projet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Activity of upgrade/achievement store
 */
public class UpgradesActivity extends AppCompatActivity {
    public final static int REQUESTCODE = 42;
    int index = 0;
    private RecyclerView mainRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrades);

        //Recycler View
        mainRecyclerView = findViewById(R.id.RecyclerUpgrade);

        mainRecyclerView.setAdapter(new Adapter(this, GameManager.getInstance().upgrades));
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Initialize Button
        Button button = findViewById(R.id.leave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    private class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {


        private ArrayList<Upgrade> mDataset;
        private AutoMaker position;

        // Provide a suitable constructor (depends on the kind of dataset)
        public Adapter(Context context, ArrayList<Upgrade> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public UpgradesActivity.Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            // create a new view
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowupgrade, parent, false);


            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element

            holder.buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (GameManager.getInstance().dinos >= GameManager.getInstance().achievements.get(position).getUpgrade().getCost()) {

                        try {
                            mDataset.get(holder.getAdapterPosition()).buy(mDataset.get(holder.getAdapterPosition()));
                            mDataset.remove(holder.getAdapterPosition());
                            notifyDataSetChanged();
                        } catch (Exception e) {
                            Toast.makeText(getBaseContext(), "STOP SPAMMING", Toast.LENGTH_LONG);
                        }
                    }
                }
            });


            holder.buy.setText("BUY\n" + GameManager.getInstance().format(mDataset.get(position).getCost()));
            holder.autoMaker.setText(getString(mDataset.get(position).getText()));
            if (mDataset.get(position).getText() != GameManager.getInstance().achievements.get(0).getUpgrade().getText()) {
                holder.bonus.setText("x" + GameManager.getInstance().format(mDataset.get(position).getBonus()));
            } else {
                holder.bonus.setText("+" + GameManager.getInstance().format(mDataset.get(position).getBonus()) + "% of dps");
            }
            holder.imageView.setImageResource(mDataset.get(position).getImage());
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
            public TextView bonus;
            public TextView autoMaker;
            public Button buy;


            public MyViewHolder(View itemView) {
                super(itemView);


                bonus = itemView.findViewById(R.id.requirement);
                autoMaker = itemView.findViewById(R.id.effect);
                buy = itemView.findViewById(R.id.buy);
                imageView = itemView.findViewById(R.id.image);

            }
        }


    }


}