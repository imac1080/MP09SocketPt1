package practica;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ThreadLocalRandom;

class AhorcadoServer2 {
	public static void main(String args[]) {
		// Primero indicamos la dirección IP local
		try {
			System.out.println("LocalHost = " + InetAddress.getLocalHost().toString());
		} catch (UnknownHostException uhe) {
			System.err.println("No puedo saber la dirección IP local : " + uhe);
		}
		// Abrimos un "Socket de Servidor" TCP en el puerto 1234.
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(1237);
		} catch (IOException ioe) {
			System.err.println("Error al abrir el socket de servidor : " + ioe);
			System.exit(-1);
		}

		// Bucle infinito
		boolean cerrar = true;

		int vidas = 8;
		int aciertos = 0;
		String[] palabras = new String[] { "pizarra", "basura", "ordenador", "mesa", "servidor", "puerta" };
		String palabra = palabras[ThreadLocalRandom.current().nextInt(0, palabras.length)];
		String[] respuesta = palabra.split("");
		String[] adivina = new String[respuesta.length];
		for (int i = 0; i < respuesta.length; i++) {
			adivina[i] = "_ ";
		}
		String resultado = "";
		for (int i = 0; i < adivina.length; i++) {
			resultado = resultado + adivina[i];
		}
		System.out.println("- Juego listo -");
		while (cerrar) {
			try {
				// Esperamos a que alguien se conecte a nuestro Socket
				Socket sckt = ss.accept();
				// Extraemos los Streams de entrada y de salida
				DataInputStream dis = new DataInputStream(sckt.getInputStream());
				DataOutputStream dos = new DataOutputStream(sckt.getOutputStream());
				// Podemos extraer información del socket
				// Nº de puerto remoto
				int puerto = sckt.getPort();
				// Dirección de Internet remota
				byte[] ip = new byte[] { (byte) 192, (byte) 168, (byte) 41, (byte) 21 };
				InetAddress direcc = InetAddress.getByAddress(ip);

				dos.writeUTF(resultado);
				String letra = dis.readUTF();
				boolean acierto = false;
				for (int i = 0; i < respuesta.length; i++) {
					if (letra.equalsIgnoreCase(respuesta[i])) {
						adivina[i] = respuesta[i];
						aciertos++;
						acierto = true;
					}
				}
				if (!acierto) {
					vidas--;
				}
				String mensaje = "Vidas restantes: " + vidas;
				if (aciertos == respuesta.length) {
					mensaje = "VICORIA: " + palabra;
					cerrar = false;
				} else if (vidas <= 0) {
					mensaje = "Derrota...: " + palabra;
					cerrar = false;
				}
				resultado = "";
				for (int i = 0; i < adivina.length; i++) {
					resultado = resultado + adivina[i];
				}
				dos.writeUTF(mensaje);

				dos.writeBoolean(cerrar);
				// Cerramos los streams
				dis.close();
				dos.close();
				sckt.close();
				// Registramos en salida estandard
				System.out.println("Cliente = " + direcc + ":" + puerto + "\tEntrada = " + letra + "\tVidas: " + vidas
						+ "\tPalabra = " + palabra + "\tSalida: " + resultado);

			} catch (Exception e) {
				System.err.println("Se ha producido la excepción : " + e);
			}
		}
		try {
			ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
