package com.example.zad8

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class SigninUpScreen : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editTextName: EditText
    private lateinit var editTextSurname: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin_up_screen)
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)
        editTextName = findViewById(R.id.editText)
        editTextSurname = findViewById(R.id.editText2)
        editTextEmail = findViewById(R.id.editText3)
        editTextPassword = findViewById(R.id.editText4)
        editTextConfirmPassword = findViewById(R.id.editText5)
    }
    fun next2page(view: View) {
        val name = editTextName.text.toString()
        val surname = editTextSurname.text.toString()
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()
        val confirmPassword = editTextConfirmPassword.text.toString()
        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }
        if (password != confirmPassword) {
            Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
            return
        }
        saveUserData(name, surname, email, password)
        val intent = Intent(this, MenuFilms::class.java)
        startActivity(intent)
    }

    private fun saveUserData(name: String, surname: String, email: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.putString("surname", surname)
        editor.putString("email", email)
        editor.putString("password", password)
        editor.putString("confirmPassword", password)
        editor.apply()
    }
    fun loadSavedUserData(view: View) {
        val name = sharedPreferences.getString("name", "")
        val surname = sharedPreferences.getString("surname", "")
        val email = sharedPreferences.getString("email", "")
        val password = sharedPreferences.getString("password", "")
        val confirmPassword = sharedPreferences.getString("confirmPassword", "")
        if (name.isNullOrEmpty() || surname.isNullOrEmpty() || email.isNullOrEmpty() || password.isNullOrEmpty()) {
            Toast.makeText(this, "Нет сохраненных данных", Toast.LENGTH_SHORT).show()
        } else {
            val userData = "Имя: $name\nФамилия: $surname\nE-mail: $email\nПароль: $password"
            Toast.makeText(this, userData, Toast.LENGTH_LONG).show()
            editTextName.setText(name)
            editTextSurname.setText(surname)
            editTextEmail.setText(email)
            editTextPassword.setText(password)
            editTextConfirmPassword.setText(confirmPassword)
        }
    }
}
