import com.bibounde.vprotovis.AreaChartComponent;
import com.bibounde.vprotovis.chart.InterpolationMode;
import com.bibounde.vprotovis.chart.area.AreaTooltipFormatter;
import com.bibounde.vprotovis.chart.area.DefaultAreaTooltipFormatter;
import com.bibounde.vprotovis.chart.area.Serie;
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

        AreaChartComponent area = new AreaChartComponent();
<#list series as serie>
        area.addSerie("${serie.name}", new Point[] {<#list serie.values as value><#if value_index % 2 = 0 && value_index != 0>
                                              </#if>new Point(${value.x?c}d, ${value.y?c}d)<#if value_has_next>, </#if></#list>});
</#list>
        area.setChartWidth(${chartWidth?c}d);
        area.setChartHeight(${chartHeight?c}d); 
<#if marginLeft?has_content>

        area.setMarginLeft(${marginLeft?c}d);
</#if>
<#if marginTop?has_content>

        area.setMarginTop(${marginTop?c}d);
</#if>
<#if marginRight?has_content>

        area.setMarginRight(${marginRight?c}d);
</#if>
<#if marginBottom?has_content>

        area.setMarginBottom(${marginBottom?c}d);
</#if>
<#if lineWidth?has_content>

        area.setLineWidth(${lineWidth});
</#if>
<#if opacity?has_content>

        area.setAreaOpacity(${opacity?c}d);
</#if>
<#if interpolation?has_content>
        area.setInterpolationMode(InterpolationMode.${interpolation});
</#if>
<#if xAxisVisible>

        area.setXAxisVisible(true);
</#if>
<#if xAxisLabelVisible>
        area.setXAxisLabelVisible(true);
        area.setXAxisLabelStep(${xAxisLabelStep?c}d);
<#if xAxisGridVisible>
        area.setXAxisGridVisible(true);
</#if>
</#if>
<#if xAxisCustomFormatter>
        area.setXAxisLabelFormatter(new AxisLabelFormatter() {
            public String format(double labelValue) {
                return String.valueOf(labelValue) + "j.";
            }
        });
</#if>
<#if yAxisVisible >

        area.setYAxisVisible(true);
</#if>
<#if yAxisLabelVisible>
        area.setYAxisLabelVisible(true);
        area.setYAxisLabelStep(${yAxisLabelStep?c}d);
<#if yAxisGridVisible>
        area.setYAxisGridVisible(true);
</#if>
</#if>
<#if yAxisCustomFormatter>
        area.setYAxisLabelFormatter(new AxisLabelFormatter() {
            public String format(double labelValue) {
                return String.valueOf(labelValue) + "\u20AC";
            }
        });
</#if>
<#if randomColorsSelected>

        area.setColors(new String[]{<#list randomColors as color><#if color_index % 5 = 0>
                             </#if>"${color}"<#if color_has_next>, </#if></#list>});
</#if>
<#if legendVisible>

        area.setLegendVisible(true);
        area.setLegendAreaWidth(${legendAreaWidth?c}d);
<#if legendInsetLeft?has_content>
        area.setLegendInsetLeft(${legendInsetLeft?c}d);
</#if>
</#if>
<#if tooltipEnabled>

        area.setTooltipEnabled(true);
<#if tooltipCustomEnabled>        
        AreaTooltipFormatter toolTipFormatter = new AreaTooltipFormatter() {

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

        area.setTooltipFormatter(toolTipFormatter);
</#if>
<#else>

        area.setTooltipEnabled(false);
</#if>

        window.addComponent(area);
    }

}
