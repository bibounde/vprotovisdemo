package com.bibounde.vprotovisdemo.spiderchart;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bibounde.vprotovis.SpiderChartComponent;
import com.bibounde.vprotovis.chart.spider.DefaultSpiderTooltipFormatter;
import com.bibounde.vprotovis.chart.spider.Serie;
import com.bibounde.vprotovis.chart.spider.SpiderTooltipFormatter;
import com.bibounde.vprotovis.common.AxisLabelFormatter;
import com.bibounde.vprotovisdemo.Page;
import com.bibounde.vprotovisdemo.dialog.CodeDialog;
import com.bibounde.vprotovisdemo.util.RandomUtil;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window.Notification;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class SpiderChartPage implements Page {

    public static final String FQN = "SpiderChart";

    private static final String TAB_DIMENSIONS = "Dimensions";
    private static final String TAB_AXIS = "Grid";
    private static final String TAB_DATA = "Data";
    private static final String TAB_MISC = "Misc";

    private VerticalSplitPanel content;
    private DataPanel dataPanel;
    private DimensionPanel dimensionPanel;
    private AxisPanel axisPanel;
    private MiscPanel miscPanel;
    private ChartPanel chartPanel;

    private TabSheet tabSheet;
    private Map<String, Object> sourceCodeMap = new HashMap<String, Object>();
    
    public SpiderChartPage() {
        this.initLayout();
        this.initListener();
        this.renderChart(true);
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
                renderChart(false);
            }
        });
        
        this.chartPanel.getSourceButton().addListener(new ClickListener() {
            
            public void buttonClick(ClickEvent event) {
                try {
                    Configuration configuration = new Configuration();
                    configuration.setClassForTemplateLoading(getClass(), "/templates/");
                    Template tpl = configuration.getTemplate("SpiderChartComponentCode.ftl");
                    StringWriter sWriter = new StringWriter();
                    
                    tpl.process(sourceCodeMap, sWriter);
                    CodeDialog codeDialog = new CodeDialog(sWriter.toString());
                    content.getWindow().addWindow(codeDialog);
                    codeDialog.center();
                    
                } catch (IOException e) {
                    content.getWindow().showNotification("Configuration error", e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
                } catch (TemplateException e) {
                    content.getWindow().showNotification("Template error", e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
                }
            }
        });
    }
    
    private void renderChart(boolean firstRendering) {
        if (!firstRendering) {
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
        
        SpiderChartComponent chart = this.chartPanel.getChart();
        this.sourceCodeMap.clear();
        
        chart.setAxisNames(this.dataPanel.getAxis());
        this.sourceCodeMap.put("axisNames", this.dataPanel.getAxis());
        
        chart.clearSeries();
        for (Serie serie : series) {
            chart.addSerie(serie.getName(), serie.getValues());
        }
        this.sourceCodeMap.put("series", series);

        chart.setChartWidth(this.dimensionPanel.getChartWidth());
        chart.setChartHeight(this.dimensionPanel.getChartHeight());
        
        this.sourceCodeMap.put("chartWidth", this.dimensionPanel.getChartWidth());
        this.sourceCodeMap.put("chartHeight", this.dimensionPanel.getChartHeight());

        Double marginLeft = this.dimensionPanel.getMarginLeft();
        if (marginLeft != null) {
            chart.setMarginLeft(marginLeft);
            this.sourceCodeMap.put("marginLeft", marginLeft);
        } else {
            chart.setMarginLeft(10d);
        }

        Double marginRight = this.dimensionPanel.getMarginRight();
        if (marginRight != null) {
            chart.setMarginRight(marginRight);
            this.sourceCodeMap.put("marginRight", marginRight);
        } else {
            chart.setMarginRight(10d);
        }

        Double marginTop = this.dimensionPanel.getMarginTop();
        if (marginTop != null) {
            chart.setMarginTop(marginTop);
            this.sourceCodeMap.put("marginTop", marginTop);
        } else {
            chart.setMarginTop(10d);
        }

        Double marginBottom = this.dimensionPanel.getMarginBottom();
        if (marginBottom != null) {
            chart.setMarginBottom(marginBottom);
            this.sourceCodeMap.put("marginBottom", marginBottom);
        } else {
            chart.setMarginBottom(10d);
        }

        Integer lineWidth = this.dimensionPanel.getLineWidth();
        if (lineWidth != null) {
            chart.setLineWidth(lineWidth);
            this.sourceCodeMap.put("lineWidth", lineWidth);
        } else {
            chart.setLineWidth(1);
        }
        
        chart.setAxisVisible(this.axisPanel.isAxisEnabled());
        this.sourceCodeMap.put("axisVisible", this.axisPanel.isAxisEnabled());
        
        chart.setAxisLabelVisible(this.axisPanel.isAxisLabelEnabled());
        this.sourceCodeMap.put("axisLabelVisible", this.axisPanel.isAxisLabelEnabled());
        
        chart.setAxisLabelStep(this.axisPanel.getAxisLabelStep());
        this.sourceCodeMap.put("axisLabelStep", this.axisPanel.getAxisLabelStep());
        
        chart.setAxisCaptionVisible(this.axisPanel.isAxisCaptionEnabled());
        this.sourceCodeMap.put("axisCaptionVisible", this.axisPanel.isAxisCaptionEnabled());
        
        chart.setAxisGridVisible(this.axisPanel.isAxisGridEnabled());
        this.sourceCodeMap.put("axisGridVisible", this.axisPanel.isAxisGridEnabled());
        
        if (this.axisPanel.isAxisCustomFormatter()) {
            chart.setAxisLabelFormatter(new AxisLabelFormatter() {
                public String format(double labelValue) {
                    return String.valueOf(labelValue) + "\u20AC";
                }
            });
        } else {
            chart.setAxisLabelFormatter(null);
        }
        this.sourceCodeMap.put("axisCustomFormatter", this.axisPanel.isAxisCustomFormatter());
        
        if (this.miscPanel.isRandomColorSelected()) {
            String[] colors = RandomUtil.nextColors();
            chart.setColors(colors);
            this.sourceCodeMap.put("randomColors", colors);
        } else {
            chart.setColors(null);
        }
        this.sourceCodeMap.put("randomColorsSelected", this.miscPanel.isRandomColorSelected());

        if (this.miscPanel.isLegendEnabled()) {
            chart.setLegendVisible(true);
            chart.setLegendAreaWidth(this.miscPanel.getLegendAreaWidth());
            this.sourceCodeMap.put("legendAreaWidth", this.miscPanel.getLegendAreaWidth());
            
            Double legendInsetLeft = this.miscPanel.getLegendInsetLeft();
            if (legendInsetLeft != null) {
                chart.setLegendInsetLeft(legendInsetLeft);
                this.sourceCodeMap.put("legendInsetLeft", legendInsetLeft);
            } else {
                chart.setLegendInsetLeft(20d);
            }
        } else {
            chart.setLegendVisible(false);
        }
        this.sourceCodeMap.put("legendVisible", this.miscPanel.isLegendEnabled());
        
        chart.setTooltipEnabled(this.miscPanel.isTooltipEnabled());
        if (this.miscPanel.isTooltipEnabled()) {
            if (this.miscPanel.isTooltipCustomEnabled()) {
                chart.setTooltipFormatter(new SpiderTooltipFormatter() {

                    public String getTooltipHTML(String axisName, String serieName, double value) {

                        StringBuilder tooltipHTML = new StringBuilder();
                        tooltipHTML.append("<table border=0 cellpadding=2 ><tr><td valign=top>").append("<img src=\"");

                        String img = "/VAADIN/themes/vprotovisdemo/thumb_up.png";
                        if (value < 2.5) {
                            img = "/VAADIN/themes/vprotovisdemo/thumb_down.png";
                        }
                        tooltipHTML.append(img);
                        tooltipHTML.append("\"></td><td>");
                        tooltipHTML.append("<b><i>").append(axisName).append("</i></b><br/>");
                        tooltipHTML.append(serieName).append(": ").append(value).append(" \u20AC");
                        tooltipHTML.append("</td><tr></table>");

                        return tooltipHTML.toString();
                    }
                });
            } else {
                chart.setTooltipFormatter(new DefaultSpiderTooltipFormatter());
            }
        } else {
            chart.setTooltipFormatter(null);
        }
        sourceCodeMap.put("tooltipEnabled", this.miscPanel.isTooltipEnabled());
        sourceCodeMap.put("tooltipCustomEnabled", this.miscPanel.isTooltipCustomEnabled());
        
        if (this.miscPanel.isAreModeEnabled()) {
            chart.setAreaModeEnabled(true);
            
            Double areaOpacity = this.miscPanel.getAreaOpacity();
            if (areaOpacity != null) {
                chart.setAreaOpacity(areaOpacity);
                this.sourceCodeMap.put("areaOpacity", areaOpacity);
            } else {
                chart.setAreaOpacity(0.3d);
            }
        } else {
            chart.setAreaModeEnabled(false);
        }
        this.sourceCodeMap.put("areaModeEnabled", this.miscPanel.isAreModeEnabled());
        
        chart.requestRepaint();
    }
    
    public Component getComponent() {
        return this.content;
    }

    public boolean validate() {
        return true;
    }


}
