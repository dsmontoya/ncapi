package com.example.demo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.Tag;

public class Agreement {
  public String name;
  public String signedBy;
  public String[] test;
  public Product[] products;

  public Agreement(String name, String signedBy, Product[] products) {
    this.name = name;
    this.signedBy = signedBy;
    this.products = products;
  }
  void save() {
    try {
      FileWriter writer = new FileWriter(this.name);
      Yaml yaml = new Yaml(this.representer);
      yaml.dump(this, writer);
      writer.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  Representer representer = new Representer() {
    @Override
    protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue,Tag customTag) {
        // if value of property is null, ignore it.
        if (propertyValue == null) {
            return null;
        } else {
            return super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
        }
    }

    @Override
    protected MappingNode representJavaBean(Set<Property> properties, Object javaBean) {
        if (!classTags.containsKey(javaBean.getClass()))
            addClassTag(javaBean.getClass(), Tag.MAP);

        return super.representJavaBean(properties, javaBean);
    }
};
}

