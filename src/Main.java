import java.math.BigDecimal;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        int[] heights={0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println(T16.trap(heights));

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
            return 0;
        }
    }
    class T13 {
        //Space complexity: ans stores prefix, another variable stores suffix.
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
    class T14 {
        //两个基本事实：
        //如果n不是解，则数组所有元素之和为负
        //如果从n开始，到n+m个加油站tank为赤字，则n到n+m都不能作为起点。
        //原因是：车子每到一站的剩余油量必>=0，因此自变量是起始点，应变量是n+m站时车子的剩余油量的函数是一个单减函数，不论从n到n+m的每一个作为起始点应变量都不可能必n作为起始点大，因此n到n+m作为起始点每一种情况车子到n+m站时车子的剩余油量都为负
        public int canCompleteCircuit(int[] gas, int[] cost) {
            int n = gas.length;
            int total_surplus = 0;
            int surplus = 0;
            int start = 0;

            for(int i = 0; i < n; i++){
                total_surplus += gas[i] - cost[i];
                surplus += gas[i] - cost[i];
                if(surplus < 0){
                    surplus = 0;
                    start = i + 1;
                }
            }
            return (total_surplus < 0) ? -1 : start;
        }
    }
    class T15 {
        //the problem of sequential solution is that smaller element change will make larger neighbor change
        //which is computing dependency, way to eliminate it:
        //comparing left neighbor: left to right
        //right neighbor: right to left
        public int candy(int[] ratings) {
            int n=ratings.length;
            int[] res=new int[n];
            Arrays.fill(res,1);
            for(int i=1;i<n;i++){
                if(ratings[i]>ratings[i-1]){
                    res[i]=res[i-1]+1; //computing dependency
                }
            }
            for (int i=n-1;i>0;i--){
                if(ratings[i-1]>ratings[i]){
                    res[i-1]=Math.max(res[i]+1,res[i-1]); //computing dependency
                }
            }
            int sum=0;
            for(int r:res) sum+=r;
            return sum;
        }
    }
    class T16 {
        static public int trap(int[] height) {
            int ans=0;
            int vertical=0;
            int horizon=height.length;
            int[] walls=new int[height.length];//记录水池墙的拐角情况
            Arrays.fill(walls,-1);
            for(int i=0;i<height.length;i++){
                int j;
                for(j=0;j<horizon;j++){
                    if(height[i]>=walls[j])
                        break;
                }
                if(j!=horizon){  //如果存在小于等于当前高度，加入ans
                    ans+=(height[i]-walls[j])*(i-j);
                    vertical=height[i]; //代表
                    for(int k=j;k<i;k++){
                        if(walls[k]!=-1)

                    }
                }
                if(height[i]>height[i+1])
                    walls[i]=height[i];
                else
                    walls[i]=-1;
            }
            return ans;
        }
    }
    class T17 {
        public int trap(int[] height) {
            if (height == null || height.length == 0) {
                return 0;
            }
            int left = 0; int right = height.length - 1; // Pointers to both ends of the array.
            int maxLeft = 0; int maxRight = 0;

            int totalWater = 0;
            while (left < right) {
                // Water could, potentially, fill everything from left to right, if there is nothing in between.
                if (height[left] < height[right]) {
                    // If the current elevation is greater than the previous maximum, water cannot occupy that point at all.
                    // However, we do know that everything from maxLeft to the current index, has been optimally filled, as we've
                    // been adding water to the brim of the last maxLeft.
                    if (height[left] >= maxLeft) {
                        // So, we say we've found a new maximum, and look to see how much water we can fill from this point on.
                        maxLeft = height[left];
                        // If we've yet to find a maximum, we know that we can fill the current point with water up to the previous
                        // maximum, as any more will overflow it. We also subtract the current height, as that is the elevation the
                        // ground will be at.
                    } else {
                        totalWater += maxLeft - height[left];
                    }
                    // Increment left, we'll now look at the next point.
                    left++;
                    // If the height at the left is NOT greater than height at the right, we cannot fill from left to right without over-
                    // flowing; however, we do know that we could potentially fill from right to left, if there is nothing in between.
                } else {
                    // Similarly to above, we see that we've found a height greater than the max, and cannot fill it whatsoever, but
                    // everything before is optimally filled
                    if (height[right] >= maxRight) {
                        // We can say we've found a new maximum and move on.
                        maxRight = height[right];
                        // If we haven't found a greater elevation, we can fill the current elevation with maxRight - height[right]
                        // water.
                    } else {
                        totalWater += maxRight - height[right];
                    }
                    // Decrement left, we'll look at the next point.
                    right--;
                }
            }
            // Return the sum we've been adding to.
            return totalWater;
        }
    }
}
