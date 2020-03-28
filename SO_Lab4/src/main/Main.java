package main;

import java.io.IOException;
import java.net.InetAddress;

import chat.ChatPanel;
import eventbroker.EventBroker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.view.ConnectDialogController;
import network.Network;

public class Main extends Application {

	private Stage window;
	private static Network network;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Log In");
		
		// Close button
		window.setOnCloseRequest(c ->  {
			EventBroker.getEventBroker().stop();
			network.terminate();
		});

		// Grid structure
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(5, 5, 5, 5));
		grid.setVgap(5);
		grid.setHgap(5);

		// Name label
		Label usernameLabel = new Label("Username:");
		GridPane.setConstraints(usernameLabel, 0, 0);

		// Name input
		TextField nameInput = new TextField("Bucky");
		GridPane.setConstraints(nameInput, 1, 0);

		// Port label
		Label portLabel = new Label("port:");
		GridPane.setConstraints(portLabel, 0, 1);

		// Port input
		TextField portInput = new TextField("1234");
		GridPane.setConstraints(portInput, 1, 1);

		// Log in button
		Button loginButton = new Button("Log In");
		loginButton.setDefaultButton(true);
		// Lambda expression
		loginButton.setOnAction(e -> {
			if (isValid(nameInput, portInput)) {
				// Valid input
				String name = nameInput.getText();
				int port = Integer.parseInt(portInput.getText());

				// Start event broker
				EventBroker.getEventBroker().start();

				// Create new network (Server that listens to incoming
				// connections)
				network = new Network(port);
				EventBroker.getEventBroker().addEventListener(network);
				// ChatPanel (ChatModel and ChatController) are created
				ChatPanel chatPanel = ChatPanel.createChatPanel();
				chatPanel.getChatModel().setName(name);

				// Create GUI
				BorderPane borderPane = new BorderPane();
				borderPane.setBottom(chatPanel.getContent());

				// Create menu
				final MenuBar menuBar = new MenuBar();
				final Menu menu = new Menu("Menu");
				menuBar.getMenus().add(menu);
				MenuItem menuItem = new MenuItem("Connect...");
				menuItem.setOnAction(f -> {
					boolean connectClicked = showConnectDialog();
					if (connectClicked) {
						// TODO: Implement logic
					}
				});
				MenuItem[] connect = { menuItem };
				menu.getItems().addAll(connect);
				borderPane.setTop(menuBar);
				
				// Create Scene and show on stage
				Scene chatScene = new Scene(borderPane, 600, 400);
				window.setTitle("Chat");
				window.setScene(chatScene);
			} else {
				// Clear text fields
				nameInput.setText("");
				portInput.setText("");
			}
		});
		GridPane.setConstraints(loginButton, 0, 2);

		// Add all constraints to grid
		grid.getChildren().addAll(usernameLabel, nameInput, portLabel, portInput, loginButton);

		// Create scene and show on stage
		Scene logInScene = new Scene(grid, 300, 150);
		window.setScene(logInScene);
		window.show();
		
		System.out.println("main terminated");
	}

	private boolean showConnectDialog() {
		try {
			// Load the fxml file and create a new stage for the popup dialog
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/ConnectDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Connect");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(window);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			ConnectDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isConnectClicked();
		} catch (IOException e) {
			e.printStackTrace();

			return false;
		}
	}

	private boolean isValid(TextField usernameInput, TextField portInput) {
		if (usernameInput.getText() == null || portInput.getText() == null)
			return false;

		int port;
		try {
			port = Integer.parseInt(portInput.getText());
		} catch (NumberFormatException e) {
			e.printStackTrace();

			return false;
		}

		if (usernameInput.getText().length() == 0 || port < 0 || port >= Math.pow(2, 16))
			return false;

		return true;
	}

	public static Network getNetwork() {
		return network;
	}

	public static void connectToNetwork(InetAddress ip, int port) {
		network.connect(ip, port);
	}

}
