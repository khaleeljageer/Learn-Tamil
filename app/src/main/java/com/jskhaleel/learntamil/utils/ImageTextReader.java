package com.jskhaleel.learntamil.utils;

import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;

/**
 * This class convert the image to text and return the text on image
 */
public class ImageTextReader {

    /**
     * TessBaseAPI instance
     */
    private static volatile TessBaseAPI api;
    //  private static volatile TesseractImageTextReader INSTANCE;

    /**
     * initialize and train the tesseract engine
     *
     * @param path     a path to training data
     * @param language language code i.e. selected by user
     * @return the instance of this class for later use
     */
    public static ImageTextReader geInstance(String path, String language) {
        api = new TessBaseAPI();
        api.init(path, language, TessBaseAPI.OEM_TESSERACT_ONLY);
//        api.setPageSegMode(TessBaseAPI.PageSegMode.PSM_AUTO_OSD);
        return new ImageTextReader();
    }

    /**
     * get the text from bitmap
     *
     * @param bitmap a image
     * @return text on image
     */
    public String getTextFromBitmap(Bitmap bitmap) {
        api.setImage(bitmap);
        String textOnImage;
        try {
            textOnImage = api.getUTF8Text();
        } catch (Exception e) {
            return "Scan Failed: WTF: Must be reported to developer!";
        }
        if (textOnImage.isEmpty()) {
            return "Scan Failed: Couldn't read the image\nProblem may be related to Tesseract or no Text on Image!";
        } else return textOnImage;
    }

    public void clearReader() {
        api.clear();
    }
}
