package main.view;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;

public class ConnectDialogController {

	@FXML
	private TextField destinationPort;
	@FXML
	private Button connectButton;

	private Stage dialogStage;
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

	/**
	 * Called when the user clicks connect.
	 */
	@FXML
	private void handleConnect() {
		try {
			int port = (Integer.parseInt(destinationPort.getText()));

			// TODO: Change to given IP address
			Main.connectToNetwork(InetAddress.getLocalHost(), port);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		connectClicked = true;
		dialogStage.close();
	}

}
