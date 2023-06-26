package com.tourism.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.tourism.myapplication.databinding.ActivityNavigationdrawerBinding;
import com.tourism.myapplication.login.LogInHelper;

public class MainActivity extends AppCompatActivity {

    private ActivityNavigationdrawerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavigationdrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.include.ttoolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                binding.drawerLayout,
                binding.include.ttoolbar,
                R.string.OpenDrawer,
                R.string.CloseDrawer);
        binding.drawerLayout.addDrawerListener(toggle);
        binding.navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.optAbout: {
                    startActivity(new Intent(this, AboutActivity.class));
                }
                break;
                case R.id.optLogout: {
                    handleLogOut();
                }
                break;
            }
            binding.drawerLayout.closeDrawers();
            return true;
        });
        toggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFrag()).commit();
        setUserData();
    }

    private void handleLogOut() {
        var gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestIdToken(getString(R.string.default_web_client_id)).build();
        var mGoogleSignInClient = com.google.android.gms.auth.api.signin.GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                LogInHelper.logOut(pair -> {
                    var intent = new Intent(this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                });
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUserData() {
        var view = binding.navigationView.getHeaderView(0);
        var img = (ImageView) view.findViewById(R.id.imageViewUser);
        var name = (TextView) view.findViewById(R.id.textViewUserName);
        var email = (TextView) view.findViewById(R.id.textViewUserEmail);
        var user = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;
        Glide.with(this).load(user.getPhotoUrl()).into(img);
        name.setText(user.getDisplayName());
        email.setText(user.getEmail());

    }

}


