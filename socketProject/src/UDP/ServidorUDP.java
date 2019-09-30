package UDP;

//servidorUDP.java
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ServidorUDP {
	public static void main(String args[]) {
		// Primero indicamos la dirección IP local
		try {
			System.out.println("LocalHost = " + InetAddress.getLocalHost().toString());
		} catch (UnknownHostException uhe) {
			System.err.println("No puedo saber la dirección IPlocal :" + uhe);
		}
		// Abrimos un Socket UDP en el puerto 1234.
		// A través de este Socket enviaremos datagramas del tipoDatagramPacket
		DatagramSocket ds = null;
		try {
			ds = new DatagramSocket(7850);
		} catch (SocketException se) {
			System.err.println("Se ha producido un error al abrir el socket : " + se);
			System.exit(-1);
		}
		// Bucle infinito
		while (true) {
			try {
				// Nos preparamos a recibir un número entero(32 bits = 4 bytes)
				byte bufferEntrada[] = new byte[4];
				// Creamos un "contenedor" de datagrama, cuyobuffer
				// será el array creado antes
				DatagramPacket dp = new DatagramPacket(bufferEntrada, 4);
				// Esperamos a recibir un paquete
				ds.receive(dp);
				// Podemos extraer información del paquete
				// Nº de puerto desde donde se envió
				int puerto = dp.getPort();
				// Dirección de Internet desde donde se envió
				byte[] ip = new byte[] { (byte) 192, (byte) 168, 41, (byte) 201 };
				InetAddress direcc = InetAddress.getByAddress(ip);
				// "Envolvemos" el buffer con un ByteArrayInputStream...
				ByteArrayInputStream bais = new ByteArrayInputStream(bufferEntrada);
				// ... que volvemos a "envolver" con un DataInputStream
				DataInputStream dis = new DataInputStream(bais);
				// Y leemos un número entero a partir del arrayde bytes
				int entrada = dis.readInt();
				long salida = (long) entrada * (long) entrada;
				// Creamos un ByteArrayOutputStream sobre elque podamos escribir
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				// Lo envolvemos con un DataOutputStream
				DataOutputStream dos = new DataOutputStream(baos);
				// Escribimos el resultado, que debe ocupar 8bytes
				dos.writeLong(salida);
				// Cerramos el buffer de escritura
				dos.close();
				// Generamos el paquete de vuelta, usando losdatos
				// del remitente del paquete original
				dp = new DatagramPacket(baos.toByteArray(), 8, direcc, puerto);
				// Enviamos
				ds.send(dp);
				// Registramos en salida estandard
				System.out.println(
						"Cliente = " + direcc + ":" + puerto + "\tEntrada = " + entrada + "\tSalida = " + salida);
			} catch (Exception e) {
				System.err.println("Se ha producido el error " + e);
			}
		}
	}
}
