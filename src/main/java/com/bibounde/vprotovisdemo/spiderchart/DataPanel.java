package com.bibounde.vprotovisdemo.spiderchart;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bibounde.vprotovis.chart.spider.Serie;
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
    private static final String PROPERTY_SERIE_VAL1 = "Val 1.";
    private static final String PROPERTY_SERIE_VAL2 = "Val 2.";
    private static final String PROPERTY_SERIE_VAL3 = "Val 3.";
    private static final String PROPERTY_SERIE_VAL4 = "Val 4.";
    private static final String PROPERTY_SERIE_VAL5 = "Val 5.";
    private static final String PROPERTY_SERIE_VAL6 = "Val 6.";
    
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
        this.serieTable.addContainerProperty(PROPERTY_SERIE_VAL1, Double.class, null);
        this.serieTable.addContainerProperty(PROPERTY_SERIE_VAL2, Double.class, null);
        this.serieTable.addContainerProperty(PROPERTY_SERIE_VAL3, Double.class, null);
        this.serieTable.addContainerProperty(PROPERTY_SERIE_VAL4, Double.class, null);
        this.serieTable.addContainerProperty(PROPERTY_SERIE_VAL5, Double.class, null);
        this.serieTable.addContainerProperty(PROPERTY_SERIE_VAL6, Double.class, null);
        
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
        
        this.serieTable.addItem(new Object[]{null, "Serie_0", 2d, 3.5d, 1d, 5d, 2d, 1.5d}, "serie_0");
        this.addSerie();
        
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
        
        this.serieTable.addItem(new Object[]{box, "Serie_" + index, RandomUtil.nextDouble(6, false), RandomUtil.nextDouble(6, false), RandomUtil.nextDouble(6, false), RandomUtil.nextDouble(6, false), RandomUtil.nextDouble(6, false), RandomUtil.nextDouble(6, false)}, id);
    }
    
    private void removeSeries() {
        for (String id : this.selectedIds) {
            this.serieTable.removeItem(id);
        }
    }
    
    public Component getComponent() {
        return this.content;
    }
    
    public List<Serie> getSeries() {
        
        List<Serie> ret = new ArrayList<Serie>();
        
        Iterator iterator = this.serieTable.getItemIds().iterator();
        while (iterator.hasNext()) {
            Object id = (Object) iterator.next();
            Item item = this.serieTable.getItem(id);
            
            String name = (String) item.getItemProperty(PROPERTY_SERIE_NAME).getValue();
            Double val1 = (Double) item.getItemProperty(PROPERTY_SERIE_VAL1).getValue();
            Double val2 = (Double) item.getItemProperty(PROPERTY_SERIE_VAL2).getValue();
            Double val3 = (Double) item.getItemProperty(PROPERTY_SERIE_VAL3).getValue();
            Double val4 = (Double) item.getItemProperty(PROPERTY_SERIE_VAL4).getValue();
            Double val5 = (Double) item.getItemProperty(PROPERTY_SERIE_VAL5).getValue();
            Double val6 = (Double) item.getItemProperty(PROPERTY_SERIE_VAL6).getValue();
            
            Serie serie = new Serie();
            serie.setName(name);
            serie.setValues(new double[]{val1, val2, val3, val4, val5, val6});
            
            ret.add(serie);
        }
        
        return ret;
    }
    
    public String[] getAxis() {
        return new String[]{PROPERTY_SERIE_VAL1, PROPERTY_SERIE_VAL2, PROPERTY_SERIE_VAL3, PROPERTY_SERIE_VAL4, PROPERTY_SERIE_VAL5, PROPERTY_SERIE_VAL6};
    }

    public boolean validate() {
        return true;
    }
}
