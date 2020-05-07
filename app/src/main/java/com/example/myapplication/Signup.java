package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myapplication.admin_portal.ui.requests.requests;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplication.admin_portal.ui.requests.requests;
import com.example.myapplication.shopowner.shopowner_info;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Signup extends AppCompatActivity implements  View.OnClickListener {
    public static String NAME,EMAIL, PASS, PHONE ,AGE;
    EditText Name;
    EditText password;
    EditText confirmPassword;
    EditText email;
    EditText PhoneNum;
    EditText age;
    Button register;
    //DatabaseRefernce R ;

    static String id;

    private ProgressDialog progressDialog ;
    private FirebaseAuth f1 = FirebaseAuth.getInstance();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference("User");
    private final DatabaseReference myRefStore = database.getReference("shipowners");
    private final DatabaseReference requests = database.getReference("requests");
    String isItShopowner;



    //private FirebaseFirestore db = FirebaseFirestore.get
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        Intent intent = getIntent();
        isItShopowner = intent.getStringExtra("ID");
        //Check is the button clicked store or customer
        if(isItShopowner==null){

        }else {
            Log.e("storeCr", isItShopowner);
        }
        progressDialog = new ProgressDialog(this);


        // Write a message to the database





        Name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        email = findViewById(R.id.emailregister);

        register = findViewById(R.id.register_button);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkDataEntered()){

                    id = f1.getUid();
                }}
        });




    }


   /* boolean isDigits = TextUtils.isDigitsOnly(edtDigits.getText().toString());

    public boolean isDigits(String number){
        if(!TextUtils.isEmpty(number)){
            return TextUtils.isDigitsOnly(number);
        }else{
            return false;
        }
    }*/

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }



    boolean checkDataEntered() {
        NAME  = Name.getText().toString().trim();
        EMAIL  = email.getText().toString().trim();
        PASS = password.getText().toString().trim();



        if (isEmpty((EditText)Name )&&(isEmpty(password))
                && (isEmpty(email))) {
            Name.setError("فضلًا ادخل إسم المستخدم");
            password.setError("فضلًا ادخل كلمة المرور");
            email.setError("فضلًا ادخل البريد الإلكتروني");
            return false;}

        if (isEmpty(Name)) {
            Name.setError( "فضلًا ادخل إسم المستخدم");
            return false;

        }



        if (isEmpty(password)) {
            password.setError("فضلًا ادخل كلمة المرور");
            return false;

        }

        if( password.getText().toString().length()<=6){
            password.setError("كلمة المرور يجب أن تحتوي على ٦ خانات على الأقل");
            return false;
        }



        if (isEmpty(email)) {
            email.setError("فضلًا ادخل البريد الإلكتروني");
            return false;

        }



        if (!isEmail(email)||(!email.getText().toString().substring(email.getText().toString().indexOf(".")+1).equals("com"))){
            email.setError("البريد الإلكتروني غير صحيح !");
            return false;
        }

        if (password.getText().toString().toLowerCase().trim().equals(password.getText().toString())) {
            password.setError("كلمة المرور ضعيفة! ");
            return false;
        } // lower and upper case

        int numDigits= getNumberDigits(password.getText().toString().trim());

        if (numDigits > 0 && numDigits == password.length()) {
            password.setError("كلمة المرور يجب ان تحتوي على رموز ارقام وحروف! ");
            return false;
        } //Check if it's only numbers

        int numCharachters= getSpecialDigits(password.getText().toString().trim());
        Log.e("special",String.valueOf(numCharachters));
        if (numCharachters == 0 || numCharachters == password.length()) {
            password.setError("كلمة المرور يجب ان تحتوي على رموز ارقام وحروف! ");
            return false;
        }//Check if there is no special character

        progressDialog.setMessage("فضلًا انتظر ...");
        progressDialog.show();

        f1.createUserWithEmailAndPassword(EMAIL,PASS)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            String uid = f1.getCurrentUser().getUid();
                            if(isItShopowner !=null && isItShopowner.equals("Store creation")){
                                shopowner_info info = new shopowner_info(Name.getText().toString(), email.getText().toString().toLowerCase(),false);
                                myRefStore.child(uid).setValue(info);
                                requests request = new requests(uid,email.getText().toString(),"New User");
                                requests.push().setValue(request);
                                AlertDialog alertDialog = new AlertDialog.Builder(Signup.this)
                                        .setTitle("تنبيه")
                                        .setMessage("عزيزي صاحب المتجر \n الرجاء التكرم بزيارتنا لتقديم المستندات المطلوبة ليتم تفعيل حسابك.")
                                        .setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //set what would happen when positive button is clicked
                                                Intent intent = new Intent(Signup.this,LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }).show();
                            } else {
                                User_info m = new User_info(Name.getText().toString(), email.getText().toString(), password.getText().toString());
                                myRef.child(uid).setValue(m);
                                Toast.makeText(Signup.this, "تمت عملية التسجيل بنجاح !", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Signup.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else {
                            Toast.makeText(Signup.this, "فشلت عملية التسجيل , حاول مرة أخرى !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // if(ag)
        return true;
    }//data checked

    @Override
    public void onClick(View view) {

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {

            onBackPressed();

            // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    public static int getNumberDigits(String inString){
        if (isEmpty(inString)) {
            return 0;
        }
        int numDigits= 0;
        int length= inString.length();
        for (int i = 0; i < length; i++) {
            if (Character.isDigit(inString.charAt(i))) {
                numDigits++;
            }
        }
        return numDigits;
    }

    public static int getSpecialDigits(String inString){
        int numDigits= 0;
        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");
        if (regex.matcher(inString).find()) {
            numDigits++;
            return numDigits;
        }
        return numDigits;
    }

    public static boolean isEmpty(String inString) {
        return inString == null || inString.length() == 0;
    }

}




