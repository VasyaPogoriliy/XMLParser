package sample;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.*;


public class Controller {

    @FXML
    private CheckBox CheckBox_Name;

    @FXML
    private CheckBox CheckBox_Faculty;

    @FXML
    private CheckBox CheckBox_Department;

    @FXML
    private CheckBox CheckBox_Position;

    @FXML
    private CheckBox CheckBox_Salary;

    @FXML
    private CheckBox CheckBox_TimeInOffice;

    @FXML
    private RadioButton DOM;

    @FXML
    private RadioButton SAX;

    @FXML
    private Button Search;

    @FXML
    private Button Clean;

    @FXML
    private Button InHTML;

    @FXML
    private TextArea TextArea;

    @FXML
    private ComboBox<String> ComboBox_Name;

    @FXML
    private ComboBox<String> ComboBox_Faculty;

    @FXML
    private ComboBox<String> ComboBox_Department;

    @FXML
    private ComboBox<String> ComboBox_Position;

    @FXML
    private ComboBox<Integer> ComboBox_Salary;

    @FXML
    private ComboBox<Integer> ComboBox_TimeInOffice;

    @FXML
    void initialize() throws ParserConfigurationException, SAXException, IOException {

        // Ініціалізуємо всі комбобокси елементами з документу
        GetXmlContent_DOM parseXML_DOM = new GetXmlContent_DOM();
        ArrayList<Scientist> scientists = parseXML_DOM.initializeScientists();
        initializeComboBox(scientists);

        // Синхронізуємо RadioButtons, помістивши їх в одну групу
        final ToggleGroup toggleGroup = new ToggleGroup();
        DOM.setToggleGroup(toggleGroup);
        SAX.setToggleGroup(toggleGroup);

        // Якщо ніякий метод пошуку не обрано, то по замовчуванню DOM
        if (!DOM.isSelected() && !SAX.isSelected()) {
            DOM.setSelected(true);
        }

        Search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                search();
            }
        });

        Clean.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TextArea.setText("");
            }
        });

        InHTML.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                transform();
            }
        });

    }

    private void search() {
        // По замовченню метод пошуку DOM
        ParseXMLStrategy analyze = new GetXmlContent_DOM();
        Scientist parameters = new Scientist();
        TextArea.setText("");

        // Метод яким будемо шукати інформацію
        if (DOM.isSelected()) {
            analyze = new GetXmlContent_DOM();
        } else if (SAX.isSelected()) {
            analyze = new GetXmlContent_SAX();
        }

        if (CheckBox_Name.isSelected()) {
            parameters.setName(ComboBox_Name.getValue());
        }
        if (CheckBox_Faculty.isSelected()) {
            parameters.setFaculty(ComboBox_Faculty.getValue());
        }
        if (CheckBox_Department.isSelected()) {
            parameters.setDepartment(ComboBox_Department.getValue());
        }
        if (CheckBox_Position.isSelected()) {
            parameters.setPosition(ComboBox_Position.getValue());
        }
        if (CheckBox_Salary.isSelected()) {
            parameters.setSalary(ComboBox_Salary.getValue());
        }
        if (CheckBox_TimeInOffice.isSelected()) {
            parameters.setTimeInOffice(ComboBox_TimeInOffice.getValue());
        }

        SearchByParameters searchByParameters = new SearchByParameters(analyze, parameters);
        ArrayList<Scientist> searchResult = searchByParameters.search();


        for (Scientist scientist : searchResult) {
            TextArea.appendText("ПІП: " + scientist.getName() + "\n");
            TextArea.appendText("Факультет: " + scientist.getFaculty() + "\n");
            TextArea.appendText("Кафедра: " + scientist.getDepartment() + "\n");
            TextArea.appendText("Посада: " + scientist.getPosition() + "\n");
            TextArea.appendText("Оклад: " + scientist.getSalary() + "\n");
            TextArea.appendText("Час на посаді: " + scientist.getTimeInOffice() + "\n");
            TextArea.appendText("\n");
        }

    }

    private void transform() {
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();

            Source xslDoc = new StreamSource("src/sample/HTMLScientists.xsl");
            Source xmlDoc = new StreamSource("src/sample/scientists.xml");

            String outputFileName = "scientists.html";

            OutputStream htmlFile = new FileOutputStream(outputFileName);
            Transformer transform = tFactory.newTransformer(xslDoc);
            transform.transform(xmlDoc, new StreamResult(htmlFile));
        } catch (FileNotFoundException | TransformerFactoryConfigurationError | TransformerException e) {
            e.printStackTrace();
        }

    }

    private void initializeComboBox(ArrayList<Scientist> scientists) {
        Set<String> names = new TreeSet<>();
        Set<String> faculties = new TreeSet<>();
        Set<String> departments = new TreeSet<>();
        Set<String> positions = new TreeSet<>();
        Set<Integer> salaries = new TreeSet<>();
        Set<Integer> time = new TreeSet<>();

        for (Scientist scientist : scientists) {
            names.add(scientist.getName());
            faculties.add(scientist.getFaculty());
            departments.add(scientist.getDepartment());
            positions.add(scientist.getPosition());
            salaries.add(scientist.getSalary());
            time.add(scientist.getTimeInOffice());
        }

        ComboBox_Name.getItems().addAll(names);
        ComboBox_Faculty.getItems().addAll(faculties);
        ComboBox_Department.getItems().addAll(departments);
        ComboBox_Position.getItems().addAll(positions);
        ComboBox_Salary.getItems().addAll(salaries);
        ComboBox_TimeInOffice.getItems().addAll(time);
    }


}

