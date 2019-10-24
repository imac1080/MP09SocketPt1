package practica;

//ClienteTCP.java
import java.io.*;
import java.net.*;
import java.util.Scanner;

class AhorcadoClient2 {
	public static void main(String args[]) {
		Scanner myObj = new Scanner(System.in);
		int numero = 0;
		boolean semaforo = true;
		boolean semaforoPrograma = true;
		String resultado = "";
		// Leemos el primer parámetro, donde debe ir la dirección
		// IP del servidor
		InetAddress direcc = null;
		byte[] ip = new byte[] { (byte) 192, (byte) 168, (byte) 41, (byte) 62 };
		try {
			direcc = InetAddress.getByAddress(ip);
		} catch (UnknownHostException uhe) {
			System.err.println("Host no encontrado : " + uhe);
			System.exit(-1);
		}

		// Puerto que hemos usado para el servidor
		int puerto = 1235;
		// Para cada uno de los argumentos...
		Socket sckt = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		System.out.println("JOC DE L'AHORCADO--------");
		while (semaforoPrograma) {
			try {
				// Creamos el Socket
				sckt = new Socket(direcc, puerto);
				// Extraemos los streams de entrada y salida
				dis = new DataInputStream(sckt.getInputStream());
				dos = new DataOutputStream(sckt.getOutputStream());
				// agafa la lletra
				System.out.println("-Palabra-\n" + dis.readUTF());
				System.out.println("\nDigues una lletra:");
				String lletra = String.valueOf(myObj.nextLine().charAt(0));
				// Lo escribimos
				dos.writeUTF(lletra);
				// Leemos el resultado final
				resultado = dis.readUTF();
				// Indicamos en pantalla
				System.out.println();
				System.out.println(resultado + "\n");
				semaforoPrograma = dis.readBoolean();
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