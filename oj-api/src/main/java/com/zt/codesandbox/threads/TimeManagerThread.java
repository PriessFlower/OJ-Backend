package com.zt.codesandbox.threads;

//这个超时逻辑是怎么实现的？
public class TimeManagerThread extends Thread {

	private long time;

	private boolean isTimeout = false;

	private Process process;

	public TimeManagerThread(long time) {
		isTimeout = false;
		this.time = time;
	}

	@Override
	public void run() {
        try {
            // 线程休眠指定的时间（例如 5 秒）
            Thread.sleep(time);

            // 如果休眠结束没有被打断，说明主线程还没跑完，意味着超时了！
            if (process != null && process.isAlive()) {
                process.destroy(); // 强行杀死底层运行的命令行进程
                isTimeout = true;
            }
        } catch (InterruptedException e) {
            // 如果在睡眠期间被主线程打断 (interrupt)，说明代码在超时时间内顺利跑完了
            // 此时抛出异常，线程会自然结束，不需要做任何超时处理
            isTimeout = false;
        }
	}

	public void setTime(long time) {
		this.time = time;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public boolean IsTime_out() {
		return isTimeout;
	}

}
