package com.example.asyncawaitdemo2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Main).launch {
            Log.i("MyTag", "계산 시작")
            val stock1 = async(IO) { getStock1() }
            val stock2 = async(IO) { getStock2() }

            val total = stock1.await() + stock2.await()
            Toast.makeText(applicationContext,"[Main] 합계 : $total",Toast.LENGTH_SHORT).show()
        }

        // 위와 같은 기능
        CoroutineScope(IO).launch {
            Log.i("MyTag", "계산 시작")
            val stock1 = async { getStock1() }
            val stock2 = async { getStock2() }

            val total = stock1.await() + stock2.await()
            Log.i("MyTag","[IO] 합계 : $total")
        }
    }

    private suspend fun getStock1(): Int {
        delay(10000)
        Log.i("MyTag", "Stock1 returned")
        return 55000
    }

    private suspend fun getStock2(): Int {
        delay(8000)
        Log.i("MyTag", "Stock2 returned")
        return 35000
    }
}