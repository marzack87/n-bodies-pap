package concurrency;

import java.util.concurrent.locks.*;

class MyMonitor {
	  
	  //Updated per cosa lo vogliamo utilizzare?! ma sopratutto serve?!
	  private ReentrantLock lock;
	  private Condition goSystem, toBeUpdated, Updated;
	  private int nBody, nDone;
	  private int n;
	  
	  public MyMonitor(int number){
	    lock = new ReentrantLock();
	    goSystem = lock.newCondition();
	    toBeUpdated = lock.newCondition();
	    Updated = lock.newCondition();
	    this.n = number;
	  }
	  
	  public  void updateDone() throws InterruptedException {
	    try {
	      lock.lock();
	      toBeUpdated.signal();
	      nDone = nDone + 1;
	 	  if(nDone == n){
	  		goSystem.signal();
	 	  }
	 	}finally{
	 		lock.unlock();
	 	}
	  }

	  public  void wantUpdate() throws InterruptedException {
	    try {
	    lock.lock();
	    nBody = nBody - 1;
	 	  toBeUpdated.await();
	 	  //...
	 	}finally{
	 		lock.unlock();
	 	}
	  }
	  
	  public  void step() throws InterruptedException {
	    try {
	      lock.lock();
	      goSystem.await();
	 	  nBody = n;
	      toBeUpdated.signal();
	  }finally{
	 		lock.unlock();
	 	}
	  }
	}

