import java.math.BigDecimal;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println(minSpeedOnTime(new int[]{1, 1, 10000}, 2.01));
    }

    public static int minSpeedOnTime(int[] dist, double hour) {
        if (hour <= dist.length - 1) return -1;
        int diSum = 0;
        int avgSpeed;
        double actTime = 0;
        double waitingTime;
        for (int d : dist) {
            diSum += d;
        }
        avgSpeed = (int) Math.ceil((double) diSum / hour);
        for (int i = 0; i < dist.length - 1; i++) {
            actTime += Math.ceil((double) dist[i] / avgSpeed);
        }
        actTime += (double) dist[dist.length - 1] / avgSpeed;
        waitingTime = actTime - hour;
        while (waitingTime > 0) {
            waitingTime -= (double) dist[dist.length - 1] / avgSpeed - (double) dist[dist.length - 1] / (avgSpeed + 1);
            avgSpeed++;
            for (int d : dist) {
                if (d % avgSpeed == 0) {
                    waitingTime--;
                }
            }
        }
        return avgSpeed;
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] nums = new int[m];
        int k = 0; //indicate the location of nums1
        int j = 0; //indicate the location of nums2
        for (int i = 0; i < m; i++)
            nums[i] = nums1[i];
        for (int i : nums) {  //for every element in nums, find the first element which is larger than it.
            while (j < nums2.length && nums2[j] < i) {//the length judgment should happen first in case out of boundary
                nums1[k++] = nums2[j++];
            }
            nums1[k++] = i;
        }
        for (; j < nums.length; j++)
            //nums firstly ends
            nums1[k++] = nums2[j];
    }

    public int removeElement(int[] nums, int val) {
        int k = 0;
        for (int i : nums) {
            if (i != val) nums[k++] = i;
        }
        return k;
    }

    public int removeDuplicates(int[] nums) {
        int[] nums1 = new int[nums.length];
        int k = 0;
        for (int i = 0; i < nums.length; i++) {
            nums1[i] = nums[i];
        }
        for (int i : nums1) {
            boolean flag = false;
            for (int j = 0; j < k; j++)
                if (i == nums[j]) {
                    flag = true;
                }
            if (!flag) nums[k++] = i;
        }
        return k;
    }

    public int removeDuplicate2(int[] nums) {
        int k = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] != nums[i + 1]) nums[k++] = nums[i];
        }//有的题目不需要复制原数组，在更新过程中不影响到后面的数据就可以
        nums[k++] = nums[nums.length - 1];
        return k;
    }

    public int removeDuplicatesMedium(int[] nums) {
        if (nums.length < 3) return nums.length;
        int k = 0;
        int flag = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) flag = 1;
            else flag++;
            if (flag < 2) nums[k++] = nums[i];
        }
        return k;
    }

    public int majorityElement(int[] nums) {
        Map<Integer, Integer> sum = new HashMap<>();
        int max = 0;
        for (int i : nums) {
            Integer count = 0;
            if (sum.containsKey(i)) {
                count = sum.get(i);
                count++;
            } else count = 1;
            if (count >= nums.length / 2) return i;
            sum.put(i, count);
        }
        return -1;
    }
    public void rotate(int[] nums, int k) {
     for(int i=0;i<k;i++){
         int end=nums[nums.length-1];
         for(int j=1;j< nums.length;j++){
             nums[j]=nums[j-1];
         }
         nums[0]=end;
    }}
}