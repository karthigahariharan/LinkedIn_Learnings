public class Test {


    public static void main(String[] args){
        int[] a={-1,-2,6,2,5,8};
        int lowerVal=10e6+1;
        int difference=0;
        Boolean updated=Boolean.FALSE;
        for(int i=0;i<a.length;i++){
            System.out.println(a[i]%2);
            if(a[i]%2!=0 && lowerVal>a[i]){
                lowerVal=a[i];
                System.out.println(lowerVal);
            }
            if(a[i]%2==0 && lowerVal<a[i]){
                if(!updated){
                    difference=a[i]-lowerVal;
                }
                else if(a[i]-lowerVal <=difference){
                    difference=a[i]-lowerVal;
                    updated=Boolean.TRUE;
                }


            }
        }
        System.out.println(difference);
    }

}

