package com.example.myapplication.ui.dashboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DeleteAccountAcivity extends AppCompatActivity {
    private FirebaseAuth lateinit;
    private EditText email,Pass;
    private Button delete,cancel;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_delete_account);
        user = FirebaseAuth.getInstance().getCurrentUser();
        email = (EditText) findViewById(R.id.email_edt_text);
        email.setText(user.getEmail());
        Pass = (EditText) findViewById(R.id.passwordUPN_edt_text);
        cancel = (Button) findViewById(R.id.back_btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        delete = (Button) findViewById(R.id.reset_pass_btn);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Pass.getText().toString().isEmpty()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(DeleteAccountAcivity.this)
                            .setTitle("تنبيه")
                            .setMessage("الرجاء إدخال الحقول المطلوبة")
                            .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //set what would happen when positive button is clicked
                                    Pass.setText("");
                                }
                            }).show();
                } else {
                    AuthCredential credential = EmailAuthProvider.getCredential(email.getText().toString(), Pass.getText().toString());
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (!task.isSuccessful()) {
                                            AlertDialog alertDialog = new AlertDialog.Builder(DeleteAccountAcivity.this)
                                                    .setTitle("تنبيه")
                                                    .setMessage("حدث خطأ ما! الرجاء المحاولة مرة أخرى")
                                                    .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            //set what would happen when positive button is clicked
                                                            Pass.setText("");
                                                        }
                                                    }).setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            finish();
                                                        }
                                                    }).show();
                                        } else {
                                            Log.e("delete success", "deleted Successfully");
                                            AlertDialog alertDialog = new AlertDialog.Builder(DeleteAccountAcivity.this)
                                                    .setTitle("تنبيه")
                                                    .setMessage("تم حذف حسابك بنجاح")
                                                    .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            //set what would happen when positive button is clicked
                                                            Intent intent = new Intent(DeleteAccountAcivity.this, LoginActivity.class);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            startActivity(intent);
                                                        }
                                                    }).show();
                                        }
                                    }
                                });
                            } else {
                                Log.e("auth failed", "try again");
                                AlertDialog alertDialog = new AlertDialog.Builder(DeleteAccountAcivity.this)
                                        .setTitle("تنبيه")
                                        .setMessage("فشل التوثيق، الرجاء إدخال بياناتك بشكل صحيح")
                                        .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //set what would happen when positive button is clicked
                                                Pass.setText("");
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
    }
}
