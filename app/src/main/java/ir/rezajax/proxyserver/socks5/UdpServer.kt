package ir.rezajax.proxyserver.socks5

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class UdpServer(private val port: Int) : Thread() {
    override fun run() {
        try {
            val socket = DatagramSocket(port, InetAddress.getByName("0.0.0.0"))
            println("UDP SOCKS5 server started on port $port")

            while (true) {
                val buffer = ByteArray(1024)
                val packet = DatagramPacket(buffer, buffer.size)
                socket.receive(packet)
                handlePacket(packet)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun handlePacket(packet: DatagramPacket) {
        val data = String(packet.data, 0, packet.length)
        println("Received UDP data: $data")
        // اینجا می‌توانید داده‌ها را برای کلاینت ارسال کنید
    }
}
