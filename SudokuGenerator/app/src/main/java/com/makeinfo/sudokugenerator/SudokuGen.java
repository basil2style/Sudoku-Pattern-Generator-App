package com.makeinfo.sudokugenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Basil on 3/7/2015.
 */
public class SudokuGen {
    public static boolean checker(int[][] bs, int i, ArrayList<Integer> ar) {
        ArrayList<Integer> temp_ar = new ArrayList<Integer>();
        boolean check1 = true;          //For returning true for good Sudoku
        for(int e=0;e<9;e++){
            bs[i][e] = ar.get(e);
        }
        for(int t=0;t<9;t++){
            for(int e=0;e<=i;e++){

                temp_ar.add(e, bs[e][t]);
            }
            Set<Integer> temp_set = new HashSet<Integer>(temp_ar);
            if(temp_set.size()<temp_ar.size()){
                check1 = false; break;                                      //Fuck OFF
            }
            else {
                temp_ar.clear();
                temp_set.clear();
            }
        }
        return check1;
    }

    public static boolean checkPath(int[][] bs, int i) {
        //boolean check_cP = false;
        ArrayList<Integer> temp_cP = new ArrayList<Integer>();
        Set<Integer> temp_setcP = new HashSet<Integer>();
        boolean denoter = true;
        while(i==3){
            for(int k =0;k<i;k++ ){
                for(int e=0;e<3;e++){
                    temp_cP.add(e, bs[k][e]);
                }
            }
            temp_setcP = new HashSet<Integer>(temp_cP);
            if(temp_cP.size()>temp_setcP.size()){
                denoter = false;break;

            }
            else {
                temp_cP.clear();
                temp_setcP.clear();

                for(int k =0;k<i;k++ ){
                    for(int e=3;e<6;e++){
                        temp_cP.add(bs[k][e]);
                    }
                }
                temp_setcP = new HashSet<Integer>(temp_cP);
                if(temp_cP.size()>temp_setcP.size()){
                    denoter = false;break;

                }
                else {
                    break;
                }
            }
        }
        while(i==6){

            for(int k =3;k<i;k++ ){
                for(int e=0;e<3;e++){
                    temp_cP.add(e, bs[k][e]);
                }
            }
            temp_setcP = new HashSet<Integer>(temp_cP);
            if(temp_cP.size()>temp_setcP.size()){
                denoter = false;break;

            }
            else {
                temp_cP.clear();
                temp_setcP.clear();

                for(int k =3;k<i;k++ ){
                    for(int e=3;e<6;e++){
                        temp_cP.add(bs[k][e]);
                    }
                }
                temp_setcP = new HashSet<Integer>(temp_cP);
                if(temp_cP.size()>temp_setcP.size()){
                    denoter = false;break;

                }
                else {
                    break;
                }
            }

        }
        return denoter;
    }

    public static int[][] sudoGen() {
        ArrayList<Integer> ar = new ArrayList<Integer>();
        int[][] bs  = new int[9][9];
        for(int i=0;i<9;i++){
            ar.add(i+1);
            // System.out.print(ar.get(i));
        }
        Collections.shuffle(ar);
        for(int i=0;i<1;i++){
            for(int j=0;j<9;j++){

                bs[i][j] = ar.get(j);
            }

        }
        boolean check = true;
        for(int i=0;i<9;i++){
            if(i==3 ){
                check = checkPath(bs, i);
                if(check==false)
                    i=i-2;
                //System.out.print("Haiii");
            }
            if(i==6){
                check = checkPath(bs,i);
                if(check==false){
                    i=i-2;
                }
            }

            for(int j=0;j<9;j++){
                if(i>0) {
                    check = checker(bs,i,ar);
                    if(check==false){
                        i--;
                        break;
                    }
                    else {
                        bs[i][j]=ar.get(j);
                    }
                }

            }
            Collections.shuffle(ar);
        }

return bs;
    }
}
