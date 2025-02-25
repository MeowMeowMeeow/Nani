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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.nani.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TriStateCheckbox
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
fun SignUpScreen(){
SignUpScreenGroup(
    modifier = Modifier,
    onUserEmail = {  },
    onUserPass = {},
    onUserVPass = {}
)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreenGroup( modifier: Modifier = Modifier,
                       onUserEmail: (String) -> Unit = {},
                       onUserPass: (String) -> Unit = {},
                       onUserVPass: (String) -> Unit){
    var checked by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var Vpass by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(true) }
Surface(  color = MaterialTheme.colorScheme.background,
    modifier = Modifier) {
Column(
verticalArrangement = Arrangement.Top,
horizontalAlignment = Alignment.CenterHorizontally,
modifier = Modifier
    .padding(top = 50.dp, start = 10.dp, end = 10.dp)
    .fillMaxSize()
    .background(MaterialTheme.colorScheme.background)
) {
    Image(
        painter = painterResource(R.drawable.jairosoft),
        contentDescription = "Jairosoft Image",
        modifier = Modifier
            .size(width = 85.dp, height = 75.dp) //refer gikan bubble add dimens.kt soon
    )
    Text(
        text = stringResource(R.string.txtWelcome),
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.primary,
    )
    Spacer(modifier = Modifier.padding(bottom = 57.dp))

    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {

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

//password
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {

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

    Spacer(modifier = Modifier.padding(bottom = 30.dp))


    //VerifyPassowrd
    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
      OutlinedTextField(
            value = Vpass,
            onValueChange = { newVPasstxt ->
                Vpass = newVPasstxt
                onUserVPass(newVPasstxt)
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
                    contentDescription = "Verify Pass Icon",
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
    Spacer(modifier = Modifier.padding(bottom = 30.dp))
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
        Spacer(modifier = Modifier.padding(start = 20.dp))
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it },
            modifier = Modifier
                .clip(RoundedCornerShape(3.dp))
                .size(20.dp)


        )
        Text(
            text = "I agree to Jairosoft",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 6.dp)
        )

        Text(
            text = stringResource(R.string.terms_and_conditions),//add underline
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.clickable { TODO() }//add
                .padding(2.dp)
        )


    }
    Spacer(modifier = Modifier.padding(30.dp))
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = { },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Text(
            text = "Create an Acount",
            style =MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.tertiary
        )

    }
    Spacer(modifier = Modifier.padding(10.dp))
    Text(
        text = "Already have an account?",
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.secondary
    )
    Spacer(modifier = Modifier.padding(27.dp))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Divider(
            modifier = Modifier.weight(1f),
            color =MaterialTheme.colorScheme.primary,
            thickness = 1.dp
        )

        Text(
            text = "or",
            modifier = Modifier.padding(horizontal = 8.dp),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium
        )

        Divider(
            modifier = Modifier.weight(1f),
            color =MaterialTheme.colorScheme.primary,
            thickness = 1.dp
        )
    }
    Spacer(modifier = Modifier.padding(18.dp))

    OutlinedButton(
        modifier = Modifier,
        onClick = { },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.background,

        ),
                shape = RoundedCornerShape(3.dp),

    ) {
        Icon(
            painter = painterResource(R.drawable.plus),
            contentDescription = "google",
            tint = MaterialTheme.colorScheme.onPrimary ,
            modifier = Modifier.padding(end=5.dp)
        )
        Text(
            text = "Login with Google",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )

    }




}
}
    }


@Preview(name = "Light Theme")
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SignUpScreenPreview() {
    NaNiTheme {
        SignUpScreenGroup(
            modifier = Modifier,
            onUserEmail = {},
            onUserPass = {},
            onUserVPass = {}
        )

    }
}