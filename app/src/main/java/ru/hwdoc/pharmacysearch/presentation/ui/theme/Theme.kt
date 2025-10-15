package ru.hwdoc.pharmacysearch.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Black300 ,        //цвет карточек
    primaryContainer = White300,//граница карточек
    onPrimary = White100,       //цвет текста на карточке
    background = Black200,      //основной цвет
    secondary = Black100,       //акцент, верх приложения
    surface = White300,         //цвет строки поиска
    onSurface = White100,       //цвет текста в строке поиска
    onSurfaceVariant = White200,//цвет рамки строки поиска
    outlineVariant = White300,  //цвет кнопок



)

private val LightColorScheme = lightColorScheme(
    primary = White100,         //цвет карточек
    primaryContainer = Gray100, //граница карточек
    onPrimary = Black400,       //цвет текста на карточке
    background = White100,      //основной цвет
    secondary = Black100,       //акцент, верх приложения
    surface = White300,         //цвет строки поиска
    onSurface = White100,       //цвет текста в строке поиска
    onSurfaceVariant = White200,//цвет рамки строки поиска
    outlineVariant = Green100,  //цвет кнопок

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun PharmacySearchTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content
    )
}