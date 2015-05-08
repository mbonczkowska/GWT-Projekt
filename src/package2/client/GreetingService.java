package package2.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	String[][] addRow(String name,String surname);
	String[][] editRow(String name,String surname, int editThisRow);
	String[][] deleteRow(int deleteThisRow);
	

}
