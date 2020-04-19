import org.jsoup.nodes.Document;
import java.util.*;

class LinksStock {
    private final int maxCount;
    Set<String> visited = new HashSet<String>();
    Set<String> auxilary = new HashSet<String>();
    CustomQueue<String> toVisit = new CustomQueue<String>();
    //List<Document> pages = new LinkedList<Document>();
    //HashMap<String, ArrayList> robotTxtFiles;
    public LinksStock(int max, CustomQueue<String> toVisit) {
        this.maxCount = max;
        this.toVisit.addAll(toVisit);
        toVisit.getQueue().forEach((s)->DBConnect.toVisitDB(s.toString()));
    }
    public LinksStock(int max, CustomQueue<String> toVisit,Set<String> auxilary,Set<String> visited) {
        this.maxCount = max;
        this.toVisit.addAll(toVisit);
        this.auxilary.addAll(auxilary);
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
            DBConnect.visitDB(link);
            DBConnect.deleteAuxDB(link);
            //pages.add(Dom);
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
}
