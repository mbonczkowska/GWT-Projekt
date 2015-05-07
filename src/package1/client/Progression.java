package package1.client;

import package1.shared.Clients;
import package1.shared.FieldVerifier;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LongBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


public class Progression implements EntryPoint {

	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";


	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	private final GreetingServiceAsync addingService = GWT
			.create(GreetingService.class);
	
	private VerticalPanel panel = new VerticalPanel();
	private Label title = new Label("Lista klientów");
	private VerticalPanel content = new VerticalPanel();
	private FlexTable table = new FlexTable();
	private VerticalPanel addPanel = new VerticalPanel(); 
	private Label addTitle = new Label("Nowy klient");
	private Label titleName = new Label("Imię");
	private LongBox name = new LongBox();
	private Label titleSurname = new Label("Nazwisko");
	private LongBox surname = new LongBox();
	private Button addClientButton = new Button("Dodaj klienta");
	private static Long id = (long) 1;

	final Button sendButton = new Button("Send");
	final TextBox nameField = new TextBox();
	final TextBox surnameField = new TextBox();			
	final Label errorLabel = new Label();
	
	public void onModuleLoad() {

        
		table.setText(0,0,"ID");
		table.setText(0, 1, "Imie");
		table.setText(0, 2, "Nazwisko");
		table.setText(0, 4, "Edytuj");
		table.setText(0, 5, "Usuń");

		
		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("nameFieldContainer").add(surnameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);
		
		
		

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		//final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		//closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);

		dialogBox.setWidget(dialogVPanel);

       

		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendNameToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendNameToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void sendNameToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String nameToServer = nameField.getText();
				String surnameToServer = surnameField.getText();
				if (!FieldVerifier.isValidName(nameToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				textToServerLabel.setText(nameToServer);
				serverResponseLabel.setText("");
			   addingService.addToServer(nameToServer, surnameToServer,
					  new AsyncCallback<String>(){

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							System.out.println(caught);
						}

						@Override
						public void onSuccess(String result) {
							// TODO Auto-generated method stub
							dialogBox.setText("Remote Procedure Call");
							serverResponseLabel
									.removeStyleName("serverResponseLabelError");
							serverResponseLabel.setHTML(result);
							dialogBox.center();
						}
			   });
			/*	greetingService.greetServer(nameToServer,
						 new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								System.out.println(caught);
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								
								
							}

							public void onSuccess(String result) {
								dialogBox.setText("Remote Procedure Call");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(result);
								dialogBox.center();
								
							}
						});*/
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);

	}

	protected void addClient(){
		Clients client = new Clients(this.name.getText(),this.surname.getText());
		
		addingService.addClient(client, new AsyncCallback<Clients>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Clients result) {
				// TODO Auto-generated method stub
				int row = table.getRowCount();
				table.setText(row,0,"ID");
				table.setText(row, 1, "Imie");
				table.setText(row, 2, "Nazwisko");
				
				
			}
		
		});
	}
	
	
}
