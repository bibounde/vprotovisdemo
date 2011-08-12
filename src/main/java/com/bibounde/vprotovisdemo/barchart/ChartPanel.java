package com.bibounde.vprotovisdemo.barchart;

import java.io.Serializable;

import com.bibounde.vprotovis.BarChartComponent;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class ChartPanel implements Serializable {

    private Panel content;
    private Button renderButton;
    private Button showSourceButton;
    private BarChartComponent chart;
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
        
        this.chart = new BarChartComponent();
        this.chartContent.addComponent(this.chart);
        
        //TODO: remove
        this.chart.setChartWidth(400);
        this.chart.setChartHeight(300);
    }

    public Component getComponent() {
        return this.content;
    }
    
    public BarChartComponent getChart() {
        return this.chart;
    }
    
    public Button getRenderButton() {
        return this.renderButton;
    }
}
