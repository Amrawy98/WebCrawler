import java.util.*;

public class CustomQueue<T>{

    //    Pop is:
//
//    Foo pop(Set<Foo> s) {
//        Iterator<Foo> it = s.iterator() ;
//        Foo popped = it.next() ;
//        it.remove() ;
//        return popped ;
//    }
    private HashSet<T> hashSet = new HashSet<T>();
    private Queue<T> queue = new LinkedList<T>();
    T poll()
    {
        T t = queue.poll();
        hashSet.remove(t);
        return t;
    }
    Boolean add (T t)
    {
        if(hashSet.add(t)){
            queue.add(t);
            return true;
        }
        return false;
    }
    int size()
    {
        return queue.size();
    }

    public void addAll(CustomQueue<T> toVisit) {
        queue.addAll(toVisit.queue);
        hashSet.addAll(toVisit.queue);
    }
    Queue<T> getQueue()
    {
        return queue;
    }

    public void addAll(Set<T> auxilary) {
        auxilary.removeAll(hashSet);
        hashSet.addAll(auxilary);
        queue.addAll(auxilary);
    }
}
