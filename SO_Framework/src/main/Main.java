package main;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import chat.ChatPanel;
import eventbroker.EventBroker;
import game.GameManager;
import game.events.GameInvitationEvent;
import game.events.GameInviteAcceptedEvent;
import game.events.GameInviteDeclinedEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.view.ConnectDialogController;
import network.Network;

public class Main extends Application {

	private Stage window;
	private Network network;
	private int destinationPort;
	private BorderPane borderPane;
	private Pane othelloPanel;
	private GameManager gameManager;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Log In");

		// Close button
		window.setOnCloseRequest(e -> {
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
		TextField mName = new TextField("Bucky");
		GridPane.setConstraints(mName, 1, 0);

		// Port label
		Label portLabel = new Label("port:");
		GridPane.setConstraints(portLabel, 0, 1);

		// Port input
		TextField mPort = new TextField("1234");
		GridPane.setConstraints(mPort, 1, 1);

		// Log in button
		Button loginButton = new Button("Log In");
		loginButton.setDefaultButton(true);
		
		// Create a new GameManager
		gameManager = new GameManager();
		gameManager.setMain(this);
		EventBroker.getEventBroker().addEventListener(GameInvitationEvent.type, gameManager);
		
		// Lambda expression
		loginButton.setOnAction(e -> {
			if (isValid(mName, mPort)) {
				// Valid input
				String name = mName.getText();
				int localport = Integer.parseInt(mPort.getText());

				// Start event broker
				EventBroker.getEventBroker().start();

				// Create new network (Server that listens to incoming
				// connections)
				network = new Network(localport);
				EventBroker.getEventBroker().addEventListener(network);

				// ChatPanel (ChatModel and ChatController) are created
				ChatPanel chatPanel = ChatPanel.createChatPanel();
				chatPanel.getChatModel().setName(name);

				// Create GUI
				borderPane = new BorderPane();
				borderPane.setBottom(chatPanel.getContent());
				if (othelloPanel != null)
					borderPane.setCenter(othelloPanel);

				// Create MenuBar
				final MenuBar menuBar = new MenuBar();

				// Create Menu
				final Menu menu = new Menu("Menu");
				menuBar.getMenus().add(menu);

				// Create MenuItem Connect...
				MenuItem connect = new MenuItem("Connect...");
				connect.setOnAction(f -> {
					if (showConnectDialog()) {
						try {
							// TODO Change to given IP address
							network.connect(InetAddress.getLocalHost(), destinationPort);
						} catch (UnknownHostException ee) {
							ee.printStackTrace();
						}
					}
				});

				// Create MenuItem Open
				MenuItem open = new MenuItem("Open");
				open.setOnAction(g -> {
					// New FileChooser
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("Open Resource File");
					fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Java Archive Files", "*.jar"));

					EventBroker.getEventBroker().addEventListener(GameInviteAcceptedEvent.type, gameManager);
					EventBroker.getEventBroker().addEventListener(GameInviteDeclinedEvent.type, gameManager);
					
					// Get the .jar File
					File selectedFile = fileChooser.showOpenDialog(window);
					if (selectedFile != null) {
						// Load the game
						gameManager.loadGame(selectedFile);
						gameManager.getLoadedGame().showBoard();
						
						// Show game
						updateCenter();
					} else
						System.out.println("No File selected!");
				});

				MenuItem start = new MenuItem("Start");
				start.setOnAction(g -> {
					if (gameManager.getLoadedGame() == null)
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Game not loaded!");
								alert.setContentText("Please load your game before playing.");
								alert.showAndWait();
							}
						});
					else if (!network.isConnected())
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Not yet connected!");
								alert.setContentText("Please connect before pkaying.");
								alert.showAndWait();
							}
						});
					else
						gameManager.sendInvitation();
				});

				// Add MenuItems to Menu
				MenuItem[] menuItems = { connect, open, start };
				menu.getItems().addAll(menuItems);

				// Add MenuBar to scene
				borderPane.setTop(menuBar);

				// Create Scene and show on stage
				Scene chatScene = new Scene(borderPane, 600, 400);
				window.setTitle("Chat");
				window.setScene(chatScene);
			} else {
				// Clear text fields
				mName.setText("");
				mPort.setText("");
			}
		});

		GridPane.setConstraints(loginButton, 0, 2);

		// Add all constraints to grid
		grid.getChildren().addAll(usernameLabel, mName, portLabel, mPort, loginButton);

		// Create scene and show on stage
		Scene logInScene = new Scene(grid, 300, 150);
		window.setScene(logInScene);
		window.show();
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

			destinationPort = controller.getDestinationPort();

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
	
	public void updateCenter() {
		System.out.println(gameManager.getLoadedGame().getGameName());
		othelloPanel = gameManager.getLoadedGame().getGamePanel();
		if (othelloPanel != null) {
			Platform.runLater(new Runnable() {
				public void run() {
					borderPane.setCenter(othelloPanel);
				}
			});
		}
	}

}
