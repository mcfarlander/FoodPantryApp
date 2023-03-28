package org.pantry.food;

import java.util.function.Function;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

public class SwitchContextEventHandler implements EventHandler<ActionEvent> {

	private final UiMainContext context;
	private final Function<ActionEvent, Node> fn;

	public SwitchContextEventHandler(UiMainContext context, Function<ActionEvent, Node> fn) {
		this.context = context;
		this.fn = fn;
	}

	@Override
	public void handle(ActionEvent event) {
		Node newContext = fn.apply(event);
		context.switchContext(newContext);
	}

}
