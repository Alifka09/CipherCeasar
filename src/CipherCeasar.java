import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;


public class CipherCeasar {
    private final Alphabet alphabet;
    private ArrayList<Character> inputArray;
    private final ArrayList<Character> outputArray;

    CipherCeasar(Alphabet alphabet)
    {
        this.alphabet = alphabet;
        this.inputArray = new ArrayList<>();
        this.outputArray = new ArrayList<>();

    }
    CipherCeasar(Alphabet alphabet,ArrayList<Character> inputArray)
    {
        this.alphabet = alphabet;
        this.inputArray = inputArray;
        this.outputArray = new ArrayList<>();
    }
    CipherCeasar(Alphabet alphabet,ArrayList<Character> inputArray,ArrayList<Character> outputArray)
    {
        this.alphabet = alphabet;
        this.inputArray = inputArray;
        this.outputArray = outputArray;
    }

    public void decode(BufferedReader bufferedAdditional, String mode) throws IOException {
        if (mode.equals("DecryptBF")) {
            Map<Integer, Integer> frequencyMap = new TreeMap<>();
            String delimiterString = " \n\r\t.,#*&?:;~(){}[]—\\/%\"$";
            StringTokenizer wordString;
            StringBuilder inputString = new StringBuilder();
            StringBuilder additionalString = new StringBuilder();
            int c;
            while ((c = bufferedAdditional.read()) != -1) {
                additionalString.append((char) c);
            }
            HashSet<String> additionalArray;
            additionalArray = new HashSet<>(Arrays.asList(additionalString.toString().split("\n")));

            int count;
            for (int a = 0; a < alphabet.getSize(); a++) {
                count = 0;
                    for (Character symbol : inputArray) {
                        try{

                        inputString.append(alphabet.getAlphabetMap().getForward((alphabet.getAlphabetMap().getBackward(symbol) + a) % alphabet.getSize()));
                    } catch (Exception e)
                    {
                        System.out.println(a + "===" + e.getMessage());
                    }
                    }


                wordString = new StringTokenizer(inputString.toString(), delimiterString);

                while (wordString.hasMoreTokens()) {
                    String str = wordString.nextToken();
                    if (additionalArray.contains(str)) {
//                        System.out.println(str);
                        count++;
                    }

                }
                System.out.println("============" + a + "============");
                frequencyMap.put(a,count);
                inputString.delete(0,inputString.length()-1);


            }
            Map.Entry<Integer, Integer> inputMaxEntry = null;
            for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
                if (inputMaxEntry == null || entry.getValue() > inputMaxEntry.getValue()) {
                    inputMaxEntry = entry;
                }
            }
            System.out.println(inputMaxEntry);
        encode(inputMaxEntry.getKey());

        } else if (mode.equals("DecryptFA")) {
            Map<Character, Integer> frequencyMapAdditional = new TreeMap<>();
            Map<Character, Integer> frequencyMapInput =  new TreeMap<>();
            ArrayList<Character> additionalArray = new ArrayList<>();
            int c;
            while ((c = bufferedAdditional.read()) != -1) {
                additionalArray.add((char) c);
            }
            bufferedAdditional.close();
            calculationFrequencySymbols(inputArray,frequencyMapInput);
            calculationFrequencySymbols(additionalArray,frequencyMapAdditional);
            int inputKey = alphabet.getAlphabetMap().getBackward(treeMaxEntry(frequencyMapInput).getKey());
            int additionalKey = alphabet.getAlphabetMap().getBackward(treeMaxEntry(frequencyMapAdditional).getKey());
            int supposedKey = additionalKey - inputKey;
//            System.out.println(inputKey);
//            System.out.println(additionalKey);
//            System.out.println(supposedKey);
            encode(supposedKey);
        }
    }


    protected Map.Entry<Character,Integer> treeMaxEntry(Map<Character, Integer> treeMap)
    {
        Map.Entry<Character, Integer> inputMaxEntry = null;
        for (Map.Entry<Character, Integer> entry : treeMap.entrySet()) {
            if (inputMaxEntry == null || entry.getValue() > inputMaxEntry.getValue()) {
                inputMaxEntry = entry;
            }
        }
        return inputMaxEntry ;
    }




    protected void calculationFrequencySymbols(ArrayList<Character> IArray, Map<Character,Integer> IMap)
    {
        int number;
        for (Character symbol: IArray)
        {
            if(IMap.containsKey(symbol)) {
                number = IMap.get(symbol);
                IMap.put(symbol, number + 1);
            }
            else
                IMap.put(symbol,1);
        }
    }

    public void encode(int keyOffset)
    {
           try{
               int offset;
               for (Character character: inputArray) {
                   if(alphabet.getAlphabetMap().backwardContainsKey(character)) {
                       offset = (alphabet.getAlphabetMap().getBackward(character) + keyOffset) % alphabet.getSize();
                       outputArray.add(alphabet.getAlphabetMap().getForward(offset));
                   }
                   else
                       outputArray.add(character);
               }
           }
           catch (Exception e)
           {
               System.out.println(e.getMessage());
           }

    }

    public ArrayList<Character> getInputArray()
    {
        return this.inputArray;
    }
    public ArrayList<Character> getOutputArray()
    {
        return this.outputArray;
    }

    public Alphabet getAlphabet()
    {
        return this.alphabet;
    }
    public void setInputArray(BufferedReader bufferedReader) throws Exception
    {
            if(this.inputArray.size() == 0)
            {
                inputArray = new ArrayList<>();
                int c;
                while ((c = bufferedReader.read()) != -1) {
                    inputArray.add((char) c);
            }

            }
            else
                throw  new Exception("InputArray is not empty");

    }
}
