class spider {
    public static void main(String... args) throws InterruptedException {
        CustomQueue<String> toVisit= new CustomQueue<String>();
       // toVisit.add("https://developers.google.com/youtube/reporting");
      //  toVisit.add("https://www.youtube.com/user/manosman97/community");
       toVisit.add("https://www.youtube.com/watch?v=TcOWwIQDpnE&list=RDTcOWwIQDpnE&start_radio=1");
     //toVisit.add("https://2.bp.blogspot.com/-pv7EZFcvwJU/XLDMLLfutHI/AAAAAAAAAmw/bwnCBlGyE1EhFSdI9bGFYiIWcYSbtBH3ACLcBGAs/s1600/unnamed.gif");
        LinksStock b = new LinksStock(1000,toVisit);


        Thread supplierThread = new Thread (new Supplier(b));
        Thread supplierThread2 = new Thread (new Supplier(b));
        Thread supplierThread3 = new Thread (new Supplier(b));
        Thread supplierThread4 = new Thread (new Supplier(b));
        Thread supplierThread5 = new Thread (new Supplier(b));
        supplierThread.start();
        supplierThread2.start();
        supplierThread3.start();
        supplierThread4.start();
        supplierThread5.start();
        supplierThread.join();
        supplierThread2.join();
        supplierThread3.join();
        supplierThread4.join();
        supplierThread5.join();

        b.printData();
    }
}