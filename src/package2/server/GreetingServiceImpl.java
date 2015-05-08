package package2.server;

import package2.client.GreetingService;
import package2.shared.FieldVerifier;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
	
	public static String Clients[][]= new String[100][2];
	public static int i = 1;
	@Override
	public String[][] addRow(String name, String surname) {
		Clients[i][0] = name;
		Clients[i][1] = surname;
		i++;
		return Clients;
	
	}

	@Override
	public String[][] editRow(String name, String surname, int editThisRow) {
		Clients[editThisRow][0] = name;
		Clients[editThisRow][1] = surname;
		return Clients;
		
	}

	@Override
	public String[][] deleteRow(int deleteThisRow) {
		Clients[deleteThisRow][0] = "";
		Clients[deleteThisRow][1] = "";
		return Clients;
	}


}
