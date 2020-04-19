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
            document = Jsoup.connect(SURL).get();
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
                    URL urlCheck= new URL(url);
//                    if ((b.getToVisitSize()) < 1000 && b.getVisitedSize()<b.getMaxCount() && budget!=0) //TODO:ya reet nezawed 7ewar el depth wel budget dh HAAAAAAA
//                    {
                    if(b.getVisitedSize()<b.getMaxCount()&&budget!=0){
                        String host = urlCheck.getHost();
                        //\\w+\\.google.com
                        //if ((url.toLowerCase().startsWith("http://") || url.toLowerCase().startsWith("https://"))&&(!(host.equals("support.google.com")||host.equals("policies.google.com")||host.equals("accounts.google.com")||host.equals("play.google.com")))) {
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
                                System.err.println("Google: SURPRISE MOTHER FUCKER");
                            }
                    }
                    else
                        {
                            System.out.println("number of visited sites is: "+b.getVisitedSize()+"/n"+"and the current budget for the site is: " + budget);
                            break;
                        }
//                    } else {
//                        System.out.println("__Reached upper limit kolo yerawa7__");
//                                /*TODO:break wala wait ba2a ana mesh 3aref bas azon enno logical en el upper limit dh isA ya3ny hyeb2a el crawler budget
//                                        fel 7ala de lazem dh yeb2a break 3ashan el budget 5elset bas mesh hyeb2a el condition keda*/
//                        break;
//
//
//                    }
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
