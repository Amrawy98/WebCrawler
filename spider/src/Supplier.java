import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

class Supplier implements Runnable {
    //the supplier is the one who will fill the tovisit list for us
    private LinksStock b;

    public Supplier (LinksStock b) {
        this.b = b;
    }

    @Override
    public void run() {
        doWork();
    }

    public void doWork () {
        //consumer code
        //consume a link and read the links in this page
        while (b.getVisitedSize()<b.getMaxCount()) {
            Document document = null;
            String SURL;
            synchronized (b) {
                while (b.getToVisitSize() <= 0) {
                    //No links in the queue
                    try {
                        System.out.println("___NO LINKS TO VISIT___");
                        b.wait();
                    } catch (InterruptedException e) {

                    }
                }
                SURL = b.consume();
                try {
                    if(!(Robot.robotSafe(new URL(SURL))))
                        continue;
                }
                catch (MalformedURLException exp)
                {
                    System.err.println("For '" + SURL + "': " + exp.getMessage());
                }
                System.out.println(Thread.currentThread().getName() + " pulled URL: " + SURL);
                try {
                    document = Jsoup.connect(SURL).get();
                    b.visit(SURL,document);
                    System.out.println("___ "+SURL+" Downloaded ___");
                }
                catch (IOException e) {
                    System.err.println("For '" + SURL + "': " + e.getMessage());
                }
                b.notifyAll();
            }
            Elements linksOnPage = document.select("a[href]");
            for (Element page : linksOnPage) {
                synchronized (b)
                {
                    if (b.getToVisitSize() < 200) //TODO:ya reet nezawed 7ewar el depth wel budget dh HAAAAAAA
                    {
                        b.produce(page.attr("abs:href"));
                        System.out.println(Thread.currentThread().getName() + " provided a link : '"+ page.attr("abs:href") +"' total " + b.getToVisitSize());
                        b.notifyAll();
                    }
                    else
                    {
                        System.out.println("__Reached upper limit kolo yerawa7__");
                                /*TODO:break wala wait ba2a ana mesh 3aref bas azon enno logical en el upper limit dh isA ya3ny hyeb2a el crawler budget
                                        fel 7ala de lazem dh yeb2a break 3ashan el budget 5elset bas mesh hyeb2a el condition keda*/
                        break;


                    }
                }
            }

        }
        System.out.println("__5ALAS BA7__");
    }


}