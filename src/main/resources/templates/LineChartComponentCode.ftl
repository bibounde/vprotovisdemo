import com.bibounde.vprotovis.LineChartComponent;
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

        LineChartComponent bar = new LineChartComponent();
<#list series as serie>
        bar.addSerie("${serie.name}", new Point[] {<#list serie.values as value><#if value_index % 2 = 0 && value_index != 0>
                                             </#if>new Point(${value.x?c}d, ${value.y?c}d)<#if value_has_next>, </#if></#list>});
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
<#if lineWidth?has_content>

        bar.setLineWidth(${lineWidth})
</#if>
<#if interpolation?has_content>
        bar.setInterpolationMode(InterpolationMode.${interpolation});
</#if>
<#if xAxisVisible>

        bar.setXAxisVisible(true);
</#if>
<#if xAxisLabelVisible>
        bar.setXAxisLabelVisible(true);
        bar.setXAxisLabelStep(${xAxisLabelStep?c}d);
<#if xAxisGridVisible>
        bar.setXAxisGridVisible(true);
</#if>
</#if>
<#if xAxisCustomFormatter>
        bar.setXAxisLabelFormatter(new AxisLabelFormatter() {
            public String format(double labelValue) {
                return String.valueOf(labelValue) + "j.";
            }
        });
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
</#if>

        window.addComponent(bar);
    }

}
