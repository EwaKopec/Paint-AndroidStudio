package com.example.paint;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tutaj wszystkie buttony i pierdoly
        final Button colorbutton = findViewById(R.id.colorButton);
        final Button linebutton = findViewById(R.id.lineButton);
        final Button pathbutton = findViewById(R.id.pathButton);
        final Button backgroundbutton = findViewById(R.id.backbutton);
        final Button returnbutton = findViewById(R.id.returnbutton);
        final Button clearbutton = findViewById(R.id.clearbutton);
        final PaintView paintView = findViewById(R.id.paintview);

        colorbutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                ColorPickerDialogBuilder
                        .with(MainActivity.this)
                        .setTitle("Choose color")
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                PaintView.getInstance().current_paint.setColor(selectedColor);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
            }
        });

        linebutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder popDialog = new AlertDialog.Builder(MainActivity.this);
                final SeekBar seek = new SeekBar(MainActivity.this);
                seek.setMax(70);
                seek.setMin(1);
                seek.setKeyProgressIncrement(1);

                popDialog.setIcon(android.R.drawable.progress_indeterminate_horizontal);
                popDialog.setTitle("Select the line thickness");
                popDialog.setView(seek);

                seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        PaintView.getInstance().current_paint.setStrokeWidth(progress);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                popDialog.show();
            }
        });

        pathbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                List<String> stringList = new ArrayList<>();
                stringList.add("None");
                stringList.add("Corner");
                stringList.add("Dash");
                stringList.add("Compose 1");
                stringList.add("Compose 2");

                final RadioGroup rg = new RadioGroup(MainActivity.this);
                for(int i = 0; i < stringList.size(); i++){
                    RadioButton rb = new RadioButton(MainActivity.this);
                    rb.setText(stringList.get(i));
                    rg.addView(rb);
                }

                final CornerPathEffect cornerPathEffect = new CornerPathEffect(50.0f);
                float[] intervals = new float[]{80.0f, 30.0f};
                float phase = 0;
                final DashPathEffect dashPathEffect = new DashPathEffect(intervals, phase);
                final ComposePathEffect composePathEffect1 = new ComposePathEffect(cornerPathEffect, dashPathEffect);
                final ComposePathEffect composePathEffect2 = new ComposePathEffect(dashPathEffect, cornerPathEffect);
                final ComposePathEffect composePathEffect3 = new ComposePathEffect(cornerPathEffect, cornerPathEffect);

                PaintView.getInstance().current_paint.setStyle(Paint.Style.STROKE);

                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if(checkedId == 0){
                            PaintView.getInstance().current_paint.setPathEffect(cornerPathEffect);
                        }else if(checkedId == 1){
                            PaintView.getInstance().current_paint.setPathEffect(dashPathEffect);
                        } else if(checkedId == 2){
                            PaintView.getInstance().current_paint.setPathEffect(composePathEffect1);
                        }
                        else if(checkedId == 3){
                            PaintView.getInstance().current_paint.setPathEffect(composePathEffect2);
                        }
                        else if (checkedId == 4){
                            PaintView.getInstance().current_paint.setPathEffect(composePathEffect3);
                        }
                    }
                });

                dialog.setContentView(rg);
                dialog.show();

            }
        });

        backgroundbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialogBuilder
                        .with(MainActivity.this)
                        .setTitle("Choose color")
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                paintView.setBackgroundColor(selectedColor);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
            }
        });

        clearbutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                paintView.setBackgroundColor(Color.WHITE);
                paintView.clear();
            }
        });

        returnbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintView.removeLastOperation();
            }
        });
    }
}
