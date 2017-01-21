package com.kotlang.minion

import com.auth0.jwt.JWT
import com.mashape.unirest.http.Unirest
import org.bytedeco.javacpp.tesseract
import java.io.FileOutputStream
import org.bytedeco.javacpp.lept.*
import org.bytedeco.javacpp.tesseract.*


/**
 * Created by sainageswar on 21/01/17.
 */
fun main(args: Array<String>) {
    val res = Unirest.get("https://f.flockusercontent2.com/17a79c7148501445224fa80f")
            .asBinary()
    val api = tesseract.TessBaseAPI()
    // Initialize tesseract-ocr with English, without specifying tessdata path
    if (api.Init(".", "ENG") != 0) {
        System.err.println("Could not initialize tesseract.");
        System.exit(1);
    }

    val out = FileOutputStream("tmpImg")
    var read: Int

    do {
        read = res.body.read()
        out.write(read)
    } while (read != -1)

    val image = pixRead("tmpImg")
    println(res)
}