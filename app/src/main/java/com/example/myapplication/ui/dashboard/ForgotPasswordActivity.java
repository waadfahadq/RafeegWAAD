package com.example.myapplication.ui.dashboard;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPasswordActivity extends AppCompatActivity {
    private  FirebaseAuth lateinit;
    private EditText email,oPass,nPass;
    private Button reset,cancel;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_activity);

         user = FirebaseAuth.getInstance().getCurrentUser();
        email = (EditText) findViewById(R.id.email_edt_text);
        email.setText(user.getEmail());
        oPass = (EditText) findViewById(R.id.passwordUP_edt_text);
        nPass = (EditText) findViewById(R.id.passwordUPN_edt_text);
        cancel = (Button) findViewById(R.id.back_btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        reset = (Button) findViewById(R.id.reset_pass_btn);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nPass.getText().toString().isEmpty() || oPass.getText().toString().isEmpty()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(ForgotPasswordActivity.this)
                            .setTitle("تنبيه")
                            .setMessage("الرجاء إدخال الحقول المطلوبة")
                            .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //set what would happen when positive button is clicked
                                    nPass.setText("");
                                    oPass.setText("");
                                }
                            }).show();
                } else {
                    AuthCredential credential = EmailAuthProvider.getCredential(email.getText().toString(), oPass.getText().toString());
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(nPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(getBaseContext(), "Something went wrong. Please try again later", Toast.LENGTH_LONG);
                                            Log.e("pass fail", "Something went wrong. Please try again later");
                                            AlertDialog alertDialog = new AlertDialog.Builder(ForgotPasswordActivity.this)
                                                    .setTitle("تنبيه")
                                                    .setMessage("الرجاء المحاولة مرة أخرى")
                                                    .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            //set what would happen when positive button is clicked
                                                            nPass.setText("");
                                                            oPass.setText("");
                                                        }
                                                    }).setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            finish();
                                                        }
                                                    }).show();
                                        } else {
                                            Log.e("pass success", "Password Successfully Modified");
                                            AlertDialog alertDialog = new AlertDialog.Builder(ForgotPasswordActivity.this)
                                                    .setTitle("تنبيه")
                                                    .setMessage("تم تغيير كلمة المرور بنجاح")
                                                    .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            //set what would happen when positive button is clicked
                                                            finish();
                                                        }
                                                    }).show();
                                        }
                                    }
                                });
                            } else {
                                Log.e("auth failed", "try again");
                                AlertDialog alertDialog = new AlertDialog.Builder(ForgotPasswordActivity.this)
                                        .setTitle("تنبيه")
                                        .setMessage("فشل التوثيق، الرجاء المحاولة مرة أخرى")
                                        .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //set what would happen when positive button is clicked
                                                nPass.setText("");
                                                oPass.setText("");
                                            }
                                        }).setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        }).show();
                            }
                        }
                    });
                }
            }
        });

/*        ---Update password----

    */

/*      --reset Password
        Log.e("test email",email.getText().toString());
                lateinit = FirebaseAuth.getInstance();
                lateinit.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getBaseContext(), "Reset email instructions sent to " + email.getText().toString(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getBaseContext(), email.getText().toString() + " does not exist", Toast.LENGTH_LONG).show();
                        }
                    }
                });
 */
    }
}
