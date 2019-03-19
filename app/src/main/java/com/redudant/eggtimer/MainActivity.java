package com.redudant.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //ini disebut variable global
    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controllButton;
    boolean counterIsAcrive = false; //membuat seekbar menjadi aktive dan tidak aktive
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = (TextView) findViewById(R.id.timerTextView);
        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        controllButton = (Button) findViewById(R.id.controllButton);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {

                /* ini dimasuka ke method terpisah agar bisa dipanggil dua kali
                int minutes = (int) i / 60;
                int seconds = i - minutes * 60;

                //membuat string baru
                String secondString = Integer.toString(seconds);

                if (secondString == "0") {

                    secondString = "00";
                }

                timerTextView.setText(Integer.toString(minutes) + ":" + secondString);
                */

                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void resetTimer () {

        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        controllButton.setText("Go!");
        timerSeekBar.setEnabled(true);
        counterIsAcrive = false;
    }
    public void controllTimer(View view) {

        if (counterIsAcrive == false) {

            counterIsAcrive = true; //ketika tombol button di tekan maka seekbar akan hilang
            timerSeekBar.setEnabled(false); //
            controllButton.setText("Stop"); //mengatur tex berhenti

            //untuk menguji button berfungsi dengan benar atau tidak
            //Log.i("Button press", "Success")

            //menghitung mundur setiap detik
            //mengapatkan nilainya dengan seekbar value dengan menjadika seekbar variable global
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    //memperbaruhi waktu dibagi 1000 untuk mendapatkan detik
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {

                    resetTimer();

                    //timerTextView.setText("0:00");

                    //Log.i("Finish", "timer done");

                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();

                }
            }.start();

        } //penutup counterIsAcrive
        else {

            resetTimer();
        }

    }

    //membutuhkan integer yang akan menjadi detik sisah
    //membuat method updateTimer
    public void updateTimer(int secondLeft) {

        int minutes = (int) secondLeft / 60;
        int seconds = secondLeft - minutes * 60;

        //membuat string baru
        String secondString = Integer.toString(seconds);

        //jika seconds kurang dari atau sama dengan
        if (seconds <= 9) {

            //maka kita menambah nol didepannya
            secondString = "0" + secondString;
        }

        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);

    }
}
