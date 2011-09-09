package com.bibounde.vprotovisdemo.areachart;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bibounde.vprotovis.chart.area.Serie;
import com.bibounde.vprotovis.common.Point;
import com.bibounde.vprotovisdemo.Page;
import com.bibounde.vprotovisdemo.util.RandomUtil;
import com.vaadin.data.Item;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class DataPanel implements Page {

    private static final String PROPERTY_SERIE_NAME = "Name";
    private static final String PROPERTY_SERIE_X1 = "X 1.";
    private static final String PROPERTY_SERIE_Y1 = "Y 1.";
    private static final String PROPERTY_SERIE_X2 = "X 2.";
    private static final String PROPERTY_SERIE_Y2 = "Y 2.";
    private static final String PROPERTY_SERIE_X3 = "X 3.";
    private static final String PROPERTY_SERIE_Y3 = "Y 3.";
    private static final String PROPERTY_SERIE_X4 = "X 4.";
    private static final String PROPERTY_SERIE_Y4 = "Y 4.";
    
    private GridLayout content;
    private Table serieTable; 
    
    private List<String> selectedIds = new ArrayList<String>();
    private int serieIndex = 1;
    
    public DataPanel() {
        this.init();
    }
    
    private void init() {
        this.content = new GridLayout(2, 1);
        this.content.setMargin(true);
        this.content.setSpacing(true);
        this.content.setWidth("100%");
        
        this.content.setColumnExpandRatio(0, 0.9f);
        this.content.setColumnExpandRatio(1, 0.1f);
        
        this.serieTable = new Table();
        this.content.addComponent(this.serieTable, 0, 0);
        
        this.serieTable.setWidth("100%");
        this.serieTable.setPageLength(8);
        this.serieTable.setImmediate(true);
        this.serieTable.setEditable(true);
        this.serieTable.addContainerProperty("", CheckBox.class, null);
        this.serieTable.addContainerProperty(PROPERTY_SERIE_NAME, String.class, null);
        this.serieTable.addContainerProperty(PROPERTY_SERIE_X1, Double.class, null);
        this.serieTable.addContainerProperty(PROPERTY_SERIE_Y1, Double.class, null);
        this.serieTable.addContainerProperty(PROPERTY_SERIE_X2, Double.class, null);
        this.serieTable.addContainerProperty(PROPERTY_SERIE_Y2, Double.class, null);
        this.serieTable.addContainerProperty(PROPERTY_SERIE_X3, Double.class, null);
        this.serieTable.addContainerProperty(PROPERTY_SERIE_Y3, Double.class, null);
        this.serieTable.addContainerProperty(PROPERTY_SERIE_X4, Double.class, null);
        this.serieTable.addContainerProperty(PROPERTY_SERIE_Y4, Double.class, null);
        
        VerticalLayout buttonLayout = new VerticalLayout();
        buttonLayout.setSpacing(true);
        this.content.addComponent(buttonLayout, 1, 0);
        
        Button addSerieButton = new Button();
        addSerieButton.setIcon(new ThemeResource("table_row_insert.png"));
        addSerieButton.setDescription("Add new serie");
        buttonLayout.addComponent(addSerieButton);
        
        addSerieButton.addListener(new ClickListener() {
            public void buttonClick(ClickEvent event) {
                addSerie();                
            }
        });
        
        Button deleteSerieButton = new Button();
        deleteSerieButton.setIcon(new ThemeResource("table_row_delete.png"));
        deleteSerieButton.setDescription("Remove selected serie(s)");
        buttonLayout.addComponent(deleteSerieButton);
        
        deleteSerieButton.addListener(new ClickListener() {
            
            public void buttonClick(ClickEvent event) {
                removeSeries();
            }
        });
        
        this.serieTable.addItem(new Object[]{null, "Serie_0", 0, 1000, 1, 1170, 2, 660, 3, 1030}, "serie_0");
        
        for (int i = 0; i < 2; i++) {
            this.addSerie();
        }
    }
    
    private void addSerie() {
        int index = this.serieIndex++;
        final String id = "serie_" + index;
        
        CheckBox box = new CheckBox();
        box.setImmediate(true);
        box.addListener(new ClickListener() {
            
            public void buttonClick(ClickEvent event) {
                if (event.getButton().booleanValue()) {
                    selectedIds.add(id);
                } else {
                    selectedIds.remove(id);
                }
            }
        });
        
        this.serieTable.addItem(new Object[]{box, "Serie_" + index, 0, RandomUtil.nextDouble(2000, false), 1, RandomUtil.nextDouble(2000, false), 2, RandomUtil.nextDouble(2000, false), 3, RandomUtil.nextDouble(2000, false)}, id);
    }
    
    private void removeSeries() {
        for (String id : this.selectedIds) {
            this.serieTable.removeItem(id);
        }
    }
    
    public List<Serie> getSeries() {
        List<Serie> ret = new ArrayList<Serie>();
        
        Iterator iterator = this.serieTable.getItemIds().iterator();
        while (iterator.hasNext()) {
            Object id = (Object) iterator.next();
            Item item = this.serieTable.getItem(id);
            
            String name = (String) item.getItemProperty(PROPERTY_SERIE_NAME).getValue();
            Double x1 = (Double) item.getItemProperty(PROPERTY_SERIE_X1).getValue();
            Double y1 = (Double) item.getItemProperty(PROPERTY_SERIE_Y1).getValue();
            Double x2 = (Double) item.getItemProperty(PROPERTY_SERIE_X2).getValue();
            Double y2 = (Double) item.getItemProperty(PROPERTY_SERIE_Y2).getValue();
            Double x3 = (Double) item.getItemProperty(PROPERTY_SERIE_X3).getValue();
            Double y3 = (Double) item.getItemProperty(PROPERTY_SERIE_Y3).getValue();
            Double x4 = (Double) item.getItemProperty(PROPERTY_SERIE_X4).getValue();
            Double y4 = (Double) item.getItemProperty(PROPERTY_SERIE_Y4).getValue();
            
            Serie serie = new Serie();
            serie.setName(name);
            serie.setValues(new Point[]{new Point(x1, y1), new Point(x2, y2), new Point(x3, y3), new Point(x4, y4)});
            
            ret.add(serie);
        }
        
        return ret;
    }

    public Component getComponent() {
        return this.content;
    }

    public boolean validate() {
        return true;
    } 
}
