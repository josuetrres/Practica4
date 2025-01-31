package controller.dao.implement;

import tda.Cable;
import tda.LinkedList.LinkedList;

public class CableDao extends AdapterDao{
    private Cable cable;
    private LinkedList<Cable> listAll;

    public CableDao() { 
        super(Cable.class);
    }
    

    public void setCable(Cable cable) {
        this.cable = cable;
    }

    public Cable getCable() {
        if (this.cable == null) {
            this.cable = new Cable();
        }
        return this.cable;
    }

    public LinkedList getListAll() {
        if(listAll == null){
            this.listAll = listAll();
        }
        return this.listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize()+1;
        cable.setId(id);
        this.persist(this.cable);
        this.listAll = listAll();
        return true;
    }

    public Boolean update() throws Exception {
        Integer index = getCable().getId() - 1;
    
        if (index < 0 || index >= getListAll().getSize()) {
            throw new Exception("Índice de Cable inválido");
        }
        this.merge(getCable(), index);
        this.listAll = listAll();
        return true;
    }

    public void deleteCable(Integer id) throws Exception {
  
        if (listAll == null) {
            this.listAll = listAll(); 
        }

        for (int i = 0; i < listAll.getSize(); i++) {
            Cable cable = listAll.get(i); 
            if (cable.getId().equals(id)) {
  
                listAll.delete(i);
                return; 
            }
        }
    
        throw new Exception("Cable no encontrado");
    }
    

}
