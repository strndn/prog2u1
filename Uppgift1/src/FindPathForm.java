import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.List;

public class FindPathForm extends Alert {

    private TextArea pathArea = new TextArea();
    public FindPathForm(Node n1, Node n2, List list) {
        super(AlertType.CONFIRMATION);
        this.setTitle("Path");
        this.setHeaderText(String.format("The Path from %s to %s:", n1, n2));
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);

        fillTextArea(list);

        grid.addRow(0, pathArea);
        getDialogPane().setContent(grid);
        getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(Bindings.isEmpty(pathArea.textProperty()));
    }

    private void fillTextArea(List list){
        int totalTime = 0;
        for (Object o : list) {
            pathArea.appendText(o.toString() + "\n");
            String[] s = o.toString().split(" ");
            totalTime += Integer.parseInt(s[s.length-1]);
        }
        pathArea.appendText("Total " + totalTime);
    }

}

