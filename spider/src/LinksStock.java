import javafx.util.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

class LinksStock {
    private final int maxCount;
    Set<String> visited = new HashSet<String>();
    Set<String> auxilary = new HashSet<String>();
    CustomQueue<String> toVisit = new CustomQueue<String>();
    public HashMap<String,ArrayList<String>> outLinks  = new HashMap<>();
    public HashMap<String,ArrayList<String>> inLinks  = new HashMap<>();
    HashMap<String,Document> pages = new HashMap<String,Document>();
    public LinksStock(int max, CustomQueue<String> toVisit) {
        this.maxCount = max;
        this.toVisit.addAll(toVisit);
        toVisit.getQueue().forEach((s)->DBConnect.toVisitDB(s.toString()));
    }
    public LinksStock(int max, CustomQueue<String> toVisit,Set<String> auxilary,Set<String> visited) {
        this.maxCount = max;
        this.toVisit.addAll(toVisit);
        this.toVisit.addAll(auxilary);
        this.visited.addAll(visited);
        //dol already gayen men el DB fa mesh han7ot 7aga fel DB initially at the start of the program, atleast not in this case
        }
    public boolean produce(String link) {
        if(!(visited.contains(link)||auxilary.contains(link)))
        {
            if(toVisit.add(link))
            {
                DBConnect.toVisitDB(link);
                return true;
            }
        }
        return false;
    }
    public void addToPage(String link,String source)
    {
        outLinks.get(source).add(link);
        if(inLinks.get(link)==null)
        {
            inLinks.put(link, new ArrayList<String>());
        }
        inLinks.get(link).add(source);
    }
    public String consume () {
        String s = toVisit.poll();
        auxilary.add(s);
        DBConnect.deleteToVisitDB(s);
        DBConnect.auxDB(s);
        System.out.println("Number of Links after pull: "+toVisit.size());
        return s;
    }
    public void visit(String link, Document Dom) {
            visited.add(link);
            auxilary.remove(link);
            DBConnect.visitDB(link,Dom.toString());
            DBConnect.deleteAuxDB(link);
            pages.put(link,Dom);
            System.out.println("Number of visited links after: " + visited.size() + " the link is:" + link);
            //System.out.println("Number of visited pages after pull: " + pages.size());

    }
    public int getToVisitSize() {
        return toVisit.size();
    }
    public final int getMaxCount() {
        return maxCount;
    }
    public void printData()
    {
        System.out.println("toVisit Links Count: "+toVisit.size());
        System.out.println("maxCount: "+maxCount);
        System.out.println("visited Count: "+visited.size());
    }
    public boolean isVisited(String URL)
    {
        return visited.contains(URL);

    }
//    public HashMap<String, ArrayList> getRobotTxtFiles() {
//        return robotTxtFiles;
//    }
    public int getVisitedSize()
    {
        return visited.size();
    }
 public void page()
   {
      for(String s : visited)
      {
          if(outLinks.get(s)==null)
          {
              outLinks.put(s, new ArrayList<String>());
          }
          Elements linksOnPage = pages.get(s).select("a[href]");
          for (Element page : linksOnPage)
          {
              String url = page.attr("abs:href");
              if(visited.contains(url))
                addToPage(url,s);
          }
      }
      inLinks.keySet().retainAll(visited);
      outLinks.keySet().retainAll(visited);
      DBConnect.pageRankTable(outLinks,inLinks);
   }
}
