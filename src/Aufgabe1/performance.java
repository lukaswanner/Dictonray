package Aufgabe1;

public class performance {

    public long insert1(Dictionary dict) {
        long startTime = System.nanoTime();

        for (int i = 0; i < 8000; i++) {
            dict.insert(i,i);
        }
        long endTime = System.nanoTime();
        long duration = (endTime -startTime);
        return duration;
    }

    public long insert2(Dictionary dict) {
        long startTime = System.nanoTime();
        for (int i = 0; i < 16000; i++) {
            dict.insert(i,i);
        }
        long endTime = System.nanoTime();
        long duration = (endTime -startTime);
        return duration;

    }

    public long search1(Dictionary dict) {
        long startTime = System.nanoTime();

        for (int i = 0; i < 8000; i++) {
            dict.search(i);
        }
        long endTime = System.nanoTime();
        long duration = (endTime -startTime);
        return duration;
    }

    public long search2(Dictionary dict) {
        long startTime = System.nanoTime();

        for (int i = 0; i < 16000; i++) {
            dict.search(i);
        }
        long endTime = System.nanoTime();
        long duration = (endTime -startTime);
        return duration;
    }

    public long search3(Dictionary dict) {
        long startTime = System.nanoTime();

        for (int i = 8000; i < 16000; i++) {
            dict.search(i);
        }
        long endTime = System.nanoTime();
        long duration = (endTime -startTime);
        return duration;
    }

}
