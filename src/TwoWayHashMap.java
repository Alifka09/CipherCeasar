import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class TwoWayHashMap<K, V> {

    private final Map<K,V> forward = new Hashtable<K, V>();
    private final Map<V,K> backward = new Hashtable<V, K>();

    public void add(K key, V value) {
        forward.put(key, value);
        backward.put(value, key);
    }
//    public void addForward(K key, V value) {
//        forward.put(key, value);
//    }
//    public void addBackward(V key,K value) {
//        backward.put(key,value);
//    }
    public V getForward(K key) {return forward.get(key);}
    public K getBackward(V key) {return backward.get(key);}
//    public Map<K, V> getForward() {return forward;}
//    public Map<V, K> getBackward() {return backward;}
//    public boolean forwardContainsKey(K key) {
//        return forward.containsKey(key);
//    }
    public boolean backwardContainsKey(V key) {
        return backward.containsKey(key);
    }

    public int size() {
        return forward.size();
    }
    public Set<Map.Entry<K,V>> toSetForward()
    {
        return forward.entrySet();
    }


}