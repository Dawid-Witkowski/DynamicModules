package com.dynamic.dynamicfeature

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import com.dynamic.dynamicmodules.ui.theme.DynamicModulesTheme
import com.google.android.play.core.splitcompat.SplitCompat


class FeatureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            DynamicModulesTheme {
                Text("Feature activity")
            }
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.installActivity(this)
    }

}