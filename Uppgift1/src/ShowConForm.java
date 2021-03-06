import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ShowConForm extends Alert {

    private TextField nameField = new TextField();
    private TextField timeField = new TextField();
    public ShowConForm(Node n1, Node n2, Edge e) {
        super(AlertType.CONFIRMATION);
        this.setTitle("Show Connection");
        this.setHeaderText(String.format("Connection from %s to %s", n1.getName(), n2.getName()));
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);

        grid.addRow(0, new Label("Name:"), nameField);
        nameField.setText(e.getName());
        nameField.setEditable(false);
        grid.addRow(1, new Label("Time:"), timeField);
        timeField.setText(String.valueOf(e.getWeight()));
        timeField.setEditable(false);
        getDialogPane().setContent(grid);

    }

    public String getName() {
        return nameField.getText();
    }

    public int getTime() {
        return Integer.parseInt(timeField.getText());
    }
}
