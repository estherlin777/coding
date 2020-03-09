package edu.illinois.cs.cs125.spring2020.mp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

/**public class.*/
public class LaunchActivity extends AppCompatActivity {
    /**RC sign in code.*/
    private static final int RC_SIGN_IN = 123;

    /**overide.*/
    @Override
    /**oncreate.
     * @param savedInstanceState - describe
     */
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
            finish();
        } else {
            createSignInIntent();
        }
    }
    /**creates the sign in Intent.*/
    public void createSignInIntent() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    // [START auth_fui_result]
    @Override
    /**on Activity Result*/
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Intent main = new Intent(this, MainActivity.class);
                startActivity(main);
                finish();
            } else {
                Button login = findViewById(R.id.goLogin);
                login.setOnClickListener(unused -> {
                    createSignInIntent();
                });
                //int error = response.getError().getErrorCode();
            }
        }
    }
}
