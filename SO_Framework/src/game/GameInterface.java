package game;

import javafx.scene.layout.Pane;

public interface GameInterface {

	public Pane getGamePanel();

	public String getGameName();

	public void startGame(int role);

	public void showBoard();

}
