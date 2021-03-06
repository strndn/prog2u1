import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ConForm extends Alert {

    private TextField nameField = new TextField();
    private TextField timeField = new TextField();
    public ConForm(Node n1, Node n2) {
        super(AlertType.CONFIRMATION);
        this.setTitle("New Connection");
        this.setHeaderText(String.format("Connection from %s to %s", n1.getName(), n2.getName()));
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);

        grid.addRow(0, new Label("Name:"), nameField);
        grid.addRow(1, new Label("Time:"), timeField);
        getDialogPane().setContent(grid);
        getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(
                Bindings.isEmpty(nameField.textProperty())
                .and(Bindings.isEmpty(timeField.textProperty())));
    }

    public String getName() {
        return nameField.getText();
    }

    public int getTime() {
        return Integer.parseInt(timeField.getText());
    }
}
