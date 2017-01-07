package runway;

import java.util.LinkedList;
import java.util.Queue;

import java.util.PriorityQueue;
public class Runway<Plane> implements Runnable{
    int numPlanes=120;
    int nplanes;
    int nrefuse;
    int idletime;
    int ntakeoff;
    int takeoffwait;
    int landwait;
    int nland=0;
    int buff=0;
    int numtoTakeOff=0;
    int numtoLand=0;
    double hrs=12.0;
    double min=0.0;
    ArrayQueue<Plane> runway;
    ArrayQueue<Plane> takeoff;
    ArrayQueue<Plane> landing;
    
    private Object syncObject = new Object();
 
    public Runway() {
        runway =  new ArrayQueue<Plane>(1);
        takeoff= new ArrayQueue<Plane>(60);
        landing= new ArrayQueue<Plane>(60);
    }
    
    public class Plane{
    private int planeNum;
    
    public Plane(int id){
    planeNum = id;
    }
    }
    
   Plane strip = new Plane(0);
   
   public void instantiate(){

   for(int i=1; i<=numPlanes;i++){
  
   double planeCheck=  Math.round((Math.random() * 1));
   if(planeCheck>.5){
        Plane p= new Plane(2*i+1);
        takeoff.offer(p);
        numtoTakeOff+=1;
   }
   else{
       Plane p= new Plane(2*i+2);
       landing.offer(p);
       numtoLand+=1;
   }
   } 
   }    
    public void run() {
            while (!Thread.currentThread().isInterrupted() && hrs!=14) {
                long clocki= System.currentTimeMillis();
                if (!runway.empty()) {
                    //takeoff logic
                    long startTime=System.currentTimeMillis();
                    Plane p = takeoff.peek();
                    try {
                        Thread.currentThread();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    min+=7;
                    if (min>=60){hrs+=1;min-=60;}
                    if(hrs==14){if (min==0) {
                        System.exit(1);
                        }
}
                    System.out.println("Time: "+(hrs+":"+min));
                    System.out.println("Planes waiting to land: "+(60-nland));
                    System.out.println("Planes waiting to take off: "+(60-ntakeoff));
                    System.out.println("Plane # "+p.planeNum+" is cleared to take off.\n");
   
                    takeoff.poll();
                    runway.poll();
                    long stopTime=System.currentTimeMillis();
                    landwait+=stopTime - startTime;
                    if(landing.full() || takeoff.full()){
                    nrefuse+=1;
                    }
                    ntakeoff+=1;
                }
                if (runway.empty()) {
                    //landing logic
                    long startTime=System.currentTimeMillis();
                    Plane p = landing.peek();
                    try {
                        Thread.currentThread();
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    min+=7;
                    if (min>=60){hrs+=1;min-=60;}
                     if(hrs==14){if (min==0) {
                        System.exit(1);
                        }
}
                    System.out.println("Time: "+hrs+":"+min);
                    System.out.println("Planes waiting to land: "+(60-nland));
                    System.out.println("Planes waiting to take off: "+(60-ntakeoff));
                    System.out.println("Plane # "+p.planeNum+" is cleared to land.\n");
                    landing.poll();
                    runway.offer(strip);
                    long stopTime=System.currentTimeMillis();
                    takeoffwait+=stopTime - startTime;
                    nland+=1;
                    if(landing.full()|| takeoff.full()){
                    nrefuse+=1;
                    }
                }
                else{
                    long startTime=System.currentTimeMillis();
                    try {
                        Thread.currentThread();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    long stopTime=System.currentTimeMillis();
                    idletime+=stopTime - startTime;
                }
            }
            nplanes=nland+ntakeoff;
            System.out.println("Total number planes processed: "+nplanes);
            System.out.println("Total number planes ready to land: "+numtoLand);
            System.out.println("Total number planes ready to takeoff: "+numtoTakeOff);
            System.out.println("Total number planes taken off: "+ntakeoff);
            System.out.println("Total number planes refused to land: "+nrefuse);
            System.out.println("Total number planes landed: "+nland);
            System.out.println("idle-time: "+idletime+"minutes");
            System.out.println("Average takeoff wait: "+(takeoffwait/ntakeoff)/100+"minutes");
            System.out.println("Average landing wait: "+(landwait/nland)/100+"minutes");
          

                 
    }
  public static void main(String[] args) {
       Runway r1= new Runway();
       r1.instantiate();
       Thread t1 = new Thread(r1);
       t1.start();
       try {
            Thread.currentThread().sleep(12000);
        } catch (InterruptedException e) {
        }
           t1.interrupt();
       }
      
}
