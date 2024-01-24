package com.example.method_channel
import androidx.annotation.NonNull
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
                    result.success(message)
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
