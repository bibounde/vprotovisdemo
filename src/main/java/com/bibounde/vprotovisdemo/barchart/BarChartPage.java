package com.bibounde.vprotovisdemo.barchart;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bibounde.vprotovis.BarChartComponent;
import com.bibounde.vprotovis.chart.bar.BarTooltipFormatter;
import com.bibounde.vprotovis.chart.bar.DefaultBarTooltipFormatter;
import com.bibounde.vprotovis.chart.bar.Serie;
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

public class BarChartPage implements Page {

    public static final String FQN = "BarChart";

    private static final String TAB_DIMENSIONS = "Dimensions";
    private static final String TAB_AXIS = "Grid";
    private static final String TAB_DATA = "Data";
    private static final String TAB_MISC = "Misc";

    private VerticalSplitPanel content;
    private DataPanel dataPanel;
    private DimensionPanel dimensionPanel;
    private ChartPanel chartPanel;
    private AxisPanel axisPanel;
    private MiscPanel miscPanel;

    private TabSheet tabSheet;
    private Map<String, Object> sourceCodeMap = new HashMap<String, Object>();

    public BarChartPage() {
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
                    Template tpl = configuration.getTemplate("BarChartComponentCode.ftl");
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

        BarChartComponent chart = this.chartPanel.getChart();
        this.sourceCodeMap.clear();

        chart.setGroupNames(this.dataPanel.getGroupNames());

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

        Double groupInset = this.dimensionPanel.getGroupInset();
        if (groupInset != null) {
            chart.setGroupInset(groupInset);
            this.sourceCodeMap.put("groupInset", groupInset);
        } else {
            chart.setGroupInset(25d);
        }

        Double barInset = this.dimensionPanel.getBarInset();
        if (barInset != null) {
            chart.setBarInset(barInset);
            this.sourceCodeMap.put("barInset", barInset);
        } else {
            chart.setBarInset(2d);
        }

        chart.setXAxisVisible(this.axisPanel.isXAxisEnabled());
        this.sourceCodeMap.put("xAxisVisible", this.axisPanel.isXAxisEnabled());
        
        chart.setXAxisLabelVisible(this.axisPanel.isXAxisLabelEnabled());
        this.sourceCodeMap.put("xAxisLabelVisible", this.axisPanel.isXAxisLabelEnabled());

        chart.setYAxisVisible(this.axisPanel.isYAxisEnabled());
        this.sourceCodeMap.put("yAxisVisible", this.axisPanel.isYAxisEnabled());
        
        chart.setYAxisLabelVisible(this.axisPanel.isYAxisLabelEnabled());
        this.sourceCodeMap.put("yAxisLabelVisible", this.axisPanel.isYAxisLabelEnabled());
        
        chart.setYAxisLabelStep(this.axisPanel.getYAxisLabelStep());
        this.sourceCodeMap.put("yAxisLabelStep", this.axisPanel.getYAxisLabelStep());
        
        chart.setYAxisGridVisible(this.axisPanel.isYAxisGridEnabled());
        this.sourceCodeMap.put("yAxisGridVisible", this.axisPanel.isYAxisGridEnabled());

        if (this.axisPanel.isYAxisCustomFormatter()) {
            chart.setYAxisLabelFormatter(new AxisLabelFormatter() {
                public String format(double labelValue) {
                    return String.valueOf(labelValue) + "\u20AC";
                }
            });
        } else {
            chart.setYAxisLabelFormatter(null);
        }
        this.sourceCodeMap.put("yAxisCustomFormatter", this.axisPanel.isYAxisCustomFormatter());

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
                chart.setTooltipFormatter(new BarTooltipFormatter() {

                    public String getTooltipHTML(String serieName, double value, String groupName) {

                        StringBuilder tooltipHTML = new StringBuilder();
                        tooltipHTML.append("<table border=0 cellpadding=2 ><tr><td valign=top>").append("<img src=\"");

                        String img = "/VAADIN/themes/vprotovisdemo/thumb_up.png";
                        if (value < 1000) {
                            img = "/VAADIN/themes/vprotovisdemo/thumb_down.png";
                        }
                        tooltipHTML.append(img);
                        tooltipHTML.append("\"></td><td>");
                        tooltipHTML.append("<b><i>").append(groupName).append("</i></b><br/>");
                        tooltipHTML.append(serieName).append(": ").append(value).append(" \u20AC");
                        tooltipHTML.append("</td><tr></table>");

                        return tooltipHTML.toString();
                    }
                });
            } else {
                chart.setTooltipFormatter(new DefaultBarTooltipFormatter());
            }
        } else {
            chart.setTooltipFormatter(null);
        }
        sourceCodeMap.put("tooltipEnabled", this.miscPanel.isTooltipEnabled());
        sourceCodeMap.put("tooltipCustomEnabled", this.miscPanel.isTooltipCustomEnabled());

        chart.requestRepaint();   
    }

    public Component getComponent() {
        return this.content;
    }

    public boolean validate() {
        return false;
    }
}
