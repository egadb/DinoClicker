package com.example.projet;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Activity that contains a graph of progression
 */
public class ProgressGraph extends AppCompatActivity {

    private double MaxY;
    private double MaxX;
    private Button quitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_graph);

        //Initialize button
        quitter = findViewById(R.id.quitter);
        quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Graph with points from GameManager
        GraphView graph = findViewById(R.id.graph);

        DataPoint[] points = new DataPoint[GameManager.getInstance().points.size()];

        for (int i = 0; i < GameManager.getInstance().points.size(); i++) {
            points[i] = new DataPoint(GameManager.getInstance().points.get(i).getX(), GameManager.getInstance().points.get(i).getY());
        }

        MaxY = GameManager.getInstance().maxDino + (20 / 100 * GameManager.getInstance().maxDino);
        MaxX = (GameManager.getInstance().time / 10) + (20 / 100 * GameManager.getInstance().time);

        //Naming axis
        graph.setTitle(getString(R.string.graphTitle));
        graph.getGridLabelRenderer().setHorizontalAxisTitle(getString(R.string.graphHorizontalAxis));
        graph.getGridLabelRenderer().setVerticalAxisTitle(getString(R.string.graphVerticalAxis));


        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {

            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return super.formatLabel(value, isValueX) + "s";
                } else {

                    String formated = GameManager.getInstance().format(value);
                    return formated;
                }

            }

        });

        //Creating the graph with points made
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);

        graph.getViewport().setYAxisBoundsManual(false);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(MaxY);

        graph.getViewport().setXAxisBoundsManual(false);
        graph.getViewport().setMinX(4);
        graph.getViewport().setMaxX(MaxX);

        // enable scaling and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        graph.addSeries(series);
    }
}
