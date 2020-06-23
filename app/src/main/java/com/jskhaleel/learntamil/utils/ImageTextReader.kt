package com.jskhaleel.learntamil.utils

import android.graphics.Bitmap
import com.googlecode.tesseract.android.TessBaseAPI

/**
 * This class convert the image to text and return the text on image
 */
class ImageTextReader {
    /**
     * get the text from bitmap
     *
     * @param bitmap a image
     * @return text on image
     */
    fun getTextFromBitmap(bitmap: Bitmap?): String {
        api!!.setImage(bitmap)
        val textOnImage: String = try {
            api!!.utF8Text
        } catch (e: Exception) {
            return "Scan Failed: WTF: Must be reported to developer!"
        }
        return if (textOnImage.isEmpty()) {
            "Scan Failed: Couldn't read the image\nProblem may be related to Tesseract or no Text on Image!"
        } else textOnImage
    }

    fun clearReader() {
        api!!.clear()
    }

    companion object {
        /**
         * TessBaseAPI instance
         */
        @Volatile
        private var api: TessBaseAPI? = null
        //  private static volatile TesseractImageTextReader INSTANCE;
        /**
         * initialize and train the tesseract engine
         *
         * @param path     a path to training data
         * @param language language code i.e. selected by user
         * @return the instance of this class for later use
         */
        fun geInstance(path: String?, language: String?): ImageTextReader {
            api = TessBaseAPI()
            api!!.init(path, language, TessBaseAPI.OEM_TESSERACT_ONLY)
            //        api.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO_OSD);
            return ImageTextReader()
        }
    }
}