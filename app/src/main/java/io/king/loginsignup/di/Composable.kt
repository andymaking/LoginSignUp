package io.king.loginsignup.di

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import io.king.loginsignup.API.SignUpActivityViewModel
import io.king.loginsignup.API.UserBody
import io.king.loginsignup.R
import io.king.loginsignup.ui.theme.Purple500

@Composable
fun Forms(buyCard: ()-> Unit, viewModelOne: SignUpActivityViewModel, navController: NavController) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()


    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }

    var validateFirstName by rememberSaveable { mutableStateOf(true) }
    var validateLastName by rememberSaveable { mutableStateOf(true) }
    var validatePhoneNumber by rememberSaveable { mutableStateOf(true) }
    var validateEmail by rememberSaveable { mutableStateOf(true) }

    val validateFirstNameError = "Please input a valid name"
    val validateLastNameError = "Please input a surname"
    val validateEmailError = "E-mail format incorrect"
    val validatePhoneNumberError = "phone number format incorrect"

    fun validateData(fullName: String, status: String, email: String, gender: String): Boolean {

        validateFirstName = fullName.isNotBlank()
        validateLastName = status.isNotBlank()
        validateEmail = email.isNotBlank()
        validatePhoneNumber = gender.isNotBlank()


        return validateFirstName && validateLastName && validateEmail && validatePhoneNumber
    }

    fun register(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
    ){
        if (validateData(firstName,lastName,email,phoneNumber)){
            // registration Logic

            Log.d("Register Detail", "FirstName: $firstName, LastName: $lastName,  Email: $email, Phone Number: $phoneNumber")
            val users = UserBody(amount = 1000, first_name = firstName, last_name = lastName, email = email, phone_number = phoneNumber, reference = "0011929222")
            viewModelOne.signup(users)
            navController.navigate("/logIn")
        }else {
            Toast.makeText(context, "Please review fields", Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = if (isSystemInDarkTheme()) Color.Black else Color.White),
        contentAlignment = Alignment.Center)
    {

        Column(
            modifier = Modifier
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
                onValueChange = { firstName = it },
                painter = R.drawable.perm_identity,
                showError = !validateFirstName,
                label = "First Name",
                errorMessage = validateFirstNameError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            CustomOutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                painter = R.drawable.perm_identity,
                showError = !validateLastName,
                label = "Last Name",
                errorMessage = validateLastNameError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            CustomOutlinedTextField(
                value = email,
                onValueChange = { email = it },
                painter = R.drawable.alternate_email,
                showError = !validateEmail,
                label = "Email Address",
                errorMessage = validateEmailError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            CustomOutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                painter = R.drawable.ic_baseline_smartphone_24,
                showError = !validatePhoneNumber,
                label = "Phone Number",
                errorMessage = validatePhoneNumberError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )


            Button(
                onClick = {
                    register(
                        firstName = firstName,
                        lastName = lastName,
                        email = email,
                        phoneNumber = phoneNumber
                    )
                    Log.d("userdetail", phoneNumber)

                },
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Register", fontSize = 20.sp
                )
            }

            Row {

                val color = if (isSystemInDarkTheme()) Color.White else Color.Black
                Text(
                    "Registered Already?",
                    color = color,
                    style = MaterialTheme.typography.body2,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 25.dp, horizontal = 10.dp)
                )
                Text(
                    "Login Here!",
                    color = color,
                    style = MaterialTheme.typography.body2,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(vertical = 25.dp)
                        .clickable {
                            navController.navigate("/logIn")
                        }
                )

            }
            Button(
                onClick = {
                    buyCard()
                },
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Purple500,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Buy Card", fontSize = 20.sp
                )
            }

        }
    }
}
