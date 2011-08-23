package com.bibounde.vprotovisdemo.action;

import com.vaadin.ui.Component;

public class ActionEvent {

    private Object source;
    private String command;
    
    public ActionEvent(Object source, String command) {
        this.source = source;
        this.command = command;
    }
    /**
     * @return the source
     */
    public Object getSource() {
        return source;
    }
    /**
     * @return the command
     */
    public String getCommand() {
        return command;
    }
}
