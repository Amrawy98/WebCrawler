import java.sql.*;

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
            con = DriverManager.getConnection("jdbc:mysql://localhost/db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
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
    public static int getLatestFinalState() //ana hasamek getLatestFinalState XD
    {
        try {
        String query2 = "SELECT * FROM auxilary";
        String query3 = "SELECT * FROM visited";
        ResultSet auxilary  = st.executeQuery(query2);
        ResultSet visited  = st.executeQuery(query3);
        ///////////////////logic latef nemla feh el sets ya  beh elly hategy fel parameters ////////////////////
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
    public static int visitDB(String link)
    {
        link=link.replace("\\","\\\\");
        link=link.replace("'","\\'");
        String query = "INSERT INTO `visited` (`id`, `link`) VALUES (NULL, '" + link + "')";
        try {
            return st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
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
//    public void getData() {
//        try {
//            String query2 = "SELECT * FROM auxilary";
//            String query3 = "SELECT * FROM visited";
//            rs = st.executeQuery(query2);
//            System.out.println("Records from Database");
//            while (rs.next()) {
//                String name = rs.getString("link");
//                String age = rs.getString("id");
//                System.out.println("Name: " + name + " Age: " + age);
//            }
//            rs = st.executeQuery(query3);
//            System.out.println("Records from Database");
//            while (rs.next()) {
//                String name = rs.getString("link");
//                String age = rs.getString("id");
//                System.out.println("Name: " + name + " Age: " + age);
//            }
//        } catch(Exception ex) {
//            System.out.println(ex);
//        }
//    }
}
