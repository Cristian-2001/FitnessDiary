package com.example.applicazione.allenamento;

import static com.example.applicazione.allenamento.SvolgiEsercizioActivity.NUMERO_ES;
import static com.example.applicazione.allenamento.SvolgiEsercizioActivity.NUMERO_SERIE;
import static com.example.applicazione.allenamento.VisualizzaAllenamentoActivity.ALLENAMENTO_ID_KEY;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applicazione.R;
import com.example.applicazione.dieta.ElencoDieteActivity;
import com.example.applicazione.dieta.VisualizzaDietaActivity;

import java.util.concurrent.TimeUnit;

public class TimerActivity extends AppCompatActivity {
    private TextView txtTimer;
    private Button btnInterrompiTimer, btnNextEs;

    private DataBaseAllenamento dataBaseAllenamento;

    long startTime = 0;

    //id dell'allenamento avviato
    private int allenamentoid;

    //numero dell'esercizio
    private int numEs;

    //numero di esercizi totali
    private int esTot;

    //numero della serie
    private int numSerie;

    //tempo di recupero
    private int time;

    private ColorStateList oldColors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        dataBaseAllenamento = new DataBaseAllenamento(TimerActivity.this);

        initView();
        btnNextEs.setClickable(false);
        btnNextEs.setBackgroundColor(getResources().getColor(R.color.grey));

        Intent intent = getIntent();
        if (intent != null) {
            allenamentoid = intent.getIntExtra(ALLENAMENTO_ID_KEY, -1);
            numEs = intent.getIntExtra(NUMERO_ES, -1);
            numSerie = intent.getIntExtra(NUMERO_SERIE, -1);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(TimerActivity.this);
            builder.setMessage("Si è verificato un errore");
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.show();
        }

        if (allenamentoid != -1 && numEs != -1) {
            time = dataBaseAllenamento.getAllenamentoById(allenamentoid).getEserciziTrec().get(numEs);
            startTime = time;
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(TimerActivity.this);
            builder.setMessage("Si è verificato un errore");
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.show();
        }

        CountDownTimer countDownTimer = new CountDownTimer(TimeUnit.SECONDS.toMillis(time), 1000) {

            public void onTick(long millisUntilFinished) {
                txtTimer.setText(String.valueOf((millisUntilFinished / 1000) + 1));
            }

            public void onFinish() {
                txtTimer.setText("0");
                Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), alarm);
                r.play();
                btnNextEs.setClickable(true);
                int nightModeFlags =
                        getResources().getConfiguration().uiMode &
                                Configuration.UI_MODE_NIGHT_MASK;
                switch (nightModeFlags) {
                    case Configuration.UI_MODE_NIGHT_YES:
                        btnNextEs.setBackgroundColor(getResources().getColor(R.color.purple_200));
                        break;

                    case Configuration.UI_MODE_NIGHT_NO:
                        btnNextEs.setBackgroundColor(getResources().getColor(R.color.purple_500));
                        break;

                    default:
                        break;
                }
                btnNextEs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        r.stop();

                        if (numSerie == dataBaseAllenamento.getAllenamentoById(allenamentoid).getEserciziSerie().get(numEs)) {
                            numEs++;
                            numSerie = 1;
                        } else {
                            numSerie++;
                        }

                        if (numEs == dataBaseAllenamento.getAllenamentoById(allenamentoid).getNumElem()) {
                            Toast.makeText(TimerActivity.this, "Allenamento concluso", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TimerActivity.this, VisualizzaAllenamentoActivity.class);
                            intent.putExtra(ALLENAMENTO_ID_KEY, allenamentoid);
                            TimerActivity.this.startActivity(intent);
                        } else {
                            Intent intent = new Intent(TimerActivity.this, SvolgiEsercizioActivity.class);
                            intent.putExtra(ALLENAMENTO_ID_KEY, allenamentoid);
                            intent.putExtra(NUMERO_SERIE, numSerie);
                            intent.putExtra(NUMERO_ES, numEs);
                            TimerActivity.this.startActivity(intent);
                        }
                    }
                });

                btnInterrompiTimer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        r.stop();
                        Intent intent = new Intent(TimerActivity.this, VisualizzaAllenamentoActivity.class);
                        intent.putExtra(ALLENAMENTO_ID_KEY, allenamentoid);
                        TimerActivity.this.startActivity(intent);
                    }
                });
            }

        }.start();

        btnInterrompiTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.cancel();
                Intent intent = new Intent(TimerActivity.this, VisualizzaAllenamentoActivity.class);
                intent.putExtra(ALLENAMENTO_ID_KEY, allenamentoid);
                TimerActivity.this.startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        //non faccio nulla perché non voglio consentire di tornare indietro
    }

    private void initView() {
        txtTimer = findViewById(R.id.txtTimer);
        btnInterrompiTimer = findViewById(R.id.btnInterrompiTimer);
        btnNextEs = findViewById(R.id.btnNextEs);

        oldColors = btnNextEs.getBackgroundTintList();
    }


}