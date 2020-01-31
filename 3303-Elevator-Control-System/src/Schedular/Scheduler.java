package Schedular;

public class Scheduler implements Runnable {
	
	private boolean elevatorRecieve;
	private boolean floorRecieve;
	
	public Scheduler() {
		elevatorRecieve = false;
		floorRecieve = false;
	}

	@Override
	public void run() {
		while(true) {
			if(elevatorRecieve) {
				
			}
			else if(floorRecieve) {
				
			}
			else {
				try {
					System.out.println(Thread.currentThread().getName() + " is waiting");
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
