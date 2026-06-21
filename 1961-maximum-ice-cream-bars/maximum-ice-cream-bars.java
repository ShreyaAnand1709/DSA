class Solution {
    public int maxIceCream(int[] costs, int coins) {
       Arrays.sort(costs); 
       int leftcoins = 0;
       int candy = 0;
       for(int i=0;i<costs.length;i++) {
        if(costs[i] <= coins) {
            coins -=costs[i];
            candy++;
        }
       }
       return candy;
    }
}