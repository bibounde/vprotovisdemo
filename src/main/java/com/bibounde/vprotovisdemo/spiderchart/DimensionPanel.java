package com.bibounde.vprotovisdemo.spiderchart;

import com.bibounde.vprotovisdemo.Page;
import com.vaadin.data.validator.DoubleValidator;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public class DimensionPanel implements Page {

    private GridLayout content;
    private TextField chartWidthText, chartHeightText, marginLeftText, marginTopText, marginBottomText, marginRightText, lineWidthText;
    
    public DimensionPanel() {
        this.initLayout();
        this.initValidator();
        this.initValues();
    }

    private void initLayout() {
        this.content = new GridLayout(8, 3);
        this.content.setMargin(true);
        this.content.setSpacing(true);
        
        Label chartWidthLabel = new Label("Width : ");
        this.content.addComponent(chartWidthLabel, 0, 0);
        this.content.setComponentAlignment(chartWidthLabel, Alignment.MIDDLE_LEFT);

        this.chartWidthText = new TextField();
        this.chartWidthText.setWidth("110px");
        this.content.addComponent(this.chartWidthText, 1, 0);
        this.content.setComponentAlignment(this.chartWidthText, Alignment.MIDDLE_LEFT);

        Label chartHeightLabel = new Label("Height : ");
        this.content.addComponent(chartHeightLabel, 2, 0);
        this.content.setComponentAlignment(chartHeightLabel, Alignment.MIDDLE_LEFT);

        this.chartHeightText = new TextField();
        this.chartHeightText.setWidth("110px");
        this.content.addComponent(this.chartHeightText, 3, 0);
        this.content.setComponentAlignment(this.chartHeightText, Alignment.MIDDLE_LEFT);

        Label marginTopLabel = new Label("Margin top : ");
        this.content.addComponent(marginTopLabel, 0, 1);
        this.content.setComponentAlignment(marginTopLabel, Alignment.MIDDLE_LEFT);

        this.marginTopText = new TextField();
        this.marginTopText.setWidth("110px");
        this.content.addComponent(this.marginTopText, 1, 1);
        this.content.setComponentAlignment(this.marginTopText, Alignment.MIDDLE_LEFT);
        
        Label marginLeftLabel = new Label("Margin left : ");
        this.content.addComponent(marginLeftLabel, 2, 1);
        this.content.setComponentAlignment(marginLeftLabel, Alignment.MIDDLE_LEFT);

        this.marginLeftText = new TextField();
        this.marginLeftText.setWidth("110px");
        this.content.addComponent(this.marginLeftText, 3, 1);
        this.content.setComponentAlignment(this.marginLeftText, Alignment.MIDDLE_LEFT);

        Label marginBottomLabel = new Label("Margin bottom : ");
        this.content.addComponent(marginBottomLabel, 4, 1);
        this.content.setComponentAlignment(marginBottomLabel, Alignment.MIDDLE_LEFT);

        this.marginBottomText = new TextField();
        this.marginBottomText.setWidth("110px");
        this.content.addComponent(this.marginBottomText, 5, 1);
        this.content.setComponentAlignment(this.marginBottomText, Alignment.MIDDLE_LEFT);
        
        Label marginRightLabel = new Label("Margin right : ");
        this.content.addComponent(marginRightLabel, 6, 1);
        this.content.setComponentAlignment(marginRightLabel, Alignment.MIDDLE_LEFT);

        this.marginRightText = new TextField();
        this.marginRightText.setWidth("110px");
        this.content.addComponent(this.marginRightText, 7, 1);
        this.content.setComponentAlignment(this.marginRightText, Alignment.MIDDLE_LEFT);
        
        Label lineWidthLabel = new Label("Line width : ");
        this.content.addComponent(lineWidthLabel, 0, 2);
        this.content.setComponentAlignment(lineWidthLabel, Alignment.MIDDLE_LEFT);

        this.lineWidthText = new TextField();
        this.lineWidthText.setWidth("110px");
        this.content.addComponent(this.lineWidthText, 1, 2);
        this.content.setComponentAlignment(this.lineWidthText, Alignment.MIDDLE_LEFT);

    }
    
    private void initValidator() {
        DoubleValidator doubleValidator = new DoubleValidator("Value must be a number");
        
        this.chartWidthText.setRequired(true);
        this.chartWidthText.setRequiredError("Chart width must be specified");
        this.chartWidthText.addValidator(doubleValidator);
        this.chartWidthText.setImmediate(true);
        
        this.chartHeightText.setRequired(true);
        this.chartHeightText.setRequiredError("Chart height must be specified");
        this.chartHeightText.addValidator(doubleValidator);
        this.chartHeightText.setImmediate(true);
        
        this.marginLeftText.addValidator(doubleValidator);
        this.marginLeftText.setImmediate(true);
        this.marginTopText.addValidator(doubleValidator);
        this.marginTopText.setImmediate(true);
        this.marginBottomText.addValidator(doubleValidator);
        this.marginBottomText.setImmediate(true);
        this.marginRightText.addValidator(doubleValidator);
        this.marginRightText.setImmediate(true);
        
        this.lineWidthText.addValidator(new IntegerValidator("Value must be an integer"));
        this.lineWidthText.setImmediate(true);
    }
    
    public boolean validate() {
        try {
            this.chartWidthText.validate();
            this.chartHeightText.validate();
            this.marginLeftText.validate();
            this.marginRightText.validate();
            this.marginBottomText.validate();
            this.marginTopText.validate();
            
            return true;
        } catch (Exception e) {
            // Do nothing. All is done in ui
            return false;
        }
    }
    
    private void initValues() {
        this.chartWidthText.setValue("450");
        this.chartHeightText.setValue("300");
        this.marginLeftText.setValue("40");
        this.marginTopText.setValue("20");
        this.marginBottomText.setValue("20");
    }
    
    public Component getComponent() {
        return this.content;
    }
    
    public Double getChartWidth() {
        return Double.valueOf((String) this.chartWidthText.getValue());
    }
    
    public Double getChartHeight() {
        return Double.valueOf((String) this.chartHeightText.getValue());
    }
    
    public Double getMarginLeft() {
        return this.marginLeftText.getValue().equals("") ? null : Double.valueOf((String) this.marginLeftText.getValue());
    }
    
    public Double getMarginRight() {
        return this.marginRightText.getValue().equals("") ? null : Double.valueOf((String) this.marginRightText.getValue());
    }
    
    public Double getMarginTop() {
        return this.marginTopText.getValue().equals("") ? null : Double.valueOf((String) this.marginTopText.getValue());
    }
    
    public Double getMarginBottom() {
        return this.marginBottomText.getValue().equals("") ? null : Double.valueOf((String) this.marginBottomText.getValue());
    }
    
    public Integer getLineWidth() {
        return this.lineWidthText.getValue().equals("") ? null : Integer.valueOf((String) this.lineWidthText.getValue());
    }
}
