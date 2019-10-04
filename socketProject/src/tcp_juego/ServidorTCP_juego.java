package tcp_juego;

//ServidorTCP.java
import java.io.*;
import java.net.*;

class ServidorTCP_juego {
	public static void main(String args[]) {
		// Primero indicamos la direcci�n IP local
		try {
			System.out.println("LocalHost = " + InetAddress.getLocalHost().toString());
		} catch (UnknownHostException uhe) {
			System.err.println("No puedo saber la direcci�n IP local : " + uhe);
		}
		// Abrimos un "Socket de Servidor" TCP en el puerto 1234.
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(9972);
		} catch (IOException ioe) {
			System.err.println("Error al abrir el socket de servidor : " + ioe);
			System.exit(-1);
		}
		int entrada;
		long salida;
		// Bucle infinito
		while (true) {
			try {
				// Esperamos a que alguien se conecte a nuestro Socket
				Socket sckt = ss.accept();
				// Extraemos los Streams de entrada y de salida
				DataInputStream dis = new DataInputStream(sckt.getInputStream());
				DataOutputStream dos = new DataOutputStream(sckt.getOutputStream());
				// Podemos extraer informaci�n del socket
				// N� de puerto remoto
				int puerto = sckt.getPort();
				// Direcci�n de Internet remota
				byte[] ip = new byte[] { (byte) 192, (byte) 168, (byte) 41,(byte) 201 };
				InetAddress direcc = InetAddress.getByAddress(ip);
				// Leemos datos de la peticion
				entrada = dis.readInt();
				// Calculamos resultado
				salida = (long) entrada * (long) entrada;
				// Escribimos el resultado
				dos.writeLong(salida);
				// Cerramos los streams
				dis.close();
				dos.close();
				sckt.close();
				// Registramos en salida estandard
				System.out.println(
						"Cliente = " + direcc + ":" + puerto + "\tEntrada = " + entrada + "\tSalida = " + salida);
			} catch (Exception e) {
				System.err.println("Se ha producido la excepci�n : " + e);
			}
		}
	}
}