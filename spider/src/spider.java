class spider {
    public static void main(String... args) throws InterruptedException {
        CustomQueue<String> toVisit= new CustomQueue<String>();
       // toVisit.add("https://developers.google.com/youtube/reporting");
        toVisit.add("https://www.youtube.com/watch?v=TcOWwIQDpnE&list=RDTcOWwIQDpnE&start_radio=1");
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