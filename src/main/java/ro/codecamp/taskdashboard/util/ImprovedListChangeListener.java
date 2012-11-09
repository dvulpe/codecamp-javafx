package ro.codecamp.taskdashboard.util;

import javafx.collections.ListChangeListener;

public abstract class ImprovedListChangeListener<T> implements ListChangeListener<T> {
    @Override
    public void onChanged(Change<? extends T> change) {
        while (change.next()) {
            if (change.wasPermutated()) {
                for (int i = change.getFrom(); i < change.getTo(); ++i) {
                    //permutate
                }
            } else if (change.wasUpdated()) {
                //update item
            } else {
                for (T removed : change.getRemoved()) {
                    onRemoved(removed);
                }
                for (T added : change.getAddedSubList()) {
                    onAdded(added);
                }
            }
        }
    }

    public abstract void onRemoved(T removed);

    public abstract void onAdded(T added);
}
