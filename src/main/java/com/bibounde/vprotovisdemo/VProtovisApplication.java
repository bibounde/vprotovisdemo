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
import com.vaadin.Application;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
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
    private VerticalLayout sampleContainer;

    private Map<String, ChartSample> sampleMap = new HashMap<String, ChartSample>();
    
    @Override
    public void init() {
        
        window = new Window("Protovis Wrapper Demo");
        setMainWindow(window);
        
        sampleMap.put(BarChartPage.FQN, new BarChartPage());

        HorizontalSplitPanel content = new HorizontalSplitPanel();
        window.addComponent(content);
        content.setSplitPosition(15);
        
        Tree navTree = new Tree();
        navTree.setImmediate(true);
        content.addComponent(navTree);
        
        navTree.addItem(TREE_ROOT_NODE);
        navTree.addItem(BarChartPage.FQN);
        navTree.setParent(BarChartPage.FQN, TREE_ROOT_NODE);
        navTree.setChildrenAllowed(BarChartPage.FQN, false);
        navTree.expandItem(TREE_ROOT_NODE);
        
        navTree.addListener(new ValueChangeListener() {
            
            public void valueChange(ValueChangeEvent event) {
                showSample((String) event.getProperty().getValue());
            }
        });
        
        this.sampleContainer = new VerticalLayout();
        content.addComponent(this.sampleContainer);
        this.sampleContainer.setSizeFull();
        
        window.getContent().setSizeFull();
    }
    
    private void showSample(String FQN) {
        this.sampleContainer.removeAllComponents();
        if (this.sampleMap.containsKey(FQN)) {
            this.sampleContainer.addComponent(this.sampleMap.get(FQN).getComponent());
        }
    }
}
