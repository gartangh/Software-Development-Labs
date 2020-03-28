package othello;

import game.GameInterface;
import javafx.scene.layout.Pane;

public class Othello implements GameInterface {

	private Pane gamePanel;

	public Othello() {
		gamePanel = new OthelloPanel(4).getContent();
	}

	@Override
	public Pane getGamePanel() {
		return gamePanel;
	}

}
