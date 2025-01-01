package ir.rezajax.proxyserver.socks5

import java.io.*
import java.net.ServerSocket
import java.net.Socket

class TcpSocks5Server(private val port: Int) : Thread() {
    override fun run() {
        try {
            val serverSocket = ServerSocket(port)
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
        val input = clientSocket.getInputStream()
        val output = clientSocket.getOutputStream()
        try {
            // Step 1: Read the SOCKS5 greeting
            val version = input.read()
            println("Received SOCKS version: $version")
            val methodCount = input.read()
            println("Number of authentication methods: $methodCount")
            val methods = ByteArray(methodCount)
            input.read(methods)
            println("Authentication methods: ${methods.joinToString(",")}")

            // Step 2: Respond with chosen method (No authentication in this case)
            println("Sending response: version 0x05, method 0x00 (No authentication)")
            output.write(byteArrayOf(0x05, 0x00)) // Version 5, No authentication

            // Step 3: Read the SOCKS5 request
            val request = ByteArray(4)
            input.read(request)
            val command = request[1].toInt()
            val addressType = request[3].toInt()
            println("Received SOCKS request: command $command, address type $addressType")

            // Handle IPv4 addresses
            if (addressType == 1) {
                val address = ByteArray(4)
                input.read(address)
                val portBytes = ByteArray(2)
                input.read(portBytes)
                val ip = address.joinToString(".") { it.toUByte().toString() }
                val port = ((portBytes[0].toInt() and 0xFF) shl 8) or (portBytes[1].toInt() and 0xFF)
                println("Client wants to connect to $ip:$port")

                // Respond with success
                println("Sending success response to client")
                output.write(byteArrayOf(0x05, 0x00, 0x00, 0x01) + address + portBytes)
            }
        } catch (e: Exception) {
            println("Error while handling client request: ${e.message}")
            e.printStackTrace()
        } finally {
            println("Closing client socket")
            clientSocket.close()
        }
    }

}


// old

/*
* package ir.rezajax.proxyserver.socks5
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
*/