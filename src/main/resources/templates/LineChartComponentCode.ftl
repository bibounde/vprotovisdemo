import com.bibounde.vprotovis.LineChartComponent;
import com.bibounde.vprotovis.chart.line.DefaultLineTooltipFormatter;
import com.bibounde.vprotovis.chart.line.LineTooltipFormatter;
import com.bibounde.vprotovis.chart.line.InterpolationMode;
import com.bibounde.vprotovis.common.AxisLabelFormatter;
import com.bibounde.vprotovis.common.Point;
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

        LineChartComponent line = new LineChartComponent();
<#list series as serie>
        line.addSerie("${serie.name}", new Point[] {<#list serie.values as value><#if value_index % 2 = 0 && value_index != 0>
                                             </#if>new Point(${value.x?c}d, ${value.y?c}d)<#if value_has_next>, </#if></#list>});
</#list>
        line.setChartWidth(${chartWidth?c}d);
        line.setChartHeight(${chartHeight?c}d); 
<#if marginLeft?has_content>

        line.setMarginLeft(${marginLeft?c}d);
</#if>
<#if marginTop?has_content>

        line.setMarginTop(${marginTop?c}d);
</#if>
<#if marginRight?has_content>

        line.setMarginRight(${marginRight?c}d);
</#if>
<#if marginBottom?has_content>

        line.setMarginBottom(${marginBottom?c}d);
</#if>
<#if lineWidth?has_content>

        line.setLineWidth(${lineWidth});
</#if>
<#if interpolation?has_content>
        line.setInterpolationMode(InterpolationMode.${interpolation});
</#if>
<#if xAxisVisible>

        line.setXAxisVisible(true);
</#if>
<#if xAxisLabelVisible>
        line.setXAxisLabelVisible(true);
        line.setXAxisLabelStep(${xAxisLabelStep?c}d);
<#if xAxisGridVisible>
        line.setXAxisGridVisible(true);
</#if>
</#if>
<#if xAxisCustomFormatter>
        line.setXAxisLabelFormatter(new AxisLabelFormatter() {
            public String format(double labelValue) {
                return String.valueOf(labelValue) + "j.";
            }
        });
</#if>
<#if yAxisVisible >

        line.setYAxisVisible(true);
</#if>
<#if yAxisLabelVisible>
        line.setYAxisLabelVisible(true);
        line.setYAxisLabelStep(${yAxisLabelStep?c}d);
<#if yAxisGridVisible>
        line.setYAxisGridVisible(true);
</#if>
</#if>
<#if yAxisCustomFormatter>
        line.setYAxisLabelFormatter(new AxisLabelFormatter() {
            public String format(double labelValue) {
                return String.valueOf(labelValue) + "\u20AC";
            }
        });
</#if>
<#if randomColorsSelected>

        line.setColors(new String[]{<#list randomColors as color><#if color_index % 5 = 0>
                             </#if>"${color}"<#if color_has_next>, </#if></#list>});
</#if>
<#if legendVisible>

        line.setLegendVisible(true);
        line.setLegendAreaWidth(${legendAreaWidth?c}d);
<#if legendInsetLeft?has_content>
        line.setLegendInsetLeft(${legendInsetLeft?c}d);
</#if>
</#if>
<#if tooltipEnabled>

        line.setTooltipEnabled(true);
<#if tooltipCustomEnabled>        
        LineTooltipFormatter toolTipFormatter = new LineTooltipFormatter() {

            public String getTooltipHTML(String serieName, Point value) {
                StringBuilder tooltipHTML = new StringBuilder();
                tooltipHTML.append("<table border=0 cellpadding=2 ><tr><td valign=top>").append("<img src=\"");

                String img = "/VAADIN/themes/reindeer/thumb_up.png";
                if (value.getY() < 0) {
                   img = "/VAADIN/themes/vprotovisdemo/thumb_down.png";
                }
                tooltipHTML.append(img);
                tooltipHTML.append("\"></td><td>");
                tooltipHTML.append("<b><i>").append(serieName).append("</i></b><br/>");
                tooltipHTML.append("\u0024").append(": ").append(value.getY()).append(" \u20AC").append("<br/>");
                tooltipHTML.append("t").append(": ").append(value.getX()).append(" ms");
                tooltipHTML.append("</td><tr></table>");

                return tooltipHTML.toString();
            }
        };

        line.setTooltipFormatter(toolTipFormatter);
</#if>
<#else>

        line.setTooltipEnabled(false);
</#if>


        window.addComponent(line);
    }

}
