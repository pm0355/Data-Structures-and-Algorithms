    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hash;

/**
 *
 * @author pm
 */
public class Hash {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String year="senior";
        String dept= "CSC";
        String gpa="4.0";
        String[] names={"Abou Nassif","Abulhul","Ajazi","Akahoshi","Alokily","Aloqayli","Baker","Bohay","Brennan","Castro","Clark","Coggeshall","Cole","Crofton","Dadzie","Doud","Flores","Grant","Haggerty","Hall","Hungaski","Jung","Jurado","Koprowski","Mattioli","Mcclanahan","McGowin","Morgan","Neunkirk","Paredes","Plucknett","Poll","Sandhu","Sandstrom","Shi","Teres","Tirba","Toksoz-exley","Vignola","Wilk","Young","Tuan"};
        MyMap<String, Integer> map=new MyHashMap<String, Integer>();
        for(int i=0; i<42;i++){ 
            int key=names[i].hashCode(); 
            map.put(key,names[i],year,dept,gpa);
        }
        // error message returns null
       // System.out.println(map.Search("Matt".hashCode()));
        // search example given key
       System.out.println(map.Search(("Abulhul").hashCode()));
        
        // average linear and quadratic probing
        //System.out.println("Average linear probing: "+map.averageProbe());
       System.out.println("Average quadratic probing: "+map.averageProbe());
    }
}
