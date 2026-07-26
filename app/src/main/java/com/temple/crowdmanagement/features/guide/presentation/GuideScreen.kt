package com.temple.crowdmanagement.features.guide.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.temple.crowdmanagement.features.guide.components.*
import com.temple.crowdmanagement.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuideScreen(
    viewModel: GuideViewModel = viewModel()
) {
    val guideData by viewModel.guideData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    

    LaunchedEffect(Unit) {
        viewModel.loadGuideData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Pilgrim Guide",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = SaffronPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* Navigate back */ }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = SaffronPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SpiritualDarkBg
                )
            )
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(
                        color = SaffronPrimary,
                        strokeWidth = 4.dp,
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Loading guide... 🙏",
                        color = TextSecondary,
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(SpiritualDarkBg),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Temple Info Card
                item {
                    TempleInfoCard(
                        templeName = guideData.templeName,
                        location = guideData.location,
                        description = guideData.description,
                        history = guideData.history,
                        architecture = guideData.architecture,
                        timings = guideData.timings
                    )
                }

                // Quick Facts
                item {
                    QuickInfoCard(quickFacts = guideData.quickFacts)
                }

                // Aarti Timings
                item {
                    AartiTimingsCard(aartiTimings = guideData.aartiTimings)
                }

                // FAQs
                item {
                    FAQSection(faqs = guideData.faqs)
                }

                // Contacts
                item {
                    ContactCard(contacts = guideData.contacts)
                }
            }
        }
    }
}