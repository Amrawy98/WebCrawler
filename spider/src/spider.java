class spider {
    public static void main(String... args) throws InterruptedException {
        CustomQueue<String> toVisit= new CustomQueue<String>();
        toVisit.add("https://support.google.com/chrome/answer/6130773?hl=en");
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
        DBConnect.initDB();
        LinksStock b = new LinksStock(5000,toVisit);
     Thread[] threads = new Thread[15];
     for (int i = 0; i < threads.length; i++) {
      threads[i] = new Thread(new Supplier(b));
      threads[i].start();
     }

     for (Thread thread : threads) {
      thread.join();
     }
//        Thread supplierThread = new Thread (new Supplier(b));
//        Thread supplierThread2 = new Thread (new Supplier(b));
//        Thread supplierThread3 = new Thread (new Supplier(b));
//        Thread supplierThread4 = new Thread (new Supplier(b));
//        Thread supplierThread5 = new Thread (new Supplier(b));
//        supplierThread.start();
//        supplierThread2.start();
//        supplierThread3.start();
//        supplierThread4.start();
//        supplierThread5.start();
//        supplierThread.join();
//        supplierThread2.join();
//        supplierThread3.join();
//        supplierThread4.join();
//        supplierThread5.join();
        b.printData();
    }
}