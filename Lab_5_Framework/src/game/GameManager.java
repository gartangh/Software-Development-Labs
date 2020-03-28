package game;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Optional;

import eventbroker.Event;
import eventbroker.EventListener;
import eventbroker.EventPublisher;
import game.events.GameInvitationEvent;
import game.events.GameInviteAcceptedEvent;
import game.events.GameInviteDeclinedEvent;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import main.Main;
import javafx.scene.control.ButtonType;

public class GameManager extends EventPublisher implements EventListener {

	private GameInterface game;

	// Reference to the main application
	private Main main;

	public void setMain(Main main) {
		this.main = main;
	}

	public void loadGame(File gameFile) {
		// Get classname by naming convention
		String fileName = gameFile.getName();
		String fileNameNoExtention = fileName.substring(0, fileName.indexOf('.'));

		// Get URL of .jar File
		URL[] urls = new URL[1];
		try {
			urls[0] = gameFile.toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		// Create a URLCLassLoader
		URLClassLoader loader = new URLClassLoader(urls);
		try {
			Class<?> someClass = loader.loadClass(fileNameNoExtention.toLowerCase() + "." + fileNameNoExtention);

			// Get interfaces
			Class<?>[] interfaces = someClass.getInterfaces();
			for (int i = 0; i < interfaces.length; i++) {
				if (interfaces[i].getName().equals("game.GameInterface"))
					game = (GameInterface) someClass.newInstance();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		// Close the URLCLassLoader
		try {
			loader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public GameInterface getLoadedGame() {
		return game;
	}

	@Override
	public void handleEvent(Event event) {
		switch (event.getType()) {
		case GameInvitationEvent.type:
			// 1.1.1.1
			@SuppressWarnings("unused")
			GameInvitationEvent gIE = (GameInvitationEvent) event;

			// 1.1.1.1.1
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					showOptionDialog();
				}
			});

			break;

		case GameInviteAcceptedEvent.type:
			@SuppressWarnings("unused")
			GameInviteAcceptedEvent gIAE = (GameInviteAcceptedEvent) event;
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Game invite accepted");
					alert.setContentText("Your game invite was accepted.");

					alert.showAndWait();

					// Start the game as white
					// 1.1.1.1.3.1.1.1
					game.startGame(-1);
					main.updateCenter();
				}
			});

			break;

		case GameInviteDeclinedEvent.type:
			@SuppressWarnings("unused")
			GameInviteDeclinedEvent gIDE = (GameInviteDeclinedEvent) event;
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Game invite declined");
					alert.setContentText("Your game invite was declined.");

					alert.showAndWait();
				}
			});

			break;
		}
	}

	public void sendInvitation() {
		// 1.1
		publishEvent(new GameInvitationEvent());
	}

	public void acceptInvitation() {
		publishEvent(new GameInviteAcceptedEvent());
	}

	public void declineInvitation() {
		publishEvent(new GameInviteDeclinedEvent());
	}

	private void showOptionDialog() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Game invite");
		alert.setHeaderText("You got a new game invite");
		alert.setContentText("Would you like to play?");

		// 1.1.1.1.2
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			if(game != null) {
				// 1.1.1.1.3
				acceptInvitation();

				// Start the game as black
				// 1.1.1.1.4
				game.startGame(1);
				main.updateCenter();
			} else {
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Game not loaded!");
				alert.setContentText("Please load your game before playing.");
				alert.showAndWait();
				declineInvitation();
			}
		} else {
			// 1.1.1.1.3
			declineInvitation();
		}
	}

}
