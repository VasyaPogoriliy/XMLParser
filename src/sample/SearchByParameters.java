package sample;

import java.util.ArrayList;


public class SearchByParameters {
    ParseXMLStrategy analyze;
    Scientist parameters;

    public SearchByParameters(ParseXMLStrategy analyze, Scientist parameters) {
        this.analyze = analyze;
        this.parameters = parameters;
    }

    public ArrayList<Scientist> search() {
        return analyze.Search(parameters);
    }

}
