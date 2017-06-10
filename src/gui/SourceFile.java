package gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by garicchi on 16/05/15.
 */
public class SourceFile {
    public StringProperty path;
    public StringProperty name;
    public StringProperty label;

    public SourceFile(String path,String name,String label){
        this.path = new SimpleStringProperty(path);
        this.name = new SimpleStringProperty(name);
        this.label = new SimpleStringProperty(label);

    }

    public StringProperty pathProperty(){
        return path;
    }

    public StringProperty nameProperty(){
        return name;
    }

    public StringProperty labelProperty(){
        return label;
    }
}
