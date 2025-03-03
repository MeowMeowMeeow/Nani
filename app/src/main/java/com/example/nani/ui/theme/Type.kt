package com.example.nani.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.nani.R

val PoppinsRegular = FontFamily(Font(R.font.poppins_regular, FontWeight.Normal))
val PoppinsMedium = FontFamily(Font(R.font.poppins_medium, FontWeight.Medium))
val PoppinsSemiBold = FontFamily(Font(R.font.poppins_semibold, FontWeight.SemiBold))
val PoppinsExtraBold = FontFamily(Font(R.font.poppins_extrabold, FontWeight.ExtraBold))
val PoppinsThin = FontFamily(Font(R.font.poppins_thin, FontWeight.Thin))



val CustomTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = PoppinsExtraBold,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = PoppinsRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = PoppinsRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,

    ),
            titleSmall = TextStyle(
        fontFamily = PoppinsRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 1.sp,
    ),
    titleMedium =
    TextStyle(
        fontFamily = PoppinsSemiBold,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
),
    bodySmall = TextStyle(
        fontFamily = PoppinsRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = PoppinsRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    )
)
