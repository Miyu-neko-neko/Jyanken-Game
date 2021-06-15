@file:Suppress("DEPRECATION")

package com.example.jyankengame


import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule


class MainActivity : AppCompatActivity(), View.OnClickListener{

    lateinit var timer: Timer
    lateinit var handler: Handler

    lateinit var soundPool: SoundPool
    var soundAiko:Int = 0
    var soundPon:Int = 0
    var soundJyanken:Int = 0
    var soundKachi:Int = 0
    var soundzannen:Int = 0

    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        guuButton.setOnClickListener(this)
        chokiButton.setOnClickListener(this)
        paaButton.setOnClickListener(this)

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH).build()
        soundPool = SoundPool.Builder().setAudioAttributes(audioAttributes )
            .setMaxStreams(2).build()

        soundAiko =  soundPool.load(this, R.raw.aiko, 1)
        soundPon =  soundPool.load(this, R.raw.pon1, 1)
        soundJyanken =  soundPool.load(this, R.raw.jyanken1, 1)
        soundKachi =  soundPool.load(this, R.raw.kachi1, 1)
        soundzannen =  soundPool.load(this, R.raw.zannen1, 1)
    }

    //アプリが立ち上がった時に実行されるメソッド
    override fun onResume() {
        super.onResume()
        var f = 0

        //遅延処理クラスのインスタンス化
        handler = Handler()
        //タイマー処理
        timer = Timer()
        //タイマー処理の実行
        //2秒後に処理を開始、0.1秒ごとに処理を実行   重い処理を別スレッドでやる。
        timer.schedule(2000,100,{runOnUiThread{
            f++ //インクリメント（自分自身にfを足して代入していく）

            if(f==1){
                resultView.setImageResource(R.drawable.guu)
            }
            else if (f==2){
                resultView.setImageResource(R.drawable.choki)
            }
            else{
                resultView.setImageResource(R.drawable.paa)
            }
            //変数fの値が3以上になったら0に戻す（無限ループできる）
            if (f==3){
                f = 0
            }
        }})

    }



    override fun onClick(v: View?) {

//じゃんけんの結果が出るまでボタンを押せないようにする
    guuButton.isEnabled = false
    chokiButton.isEnabled = false
    paaButton.isEnabled = false

    val n = Random().nextInt(3)

        if (n==0) {
        resultView2.setImageResource(R.drawable.guu)
        }
        else if (n==1) {
        resultView2.setImageResource(R.drawable.choki)
        }
        else {
        resultView2.setImageResource(R.drawable.paa)
        }
        textView.text = getString(R.string.pon)
        soundPool.play(soundPon, 1.0f, 1.0f, 1, 0, 1F)



        //Buttonの背景画像を変更します
        when (v?.id) {
        R.id.guuButton
        -> guuButton.setBackgroundResource(R.drawable.guuneko)

        R.id.chokiButton
        -> chokiButton.setBackgroundResource(R.drawable.chokineko)

        R.id.paaButton
        -> paaButton.setBackgroundResource(R.drawable.paaneko)
        }

        resultView2.visibility = View.VISIBLE
        resultView.visibility = View.INVISIBLE
        //遅延処理
        handler.postDelayed(Runnable{
        when (v?.id) {
        R.id.guuButton
        -> {
        guuButton.setBackgroundResource(R.drawable.guu)
        if (n == 0) {
        textView.text = getString(R.string.draw)
            soundPool.play(soundAiko, 1.0f, 1.0f, 1, 0, 1F)
        } else if (n == 1) {
        textView.text = getString(R.string.win)
            soundPool.play(soundKachi, 1.0f, 1.0f, 1, 0, 1F)
        } else {
        textView.text = getString(R.string.loose)
            soundPool.play(soundzannen, 1.0f, 1.0f, 1, 0, 1F)
        }
        }

        R.id.chokiButton
        -> {
        chokiButton.setBackgroundResource(R.drawable.choki)
        if (n == 0) {
        textView.text = getString(R.string.loose)
            soundPool.play(soundzannen, 1.0f, 1.0f, 1, 0, 1F)
        } else if (n == 1) {
        textView.text = getString(R.string.draw)
            soundPool.play(soundAiko, 1.0f, 1.0f, 1, 0, 1F)
        } else {
        textView.text = getString(R.string.win)
            soundPool.play(soundKachi, 1.0f, 1.0f, 1, 0, 1F)
        }
        }

        R.id.paaButton
        -> {
        paaButton.setBackgroundResource(R.drawable.paa)
        if (n == 0) {
        textView.text = getString(R.string.win)
            soundPool.play(soundKachi, 1.0f, 1.0f, 1, 0, 1F)
        } else if (n == 1) {
        textView.text = getString(R.string.loose)
            soundPool.play(soundzannen, 1.0f, 1.0f, 1, 0, 1F)
        } else {
        textView.text = getString(R.string.draw)
            soundPool.play(soundAiko, 1.0f, 1.0f, 1, 0, 1F)
        }
        }

        }
        //1秒遅れてから↑の処理を実行
        }, 1000)


            handler.postDelayed(Runnable {
                when (v?.id) {
                    R.id.guuButton
                    -> {
                        guuButton.setBackgroundResource(R.drawable.guu)
                        if (n == 0)  {
                            textView.text = getString(R.string.aikodesho)
                        }
                        else{textView.text = getString(R.string.jannkenn)
                        }
                        }
                    R.id.chokiButton
                    -> {
                        chokiButton.setBackgroundResource(R.drawable.choki)
                        if (n == 1)  {
                            textView.text = getString(R.string.aikodesho)
                        }
                        else{textView.text = getString(R.string.jannkenn)
                        }
                    }
                    R.id.paaButton
                    -> {
                        paaButton.setBackgroundResource(R.drawable.paa)
                        if (n == 2)  {
                            textView.text = getString(R.string.aikodesho)
                        }
                        else{textView.text = getString(R.string.jannkenn)
                        }
                    }


                }

                guuButton.isEnabled = true
                chokiButton.isEnabled = true
                paaButton.isEnabled = true

                resultView2.visibility = View.INVISIBLE
                resultView.visibility = View.VISIBLE

            }, 2000)


        //見えなくするのを２秒遅らせる
        /*handler.postDelayed(Runnable{
        guuButton.isEnabled = true
        chokiButton.isEnabled = true
        paaButton.isEnabled = true

        textView.text = getString(R.string.jannkenn)
        resultView2.visibility = View.INVISIBLE
        resultView.visibility = View.VISIBLE
        }, 2000) */

    }

    //画面が閉じた時に実行されるメソッド
    override fun onPause() {
        super.onPause()
        timer.cancel()
    }

    override fun onDestroy() {
        soundPool.release()
        super.onDestroy()
    }
}


