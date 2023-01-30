 package dev.swapnil.splashscreen

import android.animation.ObjectAnimator
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen



 class MainActivity : AppCompatActivity() {
     @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         // Set up an OnPreDrawListener to the root view.
         val content: View = findViewById(android.R.id.content)
         content.viewTreeObserver.addOnPreDrawListener(
             object : ViewTreeObserver.OnPreDrawListener {
                 override fun onPreDraw(): Boolean {
                     // Check if the initial data is ready.
                     return if (true) {
                         // The content is ready; start drawing.
                         content.viewTreeObserver.removeOnPreDrawListener(this)
                         true
                     } else {
                         // The content is not ready; suspend.
                         false
                     }
                 }
             }
         )

         splashScreen.setOnExitAnimationListener { splashScreenView ->
             // Create your custom animation.
             val slideUp = ObjectAnimator.ofFloat(
                 splashScreenView,
                 View.TRANSLATION_Y,
                 0f,
                 -splashScreenView.height.toFloat()
             )
             slideUp.interpolator = AnticipateInterpolator()
             slideUp.duration = 200L

             // Call SplashScreenView.remove at the end of your custom animation.
             slideUp.doOnEnd { splashScreenView.remove() }

             // Run your animation.
             slideUp.start()
         }

    }
}