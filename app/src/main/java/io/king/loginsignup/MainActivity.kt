package io.king.loginsignup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.king.loginsignup.API.LogInActivityModel
import io.king.loginsignup.API.SignUpActivityViewModel
import io.king.loginsignup.di.Format
import io.king.loginsignup.di.Forms
import io.king.loginsignup.ui.theme.LoginSignUpTheme
import okhttp3.ResponseBody

class MainActivity : ComponentActivity() {

    private lateinit var viewModel : SignUpActivityViewModel

    private lateinit var logViewModel : LogInActivityModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        logViewModel = ViewModelProvider(this)[LogInActivityModel::class.java]
        logViewModel.getLogInUserObserver().observe(this, Observer<ResponseBody?> {
            if (it==null){
                Toast.makeText(this@MainActivity, "${logViewModel.getLogInUserObserver()}", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this@MainActivity, "Successful  ${logViewModel.getLogInUserObserver()}", Toast.LENGTH_LONG).show()

            }
        })

        viewModel = ViewModelProvider(this)[SignUpActivityViewModel::class.java]
        viewModel.getCreateNewUserObserver().observe(this, Observer<ResponseBody?>{

            if (it==null){
                Toast.makeText(this@MainActivity, "Failed to create ${viewModel.getCreateNewUserObserver()}", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this@MainActivity, "Successfully created User  ${viewModel.getCreateNewUserObserver()}", Toast.LENGTH_LONG).show()

            }
        })

        setContent {
            LoginSignUpTheme {

                val intent = Intent(this, BuyCardActivity::class.java)
                    Navigation(
                        viewModelOne = viewModel,
                        viewModelTwo = logViewModel,
                        buyCard = {startActivity(intent) }
                    )
            }
        }
    }
}

@Composable
fun Navigation(buyCard: ()-> Unit, viewModelOne: SignUpActivityViewModel, viewModelTwo: LogInActivityModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination ="/signUp" ){
        composable("/signUp"){
            Forms(buyCard = buyCard, viewModelOne = viewModelOne, navController = navController)
        }
        composable("/logIn"){
            Format(viewModelTwo = viewModelTwo, navController)
        }
        composable("/welcome"){
            Welcome()
        }
    }
}

@Composable
fun Welcome() {
    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.Gray)) {
        Text(
            text = "Welcome",
            fontSize = 35.sp,
            style = MaterialTheme.typography.h5,
            color = White
        )
    }
}
