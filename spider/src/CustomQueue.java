import java.util.HashSet;
import java.util.Iterator;

public class CustomQueue<T> extends HashSet {

    //    Pop is:
//
//    Foo pop(Set<Foo> s) {
//        Iterator<Foo> it = s.iterator() ;
//        Foo popped = it.next() ;
//        it.remove() ;
//        return popped ;
//    }
    T poll()
    {
        Iterator<T> iterator = this.iterator();
        T t = iterator.next();
        iterator.remove();
        return t;
    }
}
