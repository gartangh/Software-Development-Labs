package main.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConnectDialogController {

	@FXML
	private TextField mDestinationPort;

	private Stage dialogStage;
	private int destinationPort;
	private boolean connectClicked = false;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Empty initialize
	}

	/**
	 * Sets the stage of this dialog.
	 * 
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Returns true if the user clicked Connect, false otherwise.
	 * 
	 * @return
	 */
	public boolean isConnectClicked() {
		return connectClicked;
	}

	public int getDestinationPort() {
		return destinationPort;
	}

	/**
	 * Called when the user clicks connect.
	 */
	@FXML
	private void handleConnect() {
		try {
			destinationPort = Integer.parseInt(mDestinationPort.getText());

			connectClicked = true;

			dialogStage.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

}
