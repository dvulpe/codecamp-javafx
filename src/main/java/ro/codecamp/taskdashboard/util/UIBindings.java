package ro.codecamp.taskdashboard.util;

import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Labeled;

import java.lang.reflect.Field;

public class UIBindings {

    private Object model;
    private Parent view;

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public Parent getView() {
        return view;
    }

    public void setView(Parent view) {
        this.view = view;
        try {
            doBinding();
        } catch (IllegalAccessException e) {
            // whatever failed
            e.printStackTrace();
        }
    }

    private void doBinding() throws IllegalAccessException {
        Field[] declaredFields = model.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (!field.getType().isAssignableFrom(StringProperty.class)) {
                continue;
            }
            field.setAccessible(true);
            Node node = view.lookup("#" + field.getName());
            if (node != null && node instanceof Labeled) {
                StringProperty stringProperty = Labeled.class.cast(node).textProperty();
                stringProperty.bindBidirectional((StringProperty) field.get(model));
            }
        }
    }
}
