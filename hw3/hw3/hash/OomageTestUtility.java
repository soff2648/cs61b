package hw3.hash;

import java.util.ArrayList;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {

        List<Oomage>[] buckets = new ArrayList[M];
        for (int i = 0; i < M; i++) {
            buckets[i] = new ArrayList<Oomage>();
        }


        for (Oomage o: oomages) {
            int numBuckets = (o.hashCode() & 0x7FFFFFFF) % M;
            buckets[numBuckets].add(o);
        }
        int N = oomages.size();

        for (var list: buckets) {
            if (list.size() < (double) N / 50 || list.size() > (double) N / 2.5) {
                return false;
            }
        }

        return true;
    }
}
