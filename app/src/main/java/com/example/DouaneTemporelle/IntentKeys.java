package com.example.DouaneTemporelle;

public class IntentKeys {

    // ekher 4 ar9am mel carte d'etudiant
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