import java.util.HashSet;
import java.util.Set;

class spider {
    static final int MaxCap= 100;
    public static void main(String... args) throws InterruptedException {
        Set<String> visited = new HashSet<String>();
        CustomQueue<String> toVisit = new CustomQueue<String>();
        DBConnect.initDB();
        int c = DBConnect.getLastVisited(visited);
        if(c>=MaxCap)
        {
            System.out.println("DATA BASE IS ALREADY FULL Feha: " + c +" Site");
        }
        else if(c!=0)
        {
            Set<String> auxilary = new HashSet<String>();
            DBConnect.getLatestFinalState(auxilary,toVisit);
            LinksStock b = new LinksStock(MaxCap,toVisit,auxilary,visited);
            b.printData();
            Thread[] threads = new Thread[15];
            for (int i = 0; i < threads.length; i++) {
                threads[i] = new Thread(new Supplier(b));
                threads[i].start();
            }

            for (Thread thread : threads) {
                thread.join();
            }
            System.out.println("Finished crawling");
            b.page();
            b.printData();
        }
        else
            {
                toVisit.add("https://www.youtube.com/watch?v=TcOWwIQDpnE&list=RDTcOWwIQDpnE&start_radio=1"); //1
                toVisit.add("https://www.youtube.com/watch?v=-UIn2xq-tOE&list=RD-UIn2xq-tOE&start_radio=1"); //2
                toVisit.add("https://www.youtube.com/watch?v=qBUZjsxGiDk&list=RD-UIn2xq-tOE&index=1"); //3
                toVisit.add("https://www.youtube.com/watch?v=cLYBeA4I-vM&list=RDCMUCzEy7pi3B7TIS9cn_sdKK9A&start_radio=1&t=2"); //4
                toVisit.add("https://www.youtube.com/watch?v=KQj0eXtsObU&list=RDKQj0eXtsObU&start_radio=1"); //5
                toVisit.add("https://www.youtube.com/watch?v=ktvTqknDobU&list=PLzxEw1CbicllxqVJaN9hodbkMXNX0Cnds"); //6
                toVisit.add("https://www.youtube.com/watch?v=fJ9rUzIMcZQ&list=PLNxOe-buLm6cz8UQ-hyG1nm3RTNBUBv3K"); //7
                toVisit.add("https://www.youtube.com/watch?v=8SbUC-UaAxE&list=PL3485902CC4FB6C67"); //8
                toVisit.add("https://www.youtube.com/watch?v=RPfFhfSuUZ4&list=PL8F6B0753B2CCA128"); //9
                toVisit.add("https://www.youtube.com/watch?v=71Gt46aX9Z4&list=PL2140A0411C65DD13"); //10
                toVisit.add("https://www.youtube.com/watch?v=n2rVnRwW0h8&list=PL65E33789AA7052BC"); //11
                toVisit.add(" https://www.youtube.com/watch?v=UhVjp48U2Oc&list=PL7dCUHQ5D4wvrUfoy2r9_5ehFtbY8M8ZY"); //12
                toVisit.add("https://www.youtube.com/watch?v=tq9bB6QPAdA&list=PLbAFXJC0J5Gae5_5P4coPFJqSpj8EVeN7"); //13
                toVisit.add("https://www.youtube.com/watch?v=oygrmJFKYZY&list=PL4o29bINVT4EG_y-k5jGoOu3-Am8Nvi10"); //14
                toVisit.add("https://www.youtube.com/watch?v=21jLmc_Il3o&list=PLqjEF1rwSoHmB5ktOSZgB4I_Z40UmrFNB"); //15
                LinksStock b = new LinksStock(MaxCap,toVisit);
                Thread[] threads = new Thread[15];
                for (int i = 0; i < threads.length; i++) {
                    threads[i] = new Thread(new Supplier(b));
                    threads[i].start();
                }

                for (Thread thread : threads) {
                    thread.join();
                }
                System.out.println("Finished crawling");
                b.page();
                b.printData();
        }
    }
}