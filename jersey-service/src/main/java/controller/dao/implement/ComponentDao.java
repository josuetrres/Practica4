package controller.dao.implement;

import tda.Component;
import tda.LinkedList.LinkedList;

public class ComponentDao extends AdapterDao{
    private Component component;
    private LinkedList<Component> listAll;

    public ComponentDao() { 
        super(Component.class);
    }
    

    public void setComponent(Component Component) {
        this.component = Component;
    }

    public Component getComponent() {
        if (this.component == null) {
            this.component = new Component();
        }
        return this.component;
    }

    public LinkedList getListAll() {
        if(listAll == null){
            this.listAll = listAll();
        }
        return this.listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize()+1;
        component.setId(id);
        this.persist(this.component);
        this.listAll = listAll();
        return true;
    }

    public Boolean update() throws Exception {
        Integer index = getComponent().getId() - 1;
    
        if (index < 0 || index >= getListAll().getSize()) {
            throw new Exception("Índice de Component inválido");
        }
        this.merge(getComponent(), index);
        this.listAll = listAll();
        return true;
    }

    public void deleteComponent(Integer id) throws Exception {
  
        if (listAll == null) {
            this.listAll = listAll(); 
        }

        for (int i = 0; i < listAll.getSize(); i++) {
            Component Component = listAll.get(i); 
            if (Component.getId().equals(id)) {
  
                listAll.delete(i);
                return; 
            }
        }
    
        throw new Exception("Component no encontrado");
    }
    
    
}
