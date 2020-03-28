package othello;

import game.GameInterface;
import javafx.scene.layout.Pane;

public class Othello implements GameInterface {

	private Pane gamePanel;

	@Override
	public Pane getGamePanel() {
		return gamePanel;
	}

	@Override
	public String getGameName() {
		return "Othello";
	}

	@Override
	public void startGame(int role) {
		OthelloPanel othelloPanel = new OthelloPanel(4, role);
		gamePanel = othelloPanel.getContent();
	}

	@Override
	public void showBoard() {
		OthelloPanel othelloPanel = new OthelloPanel(4);
		gamePanel = othelloPanel.getContent();
	}

}
