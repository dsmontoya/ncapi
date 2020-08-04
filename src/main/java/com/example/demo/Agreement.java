package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.Tag;

public class Agreement {
  public String name;
  public String signedBy;
  public Product[] products;

  public Agreement(String name) {
    this.name = name;
    try {
      this.load();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public Agreement(String name, String signedBy, Product[] products) {
    this.name = name;
    this.signedBy = signedBy;
    this.products = products;
  }

  @SuppressWarnings("unchecked")
  Product[] parseProduct(LinkedHashMap<String, Object> map) {
    ArrayList<Product> products = ( ArrayList<Product>) map.get("products");
    if (products == null) {
      return null;
    }
    Object[] objs = products.toArray();
    Product[] out = new Product[products.size()];
    for (int i = 0; i < objs.length; i++) {
      Product product = new Product();
      LinkedHashMap<String, Object> hm = (LinkedHashMap<String, Object>) objs[i];
      product.name = (String) hm.get("name");
      product.price = (Double) hm.get("price");
      product.products = this.parseProduct(hm);
      out[i] = product;
    }
    return out;
  }

  @SuppressWarnings("unchecked")
  void load() throws FileNotFoundException {
    InputStream input = new FileInputStream(new File(this.name));
    Yaml yaml = new Yaml();
    Object data = yaml.load(input);
    LinkedHashMap<String, Object> map = ( LinkedHashMap<String, Object>) data;
    String name = (String) map.get("name");
    this.name = name;
    String signedBy = (String) map.get("signedBy");
    this.signedBy = signedBy;
    this.products = this.parseProduct(map);
    System.out.println(map);
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

