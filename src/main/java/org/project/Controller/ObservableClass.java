package org.project.Controller;

import org.project.View.ClassBox;
import java.util.ArrayList;
import java.util.List;
public class ObservableClass {
    private ClassBox selectedClassBox;
    private final List<ObserverInterface> observable = new ArrayList<>();
    private final List<ClassBox> classBoxList = new ArrayList<>();

    public void addObserver(ObserverInterface observer) {
        observable.add(observer);
    }

    public void removeObserver(ObserverInterface observer) {
        observable.remove(observer);
    }


    public void addClassBox(ClassBox classBox) {
        classBoxList.add(classBox);
        notifyObservers();
    }

    public void removeClasBox(ClassBox classBox) {
        classBoxList.remove(classBox);
        notifyObservers();
    }

    public void setSelectedClassBox(ClassBox classBox) {
        this.selectedClassBox = classBox;
        notifyObservers();
    }

    public ClassBox getSelectedClassBox() {
        return selectedClassBox;
    }

    public List<ClassBox> getAllClassBoxes() {
        return new ArrayList<>(classBoxList);
    }

    public void notifyObservers() {
        for(ObserverInterface observer : observable) {
            observer.update(selectedClassBox);
        }
    }
}


