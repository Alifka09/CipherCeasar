

public class Alphabet {

    private final int size;
    private TwoWayHashMap<Integer,Character> alphabetMap;
    Alphabet(String typeAlphabetMap, boolean addPunctuation){

        if(typeAlphabetMap.equals("Cyrillic"))
        {
            alphabetMap = new TwoWayHashMap<>();
            addGroup(6,1040);
            add(1025);
            addGroup(32,1046);
            add(1105);
            addGroup(26,1078);


        } else if (typeAlphabetMap.equals("Latin")) {

            alphabetMap = new TwoWayHashMap<>();
            addGroup(26, 65);
            addGroup(26, 97);
        }
        if(addPunctuation)
        {
            add(10);
            add(13);
            addGroup(33,32);
            addGroup(6,91);
            addGroup(4,123);
        }
        assert alphabetMap != null;
        this.size = alphabetMap.size();




    }
     private void addGroup(int length,int start_offset)
    {
        int size = alphabetMap.size();
        for(int a=0;a<length;a++) {
            alphabetMap.add((size + a),(char) (start_offset + a));
        }
    }

    public void add(int c) {
        alphabetMap.add(alphabetMap.size(),(char)c);
    }

//    public Object[] toArray() {
//        return alphabetMap.toSetForward().toArray();
//    }
    
    public TwoWayHashMap<Integer,Character> getAlphabetMap()
    {
        return this.alphabetMap;
    }

    public int getSize()
    {
        return size;
    }

}


