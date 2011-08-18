/*
 * Copyright 2009 IT Mill Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.bibounde.vprotovisdemo;

import java.util.HashMap;
import java.util.Map;

import com.bibounde.vprotovisdemo.barchart.BarChartPage;
import com.bibounde.vprotovisdemo.linechart.LineChartPage;
import com.vaadin.Application;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class VProtovisApplication extends Application {
    
    private static final String TREE_ROOT_NODE = "All samples";
    
    private Window window;
    private Panel welcomePanel;
    private VerticalLayout sampleContainer;

    private Map<String, Page> sampleMap = new HashMap<String, Page>();
    
    @Override
    public void init() {
        
        window = new Window("Protovis Wrapper Demo");
        setMainWindow(window);
        
        sampleMap.put(BarChartPage.FQN, new BarChartPage());
        sampleMap.put(LineChartPage.FQN, new LineChartPage());

        HorizontalSplitPanel content = new HorizontalSplitPanel();
        window.addComponent(content);
        content.setSplitPosition(15);
        
        Panel treePanel = new Panel();
        treePanel.setSizeFull();
        content.addComponent(treePanel);
        
        Tree navTree = new Tree();
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
        
        navTree.expandItem(TREE_ROOT_NODE);
        navTree.addListener(new ValueChangeListener() {
            
            public void valueChange(ValueChangeEvent event) {
                showSample((String) event.getProperty().getValue());
            }
        });
        
        this.sampleContainer = new VerticalLayout();
        content.addComponent(this.sampleContainer);
        this.sampleContainer.setSizeFull();
        
        this.welcomePanel = new Panel();
        this.welcomePanel.setSizeFull();
        this.sampleContainer.addComponent(this.welcomePanel);
        
        window.getContent().setSizeFull();
        
        //Dev only
        navTree.setValue(LineChartPage.FQN);
    }
    
    private void showSample(String FQN) {
        this.sampleContainer.removeAllComponents();
        if (this.sampleMap.containsKey(FQN)) {
            this.sampleContainer.addComponent(this.sampleMap.get(FQN).getComponent());
        } else {
            this.sampleContainer.addComponent(this.welcomePanel);
        }
    }
}
