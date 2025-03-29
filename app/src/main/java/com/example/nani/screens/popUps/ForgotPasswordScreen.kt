package com.example.nani.screens.popUps

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nani.JairosoftAppScreen
import com.example.nani.R
import com.example.nani.ui.theme.NaNiTheme

//DAPAT POP UP pero gi wholescreen lang sa nako
@Composable
fun ForgotPasswordScreen(navController: NavHostController) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        ForgotPasswordGroup(
            onUserEmail = {},
            onBackClick = {navController.navigate(JairosoftAppScreen.Login.name)},
            modifier = Modifier,

        )
    }
}

@Composable
fun ForgotPasswordGroup(
    onUserEmail: (String) -> Unit = {},
    onBackClick: () -> Unit , modifier: Modifier,
    ) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier
    ) {
        Column {
            Box {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondary),
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
            Spacer(modifier = Modifier.padding(32.dp))
            Column(
                horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Center,
                modifier = Modifier

                    .padding(10.dp)
            )
            {
                Text(
                    text = stringResource(R.string.forgot_password),
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 11.dp)
                )
                Text(
                    text = stringResource(R.string.txtForgotPassword),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 12.dp)
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
                Spacer(modifier = Modifier.padding(bottom = 55.dp))


            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {   Toast.makeText(context, "Still working on this feature", Toast.LENGTH_SHORT).show()},
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),

                ) {
                Text(
                    text = stringResource(R.string.buttonSendEmail),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}


@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewForgotPasswordGroup() {
    NaNiTheme {
        ForgotPasswordGroup(
            onUserEmail = {},
            onBackClick = {},
            modifier = Modifier,
        )
    }
}