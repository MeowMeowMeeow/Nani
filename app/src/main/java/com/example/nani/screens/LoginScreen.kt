package com.example.nani.screens

import android.content.res.Configuration
import androidx.compose.material3.ButtonDefaults

import android.graphics.drawable.Icon
import android.widget.Button
import android.window.SplashScreenView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.example.nani.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.sp
import com.example.nani.ui.theme.NaNiTheme


@Composable
fun LoginScreen(){
Surface(
    color = MaterialTheme.colorScheme.background,
    modifier = Modifier
){
    LoginGroup()
}
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginGroup(
    modifier: Modifier = Modifier,
    onUserEmail: (String) -> Unit = {},
    onUserPass: (String) -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(true) }
    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = MaterialTheme.colorScheme.background
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
                        color  = MaterialTheme.colorScheme.onPrimary //edit temporary
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.email_icon),
                        contentDescription = "Mail Icon",
                        modifier = Modifier
                            .size(30.dp)
                            .padding(4.dp) ,
                        tint = Color.Gray//edit
                    )
                },
                modifier = Modifier
                    .width(530.dp)
                    .height(50.dp)
                    .fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
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
                        color  = MaterialTheme.colorScheme.onPrimary //edit temporary
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.key_icon),
                        contentDescription = "Pass Icon",
                        modifier = Modifier
                            .size(30.dp)
                            .padding(4.dp) ,
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
                                passwordVisible = !passwordVisible // On or off pass
                            },
                        tint = Color.LightGray // Edit ? depende sa theme
                    )
                },
                modifier = Modifier
                    .width(530.dp)
                    .height(50.dp)
                    .fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
        Spacer(modifier = Modifier.padding(bottom = 6.dp))
        Row( modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End) {
            Text(
                text = stringResource(R.string.forgot_password),
                style = MaterialTheme.typography.titleSmall,

                color = Color(0xFF5F71CA),//edit
                modifier = modifier.padding(4.dp)
            )
        }
        Spacer(modifier = Modifier.padding(bottom = 34.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {  },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(
                text = stringResource(R.string.lblLogin),
                style =MaterialTheme.typography.titleMedium,
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
@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewLoginGroup() {
    NaNiTheme {
        LoginGroup(
            onUserEmail = {},
            onUserPass = {}
        )
    }
}