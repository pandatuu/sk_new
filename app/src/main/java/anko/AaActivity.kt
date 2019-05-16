package anko

import android.app.Activity
import android.os.Bundle
import android.widget.*
import org.jetbrains.anko.*

import com.example.sk_android.R

/**
 * Generate with Plugin
 * @plugin Kotlin Anko Converter For Xml
 * @version 1.3.4
 */
class AaActivity : Activity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		linearLayout {
			orientation = LinearLayout.VERTICAL
			textView {
				//android:lineSpacingExtra = 20dp //not support attribute
				//android:letterSpacing = 1dp //not support attribute
				letterSpacing = dip(1).toFloat()
			}.lparams(width = matchParent, height = matchParent)
		}
	}
}
