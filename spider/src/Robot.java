import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Pattern;

////////////////Experimental////////////////////
public class Robot {
    public static final String DISALLOW = "Disallow:";
    public static final boolean DEBUG = false;
    static HashSet<URL> visited = new HashSet<URL>();
   // ArrayList<RobotRule> robotRules = new ArrayList<>();

    ////LAST WORKING VERISON//////
    public static boolean robotSafe(URL url) {
        String strHost = url.getHost();
        String strRobot = "http://" + strHost + "/robots.txt";
        System.out.println(strRobot);
        URL urlRobot;
        try {
            urlRobot = new URL(strRobot);
        } catch (MalformedURLException e) {
            // something weird is happening, so don't trust it
            return false;
        }
        // ArrayList<RobotRule> robotRules =linkStock.getRobotTxtFiles().get(strHost);
        ArrayList<RobotRule> robotRules = new ArrayList<RobotRule>();
        ;
        //if(robotRules==null){
        String strCommands;
        try {
            InputStream urlRobotStream = urlRobot.openStream();
            //read the file
            //System.out.println(url);
            //System.out.println(strRobot);
            byte b[] = new byte[1000];
            int numRead = urlRobotStream.read(b);
            System.out.println(numRead);
            if (numRead == -1) {
                visited.add(urlRobot);
                return false;
            }
            strCommands = new String(b, 0, numRead);
            while (numRead != -1) {
                numRead = urlRobotStream.read(b);
                if (numRead != -1) {
                    String newCommands = new String(b, 0, numRead);
                    strCommands += newCommands;
                }
            }
            urlRobotStream.close();
        } catch (IOException e) {
            return true; // if there is no robots.txt file, it is OK to search
        }

        if (strCommands.contains(DISALLOW)) // if there are no "disallow" values, then they are not blocking anything.
        {
            String[] split = strCommands.split("\n"); //split line by line
            String mostRecentUserAgent = null;
            for (int i = 0; i < split.length; i++) {
                String line = split[i].trim();
                if (line.toLowerCase().startsWith("user-agent")) {
                    int start = line.indexOf(":") + 1;
                    int end = line.length();
                    mostRecentUserAgent = line.substring(start, end).trim();
                } else if (line.startsWith(DISALLOW)) {
                    if (mostRecentUserAgent != null) {
                        RobotRule r = new RobotRule();
                        r.userAgent = mostRecentUserAgent;
                        int start = line.indexOf(":") + 1;
                        int end = line.length();
                        r.rule = line.substring(start, end).trim();
                        r.rule = r.rule.replace("*", ".*");
                        robotRules.add(r);
                    }
                }
            }
          //  linkStock.getRobotTxtFiles().put(strHost, robotRules);
        }
        String path = url.getPath();
        for (RobotRule robotRule : robotRules) {
            if (!(robotRule.userAgent.equals("*")))
                continue;
            if (robotRule.rule.length() == 0)
                return true; // allows everything if BLANK
            if (robotRule.rule == "/")
                return false;       // allows nothing if /

            if (robotRule.rule.length() <= path.length()) {
                String pathCompare = path.substring(0, robotRule.rule.length());
                //pathCompare.equals(robotRule.rule)
                if (Pattern.matches(robotRule.rule, path) || pathCompare.equals(robotRule.rule))
                    return false;
            }
        }
        // }

        return true;
    }
   /* public static boolean robotSafe(URL url) {
        String strHost = url.getHost();

        // form URL of the robots.txt file
        String strRobot = "http://" + strHost + "/robots.txt";
        URL urlRobot;
        try { urlRobot = new URL(strRobot);
        } catch (MalformedURLException e) {
            // something weird is happening, so don't trust it
            return false;
        }

        if (DEBUG) System.out.println("Checking robot protocol " +
                urlRobot.toString());
        String strCommands;
        try {
            InputStream urlRobotStream = urlRobot.openStream();

            // read in entire file
            byte b[] = new byte[1000];
            int numRead = urlRobotStream.read(b);
            strCommands = new String(b, 0, numRead);
            while (numRead != -1) {
                numRead = urlRobotStream.read(b);
                if (numRead != -1) {
                    String newCommands = new String(b, 0, numRead);
                    strCommands += newCommands;
                }
            }
            urlRobotStream.close();
        } catch (IOException e) {
            // if there is no robots.txt file, it is OK to search
            return true;
        }
        if (DEBUG) System.out.println(strCommands);

        // assume that this robots.txt refers to us and
        // search for "Disallow:" commands.
        String strURL = url.getFile();
        int index = 0;
        while ((index = strCommands.indexOf(DISALLOW, index)) != -1) {
            index += DISALLOW.length();
            String strPath = strCommands.substring(index);
            StringTokenizer st = new StringTokenizer(strPath);

            if (!st.hasMoreTokens())
                break;

            String strBadPath = st.nextToken();

            // if the URL starts with a disallowed path, it is not safe
            if (strURL.indexOf(strBadPath) == 0)
                return false;
        }

        return true;
    }*/

