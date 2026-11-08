package com.example.DouaneTemporelle;

public class IntentKeys {

    // 4 ar9am lekhrin mel carte d'etudiant
    public static final String MATRICULE = "1234";

    // el asm2 mta3 les clés (el prefix yet7at automatiquement)
    public static final String NOM = "NOM_VOYAGEUR";
    public static final String NAISSANCE = "ANNEE_NAISSANCE";
    public static final String DESTINATION = "ANNEE_DESTINATION";
    public static final String DATE = "DATE_DESTINATION";
    public static final String MOTIF = "MOTIF_VOYAGE";
    public static final String VERDICT = "VERDICT";

    public static final String CARTE = "CARTE_ETUDIANT";

    // e5er 4 ar9am mel carte d'etudiant
    public static String getKey(String matricule, String keyName) {

        if (matricule == null || matricule.length() < 4) {
            throw new IllegalArgumentException(
                    "Le matricule doit contenir au moins 4 chiffres"
            );
        }

        String prefix = matricule.substring(
                matricule.length() - 4
        );

        return prefix + "_" + keyName;
    }
}
