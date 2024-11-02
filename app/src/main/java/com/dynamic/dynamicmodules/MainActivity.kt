package com.dynamic.dynamicmodules

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.dynamic.dynamicmodules.ui.theme.DynamicModulesTheme
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DynamicModulesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Screen(paddingValues = innerPadding)
                }
            }
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this.applicationContext)
    }
}

@Composable
fun Screen(paddingValues: PaddingValues) {
    val context = LocalContext.current
    var moduleState by remember { mutableStateOf("") }
    val manager = SplitInstallManagerFactory.create(context)
    moduleState = if (manager.installedModules.contains("dynamicfeature")) {
        "installed already"
    } else {
        "module not installed"
    }

    Column(Modifier.padding(paddingValues)) {
        Button(onClick = {
            val request = SplitInstallRequest.newBuilder()
                .addModule("dynamicfeature")
                .build()
            manager.startInstall(request)
                .addOnSuccessListener {
                    moduleState = "installing, see notifications"
                }
                .addOnFailureListener {
                    moduleState = "something went wrong"
                }
        }) {
            Text(moduleState)
        }

        Button(
            onClick = {
                try {
                    val intent = Intent().setClassName(
                        "com.dynamic.dynamicmodules",
                        "com.dynamic.dynamicfeature.FeatureActivity"
                    )
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "something went wrong... are you sure you downloaded the module first?",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
            Text("try to open another activity")
        }
    }
}