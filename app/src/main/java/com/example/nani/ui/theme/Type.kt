package com.example.nani.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.nani.R

val Poppins = FontFamily(
    Font(R.font.poppins_regular,FontWeight.Normal ),
    Font(R.font.poppins_extrabold,FontWeight.ExtraBold ),
    Font(R.font.poppins_medium,FontWeight.Medium ),
    Font(R.font.poppins_semibold,FontWeight.Light ),
    Font(R.font.poppins_mediumitalic,FontWeight.W100 ),
)
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 1.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Thin,
        fontSize = 18.sp,
        lineHeight = 20.sp,
        letterSpacing = 1.4.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 1.sp
    )

)