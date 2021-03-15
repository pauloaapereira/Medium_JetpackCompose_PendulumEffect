/*
 * Copyright 2021 Paulo Pereira
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {

        Pendulum {
            Surface(
                modifier = Modifier.size(24.dp),
                shape = CircleShape,
                color = Color.Blue,
                content = {}
            )
        }
    }
}

@Composable
fun Pendulum(
    modifier: Modifier = Modifier,
    swingDuration: Int = 1500,
    startX: Float = .2f,
    endX: Float = .8f,
    topY: Float = .2f,
    bottomY: Float = .4f,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()

    BoxWithConstraints(
        modifier = modifier
    ) {

        val start = maxWidth * startX
        val end = maxWidth * endX
        val top = maxHeight * topY
        val bottom = maxHeight * bottomY

        val x by infiniteTransition.animateFloat(
            initialValue = start.value,
            targetValue = end.value,
            animationSpec = infiniteRepeatable(
                animation = tween(swingDuration, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        val y by infiniteTransition.animateFloat(
            initialValue = top.value,
            targetValue = bottom.value,
            animationSpec = infiniteRepeatable(
                animation = tween(swingDuration / 2, easing = LinearOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        Box(
            modifier = Modifier.offset(x = x.dp, y = y.dp)
        ) {
            content()
        }
    }
}
