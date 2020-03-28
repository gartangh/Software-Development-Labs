package othello;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainOthello extends Application {

	private Stage window;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("MainOthello");

		Pane field = new OthelloPanel(4).getContent();

		Scene scene = new Scene(field);
		window.setScene(scene);
		window.show();
	}

}
