/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package weka.clusterquality;

/**
 *
 * @author hans
 */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Permutations<T> {
    
    public Collection<List<T>> permute(Collection<T> input) {
        Collection<List<T>> output = new ArrayList<List<T>>();
        if (input.isEmpty()) {
            output.add(new ArrayList<T>());
            return output;
        }
        List<T> list = new ArrayList<T>(input);
        T head = list.get(0);
        List<T> rest = list.subList(1, list.size());
        for (List<T> permutations : permute(rest)) {
            List<List<T>> subLists = new ArrayList<List<T>>();
            for (int i = 0; i <= permutations.size(); i++) {
                List<T> subList = new ArrayList<T>();
                subList.addAll(permutations);
                subList.add(i, head);
                subLists.add(subList);
            }
            output.addAll(subLists);
        }
        return output;
    }
    
    public static void main(String args[]) {
        Permutations<Integer> obj = new Permutations<Integer>();
        Collection<Integer> input = new ArrayList<Integer>();
        input.add(1);
        input.add(2);
        input.add(3);

        Collection<List<Integer>> output = obj.permute(input);
        int k = 0;
        Set<List<Integer>> pnr = null;
        for (int i = 0; i <= input.size(); i++) {
            pnr = new HashSet<List<Integer>>();
            for(List<Integer> integers : output){
            pnr.add(integers.subList(i, integers.size()));
            }
            k = input.size()- i;
            System.out.println("P("+input.size()+","+k+") :"+ 
            "Count ("+pnr.size()+") :- "+pnr);
        }
    }    
}
