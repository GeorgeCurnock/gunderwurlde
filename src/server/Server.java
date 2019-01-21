
// Usage:
//        java Server
//
// There is no provision for ending the server gracefully.  It will
// end if (and only if) something exceptional happens.

import java.net.*;
import java.io.*;

public class Server extends Thread {

	public  void run() {

		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(Port.number);
		} catch (IOException e) {
			Report.errorAndGiveUp("Couldn't listen on port " + Port.number);
		}

		try {
			// We loop for ever, as servers usually do.
			while (true) {
				// Listen to the socket, accepting connections from new clients:
				Socket socket = serverSocket.accept(); // Matches AAAAA in Client

				// This is so that we can use readLine():
				BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintStream toClient = new PrintStream(socket.getOutputStream());

				// We create and start a new thread to write to the client:
				(new ServerSender(clientTable, realClientName, clientName, toClient)).start();

				// We create and start a new thread to read from the client:
				(new ServerReceiver(realClientName, clientName, fromClient, clientTable)).start();

			}
		} catch (IOException e) {
			Report.error("IO error " + e.getMessage());

		}
	}
}
