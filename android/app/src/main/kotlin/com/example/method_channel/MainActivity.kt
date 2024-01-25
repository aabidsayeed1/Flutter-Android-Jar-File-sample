package com.example.method_channel
import android.util.Log
import androidx.annotation.NonNull
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    private val channel = "a-b-s.com/native-code-example"
    override fun configureFlutterEngine(@NonNull flutterEngine:FlutterEngine){
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger,channel).setMethodCallHandler{
            //this method is invoked in main thread
            call, result -> 
            if(call.method == "messageFromNativeCode"){
                var message = getMessage()
                if(message.isNotEmpty()){
                    val imageUrl = "https://jpeg.org/images/jpeg-home.jpg"
                    try{
                      val myImage = Picasso.with(this)
                                .load(imageUrl).fetch(object : Callback {
                                  override fun onSuccess() {
                                      Log.d("ImageSize","Downloaded image  success")
                                      result.success("Image Downloaded successfully -> $message")
                                  }
                                  override fun onError() {
                                      result.error("UNAVAILABLE","Imaged download failed",null)
                                  }
                              })
                    } catch (e:Exception){
                        Log.d("ImageSize","image size ${e.message} ")

                    }
                }else{
                    result.error("UNAVAILABLE","Message from kotlin code is empty",null)
                }
            }else{
                result.notImplemented()
            }
        }
    }
    private fun getMessage():String{
        return  "Message from Kotlin code"
    }

}
