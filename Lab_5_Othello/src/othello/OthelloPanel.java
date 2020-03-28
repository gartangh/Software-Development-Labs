package othello;

import eventbroker.EventBroker;
import game.events.GameMoveEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import othello.exception.BoardIndexOutOfBoundsException;
import othello.exception.InvalidBoardSizeException;
import othello.exception.InvalidMoveException;

public class OthelloPanel {

	private Pane field;
	private GridPane playingField = new GridPane();

	private Counter emptyCounter = new Counter(0);
	private Counter whiteCounter = new Counter(1);
	private Counter blackCounter = new Counter(-1);

	private OthelloModel othelloModel;
	private OthelloController othelloController;

	private final Handler handler = new Handler();

	// Constructors
	public OthelloPanel() {
		GridPane root = new GridPane();
		root.setVgap(5);
		root.setHgap(5);
		playingField.setVgap(5);
		playingField.setHgap(5);
		GridPane counters = new GridPane();
		counters.setVgap(5);
		counters.setHgap(5);

		GridPane.setConstraints(playingField, 0, 0);
		GridPane.setConstraints(counters, 1, 0);

		root.getChildren().addAll(playingField, counters);

		Pane counter01Pane = emptyCounter.getContent();
		Pane counter11Pane = whiteCounter.getContent();
		Pane counter21Pane = blackCounter.getContent();

		GridPane.setConstraints(counter01Pane, 1, 0);
		GridPane.setConstraints(counter11Pane, 1, 1);
		GridPane.setConstraints(counter21Pane, 1, 2);

		counters.getChildren().addAll(counter01Pane, counter11Pane, counter21Pane);

		this.field = new Pane();
		this.field.getChildren().add(root);
	}

	public OthelloPanel(int size, int role) {
		this();

		emptyCounter.setNumber(size * size);

		// Make OthelloModel and OthelloController
		try {
			this.othelloModel = new OthelloModel(size);
			othelloController = new OthelloController(othelloModel, role);
			
			// Add othelloController to the EventBroker as listener
			EventBroker.getEventBroker().addEventListener(GameMoveEvent.type, othelloController);
			
			// Add listener to valid property
			// UNUSED!
			othelloModel.validProperty().addListener(handler);
		} catch (InvalidBoardSizeException e) {
			e.printStackTrace();
		}

		// Make pieces
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				final int x = i;
				final int y = j;

				OthelloPiece othelloPiece = new OthelloPiece();
				Pane othelloPiecePane = othelloPiece.getContent();

				// On hover
				othelloPiecePane.setOnMouseEntered(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						try {
							if (othelloController.isValidMove(x, y))
								othelloPiece.setState(othelloModel.getTurn());
						} catch (BoardIndexOutOfBoundsException e) {
							e.printStackTrace();
						}
					}

				});

				// On stop hover
				othelloPiecePane.setOnMouseExited(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						othelloPiece.setState(othelloModel.getState(x, y));
					}

				});

				// On click
				othelloPiecePane.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						try {
							if (othelloController.isValidMove(x, y))
								othelloController.doMove(x, y);
							else
								throw new InvalidMoveException();
							
							othelloController.setStates();
							
						} catch (InvalidMoveException | BoardIndexOutOfBoundsException e) {
							System.out.println("Invalid move!");
						}
					}

				});

				othelloPiece.stateProperty().addListener(emptyCounter);
				othelloPiece.stateProperty().addListener(whiteCounter);
				othelloPiece.stateProperty().addListener(blackCounter);

				othelloPiece.setState(othelloModel.getState(i, j));
				othelloController.getPieces()[i][j] = othelloPiece;
				GridPane.setConstraints(othelloPiecePane, j, i);

				playingField.getChildren().addAll(othelloPiecePane);
			}
		}
	}

	public OthelloPanel(int size) {
		this();

		// Make OthelloModel
		try {
			this.othelloModel = new OthelloModel(size);
			othelloController = new OthelloController(othelloModel, -1);
		} catch (InvalidBoardSizeException e) {
			e.printStackTrace();
		}

		// Make pieces
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				OthelloPiece othelloPiece = new OthelloPiece();
				Pane othelloPiecePane = othelloPiece.getContent();

				othelloPiece.setState(0);
				othelloController.getPieces()[i][j] = othelloPiece;
				GridPane.setConstraints(othelloPiecePane, j, i);

				playingField.getChildren().addAll(othelloPiecePane);
			}
		}
	}

	// Getters
	public Pane getContent() {
		return field;
	}

	public OthelloController getOthelloController() {
		return othelloController;
	}

	public Handler getHandler() {
		return handler;
	}

	// Inner class
	// UNUSED!
	private class Handler implements ChangeListener<Boolean> {

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			// TODO Implement logic when valid changes
		}

	}

}
