package controller.dao.services;

import com.google.gson.Gson;

import controller.dao.implement.ComponentDao;
import tda.Component;
import tda.LinkedList.LinkedList;

public class ComponentServices {
    private ComponentDao obj;

    public ComponentServices() {
        this.obj = new ComponentDao();
        
    }

    public Boolean save() throws Exception{
        
        return this.obj.save();
    }

    public LinkedList<Component> listAll() {
        return this.obj.listAll();
    }
    public Component getComponent(){
        return this.obj.getComponent();
    }
    public void setComponent(Component component){
        this.obj.setComponent(component);
    }

    public ComponentDao getComponentDao() throws Exception{
        return this.obj;
    }
    public Component get(Integer id) throws Exception{
        return (Component) obj.get(id);
    }

    public Boolean update() throws Exception {
       
        return obj.update();
    }

    public String toJson() {
        LinkedList<Component> components = listAll(); 
        return new Gson().toJson(components); 
    }
    
    public void deleteComponent(Integer id) throws Exception {
        obj.deleteComponent(id);
    }

}
