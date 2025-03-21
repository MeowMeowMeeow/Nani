package com.example.nani.screens.analytics

import android.content.res.Configuration
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nani.R
import com.example.nani.data.UserLogs
import com.example.nani.ui.theme.NaNiTheme
import com.example.nani.ui.theme.components.tablePadding
import java.util.Locale

@Composable
fun AnalyticsScreen(navController: NavHostController, viewModel: AnalyticsViewModel) {

    //need mu add og viewmodel and better repository para ma actionan ang token for the authentication
    var selectedMonth by remember {
        mutableStateOf(SimpleDateFormat("MMMM", Locale.getDefault()).format(Calendar.getInstance().time))
    }
    val scrollState = rememberScrollState()
    val logs by viewModel.logs
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            HeaderSection()
            Spacer(modifier = Modifier.height(16.dp))
            MonthSelection(selectedMonth) {
                selectedMonth = it
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                //wala naga take in og data from api
                items(logs) { userLog ->
                    AnalyticsTableSection(userLog)
                    Spacer(modifier = Modifier.height(16.dp))
                    Log.e("responded", "Network error: ")
                }

                Log.e("no response", "hidden")
            }

            Spacer(modifier = Modifier.height(20.dp))
            DownloadReportButton()
        }
    }
}

@Composable
fun HeaderSection() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.jairosoft),
            contentDescription = "Logo",
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Jairosoft",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
        )
    }
    Spacer(modifier = Modifier.height(14.dp))
    Text(
        text = "Analytics",
        fontSize = 30.sp,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun MonthSelection(selectedMonth: String, onMonthSelected: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colorScheme.onSecondaryContainer,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.calendar),
                contentDescription = "Calendar Icon",
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
            )
            Spacer(modifier = Modifier.width(8.dp))
            DatePickerDemo(selectedMonth, onMonthSelected)
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.time),
                contentDescription = "Time Icon",
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDemo(selectedMonth: String, onMonthSelected: (String) -> Unit) {
    val context = LocalContext.current
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    Box {
        TextButton(onClick = { showDatePicker = true }) {
            Text(text = selectedMonth)
            Icon(
                painter = painterResource(id = R.drawable.dropdown),
                contentDescription = "Dropdown",
                modifier = Modifier.size(15.dp).padding(start = 5.dp)
            )
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                headlineContentColor = MaterialTheme.colorScheme.onSurface,
            ),
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val calendar = Calendar.getInstance().apply { timeInMillis = millis }
                            val day = calendar.get(Calendar.DAY_OF_MONTH)
                            val month = SimpleDateFormat("MMMM", Locale.getDefault()).format(calendar.time)
                            val year = calendar.get(Calendar.YEAR)

                            //mao ni ang stored data sample paras api later
                            val fullDate = "$day $month, $year"


                            onMonthSelected(month)
                        }
                        showDatePicker = false
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.Cyan
                    )
                ) {
                    Text("OK", color = MaterialTheme.colorScheme.secondary)
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surface ,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    headlineContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationContentColor = MaterialTheme.colorScheme.secondary,
                    weekdayContentColor = MaterialTheme.colorScheme.onSurface,
                    dayContentColor = MaterialTheme.colorScheme.onSurface,
                    selectedDayContentColor = MaterialTheme.colorScheme.primary,
                    selectedDayContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    todayContentColor = MaterialTheme.colorScheme.secondary,
                    disabledDayContentColor = MaterialTheme.colorScheme.background,
                    yearContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedYearContentColor = MaterialTheme.colorScheme.primary,
                    selectedYearContainerColor = MaterialTheme.colorScheme.tertiaryContainer,

                ),

            )
        }
    }
}

@Composable
fun AnalyticsTableSection(userLogs: UserLogs) {
    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                MaterialTheme.colorScheme.onSecondaryContainer,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .height(300.dp)



        ) {
            AnalyticsTable(
                userLogs = userLogs
            )
        }
    }
}

@Composable
fun DownloadReportButton() {
    Button(
        onClick = { /* TODO: Handle Download Report */ },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Text(text = "Download Report", color = Color.White )
    }
    Spacer(modifier = Modifier.height(50.dp))
}

@Composable
fun AnalyticsTable(userLogs: UserLogs) {
    Column {
        Row( verticalAlignment = Alignment.CenterVertically) {
            TableHeaderCell("Date")
            Spacer(modifier = Modifier.width(46.dp))
            TableHeaderCell("Time In")
            Spacer(modifier = Modifier.width(5.dp))
            TableHeaderCell("Location")
            Spacer(modifier = Modifier.width(5.dp))
            TableHeaderCell("Time Out")
            Spacer(modifier = Modifier.width(15.dp))
            TableHeaderCell("Late\nMinutes")
            Spacer(modifier = Modifier.width(18.dp))
            TableHeaderCell("Undertime\nMinutes")
            TableHeaderCell("Total Late &\nUndertime Minutes")
            Spacer(modifier = Modifier.width(18.dp))
            TableHeaderCell("Total\nHours")
        }

            Row(Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.height(5.dp))
                TableCell(userLogs.date)
                Spacer(modifier = Modifier.width(5.dp))
                TableCell(userLogs.time_in)
                Spacer(modifier = Modifier.width(5.dp))
                TableCell("Davao City")
                Spacer(modifier = Modifier.width(10.dp))
                TableCell(userLogs.time_out)
                Spacer(modifier = Modifier.width(46.dp))
                TableCell("--")
                Spacer(modifier = Modifier.width(64.dp))
                TableCell("--")
                Spacer(modifier = Modifier.width(95.dp))
                TableCell("--")
                Spacer(modifier = Modifier.width(70.dp))
                TableCell(userLogs.totalHours)

        }
    }
}

@Composable
fun TableHeaderCell(text: String) {
    Text(text = text, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(start = 8.dp ,top = 8.dp , bottom = 8.dp, end = tablePadding() ), color = MaterialTheme.colorScheme.onSurface , textAlign = TextAlign.Center)
}

@Composable
fun TableCell(text: String) {
    Text(text = text, fontSize = 12.sp, modifier = Modifier.padding(start = 8.dp ,top = 8.dp , bottom = 8.dp, end = tablePadding()), color = MaterialTheme.colorScheme.onSurface, textAlign = TextAlign.Center)
}



