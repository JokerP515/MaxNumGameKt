package com.example.juegodelnumeromayor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.juegodelnumeromayor.ui.theme.JuegoDelNumeroMayorTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JuegoDelNumeroMayorTheme {
                GameScreen()
            }
        }
    }

    @Composable
    fun GameScreen() {
        var randomNumber1 by rememberSaveable { mutableIntStateOf(Random.nextInt(1, 10)) }
        var randomNumber2 by rememberSaveable { mutableIntStateOf(Random.nextInt(1, 10)) }
        var resultText by remember { mutableStateOf("") }

        val colorScheme = MaterialTheme.colorScheme

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.TextoExplicacionJuego),
                    style = MaterialTheme.typography.titleLarge,
                    color = colorScheme.tertiary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Row {
                    Button(
                        onClick = {
                            resultText = if (randomNumber1 > randomNumber2) getString(R.string.correcto) else getString(
                                R.string.incorrecto
                            )
                            randomNumber1 = Random.nextInt(1, 10)
                            do {
                                randomNumber2 = Random.nextInt(1, 10)
                            } while (randomNumber1 == randomNumber2)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorScheme.secondary,
                            contentColor = colorScheme.onSecondary
                        )
                    ) {
                        Text(text = randomNumber1.toString())
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = {
                            resultText = if (randomNumber2 > randomNumber1) getString(R.string.correcto) else getString(
                                R.string.incorrecto
                            )
                            randomNumber1 = Random.nextInt(1, 10)
                            do {
                                randomNumber2 = Random.nextInt(1, 10)
                            } while (randomNumber1 == randomNumber2)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorScheme.secondary,
                            contentColor = colorScheme.onSecondary
                        )
                    ) {
                        Text(text = randomNumber2.toString())
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = resultText,
                    style = MaterialTheme.typography.bodyLarge,
                    color = colorScheme.tertiary
                )
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun GameScreenPreview() {
        GameScreen()
    }
}
