package com.rideassistant.ocr

import android.content.Context
import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.rideassistant.models.RideData
import com.rideassistant.utils.Constants
import com.rideassistant.utils.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.tasks.await

/**
 * Engine OCR cu ML Kit Text Recognition
 */
@Singleton
class OCREngine @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.Builder().build())

    /**
     * Extrage text din imagine
     */
    suspend fun recognizeText(bitmap: Bitmap): String {
        return try {
            val inputImage = InputImage.fromBitmap(bitmap, 0)
            val result = recognizer.process(inputImage).await()
            result.text
        } catch (e: Exception) {
            Logger.e("OCREngine", "Error recognizing text", e)
            ""
        }
    }

    /**
     * Extrage datele de cursă din text
     */
    suspend fun extractRideData(
        text: String,
        platform: String
    ): RideData? {
        return try {
            val extractor = TextRecognitionHelper()
            extractor.extract(text, platform)
        } catch (e: Exception) {
            Logger.e("OCREngine", "Error extracting ride data", e)
            null
        }
    }
}
