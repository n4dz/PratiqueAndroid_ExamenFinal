package com.example.pratiqueandroid_examenfinal;

import static java.lang.Integer.parseInt;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Film> listeFilms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            DatabaseHelper myDB = new DatabaseHelper(MainActivity.this);
            listeFilms = myDB.listerFilm();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        afficherResultat();
        afficherCategorie();
        afficherSupprimer();
        gestionEvents();
        lister();
    }
    private void gestionEvents(){
        Button lister = findViewById(R.id.lister);
        Button categorie = findViewById(R.id.btn_categorie);
        Spinner spinner_categ= findViewById(R.id.spinnerCategorie);
        Button ajouter = findViewById(R.id.afficherAjouter);
        Button supprimer = findViewById(R.id.a_supprimer);
        Button afficher_supprimer = findViewById(R.id.supprimer);
        Button quitter = findViewById(R.id.quitter);
        Button vider = findViewById(R.id.vider);
        lister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lister();
            }
        });

        categorie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                afficherSpinnerCatégorie();
            }
        });
        spinner_categ.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0) {
                    String categSelctionner = spinner_categ.getSelectedItem().toString();
                    listerCatégorie(categSelctionner);
                    spinner_categ.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ajouter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ajouter();
            }
        });


        supprimer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                supprimer();
            }
        });
        afficher_supprimer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                afficherSupprimer();
            }
        });
        quitter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        vider.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                disparaitreTout();
            }
        });


    }
    private void ajouter(){
        Intent intent = new Intent(MainActivity.this, AjouterActivity.class);
        intent.putExtra("listeFilms", listeFilms);
        AjouterActivityResultLaucher.launch(intent);
    }

    ActivityResultLauncher<Intent> AjouterActivityResultLaucher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent donnees = result.getData();
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                            listeFilms = donnees.getParcelableArrayListExtra("listeFilms",Film.class);
                        }
                        // On affiche la donnée envoyé par activité 2
                        lister();
                    }else {
                        Toast.makeText(MainActivity.this, "Problème avec activité 2",Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private void afficherSupprimer(){
        LinearLayout l = findViewById(R.id.layout_supprimer);
        if (l.getVisibility()==View.GONE){
            l.setVisibility(View.VISIBLE);
        }
        else {
            l.setVisibility(View.GONE);
        }
    }
    private void supprimer(){
        TextView aSupprimer = findViewById(R.id.NumSupp);
        int id = parseInt(aSupprimer.getText().toString());
        Film trouver=null;
        for (Film unFilm : listeFilms){
            if (unFilm.getNum() == id) {
                trouver = unFilm;
            }
        }
        if (trouver!=null) {
            listeFilms.remove(trouver);
            DatabaseHelper myDB = new DatabaseHelper(MainActivity.this);
            myDB.supprimerFilm(id);
            Toast.makeText(this, "Film supprimé." ,Toast.LENGTH_SHORT).show();
            aSupprimer.setText("");
            lister();
        }
    }

    private void afficherCategorie(){
        String[] listeCategorie= new String []{"Choisir une catégorie","1","2","3","4","5"};
        Spinner s = (Spinner) findViewById(R.id.spinnerCategorie);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listeCategorie);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        afficherSpinnerCatégorie();
    }

    private void lister(){
        RecyclerView recyclerView = findViewById(R.id.listeFilm);
        Film_RecyclerViewAdapter adapter = new Film_RecyclerViewAdapter(this,listeFilms);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void afficherSpinnerCatégorie(){

        Spinner spinner_categorie = findViewById(R.id.spinnerCategorie);
        if (spinner_categorie.getVisibility()==View.GONE){
            spinner_categorie.setVisibility(View.VISIBLE);
        }
        else {
            spinner_categorie.setVisibility(View.GONE);

        }
    }
    private void listerCatégorie(String categorire){
        Intent intent = new Intent(MainActivity.this, ListerParCategorieActivity.class);
        intent.putExtra("listeFilms", listeFilms);
        intent.putExtra("categorie", categorire);
        ListerParCategorieActivityResultLaucher.launch(intent);
    }

    ActivityResultLauncher<Intent> ListerParCategorieActivityResultLaucher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent donnees = result.getData();
                        String nbF = donnees.getStringExtra("nbFrancais");
                        String nbA = donnees.getStringExtra("nbAnglais");
                        // On affiche la donnée envoyé par activité 2
                        TextView textNombre = findViewById(R.id.resultat);
                        textNombre.setText("Nombre de film en français : "+nbF+"\nNombre de film en anglais : "+nbA);
                        textNombre.setVisibility(View.VISIBLE);
                    }else {
                        Toast.makeText(MainActivity.this, "Problème avec activité 2",Toast.LENGTH_SHORT).show();
                    }
                }
            });

    public void afficherResultat(){
        TextView resultat = findViewById(R.id.resultat);
        if (resultat.getVisibility() == View.GONE){
            resultat.setVisibility(View.VISIBLE);
        }
        else{
            resultat.setVisibility(View.GONE);
        }
    }
    private void disparaitreTout(){
        Spinner categ = findViewById(R.id.spinnerCategorie);
        LinearLayout supp = findViewById(R.id.layout_supprimer);
        TextView resultat = findViewById(R.id.resultat);
        categ.setVisibility(View.GONE);
        supp.setVisibility(View.GONE);
        resultat.setVisibility(View.GONE);

    }
}