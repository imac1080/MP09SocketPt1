package tcp_juego;

//ClienteTCP.java
import java.io.*;
import java.net.*;
import java.util.Scanner;

class ClienteTCP_juego {
	public static void main(String args[]) {
		Scanner myObj = new Scanner(System.in);
		int numero = 0;
		boolean semaforo = true;
		boolean semaforoPrograma=true;
		String resultado = "";
		// Leemos el primer parámetro, donde debe ir la dirección
		// IP del servidor
		InetAddress direcc = null;
		byte[] ip = new byte[] { (byte) 192, (byte) 168, (byte) 41, (byte) 232 };
		try {
			direcc = InetAddress.getByAddress(ip);
		} catch (UnknownHostException uhe) {
			System.err.println("Host no encontrado : " + uhe);
			System.exit(-1);
		}

		// Puerto que hemos usado para el servidor
		int puerto = 12399;
		// Para cada uno de los argumentos...
		Socket sckt = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		while (semaforoPrograma) {
			try {
				semaforo = true;
				// si no es un int no continua
				while (semaforo) {
					System.out.println("JOC PEDRA PAPER TISORA\n1. PEDRA\n2. PAPER\n3. TISORA");
					if (myObj.hasNextInt()) {
						numero = myObj.nextInt();
						myObj.nextLine();
						if (numero > 0 && numero < 4) {
							semaforo = false;
						}
					} else {
						System.out.println("NO HAS POSAT UN NUMERO");
						myObj.nextLine();
					}
				}
				// Creamos el Socket
				sckt = new Socket(direcc, puerto);
				// Extraemos los streams de entrada y salida
				dis = new DataInputStream(sckt.getInputStream());
				dos = new DataOutputStream(sckt.getOutputStream());
				// Lo escribimos
				dos.writeInt(numero);
				if (numero == 1) {
					System.out.println("HAS ENVIAT PEDRA");
				} else if (numero == 2) {
					System.out.println("HAS ENVIAT PAPER");
				} else if (numero == 3) {
					System.out.println("HAS ENVIAT TISORA");
				}
				System.out.println("-------------");
				// Leemos el resultado final
				resultado = dis.readUTF();
				// Indicamos en pantalla
				System.out.println(resultado+"\n");
				semaforoPrograma=dis.readBoolean();
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