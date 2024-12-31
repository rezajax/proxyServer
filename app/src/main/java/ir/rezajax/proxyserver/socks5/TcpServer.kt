package ir.rezajax.proxyserver.socks5
import java.io.*
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket

class TcpServer(private val port: Int) : Thread() {
    override fun run() {
        try {
            val serverSocket = ServerSocket(port, 50, InetAddress.getByName("0.0.0.0"))
            println("TCP SOCKS5 server started on port $port")

            while (true) {
                val clientSocket = serverSocket.accept()
                handleClient(clientSocket)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun handleClient(clientSocket: Socket) {
        val input = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
        val output = BufferedWriter(OutputStreamWriter(clientSocket.getOutputStream()))

        // در اینجا، شما می‌توانید پروتکل SOCKS5 را پیاده‌سازی کنید
        // برای شروع ارتباط و پذیرش درخواست‌ها از سمت کلاینت
        try {
            // نمونه‌ای از ارسال و دریافت داده
            val data = input.readLine()
            println("Received data: $data")
            output.write("Response: $data\n")
            output.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            clientSocket.close()
        }
    }
}
