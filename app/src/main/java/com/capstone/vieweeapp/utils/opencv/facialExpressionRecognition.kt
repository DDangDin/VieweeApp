package com.capstone.vieweeapp.utils.opencv

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.util.Log
import com.capstone.vieweeapp.R
import com.capstone.vieweeapp.presentation.viewmodel.InterviewViewModel
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.MatOfRect
import org.opencv.core.Point
import org.opencv.core.Rect
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import org.opencv.objdetect.CascadeClassifier
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.gpu.GpuDelegate
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class FacialExpressionRecognition internal constructor(
    assetManager: AssetManager, context: Context, modelPath: String, // define input size
    private val INPUT_SIZE: Int,
) {
    // define interpreter
    // Before this implement tensorflow to build.gradle file
    private val interpreter: Interpreter

    // define height and width of original frame
    private var height = 0
    private var width = 0

    // now define Gpudelegate
    // it is use to implement gpu in interpreter
    private var gpuDelegate: GpuDelegate? = null

    // now define cascadeClassifier for face detection
    private var cascadeClassifier: CascadeClassifier? = null

    // now call this in CameraActivity
    init {
        // set GPU for the interpreter
        val options: Interpreter.Options = Interpreter.Options()
        gpuDelegate = GpuDelegate()
        // add gpuDelegate to option
        options.addDelegate(gpuDelegate)
        // now set number of threads to options
        options.setNumThreads(4) // set this according to your phone
        // this will load model weight to interpreter
        interpreter = Interpreter(loadModelFile(assetManager, modelPath), options)
        // if model is load print
        Log.d("facial_Expression", "Model is loaded")

        // now we will load haarcascade classifier
        try {
            // define input stream to read classifier
            val `is` = context.resources.openRawResource(R.raw.haarcascade_frontalface_alt)
            // create a folder
            val cascadeDir = context.getDir("cascade", Context.MODE_PRIVATE)
            // now create a new file in that folder
            val mCascadeFile = File(cascadeDir, "haarcascade_frontalface_alt")
            // now define output stream to transfer data to file we created
            val os = FileOutputStream(mCascadeFile)
            // now create buffer to store byte
            val buffer = ByteArray(4096)
            var byteRead: Int
            // read byte in while loop
            // when it read -1 that means no data to read
            while (`is`.read(buffer).also { byteRead = it } != -1) {
                // writing on mCascade file
                os.write(buffer, 0, byteRead)
            }
            // close input and output stream
            `is`.close()
            os.close()
            cascadeClassifier = CascadeClassifier(mCascadeFile.absolutePath)
            // if cascade file is loaded print
            Log.d("facial_Expression", "Classifier is loaded")
            // check your code one more time
            // select device and run
            //I/MainActivity: OpenCv Is loaded
            //D/facial_Expression: Model is loaded
            //D/facial_Expression: Classifier is loaded
            // Next video we will predict face in frame.
            // cropped frame is then pass through interpreter which will return facial expression/emotion
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // Before watching this video please watch my previous video :
    //Facial Expression Or Emotion Recognition Android App Using TFLite (GPU) and OpenCV:Load Model Part 2
    // Let's start
    // Create a new function
    // input and output are in Mat format
    // call this in onCameraframe of CameraActivity
    fun recognizeImage(
        mat_image: Mat,
        interviewViewModel: InterviewViewModel,
        rotationDegrees: Int
    ): Mat {
        // before predicting
        // our image is not properly align
        // we have to rotate it by 90 degree for proper prediction
//        Core.flip(mat_image.t(), mat_image, 1) // rotate mat_image by 90 degree
        if (rotationDegrees == 90) {
            Core.flip(mat_image.t(), mat_image, 1) // rotate mat_image by 90 degree
        } else { // 270
            Core.flip(mat_image.t(), mat_image, 0)
        }

        // start with our process
        // convert mat_image to gray scale image
        val grayscaleImage = Mat()
        Imgproc.cvtColor(mat_image, grayscaleImage, Imgproc.COLOR_RGBA2GRAY)
        // set height and width
        height = grayscaleImage.height()
        width = grayscaleImage.width()

        // define minimum height of face in original image
        // below this size no face in original image will show
        val absoluteFaceSize = (height * 0.1).toInt()
        // now create MatofRect to store face
        val faces = MatOfRect()
        // check if cascadeClassifier is loaded or not
        if (cascadeClassifier != null) {
            // detect face in frame
            //                                  input         output
            cascadeClassifier!!.detectMultiScale(
                grayscaleImage, faces, 1.1, 2, 2,
                Size(absoluteFaceSize.toDouble(), absoluteFaceSize.toDouble()), Size()
            )
            // minimum size
        }

        // now convert it to array
        val faceArray = faces.toArray()
        // loop through each face
        for (i in faceArray.indices) {
            // if you want to draw rectangle around face
            //                input/output starting point ending point        color   R  G  B  alpha    thickness
            Imgproc.rectangle(
                mat_image,
                faceArray[i].tl(),
                faceArray[i].br(),
                Scalar(0.0, 255.0, 0.0, 255.0),
                2
            )

            // now crop face from original frame and grayscaleImage
            // starting x coordinate       starting y coordinate
            val roi = Rect(
                faceArray[i].tl().x.toInt(), faceArray[i].tl().y.toInt(),
                faceArray[i].br().x.toInt() - faceArray[i].tl().x.toInt(),
                faceArray[i].br().y.toInt() - faceArray[i].tl().y.toInt()
            )
            // it's very important check one more time
            val cropped_rgba = Mat(mat_image, roi) //
            // now convert cropped_rgba to bitmap
            var bitmap: Bitmap? = null
            bitmap = Bitmap.createBitmap(
                cropped_rgba.cols(),
                cropped_rgba.rows(),
                Bitmap.Config.ARGB_8888
            )
            Utils.matToBitmap(cropped_rgba, bitmap)
            // resize bitmap to (48,48)
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 48, 48, false)
            // now convert scaledBitmap to byteBuffer
            val byteBuffer = convertBitmapToByteBuffer(scaledBitmap)
            // now create an object to hold output
            val emotion = Array(1) {
                FloatArray(
                    1
                )
            }
            //now predict with bytebuffer as an input and emotion as an output
            interpreter.run(byteBuffer, emotion)
            // if emotion is recognize print value of it

            // define float value of emotion
            val emotion_v =
                java.lang.reflect.Array.get(java.lang.reflect.Array.get(emotion, 0), 0) as Float
            Log.d("facial_expression", "Output:  $emotion_v")
            // create a function that return text emotion
            val emotion_s = get_emotion_text(emotion_v)
            Log.d("emotion_text", "emotion: $emotion_s, rotationDegrees: $rotationDegrees")
            // now put text on original frame(mat_image)
            //             input/output    text: Angry (2.934234)
            Imgproc.putText(
                mat_image, "$emotion_s ($emotion_v)",
                Point(
                    (faceArray[i].tl().x.toInt() + 10).toDouble(),
                    (faceArray[i].tl().y.toInt() + 20).toDouble()
                ),
                1, 1.5, Scalar(0.0, 0.0, 255.0, 150.0), 2
            )
            //      use to scale text      color     R G  B  alpha    thickness

            // select device and run
            // Everything is working fine
            // Remember to try other model
            // If you want me to improve model comment below
            // This model had average accuracy it can be improve
            // Bye

            // send My TestViewModel
//            layoutTestViewModel.updateFaceRectangle(
//                cropped_rgba,
//                faceArray[i].tl(),
//                faceArray[i].br(),
//                "$emotion_s ($emotion_v)\""
//            )
            interviewViewModel.updateFacialExpression(emotion_s, emotion_v)
        }


        // after prediction
        // rotate mat_image -90 degree
//        Core.flip(mat_image.t(), mat_image, 0)
        if (rotationDegrees == 90) {
            Core.flip(mat_image.t(), mat_image, 0)
        } else { // 270
            Core.flip(mat_image.t(), mat_image, -1)
        }

        return mat_image
    }

    private fun get_emotion_text(emotion_v: Float): String {
        // create an empty string
        var `val` = ""
        // use if statement to determine val
        // You can change starting value and ending value to get better result
        // Like
        `val` = if ((emotion_v >= 0) and (emotion_v < 0.5)) {
            "Surprise"
        } else if ((emotion_v >= 0.5) and (emotion_v < 1.5)) {
            "Fear"
        } else if ((emotion_v >= 1.5) and (emotion_v < 2.5)) {
            "Angry"
        } else if ((emotion_v >= 2.5) and (emotion_v < 3.5)) {
            "Neutral"
        } else if ((emotion_v >= 3.5) and (emotion_v < 4.5)) {
            "Sad"
        } else if ((emotion_v >= 4.5) and (emotion_v < 5.5)) {
            "Disgust"
        } else {
            "Happy"
        }
        return `val`
    }

    private fun convertBitmapToByteBuffer(scaledBitmap: Bitmap): ByteBuffer {
        val byteBuffer: ByteBuffer
        val size_image = INPUT_SIZE //48
        byteBuffer = ByteBuffer.allocateDirect(4 * 1 * size_image * size_image * 3)
        // 4 is multiplied for float input
        // 3 is multiplied for rgb
        byteBuffer.order(ByteOrder.nativeOrder())
        val intValues = IntArray(size_image * size_image)
        scaledBitmap.getPixels(
            intValues,
            0,
            scaledBitmap.width,
            0,
            0,
            scaledBitmap.width,
            scaledBitmap.height
        )
        var pixel = 0
        for (i in 0 until size_image) {
            for (j in 0 until size_image) {
                val `val` = intValues[pixel++]
                // now put float value to bytebuffer
                // scale image to convert image from 0-255 to 0-1
                byteBuffer.putFloat((`val` shr 16 and 0xFF) / 255.0f)
                byteBuffer.putFloat((`val` shr 8 and 0xFF) / 255.0f)
                byteBuffer.putFloat((`val` and 0xFF) / 255.0f)
            }
        }
        return byteBuffer
        // check one more time it is important else you will get error
    }

    @Throws(IOException::class)
    private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
        // this will give description of file
        val assetFileDescriptor = assetManager.openFd(modelPath)
        // create a inputsteam to read file
        val inputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }
}