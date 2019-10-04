package tcp;

import java.io.*;
import java.net.*;

class ServidorTCP_mod {
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
		while (true) {
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
				byte[] ip = new byte[] { (byte) 192, (byte) 168, (byte) 41, (byte) 201 };
				InetAddress direcc = InetAddress.getByAddress(ip);
				// Leemos datos de la peticion
				String entrada;
				String salida = "";
				entrada = dis.readUTF();
				// Calculamos resultado
				for(int i = entrada.length() - 1; i >= 0; i--){
			        salida = salida + entrada.charAt(i);
			    }
				// Escribimos el resultado
				dos.writeUTF(salida);
				// Cerramos los streams
				dis.close();
				dos.close();
				sckt.close();
				// Registramos en salida estandard
				System.out.println(
						"Cliente = " + direcc + ":" + puerto + "\tEntrada = " + entrada + "\tSalida = " + salida);

			} catch (Exception e) {
				System.err.println("Se ha producido la excepción : " + e);
			}
		}
	}
}

