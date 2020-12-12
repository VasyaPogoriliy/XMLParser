package sample;

import org.w3c.dom.*;
import org.xml.sax.*;

import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;

class GetXmlContent_DOM implements ParseXMLStrategy {

    private NodeList initializeDocument() throws ParserConfigurationException, IOException, SAXException {
        // Створюємо будівника документа
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        // Створюємо дерево DOM документа з файлу
        Document document = documentBuilder.parse("src/sample/scientists.xml");

        // Отримуємо кореневий документ
        Node root = document.getDocumentElement();

        // Повертаємо елементи документу
        return root.getChildNodes();
    }

    @Override
    public ArrayList<Scientist> Search(Scientist parameters) {
        ArrayList<Scientist> scientistsInfo = new ArrayList<>();

        try {
            // Отримуємо всіх вмкладачів з документу
            NodeList allScientists = initializeDocument();

            // Перебираємо всі елементи(викладачів)
            for (int i = 0; i < allScientists.getLength(); i++) {
                Node concreteScientist = allScientists.item(i);
                String name = "";
                String faculty = "";
                String department = "";
                String position = "";
                String salary = "";
                String timeInOffice = "";

                // Якщо нода не текст, то це конкретний викладач
                if (concreteScientist.getNodeType() != Node.TEXT_NODE) {
                    NodeList scientistNODES = concreteScientist.getChildNodes();
                    for (int j = 0; j < scientistNODES.getLength(); j++) {
                        Node concreteNODE = scientistNODES.item(j);

                        // Якщо нода не текст, то це один з параметрів викладача
                        if (concreteNODE.getNodeType() != Node.TEXT_NODE) {
                            if (concreteNODE.getNodeName().equals("Name") &&
                                    (concreteNODE.getTextContent().equals(parameters.getName()) || parameters.getName().equals("default"))) {
                                name = concreteNODE.getTextContent();
                            }
                            if (concreteNODE.getNodeName().equals("Faculty") &&
                                    (concreteNODE.getTextContent().equals(parameters.getFaculty()) || parameters.getFaculty().equals("default"))) {
                                faculty = concreteNODE.getTextContent();
                            }

                            if (concreteNODE.getNodeName().equals("Department") &&
                                    (concreteNODE.getTextContent().equals(parameters.getDepartment()) || parameters.getDepartment().equals("default"))) {
                                department = concreteNODE.getTextContent();
                            }
                            if (concreteNODE.getNodeName().equals("Position") &&
                                    (concreteNODE.getTextContent().equals(parameters.getPosition()) || parameters.getPosition().equals("default"))) {
                                position = concreteNODE.getTextContent();
                            }
                            if (concreteNODE.getNodeName().equals("Salary") &&
                                    (Integer.parseInt(concreteNODE.getTextContent()) == parameters.getSalary() || parameters.getSalary() == 0)) {
                                salary = concreteNODE.getTextContent();
                            }
                            if (concreteNODE.getNodeName().equals("TimeInOffice") &&
                                    (Integer.parseInt(concreteNODE.getTextContent()) == parameters.getTimeInOffice() || parameters.getTimeInOffice() == 0)) {
                                timeInOffice = concreteNODE.getTextContent();
                            }
                        }
                    }
                    if (!name.equals("") && !faculty.equals("") && !department.equals("") &&
                            !position.equals("") && !salary.equals("") && !timeInOffice.equals("")) {
                        scientistsInfo.add(new Scientist(name, faculty, department, position, Integer.parseInt(salary), Integer.parseInt(timeInOffice)));
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace(System.out);
        }
        return scientistsInfo;
    }


    public ArrayList<Scientist> initializeScientists() {
        ArrayList<Scientist> scientists = new ArrayList<>();

        try {
            // Отримуємо всіх вмкладачів з документу
            NodeList allScientists = initializeDocument();

            // Перебираємо всі елементи(викладачів)
            for (int i = 0; i < allScientists.getLength(); i++) {
                Node concreteScientist = allScientists.item(i);
                Scientist scientist = new Scientist();

                // Якщо нода не текст, то це конкретний викладач
                if (concreteScientist.getNodeType() != Node.TEXT_NODE) {
                    NodeList scientistNODES = concreteScientist.getChildNodes();
                    for (int j = 0; j < scientistNODES.getLength(); j++) {
                        Node concreteNODE = scientistNODES.item(j);

                        // Якщо нода не текст, то це один з параметрів викладача
                        if (concreteNODE.getNodeType() != Node.TEXT_NODE) {
                            switch (concreteNODE.getNodeName()) {
                                case "Name" -> scientist.setName(concreteNODE.getTextContent());
                                case "Faculty" -> scientist.setFaculty(concreteNODE.getTextContent());
                                case "Department" -> scientist.setDepartment(concreteNODE.getTextContent());
                                case "Position" -> scientist.setPosition(concreteNODE.getTextContent());
                                case "Salary" -> scientist.setSalary(Integer.parseInt(concreteNODE.getTextContent()));
                                case "TimeInOffice" -> scientist.setTimeInOffice(Integer.parseInt(concreteNODE.getTextContent()));
                            }
                        }
                    }
                    scientists.add(scientist);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace(System.out);
        }
        return scientists;
    }

}