/*
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class spider {
    //a producer consumer problem
    //shared resource
    class Crawler {
        int visitedMax = 5000;
        int count;
        Queue<String> toVisit = new LinkedList<String>();
        Set<String> visited = new HashSet<String>();
        List<Document> pages = new LinkedList<Document>();

        void crawler(int visitedMax, Queue<String> toVisit) {
            this.visitedMax = visitedMax;
            this.toVisit.addAll(toVisit);
            this.count=0;

        }

        public int getCount() {
            return count;

        }
        public String getVisit() {
            if (toVisit.isEmpty()) {
                return null;
            }
            this.count++;
            return toVisit.poll();

        }

        public void visit(String link, Document Dom) {
            visited.add(link);
            pages.add(Dom);

        }

        public boolean addToVisit(String link) {
            if(visited.contains(link))
                return false;
            toVisit.add(link);
            return true;

        }
    }

    /////////////////////////////A7AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA////////////////////
    public class BasicWebCrawler {

        private Crawler crawler;
        private HashSet<String> links;
        //private HashSet<String> links;

        public BasicWebCrawler(Crawler crawler) {
            //links = new HashSet<String>();
            this.crawler = crawler;
        }

        public void getPageLinks() {
            //4. Check if you have already crawled the URLs
            //(we are intentionally not checking for duplicate content in this example)
            while (!crawler.getCrawlerFinished()) {
                String URL = crawler.getVisit(); //we gurrentee that all links to visit are never visited before so we vist it without checks here
                //if (!links.contains(URL)) {
                try {
                    //4. (i) If not add it to the index
                    //////links.add(URL) de step mohema


                    //2. Fetch the HTML code
                    Document document = Jsoup.connect(URL).get();
                    //3. Parse the HTML to extract links to other URLs
                    Elements linksOnPage = document.select("a[href]");
                    //add the link to the visited pages list
                    crawler.visit(URL, document);
                    //5. For each extracted URL... add to the toVisit queue.
                    for (Element page : linksOnPage) {
                        crawler.addToVisit(page.attr("abs:href"));
                    }
                } catch (IOException e) {
                    System.err.println("For '" + URL + "': " + e.getMessage());
                }
                //}
                //}
            }
        }
    }
    ////////////////////////////////////////////////////////kosom 7yaty///////////////////////////////////////////////////////////////////////////////
}
*/

class spider {
    public static void main(String... args) throws InterruptedException {
        CustomQueue<String> toVisit= new CustomQueue<String>();
        toVisit.add("https://www.youtube.com/watch?v=jXYN_M2RDLQ&list=PLvR1Vs9Qj4fk-VnGR2xUtNLvLcwBwGB6V");
        LinksStock b = new LinksStock(50,toVisit);


        Thread supplierThread = new Thread (new Supplier(b));
        Thread supplierThread2 = new Thread (new Supplier(b));

        supplierThread.start();
        supplierThread2.start();
        supplierThread.join();
        supplierThread2.join();
        b.printData();
    }
}