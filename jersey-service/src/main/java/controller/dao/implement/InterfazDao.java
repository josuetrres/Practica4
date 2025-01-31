package controller.dao.implement;
import tda.LinkedList.LinkedList;


public interface InterfazDao <T> {
    public void persist(T object) throws Exception;
    public void merge(T object, Integer index) throws Exception;
    public LinkedList listAll();
    public T get(Integer ID) throws Exception;
}


