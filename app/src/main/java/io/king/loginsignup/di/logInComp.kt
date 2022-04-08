package io.king.loginsignup.di

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.king.loginsignup.API.LogInActivityModel
import io.king.loginsignup.API.SignInBody
import io.king.loginsignup.R

@Composable
fun Format(viewModelTwo: LogInActivityModel, navController: NavController) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    var validateEmail by rememberSaveable { mutableStateOf(true) }
    var validatePassword by rememberSaveable { mutableStateOf(true) }

    val validateEmailError = "E-mail format incorrect"
    val validatePasswordError = "phone number format incorrect"

    fun validateData(email: String, password: String): Boolean {

        validateEmail = email.isNotBlank()
        validatePassword = password.isNotBlank()


        return validateEmail && validatePassword
    }

    fun login(
        email: String,
        password: String,
    ){
        if (validateData(email = email, password = password)){
            // registration Logic

            Log.d("Register Detail", "Email: $email, Password: $password")
            val regs = SignInBody(email = email, password = password)
            viewModelTwo.signIn(regs)
            navController.navigate("/welcome")
        }else {
            Toast.makeText(context, "Please review fields", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier.background(if (isSystemInDarkTheme()) Color.Black else Color.White)
            .fillMaxSize().padding(horizontal = 20.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Register",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(vertical = 20.dp),
            color = Color.Blue
        )

        CustomOutlinedTextField(
            value = email,
            onValueChange = {email = it},
            painter = R.drawable.alternate_email,
            showError = !validateEmail,
            label = "Full Name",
            errorMessage = validateEmailError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {focusManager.moveFocus(FocusDirection.Down)}
            )
        )

        CustomOutlinedTextField(
            value = password,
            onValueChange = {password = it},
            painter = R.drawable.password,
            showError = !validatePassword,
            label = "Marital Status",
            errorMessage = validatePasswordError,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {focusManager.moveFocus(FocusDirection.Down)}
            )
        )

        Button(
            onClick = {
                login(email = email, password = password)
                Log.d("password", password)

            },
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White)
        ) {
            Text(
                text = "Register", fontSize = 20.sp
            )
        }

        Row {
            Text(
                "New to this App?",
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                style = MaterialTheme.typography.body2,
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 25.dp, horizontal =10.dp )
            )
            Text(
                "Buy Card Here!",
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                style = MaterialTheme.typography.body2,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(vertical = 25.dp)
                    .clickable {
                        navController.navigate("/signUp")
                    }
            )

        }
    }
}
