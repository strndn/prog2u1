import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class NodeForm extends Alert {

    private TextField nameField = new TextField();
    public NodeForm() {
        super(AlertType.CONFIRMATION);
        this.setTitle("New Place");
        this.setHeaderText("Name your place!");
        this.setGraphic(null);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);

        grid.addRow(0, new Label("Name:"), nameField);
        getDialogPane().setContent(grid);
        getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(Bindings.isEmpty(nameField.textProperty()));
    }

    public String getName() {
        return nameField.getText();
    }
}
