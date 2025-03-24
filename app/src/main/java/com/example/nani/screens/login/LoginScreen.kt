    package com.example.nani.screens.login

    import android.content.res.Configuration
    import android.widget.Toast
    import androidx.compose.material3.ButtonDefaults
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.size
    import androidx.compose.foundation.rememberScrollState
    import androidx.compose.foundation.verticalScroll
    import androidx.compose.material3.Button
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.unit.dp
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
    import androidx.navigation.NavController
    import androidx.navigation.compose.rememberNavController
    import com.example.nani.R
    import com.example.nani.JairosoftAppScreen
    import com.example.nani.ui.theme.NaNiTheme
    import com.example.nani.ui.theme.components.isNetworkAvailable


    @Composable
    fun LoginScreen(navController: NavController, viewModel: LoginViewModel) {
        val context = LocalContext.current
        val user by viewModel.details.collectAsState()

        LoginGroup(
            onUserEmail = {}, // Unused
            onUserPass = {},  // Unused
            onLogin = { email, password ->

                if (!isNetworkAvailable(context)) {
                    Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show()
                    return@LoginGroup
                }

                viewModel.login(
                    email,
                    password,
                    onSuccess = {
                        navController.navigate(JairosoftAppScreen.Dashboard.name) {
                            popUpTo(JairosoftAppScreen.Login.name) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onFailure = { error ->
                        Toast.makeText(context, error, Toast.LENGTH_LONG).show()

                    },
                    context = context
                )
            },
            onForgotPassword = {
                navController.navigate(JairosoftAppScreen.Forgot.name)
            }
        )
    }




    @Composable
    fun LoginGroup(
        modifier: Modifier = Modifier,
        onUserEmail: (String) -> Unit = {},
        onUserPass: (String) -> Unit = {},
        onForgotPassword: () -> Unit,
        onLogin:  (String, String) -> Unit // Pass email & password

    ) {
        var email by remember { mutableStateOf("") }
        var pass by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(true) }

        val backgroundColor = MaterialTheme.colorScheme.background
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
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
                    onClick = {
                        onLogin(email, pass)
                              },
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

//
//    @Composable
//    @Preview(name = "Light Theme", showBackground = true)
//    @Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
//    fun LoginPreview(){
//
//        NaNiTheme {
//            LoginScreen(
//                navController = rememberNavController()
//            )
//        }
//    }
