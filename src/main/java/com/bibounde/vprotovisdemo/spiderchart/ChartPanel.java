package com.bibounde.vprotovisdemo.spiderchart;

import com.bibounde.vprotovis.BarChartComponent;
import com.bibounde.vprotovis.SpiderChartComponent;
import com.bibounde.vprotovisdemo.Page;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class ChartPanel implements Page {

    private Panel content;
    private Button renderButton;
    private Button showSourceButton;
    private SpiderChartComponent chart;
    private Panel chartContent;
    
    public ChartPanel() {
        this.initLayout();
    }
    
    private void initLayout() {
        
        this.content = new Panel();
        this.content.setSizeFull();
        
        VerticalLayout mainLayout = (VerticalLayout) this.content.getContent();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        
        HorizontalLayout buttonLayout = new HorizontalLayout();
        this.content.addComponent(buttonLayout);
        buttonLayout.setSpacing(true);
        
        this.renderButton = new Button("Render chart !");
        this.renderButton.setIcon(new ThemeResource("chart_curve_go.png"));
        buttonLayout.addComponent(this.renderButton);
        
        this.showSourceButton = new Button();
        this.showSourceButton.setIcon(new ThemeResource("script_code.png"));
        this.showSourceButton.setDescription("Show source code");
        buttonLayout.addComponent(this.showSourceButton);
        
        this.chartContent = new Panel();
        this.content.addComponent(this.chartContent);
        
        this.chart = new SpiderChartComponent();
        this.chartContent.addComponent(this.chart);
    }

    public Component getComponent() {
        return this.content;
    }
    
    public SpiderChartComponent getChart() {
        return this.chart;
    }
    
    public Button getRenderButton() {
        return this.renderButton;
    }
    
    public Button getSourceButton() {
        return this.showSourceButton;
    }

    public boolean validate() {
        return false;
    }
}
