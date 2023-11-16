package com.example.pratiqueandroid_examenfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListerParCategorieActivity extends AppCompatActivity {
    ArrayList<Film>listeFilms;
    int categorie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister_par_categorie);
        gestionEvents();
        chargerDonnes();
        afficherCategorie();
        afficherFilms();
    }

    private void gestionEvents(){

        Button retour = findViewById(R.id.retourCategorie);
        retour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int nbF=0 , nbA = 0;
                for (Film unFilm : listeFilms) {
                    switch (unFilm.getLangue()) {
                        case "FR":
                            nbF++;
                            break;
                        case "AN":
                            nbA++;
                            break;
                    }
                }
                Intent result = new Intent();
                result.putExtra("nbFrancais", nbF+"");
                result.putExtra("nbAnglais", nbA+"");
                setResult(RESULT_OK, result);
                finish();
            }
        });

    }
    private void chargerDonnes(){
        Bundle donnees = getIntent().getExtras();
        categorie = Integer.parseInt(donnees.getString("categorie"));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            DatabaseHelper myDB = new DatabaseHelper(ListerParCategorieActivity.this);
            listeFilms = myDB.listerFilmParCategorie(categorie);
        }
    }
    private void afficherCategorie(){
        TextView vw_categorie = findViewById(R.id.categorieChoisit);
        vw_categorie.setText("La catégorie choisie est " + categorie);

    }
    private void afficherFilms(){
        ArrayList<Film> newList=new ArrayList<Film>();
        for (Film unFilm : listeFilms){
            if (unFilm.getCodeCateg() == categorie){
                newList.add(unFilm);
            }
        }
        RecyclerView recyclerView = findViewById(R.id.liste_film_categorie);
        Film_RecyclerViewAdapter adapter = new Film_RecyclerViewAdapter(this,newList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}