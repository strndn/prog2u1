import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class NodeForm extends Alert {

    private TextField nameField = new TextField();
    public NodeForm() {
        super(AlertType.CONFIRMATION);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);

        grid.addRow(0, new Label("Namn"), nameField);
        getDialogPane().setContent(grid);
    }

    public String getName() {
        return nameField.getText();
    }
}
