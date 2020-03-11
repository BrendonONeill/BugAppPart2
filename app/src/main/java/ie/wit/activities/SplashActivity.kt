package ie.wit.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import ie.wit.R
import kotlinx.android.synthetic.main.content_home.*


class SplashActivity : AppCompatActivity() {
    private val splash:Long=4000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            startActivity(Intent(this,Login::class.java))
            finish()
        }, splash)
    }
}