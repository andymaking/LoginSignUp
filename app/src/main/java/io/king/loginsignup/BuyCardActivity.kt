package io.king.loginsignup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import com.flutterwave.raveandroid.RaveConstants
import com.flutterwave.raveandroid.RavePayActivity
import com.flutterwave.raveandroid.RavePayManager
import io.king.loginsignup.di.CustomOutlinedTextField
import io.king.loginsignup.ui.theme.LoginSignUpTheme
import java.util.*


class BuyCardActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginSignUpTheme {

                val context = LocalContext.current
                val focusManager = LocalFocusManager.current
                val scrollState = rememberScrollState()
                val flutterWaveKey = "FLWPUBK-d41352b5a38d1be3fb338375ebf73256-X"

                var firstName by rememberSaveable { mutableStateOf("") }
                var lastName by rememberSaveable { mutableStateOf("") }
                var email by rememberSaveable { mutableStateOf("") }
                var narration by rememberSaveable { mutableStateOf("") }

                var validateFirstName by rememberSaveable { mutableStateOf(true) }
                var validateLastName by rememberSaveable { mutableStateOf(true) }
                var validateEmail by rememberSaveable { mutableStateOf(true) }
                var validateNarration by rememberSaveable { mutableStateOf(true) }

                val validateFirstNameError = "please enter your first name"
                val validateLastNameError = "please enter your last name"
                val validateEmailError = "please enter your email address"
                val validateNarrationError = "please enter a narration"

                fun validateData(email: String, firstName: String, lastName: String,narration: String): Boolean {

                    validateFirstName = firstName.isNotBlank()
                    validateLastName = lastName.isNotBlank()
                    validateEmail = email.isNotBlank()
                    validateNarration = narration.isNotBlank()


                    return validateFirstName && validateLastName && validateEmail && validateNarration
                }

                fun login(
                    firstName: String,
                    lastName: String,
                    email: String,
                    narration: String
                ){
                    if (validateData(firstName = firstName, lastName = lastName, email = email, narration = narration)){

                        RavePayManager(this@BuyCardActivity).setAmount(1000.0)
                            .setCountry("NG")
                            .setCurrency("NGN")
                            .setEmail(email)
                            .setfName(firstName)
                            .setlName(lastName)
                            .setNarration(narration)
                            .setPublicKey(flutterWaveKey)
                            .setTxRef(Calendar.getInstance().timeInMillis.toString() + "")
                            .acceptAccountPayments(true)
                            .acceptCardPayments(true)
                            .onStagingEnv(true)
                            .allowSaveCardFeature(true)
                            .initialize()


                        // registration Logic

                        Log.d("Register Detail", "First Name: $firstName, Last Name: $lastName, Email: $email, Narration: $narration")
                    }else {
                        Toast.makeText(context, "Please review fields", Toast.LENGTH_LONG).show()
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
                        value = firstName,
                        onValueChange = {firstName = it},
                        painter = R.drawable.perm_identity,
                        showError = !validateFirstName,
                        label = "First Name",
                        errorMessage = validateFirstNameError,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {focusManager.moveFocus(FocusDirection.Down)}
                        )
                    )

                    CustomOutlinedTextField(
                        value = lastName,
                        onValueChange = {lastName = it},
                        painter = R.drawable.perm_identity,
                        showError = !validateLastName,
                        label = "Last Name",
                        errorMessage = validateLastNameError,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {focusManager.moveFocus(FocusDirection.Down)}
                        )
                    )

                    CustomOutlinedTextField(
                        value = email,
                        onValueChange = {email = it},
                        painter = R.drawable.alternate_email,
                        showError = !validateEmail,
                        label = "Email Address",
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
                        value = narration,
                        onValueChange = {narration = it},
                        painter = R.drawable.password,
                        showError = !validateNarration,
                        label = "Narration",
                        errorMessage = validateNarrationError,
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
                            login(firstName = firstName, lastName = lastName ,email = email, narration = narration)
                            Log.d("narration", narration)

                        },
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White)
                    ) {
                        Text(
                            text = "Buy Card", fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            val message = data.getStringExtra("response")
            when (resultCode) {
                RavePayActivity.RESULT_SUCCESS -> {
                    Toast.makeText(this, "SUCCESS $message", Toast.LENGTH_SHORT).show()
                }
                RavePayActivity.RESULT_ERROR -> {
                    Toast.makeText(this, "ERROR $message", Toast.LENGTH_SHORT).show()
                }
                RavePayActivity.RESULT_CANCELLED -> {
                    Toast.makeText(this, "CANCELLED $message", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}