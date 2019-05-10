package anko

import android.app.Activity
import android.os.Bundle
import android.text.*
import android.widget.*
import android.view.*
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
			id = R.id.LinearLayout1
			orientation = LinearLayout.HORIZONTAL
			editText {
				id = R.id.add_content
				//android:enabled = true //not support attribute
				gravity = Gravity.TOP
				inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
				//android:minLines = 8 //not support attribute
				maxLines = 10
				isVerticalScrollBarEnabled = true
				isHorizontalScrollBarEnabled = false
				backgroundResource = android.R.drawable.edit_text
			}.lparams(width = matchParent)
		}
	}
}
