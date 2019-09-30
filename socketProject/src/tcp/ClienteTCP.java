package tcp;

//ClienteTCP.java
import java.io.*;
import java.net.*;

class ClienteTCP {
	public static void main(String args[]) {
		// Leemos el primer parámetro, donde debe ir la dirección
		// IP del servidor
		InetAddress direcc = null;
		byte[] ip = new byte[] { (byte) 192, (byte) 168, (byte)40,(byte) 245 };
		try {
			direcc = InetAddress.getByAddress(ip);
		} catch (UnknownHostException uhe) {
			System.err.println("Host no encontrado : " + uhe);
			System.exit(-1);
		}
		// Puerto que hemos usado para el servidor
		int puerto = 36988;
		// Para cada uno de los argumentos...
		for (int n = 1; n < 10; n++) {
			Socket sckt = null;
			DataInputStream dis = null;
			DataOutputStream dos = null;
			try {
				// Convertimos el texto en número
				int numero = 9;
				// Creamos el Socket
				sckt = new Socket(direcc, puerto);
				// Extraemos los streams de entrada y salida
				dis = new DataInputStream(sckt.getInputStream());
				dos = new DataOutputStream(sckt.getOutputStream());
				// Lo escribimos
				dos.writeInt(numero);
				// Leemos el resultado final
				long resultado = dis.readLong();
				// Indicamos en pantalla
				System.out.println("Solicitud = " + numero + "\tResultado = " + resultado);
				// y cerramos los streams y el socket
				dis.close();
				dos.close();
			} catch (Exception e) {
				System.err.println("Se ha producido la excepción : " + e);
			}
			try {
				if (sckt != null)
					sckt.close();
			} catch (IOException ioe) {
				System.err.println("Error al cerrar el socket : " + ioe);
			}
		}
	}
}