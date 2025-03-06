    package com.example.nani.screens.Login

    import android.content.res.Configuration
    import android.widget.Toast
    import androidx.compose.material3.ButtonDefaults
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.isSystemInDarkTheme
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.size
    import androidx.compose.foundation.layout.width
    import androidx.compose.material3.Button
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.unit.dp
    import androidx.compose.material3.ExperimentalMaterial3Api
    import androidx.compose.material3.Icon
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.OutlinedTextField
    import androidx.compose.material3.Surface
    import androidx.compose.material3.Text
    import androidx.compose.material3.TextFieldDefaults
    import androidx.compose.runtime.collectAsState
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.res.stringResource
    import androidx.compose.ui.text.input.PasswordVisualTransformation
    import androidx.compose.ui.text.input.VisualTransformation
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.lifecycle.viewmodel.compose.viewModel
    import com.example.nani.R
    import com.example.nani.data.UserDao
    import com.example.nani.repository.LoginViewModelFactory
    import com.example.nani.repository.UserRepository
    import com.example.nani.ui.theme.NaNiTheme


    @Composable
    fun LoginScreen() {
        val context = LocalContext.current
        val factory = LoginViewModelFactory(context)
        val viewModel: LoginViewModel = viewModel(factory = factory)
        val email by viewModel.email.collectAsState()
        val password by viewModel.password.collectAsState()
        val loginResult by viewModel.loginResult.collectAsState()
        val errorMessage by viewModel.errorMessage.collectAsState()

        LoginGroup(
            modifier = Modifier,
            onUserEmail = { viewModel.updateEmail(it) },
            onUserPass = { viewModel.updatePassword(it) },
            onLogin = { email, password -> viewModel.loginUser(email, password) },
            onForgotPassword = { },
            loginViewModel = viewModel
        )
    }




    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LoginGroup(
        modifier: Modifier = Modifier,
        onUserEmail: (String) -> Unit = {},
        onUserPass: (String) -> Unit = {},
        onForgotPassword: () -> Unit,
        onLogin: (String, String) -> Unit, // Accept email & password
        loginViewModel: LoginViewModel // Use ViewModel
    ) {
        var email by remember { mutableStateOf("") }
        var pass by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(true) }
        val isDarkTheme = isSystemInDarkTheme()
        val backgroundColor = MaterialTheme.colorScheme.background
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
                    .background(backgroundColor)
            ) {
                Image(
                    painter = painterResource(R.drawable.jairosoft),
                    contentDescription = "Jairosoft Image",
                    modifier = Modifier
                        .size(width = 85.dp, height = 75.dp) //refer gikan bubble add dimens.kt soon
                )
                Text(
                    text = stringResource(R.string.txtLogin),
                    style = MaterialTheme.typography.titleLarge, //from Type.kt refer in bubble.io specifics
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary, //Add sa theme later

                )
                Spacer(modifier = Modifier.padding(bottom = 29.dp))
                // Email
                Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(R.string.lblEmail),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(bottom = 6.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    OutlinedTextField(
                        value = email,
                        onValueChange = { newEmailtxt ->
                            email = newEmailtxt
                            onUserEmail(newEmailtxt)
                        },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.enter_email),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimary //edit temporary
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.email_icon),
                                contentDescription = "Mail Icon",
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(4.dp),
                                tint = Color.Gray//edit
                            )
                        },
                        modifier = Modifier
                            .width(530.dp)
                            .height(50.dp)
                            .fillMaxWidth(),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )

                    )
                }

                Spacer(modifier = Modifier.padding(bottom = 29.dp))
                // Password
                Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(R.string.lblPassword),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    OutlinedTextField(
                        value = pass,
                        onValueChange = { newPasstxt ->
                            pass = newPasstxt
                            onUserPass(newPasstxt)
                        },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.enter_pass),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.key_icon),
                                contentDescription = "Pass Icon",
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(4.dp),
                                tint = Color.Gray
                            )
                        },
                        trailingIcon = {
                            Icon(
                                painter = painterResource(
                                    if (passwordVisible) R.drawable.visible else R.drawable.hidden
                                ),
                                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(4.dp)
                                    .clickable {
                                        passwordVisible = !passwordVisible
                                    },
                                tint = Color.LightGray
                            )
                        },
                        modifier = Modifier
                            .width(530.dp)
                            .height(50.dp)
                            .fillMaxWidth(),
                        singleLine = true,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
                Spacer(modifier = Modifier.padding(bottom = 6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = stringResource(R.string.forgot_password),
                        style = MaterialTheme.typography.titleSmall,

                        color = Color(0xFF5F71CA),//edit
                        modifier = modifier
                            .padding(4.dp)
                            .clickable { onForgotPassword() }
                    )
                }
                Spacer(modifier = Modifier.padding(bottom = 34.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {onLogin(email, pass) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = stringResource(R.string.lblLogin),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.tertiary
                    )


                }
                Spacer(modifier = Modifier.padding(bottom = 43.dp))
                Text(
                    text = stringResource(R.string.copyright),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface

                )

            }
        }
    }
