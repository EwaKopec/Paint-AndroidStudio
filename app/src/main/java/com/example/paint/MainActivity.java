package com.example.paint;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //tutaj wszystkie buttony i pierdoly
        final Button colorbutton = findViewById(R.id.colorButton);
        final Button linebutton = findViewById(R.id.lineButton);
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

                seek.setProgress((int) PaintView.getInstance().current_paint.getStrokeWidth());

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
