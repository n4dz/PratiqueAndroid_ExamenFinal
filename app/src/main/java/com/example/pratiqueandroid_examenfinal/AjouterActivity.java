package com.example.pratiqueandroid_examenfinal;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class AjouterActivity extends AppCompatActivity {
    ArrayList<Film> listeFilms;
    String[] choix_categ =new String[]{"Choisir une cat.","1","2","3","4","5"};
    String[] choix_langue=new String[]{"Choisir une langue","FR","AN"};
    String[] choix_cote=new String[]{"Choisir une cote","1","2","3","4","5"};
    String msg = "Problème avec l'enregistrement";
    String pochette;
    ImageView imageView ;
    int PICK_IMAGE_REQUEST = 111;
    Bitmap bitmap;
    ActivityResultLauncher<PickVisualMediaRequest> launcher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri o) {
            if (o == null) {
                Toast.makeText(AjouterActivity.this, "Aucune image selectionner", Toast.LENGTH_SHORT).show();
                imageView.setImageDrawable(getDrawable(R.drawable.film));
                pochette = getDrawable(R.drawable.film)+"";
            } else {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), o);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,"File",null);
                //Placer image dans ImageView
                pochette = path;
                imageView.setImageURI(o);
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);
        chargerDonnees();
//        remplirSpinner();

        gestionEvent();


    }
    private void chargerDonnees(){
        Bundle donnees = getIntent().getExtras();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            listeFilms = donnees.getParcelableArrayList("listeFilms",Film.class);
        }
    }
//    private  void  remplirSpinner(){
//        Spinner spinner_categ = (Spinner) findViewById(R.id.spinner_categorie);
//        ArrayAdapter<String> adapter_categorie = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, choix_categ);
//        adapter_categorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_categ.setAdapter(adapter_categorie);
//
//        Spinner spinner_langue = (Spinner) findViewById(R.id.spinner_langue);
//        ArrayAdapter<String> adapter_langue = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, choix_langue);
//        adapter_langue.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_langue.setAdapter(adapter_langue);
//
//        Spinner spinner_cote = (Spinner) findViewById(R.id.spinner_cote);
//        ArrayAdapter<String> adapter_cote = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, choix_cote);
//        adapter_cote.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_cote.setAdapter(adapter_cote);
//    }
    private void gestionEvent(){
        Button retour = findViewById(R.id.retour_ajouter);
        retour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent result = new Intent();
                result.putExtra("listeFilms", listeFilms);
                setResult(RESULT_OK, result);
                finish();
            }
        });
        Button btn_ajouter = findViewById(R.id.ajouter);
        btn_ajouter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ajouter();
            }
        });
        Button btn_image = findViewById(R.id.button_image);
        btn_image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                imageView =findViewById(R.id.imageSelectionner);
                launcher.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)

                        .build());
            }

        });

    }

    private void ajouter(){

        int num, categ, cote;
        String num_texte,titre, langue;

        TextView vw_num,vw_titre;
        Spinner vw_categ,vw_langue,vw_cote;
        vw_num = findViewById(R.id.num_ajouter);
//        vw_titre = findViewById(R.id.titre_ajouter);
//        vw_categ = findViewById(R.id.spinner_categorie);
//        vw_langue = findViewById(R.id.spinner_langue);
//        vw_cote = findViewById(R.id.spinner_cote);

        num_texte = vw_num.getText().toString();
        titre = vw_titre.getText().toString();
        if(num_texte.isEmpty()|| titre.isEmpty()||vw_categ.getSelectedItemPosition()==0||vw_langue.getSelectedItemPosition()==0 ||vw_cote.getSelectedItemPosition()==0){
            Toast.makeText(AjouterActivity.this, "Veuillez remplir tous les champs " ,Toast.LENGTH_SHORT).show();
        }
        else{
            num =Integer.parseInt(num_texte);
            categ = Integer.parseInt(vw_categ.getSelectedItem().toString());
            langue = vw_langue.getSelectedItem().toString();
            cote = Integer.parseInt(vw_cote.getSelectedItem().toString());
            Film unfilm;
            unfilm = new Film(num, titre, categ, langue, cote, pochette);
            listeFilms.add(unfilm);

            DatabaseHelper myDB = new DatabaseHelper(AjouterActivity.this);
            myDB.ajouterFilm(unfilm);

            Toast.makeText(AjouterActivity.this, "Film enregistré",Toast.LENGTH_SHORT).show();
            vw_num.setText("");
            vw_titre.setText("");
            vw_categ.setSelection(0);
            vw_langue.setSelection(0);
            vw_cote.setSelection(0);
            imageView.setImageDrawable(getDrawable(R.drawable.film));
        }
        try{

        }catch(Exception e){
            Toast.makeText(AjouterActivity.this, "Problème d'enregistrement : "+e ,Toast.LENGTH_SHORT).show();
        }
    }
}