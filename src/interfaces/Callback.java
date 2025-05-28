package interfaces;

import Model.User;

public interface Callback { // Changed from 'interface Callback' to 'public interface Callback'
    void execute(String view, User user);
}
