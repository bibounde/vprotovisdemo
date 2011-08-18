package com.bibounde.vprotovisdemo.linechart;

import java.util.List;

import com.bibounde.vprotovis.LineChartComponent;
import com.bibounde.vprotovis.chart.line.Serie;
import com.bibounde.vprotovis.common.AxisLabelFormatter;
import com.bibounde.vprotovisdemo.Page;
import com.bibounde.vprotovisdemo.util.RandomUtil;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.Notification;

public class LineChartPage implements Page {

    public static final String FQN = "LineChartComponent";
    
    private static final String TAB_DIMENSIONS = "Dimensions";
    private static final String TAB_AXIS = "Grid";
    private static final String TAB_DATA = "Data";
    private static final String TAB_MISC = "Misc";
    
    private VerticalSplitPanel content;
    private ChartPanel chartPanel;
    private DataPanel dataPanel;
    private AxisPanel axisPanel;
    private DimensionPanel dimensionPanel;
    private MiscPanel miscPanel;
    
    private TabSheet tabSheet;
    
    public LineChartPage() {
        this.initLayout();
        this.initListener();
        this.renderChart(false);
    }
    
    private void initLayout() {
        this.content = new VerticalSplitPanel();
        this.content.setSplitPosition(40);
        
        this.tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        this.content.addComponent(tabSheet);
        
        this.dimensionPanel = new DimensionPanel();
        tabSheet.addTab(this.dimensionPanel.getComponent(), TAB_DIMENSIONS, new ThemeResource("wrench.png"));
        
        this.dataPanel = new DataPanel();
        tabSheet.addTab(this.dataPanel.getComponent(), TAB_DATA, new ThemeResource("table.png"));
        
        this.axisPanel = new AxisPanel();
        tabSheet.addTab(this.axisPanel.getComponent(), TAB_AXIS, new ThemeResource("shape_align_middle.png"));
        
        this.miscPanel = new MiscPanel();
        tabSheet.addTab(this.miscPanel.getComponent(), TAB_MISC, new ThemeResource("palette.png"));
        
        this.chartPanel = new ChartPanel();
        this.content.addComponent(this.chartPanel.getComponent());
    }
    
    private void initListener() {
        this.chartPanel.getRenderButton().addListener(new ClickListener() {
            
            public void buttonClick(ClickEvent event) {
                renderChart(true);
            }
        });
    }
    
    private void renderChart(boolean validate) {
        
        if (validate) {
            if (!this.dimensionPanel.validate()) {
                this.content.getWindow().showNotification("Unable to render chart", "Dimension values are invalid.", Notification.TYPE_ERROR_MESSAGE);
                this.tabSheet.setSelectedTab(this.dimensionPanel.getComponent());
                return;
            } else if (!this.axisPanel.validate()) {
                this.content.getWindow().showNotification("Unable to render chart", "Axis values are invalid.", Notification.TYPE_ERROR_MESSAGE);
                this.tabSheet.setSelectedTab(this.axisPanel.getComponent());
                return;
            } else if (!this.miscPanel.validate()) {
                this.content.getWindow().showNotification("Unable to render chart", "Misc values are invalid.", Notification.TYPE_ERROR_MESSAGE);
                this.tabSheet.setSelectedTab(this.miscPanel.getComponent());
                return;
            }
        }
        
        List<Serie> series = this.dataPanel.getSeries();
        
        LineChartComponent chart = this.chartPanel.getChart();
        
        chart.clearSeries();
        for (Serie serie : series) {
            chart.addSerie(serie.getName(), serie.getValues());
        }
        
        chart.setChartWidth(this.dimensionPanel.getChartWidth());
        chart.setChartHeight(this.dimensionPanel.getChartHeight());
        
        Double marginLeft = this.dimensionPanel.getMarginLeft();
        if (marginLeft != null) {
            chart.setMarginLeft(marginLeft);
        } else {
            chart.setMarginLeft(10d);
        }
        
        Double marginRight = this.dimensionPanel.getMarginRight();
        if (marginRight != null) {
            chart.setMarginRight(marginRight);
        } else {
            chart.setMarginRight(10d);
        }
        
        Double marginTop = this.dimensionPanel.getMarginTop();
        if (marginTop != null) {
            chart.setMarginTop(marginTop);
        } else {
            chart.setMarginTop(10d);
        }
        
        Double marginBottom = this.dimensionPanel.getMarginBottom();
        if (marginBottom != null) {
            chart.setMarginBottom(marginBottom);
        } else {
            chart.setMarginBottom(10d);
        }
        
        Integer lineWidth = this.dimensionPanel.getLineWidth();
        if (lineWidth != null) {
            chart.setLineWidth(lineWidth);
        } else {
            chart.setLineWidth(1);
        }
        
        chart.setXAxisVisible(this.axisPanel.isXAxisEnabled());
        chart.setXAxisLabelVisible(this.axisPanel.isXAxisLabelEnabled());
        chart.setXAxisLabelStep(this.axisPanel.getXAxisLabelStep());
        chart.setXAxisGridVisible(this.axisPanel.isXAxisGridEnabled());

        if (this.axisPanel.isXAxisCustomFormatter()) {
            chart.setXAxisLabelFormatter(new AxisLabelFormatter() {
                public String format(double labelValue) {
                    return String.valueOf(labelValue) + "j.";
                }
            });
        } else {
            chart.setXAxisLabelFormatter(null);
        }
        
        chart.setYAxisVisible(this.axisPanel.isYAxisEnabled());
        chart.setYAxisLabelVisible(this.axisPanel.isYAxisLabelEnabled());
        chart.setYAxisLabelStep(this.axisPanel.getYAxisLabelStep());
        chart.setYAxisGridVisible(this.axisPanel.isYAxisGridEnabled());

        if (this.axisPanel.isYAxisCustomFormatter()) {
            chart.setYAxisLabelFormatter(new AxisLabelFormatter() {
                public String format(double labelValue) {
                    return String.valueOf(labelValue) + "\u20AC";
                }
            });
        } else {
            chart.setYAxisLabelFormatter(null);
        }
        
        if (this.miscPanel.isRandomColorSelected()) {
            chart.setColors(RandomUtil.nextColors());
        } else {
            chart.setColors(null);
        }

        if (this.miscPanel.isLegendEnabled()) {
            chart.setLegendVisible(true);
            chart.setLegendAreaWidth(this.miscPanel.getLegendAreaWidth());
        } else {
            chart.setLegendVisible(false);
        }
        
        chart.setInterpolationMode(this.miscPanel.getInterpolationMode());
        
        chart.requestRepaint();
    }
    

    public Component getComponent() {
        return this.content;
    }

    public boolean validate() {
        return false;
    }

}
