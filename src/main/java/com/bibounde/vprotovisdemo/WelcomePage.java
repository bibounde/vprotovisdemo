package com.bibounde.vprotovisdemo;

import com.bibounde.vprotovisdemo.action.ActionEvent;
import com.bibounde.vprotovisdemo.action.ActionListener;
import com.bibounde.vprotovisdemo.barchart.BarChartPage;
import com.bibounde.vprotovisdemo.linechart.LineChartPage;
import com.bibounde.vprotovisdemo.piechart.PieChartPage;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class WelcomePage implements Page {

    public static final String ACTION_SELECT_BARCHART = BarChartPage.FQN;
    public static final String ACTION_SELECT_LINECHART = LineChartPage.FQN;
    public static final String ACTION_SELECT_PIECHART = PieChartPage.FQN;
    
    private Panel content;
    private ActionListener actionListener;

    public WelcomePage(ActionListener actionListener) {
        this.initLayout();
        this.actionListener = actionListener;
    }

    private void initLayout() {

        content = new Panel();
        content.setSizeFull();

        content.addComponent(new Label("<h2>Conventional<h2><hr/>", Label.CONTENT_XHTML));

        GridLayout conventionalLayout = new GridLayout(3, 1);
        content.addComponent(conventionalLayout);
        conventionalLayout.setSpacing(true);

        Component barChartComponent = this.createScreenShotComponent("BarChart", "75-barchart.png", ACTION_SELECT_BARCHART);
        conventionalLayout.addComponent(barChartComponent, 0, 0);
        
        Component lineChartComponent = this.createScreenShotComponent("LineChart", "75-linechart.png", ACTION_SELECT_LINECHART);
        conventionalLayout.addComponent(lineChartComponent, 1, 0);
        
        Component pieChartComponent = this.createScreenShotComponent("PieChart", "75-piechart.png", ACTION_SELECT_PIECHART);
        conventionalLayout.addComponent(pieChartComponent, 2, 0);

    }

    private Component createScreenShotComponent(String caption, String pict, final String cmd) {
        VerticalLayout ret = new VerticalLayout();
        ret.setMargin(false);
        ret.setSpacing(false);

        VerticalLayout imgLayout = new VerticalLayout();
        ret.addComponent(imgLayout);
        imgLayout.setMargin(false);
        imgLayout.setSpacing(false);
        imgLayout.setWidth("99px");
        imgLayout.setHeight("99px");
        imgLayout.setStyleName("screenshot-frame");

        final Embedded barChartLink = new Embedded(null, new ThemeResource(pict));
        barChartLink.setWidth("75px");
        barChartLink.setHeight("75px");
        barChartLink.setStyleName("screenshot-img");
        imgLayout.addComponent(barChartLink);
        
        barChartLink.addListener(new ClickListener() {
            
            public void click(ClickEvent event) {
                actionListener.actionPerformed(new ActionEvent(WelcomePage.this, cmd));
            }
        });

        Label captionLabel = new Label(caption, Label.CONTENT_XHTML);
        ret.addComponent(captionLabel);
        ret.setComponentAlignment(captionLabel, Alignment.MIDDLE_CENTER);

        return ret;
    }

    public Component getComponent() {
        return this.content;
    }

    public boolean validate() {
        return true;
    }

}
