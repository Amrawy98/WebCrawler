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
    public Supplier(LinksStock b) {
        this.b = b;
    }

    @Override
    public void run() {
        doWork();
    }

    public void doWork() {
        //consumer code
        //consume a link and read the links in this page
        while (b.getVisitedSize() < b.getMaxCount()) {
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
                //b.notifyAll();
                System.out.println(Thread.currentThread().getName() + " pulled URL: " + SURL);
            }
            try {
                URL UURL = new URL(SURL);
                if (!(Robot.robotSafe(UURL))){
                    continue;
                }

            try {
            document = Jsoup.connect(SURL).timeout(100000).ignoreHttpErrors(true).get();
            synchronized (b) {
                    if(b.getVisitedSize()>=b.getMaxCount())
                        continue;
                    b.visit(SURL, document);
                    System.out.println(Thread.currentThread().getName()+ "___ " + SURL + " Downloaded ___ Visited Sites: "+b.getVisitedSize());

            }
            } catch (IOException e) {
                System.err.println("For '" + SURL + "': " + e.getMessage());
                continue;
            }
            Elements linksOnPage = document.select("a[href]");
            int budget = 20;
            for (Element page : linksOnPage) {
                synchronized (b) {
                    String url = page.attr("abs:href");
                    if(b.getVisitedSize()<b.getMaxCount()&&budget!=0){
                        String host = (new URL(url)).getHost();
                         if ((url.toLowerCase().startsWith("http://") || url.toLowerCase().startsWith("https://"))&&((!host.matches("\\w+\\.google\\.com"))||host.equals("www.google.com"))&&(!host.equals("apps.apple.com")))
                        {
                            if(b.produce(url))
                            {
                                System.out.println(Thread.currentThread().getName() + " provided a link : '" + url + "' total " + b.getToVisitSize());
                                budget--;
                            }
                            //budget--;
                            b.notifyAll();
                        }
                        else
                            {
                                System.err.println("unallowed link or budget consumed"); //Tab3an dh mesh el right implementation lel budget
                            }
                    }
                    else
                        {
                            System.out.println("Budget Reached");
                        }
                }
            }
            } catch (MalformedURLException exp) {
                System.err.println("For '" + SURL + "': " + exp.getMessage());
                continue;
            }

        }
        System.out.println("__5ALAS BA7__");
    }


}
