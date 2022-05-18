import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class PathFinder extends Application{
    private boolean changed;
    private BorderPane root;
    private Stage stage;
    private Pane center;
    private Button newPlaceButton;
    private Button newConButton;
    private Button showConButton;
    private Button changeConButton;
    private Button findPathButton;
    private final ListGraph<Node> listGraph = new ListGraph<>();
    private SelectHandler selectHandler = new SelectHandler();
    private Node node1;
    private Node node2;

    @Override
    public void start(Stage primaryStage) {
        stage = new Stage();
        root = new BorderPane();

        center = new Pane();
        root.setCenter(center);

        VBox vbox = new VBox();
        root.setTop(vbox);
        root.setStyle("-fx-font-size:14");

        MenuBar menuBar = new MenuBar();
        vbox.getChildren().add(menuBar);

        Menu fileMenu = new Menu("File");
        menuBar.getMenus().add(fileMenu);

        MenuItem newMapMenu = new MenuItem("New Map");
        fileMenu.getItems().add(newMapMenu);
        newMapMenu.setOnAction(new NewMapHandler());

        MenuItem openMenu = new MenuItem("Open");
        fileMenu.getItems().add(openMenu);
        openMenu.setOnAction(new OpenHandler());

        MenuItem saveMenu = new MenuItem("Save");
        fileMenu.getItems().add(saveMenu);
        saveMenu.setOnAction(new SaveHandler());

        MenuItem saveImgMenu = new MenuItem("Save Image");
        fileMenu.getItems().add(saveImgMenu);
        saveImgMenu.setOnAction(new SaveImageHandler());

        MenuItem exitMenu = new MenuItem("Exit");
        fileMenu.getItems().add(exitMenu);
        exitMenu.setOnAction(new ExitMenuHandler());

        FlowPane controls = new FlowPane();
        vbox.getChildren().add(controls);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(5));
        controls.setHgap(10);

        newPlaceButton = new Button("New Place");
        newPlaceButton.setOnAction(new NewPlaceHandler());

        newConButton = new Button("New Connection");
        newConButton.setOnAction(new NewConHandler());

        showConButton = new Button("Show Connection");
        showConButton.setOnAction(new ShowConHandler());

        changeConButton = new Button("Change Connection");
        changeConButton.setOnAction(new ChangeConHandler());

        findPathButton = new Button("Find Path");
        findPathButton.setOnAction(new FindPathHandler());
        controls.getChildren().addAll(newPlaceButton, newConButton, showConButton, changeConButton, findPathButton);

        Scene scene = new Scene(root, 618, 82);
        stage.setTitle("PathFinder");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(new ExitHandler());
        stage.show();

        //id för testning----------------------------
        menuBar.setId("menu");
        fileMenu.setId("menuFile");
        newMapMenu.setId("menuNewMap");
        openMenu.setId("menuOpenFile");
        saveMenu.setId("menuSaveFile");
        saveImgMenu.setId("menuSaveImage");
        exitMenu.setId("menuExit");

        findPathButton.setId("btnFindPath");
        showConButton.setId("btnShowConnection");
        newPlaceButton.setId("btnNewPlace");
        changeConButton.setId("btnChangeConnection");
        newConButton.setId("btnNewConnection");
        center.setId("outputArea");
        //-------------------------------------------
    }


    //hanterar exit från menyn
    class ExitMenuHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        }
    }

    //hanterar exit från kryss
    class ExitHandler implements EventHandler<WindowEvent> {
        @Override
        public void handle(WindowEvent windowEvent) {
            if (changed) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Unsaved changes, exit anyway?");
                alert.setContentText(null);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() != ButtonType.OK)
                    windowEvent.consume();
            }
        }
    }

    //laddar in en ny karta
    class NewMapHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (node1 != null) {
                node1.paintUnSelected();
                node1 = null;
            }

            if (node2 != null) {
                node2.paintUnSelected();
                node2 = null;
            }

            if (changed) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Unsaved changes, create new anyway?");
                alert.setContentText(null);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK){
                    showImg("europa.gif");
                }
            } else {
                showImg("europa.gif");
            }
        }
    }

    //laddar in en fil
    class OpenHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            if (node1 != null) {
                node1.paintUnSelected();
                node1 = null;
            }

            if (node2 != null) {
                node2.paintUnSelected();
                node2 = null;
            }

            if (changed) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Unsaved changes, open anyway?");
                alert.setContentText(null);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK){
                    open();
                }
            } else {
                open();
            }
        }
    }

    private void open(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("europa.graph"));

            ArrayList<String> al = new ArrayList<>();
            String s;

            while ((s = bufferedReader.readLine()) != null) {
                al.add(s);
            }

            openImageFile(al.get(0));
            readNodes(al.get(1));

            for(int i = 2; i < al.size(); i++) {
                readEdges(al.get(i));
            }
            bufferedReader.close();
        } catch(IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Fel "+e.getMessage());
            alert.showAndWait();
        }
    }

    //sparar till en fil
    class SaveHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                FileWriter writer = new FileWriter("europa.graph");
                PrintWriter out = new PrintWriter(writer);
                out.println("file:europa.gif");

                Set<Node> set = listGraph.getNodes();
                ArrayList<String> al = new ArrayList<>();
                String s = "";
                String t = "";
                Collection<Edge> col;
                for (Node n : set){
                    s += String.format("%s;%s;%s;", n.getName(), n.getFirstCoordinate(), n.getSecondCoordinate());

                    col = listGraph.getEdgesFrom(n);
                    for (Edge e : col) {
                        t = String.format("%s;%s;%s;%s", n.getName(), e.getDestination(), e.getName(), e.getWeight());
                        al.add(t);
                    }
                }
                s = s.substring(0, s.length()-1);
                out.println(s);
                for (String d : al) {
                    out.println(d);
                }
                out.close();
                writer.close();
                changed = false;

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Fel "+e.getMessage());
                alert.showAndWait();
            }
        }
    }

    //läser in addressen till filens bild
    private void openImageFile(String s){
        String[] file = s.split(":");
        showImg(file[1]);
    }

    //läser in alla noder
    private void readNodes(String s){
        String[] nodes = s.split(";");
        for (int i = 0; i < nodes.length; i+= 3) {
            createNode(nodes[i], Double.parseDouble(nodes[i+1]), Double.parseDouble(nodes[i+2]));
        }
    }

    //läser in en nods kanter
    private void readEdges(String s) {
        String[] edges = s.split(";");

        if (listGraph.getEdgeBetween(getNode(edges[1]), getNode(edges[0])) == null) {
            createEdges(getNode(edges[0]), getNode(edges[1]), edges[2], Integer.parseInt(edges[3]));
        }
    }

    //hämtar en nod
    private Node getNode(String s){
        Set<Node> set = listGraph.getNodes();
        for (Node n : set) {
            if (n.getName().equals(s))
                return n;
        }
        return null;
    }

    //hanterar när man skapar en ny nod
    class NewPlaceHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent actionEvent) {
            center.setOnMouseClicked(new ClickHandler());
            center.setCursor(Cursor.CROSSHAIR);
            newPlaceButton.setDisable(true);
        }
    }

    //hanterar när man skapar en ny koppling mellan två noder
    class NewConHandler implements EventHandler<ActionEvent> {
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
                unselect();
            } else {
                    try {
                        ConForm form = new ConForm(node1, node2);
                        Optional<ButtonType> answer = form.showAndWait();

                        if (answer.isPresent() && answer.get() == ButtonType.OK) {
                            String name = form.getName();
                            int time = form.getTime();
                            createEdges(node1, node2, name, time);
                            changed = true;
                        }
                        unselect();
                    } catch (NumberFormatException e){
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error!");
                        errorAlert.setHeaderText("Please enter numbers into time!");
                        errorAlert.showAndWait();
                    }
                }
            newConButton.setDisable(false);
        }
    }

    //skapar kanter mellan två noder
    private void createEdges(Node n1, Node n2, String name, int time){
        listGraph.connect(n1, n2, name, time);
        Line line = new Line(n1.getFirstCoordinate(), n1.getSecondCoordinate(), n2.getFirstCoordinate(), n2.getSecondCoordinate());
        line.setStrokeWidth(4);
        line.setDisable(true);
        center.getChildren().add(line);
    }

    //hanterar vart man klickade på kartan
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
                        createNode(name, x, y);
                        changed = true;
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Fel "+e.getMessage());
                alert.showAndWait();
            }
            center.setOnMouseClicked(null);
            center.setCursor(Cursor.DEFAULT);
            newPlaceButton.setDisable(false);
        }
    }

    //skapar en ny nod
    private void createNode(String name, double x, double y){
        Node node = new Node(name, x, y);
        node.setOnMouseClicked(selectHandler);
        listGraph.add(node);
        node.setId(node.getName());
        Text text = new Text(node.getName());
        text.setX(node.getFirstCoordinate());
        text.setY(node.getSecondCoordinate()+30);
        text.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        center.getChildren().addAll(node, text);
    }

    //hanterar vilka noder man markerar
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

    //hanterar vilka noder man vill visa koppling mellan
    class ShowConHandler implements EventHandler<ActionEvent>{
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
                unselect();
            } else{
                Edge e = listGraph.getEdgeBetween(node1, node2);
                ShowConForm showConForm = new ShowConForm(node1, node2, e);
                showConForm.showAndWait();
                unselect();
            }
            showConButton.setDisable(false);
        }
    }

    //avselekterar alla noder som är selekterade
    private void unselect(){
        node1.paintUnSelected();
        node1 = null;
        node2.paintUnSelected();
        node2 = null;
    }

    //hanterar ändring av kopplings vikt
    class ChangeConHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            changeConButton.setDisable(true);
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
                unselect();
            } else{
                Edge e = listGraph.getEdgeBetween(node1, node2);
                ChangeConForm changeConForm = new ChangeConForm(node1, node2, e);
                changeConForm.showAndWait();
                listGraph.setConnectionWeight(node1, node2, changeConForm.getTime());
                changed = true;
                unselect();
            }
            changeConButton.setDisable(false);
        }
    }

    //hanterar letandet av den kortaste vägen mellan två noder
    class FindPathHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            changeConButton.setDisable(true);
            if (node1 == null || node2 == null ) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error!");
                errorAlert.setHeaderText("Two places must be selected!");
                errorAlert.showAndWait();
            } else if (!listGraph.pathExists(node1, node2)) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error!");
                errorAlert.setHeaderText("There is no path between these two!");
                errorAlert.showAndWait();
                unselect();
            } else{
                List l = listGraph.getPath(node1, node2);
                FindPathForm form = new FindPathForm(node1, node2, l);
                form.showAndWait();
                unselect();
            }
            changeConButton.setDisable(false);
        }
    }

    //sparar en skärmdump av kartan
    class SaveImageHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent event){
            try{
                WritableImage image = center.snapshot(null, null);
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
                ImageIO.write(bufferedImage, "png", new File("capture.png"));
            }catch (IOException e){
                Alert alert = new Alert(Alert.AlertType.ERROR,"IO-fel "+e.getMessage());
                alert.showAndWait();
            }
        }
    }

    //visar upp kartan
    private void showImg(String fil){
        center.getChildren().clear();
        listGraph.clear();
        Image image = new Image(fil);
        ImageView imageView = new ImageView(image);
        center.getChildren().add(imageView);
        double height = 112 + image.getHeight();
        stage.setHeight(height);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
