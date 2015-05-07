package package1.client;

import package1.shared.Clients;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input,AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void addToServer(String name, String surname, AsyncCallback<String> callback);

	void addClient(Clients client, AsyncCallback<Clients> callback);

	
}
