package com.example.oder_food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {

    TextInputEditText edt_Email, edt_Password;
    Button btn_Sign_up;
    TextView txt_Sign_in;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigup);

        // Ánh xạ các thành phần trong layout
        edt_Email = findViewById(R.id.email);
        edt_Password = findViewById(R.id.password);
        btn_Sign_up = findViewById(R.id.btn_Sing_up);
        txt_Sign_in = findViewById(R.id.txt_Sing_in);

        // Bắt sự kiện khi người dùng nhấn vào TextView "Đăng nhập"
        txt_Sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến activity Đăng nhập
                Intent intent = new Intent(Signup.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Bắt sự kiện khi người dùng nhấn vào Button "Đăng ký"
        btn_Sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy giá trị email và password từ EditText
                String email = String.valueOf(edt_Email.getText()).trim();
                String password = String.valueOf(edt_Password.getText()).trim();

                // Kiểm tra tính hợp lệ của email
                if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(Signup.this, "Vui lòng nhập địa chỉ email hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra tính hợp lệ của password
                if (TextUtils.isEmpty(password) || password.length() < 6) {
                    Toast.makeText(Signup.this, "Vui lòng nhập mật khẩu có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Đăng ký bằng Firebase Authentication
                firebaseAuth.createUserWithEmailAndPassword(email, password).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Nếu đăng ký thành công, chuyển đến activity Đăng nhập
                                    Toast.makeText(Signup.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Signup.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Nếu đăng ký thất bại, thông báo lỗi
                                    Toast.makeText(Signup.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
