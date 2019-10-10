package tcp_juego;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ThreadLocalRandom;

class ServidorTCP_juego {
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
			ss = new ServerSocket(12449);
		} catch (IOException ioe) {
			System.err.println("Error al abrir el socket de servidor : " + ioe);
			System.exit(-1);
		}

		// Bucle infinito
		int puntuacionCliente = 0;
		int puntuacionServidor = 0;
		boolean cerrar = true;
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
				byte[] ip = new byte[] { (byte) 192, (byte) 168, (byte) 40, (byte) 187 };
				InetAddress direcc = InetAddress.getByAddress(ip);
				// Leemos datos de la peticion
				int entrada;
				String salida;
				entrada = dis.readInt();
				// Calculamos resultado
				String resultado;
				int tirada = ThreadLocalRandom.current().nextInt(1, 3 + 1);
				String mano;

				if (tirada == 1) {
					mano = "PIEDRA";
				} else if (tirada == 2) {
					mano = "PAPEL";
				} else {
					mano = "TIJERA";
				}

				if (entrada == tirada) {
					resultado = "EMPATE";

					// 1.Piedra 2.Papel 3.Tijera
				} else if ((entrada == 1 && tirada == 3) || (entrada == 2 && tirada == 1)
						|| (entrada == 3 && tirada == 2)) {
					resultado = "RONDA GANADA";
					puntuacionCliente++;
				} else {
					resultado = "RONDA PERDIDA";
					puntuacionServidor++;
				}
				String tabla = " (" + Integer.toString(puntuacionCliente) + " - " + Integer.toString(puntuacionServidor)
						+ ")";
				if (puntuacionCliente == 3) {
					salida = "¡VICTORIA!¡HAS GANADO!" + tabla;
					cerrar = false;
				} else if (puntuacionServidor == 3) {
					salida = "Oooh... Has perdido" + tabla;
					cerrar = false;
				} else {
					salida = resultado + tabla + "\nEl rival ha sacado " + mano;
				}
				// Escribimos el resultado
				dos.writeUTF(salida);
				dos.writeBoolean(cerrar);
				// Cerramos los streams
				dis.close();
				dos.close();
				sckt.close();
				// Registramos en salida estandard
				System.out.println("Cliente = " + direcc + ":" + puerto + "\tEntrada = " + entrada
						+ "\tNumero Random: " + tirada + "\tSalida = " + salida);

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
