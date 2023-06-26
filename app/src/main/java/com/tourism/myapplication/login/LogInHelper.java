package com.tourism.myapplication.login;

import android.util.Log;
import android.util.Pair;

import com.google.firebase.firestore.FirebaseFirestore;
import com.tourism.myapplication.login.model.User;

import java.util.function.Consumer;


public abstract class LogInHelper {


    public static void signIn(User user, Consumer<Pair<User, Exception>> listener) {
        var collectionRef = FirebaseFirestore.getInstance().collection("users");
        collectionRef.document(user.getUid()).set(user).addOnSuccessListener(aVoid -> {
            listener.accept(new Pair<>(user, null));
        }).addOnFailureListener(e -> {
            Log.d("AAA", "signIn: " + e);
            listener.accept(new Pair<>(null, e));
        });
    }

    public static void logOut(Consumer<Void> listener) {
        var auth = com.google.firebase.auth.FirebaseAuth.getInstance();
        auth.signOut();
        listener.accept(null);
    }
}
