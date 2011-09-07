import com.bibounde.vprotovis.PieChartComponent;
import com.bibounde.vprotovis.chart.pie.PieLabelFormatter;
import com.bibounde.vprotovis.chart.pie.PieTooltipFormatter;
import com.vaadin.Application;
import com.vaadin.ui.Window;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class WidgetTestApplication extends Application {
    
    private Window window;

    @Override
    public void init() {
        window = new Window("Widget Test");
        setMainWindow(window);

        PieChartComponent pie = new PieChartComponent();
        
<#list series as serie>
        pie.addSerie("${serie.name}", ${serie.value?c}d, ${serie.highlight?string});
</#list>
        final double total = ${total?c}d;
        
        pie.setChartWidth(${chartWidth?c}d);
        pie.setChartHeight(${chartHeight?c}d); 
<#if marginLeft?has_content>

        pie.setMarginLeft(${marginLeft?c}d);
</#if>
<#if marginTop?has_content>

        pie.setMarginTop(${marginTop?c}d);
</#if>
<#if marginRight?has_content>

        pie.setMarginRight(${marginRight?c}d);
</#if>
<#if marginBottom?has_content>

        pie.setMarginBottom(${marginBottom?c}d);
</#if>
<#if lineWidth?has_content>

        pie.setLineWidth(${lineWidth});
</#if>
<#if lineColor?has_content>
        pie.setLineColor(${lineColor});
</#if>
<#if highlightOffset?has_content>

        pie.setHighlightOffset(${highlightOffset});
</#if>
<#if randomColorsSelected>

        pie.setColors(new String[]{<#list randomColors as color><#if color_index % 5 = 0>
                             </#if>"${color}"<#if color_has_next>, </#if></#list>});
</#if>
<#if legendVisible>

        pie.setLegendVisible(true);
        pie.setLegendAreaWidth(${legendAreaWidth?c}d);
<#if legendInsetLeft?has_content>
        bar.setLegendInsetLeft(${legendInsetLeft?c}d);
</#if>
</#if>
<#if tooltipEnabled>

        pie.setTooltipEnabled(true);
<#if tooltipCustomEnabled>   
        PieTooltipFormatter toolTipFormatter = new PieTooltipFormatter() {

            public boolean isVisible(String serieName, double value) {
                return 0.05d <= value/total;
            }

            public String getTooltipHTML(String serieName, doube value) {
                StringBuilder tooltipHTML = new StringBuilder();
                tooltipHTML.append("<table border=0 cellpadding=2 ><tr><td valign=top>").append("<img src=\"");

                String img = "/VAADIN/themes/reindeer/thumb_up.png";
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
        };

        pie.setTooltipFormatter(toolTipFormatter);
</#if>
<#else>

        pie.setTooltipEnabled(false);
</#if>
<#if labelEnabled>

        pie.setLabelVisible(true);
<#if customLabelFormatter>
        PieLabelFormatter labelFormatter = new PieLabelFormatter() {
                    
            public boolean isVisible(double labelValue) {
                return 0.05d <= labelValue/total;
            }
            
            public String format(double labelValue) {
               int percent = Double.valueOf(labelValue/total * 100).intValue();
               return percent + "%";
            }
        });
</#if>
<#if labelColor?has_content>
        pie.setLabelColor("${labelColor}");
</#if>
<#else>
        pie.setLabelVisible(false);
</#if>

        window.addComponent(pie);
    }

}
