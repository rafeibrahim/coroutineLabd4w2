package fi.metropolia.coroutinelabd4w2

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.net.URL
import java.io.InputStream
import java.net.HttpURLConnection

class MainActivity : AppCompatActivity() {
    private val imgUrl = URL("https://image.shutterstock.com/z/stock-photo-handsome-male-and-beautiful-female-mobile-application-designers-test-and-discuss-new-app-features-714655705.jpg")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

 /*       GlobalScope.launch(Dispatchers.Main) {
            val myImg =  getImg(imgUrl.toString())
            showRes(myImg)
        }*/

        lifecycleScope.launch(Dispatchers.Main){
            val myImg = async (Dispatchers.IO) {getImg(imgUrl)}
            showRes(myImg.await())
        }
    }

 /*   private suspend fun getImg(url: String) =
        withContext(Dispatchers.IO) {
            return@withContext BitmapFactory.decodeStream(URL(url).openStream())
        }*/




    private fun getImg(url: URL): Bitmap {
        val myConn = url.openConnection() as HttpURLConnection
        myConn.requestMethod = "GET"
        val istream: InputStream = myConn.inputStream
        val imgBitmap = BitmapFactory.decodeStream(istream)
        return imgBitmap
    }

    private fun showRes(serverImg: Bitmap){
       // serverImg.run {
            imageView.setImageBitmap(serverImg)
        }
    //}
}