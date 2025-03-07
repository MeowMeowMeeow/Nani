package com.example.nani.screens.Analytics

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nani.R
import com.example.nani.ui.theme.NaNiTheme

@Composable
fun AnalyticsScreen(navController: NavHostController) {
    val months = listOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
    var selectedMonth by remember { mutableStateOf("February") }
    val scrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState)
    ){
        Column(modifier = Modifier.padding(16.dp)) {
            HeaderSection()
            Spacer(modifier = Modifier.height(16.dp))
            MonthSelection(selectedMonth, months) {
                selectedMonth = it
            }
            Spacer(modifier = Modifier.height(16.dp))
            AnalyticsTableSection(horizontalScrollState)
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
fun MonthSelection(selectedMonth: String, months: List<String>, onMonthSelected: (String) -> Unit) {
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
            DropdownMenuDemo(selectedMonth, months, onMonthSelected)
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

@Composable
fun AnalyticsTableSection(horizontalScrollState: ScrollState) {
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
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.horizontalScroll(horizontalScrollState)) {
                AnalyticsTable()
            }
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
        Text(text = "Download Report", color = Color.White)
    }
}

@Composable
fun DropdownMenuDemo(selectedMonth: String, months: List<String>, onMonthSelected: (String) -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Box {
        TextButton(onClick = {
            expanded = true
        }) {
            Text(text = selectedMonth)
            Icon(
                painter = painterResource(id = R.drawable.dropdown),
                contentDescription = "Dropdown",
                modifier = Modifier.size(15.dp)
                    .padding(start = 5.dp)

            )
        }
        DropdownMenu(expanded = expanded, onDismissRequest = {
            expanded = false
        }) {
            months.forEach { month ->
                DropdownMenuItem(text = {
                    Text(month)
                                        },
                    onClick = {
                    onMonthSelected(month)
                    expanded = false
                })
            }
        }
    }
}

@Composable
fun AnalyticsTable() {
    Column {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            TableHeaderCell("Date")
            Spacer(modifier = Modifier.width(46.dp))
            TableHeaderCell("Time In")
            Spacer(modifier = Modifier.width(5.dp))
            TableHeaderCell("Location")
            Spacer(modifier = Modifier.width(5.dp))
            TableHeaderCell("Time Out")
            Spacer(modifier = Modifier.width(10.dp))
            TableHeaderCell("Late\nMinutes")
            Spacer(modifier = Modifier.width(18.dp))
            TableHeaderCell("Undertime\nMinutes")
            TableHeaderCell("Total Late &\nUndertime Minutes")
            Spacer(modifier = Modifier.width(18.dp))
            TableHeaderCell("Total\nHours")
        }
        repeat(8) {
            Row(Modifier.fillMaxWidth()) {
                TableCell("Feb ${14 + it}, 2025")
                Spacer(modifier = Modifier.width(5.dp))
                TableCell("7:${30 + it} am")
                Spacer(modifier = Modifier.width(5.dp))
                TableCell("Davao City")
                Spacer(modifier = Modifier.width(5.dp))
                TableCell("5:${20 + it} pm")
                Spacer(modifier = Modifier.width(46.dp))
                TableCell("${15 + it * 5}")
                Spacer(modifier = Modifier.width(64.dp))
                TableCell("${20 + it * 10}")
                Spacer(modifier = Modifier.width(85.dp))
                TableCell("${35 + it * 15}")
                Spacer(modifier = Modifier.width(75.dp))
                TableCell("${if (it % 2 == 0) "1hr 10m" else "0"}")
            }
        }
    }
}

@Composable
fun TableHeaderCell(text: String) {
    Text(text = text, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(8.dp), color = MaterialTheme.colorScheme.onSurface , textAlign = TextAlign.Center)
}

@Composable
fun TableCell(text: String) {
    Text(text = text, fontSize = 12.sp, modifier = Modifier.padding(8.dp), color = MaterialTheme.colorScheme.onSurface, textAlign = TextAlign.Center)
}

@Composable
@Preview(name = "Light Theme", showBackground = true)
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun PreviewAnalytics() {
    NaNiTheme {
        AnalyticsScreen(navController = rememberNavController())
    }
}
