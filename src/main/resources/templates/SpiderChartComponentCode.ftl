import com.bibounde.vprotovis.SpiderChartComponent;
import com.bibounde.vprotovis.chart.spider.DefaultSpiderTooltipFormatter;
import com.bibounde.vprotovis.chart.spider.Serie;
import com.bibounde.vprotovis.chart.spider.SpiderTooltipFormatter;
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

        SpiderChartComponent spider = new SpiderChartComponent();

        spider.setAxisNames(new <@compress single_line=true>String[]{<#list axisNames as name>"${name}"<#if name_has_next>, </#if></#list>}</@compress>);

<#list series as serie>
        spider.addSerie("${serie.name}", <@compress single_line=true>new double[] {<#list serie.values as value>${value?c}d<#if value_has_next>, </#if></#list>}</@compress>);
</#list>

        spider.setChartWidth(${chartWidth?c}d);
        spider.setChartHeight(${chartHeight?c}d); 
<#if marginLeft?has_content>

        spider.setMarginLeft(${marginLeft?c}d);
</#if>
<#if marginTop?has_content>

        spider.setMarginTop(${marginTop?c}d);
</#if>
<#if marginRight?has_content>

        spider.setMarginRight(${marginRight?c}d);
</#if>
<#if marginBottom?has_content>

        spider.setMarginBottom(${marginBottom?c}d);
</#if>
<#if lineWidth?has_content>

        spider.setLineWidth(${lineWidth});
</#if>
<#if !areaModeEnabled>

        spider.setAreaModeEnabled(false);
<#else>
<#if areaOpacity?has_content>
        spider.setAreaOpacity(${areaOpacity?c}d);
</#if>
</#if>

<#if !axisVisible>
        spider.setAxisVisible(false);
</#if>
<#if !axisCaptionVisible>
        spider.setAxisCaptionVisible(false);
</#if>
<#if !axisLabelVisible>
        spider.setAxisLabelVisible(false);
<#else>
        spider.setAxisLabelStep(${axisLabelStep?c}d);
</#if>
<#if !axisGridVisible>
        spider.setAxisGridVisible(false);
</#if>
<#if axisCustomFormatter>
        spider.setAxisLabelFormatter(new AxisLabelFormatter() {
            public String format(double labelValue) {
                return String.valueOf(labelValue) + "\u20AC";
            }
        });
</#if>
<#if randomColorsSelected>

        spider.setColors(new String[]{<#list randomColors as color><#if color_index % 5 = 0>
                             </#if>"${color}"<#if color_has_next>, </#if></#list>});
</#if>
<#if legendVisible>

        spider.setLegendVisible(true);
        spider.setLegendAreaWidth(${legendAreaWidth?c}d);
<#if legendInsetLeft?has_content>
        spider.setLegendInsetLeft(${legendInsetLeft?c}d);
</#if>
</#if>
<#if !tooltipEnabled>

        spider.setTooltipEnabled(false);
<#else>
        
        spider.setTooltipEnabled(true);
<#if tooltipCustomEnabled>        
        SpiderTooltipFormatter toolTipFormatter = new SpiderTooltipFormatter() {

            public String getTooltipHTML(String axisName, String serieName, double value) {
                StringBuilder tooltipHTML = new StringBuilder();
                tooltipHTML.append("<table border=0 cellpadding=2 ><tr><td valign=top>").append("<img src=\"");

                String img = "/VAADIN/themes/reindeer/thumb_up.png";
                if (value.getY() < 0) {
                   img = "/VAADIN/themes/vprotovisdemo/thumb_down.png";
                }
                tooltipHTML.append(img);
                tooltipHTML.append("\"></td><td>");
                tooltipHTML.append("<b><i>").append(axisName).append("</i></b><br/>");
                tooltipHTML.append(serieName).append(": ").append(value).append(" \u20AC");
                tooltipHTML.append("</td><tr></table>");

                return tooltipHTML.toString();
            }
        };

        spider.setTooltipFormatter(toolTipFormatter);
</#if>
</#if>

        window.addComponent(spider);
    }

}
