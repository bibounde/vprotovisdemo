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

        window.addComponent(bar);
    }

}
