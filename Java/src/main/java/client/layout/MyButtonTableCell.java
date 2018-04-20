package main.java.client.layout;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.util.Callback;

public class MyButtonTableCell<S, T> extends TableCell<S, T> {

    private Button button;
    private Callback<S, Object> callback;

    public MyButtonTableCell(String text, Callback<S, Object> callback) {
        button = new Button(text);
        this.callback = callback;
    }



    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            button.setOnAction(event -> callback.call(getTableView().getItems().get(getIndex())));
            setGraphic(button);
        }

        setText(null);
    }
}
