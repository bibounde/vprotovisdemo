package com.bibounde.vprotovisdemo.piechart;

import com.bibounde.vprotovisdemo.Page;
import com.bibounde.vprotovisdemo.util.ColorValidator;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.validator.DoubleValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;

public class AxisPanel implements Page {

    private static final String ENABLED_OPTION = "enabled";
    private static final String DISABLED_OPTION = "disabled";
    private static final String CUSTOM_OPTION = "Custom";
    private static final String DEFAULT_OPTION = "Default";

    private GridLayout content;

    private NativeSelect labelBox, labelFormatterBox;
    private Label labelFormatterLabel;
    private Label labelColorLabel;
    private TextField labelColorText;

    public AxisPanel() {
        this.initLayout();
        this.initListener();
        this.initValidator();
        this.initValues();
    }

    private void initLayout() {
        this.content = new GridLayout(10, 3);
        this.content.setMargin(true);
        this.content.setSpacing(true);

        Label infoLabel = new Label("Labels : ");
        this.content.addComponent(infoLabel, 0, 0);
        this.content.setComponentAlignment(infoLabel, Alignment.MIDDLE_LEFT);

        this.labelBox = new NativeSelect();
        this.labelBox.setNullSelectionAllowed(false);
        this.labelBox.setWidth("110px");
        this.labelBox.addItem(ENABLED_OPTION);
        this.labelBox.addItem(DISABLED_OPTION);
        this.labelBox.setImmediate(true);
        this.content.addComponent(this.labelBox, 1, 0);
        this.content.setComponentAlignment(this.labelBox, Alignment.MIDDLE_LEFT);

        this.labelFormatterLabel = new Label("Formatter : ");
        this.content.addComponent(labelFormatterLabel, 2, 0);
        this.content.setComponentAlignment(labelFormatterLabel, Alignment.MIDDLE_LEFT);

        this.labelFormatterBox = new NativeSelect();
        this.labelFormatterBox.setNullSelectionAllowed(false);
        this.labelFormatterBox.setWidth("110px");
        this.labelFormatterBox.addItem(DEFAULT_OPTION);
        this.labelFormatterBox.addItem(CUSTOM_OPTION);
        this.labelFormatterBox.setImmediate(true);
        this.content.addComponent(this.labelFormatterBox, 3, 0);
        this.content.setComponentAlignment(this.labelFormatterBox, Alignment.MIDDLE_LEFT);

        this.labelColorLabel = new Label("Color : ");
        this.content.addComponent(labelColorLabel, 4, 0);
        this.content.setComponentAlignment(labelColorLabel, Alignment.MIDDLE_LEFT);
        
        this.labelColorText = new TextField();
        this.labelColorText.setWidth("110px");
        this.content.addComponent(this.labelColorText, 5, 0);
        this.content.setComponentAlignment(this.labelColorText, Alignment.MIDDLE_LEFT);
    }

    private void initListener() {

        this.labelBox.addListener(new ValueChangeListener() {

            public void valueChange(ValueChangeEvent event) {
                boolean enabled = ENABLED_OPTION.equals(event.getProperty().getValue());
                labelFormatterLabel.setEnabled(enabled);
                labelFormatterBox.setEnabled(enabled);
                labelColorLabel.setEnabled(enabled);
                labelColorText.setEnabled(enabled);
            }
        });
    }

    private void initValidator() {
        this.labelColorText.addValidator(new ColorValidator());
        this.labelColorText.setImmediate(true);
    }

    private void initValues() {
        this.labelBox.setValue(ENABLED_OPTION);
        this.labelFormatterBox.setValue(CUSTOM_OPTION);
        this.labelColorText.setValue("#FFFFFF");
    }
    
    public boolean validate() {
        try {
            this.labelColorText.validate();
            
            return true;
        } catch (Exception e) {
            // Do nothing. All is done in ui
            return false;
        }
    }

    public Component getComponent() {
        return this.content;
    }
    
    public boolean isLabelEnabled() {
        return ENABLED_OPTION.equals(this.labelBox.getValue());
    }
    
    public boolean isLabelCustomFormatter() {
        return this.labelFormatterBox.isEnabled() && CUSTOM_OPTION.equals(this.labelFormatterBox.getValue());
    }
    
    public String getLabelColor() {
        return this.labelColorText.getValue().equals("") ? null : (String) this.labelColorText.getValue();
    }
}
