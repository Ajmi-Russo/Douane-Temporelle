package com.example.DouaneTemporelle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

// Activité 1: formulaire l tasjil
public class MainActivity extends AppCompatActivity {

    // asemi el echohra eli ybeno fel picker
    private static final String[] MOIS = {
            "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
            "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"
    };

    private EditText edtNom, edtNaissance, edtCarte;
    private RadioGroup rgMotif;
    private NumberPicker npYear, npMonth, npDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // narebtou el vues bel ids
        edtNom = findViewById(R.id.edt_nom_aj);
        edtNaissance = findViewById(R.id.edt_naissance_aj);
        edtCarte = findViewById(R.id.edt_carte_aj);
        rgMotif = findViewById(R.id.rg_motif_aj);
        npYear = findViewById(R.id.np_year_aj);
        npMonth = findViewById(R.id.np_month_aj);
        npDay = findViewById(R.id.np_day_aj);
        Button btnInspecter = findViewById(R.id.btn_inspecter_aj);

        setupPickers();

        // ki nenzel 3al bouton nbadlou lel activité 2 (switch)
        btnInspecter.setOnClickListener(v -> inspecterProfil());
    }

    // ta7dhir el 3 pickers (3am / chhar / nhar)
    private void setupPickers() {
        // el 3am: men 1000 l 3000, el defaut 2050
        npYear.setMinValue(1000);
        npYear.setMaxValue(3000);
        npYear.setValue(2050);
        npYear.setWrapSelectorWheel(false);

        // el chhar: 1..12 w nechrou el asm2
        npMonth.setMinValue(1);
        npMonth.setMaxValue(12);
        npMonth.setDisplayedValues(MOIS);
        npMonth.setValue(1);

        // el nhar: yetbadel 7asb el chhar w el 3am
        npDay.setMinValue(1);
        npDay.setMaxValue(31);
        npDay.setValue(1);
        updateDays();

        npYear.setOnValueChangedListener((p, o, n) -> updateDays());
        npMonth.setOnValueChangedListener((p, o, n) -> updateDays());

        // bech l clavier ma yatla3ch ki nal3ab bel pickers (l3ajla)
        NumberPicker[] pickers = {npYear, npMonth, npDay};
        for (NumberPicker p : pickers) {
            p.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            // color l ktiba abyedh
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                p.setTextColor(Color.WHITE);
            }
        }
    }

    // n7sbou akther nhar fel chhar eli a7na fih
    private void updateDays() {
        GregorianCalendar cal = new GregorianCalendar(
                npYear.getValue(), npMonth.getValue() - 1, 1);
        int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        npDay.setMaxValue(max);
    }

    // na9ra données, nverifiw, w naadehom lel activité 2
    private void inspecterProfil() {
        String nom = edtNom.getText().toString().trim();
        String naissanceStr = edtNaissance.getText().toString().trim();

        // verification mta3 lesm
        if (nom.isEmpty()) {
            edtNom.setError(getString(R.string.error_name));
            return;
        }

        // verification mta3 l4 ar9am mta3 carte étudiant
        String carte = edtCarte.getText().toString().trim();
        if (carte.length() != 4) {
            edtCarte.setError(getString(R.string.error_card));
            return;
        }

        // verification mta3 3am lwileda
        if (naissanceStr.isEmpty()) {
            edtNaissance.setError(getString(R.string.error_birth_year));
            return;
        }

        int naissance = Integer.parseInt(naissanceStr);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (naissance < 1800 || naissance > currentYear) {
            edtNaissance.setError(getString(R.string.error_invalid_year));
            return;
        }

        // lwin (3am + date keml)
        int destination = npYear.getValue();
        String date = String.format(Locale.getDefault(), "%02d %s %d",
                npDay.getValue(), MOIS[npMonth.getValue() - 1], destination);

        // motif 7asb chip eli te5taret
        String motif;
        int checkedId = rgMotif.getCheckedRadioButtonId();
        if (checkedId == R.id.rdo_passe_aj) {
            motif = getString(R.string.purpose_past);
        } else if (checkedId == R.id.rdo_fuite_aj) {
            motif = getString(R.string.purpose_escape);
        } else {
            motif = getString(R.string.purpose_tourism);
        }

        // Intent explicite + clés mabnia bel méthode statique
        Intent intent = new Intent(MainActivity.this, verif.class);
        intent.putExtra(IntentKeys.getKey(IntentKeys.MATRICULE, IntentKeys.NOM), nom);
        intent.putExtra(IntentKeys.getKey(IntentKeys.MATRICULE, IntentKeys.CARTE), carte);
        intent.putExtra(IntentKeys.getKey(IntentKeys.MATRICULE, IntentKeys.NAISSANCE), naissance);
        intent.putExtra(IntentKeys.getKey(IntentKeys.MATRICULE, IntentKeys.DESTINATION), destination);
        intent.putExtra(IntentKeys.getKey(IntentKeys.MATRICULE, IntentKeys.DATE), date);
        intent.putExtra(IntentKeys.getKey(IntentKeys.MATRICULE, IntentKeys.MOTIF), motif);
        startActivity(intent);
    }
}
