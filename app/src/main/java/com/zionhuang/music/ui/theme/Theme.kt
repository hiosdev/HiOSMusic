package com.zionhuang.music.ui.theme

import android.graphics.Bitmap
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.palette.graphics.Palette
import com.google.material.color.dynamiccolor.DynamicScheme
import com.google.material.color.hct.Hct
import com.google.material.color.scheme.SchemeTonalSpot
import com.google.material.color.score.Score
import com.zionhuang.music.R

val DefaultThemeColor = Color(0xFF4285F4)
val outfitFontFamily = FontFamily(
    Font(R.font.outfit_regular, FontWeight.Normal)
)

@Composable
fun InnerTuneTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    pureBlack: Boolean = false,
    themeColor: Color = DefaultThemeColor,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val colorScheme = remember(darkTheme, pureBlack, themeColor) {
        if (themeColor == DefaultThemeColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (darkTheme) dynamicDarkColorScheme(context).pureBlack(pureBlack)
            else dynamicLightColorScheme(context)
        } else {
            SchemeTonalSpot(Hct.fromInt(themeColor.toArgb()), darkTheme, 0.0)
                .toColorScheme()
                .pureBlack(darkTheme && pureBlack)
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography.copy(
            // Apply Outfit Regular to all text styles
            bodyLarge = MaterialTheme.typography.bodyLarge.copy(fontFamily = outfitFontFamily),
            bodyMedium = MaterialTheme.typography.bodyMedium.copy(fontFamily = outfitFontFamily),
            bodySmall = MaterialTheme.typography.bodySmall.copy(fontFamily = outfitFontFamily),
            displayLarge = MaterialTheme.typography.displayLarge.copy(fontFamily = outfitFontFamily),
            displayMedium = MaterialTheme.typography.displayMedium.copy(fontFamily = outfitFontFamily),
            displaySmall = MaterialTheme.typography.displaySmall.copy(fontFamily = outfitFontFamily),
            headlineLarge = MaterialTheme.typography.headlineLarge.copy(fontFamily = outfitFontFamily),
            headlineMedium = MaterialTheme.typography.headlineMedium.copy(fontFamily = outfitFontFamily),
            headlineSmall = MaterialTheme.typography.headlineSmall.copy(fontFamily = outfitFontFamily),
            labelLarge = MaterialTheme.typography.labelLarge.copy(fontFamily = outfitFontFamily),
            labelMedium = MaterialTheme.typography.labelMedium.copy(fontFamily = outfitFontFamily),
            labelSmall = MaterialTheme.typography.labelSmall.copy(fontFamily = outfitFontFamily),
            titleLarge = MaterialTheme.typography.titleLarge.copy(fontFamily = outfitFontFamily),
            titleMedium = MaterialTheme.typography.titleMedium.copy(fontFamily = outfitFontFamily),
            titleSmall = MaterialTheme.typography.titleSmall.copy(fontFamily = outfitFontFamily),
        ),
        content = content
    )
}

fun Bitmap.extractThemeColor(): Color {
    val colorsToPopulation = Palette.from(this)
        .maximumColorCount(8)
        .generate()
        .swatches
        .associate { it.rgb to it.population }
    val rankedColors = Score.score(colorsToPopulation)
    return Color(rankedColors.first())
}

fun DynamicScheme.toColorScheme() = ColorScheme(
    primary = Color(primary),
    onPrimary = Color(onPrimary),
    primaryContainer = Color(primaryContainer),
    onPrimaryContainer = Color(onPrimaryContainer),
    inversePrimary = Color(inversePrimary),
    secondary = Color(secondary),
    onSecondary = Color(onSecondary),
    secondaryContainer = Color(secondaryContainer),
    onSecondaryContainer = Color(onSecondaryContainer),
    tertiary = Color(tertiary),
    onTertiary = Color(onTertiary),
    tertiaryContainer = Color(tertiaryContainer),
    onTertiaryContainer = Color(onTertiaryContainer),
    background = Color(background),
    onBackground = Color(onBackground),
    surface = Color(surface),
    onSurface = Color(onSurface),
    surfaceVariant = Color(surfaceVariant),
    onSurfaceVariant = Color(onSurfaceVariant),
    surfaceTint = Color(primary),
    inverseSurface = Color(inverseSurface),
    inverseOnSurface = Color(inverseOnSurface),
    error = Color(error),
    onError = Color(onError),
    errorContainer = Color(errorContainer),
    onErrorContainer = Color(onErrorContainer),
    outline = Color(outline),
    outlineVariant = Color(outlineVariant),
    scrim = Color(scrim),
    surfaceBright = Color(surfaceBright),
    surfaceDim = Color(surfaceDim),
    surfaceContainer = Color(surfaceContainer),
    surfaceContainerHigh = Color(surfaceContainerHigh),
    surfaceContainerHighest = Color(surfaceContainerHighest),
    surfaceContainerLow = Color(surfaceContainerLow),
    surfaceContainerLowest = Color(surfaceContainerLowest)
)

fun ColorScheme.pureBlack(apply: Boolean) =
    if (apply) copy(
        surface = Color.Black,
        background = Color.Black
    ) else this

val ColorSaver = object : Saver<Color, Int> {
    override fun restore(value: Int): Color = Color(value)
    override fun SaverScope.save(value: Color): Int = value.toArgb()
}
