package com.mkyong.DataStructure;

import com.mkyong.Book;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ItratingHashMap {
    static Map<String, String> name= new HashMap<>();

    static
    {

        name.put("3","yannis");
        name.put("2", "Bifa");

        name.put("1","amanuel");


    }

    public  void  displayLinkedList(){

        LinkedList<Book>  bookLinkedList= new LinkedList<>();
        bookLinkedList.add(new Book( 1L,"AAAA", "AAAA",  new BigDecimal(55.88)));

        bookLinkedList.add(new Book( 2L,"BBBB", "BBBB",  new BigDecimal(666.67)));

        bookLinkedList.remove();

        System.out.println("\n\n\n " + bookLinkedList.toString());

        System.out.println("\n\n\n " + name.get("2").toString());

    }


    public  void  display(){

            for (Map.Entry mapElement : name.entrySet()) {
                String key= (String) mapElement.getKey();
                String  value= (String) mapElement.getValue();


                System.out.println("key " + key + "   value : " + value);

            }
    }


    public static void main(String[] args) {
        ItratingHashMap itratingHashMap = new ItratingHashMap();
        itratingHashMap.display();

        itratingHashMap. displayLinkedList();

    }


}
