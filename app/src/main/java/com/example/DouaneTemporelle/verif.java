package com.example.DouaneTemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

// Activité 2: paradoxe scanner (fel passeport)
public class verif extends AppCompatActivity {

    private String nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_verif);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // na9raw données eli jew m3a l Intent (b nafs les clés)
        Intent in = getIntent();
        nom = in.getStringExtra(IntentKeys.getKey(IntentKeys.MATRICULE, IntentKeys.NOM));
        int naissance = in.getIntExtra(
                IntentKeys.getKey(IntentKeys.MATRICULE, IntentKeys.NAISSANCE), 0);
        int destination = in.getIntExtra(
                IntentKeys.getKey(IntentKeys.MATRICULE, IntentKeys.DESTINATION), 0);
        String date = in.getStringExtra(
                IntentKeys.getKey(IntentKeys.MATRICULE, IntentKeys.DATE));
        String motif = in.getStringExtra(
                IntentKeys.getKey(IntentKeys.MATRICULE, IntentKeys.MOTIF));
        String carte = in.getStringExtra(
                IntentKeys.getKey(IntentKeys.MATRICULE, IntentKeys.CARTE));
        if (nom == null) nom = "";
        if (carte == null) carte = "";
        if (date == null) date = String.valueOf(destination);
        if (motif == null) motif = "";

        // l'age temporel = lwin - 3am lwileda
        int ageTemporel = destination - naissance;

        // nraw lvues
        TextView txtNom = findViewById(R.id.txt_nom_aj);
        TextView txtCarte = findViewById(R.id.txt_carte_aj);
        TextView txtNaissance = findViewById(R.id.txt_naissance_aj);
        TextView txtDestination = findViewById(R.id.txt_destination_aj);
        TextView txtAge = findViewById(R.id.txt_age_aj);
        TextView txtMotif = findViewById(R.id.txt_motif_aj);
        TextView txtParadox = findViewById(R.id.txt_paradox_aj);
        Button btnApprouver = findViewById(R.id.btn_approuver_aj);
        Button btnRefuser = findViewById(R.id.btn_refuser_aj);

        // n3amrou el passeport
        txtNom.setText(getString(R.string.passport_traveler, nom));
        txtCarte.setText(getString(R.string.passport_card, carte));
        txtNaissance.setText(getString(R.string.passport_birth_year, naissance));
        txtDestination.setText(getString(R.string.passport_destination, date));
        txtAge.setText(getString(R.string.passport_age, ageTemporel));
        txtMotif.setText(getString(R.string.passport_purpose, motif));

        // 9a3da: "Modification du passé" + far* = 3dad awali => paradoxe
        boolean paradoxe = motif.equals(getString(R.string.purpose_past))
                && isPrime(Math.abs(ageTemporel));

        if (paradoxe) {
            // btn "Approuver" mel loul (disabled) idha fama paradoxe
            btnApprouver.setEnabled(false);
            btnApprouver.setAlpha(0.4f);
            txtParadox.setText(R.string.paradox_detected);
            txtParadox.setTextColor(ContextCompat.getColor(this, R.color.danger));
        } else {
            txtParadox.setText(R.string.no_paradox);
            txtParadox.setTextColor(ContextCompat.getColor(this, R.color.success));
        }

        // approuver => verdict true, refuser => verdict false
        btnApprouver.setOnClickListener(v -> ouvrirVerdict(true));
        btnRefuser.setOnClickListener(v -> ouvrirVerdict(false));
    }

    // nab3thou lverdict (boolean) w esm el voyageur lel activité 3
    private void ouvrirVerdict(boolean approuve) {
        Intent intent = new Intent(verif.this, ResultActivity.class);
        intent.putExtra(IntentKeys.getKey(IntentKeys.MATRICULE, IntentKeys.VERDICT), approuve);
        intent.putExtra(IntentKeys.getKey(IntentKeys.MATRICULE, IntentKeys.NOM), nom);
        startActivity(intent);
    }

    // test 3dad awali (nombre premier) 
    private boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; (long) i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
