package com.example.birthdayboom.ui.screens.add_birthday.components

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.birthdayboom.R
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
fun FormTextField(
    modifier: Modifier = Modifier,
    placeholder: String,
    value: String,
    onChange: (String) -> Unit,
    readOnly: Boolean,
    supportingText: String = "Empty Field",
    supportingTextCondition: () -> Boolean = { false },
    minLines: Int = 1,
    onClick: () -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text
) {
    val containerColor = colorResource(id = R.color.light_grey)

    val interactionSource = remember {
        object : MutableInteractionSource {
            override val interactions = MutableSharedFlow<Interaction>(
                extraBufferCapacity = 16,
                onBufferOverflow = BufferOverflow.DROP_OLDEST,
            )

            override suspend fun emit(interaction: Interaction) {
                when (interaction) {
                    is PressInteraction.Press -> {
                        onClick()
                    }
                }

                interactions.emit(interaction)
            }

            override fun tryEmit(interaction: Interaction): Boolean {
                return interactions.tryEmit(interaction)
            }
        }
    }

    TextField(
        modifier = modifier,
        value = value,
        onValueChange = { onChange(it) },
        readOnly = readOnly,
        placeholder = { Text(text = placeholder) },
        shape = MaterialTheme.shapes.medium,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        supportingText = {
            if (supportingTextCondition()) Text(
                text = supportingText,
                color = MaterialTheme.colorScheme.error
            )
        },
        label = { Text(text = placeholder) },
        minLines = minLines,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        interactionSource = interactionSource
    )

}