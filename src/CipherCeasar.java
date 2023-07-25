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
//    CipherCeasar(Alphabet alphabet,ArrayList<Character> inputArray)
//    {
//        this.alphabet = alphabet;
//        this.inputArray = inputArray;
//        this.outputArray = new ArrayList<>();
//    }
//    CipherCeasar(Alphabet alphabet,ArrayList<Character> inputArray,ArrayList<Character> outputArray)
//    {
//        this.alphabet = alphabet;
//        this.inputArray = inputArray;
//        this.outputArray = outputArray;
//    }

    public void decode(BufferedReader bufferedAdditional, String mode) throws IOException {
        if (mode.equals("DecryptBF")) {
            Map<Integer, Integer> frequencyMap = new TreeMap<>();
            String delimiterString = " \n\r\t.,#*&?:;~(){}[]—\\/%\"$"; // строка символов разделителей
            StringTokenizer wordString;
            StringBuilder inputString = new StringBuilder();
            StringBuilder additionalString = new StringBuilder(); // строка-конструктор для мешка слов
            int c;
            while ((c = bufferedAdditional.read()) != -1) {
                additionalString.append((char) c); // чтение словаря
            }
            HashSet<String> additionalArray;
            additionalArray = new HashSet<>(Arrays.asList(additionalString.toString().split("\n"))); // запись словаря в мешок слов HashSet

            int count;
            for (int a = 0; a < alphabet.getSize(); a++) {
                count = 0;
                    for (Character symbol : inputArray) {
                        try{
                        inputString.append(alphabet.getAlphabetMap().getForward((alphabet.getAlphabetMap().getBackward(symbol) + a) % alphabet.getSize())); // сборка строки с шифртекстом
                    } catch (Exception e)
                    {
                        System.out.println(a + "===" + e.getMessage());
                    }
                    }


                wordString = new StringTokenizer(inputString.toString(), delimiterString); // Токенизация Шифртекста по строке разделителей

                while (wordString.hasMoreTokens()) { // обход по токенам
                    String str = wordString.nextToken();
                    if (additionalArray.contains(str)) { // поиск токена в мешке слов HashSet
                        count++; // подсчет количества совпадений
                    }

                }
                frequencyMap.put(a,count); // запись в HashMap с ключем смещения по алфавиту, количества совпадений токенов с словом в мешке
                inputString.delete(0,inputString.length()-1); // очистка строки шифртекста


            }
            Map.Entry<Integer, Integer> inputMaxEntry = null;
            for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
                if (inputMaxEntry == null || entry.getValue() > inputMaxEntry.getValue()) {
                    inputMaxEntry = entry; //  поиск по наибольшему значению в HashMap
                }
            }
            assert inputMaxEntry != null;
            encode(inputMaxEntry.getKey()); // кодирование со смещением наибольшего количества совпадений

        } else if (mode.equals("DecryptFA")) {
            Map<Character, Integer> frequencyMapAdditional = new TreeMap<>(); // TreeMap для формирования частотного словаря дополнительного текста
            Map<Character, Integer> frequencyMapInput =  new TreeMap<>(); // TreeMap для формирования частотного словаря входного текста
            ArrayList<Character> additionalArray = new ArrayList<>(); // Массив для дополнительного текста
            int c;
            while ((c = bufferedAdditional.read()) != -1) {
                additionalArray.add((char) c); // чтение дополнительного текста в массив
            }
            bufferedAdditional.close();
            calculationFrequencySymbols(inputArray,frequencyMapInput); //
            calculationFrequencySymbols(additionalArray,frequencyMapAdditional);
            int inputKey = alphabet.getAlphabetMap().getBackward(treeMaxEntry(frequencyMapInput).getKey());
            int additionalKey = alphabet.getAlphabetMap().getBackward(treeMaxEntry(frequencyMapAdditional).getKey());
            int supposedKey = additionalKey - inputKey;
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

//    public ArrayList<Character> getInputArray()
//    {
//        return this.inputArray;
//    }
    public ArrayList<Character> getOutputArray()
    {
        return this.outputArray;
    }

//    public Alphabet getAlphabet()
//    {
//        return this.alphabet;
//    }
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
