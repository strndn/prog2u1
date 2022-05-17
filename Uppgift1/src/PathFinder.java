import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Optional;

public class PathFinder extends Application{

    private BorderPane root;
    private Stage stage;
    private Pane center;
    private Button newPlaceButton, newConButton, showConButton;
    private final ListGraph<Node> listGraph = new ListGraph<>();
    private SelectHandler selectHandler = new SelectHandler();
    private showConHandler showConHandler = new showConHandler();
    private Node node1 = null, node2 = null;

    @Override
    public void start(Stage primaryStage) {
        stage = new Stage();
        root = new BorderPane();

        VBox vbox = new VBox();
        root.setTop(vbox);
        root.setStyle("-fx-font-size:14");

        MenuBar menuBar = new MenuBar();
        vbox.getChildren().add(menuBar);

        Menu fileMenu = new Menu("File");
        menuBar.getMenus().add(fileMenu);

        MenuItem newMapMenu = new MenuItem("New Map");
        fileMenu.getItems().add(newMapMenu);
        newMapMenu.setOnAction(new newMapHandler());

        MenuItem openMenu = new MenuItem("Open");
        fileMenu.getItems().add(openMenu);
        openMenu.setOnAction(new openHandler());

        MenuItem saveMenu = new MenuItem("Save");
        fileMenu.getItems().add(saveMenu);

        MenuItem saveImgMenu = new MenuItem("Save Image");
        fileMenu.getItems().add(saveImgMenu);

        MenuItem exitMenu = new MenuItem("Exit");
        fileMenu.getItems().add(exitMenu);

        FlowPane controls = new FlowPane();
        vbox.getChildren().add(controls);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(5));
        controls.setHgap(10);

        newPlaceButton = new Button("New Place");
        newPlaceButton.setOnAction(new newPlaceHandler());

        newConButton = new Button("New Connection");
        newConButton.setOnAction(new newConHandler());

        showConButton = new Button("Show Connection");
        showConButton.setOnAction(new showConHandler());

        Button changeConButton = new Button("Change Connection");

        Button findPathButton = new Button("Find Path");
        controls.getChildren().addAll(newPlaceButton, newConButton, showConButton, changeConButton, findPathButton);

        Scene scene = new Scene(root, 618, 82);
        stage.setTitle("PathFinder");
        stage.setScene(scene);
        stage.setResizable(false);
        //stage.setOnCloseRequest(new ExitHandler());
        stage.show();
    }


    class newMapHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent actionEvent) {
            showImg();
        }
    }

    class openHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent actionEvent) {
            showImg();
        }
    }

    class newPlaceHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent actionEvent) {
            center.setOnMouseClicked(new ClickHandler());
            center.setCursor(Cursor.CROSSHAIR);
            newPlaceButton.setDisable(true);
        }
    }

    class newConHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            newConButton.setDisable(true);
            if (node1 == null || node2 == null ) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error!");
                errorAlert.setHeaderText("Two places must be selected!");
                errorAlert.showAndWait();
            } else if (listGraph.getEdgeBetween(node1, node2) != null) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error!");
                errorAlert.setHeaderText("There already exists a connection between these two!");
                errorAlert.showAndWait();
            } else {
                    try {
                        ConForm form = new ConForm(node1, node2);
                        Optional<ButtonType> answer = form.showAndWait();

                        if (answer.isPresent() && answer.get() == ButtonType.OK) {
                            String name = form.getName();
                            int time = form.getTime();
                            listGraph.connect(node1, node2, name, time);
                            Line line = new Line(node1.getxPos(), node1.getyPos(), node2.getxPos(), node2.getyPos());
                            line.setStrokeWidth(4);
                            center.getChildren().add(line);
                        }
                    } catch (NumberFormatException e){
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error!");
                        errorAlert.setHeaderText("Please enter numbers into time!");
                        errorAlert.showAndWait();
                    }
                }
            newConButton.setDisable(false);
            node1.paintUnSelected();
            node1 = null;
            node2.paintUnSelected();
            node2 = null;
        }
    }

    class ClickHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            try {
                NodeForm form = new NodeForm();
                Optional<ButtonType> answer = form.showAndWait();

                if (answer.isPresent() && answer.get() == ButtonType.OK) {
                        String name = form.getName();
                        Node node = new Node(name, x, y);
                        node.setOnMouseClicked(selectHandler);
                        listGraph.add(node);
                        Text text = new Text(node.getName());
                        text.setX(node.getxPos());
                        text.setY(node.getyPos()+30);
                        text.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                        center.getChildren().addAll(node, text);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            center.setOnMouseClicked(null);
            center.setCursor(Cursor.DEFAULT);
            newPlaceButton.setDisable(false);
        }
    }

    class SelectHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {
            Node n = (Node) mouseEvent.getSource();
            if (node1 == null) {
                node1 = n;
                node1.paintSelected();
            } else if (node2 == null && n != node1)  {
                node2 = n;
                node2.paintSelected();
            } else if (node1 == n) {
                node1 = null;
                n.paintUnSelected();
            } else if (node2 == n) {
                node2 = null;
                n.paintUnSelected();
            }
        }
    }
    class showConHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent actionEvent) {
            showConButton.setDisable(true);
            if (node1 == null || node2 == null ) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error!");
                errorAlert.setHeaderText("Two places must be selected!");
                errorAlert.showAndWait();
            } else if (listGraph.getEdgeBetween(node1, node2) == null) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error!");
                errorAlert.setHeaderText("There is no connection between these two!");
                errorAlert.showAndWait();
            } else{
                Edge e = listGraph.getEdgeBetween(node1, node2);
                ShowCon showCon = new ShowCon(node1, node2, e);
                showCon.showAndWait();
                showConButton.setDisable(false);
                node1.paintUnSelected();
                node1 = null;
                node2.paintUnSelected();
                node2 = null;




            }
        }
    }

    private void showImg(){
        center = new Pane();
        Image image = new Image("europa.gif");
        ImageView imageView = new ImageView(image);
        center.getChildren().add(imageView);
        double height = 112 + image.getHeight();
        root.setCenter(center);
        stage.setHeight(height);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
