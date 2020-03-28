package othello;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class Counter implements ChangeListener<Integer> {

	private Pane field;

	private IntegerProperty number = new SimpleIntegerProperty();
	private IntegerProperty stateToCount = new SimpleIntegerProperty();

	// Constructors
	public Counter() {
		this.field = new Pane();
		this.field.setPrefSize(50, 50);
		this.field.setStyle("-fx-border-color: black");
		Label state = new Label();
		Label count = new Label();
		count.setText("0");
		count.setPadding(new Insets(20, 0, 0, 0));
		this.field.getChildren().addAll(state, count);
		setNumber(0);
	}

	public Counter(int stateToCount) {
		this();
		setStateToCount(stateToCount);
	}

	// Getters and setters
	public synchronized int getNumber() {
		return number.getValue();
	}

	public synchronized void setNumber(int newValue) {
		number.setValue(newValue);
		((Label) field.getChildren().get(1)).setText(Integer.toString(getNumber()));
	}

	public Pane getContent() {
		return field;
	}

	// Methods
	public IntegerProperty numberProperty() {
		return number;
	}

	public void increment() {
		setNumber(getNumber() + 1);
		((Label) field.getChildren().get(1)).setText(Integer.toString(getNumber()));
	}

	public void decrement() {
		setNumber(getNumber() - 1);
		((Label) field.getChildren().get(1)).setText(Integer.toString(getNumber()));
	}

	public synchronized int getStateToCount() {
		return stateToCount.getValue();
	}

	public synchronized void setStateToCount(int newValue) {
		stateToCount.setValue(newValue);
		if (newValue == -1)
			((Label) field.getChildren().get(0)).setText("Black");
		else if (newValue == 1)
			((Label) field.getChildren().get(0)).setText("White");
		else
			((Label) field.getChildren().get(0)).setText("Empty");
	}

	public IntegerProperty stateToCountProperty() {
		return stateToCount;
	}

	@Override
	public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
		if (newValue == getStateToCount())
			increment();
		else if (oldValue == getStateToCount())
			decrement();
	}

}
