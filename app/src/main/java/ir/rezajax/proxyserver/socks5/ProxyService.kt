package ir.rezajax.proxyserver.socks5

import android.app.Service
import android.content.Intent
import android.os.IBinder

class ProxyService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // راه‌اندازی TCP و UDP Server
        val tcpServer = TcpSocks5Server(1080)
        val udpServer = UdpServer(1080)

        tcpServer.start()
//        udpServer.start()

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
