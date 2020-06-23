package com.jskhaleel.learntamil.ui.main

import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.jskhaleel.learntamil.R
import com.jskhaleel.learntamil.utils.ImageTextReader
import com.jskhaleel.learntamil.utils.Utils
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    lateinit var imageReader: ImageTextReader
    private lateinit var viewModel: MainViewModel
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        val data = mContext.getExternalFilesDir("")
        imageReader = ImageTextReader.geInstance(data!!.absolutePath, "tam")

        btnClear.setOnClickListener {
            writingView.clearCanvas()
        }

        btnValidate.setOnClickListener {
            val bitmap = writingView.drawingCache
            /*val tmpPath = mContext.getExternalFilesDir("/img")!!.absolutePath
            val file =
                File(tmpPath + "/" + "." + UUID.randomUUID().toString() + ".png")
            try {
                file.createNewFile()
                val ostream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream)
                ostream.flush()
                ostream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }*/

            ConvertOCR(mContext, imageReader).execute(bitmap)
        }
    }

    class ConvertOCR(
        private val context: Context,
        private val imageReader: ImageTextReader
    ) : AsyncTask<Bitmap, Void, String>() {
        override fun doInBackground(vararg bitmaps: Bitmap): String {
            var bitmap: Bitmap = bitmaps[0]
            bitmap = Utils.convertToGrayscale(bitmap)
            bitmap = Bitmap.createScaledBitmap(
                bitmap,
                (bitmap.width * 1.5).toInt(),
                (bitmap.height * 1.5).toInt(),
                true
            )
            return imageReader.getTextFromBitmap(bitmap)
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Toast.makeText(context, "You Typed : $result", Toast.LENGTH_LONG).show()
            Log.d("Khaleel", "Result : $result")
        }
    }
}