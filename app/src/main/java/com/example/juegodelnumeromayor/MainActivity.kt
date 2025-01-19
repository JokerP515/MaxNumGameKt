package com.example.juegodelnumeromayor

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
}

@Composable
fun GameScreen() {
    var randomNumber1 by rememberSaveable { mutableIntStateOf(0) }
    var randomNumber2 by rememberSaveable { mutableIntStateOf(0) }
    var resultText by remember { mutableStateOf("") }

    val colorScheme = MaterialTheme.colorScheme
    val context = LocalContext.current

    // Inicializar números aleatorios al inicio del juego
    LaunchedEffect(Unit) {
        generateAndSetNumbers { num1, num2 ->
            randomNumber1 = num1
            randomNumber2 = num2
        }
    }

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
                NumberButton(
                    number = randomNumber1,
                    onClick = {
                        resultText = checkAnswer(randomNumber1, randomNumber2, context)
                        generateAndSetNumbers { num1, num2 ->
                            randomNumber1 = num1
                            randomNumber2 = num2
                        }
                    },
                    colorScheme = colorScheme
                )
                Spacer(modifier = Modifier.width(16.dp))
                NumberButton(
                    number = randomNumber2,
                    onClick = {
                        resultText = checkAnswer(randomNumber2, randomNumber1, context)
                        generateAndSetNumbers { num1, num2 ->
                            randomNumber1 = num1
                            randomNumber2 = num2
                        }
                    },
                    colorScheme = colorScheme
                )
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

private fun generateAndSetNumbers(onGenerated: (Int, Int) -> Unit) {
    val (num1, num2) = generateDifferentNumbers()
    onGenerated(num1, num2)
}

private fun generateDifferentNumbers(): Pair<Int, Int> {
    val num1 = Random.nextInt(1, 10)
    var num2: Int
    do {
        num2 = Random.nextInt(1, 10)
    } while (num1 == num2)
    return num1 to num2
}

private fun checkAnswer(selected: Int, other: Int, context: Context): String {
    return if (selected > other) {
        context.getString(R.string.correcto)
    } else {
        context.getString(R.string.incorrecto)
    }
}

@Composable
fun NumberButton(
    number: Int,
    onClick: () -> Unit,
    colorScheme: ColorScheme
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorScheme.secondary,
            contentColor = colorScheme.onSecondary
        )
    ) {
        Text(text = number.toString())
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    GameScreen()
}