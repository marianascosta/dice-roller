package com.example.diceroller.businesscard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceroller.R

@Composable
fun BusinessCard() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LogoSection()
        Spacer(modifier = Modifier.height(16.dp))
        NameTitleSection()
        Spacer(modifier = Modifier.height(16.dp))
        ContactInfoSection()
    }
}

@Composable
fun LogoSection() {
    Image(
        painter = painterResource(R.drawable.android_logo),
        contentDescription = "Logo",
        modifier = Modifier
            .size(100.dp)
            .padding(8.dp)
    )
}

@Composable
fun NameTitleSection() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Mariana Costa",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Mestrado em Engenharia Informática",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun ContactInfoSection() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Email: fc58219@alunos.fc.ul.pt",
            fontSize = 14.sp,
            color = Color.Black
        )
        Text(
            text = "Phone: (+351) 912-345-678",
            fontSize = 14.sp,
        )
    }
}