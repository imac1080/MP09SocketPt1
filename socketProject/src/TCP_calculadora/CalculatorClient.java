package TCP_calculadora;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.NotBoundException;

public class CalculatorClient {

	public static void main(String[] args) {
		Calculator c = null;
		try {
//			Calculator c = (Calculator) Naming.lookup("//localhost:/CalculatorService");
			Registry registry = LocateRegistry.getRegistry("192.168.41.220", 2222);
			c = (Calculator) registry.lookup("CalculatorService");
			System.out.println("Resta: "+c.sub(4, 3));
			System.out.println("Suma: "+c.add(4, 5));
			System.out.println("Multiplicacio: "+c.mul(3, 6));
			System.out.println("Divisio: "+c.div(9, 3));
			System.out.println("Potencia: "+c.pow(6, 2));
		} catch (RemoteException re) {
			System.out.println();
			System.out.println("RemoteException");
			System.out.println(re);
		} catch (NotBoundException nbe) {
			System.out.println();
			System.out.println("NotBoundException");
			System.out.println(nbe);
		} catch (java.lang.ArithmeticException ae) {
			System.out.println();
			System.out.println("java.lang.ArithmeticException");
			System.out.println(ae);
		}
	}
}

