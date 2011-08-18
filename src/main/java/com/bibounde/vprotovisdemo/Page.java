package com.bibounde.vprotovisdemo;

import java.io.Serializable;

import com.vaadin.ui.Component;

public interface Page extends Serializable {

    Component getComponent();
    boolean validate();
}
