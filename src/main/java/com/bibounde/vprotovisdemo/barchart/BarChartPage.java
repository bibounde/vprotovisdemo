package com.bibounde.vprotovisdemo.barchart;

import java.io.Serializable;
import java.util.List;

import com.bibounde.vprotovis.BarChartComponent;
import com.bibounde.vprotovis.chart.bar.Serie;
import com.bibounde.vprotovisdemo.ChartSample;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalSplitPanel;

public class BarChartPage implements ChartSample, Serializable {

    public static final String FQN = "BarChartComponent";
    
    private VerticalSplitPanel content;
    private DataPanel dataPanel;
    private ChartPanel chartPanel;
    
    public BarChartPage() {
        this.initLayout();
        this.initListener();
        this.renderChart();
    }
    
    private void initLayout() {
        
        this.content = new VerticalSplitPanel();
        
        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        this.content.addComponent(tabSheet);
        
        this.dataPanel = new DataPanel();
        tabSheet.addTab(this.dataPanel.getComponent(), "Data", null);
        
        this.chartPanel = new ChartPanel();
        this.content.addComponent(this.chartPanel.getComponent());
    }
    
    private void initListener() {
        this.chartPanel.getRenderButton().addListener(new ClickListener() {
            
            public void buttonClick(ClickEvent event) {
                renderChart();
            }
        });
    }
    
    private void renderChart() {
        List<Serie> series = this.dataPanel.getSeries();
        
        BarChartComponent chart = this.chartPanel.getChart();
        
        chart.clearData();
        chart.setGroupNames(this.dataPanel.getGroupNames());
        
        for (Serie serie : series) {
            chart.addSerie(serie.getName(), serie.getValues());
        }
        
        chart.requestRepaint();
    }
    
    public Component getComponent() {
        return this.content;
    }
}
