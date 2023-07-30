import java.math.BigDecimal;
import java.util.*;

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

    public class T4 {
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

    public static void reverse(int[] nums, int start, int end) {
        while (end > start) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

    public void rotate(int[] nums, int k) {
        k %= nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }

    public class T7 {
        //1遍历过程中可以知道已遍历元素的最值
        //2去找一个元素后面元素的最值需要遍历这个元素后面的所有元素
        //能用1不用2
        public int maxProfit(int[] prices) {
            //对每一个元素找他后面最高的卖出点，在找总最高例如利润
            //每个元素后面最高卖出点每个元素都需要遍历 O(n)
            int maxProfit = 0;
            int frontMax = 0;
            for (int i = 1; i < prices.length; i++) {
                if (frontMax < prices[i]) frontMax = prices[i];
            }
            maxProfit = frontMax - prices[0];
            for (int i = 1; i < prices.length; i++) {
                if (prices[i] == frontMax) {
                    frontMax = 0;
                    for (int j = i + 1; j < prices.length; j++) {
                        if (frontMax < prices[j]) frontMax = prices[j];
                    }
                }
                maxProfit = maxProfit < frontMax - prices[i] ? frontMax - prices[i] : maxProfit;
            }
            if (maxProfit > 0) return maxProfit;
            else return 0;

            //总复杂度：最小O(n) 最大O(n^2)
        }

        public int maxProfit_Nice(int[] prices) {
            //对每一个元素找他前面最低的买入点得这个点最高的利润，再找总最高利润
            //每个元素之前最低买入点前面就记录好了 O(1)
            int buy = Integer.MAX_VALUE, sell = 0;
            for (int i = 0; i < prices.length; i++) {
                buy = Math.min(buy, prices[i]);
                sell = Math.max(sell, prices[i] - buy);
            }
            return sell;
            //总复杂度：O(n)
        }
    }

    class T8 {
        //贪心
        public int maxProfit(int[] prices) {
            int sum = 0;
            for (int i = 0; i < prices.length - 1; i++) {
                sum += prices[i] < prices[i + 1] ? prices[i + 1] - prices[i] : 0;
            }
            return sum;
        }
    }
    class T9 {
        //递归
        public boolean canJump(int[] nums) {
            return jumpTo(nums,nums.length-1);
        }
        public boolean jumpTo(int[] nums,int index){
            if(index==0)
                return true;
            for(int i= index-1;i>=0;i--){
                if(nums[i]>=index-i)
                    return jumpTo(nums,i);
            }
            return false;
        }
    }
    class T10 {
        //从数组前面开始递归
        public int jump(int[] nums) {
            return jumpTo(nums, nums.length - 1, 0);
        }

        public int jumpTo(int[] nums, int index, int deep) {
            if (index == 0)
                return deep;
            for (int i = 0; i < index; i++) {
                if (nums[i] >= index - i)
                    return jumpTo(nums, i,++deep);
            }
            return -1;
        }
        //nice!为每个元素找出从他及之前的元素最远能走到的地方w
        public int jump_Nice(int[] nums) {

            for(int i = 1; i < nums.length; i++)
            {
                nums[i] = Math.max(nums[i] + i, nums[i-1]);
            }

            int ind = 0;
            int ans = 0;

            while(ind < nums.length - 1)
            {
                ans++;
                ind = nums[ind];
            }

            return ans;
        }
    }
    class T11 {
        //也可以再排序后使用二分查找找第一个比length-index》=的citation，用length-index作为ans
        public int hIndex(int[] citations) {
            Arrays.sort(citations);
            int ans=0;
            for(int i=citations.length-1;i>=0;i--){
                if (citations[i]>ans) {
                    ans++;
                }else return ans;
            }
            return ans;
        }
    }
    class RandomizedSet {

        HashSet<Integer> hashSet=new HashSet<Integer>();
        Random random=new Random();
        public RandomizedSet() {
        }

        public boolean insert(int val) {
            if(hashSet.contains(val))
                return false;
            else {
                hashSet.add(val);
                return true;
            }
        }
        public boolean remove(int val) {
            if (hashSet.contains(val)){
                hashSet.remove(val);
                return true;
            }else
                return false;
        }

        public int getRandom() {

        }
    }
    class T13 {
        public int[] productExceptSelf(int[] nums) {
            int[] products= new int[2*nums.length];
            int[] ans=new int[nums.length];
            products[0]=1;
            products[products.length-1]=1;
            for(int i=0;i<nums.length-1;i++){
                products[i+1]=nums[i]*products[i];
                products[products.length-i-2]=nums[nums.length-i-1]*products[products.length-i-1];
            }
            for(int i=0;i<nums.length;i++){
                ans[i]=products[i]*products[i+nums.length];
            }
            return ans;
        }
    }
}
