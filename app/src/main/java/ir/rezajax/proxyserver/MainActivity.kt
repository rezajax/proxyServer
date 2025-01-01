package ir.rezajax.proxyserver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import ir.rezajax.proxyserver.socks5.ProxyService
import ir.rezajax.proxyserver.ui.theme.ProxyServerTheme
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.Socket
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // شروع سرویس به محض باز شدن برنامه
        val serviceIntent = Intent(this, ProxyService::class.java)
        startService(serviceIntent)


/*
        class BootBroadcastReceiver : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
                    val serviceIntent = Intent(context, ProxyService::class.java)
                    context.startService(serviceIntent)
                }
            }
        }
*/

        setContent {
            ProxyServerTheme {

                val networkStatus: MutableState<String> = remember { mutableStateOf("Checking network...") }

                
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = networkStatus.value,
                        modifier = Modifier.padding(innerPadding)
                    )

                    Button(
                        onClick = {
                            networkStatus.value = isInternetAvailable(this).toString()
                        }
                    ) {
                        Text( networkStatus.value)
                    }
                }
            }
        }
    }
}

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    return activeNetwork?.isConnected == true
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Network Status: $name",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProxyServerTheme {
        Greeting("Android")
    }
}