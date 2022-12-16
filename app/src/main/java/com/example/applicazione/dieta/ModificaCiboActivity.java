package com.example.applicazione.dieta;

import static com.example.applicazione.dieta.SelezionaCiboActivity.CIBO_ID_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applicazione.R;

import java.util.ArrayList;
import java.util.List;

public class ModificaCiboActivity extends AppCompatActivity {
    private static final String TAG = "ModificaCiboActivity";

    private TextView txtNomeCiboIns, txtCat, txtEner, txtLip, txtAcidi, txtCol,
            txtCarbo, txtZucc, txtFib, txtProt, txtSal;

    private ImageView imgNome, imgCategoria, imgEnergia, imgLipidi, imgAcidiGrassi, imgColesterolo,
            imgCarboidrati, imgZuccheri, imgFibre, imgProteine, imgSale;

    private DataBaseCibo dataBaseCibo;
    private DataBaseDieta dataBaseDieta;

    private int ciboId;
    private String nomeCibo;

    private Cibo cibo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_cibo);

        //chiamo l'action bar
        ActionBar actionBar = getSupportActionBar();

        //mostro il back button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initView();
        dataBaseCibo = new DataBaseCibo(this);
        dataBaseDieta = new DataBaseDieta(this);

        //ricevo l'id del cibo da modificare
        Intent intent = getIntent();
        if (intent != null) {
            ciboId = intent.getIntExtra(CIBO_ID_KEY, -1);
            if (ciboId != -1) {
                cibo = dataBaseCibo.getCiboById(ciboId);
                txtNomeCiboIns.setText(cibo.getNome());
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Errore nella selezione del cibo");
                builder.setPositiveButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });

                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        }

        setTexts(cibo);

        imgNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ModificaCiboActivity.this);

                builder.setMessage("Inserisci il nuovo nome: ");

                final EditText input = new EditText(ModificaCiboActivity.this);
                input.setText(cibo.getNome());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                input.setLayoutParams(lp);
                builder.setView(input);

                builder.setPositiveButton("Modifica", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //non faccio nulla perché faccio l'override più avanti
                    }
                });

                builder.setNegativeButton("Annulla", null);

                final AlertDialog dialog2 = builder.create();
                dialog2.setView(input, 60, 0, 60, 0);
                dialog2.show();

                dialog2.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (input.getText().toString().equals("")) {
                            Toast.makeText(ModificaCiboActivity.this, "Inserire il nome", Toast.LENGTH_SHORT).show();
                        } else {
                            String temp = input.getText().toString();
                            nomeCibo = temp.substring(0, 1).toUpperCase() + temp.substring(1);
                            if (dataBaseCibo.getNomi().contains(nomeCibo)) {
                                Toast.makeText(ModificaCiboActivity.this, "Il cibo " + nomeCibo + " esiste già", Toast.LENGTH_SHORT).show();
                            } else {
                                cibo.setNome(nomeCibo);
                                dataBaseCibo.modificaCibo(ciboId, cibo);
                                txtNomeCiboIns.setText(nomeCibo);
                                dialog2.dismiss();
                            }
                        }
                    }
                });
            }
        });

        imgCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] cat = {"Bevande Alcoliche",
                        "Bevande Analcoliche",
                        "Brodi",
                        "Carne",
                        "Cibi Asiatici",
                        "Cibi italiani",
                        "Cibi salati",
                        "Dolciumi",
                        "Frutta",
                        "Grassi e oli",
                        "Insaccati",
                        "Insalate",
                        "Legumi",
                        "Pane e cereali",
                        "Pasta",
                        "Patate",
                        "Pesce",
                        "Salse",
                        "Spezie",
                        "Torte salate",
                        "Uova e latticini",
                        "Verdure",
                        "Altro"};

                AlertDialog.Builder builder = new AlertDialog.Builder(ModificaCiboActivity.this);

                builder.setMessage("Scegliere la categoria: ");

                final ArrayAdapter<String> adp = new ArrayAdapter<String>(ModificaCiboActivity.this,
                        android.R.layout.simple_spinner_item, cat);

                final Spinner sp = new Spinner(ModificaCiboActivity.this);
                sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                sp.setAdapter(adp);

                builder.setView(sp);

                builder.setPositiveButton("Modifica", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //non faccio nulla perché faccio l'override più avanti
                    }
                });

                builder.setNegativeButton("Annulla", null);

                final AlertDialog dialog = builder.create();
                dialog.setView(sp, 50, 0, 50, 0);
                dialog.show();

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cibo.setCategoria(sp.getSelectedItem().toString());
                        dataBaseCibo.modificaCibo(ciboId, cibo);
                        txtCat.setText("Categoria: " + sp.getSelectedItem().toString());
                        dialog.dismiss();
                    }
                });
            }
        });

        imgEnergia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ModificaCiboActivity.this);
                builder.setMessage("Inserisci il nuovo valore: ");

                final EditText input = new EditText(ModificaCiboActivity.this);
                input.setText(cibo.getEnergia().toString());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                builder.setView(input);

                builder.setPositiveButton("Modifica", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //non faccio nulla perché faccio l'override più avanti
                    }
                });

                builder.setNegativeButton("Annulla", null);

                final AlertDialog dialog = builder.create();
                dialog.setView(input, 60, 0, 60, 0);
                dialog.show();

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (input.getText().toString().equals("")) {
                            Toast.makeText(ModificaCiboActivity.this, "Inserire il valore", Toast.LENGTH_SHORT).show();
                        } else {
                            double energia = Double.parseDouble(input.getText().toString());
                            if (energia > 1000) {
                                Toast.makeText(ModificaCiboActivity.this, "L'energia non può superare le 1000 kcal", Toast.LENGTH_SHORT).show();
                            } else {
                                cibo.setEnergia(energia);
                                dataBaseCibo.modificaCibo(ciboId, cibo);
                                txtEner.setText("Calorie: " + Double.parseDouble(input.getText().toString()) + " kcal");
                                dialog.dismiss();
                            }
                        }
                    }
                });

            }
        });

        imgLipidi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ModificaCiboActivity.this);
                builder.setMessage("Inserisci il nuovo valore: ");

                final EditText input = new EditText(ModificaCiboActivity.this);
                input.setText(cibo.getLipidi().toString());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                builder.setView(input);

                builder.setPositiveButton("Modifica", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //non faccio nulla perché faccio l'override più avanti
                    }
                });

                builder.setNegativeButton("Annulla", null);

                final AlertDialog dialog = builder.create();
                dialog.setView(input, 60, 0, 60, 0);
                dialog.show();

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (input.getText().toString().equals("")) {
                            Toast.makeText(ModificaCiboActivity.this, "Inserire il valore", Toast.LENGTH_SHORT).show();
                        } else {
                            double lipidi = Double.parseDouble(input.getText().toString());
                            if (lipidi > 100) {
                                Toast.makeText(ModificaCiboActivity.this, "Il valore di lipidi non può superare i 100 g", Toast.LENGTH_SHORT).show();
                            } else {
                                cibo.setLipidi(lipidi);
                                dataBaseCibo.modificaCibo(ciboId, cibo);
                                txtLip.setText("Lipidi: " + Double.parseDouble(input.getText().toString()) + " g");
                                dialog.dismiss();
                            }
                        }
                    }
                });
            }
        });

        imgAcidiGrassi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ModificaCiboActivity.this);
                builder.setMessage("Inserisci il nuovo valore: ");

                final EditText input = new EditText(ModificaCiboActivity.this);
                input.setText(cibo.getAcidigrassi().toString());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                builder.setView(input);

                builder.setPositiveButton("Modifica", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //non faccio nulla perché faccio l'override più avanti
                    }
                });

                builder.setNegativeButton("Annulla", null);

                final AlertDialog dialog = builder.create();
                dialog.setView(input, 60, 0, 60, 0);
                dialog.show();

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (input.getText().toString().equals("")) {
                            Toast.makeText(ModificaCiboActivity.this, "Inserire il valore", Toast.LENGTH_SHORT).show();
                        } else {
                            double acidigrassi = Double.parseDouble(input.getText().toString());
                            if (acidigrassi > 100) {
                                Toast.makeText(ModificaCiboActivity.this, "Il valore di acidi grassi non può superare i 100 g", Toast.LENGTH_SHORT).show();
                            } else {
                                cibo.setAcidigrassi(acidigrassi);
                                dataBaseCibo.modificaCibo(ciboId, cibo);
                                txtAcidi.setText("Acidi grassi: " + Double.parseDouble(input.getText().toString()) + " g");
                                dialog.dismiss();
                            }
                        }
                    }
                });
            }
        });

        imgColesterolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ModificaCiboActivity.this);
                builder.setMessage("Inserisci il nuovo valore: ");

                final EditText input = new EditText(ModificaCiboActivity.this);
                input.setText(cibo.getColesterolo().toString());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                builder.setView(input);

                builder.setPositiveButton("Modifica", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //non faccio nulla perché faccio l'override più avanti
                    }
                });

                builder.setNegativeButton("Annulla", null);

                final AlertDialog dialog = builder.create();
                dialog.setView(input, 60, 0, 60, 0);
                dialog.show();

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (input.getText().toString().equals("")) {
                            Toast.makeText(ModificaCiboActivity.this, "Inserire il valore", Toast.LENGTH_SHORT).show();
                        } else {
                            double colesterolo = Double.parseDouble(input.getText().toString());
                            if (colesterolo > 100) {
                                Toast.makeText(ModificaCiboActivity.this, "Il valore di colesterolo non può superare i 100 g", Toast.LENGTH_SHORT).show();
                            } else {
                                cibo.setColesterolo(colesterolo);
                                dataBaseCibo.modificaCibo(ciboId, cibo);
                                txtCol.setText("Colesterolo: " + Double.parseDouble(input.getText().toString()) + " g");
                                dialog.dismiss();
                            }
                        }
                    }
                });
            }
        });

        imgCarboidrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ModificaCiboActivity.this);
                builder.setMessage("Inserisci il nuovo valore: ");

                final EditText input = new EditText(ModificaCiboActivity.this);
                input.setText(cibo.getCarboidrati().toString());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                builder.setView(input);

                builder.setPositiveButton("Modifica", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //non faccio nulla perché faccio l'override più avanti
                    }
                });

                builder.setNegativeButton("Annulla", null);

                final AlertDialog dialog = builder.create();
                dialog.setView(input, 60, 0, 60, 0);
                dialog.show();

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (input.getText().toString().equals("")) {
                            Toast.makeText(ModificaCiboActivity.this, "Inserire il valore", Toast.LENGTH_SHORT).show();
                        } else {
                            double carbo = Double.parseDouble(input.getText().toString());
                            if (carbo > 100) {
                                Toast.makeText(ModificaCiboActivity.this, "Il valore dei carboidrati non può superare i 100 g", Toast.LENGTH_SHORT).show();
                            } else {
                                cibo.setCarboidrati(carbo);
                                dataBaseCibo.modificaCibo(ciboId, cibo);
                                txtCarbo.setText("Carboidrati: " + Double.parseDouble(input.getText().toString()) + " g");
                                dialog.dismiss();
                            }
                        }
                    }
                });
            }
        });

        imgZuccheri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ModificaCiboActivity.this);
                builder.setMessage("Inserisci il nuovo valore: ");

                final EditText input = new EditText(ModificaCiboActivity.this);
                input.setText(cibo.getZuccheri().toString());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                builder.setView(input);

                builder.setPositiveButton("Modifica", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //non faccio nulla perché faccio l'override più avanti
                    }
                });

                builder.setNegativeButton("Annulla", null);

                final AlertDialog dialog = builder.create();
                dialog.setView(input, 60, 0, 60, 0);
                dialog.show();

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (input.getText().toString().equals("")) {
                            Toast.makeText(ModificaCiboActivity.this, "Inserire il valore", Toast.LENGTH_SHORT).show();
                        } else {
                            double zuccheri = Double.parseDouble(input.getText().toString());
                            if (zuccheri > 100) {
                                Toast.makeText(ModificaCiboActivity.this, "Il valore degli zuccheri non può superare i 100 g", Toast.LENGTH_SHORT).show();
                            } else {
                                cibo.setZuccheri(zuccheri);
                                dataBaseCibo.modificaCibo(ciboId, cibo);
                                txtZucc.setText("Zuccheri: " + Double.parseDouble(input.getText().toString()) + " g");
                                dialog.dismiss();
                            }
                        }
                    }
                });
            }
        });

        imgFibre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ModificaCiboActivity.this);
                builder.setMessage("Inserisci il nuovo valore: ");

                final EditText input = new EditText(ModificaCiboActivity.this);
                input.setText(cibo.getFibre().toString());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                builder.setView(input);

                builder.setPositiveButton("Modifica", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //non faccio nulla perché faccio l'override più avanti
                    }
                });

                builder.setNegativeButton("Annulla", null);

                final AlertDialog dialog = builder.create();
                dialog.setView(input, 60, 0, 60, 0);
                dialog.show();

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (input.getText().toString().equals("")) {
                            Toast.makeText(ModificaCiboActivity.this, "Inserire il valore", Toast.LENGTH_SHORT).show();
                        } else {
                            double fibre = Double.parseDouble(input.getText().toString());
                            if (fibre > 100) {
                                Toast.makeText(ModificaCiboActivity.this, "Il valore delle fibre non può superare i 100 g", Toast.LENGTH_SHORT).show();
                            } else {
                                cibo.setFibre(fibre);
                                dataBaseCibo.modificaCibo(ciboId, cibo);
                                txtFib.setText("Fibre: " + Double.parseDouble(input.getText().toString()) + " g");
                                dialog.dismiss();
                            }
                        }
                    }
                });
            }
        });

        imgProteine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ModificaCiboActivity.this);
                builder.setMessage("Inserisci il nuovo valore: ");

                final EditText input = new EditText(ModificaCiboActivity.this);
                input.setText(cibo.getProteine().toString());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                builder.setView(input);

                builder.setPositiveButton("Modifica", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //non faccio nulla perché faccio l'override più avanti
                    }
                });

                builder.setNegativeButton("Annulla", null);

                final AlertDialog dialog = builder.create();
                dialog.setView(input, 60, 0, 60, 0);
                dialog.show();

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (input.getText().toString().equals("")) {
                            Toast.makeText(ModificaCiboActivity.this, "Inserire il valore", Toast.LENGTH_SHORT).show();
                        } else {
                            double proteine = Double.parseDouble(input.getText().toString());
                            if (proteine > 100) {
                                Toast.makeText(ModificaCiboActivity.this, "Il valore di proteine non può superare i 100 g", Toast.LENGTH_SHORT).show();
                            } else {
                                cibo.setProteine(proteine);
                                dataBaseCibo.modificaCibo(ciboId, cibo);
                                txtProt.setText("Proteine: " + Double.parseDouble(input.getText().toString()) + " g");
                                dialog.dismiss();
                            }
                        }
                    }
                });
            }
        });

        imgSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ModificaCiboActivity.this);
                builder.setMessage("Inserisci il nuovo valore: ");

                final EditText input = new EditText(ModificaCiboActivity.this);
                input.setText(cibo.getSale().toString());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                builder.setView(input);

                builder.setPositiveButton("Modifica", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //non faccio nulla perché faccio l'override più avanti
                    }
                });

                builder.setNegativeButton("Annulla", null);

                final AlertDialog dialog = builder.create();
                dialog.setView(input, 60, 0, 60, 0);
                dialog.show();

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (input.getText().toString().equals("")) {
                            Toast.makeText(ModificaCiboActivity.this, "Inserire il valore", Toast.LENGTH_SHORT).show();
                        } else {
                            double sale = Double.parseDouble(input.getText().toString());
                            if (sale > 100) {
                                Toast.makeText(ModificaCiboActivity.this, "Il valore di sale non può superare i 100 g", Toast.LENGTH_SHORT).show();
                            } else {
                                cibo.setSale(sale);
                                dataBaseCibo.modificaCibo(ciboId, cibo);
                                txtSal.setText("Sale: " + Double.parseDouble(input.getText().toString()) + " g");
                                dialog.dismiss();
                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * metodo che cambia i TextView e inserisce i valori del cibo selezionato
     *
     * @param cibo
     */
    private void setTexts(Cibo cibo) {
        txtCat.setText("Categoria: " + cibo.getCategoria());
        txtEner.setText("Calorie: " + cibo.getEnergia().toString() + " kcal");
        txtLip.setText("Lipidi: " + cibo.getLipidi().toString() + " g");
        txtAcidi.setText("Acidi grassi: " + cibo.getAcidigrassi().toString() + " g");
        txtCol.setText("Colesterolo: " + cibo.getColesterolo().toString() + " g");
        txtCarbo.setText("Carboidrati: " + cibo.getCarboidrati().toString() + " g");
        txtZucc.setText("Zuccheri: " + cibo.getZuccheri().toString() + " g");
        txtFib.setText("Fibre: " + cibo.getFibre().toString() + " g");
        txtProt.setText("Proteine: " + cibo.getProteine().toString() + " g");
        txtSal.setText("Sale: " + cibo.getSale().toString() + " g");
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    /**
     * inserisco il menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.elimina_cibo_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.elimina_cibo:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Eliminare il cibo " + cibo.getNome() + "?");
                builder.setPositiveButton("Elimina", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        List<String> nomiDiete = cercaCiboInDiete(ciboId);

                        if (nomiDiete.size() > 0) {
                            StringBuilder sb = new StringBuilder();
                            for (String s : nomiDiete) {
                                sb.append(s);
                                sb.append("\n");
                            }

                            AlertDialog.Builder builder = new AlertDialog.Builder(ModificaCiboActivity.this);
                            builder.setMessage("Questo cibo è contenuto nelle seguenti diete:\n" + sb
                                    + "Rimuoverlo prima di continuare");

                            builder.setNegativeButton("Ok", null);

                            final AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                            dataBaseCibo.eliminaCibo(ciboId);
                            ModificaCiboActivity.this.finish();
                            Intent intent = new Intent(ModificaCiboActivity.this, ElencoCibiActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
                builder.setNegativeButton("Annulla", null);

                final AlertDialog dialog = builder.create();
                dialog.show();
                return true;

            case android.R.id.home:
                goBack();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * funzione per tornare indietro
     * faccio in modo che quando clicco per tornare indietro, lo stack delle activity venga pulito
     */
    private void goBack() {
        Intent intent = new Intent(this, ElencoCibiActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * cerco tutte le occorrenze del cibo di id id e le rimuovo dalle rispettive diete,
     * modificando anche le quantità
     *
     * @param id
     * @return
     */
    private List<String> cercaCiboInDiete(int id) {
        List<Dieta> dietaList = dataBaseDieta.getAllDiete();
        List<String> nomiDiete = new ArrayList<>();
        boolean trovato;

        for (Dieta dieta : dietaList) {
            trovato = dieta.cercaCibo(id);

            if (trovato) {
                nomiDiete.add(dieta.getNome());
            }
        }

        return nomiDiete;
    }

    private void initView() {
        txtNomeCiboIns = findViewById(R.id.txtNomeCiboIns);
        txtCat = findViewById(R.id.txtCat);
        txtEner = findViewById(R.id.txtEner);
        txtLip = findViewById(R.id.txtLip);
        txtAcidi = findViewById(R.id.txtAcidi);
        txtCol = findViewById(R.id.txtCol);
        txtCarbo = findViewById(R.id.txtCarbo);
        txtZucc = findViewById(R.id.txtZucc);
        txtFib = findViewById(R.id.txtFib);
        txtProt = findViewById(R.id.txtProt);
        txtSal = findViewById(R.id.txtSal);

        imgNome = findViewById(R.id.imgNome);
        imgCategoria = findViewById(R.id.imgCategoria);
        imgEnergia = findViewById(R.id.imgEnergia);
        imgLipidi = findViewById(R.id.imgLipidi);
        imgAcidiGrassi = findViewById(R.id.imgAcidiGrassi);
        imgColesterolo = findViewById(R.id.imgColesterolo);
        imgCarboidrati = findViewById(R.id.imgCarboidrati);
        imgZuccheri = findViewById(R.id.imgZuccheri);
        imgFibre = findViewById(R.id.imgFibre);
        imgProteine = findViewById(R.id.imgProteine);
        imgSale = findViewById(R.id.imgSale);
    }
}