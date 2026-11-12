
import java.util.ArrayList;
import java.util.List;

public class Performance {

    private static final int TOTAL_NUMBERS = 1_000_000_000;
    private static final int NUM_THREADS = 1_000;
    private static final int RANGE_PER_THREAD = TOTAL_NUMBERS / NUM_THREADS;

    public static void main(String[] args)
    {
        singleThreadTime();
    }

    public static void singleThreadTime()
    {
		List<Thread> threads = new ArrayList<>();
		List<PrimeRunnable> tasks = new ArrayList<>();

		long startTime = System.currentTimeMillis();

		for (int i = 0; i < NUM_THREADS; i++)
		{
			int start = (i * RANGE_PER_THREAD) + 1;
			int end = (i + 1) * RANGE_PER_THREAD;

			PrimeRunnable task = new PrimeRunnable(start, end);
			Thread thread = new Thread(task);

			tasks.add(task);
			threads.add(thread);

			// Start the thread
			thread.start();
		}

		for (Thread thread : threads) {
			try {
				thread.join(); // Blocks until this specific thread dies
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// 3. Tally up the total count from each task instance
		long totalPrimeCount = 0;
		for (PrimeRunnable task : tasks)
		{
			totalPrimeCount += task.getPrimeCount();
		}

		long endTime = System.currentTimeMillis();

		System.out.println("Total numbers found between 1 and 1,000,000: " + totalPrimeCount);
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
	}

    // Task implementing Runnable
    static class PrimeRunnable implements Runnable {
        private final int start;
        private final int end;
        private long primeCount = 0; // Instance field to store the result

        public PrimeRunnable(int start, int end)
        {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run()
        {
            for (int i = start; i <= end; i++)
            {
            	primeCount++;
            }
        }

        public long getPrimeCount() {
            return primeCount;
        }

    }
}
