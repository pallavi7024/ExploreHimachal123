package com.tourism.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.FirebaseAuth;
import com.tourism.myapplication.databinding.ActivityLoginBinding;
import com.tourism.myapplication.login.LogInHelper;
import com.tourism.myapplication.login.model.User;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "AAA";

    private GoogleSignInOptions gso;

    private GoogleSignInClient mGoogleSignInClient;

    private ActivityLoginBinding binding;

    private ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            Log.d("AAA", " Called");
            var task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
            try {
                Log.d(TAG, "signWithFirebase: " + task.getResult(ApiException.class));
                var account = task.getResult(ApiException.class);
                var idToken = account.getIdToken();
                signWithFirebase(idToken);
            } catch (com.google.android.gms.common.api.ApiException e) {
                Log.d("AAA", "" + e);
                Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
            }
        }
        if (result.getResultCode() == RESULT_CANCELED) {
            Log.d("AAA", "Canceled");
        }
    });

    private void signWithFirebase(String idToken) {
        Log.d(TAG, "signWithFirebase: called");
        var credential = com.google.firebase.auth.GoogleAuthProvider.getCredential(idToken, null);
        Log.d(TAG, "signWithFirebase: " + credential.getProvider());
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnSuccessListener(task -> {
            if (task.getUser() == null) return;
            var taskUser = task.getUser();
            var uid = taskUser.getUid();
            var email = taskUser.getEmail();
            var displayName = taskUser.getDisplayName();
            var photoUrl = (taskUser.getPhotoUrl() == null) ? "" : taskUser.getPhotoUrl();
            var user = new User(uid, email, displayName, photoUrl.toString());
            Log.d(TAG, "signWithFirebase: " + user);
            LogInHelper.signIn(user, pair -> {
                if (pair.second == null) {
                    var intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, pair.second.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }).addOnFailureListener(e -> {
            Log.d(TAG, "signWithFirebase: " + e);
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        setClient();
        binding.signInButton.setOnClickListener(v -> {
            var signInIntent = mGoogleSignInClient.getSignInIntent();
            resultLauncher.launch(new Intent(signInIntent));
        });
    }

    private void setClient() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestIdToken(getString(R.string.default_web_client_id)).build();
        mGoogleSignInClient = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(this, gso);
    }


}