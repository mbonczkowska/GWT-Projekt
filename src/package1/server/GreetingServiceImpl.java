package package1.server;

import package1.client.GreetingService;
import package1.shared.Clients;
import package1.shared.FieldVerifier;

import com.google.gwt.user.client.ui.FlexTable;
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


	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}


	@Override
	public String addToServer(String name, String surname)
			throws IllegalArgumentException {
		if (!FieldVerifier.isValidName(name)) {

			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}
		return "Hello";
	}


	@Override
	public Clients addClient(Clients client) {
		//PMF.get().makePersistent(client);
		return client;
	}





}
