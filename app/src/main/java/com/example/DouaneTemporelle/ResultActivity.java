package com.example.DouaneTemporelle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

// Activité 3: lverdict final
public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // na9raw lverdict (boolean) w esm lmsefr
        Intent in = getIntent();
        boolean approuve = in.getBooleanExtra(
                IntentKeys.getKey(IntentKeys.MATRICULE, IntentKeys.VERDICT), false);
        String nom = in.getStringExtra(
                IntentKeys.getKey(IntentKeys.MATRICULE, IntentKeys.NOM));
        if (nom == null) nom = "";

        ImageView imgVerdict = findViewById(R.id.img_verdict_aj);
        TextView txtTitle = findViewById(R.id.txt_verdict_title_aj);
        TextView txtMessage = findViewById(R.id.txt_message_aj);
        Button btnNouveau = findViewById(R.id.btn_nouveau_aj);
        ImageView imgBg = findViewById(R.id.img_bg_aj);

        // fond yetbadel 7asb el verdict: a5dhar(approve) / a7mer(reject)
        if (approuve) {
            imgBg.setImageResource(R.drawable.approved);
            imgVerdict.setImageResource(R.drawable.ic_check);
            txtTitle.setText(R.string.voyage_approved);
            txtMessage.setText(getString(R.string.msg_approved, nom));
        } else {
            imgBg.setImageResource(R.drawable.rejected);
            imgVerdict.setImageResource(R.drawable.ic_cross);
            txtTitle.setText(R.string.voyage_refused);
            txtMessage.setText(getString(R.string.msg_refused, nom));
        }

        // voyageur jdid: nraj3ou lel MainActivity w nsakerlou lactivités eli 9balha
        btnNouveau.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
