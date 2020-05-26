import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class DBConnect {
    public static Connection con;
    public static Statement st;
/////////////////////////////////////
private static final String creatAux ="CREATE TABLE if not exists auxilary ( id INTEGER PRIMARY KEY autoincrement, link TEXT);";
private static final String dropAux ="DROP TABLE IF EXISTS " + "auxilary";
private static final String creatVisited ="CREATE TABLE if not exists visited ( id INTEGER PRIMARY KEY autoincrement, link TEXT);";
private static final String dropVisited ="DROP TABLE IF EXISTS " + "visited";
/////////////////////////////////////

    public static void initDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&maxAllowedPacket=2000000", "root", "");
            st = con.createStatement();
        } catch (Exception ex) {
            System.out.println("Error: " + ex);

        }
    }
//    public static int resetDB()
//    {
//        try {
//            st.executeQuery(dropAux);
//            st.executeQuery(dropVisited);
//            st.executeQuery(creatAux);
//            st.executeQuery(creatVisited);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return 0;
//        }
//        return 1;
//    }
    /**
     * returns size of scheduled to be visited links in database on success
     * in case of data base error returns zero displaying error in terminal*/
    public static int getLatestFinalState(Set<String> auxilary,CustomQueue<String> toVisit) //ana hasamek getLatestFinalState XD
    {
        try {
            String query1 = "SELECT * FROM tovisit";
            String query2 = "SELECT * FROM auxilary";
            ResultSet auxilaryRS  = st.executeQuery(query2);
            ///////////////////logic latef nemla feh el sets ya  beh elly hategy fel parameters ////////////////////
            //fill auxilary list
            while (auxilaryRS.next()) {
                            String link = auxilaryRS.getString("link");
                            auxilary.add(link);
                        }
            ResultSet tpVisitRS  = st.executeQuery(query1);
            //fill toVisit list
            while (tpVisitRS.next()) {
                String link = tpVisitRS.getString("link");
                toVisit.add(link);
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////
            return toVisit.size();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * returns size of visited links in database on success
     * in case of data base error returns zero displaying error in terminal*/
    public static int getLastVisited(Set<String> visited) //ana hasamek getLastVisited XD
    {
        try {
            String query1 = "SELECT * FROM visited";
            ResultSet visitedRS  = st.executeQuery(query1);
            ///////////////////logic latef nemla feh el sets ya  beh elly hategy fel parameters ////////////////////
            //Fill visited List
            while (visitedRS.next()) {
                String link = visitedRS.getString("link");
                visited.add(link);
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////
            return visited.size();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public static int visitDB(String link,String Dom)
    {
        link=link.replace("\\","\\\\");
        link=link.replace("'","\\'");
        Dom=Dom.replace("\\","\\\\");
        Dom=Dom.replace("'","\\'");
        String query = "INSERT INTO `visited` (`id`, `link`,`Dom`) VALUES (NULL, '" + link + "','"+Dom+"')";
        try {
            return st.executeUpdate(query);
        } catch (SQLException e) {
            try {
                query = "INSERT INTO `visited` (`id`, `link`,`Dom`) VALUES (NULL, '" + link + "',NULL)";
                return st.executeUpdate(query);
            }
            catch (SQLException e1)
            {
                e1.printStackTrace();
                return 0;
            }
        }
    }
    public static int auxDB(String link)
    {
        link=link.replace("\\","\\\\");
        link=link.replace("'","\\'");
        String query = "INSERT INTO `auxilary` (`id`, `link`) VALUES (NULL, '" + link + "')";
        try {
            return st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public static int toVisitDB(String link)
    {
        link=link.replace("\\","\\\\");
        link=link.replace("'","\\'");
        String query = "INSERT INTO `tovisit` (`id`, `link`) VALUES (NULL, '" + link + "')";
        try {
            return st.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("DATA BASE ERROR AT:"+link);
            e.printStackTrace();
            return 0;
        }
    }
    public static int deleteToVisitDB(String link)
    {
        link=link.replace("\\","\\\\");
        link=link.replace("'","\\'");
        String query = "DELETE FROM `tovisit` WHERE link = '" + link + "'";
        try {
            return st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public static int deleteAuxDB(String link)
    {
        link=link.replace("\\","\\\\");
        link=link.replace("'","\\'");
        String query = "DELETE FROM `auxilary` WHERE link = '" + link + "'";
        try {
            return st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    public static void pageRankTable(HashMap<String, ArrayList<String>> out , HashMap<String,ArrayList<String>> in)
    {
        try {

            String query1 = "DELETE FROM `pagerank`";
            st.executeUpdate(query1);
            for( String s : out.keySet())

            {
                Gson gson = new Gson();
                String jsonOut = gson.toJson(out.get(s));
                String outLinks ="'"+jsonOut.replace("\\","\\\\").replace("'","\\'")+"'";
                String inLinks =in.get(s)==null?"NULL":"'"+gson.toJson(in.get(s)).replace("\\","\\\\").replace("'","\\'")+"'";
                s=s.replace("\\","\\\\").replace("'","\\'");
                String query = "INSERT INTO `pagerank` (`link`, `outLinks`, `inLinks`, `rank`) VALUES ('"+s+"', "+outLinks+", "+inLinks+", '0')";
                st.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
