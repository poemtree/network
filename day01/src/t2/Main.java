package t2;

import java.util.Scanner;

public class Main {
		
	public static void main(String[] args) {
		int result=0;
		R1 r1 = new R1();
		Receiver receiver = new Receiver();
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(receiver);
		
		//t1.start();
		t2.start();
				
		/*while((result = r1.getResult()) <= 20) {
			System.out.println(result);
		}
		r1.setFlag(false);*/
		
		Scanner sc= new Scanner(System.in);
		while(true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			receiver.setCmd(sc.nextLine());
		}
	}

}

class R1 implements Runnable {
	private boolean flag;
	private int result;
	
	public R1() {
		this.flag = true;
		this.result = 0;
	}
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public int getResult() {
		return this.result;
	}
	
	@Override
	public void run() {
		while(flag) {
			result++;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
};

class Receiver implements Runnable {
	String cmd;
	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(cmd != null && cmd.equals("s")) {
				for1:for(int i=0; i<50;i++) {
					if(cmd.equals("e")) {
						break for1;
					}
					System.out.println(i);
					try {
						Thread.sleep(300);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}


