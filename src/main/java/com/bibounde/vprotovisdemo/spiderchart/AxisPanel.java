package com.bibounde.vprotovisdemo.spiderchart;

import com.bibounde.vprotovisdemo.Page;
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

    private NativeSelect axisBox, axisLabelBox, axisGridBox, axisFormatterBox, axisCaptionBox;
    private TextField axisStepText;
    private Label axisInfoLabel;
    private Label axisStepLabel;
    private Label axisGridLabel;
    private Label axisFormatterLabel;
    private Label axisCaptionLabel;

    public AxisPanel() {
        this.initLayout();
        this.initListener();
        this.initValidator();
        this.initValues();
    }

    private void initLayout() {
        this.content = new GridLayout(6, 3);
        this.content.setMargin(true);
        this.content.setSpacing(true);

        Label axisLabel = new Label("Axis : ");
        this.content.addComponent(axisLabel, 0, 0);
        this.content.setComponentAlignment(axisLabel, Alignment.MIDDLE_LEFT);

        this.axisBox = new NativeSelect();
        this.axisBox.setNullSelectionAllowed(false);
        this.axisBox.setWidth("110px");
        this.axisBox.addItem(ENABLED_OPTION);
        this.axisBox.addItem(DISABLED_OPTION);
        this.axisBox.setImmediate(true);
        this.content.addComponent(this.axisBox, 1, 0);
        this.content.setComponentAlignment(this.axisBox, Alignment.MIDDLE_LEFT);
        
        this.axisCaptionLabel = new Label("Caption : ");
        this.content.addComponent(this.axisCaptionLabel, 2, 0);
        this.content.setComponentAlignment(this.axisCaptionLabel, Alignment.MIDDLE_LEFT);

        this.axisCaptionBox = new NativeSelect();
        this.axisCaptionBox.setNullSelectionAllowed(false);
        this.axisCaptionBox.setWidth("110px");
        this.axisCaptionBox.addItem(ENABLED_OPTION);
        this.axisCaptionBox.addItem(DISABLED_OPTION);
        this.axisCaptionBox.setImmediate(true);
        this.content.addComponent(this.axisCaptionBox, 3, 0);
        this.content.setComponentAlignment(this.axisCaptionBox, Alignment.MIDDLE_LEFT);

        this.axisInfoLabel = new Label("Label : ");
        this.content.addComponent(axisInfoLabel, 4, 0);
        this.content.setComponentAlignment(axisInfoLabel, Alignment.MIDDLE_LEFT);

        this.axisLabelBox = new NativeSelect();
        this.axisLabelBox.setNullSelectionAllowed(false);
        this.axisLabelBox.setWidth("110px");
        this.axisLabelBox.addItem(ENABLED_OPTION);
        this.axisLabelBox.addItem(DISABLED_OPTION);
        this.axisLabelBox.setImmediate(true);
        this.content.addComponent(this.axisLabelBox, 5, 0);
        this.content.setComponentAlignment(this.axisLabelBox, Alignment.MIDDLE_LEFT);

        this.axisStepLabel = new Label("Step : ");
        this.content.addComponent(axisStepLabel, 0, 1);
        this.content.setComponentAlignment(axisStepLabel, Alignment.MIDDLE_LEFT);

        this.axisStepText = new TextField();
        this.axisStepText.setWidth("110px");
        this.axisStepText.setImmediate(true);
        this.content.addComponent(this.axisStepText, 1, 1);
        this.content.setComponentAlignment(this.axisStepText, Alignment.MIDDLE_LEFT);

        this.axisFormatterLabel = new Label("Formatter : ");
        this.content.addComponent(axisFormatterLabel, 2, 1);
        this.content.setComponentAlignment(axisFormatterLabel, Alignment.MIDDLE_LEFT);

        this.axisFormatterBox = new NativeSelect();
        this.axisFormatterBox.setNullSelectionAllowed(false);
        this.axisFormatterBox.setWidth("110px");
        this.axisFormatterBox.addItem(DEFAULT_OPTION);
        this.axisFormatterBox.addItem(CUSTOM_OPTION);
        this.axisFormatterBox.setImmediate(true);
        this.content.addComponent(this.axisFormatterBox, 3, 1);
        this.content.setComponentAlignment(this.axisFormatterBox, Alignment.MIDDLE_LEFT);
        
        this.axisGridLabel = new Label("Grid : ");
        this.content.addComponent(axisGridLabel, 4, 1);
        this.content.setComponentAlignment(axisGridLabel, Alignment.MIDDLE_LEFT);

        this.axisGridBox = new NativeSelect();
        this.axisGridBox.setNullSelectionAllowed(false);
        this.axisGridBox.setWidth("110px");
        this.axisGridBox.addItem(ENABLED_OPTION);
        this.axisGridBox.addItem(DISABLED_OPTION);
        this.axisGridBox.setImmediate(true);
        this.content.addComponent(this.axisGridBox, 5, 1);
        this.content.setComponentAlignment(this.axisGridBox, Alignment.MIDDLE_LEFT);

    }

    private void initListener() {

        this.axisBox.addListener(new ValueChangeListener() {

            public void valueChange(ValueChangeEvent event) {
                boolean enabled = ENABLED_OPTION.equals(event.getProperty().getValue());
                axisInfoLabel.setEnabled(enabled);
                
                axisCaptionBox.setEnabled(enabled);
                axisCaptionLabel.setEnabled(enabled);
                
                axisLabelBox.setEnabled(enabled);

                boolean axisLabelSelected = ENABLED_OPTION.equals(axisLabelBox.getValue());
                axisStepLabel.setEnabled(enabled & axisLabelSelected);
                axisStepText.setEnabled(enabled & axisLabelSelected);
                axisGridLabel.setEnabled(enabled & axisLabelSelected);
                axisGridBox.setEnabled(enabled & axisLabelSelected);
                axisFormatterLabel.setEnabled(enabled & axisLabelSelected);
                axisFormatterBox.setEnabled(enabled & axisLabelSelected);
                
                
            }
        });

        this.axisLabelBox.addListener(new ValueChangeListener() {

            public void valueChange(ValueChangeEvent event) {
                boolean enabled = ENABLED_OPTION.equals(event.getProperty().getValue());

                axisStepLabel.setEnabled(enabled);
                axisStepText.setEnabled(enabled);
                axisGridLabel.setEnabled(enabled);
                axisGridBox.setEnabled(enabled);
                axisFormatterLabel.setEnabled(enabled);
                axisFormatterBox.setEnabled(enabled);
            }
        });
    }

    private void initValidator() {
        this.axisStepText.setRequiredError("Step must be specified");
        axisStepText.setRequired(true);
        axisStepText.addValidator(new DoubleValidator("Value must be a number"));
    }

    private void initValues() {
        this.axisBox.setValue(ENABLED_OPTION);
        this.axisLabelBox.setValue(ENABLED_OPTION);
        this.axisStepText.setValue("1");
        this.axisGridBox.setValue(ENABLED_OPTION);
        this.axisFormatterBox.setValue(DEFAULT_OPTION);
        this.axisCaptionBox.setValue(ENABLED_OPTION);
    }
    
    public boolean validate() {
        try {
            if (ENABLED_OPTION.equals(this.axisLabelBox.getValue())) {
                this.axisStepText.validate();
            }
            
            return true;
        } catch (Exception e) {
            //Do nothing. All is done in ui
            return false;
        }
    }

    public Component getComponent() {
        return this.content;
    }
    
    public boolean isAxisEnabled() {
        return ENABLED_OPTION.equals(this.axisBox.getValue());
    }
    
    public boolean isAxisLabelEnabled() {
        return this.axisLabelBox.isEnabled() && ENABLED_OPTION.equals(this.axisLabelBox.getValue());
    }
    
    public Double getAxisLabelStep() {
        return Double.valueOf((String) this.axisStepText.getValue());
    }
    
    public boolean isAxisGridEnabled() {
        return this.axisGridBox.isEnabled() && ENABLED_OPTION.equals(this.axisGridBox.getValue());
    }
    
    public boolean isAxisCustomFormatter() {
        return this.axisFormatterBox.isEnabled() && CUSTOM_OPTION.equals(this.axisFormatterBox.getValue());
    }
    
    public boolean isAxisCaptionEnabled() {
        return this.axisCaptionBox.isEnabled() && ENABLED_OPTION.equals(this.axisCaptionBox.getValue());
    }
}
