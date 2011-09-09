import com.bibounde.vprotovis.BarChartComponent;
import com.bibounde.vprotovis.chart.bar.BarTooltipFormatter;
import com.bibounde.vprotovis.chart.bar.DefaultBarTooltipFormatter;
import com.bibounde.vprotovis.common.AxisLabelFormatter;
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

        BarChartComponent bar = new BarChartComponent();
<#list series as serie>
        bar.addSerie("${serie.name}", <@compress single_line=true>new double[] {<#list serie.values as value>${value?c}d<#if value_has_next>, </#if></#list>}</@compress>);
</#list>
        bar.setChartWidth(${chartWidth?c}d);
        bar.setChartHeight(${chartHeight?c}d); 
<#if marginLeft?has_content>

        bar.setMarginLeft(${marginLeft?c}d);
</#if>
<#if marginTop?has_content>

        bar.setMarginTop(${marginTop?c}d);
</#if>
<#if marginRight?has_content>

        bar.setMarginRight(${marginRight?c}d);
</#if>
<#if marginBottom?has_content>

        bar.setMarginBottom(${marginBottom?c}d);
</#if>
<#if groupInset?has_content>

        bar.setGroupInset(${groupInset?c}d);
</#if>
<#if barInset?has_content>

        bar.setBarInset(${barInset?c}d);
</#if>
<#if xAxisVisible >

        bar.setXAxisVisible(true);
</#if>
<#if xAxisLabelVisible>
        bar.setXAxisLabelVisible(true);
</#if>
<#if yAxisVisible >

        bar.setYAxisVisible(true);
</#if>
<#if yAxisLabelVisible>
        bar.setYAxisLabelVisible(true);
        bar.setYAxisLabelStep(${yAxisLabelStep?c}d);
<#if yAxisGridVisible>
        bar.setYAxisGridVisible(true);
</#if>
</#if>
<#if yAxisCustomFormatter>
        bar.setYAxisLabelFormatter(new AxisLabelFormatter() {
            public String format(double labelValue) {
                return String.valueOf(labelValue) + "\u20AC";
            }
        });
</#if>
<#if randomColorsSelected>

        bar.setColors(new String[]{<#list randomColors as color><#if color_index % 5 = 0>
                             </#if>"${color}"<#if color_has_next>, </#if></#list>});
</#if>
<#if legendVisible>

        bar.setLegendVisible(true);
        bar.setLegendAreaWidth(${legendAreaWidth?c}d);
<#if legendInsetLeft?has_content>
        bar.setLegendInsetLeft(${legendInsetLeft?c}d);
</#if>
</#if>
<#if tooltipEnabled>

        bar.setTooltipEnabled(true);
<#if tooltipCustomEnabled>        
        BarTooltipFormatter toolTipFormatter = new BarTooltipFormatter() {

            public String getTooltipHTML(String serieName, double value, String groupName) {
                StringBuilder tooltipHTML = new StringBuilder();
                tooltipHTML.append("<table border=0 cellpadding=2 ><tr><td valign=top>");
                tooltipHTML.append("<img src=\"");

                String img = "/VAADIN/themes/reindeer/thumb_up.png";
                if (value < 1000) {
                    img = "/VAADIN/themes/reindeer/thumb_down.png";
                }
                tooltipHTML.append(img);

                tooltipHTML.append("\"></td><td>");
                tooltipHTML.append("<b><i>").append(groupName).append("</i></b><br/>");
                tooltipHTML.append(serieName).append(": ").append(value).append(" \u20AC");
                tooltipHTML.append("</td><tr></table>");
                return tooltipHTML.toString();
            }
        };

        bar.setTooltipFormatter(toolTipFormatter);
</#if>
<#else>

        bar.setTooltipEnabled(false);
</#if>

        window.addComponent(bar);
    }

}
