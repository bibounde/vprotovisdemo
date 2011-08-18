package com.bibounde.vprotovisdemo.dialog;

import org.vaadin.codelabel.CodeLabel;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class CodeDialog extends Window {

    private String src;

    public CodeDialog(String src) {
        super("Source code");
        this.src = src;
        this.initLayout();
    }

    private void initLayout() {
        
        this.setWidth("60%");
        this.setHeight("75%");
        
        VerticalLayout content = (VerticalLayout) this.getContent();
        content.setMargin(true);
        content.setSpacing(true);
        
        CodeLabel codeLabel = new CodeLabel(this.src);
        codeLabel.setSizeFull();
        content.addComponent(codeLabel);
        
    }

}
