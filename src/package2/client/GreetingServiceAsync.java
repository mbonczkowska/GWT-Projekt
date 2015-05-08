package package2.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void addRow(String name, String surname, AsyncCallback<String[][]> callback);

	void editRow(String name, String surname, int editThisRow,
			AsyncCallback<String[][]> callback);

	void deleteRow(int deleteThisRow, AsyncCallback<String[][]> callback);

	
}
