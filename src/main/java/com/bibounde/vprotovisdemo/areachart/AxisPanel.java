package com.bibounde.vprotovisdemo.areachart;

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

    private NativeSelect xAxisBox, xAxisLabelBox, xAxisGridBox, xAxisFormatterBox, yAxisBox, yAxisLabelBox, yAxisGridBox, yAxisFormatterBox;
    private TextField xAxisStepText, yAxisStepText;
    private Label xAxisInfoLabel;
    private Label xAxisStepLabel;
    private Label xAxisGridLabel;
    private Label xAxisFormatterLabel;
    private Label yAxisInfoLabel;
    private Label yAxisStepLabel;
    private Label yAxisGridLabel;
    private Label yAxisFormatterLabel;

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

        Label xAxisLabel = new Label("X axis : ");
        this.content.addComponent(xAxisLabel, 0, 0);
        this.content.setComponentAlignment(xAxisLabel, Alignment.MIDDLE_LEFT);

        this.xAxisBox = new NativeSelect();
        this.xAxisBox.setNullSelectionAllowed(false);
        this.xAxisBox.setWidth("110px");
        this.xAxisBox.addItem(ENABLED_OPTION);
        this.xAxisBox.addItem(DISABLED_OPTION);
        this.xAxisBox.setImmediate(true);
        this.content.addComponent(this.xAxisBox, 1, 0);
        this.content.setComponentAlignment(this.xAxisBox, Alignment.MIDDLE_LEFT);

        this.xAxisInfoLabel = new Label("X axis label : ");
        this.content.addComponent(xAxisInfoLabel, 2, 0);
        this.content.setComponentAlignment(xAxisInfoLabel, Alignment.MIDDLE_LEFT);

        this.xAxisLabelBox = new NativeSelect();
        this.xAxisLabelBox.setNullSelectionAllowed(false);
        this.xAxisLabelBox.setWidth("110px");
        this.xAxisLabelBox.addItem(ENABLED_OPTION);
        this.xAxisLabelBox.addItem(DISABLED_OPTION);
        this.xAxisLabelBox.setImmediate(true);
        this.content.addComponent(this.xAxisLabelBox, 3, 0);
        this.content.setComponentAlignment(this.xAxisLabelBox, Alignment.MIDDLE_LEFT);

        this.xAxisStepLabel = new Label("Step : ");
        this.content.addComponent(xAxisStepLabel, 4, 0);
        this.content.setComponentAlignment(xAxisStepLabel, Alignment.MIDDLE_LEFT);

        this.xAxisStepText = new TextField();
        this.xAxisStepText.setWidth("110px");
        this.xAxisStepText.setImmediate(true);
        this.content.addComponent(this.xAxisStepText, 5, 0);
        this.content.setComponentAlignment(this.xAxisStepText, Alignment.MIDDLE_LEFT);

        this.xAxisFormatterLabel = new Label("Formatter : ");
        this.content.addComponent(xAxisFormatterLabel, 6, 0);
        this.content.setComponentAlignment(xAxisFormatterLabel, Alignment.MIDDLE_LEFT);

        this.xAxisFormatterBox = new NativeSelect();
        this.xAxisFormatterBox.setNullSelectionAllowed(false);
        this.xAxisFormatterBox.setWidth("110px");
        this.xAxisFormatterBox.addItem(DEFAULT_OPTION);
        this.xAxisFormatterBox.addItem(CUSTOM_OPTION);
        this.xAxisFormatterBox.setImmediate(true);
        this.content.addComponent(this.xAxisFormatterBox, 7, 0);
        this.content.setComponentAlignment(this.xAxisFormatterBox, Alignment.MIDDLE_LEFT);
        
        this.xAxisGridLabel = new Label("Grid : ");
        this.content.addComponent(xAxisGridLabel, 8, 0);
        this.content.setComponentAlignment(xAxisGridLabel, Alignment.MIDDLE_LEFT);

        this.xAxisGridBox = new NativeSelect();
        this.xAxisGridBox.setNullSelectionAllowed(false);
        this.xAxisGridBox.setWidth("110px");
        this.xAxisGridBox.addItem(ENABLED_OPTION);
        this.xAxisGridBox.addItem(DISABLED_OPTION);
        this.xAxisGridBox.setImmediate(true);
        this.content.addComponent(this.xAxisGridBox, 9, 0);
        this.content.setComponentAlignment(this.xAxisGridBox, Alignment.MIDDLE_LEFT);

        Label yAxisLabel = new Label("Y axis : ");
        this.content.addComponent(yAxisLabel, 0, 1);
        this.content.setComponentAlignment(yAxisLabel, Alignment.MIDDLE_LEFT);

        this.yAxisBox = new NativeSelect();
        this.yAxisBox.setNullSelectionAllowed(false);
        this.yAxisBox.setWidth("110px");
        this.yAxisBox.addItem(ENABLED_OPTION);
        this.yAxisBox.addItem(DISABLED_OPTION);
        this.yAxisBox.setImmediate(true);
        this.content.addComponent(this.yAxisBox, 1, 1);
        this.content.setComponentAlignment(this.yAxisBox, Alignment.MIDDLE_LEFT);

        this.yAxisInfoLabel = new Label("Label : ");
        this.content.addComponent(yAxisInfoLabel, 2, 1);
        this.content.setComponentAlignment(yAxisInfoLabel, Alignment.MIDDLE_LEFT);

        this.yAxisLabelBox = new NativeSelect();
        this.yAxisLabelBox.setNullSelectionAllowed(false);
        this.yAxisLabelBox.setWidth("110px");
        this.yAxisLabelBox.addItem(ENABLED_OPTION);
        this.yAxisLabelBox.addItem(DISABLED_OPTION);
        this.yAxisLabelBox.setImmediate(true);
        this.content.addComponent(this.yAxisLabelBox, 3, 1);
        this.content.setComponentAlignment(this.yAxisLabelBox, Alignment.MIDDLE_LEFT);

        this.yAxisStepLabel = new Label("Step : ");
        this.content.addComponent(yAxisStepLabel, 4, 1);
        this.content.setComponentAlignment(yAxisStepLabel, Alignment.MIDDLE_LEFT);

        this.yAxisStepText = new TextField();
        this.yAxisStepText.setWidth("110px");
        this.yAxisStepText.setImmediate(true);
        this.content.addComponent(this.yAxisStepText, 5, 1);
        this.content.setComponentAlignment(this.yAxisStepText, Alignment.MIDDLE_LEFT);
        
        this.yAxisFormatterLabel = new Label("Formatter : ");
        this.content.addComponent(yAxisFormatterLabel, 6, 1);
        this.content.setComponentAlignment(yAxisFormatterLabel, Alignment.MIDDLE_LEFT);

        this.yAxisFormatterBox = new NativeSelect();
        this.yAxisFormatterBox.setNullSelectionAllowed(false);
        this.yAxisFormatterBox.setWidth("110px");
        this.yAxisFormatterBox.addItem(DEFAULT_OPTION);
        this.yAxisFormatterBox.addItem(CUSTOM_OPTION);
        this.yAxisFormatterBox.setImmediate(true);
        this.content.addComponent(this.yAxisFormatterBox, 7, 1);
        this.content.setComponentAlignment(this.yAxisFormatterBox, Alignment.MIDDLE_LEFT);

        this.yAxisGridLabel = new Label("Grid : ");
        this.content.addComponent(yAxisGridLabel, 8, 1);
        this.content.setComponentAlignment(yAxisGridLabel, Alignment.MIDDLE_LEFT);

        this.yAxisGridBox = new NativeSelect();
        this.yAxisGridBox.setNullSelectionAllowed(false);
        this.yAxisGridBox.setWidth("110px");
        this.yAxisGridBox.addItem(ENABLED_OPTION);
        this.yAxisGridBox.addItem(DISABLED_OPTION);
        this.yAxisGridBox.setImmediate(true);
        this.content.addComponent(this.yAxisGridBox, 9, 1);
        this.content.setComponentAlignment(this.yAxisGridBox, Alignment.MIDDLE_LEFT);
    }

    private void initListener() {

        this.xAxisBox.addListener(new ValueChangeListener() {

            public void valueChange(ValueChangeEvent event) {
                boolean enabled = ENABLED_OPTION.equals(event.getProperty().getValue());
                xAxisInfoLabel.setEnabled(enabled);
                xAxisLabelBox.setEnabled(enabled);

                boolean xAxisLabelSelected = ENABLED_OPTION.equals(xAxisLabelBox.getValue());
                xAxisStepLabel.setEnabled(enabled & xAxisLabelSelected);
                xAxisStepText.setEnabled(enabled & xAxisLabelSelected);
                xAxisGridLabel.setEnabled(enabled & xAxisLabelSelected);
                xAxisGridBox.setEnabled(enabled & xAxisLabelSelected);
                xAxisFormatterLabel.setEnabled(enabled & xAxisLabelSelected);
                xAxisFormatterBox.setEnabled(enabled & xAxisLabelSelected);
            }
        });

        this.xAxisLabelBox.addListener(new ValueChangeListener() {

            public void valueChange(ValueChangeEvent event) {
                boolean enabled = ENABLED_OPTION.equals(event.getProperty().getValue());

                xAxisStepLabel.setEnabled(enabled);
                xAxisStepText.setEnabled(enabled);
                xAxisGridLabel.setEnabled(enabled);
                xAxisGridBox.setEnabled(enabled);
                xAxisFormatterLabel.setEnabled(enabled);
                xAxisFormatterBox.setEnabled(enabled);
            }
        });

        this.yAxisBox.addListener(new ValueChangeListener() {

            public void valueChange(ValueChangeEvent event) {
                boolean enabled = ENABLED_OPTION.equals(event.getProperty().getValue());
                yAxisInfoLabel.setEnabled(enabled);
                yAxisLabelBox.setEnabled(enabled);

                boolean yAxisLabelSelected = ENABLED_OPTION.equals(yAxisLabelBox.getValue());
                yAxisStepLabel.setEnabled(enabled & yAxisLabelSelected);
                yAxisStepText.setEnabled(enabled & yAxisLabelSelected);
                yAxisGridLabel.setEnabled(enabled & yAxisLabelSelected);
                yAxisGridBox.setEnabled(enabled & yAxisLabelSelected);
                yAxisFormatterLabel.setEnabled(enabled & yAxisLabelSelected);
                yAxisFormatterBox.setEnabled(enabled & yAxisLabelSelected);
            }
        });

        this.yAxisLabelBox.addListener(new ValueChangeListener() {

            public void valueChange(ValueChangeEvent event) {
                boolean enabled = ENABLED_OPTION.equals(event.getProperty().getValue());

                yAxisStepLabel.setEnabled(enabled);
                yAxisStepText.setEnabled(enabled);
                yAxisGridLabel.setEnabled(enabled);
                yAxisGridBox.setEnabled(enabled);
                yAxisFormatterLabel.setEnabled(enabled);
                yAxisFormatterBox.setEnabled(enabled);
            }
        });
    }

    private void initValidator() {
        this.yAxisStepText.setRequiredError("Step must be specified");
        yAxisStepText.setRequired(true);
        
        this.yAxisStepText.addValidator(new DoubleValidator("Value must be a number"));

        this.xAxisStepText.setRequiredError("Step must be specified");
        xAxisStepText.setRequired(true);
        
        this.xAxisStepText.addValidator(new DoubleValidator("Value must be a number"));
    }

    private void initValues() {
        this.xAxisBox.setValue(ENABLED_OPTION);
        this.xAxisLabelBox.setValue(ENABLED_OPTION);
        this.xAxisStepText.setValue("0.5");
        this.xAxisGridBox.setValue(ENABLED_OPTION);
        this.xAxisFormatterBox.setValue(DEFAULT_OPTION);
        this.yAxisBox.setValue(ENABLED_OPTION);
        this.yAxisLabelBox.setValue(ENABLED_OPTION);
        this.yAxisStepText.setValue("300");
        this.yAxisGridBox.setValue(ENABLED_OPTION);
        this.yAxisFormatterBox.setValue(DEFAULT_OPTION);    
    }
    
    public boolean validate() {
        try {
            if (ENABLED_OPTION.equals(this.xAxisLabelBox.getValue())) {
                this.xAxisStepText.validate();
            }
            
            if (ENABLED_OPTION.equals(this.yAxisLabelBox.getValue())) {
                this.yAxisStepText.validate();
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
    
    public boolean isXAxisEnabled() {
        return ENABLED_OPTION.equals(this.xAxisBox.getValue());
    }
    
    public boolean isXAxisLabelEnabled() {
        return this.xAxisLabelBox.isEnabled() && ENABLED_OPTION.equals(this.xAxisLabelBox.getValue());
    }
    
    public Double getXAxisLabelStep() {
        return Double.valueOf((String) this.xAxisStepText.getValue());
    }
    
    public boolean isXAxisGridEnabled() {
        return this.xAxisGridBox.isEnabled() && ENABLED_OPTION.equals(this.xAxisGridBox.getValue());
    }
    
    public boolean isXAxisCustomFormatter() {
        return this.xAxisFormatterBox.isEnabled() && CUSTOM_OPTION.equals(this.xAxisFormatterBox.getValue());
    }
    
    public boolean isYAxisEnabled() {
        return ENABLED_OPTION.equals(this.yAxisBox.getValue());
    }
    
    public boolean isYAxisLabelEnabled() {
        return this.yAxisLabelBox.isEnabled() && ENABLED_OPTION.equals(this.yAxisLabelBox.getValue());
    }
    
    public Double getYAxisLabelStep() {
        return Double.valueOf((String) this.yAxisStepText.getValue());
    }
    
    public boolean isYAxisGridEnabled() {
        return this.yAxisGridBox.isEnabled() && ENABLED_OPTION.equals(this.yAxisGridBox.getValue());
    }
    
    public boolean isYAxisCustomFormatter() {
        return this.yAxisFormatterBox.isEnabled() && CUSTOM_OPTION.equals(this.yAxisFormatterBox.getValue());
    }
}
