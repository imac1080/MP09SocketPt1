package TCP_calculadora;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorServer {

	public CalculatorServer() throws RemoteException {
		Calculator c = new CalculatorImpl();
		int port = 2222;

		try { // special exception handler for registry creation
			LocateRegistry.createRegistry(port);
			System.out.println("java RMI registry created.");
		} catch (RemoteException e) {
			// do nothing, error means registry already exists
			System.out.println("java RMI registry already exists.");
		}

		String hostname = "0.0.0.0";

		String bindLocation = "//" + hostname + ":" + port + "/CalculatorService";
		try {
			Naming.bind(bindLocation, c);
			System.out.println("Addition Server is ready at:" + bindLocation);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Addition Serverfailed: " + e);
		}
	}

	public static void main(String args[]) throws RemoteException {
		new CalculatorServer();
	}
}