  /*  public static boolean robotSafe(URL urlObj)
    {
        String hostId = urlObj.getProtocol() + "://" + urlObj.getHost()
                + (urlObj.getPort() > -1 ? ":" + urlObj.getPort() : "");
        Map<String, BaseRobotRules> robotsTxtRules = new HashMap<String, BaseRobotRules>();
        BaseRobotRules rules = robotsTxtRules.get(hostId);
        if (rules == null) {
            HttpGet httpget = new HttpGet(hostId + "/robots.txt");
            HttpContext context = new BasicHttpContext();
            HttpResponse response = httpclient.execute(httpget, context);
            if (response.getStatusLine() != null && response.getStatusLine().getStatusCode() == 404) {
                rules = new SimpleRobotRules(RobotRulesMode.ALLOW_ALL);
                // consume entity to deallocate connection
                EntityUtils.consumeQuietly(response.getEntity());
            } else {
                BufferedHttpEntity entity = new BufferedHttpEntity(response.getEntity());
                SimpleRobotRulesParser robotParser = new SimpleRobotRulesParser();
                rules = robotParser.parseContent(hostId, IOUtils.toByteArray(entity.getContent()),
                        "text/plain", USER_AGENT);
            }
            robotsTxtRules.put(hostId, rules);
        }
        boolean urlAllowed = rules.isAllowed(url);
    }*/
    //////////utter failure////////////
////////Yala ba2a/////
 /* public static boolean robotSafe(URL url,LinksStock linkStock)
  {
//      try {
         // URL url =new URL(urlLink);
          String host = url.getHost().toLowerCase();
          ArrayList disAllowList = linkStock.getRobotTxtFiles().get(host);
          if(disAllowList == null)
          {
              disAllowList = new ArrayList();
              try{
                  URL strRobot = new URL("http://" + host + "/robots.txt");
                  System.out.println(strRobot);
                  BufferedReader reader = new BufferedReader(new InputStreamReader(strRobot.openStream()));
                  String line;
                  while((line= reader.readLine())!= null){
                      if(line.indexOf("Disallow:")==0)
                      {
                          String disallowPath = line.substring("Disallow:".length());
                          int commentIndex = disallowPath.indexOf("#");
                          if(commentIndex != -1)
                          {
                              disallowPath = disallowPath.substring(0,commentIndex);
                          }
                          disallowPath = disallowPath.trim();
                          if(disallowPath.equals(""))
                              continue;
                          disAllowList.add(disallowPath);
                      }
                  }
                  linkStock.getRobotTxtFiles().put(host, disAllowList);
              }
              catch(Exception e)
              {
                  return true ;
              }
          }
          String path = url.getPath();
          for (int i = 0; i < disAllowList.size(); i++) {
              String disallow = (String) disAllowList.get(i);
              if (path.startsWith(disallow)) {return false;}
          }
//      }catch (MalformedURLException ex) {
//          return false ;
//      }
      return true;
  }*/
/////////////////////


}