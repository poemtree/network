package day01;

public class T1 {
	public static void main(String[] args) {
		Thread1 t1 = new Thread1("T1");
		Thread2 r = new Thread2("T2");
		Thread t2 = new Thread(r);
		
		t1.start();
		t2.start();
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		t1.setFlag(false);
		r.setFlag(false);
	}
	
}


class Thread1 extends Thread {
	private String msg;
	private boolean flag = true;
	
	public Thread1(String msg) {
		this.msg = msg;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		System.out.printf("Flag Change\n");
		this.flag = flag;
	}
	
	@Override
	public void run() {
		int i = 0;
		while(flag) {
			System.out.println(msg+" "+i);
			i++;
			try{
				Thread.sleep(300);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

class Thread2 implements Runnable {
	private String msg;
	private boolean flag = true;
	
	public Thread2(String msg) {
		this.msg = msg;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		System.out.printf("Flag Change\n");
		this.flag = flag;
	}

	
	@Override
	public void run() {
		int i = 0;
		while(flag) {
			System.out.println(msg+" "+i);
			i++;
			try{
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}