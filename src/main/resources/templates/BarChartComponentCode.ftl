import com.bibounde.vprotovis.chart.bar.TooltipFormatter;
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

        BarChartComponent bar = new BarChartComponent();
<#list series as serie>
        bar.addSerie("${serie.name}", <@compress single_line=true>new double[] {<#list serie.values as value>${value?string("0.#")}d<#if value_has_next>, </#if></#list>}</@compress>);
</#list>

        bar.setChartWidth(${chartWidth}d);
        bar.setChartHeight(${chartHeight}d);
        
<#if marginLeft?has_content>
        bar.setMarginLeft(${marginLeft}d);
</#if>
<#if marginTop?has_content>
        bar.setMarginTop(${marginTop}d);
</#if>
<#if marginRight?has_content>
        bar.setMarginRight(${marginRight}d);
</#if>
<#if marginBottom?has_content>
        bar.setMarginBottom(${marginBottom}d);
</#if>

<#if groupInset?has_content>
        bar.setGroupInset(${groupInset}d);
</#if>
<#if barInset?has_content>
        bar.setBarInset(${barInset}d);
</#if>

<#if xAxisVisible >
        bar.setXAxisVisisble(true);
</#if>
<#if xAxisLabelVisible>
        bar.setXAxisLabelVisible(true);
</#if>

<#if yAxisVisible >
        bar.setYAxisVisisble(true);
</#if>
<#if yAxisLabelVisible>
        bar.setYAxisLabelVisible(true);
        bar.setYAxisLabelStep(${yAxisLabelStep}d);
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

        window.addComponent(bar);
    }

}
