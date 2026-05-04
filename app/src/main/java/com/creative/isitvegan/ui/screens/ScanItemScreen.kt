package com.creative.isitvegan.ui.screens

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.creative.isitvegan.ui.viewmodels.ScanItemViewModel
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

@ExperimentalGetImage
@Composable
fun ScanItemScreen(
    viewModel: ScanItemViewModel,
    onCloseClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scanner = remember { BarcodeScanning.getClient() }

    // Initialize CameraController
    val cameraController = remember {
        LifecycleCameraController(context).apply {
            // Enable Image Analysis for future barcode scanning logic
            setEnabledUseCases(CameraController.IMAGE_ANALYSIS)

            setImageAnalysisAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
                val mediaImage = imageProxy.image
                if (mediaImage != null) {
                    val image =
                        InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                    scanner.process(image)
                        .addOnSuccessListener { barcodes ->
                            if (barcodes.isNotEmpty()) {
                                val code = barcodes[0].rawValue ?: ""
                                viewModel.onBarcodeDetected(code)
                            }
                        }
                        .addOnCompleteListener {
                            imageProxy.close()
                        }
                } else imageProxy.close()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Camera Preview Layer
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                PreviewView(context).apply {
                    this.controller = cameraController
                    // Use COMPATIBLE mode (TextureView) to support alpha animations and transitions
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    cameraController.bindToLifecycle(lifecycleOwner)
                }
            },
            onRelease = {
                // Ensure camera is unbound immediately when the view is released
                cameraController.unbind()
            }
        )

        // The Viewfinder Overlay
        ViewfinderOverlay()

        // Close Button (Common for scan screens)
        IconButton(
            onClick = onCloseClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 48.dp, start = 16.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.3f))
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.White
            )
        }

        // Instructions Text positioned below the viewfinder
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Scan Barcode",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Place the barcode inside the frame to scan",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun ViewfinderOverlay() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // Define scanning frame dimensions (75% of screen width)
        val rectSize = width * 0.75f
        val left = (width - rectSize) / 2
        val top = (height - rectSize) / 2.5f // Offset slightly above center
        val right = left + rectSize
        val bottom = top + rectSize
        val cornerRadius = 24.dp.toPx()

        // 1. Semi-transparent background with a hole (cutout)
        val backgroundPath = Path().apply {
            addRect(Rect(0f, 0f, width, height))
            addRoundRect(
                RoundRect(
                    rect = Rect(left, top, right, bottom),
                    cornerRadius = CornerRadius(cornerRadius)
                )
            )
            fillType = PathFillType.EvenOdd
        }
        drawPath(backgroundPath, Color.Black.copy(alpha = 0.6f))

        // 2. Modern Accent Corners
        val cornerLength = 40.dp.toPx()
        val strokeWidth = 4.dp.toPx()
        val color = Color.White

        // Helper to draw each corner with a smooth arc
        fun drawCorner(startAngle: Float, sweep: Float, moveX: Float, moveY: Float, arcRect: Rect) {
            drawPath(
                path = Path().apply {
                    // Logic to draw L-shaped corner with a curve
                    // (Simplified for readability in this example)
                },
                color = color,
                style = Stroke(width = strokeWidth)
            )
        }

        // Manual drawing of the 4 corners for precision
        // Top Left
        drawPath(
            path = Path().apply {
                moveTo(left, top + cornerLength); lineTo(left, top + cornerRadius)
                arcTo(
                    Rect(left, top, left + cornerRadius * 2, top + cornerRadius * 2),
                    180f,
                    90f,
                    false
                )
                lineTo(left + cornerLength, top)
            },
            color = color, style = Stroke(strokeWidth)
        )
        // Top Right
        drawPath(
            path = Path().apply {
                moveTo(right - cornerLength, top); lineTo(right - cornerRadius, top)
                arcTo(
                    Rect(right - cornerRadius * 2, top, right, top + cornerRadius * 2),
                    270f,
                    90f,
                    false
                )
                lineTo(right, top + cornerLength)
            },
            color = color, style = Stroke(strokeWidth)
        )
        // Bottom Left
        drawPath(
            path = Path().apply {
                moveTo(left, bottom - cornerLength); lineTo(left, bottom - cornerRadius)
                arcTo(
                    Rect(left, bottom - cornerRadius * 2, left + cornerRadius * 2, bottom),
                    180f,
                    -90f,
                    false
                )
                lineTo(left + cornerLength, bottom)
            },
            color = color, style = Stroke(strokeWidth)
        )
        // Bottom Right
        drawPath(
            path = Path().apply {
                moveTo(right - cornerLength, bottom); lineTo(right - cornerRadius, bottom)
                arcTo(
                    Rect(right - cornerRadius * 2, bottom - cornerRadius * 2, right, bottom),
                    90f,
                    -90f,
                    false
                )
                lineTo(right, bottom - cornerLength)
            },
            color = color, style = Stroke(strokeWidth)
        )
    }
}