package com.example.birthdayboom.ui.screens.birthday

/*
@Composable
fun BirthdayScreen(
    viewModel: BirthdayViewModel = hiltViewModel(),
    handleNavigationAction: (BirthdayNavigationEvent) -> Unit
) {
    val openNotifyMe = remember { mutableStateOf(false) }

    if (openNotifyMe.value) {
        NotifyMe(
            setShowDialog = { openNotifyMe.value = it },
            onSave = { date, hour, minute -> Log.d("notify time", "$date $hour $minute") })
    }

    var isRefreshing by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TodayBirthday(viewModel)
        LaunchedEffect(true) {
            viewModel.fetchAllBirthdays()
        }
        Row (modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp).zIndex(5f)){
            Text(text = "Upcoming Birthdays", fontWeight = FontWeight.ExtraBold)
        }
        PullToRefreshLazyColumn(
            items = viewModel.allBirthdays.value,
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true;
                viewModel.fetchAllBirthdays()
                isRefreshing = false
            },
            content = { item ->
                BirthdayCard(
                    item = item,
                    setShowDialog = { openNotifyMe.value = it },
                    onCardClick = {
                        handleNavigationAction(
                            BirthdayNavigationEvent.OnProfileClick(item.contactId!!)
                        )
                    })
            }
        )
    }
}

@Composable
fun MonthlyContainer(monthName:String, items: List<UIBirthdayData>){
    Column {
        Text(text = monthName)
        items.forEach { item ->
            BirthdayCard(item = item, setShowDialog = {}, onCardClick = {})
        }
    }
}
 */