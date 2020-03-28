package othello;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class OthelloPiece {

	private Pane field;

	private ObjectProperty<Integer> state = new SimpleObjectProperty<>();

	// Constructor
	public OthelloPiece() {
		this.field = new Pane();
		this.field.setPrefSize(50, 50);
		this.field.setStyle("-fx-background-color: grey;");
		setState(0);
	}

	// Getter
	public Pane getContent() {
		return field;
	}

	// Methods
	public ObjectProperty<Integer> stateProperty() {
		return state;
	}

	public synchronized int getState() {
		return state.getValue();
	}

	public synchronized void setState(int newValue) {
		state.setValue(newValue);

		Circle circle;
		if (newValue == 1) {
			circle = new Circle(25, 25, 25, Color.WHITE);
			circle.setStroke(Color.BLACK);
			field.getChildren().add(circle);
		} else if (newValue == -1) {
			circle = new Circle(25, 25, 25, Color.BLACK);
			circle.setStroke(Color.WHITE);
			field.getChildren().add(circle);
		} else {
			if (field.getChildren().size() > 0)
				field.getChildren().remove(0, field.getChildren().size());
		}
	}

}
