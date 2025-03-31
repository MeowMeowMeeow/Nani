    package com.example.nani.screens.analytics

    import android.icu.text.SimpleDateFormat
    import android.icu.util.Calendar
    import android.util.Log
    import android.widget.Toast
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
    import androidx.compose.ui.unit.Dp
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.navigation.NavHostController
    import com.example.nani.R
    import com.example.nani.data.UserLogs
    import com.example.nani.screens.login.LoginViewModel
    import com.example.nani.ui.theme.components.formatDate
    import com.example.nani.ui.theme.components.formatTime
    import com.example.nani.ui.theme.components.tablePadding
    import java.util.Date
    import java.util.Locale

    @Composable
    fun AnalyticsScreen(
        navController: NavHostController,
        viewModel: AnalyticsViewModel,
        loginViewModel: LoginViewModel,
    ) {
        var selectedStartDate by remember {
            mutableStateOf(Calendar.getInstance().apply {
                set(Calendar.DAY_OF_MONTH, 1)
            }.time)
        }

        var selectedEndDate by remember {
            mutableStateOf(Calendar.getInstance().time)
        }

        val logs by viewModel.logs
        Log.d("AnalyticsScreen", "Logs size: ${logs.size}")

        val isLoading by viewModel.isLoading
        val errorMessage by viewModel.errorMessage
        Log.d("AnalyticsScreen", "Error: $errorMessage")

        val user = loginViewModel.details.collectAsState().value
        val token = user?.token ?: ""

        LaunchedEffect(token) {
            if (token.isNotEmpty()) {
                viewModel.setToken(token)
                viewModel.fetchLogs(token)
            }
        }

        val scrollState = rememberScrollState()
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                HeaderSection()
                Spacer(modifier = Modifier.height(16.dp))

                DateRangeSelection(
                    selectedStartDate,
                    selectedEndDate,
                    onStartDateSelected = { selectedStartDate = it },
                    onEndDateSelected = { selectedEndDate = it }
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else if (!errorMessage.isNullOrEmpty()) {
                    Text(
                        text = errorMessage ?: "An error occurred",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else {
                    AnalyticsTableSection(logs, selectedStartDate, selectedEndDate)
                }

                Spacer(modifier = Modifier.height(20.dp))
                DownloadReportButton()
            }
        }
    }
    @Composable
    fun DateRangeSelection(
        selectedStartDate: Date,
        selectedEndDate: Date,
        onStartDateSelected: (Date) -> Unit,
        onEndDateSelected: (Date) -> Unit
    ) {
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
                Row(verticalAlignment = Alignment.CenterVertically){
                    Image(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "Calendar Icon",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    DatePickerButton("Start Date", selectedStartDate, onStartDateSelected)

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(id = R.drawable.time),
                        contentDescription = "Time Icon",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "Calendar Icon",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    DatePickerButton("End Date", selectedEndDate, onEndDateSelected)
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DatePickerButton(label: String, date: Date, onDateSelected: (Date) -> Unit) {
        var showDatePicker by remember { mutableStateOf(false) }
        val dateFormat = remember { SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) }

        Box {
            TextButton(onClick = { showDatePicker = true }) {
                Text("${label}: ${dateFormat.format(date)}")
                Icon(
                    painter = painterResource(id = R.drawable.dropdown),
                    contentDescription = "Dropdown",
                    modifier = Modifier.size(15.dp).padding(start = 5.dp)
                )
            }
        }

        if (showDatePicker) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = date.time
            )

            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                colors = DatePickerDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    headlineContentColor = MaterialTheme.colorScheme.onSurface,
                ),
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            onDateSelected(Date(millis))
                        }
                        showDatePicker = false
                    },  colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.Cyan
                    )
                    ) {
                        Text("OK", color = MaterialTheme.colorScheme.secondary)
                    }
                }
            ) {
                DatePicker( state = datePickerState,
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
                    ))
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
    fun AnalyticsTableSection(logs: List<UserLogs>, startDate: Date, endDate: Date) {
        val filteredLogs = logs.filter { log ->
            try {
                val millis = if (log.date!! < 1000000000000L) log.date * 1000 else log.date
                val logDate = Date(millis)

                logDate >= startDate && logDate <= endDate
            } catch (e: Exception) {
                Log.e("AnalyticsScreen", "Date parsing error: ${e.localizedMessage}")
                false
            }
        }

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
                    .horizontalScroll(horizontalScrollState)
            ) {
                AnalyticsTable(filteredLogs)
            }
        }
    }


    @Composable
    fun AnalyticsTable(logs: List<UserLogs>) {

        Column {

            // Table Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(16.dp))
                listOf(
                    "Date" to 80.dp,
                    "Time In" to 80.dp,
                    "Location" to 100.dp,
                    "Time Out" to 80.dp,
                    "Late\nMinutes" to 80.dp,
                    "Undertime\nMinutes" to 100.dp,
                    "Total Late &\nUndertime Minutes" to 140.dp,
                    "Total\nHours" to 80.dp
                ).forEach { (title, width) ->
                    TableHeaderCell(title, width)
                }
            }


            LazyColumn {
                if (logs.isEmpty()) {
                    // Placeholder row when logs are empty
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(16.dp))

                                TableCell("-", 80.dp)
                                TableCell("-", 80.dp)
                                TableCell("-", 100.dp)
                                TableCell("-", 80.dp)
                                 TableCell("-", 80.dp)
                                TableCell("-", 100.dp)
                                TableCell("-", 140.dp)
                                TableCell("-", 80.dp)

                        }
                    }
                } else {
                    items(logs) { userLogs ->

                        val formattedDate = formatDate(userLogs.date)
                        val formattedTimeIn = formatTime(userLogs.timeIn)
                        val formattedTimeOut = formatTime(userLogs.timeOut)
                        val totalHours = "${userLogs.totalHours ?: 0} hrs"
                        val location = "N/A"
                        val lateMinutes = 0
                        val undertimeMinutes = 0
                        val totalLateUndertime = lateMinutes + undertimeMinutes

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.width(16.dp))
                            listOf(
                                formattedDate to 80.dp,
                                formattedTimeIn to 80.dp,
                                location to 100.dp,
                                formattedTimeOut to 80.dp,
                                lateMinutes.toString() to 80.dp,
                                undertimeMinutes.toString() to 100.dp,
                                totalLateUndertime.toString() to 140.dp,
                                totalHours to 80.dp
                            ).forEach { (text, width) ->
                                TableCell(text, width)
                            }

                            Log.d(
                                "AnalyticsTable",
                                "Date: $formattedDate, Time In: $formattedTimeIn, Time Out: $formattedTimeOut, Location: $location"
                            )
                        }
                    }
                }
            }
        }

    }




    @Composable
    fun DownloadReportButton() {
        val context = LocalContext.current
        Button(
            onClick = { Toast.makeText(context, "Still working on this feature", Toast.LENGTH_SHORT).show()},
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            Text(text = "Download Report", color = Color.White )
        }
        Spacer(modifier = Modifier.height(50.dp))
    }



    @Composable
    fun TableHeaderCell(text: String, width: Dp) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .width(width)
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = tablePadding()),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
    }

    @Composable
    fun TableCell(text: String, width: Dp) {
        Text(
            text = text,
            fontSize = 12.sp,
            modifier = Modifier
                .width(width)
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = tablePadding()),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
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
