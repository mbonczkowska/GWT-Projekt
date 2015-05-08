package package2.client;


import package2.shared.FieldVerifier;

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

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CRUD implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	
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
    int counter = 1;
    int score = 0;
	
	
	public void onModuleLoad() {
		
		table.setText(0,0,"ID");
		table.setText(0, 1, "Imie");
		table.setText(0, 2, "Nazwisko");
		table.setText(0, 4, "Edytuj");
		table.setText(0, 5, "Usuń");
		

		addClientButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				addClient();
			}


		});
		
		RootPanel.get().add(panel);
		panel.add(title);
		RootPanel.get().add(content);
		content.add(table);
		RootPanel.get().add(addPanel);
		addPanel.add(addTitle);
		addPanel.add(titleName);
		addPanel.add(name);
		addPanel.add(titleSurname);
		addPanel.add(surname);
		addPanel.add(addClientButton);
		
		
		
		final Button sendButton = new Button("Send");
		final TextBox nameField = new TextBox();
		nameField.setText("GWT User");
		final Label errorLabel = new Label();

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				sendButton.setEnabled(true);
				sendButton.setFocus(true);
			}
		});

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
				String textToServer = nameField.getText();
				if (!FieldVerifier.isValidName(textToServer)) {
					errorLabel.setText("Please enter at least four characters");
					return;
				}

				// Then, we send the input to the server.
				sendButton.setEnabled(false);
				textToServerLabel.setText(textToServer);
				serverResponseLabel.setText("");
				greetingService.greetServer(textToServer,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(String result) {
								dialogBox.setText("Remote Procedure Call");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(result);
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});
			}
		}

		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		sendButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}
	
	protected void addClient(){
		final Button edit = new Button("edytuj");
		final Button remove = new Button("usuń");
		
		
		greetingService.addRow(this.name.getText(),this.surname.getText(), new AsyncCallback<String[][]>(){

			@Override
			public void onFailure(Throwable caught) {
				System.out.println(caught);	
				
			}
			
			@Override
			public void onSuccess(final String[][] result) {
				int row = table.getRowCount();
				counter = 0;
				for(int i=0;i < result.length ;i++ )
				{					
					if(result[i][0] != null)
					{					
						counter++;						
					}						
				}				
				System.out.println(counter);
				
				table.setText(row,0, id.toString());
				table.setText(row, 1, result[counter][0]);
				table.setText(row, 2, result[counter][1]);
				table.setWidget(row, 4, edit);	   
				table.setWidget(row, 5, remove);
				id++;
				
				
				 edit.addClickHandler(new ClickHandler(){
			    	  public void onClick(ClickEvent event){
							final int editThis = table.getCellForEvent(event).getRowIndex();
							String oldName = table.getText(editThis,1);
							String oldSurname = table.getText(editThis, 2);
							final LongBox nameEdit = new LongBox();
					        nameEdit.setText(oldName);
							final LongBox surnameEdit = new LongBox();
							surnameEdit.setText(oldSurname);
							final Button confirmEdit = new Button("ok");
							table.setWidget(editThis,1,nameEdit);
							table.setWidget(editThis,2,surnameEdit);	
							table.setText(0, 3, "Potwierdź");
							table.setWidget(editThis,3,confirmEdit);	
							
							  confirmEdit.addClickHandler(new ClickHandler(){
									 public void onClick(ClickEvent event){	
											greetingService.editRow(nameEdit.getText().toString(),surnameEdit.getText().toString(),editThis, new AsyncCallback<String[][]>(){

												@Override
												public void onFailure(
														Throwable caught) {
												
													
												}

												@Override
												public void onSuccess(
														String[][] result) {
													
													 table.setText(editThis,1,result[editThis][0]);							
												     table.setText(editThis,2,result[editThis][1]);
												     table.setText(0, 3, "");
												     table.setText(editThis,3,"");	
												}});
										 
										
									 }
								});		
			    	  }
				 });
				 
				 remove.addClickHandler(new ClickHandler(){
			    	  public void onClick(ClickEvent event){
			    		  final int deleteThis = table.getCellForEvent(event).getRowIndex();
			    		
			    		  greetingService.deleteRow(deleteThis, new AsyncCallback<String[][]>(){

							@Override
							public void onFailure(Throwable caught) {
							
								
							}

							@Override
							public void onSuccess(String[][] result) {
								 table.removeRow(deleteThis);
								
							}});
			    	  }
			      });
				
			}
		});
	
	}
	
}
