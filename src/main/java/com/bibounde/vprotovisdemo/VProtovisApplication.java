package com.bibounde.vprotovisdemo;

import java.util.HashMap;
import java.util.Map;

import org.vaadin.googleanalytics.tracking.GoogleAnalyticsTracker;

import com.bibounde.vprotovisdemo.action.ActionEvent;
import com.bibounde.vprotovisdemo.action.ActionListener;
import com.bibounde.vprotovisdemo.barchart.BarChartPage;
import com.bibounde.vprotovisdemo.linechart.LineChartPage;
import com.bibounde.vprotovisdemo.piechart.PieChartPage;
import com.bibounde.vprotovisdemo.spiderchart.SpiderChartPage;
import com.vaadin.Application;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class VProtovisApplication extends Application implements ActionListener {
    
    private static final String TREE_ROOT_NODE = "All samples";
    
    private Window window;
    private WelcomePage welcomePage;
    private VerticalLayout sampleContainer;

    private Map<String, Page> sampleMap = new HashMap<String, Page>();

    private Tree navTree;

    private GoogleAnalyticsTracker tracker;
    
    @Override
    public void init() {
        
        setTheme("vprotovisdemo");
        
        window = new Window("Protovis Wrapper Demo");
        setMainWindow(window);
        
        sampleMap.put(BarChartPage.FQN, new BarChartPage());
        sampleMap.put(LineChartPage.FQN, new LineChartPage());
        sampleMap.put(PieChartPage.FQN, new PieChartPage());
        sampleMap.put(SpiderChartPage.FQN, new SpiderChartPage());

        GridLayout mainContent = new GridLayout(1, 3);
        mainContent.setRowExpandRatio(0, 1);
        window.addComponent(mainContent);
        mainContent.setSizeFull();
        
        HorizontalSplitPanel content = new HorizontalSplitPanel();
        mainContent.addComponent(content, 0, 0);
        content.setSplitPosition(15);
        
        Panel treePanel = new Panel();
        treePanel.setSizeFull();
        content.addComponent(treePanel);
        
        this.navTree = new Tree();
        navTree.setImmediate(true);
        treePanel.addComponent(navTree);
        
        navTree.addItem(TREE_ROOT_NODE);
        navTree.setNullSelectionAllowed(false);
        
        navTree.addItem(BarChartPage.FQN);
        navTree.setParent(BarChartPage.FQN, TREE_ROOT_NODE);
        navTree.setChildrenAllowed(BarChartPage.FQN, false);
        
        navTree.addItem(LineChartPage.FQN);
        navTree.setParent(LineChartPage.FQN, TREE_ROOT_NODE);
        navTree.setChildrenAllowed(LineChartPage.FQN, false);
        
        navTree.addItem(PieChartPage.FQN);
        navTree.setParent(PieChartPage.FQN, TREE_ROOT_NODE);
        navTree.setChildrenAllowed(PieChartPage.FQN, false);
        
        navTree.addItem(SpiderChartPage.FQN);
        navTree.setParent(SpiderChartPage.FQN, TREE_ROOT_NODE);
        navTree.setChildrenAllowed(SpiderChartPage.FQN, false);
        
        navTree.expandItem(TREE_ROOT_NODE);
        navTree.addListener(new ValueChangeListener() {
            
            public void valueChange(ValueChangeEvent event) {
                showSample((String) event.getProperty().getValue());
            }
        });
        
        this.sampleContainer = new VerticalLayout();
        content.addComponent(this.sampleContainer);
        this.sampleContainer.setSizeFull();
        
        this.welcomePage = new WelcomePage(this);
        this.sampleContainer.addComponent(this.welcomePage.getComponent());
        
        //Pub
        Embedded pub = new Embedded(null, new ExternalResource("http://code.google.com/appengine/images/appengine-silver-120x30.gif"));
        pub.setStyleName("pub");
        mainContent.addComponent(pub, 0, 1);
        mainContent.setComponentAlignment(pub, Alignment.MIDDLE_CENTER);
        
        
        //Analytics
        this.tracker = new GoogleAnalyticsTracker("UA-25299561-1", "vprotovisdemo.appspot.com");
        mainContent.addComponent(tracker, 0, 2);
        
        window.getContent().setSizeFull();
    }
    
    private void showSample(String FQN) {
        this.sampleContainer.removeAllComponents();
        if (this.sampleMap.containsKey(FQN)) {
            this.sampleContainer.addComponent(this.sampleMap.get(FQN).getComponent());
            tracker.trackPageview("/" + FQN.toLowerCase());
        } else {
            this.sampleContainer.addComponent(this.welcomePage.getComponent());
            tracker.trackPageview("/welcome");
        }
    }

    public void actionPerformed(ActionEvent event) {
        if (this.welcomePage == event.getSource()) {
            this.navTree.select(event.getCommand());
        }
    }
}
