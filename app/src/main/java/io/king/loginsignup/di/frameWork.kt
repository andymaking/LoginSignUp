package io.king.loginsignup.di

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import io.king.loginsignup.R
import io.king.loginsignup.ui.theme.Purple700

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    painter: Int,
    leadingIconDescription: String = "",
    isPasswordField: Boolean = false,
    isPasswordVisible: Boolean = false,
    onVisibilityChange: (Boolean) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    showError: Boolean = false,
    errorMessage: String = ""
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {onValueChange(it)},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = if (isSystemInDarkTheme()) Color.White else Color.Blue,
                focusedBorderColor = if (isSystemInDarkTheme()) Color.Blue else Purple700,
                unfocusedLabelColor = if (isSystemInDarkTheme()) Color.White else Color.Blue,
                focusedLabelColor = if (isSystemInDarkTheme()) Color.Blue else Purple700
            ),
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = label)},
            leadingIcon = {
                Icon(
                    painter = painterResource(id = painter),
                    contentDescription = leadingIconDescription,
                    tint = if (showError) MaterialTheme.colors.error else Color.Blue
                )
            },
            isError = showError,
            trailingIcon = {
                if (showError && isPasswordField) Icon(tint = if (isSystemInDarkTheme()) Color.White else Color.Blue,
                    painter = painterResource(id = R.drawable.error),
                    contentDescription ="show error icon"
                )
                if (isPasswordField){
                    IconButton(onClick = { onVisibilityChange(!isPasswordVisible) }) {
                        Icon(tint = if (isSystemInDarkTheme()) Color.Blue else Color.White,painter = painterResource(id = if (isPasswordVisible) R.drawable.visibility else R.drawable.visibility_off ) ,contentDescription = null)
                    }
                }
            },
            visualTransformation = when {
                isPasswordField && isPasswordVisible -> VisualTransformation.None
                isPasswordField -> PasswordVisualTransformation()
                else -> VisualTransformation.None
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = true
        )
        if (showError){
            Text(
                text = errorMessage,
                color = if (isSystemInDarkTheme()) Color.White else Color.Blue,
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .offset(y = (-8).dp)
                    .fillMaxWidth(.9f)
            )
        }
    }
}