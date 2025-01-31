package controller.dao.services;

import com.google.gson.Gson;

import controller.dao.implement.CableDao;
import tda.Cable;
import tda.LinkedList.LinkedList;

public class CableServices {
    private CableDao obj;

    public CableServices() {
        this.obj = new CableDao();
        
    }

    public Boolean save() throws Exception{
        
        return this.obj.save();
    }

    public LinkedList<Cable> listAll() {
        return this.obj.listAll();
    }
    public Cable getCable(){
        return this.obj.getCable();
    }
    public void setCable(Cable cable){
        this.obj.setCable(cable);
    }

    public CableDao getCableDao() throws Exception{
        return this.obj;
    }
    public Cable get(Integer id) throws Exception{
        return (Cable) obj.get(id);
    }

    public Boolean update() throws Exception {
       
        return obj.update();
    }

    public String toJson() {
        LinkedList<Cable> cables = listAll(); 
        return new Gson().toJson(cables); 
    }
    
    public void deleteCable(Integer id) throws Exception {
        obj.deleteCable(id);
    }
}
