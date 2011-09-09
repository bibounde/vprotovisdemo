package com.bibounde.vprotovisdemo.piechart;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bibounde.vprotovis.PieChartComponent;
import com.bibounde.vprotovis.chart.pie.DefaultPieLabelFormatter;
import com.bibounde.vprotovis.chart.pie.DefaultPieTooltipFormatter;
import com.bibounde.vprotovis.chart.pie.PieLabelFormatter;
import com.bibounde.vprotovis.chart.pie.PieTooltipFormatter;
import com.bibounde.vprotovis.chart.pie.Serie;
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

public class PieChartPage implements Page {

    public static final String FQN = "PieChart";
    
    private static final String TAB_DIMENSIONS = "Dimensions";
    private static final String TAB_AXIS = "Grid";
    private static final String TAB_DATA = "Data";
    private static final String TAB_MISC = "Misc";
    
    private VerticalSplitPanel content;
    private DataPanel dataPanel;
    private DimensionPanel dimensionPanel;
    private AxisPanel axisPanel;
    private MiscPanel miscPanel;
    
    private TabSheet tabSheet;
    private Map<String, Object> sourceCodeMap = new HashMap<String, Object>();

    private ChartPanel chartPanel;
    
    public PieChartPage() {
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
                    Template tpl = configuration.getTemplate("PieChartComponentCode.ftl");
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
            } else if (!this.miscPanel.validate()) {
                this.content.getWindow().showNotification("Unable to render chart", "Misc values are invalid.", Notification.TYPE_ERROR_MESSAGE);
                this.tabSheet.setSelectedTab(this.miscPanel.getComponent());
                return;
            }
        }
        
        List<Serie> series = this.dataPanel.getSeries();
        
        PieChartComponent chart = this.chartPanel.getChart();
        this.sourceCodeMap.clear();
        
        chart.clearSeries();
        double total = 0d;
        for (Serie serie : series) {
            chart.addSerie(serie.getName(), serie.getValue(), serie.isHighlight());
            total += serie.getValue();
        }
        this.sourceCodeMap.put("series", series);
        this.sourceCodeMap.put("total", total);
        
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
        
        Double highlightOffset = this.dimensionPanel.getHighlightOffset();
        if (highlightOffset != null) {
            chart.setHighlightOffset(highlightOffset);
            this.sourceCodeMap.put("highlightOffset", highlightOffset);
        } else {
            chart.setHighlightOffset(10d);
        }
        
        Integer lineWidth = this.dimensionPanel.getLineWidth();
        if (lineWidth != null) {
            chart.setLineWidth(lineWidth);
            this.sourceCodeMap.put("lineWidth", lineWidth);
        } else {
            chart.setLineWidth(0);
        }
        
        String lineColor = this.dimensionPanel.getLineColor();
        if (lineColor != null) {
            chart.setLineColor(lineColor);
            this.sourceCodeMap.put("lineColor", lineColor);
        } else {
            chart.setLineColor("#FFFFFF");
        }
        
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
            boolean permanent = this.miscPanel.isPermanentTooltip();
            this.sourceCodeMap.put("permanentTooltip", permanent);
            if (this.miscPanel.isTooltipCustomEnabled()) {
                final double tooltipTotal = total;
                chart.setTooltipFormatter(new PieTooltipFormatter() {

                    public boolean isVisible(String serieName, double value) {
                        return 0.05d <= value/tooltipTotal;
                    }

                    public String getTooltipHTML(String serieName, double value) {
                        StringBuilder tooltipHTML = new StringBuilder();
                        tooltipHTML.append("<table border=0 cellpadding=2 ><tr><td valign=top>").append("<img src=\"");

                        String img = "/VAADIN/themes/vprotovisdemo/thumb_up.png";
                        if (value < 1000) {
                            img = "/VAADIN/themes/vprotovisdemo/thumb_down.png";
                        }
                        tooltipHTML.append(img);
                        tooltipHTML.append("\"></td><td>");
                        tooltipHTML.append("<b><i>").append(serieName).append("</i></b><br/>");
                        tooltipHTML.append(value).append(" \u20AC");
                        tooltipHTML.append("</td><tr></table>");

                        return tooltipHTML.toString();
                    }
                }, permanent);
            } else {
                chart.setTooltipFormatter(new DefaultPieTooltipFormatter(), permanent);
            }
        } else {
            chart.setTooltipFormatter(null);
        }
        sourceCodeMap.put("tooltipEnabled", this.miscPanel.isTooltipEnabled());
        sourceCodeMap.put("tooltipCustomEnabled", this.miscPanel.isTooltipCustomEnabled());
        
        if (this.axisPanel.isLabelEnabled()) {
            chart.setLabelVisible(true);
            if (this.axisPanel.isLabelCustomFormatter()) {
                final double labelTotal = total;
                chart.setLabelFormatter(new PieLabelFormatter() {
                    
                    public boolean isVisible(double labelValue) {
                        return 0.05d <= labelValue/labelTotal;
                    }
                    
                    public String format(double labelValue) {
                        int percent = Double.valueOf(labelValue/labelTotal * 100).intValue();
                        return percent + "%";
                    }
                });
            } else {
                chart.setLabelFormatter(new DefaultPieLabelFormatter());
            }
            this.sourceCodeMap.put("customLabelFormatter", this.axisPanel.isLabelCustomFormatter());
            String labelColor = this.axisPanel.getLabelColor();
            if (labelColor != null) {
                chart.setLabelColor(labelColor);
                this.sourceCodeMap.put("labelColor", labelColor);
            } else {
                chart.setLabelColor("#000000");
            }
        } else {
            chart.setLabelVisible(false);
        }
        this.sourceCodeMap.put("labelEnabled", this.axisPanel.isLabelEnabled());
        
        chart.requestRepaint();
    }
    
    public Component getComponent() {
        return this.content;
    }

    public boolean validate() {
        return false;
    }
}
