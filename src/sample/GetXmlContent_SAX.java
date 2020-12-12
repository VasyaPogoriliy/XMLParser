package sample;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;

public class GetXmlContent_SAX implements ParseXMLStrategy {

    @Override
    public ArrayList<Scientist> Search(Scientist parameters) {
        final String fileName = "src/sample/scientists.xml";
        ArrayList<Scientist> scientistsInfo = new ArrayList<>();

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();


            // Ініціалізуємо та розширюємо клас DefaultHandler
            DefaultHandler defaultHandler = new DefaultHandler() {

                // Якщо поле true, то тег почався
                boolean isName, isFaculty, isDepartment, isPosition, isSalary, isTimeInOffice = false;
                String name = "";
                String faculty = "";
                String department = "";
                String position = "";
                String salary = "";
                String timeInOffice = "";

                // Метод, що викликається, коли SAXParser доходить до початку тега
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    //thisElement = qName;
                    if (qName.equalsIgnoreCase("NAME")) {
                        isName = true;
                    } else if (qName.equalsIgnoreCase("Faculty")) {
                        isFaculty = true;
                    } else if (qName.equalsIgnoreCase("Department")) {
                        isDepartment = true;
                    } else if (qName.equalsIgnoreCase("Position")) {
                        isPosition = true;
                    } else if (qName.equalsIgnoreCase("Salary")) {
                        isSalary = true;
                    } else if (qName.equalsIgnoreCase("TimeInOffice")) {
                        isTimeInOffice = true;
                    }
                }

                // Метод викликається коли SAXParser зчитує текст між тегами
                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    String value = new String(ch, start, length);
                    if (isName && (value.equals(parameters.getName()) || parameters.getName().equals("default"))) {
                        cleanValues();
                        name = value;
                        isName = false;
                    } else if (isFaculty && (value.equals(parameters.getFaculty()) || parameters.getFaculty().equals("default"))) {
                        faculty = value;
                        isFaculty = false;
                    } else if (isDepartment && (value.equals(parameters.getDepartment()) || parameters.getDepartment().equals("default"))) {
                        department = value;
                        isDepartment = false;
                    } else if (isPosition && (value.equals(parameters.getPosition()) || parameters.getPosition().equals("default"))) {
                        position = value;
                        isPosition = false;
                    } else if (isSalary && (Integer.parseInt(value) == parameters.getSalary() || parameters.getSalary() == 0)) {
                        salary = value;
                        isSalary = false;
                    } else if (isTimeInOffice && (Integer.parseInt(value) == parameters.getTimeInOffice() || parameters.getTimeInOffice() == 0)) {
                        timeInOffice = value;
                        isTimeInOffice = false;
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (!name.equals("") && !faculty.equals("") && !department.equals("") &&
                            !position.equals("") && !salary.equals("") && !timeInOffice.equals("")){
                        scientistsInfo.add(new Scientist(name, faculty, department, position, Integer.parseInt(salary), Integer.parseInt(timeInOffice)));
                        cleanValues();
                    }
                }

                void cleanValues(){
                    name = "";
                    faculty = "";
                    department = "";
                    position = "";
                    salary = "";
                    timeInOffice = "";
                }

            };

            saxParser.parse(fileName, defaultHandler);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return scientistsInfo;
    }

}
