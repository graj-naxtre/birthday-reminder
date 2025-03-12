package com.example.birthdayboom.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "label",
    placeholder: String = "Enter your $label",
    errorText: String = "$label is required field.",
    maxLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    var isError by remember { mutableStateOf(false) }
    var wasFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            isError = it.isBlank()
        },
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        supportingText = {
            if (isError) {
                Text(text = errorText, color = Color.Red)
            }
        },
        isError = isError,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            unfocusedBorderColor = Color.DarkGray,
            focusedLabelColor = Color.Black,
            focusedBorderColor = Color.Black,
            focusedContainerColor = Color.White,
            cursorColor = Color.Black,
            errorCursorColor = Color.Black,
            errorLabelColor = Color.Red,
            errorContainerColor = Color.White,
            errorBorderColor = Color.Red
        ),
        minLines = maxLines,
        visualTransformation = visualTransformation,
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                when {
                    focusState.isFocused -> wasFocused = true
                    !focusState.isFocused -> {
                        if (wasFocused && value.isEmpty()) isError = true
                    }
                }
            },
        trailingIcon = trailingIcon
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "label",
    placeholder: String = "Enter your $label",
    errorText: String = "$label is required field.",
    maxLines: Int = 1,
) {
    var isError by remember { mutableStateOf(false) }
    var wasFocused by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    val colors =
        listOf(
            Color(0xFFFF595A),
            Color(0xFFFFC766),
            Color(0xFF35A07F),
            Color(0xFF35A07F),
            Color(0xFFFFC766),
            Color(0xFFFF595A)
        )

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    value = value,
                    innerTextField = innerTextField,
                    singleLine = true,
                    enabled = true,
                    placeholder = {
                        Text(text = placeholder, style = MaterialTheme.typography.labelMedium)
                    },
                    isError = isError,
                    supportingText = {
                        if (isError) {
                            Text(
                                text = errorText,
                                color = Color.Red,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    },
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    contentPadding = PaddingValues(
                        vertical = 20.dp,
                        horizontal = 16.dp
                    ),
                    container = {
                        CardWithAnimatedBorder(
                            modifier = Modifier.indicatorLine(
                                enabled = true,
                                isError = isError,
                                interactionSource = interactionSource,
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Color.White,
                                    errorContainerColor = Color.White,
                                    focusedTextColor = Color.White,
                                    cursorColor = Color.White,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    errorPlaceholderColor = Color.Red,
                                    focusedPlaceholderColor = Color.Black,
                                )
                            ),
                            borderColors = colors
                        )
                    },
                )
            }
        )
    }
}

@Composable
fun CardWithAnimatedBorder(
    modifier: Modifier = Modifier,
    borderColors: List<Color> = emptyList(),
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Container_Transition")
    val angle by
    infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec =
        infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Transition_angle"
    )

    val brush =
        if (borderColors.isNotEmpty()) Brush.sweepGradient(borderColors)
        else Brush.sweepGradient(listOf(Color.Gray, Color.White))

    Surface(modifier = modifier, shape = MaterialTheme.shapes.medium) {
        Surface(
            modifier =
            Modifier
                .clipToBounds()
                .fillMaxWidth()
                .padding(1.dp)
                .drawWithContent {
                    rotate(angle) {
                        drawCircle(
                            brush = brush,
                            radius = size.width,
                            blendMode = BlendMode.SrcIn,
                        )
                    }
                    drawContent()
                },
            shape = MaterialTheme.shapes.medium
        ) {
            Box(modifier = Modifier.padding(8.dp))
        }
    }
}