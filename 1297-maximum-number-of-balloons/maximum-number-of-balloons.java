class Solution {
    public int maxNumberOfBalloons(String text) {
        int[] freq = new int[5];
        String s = "balon";
        for(int i=0;i<text.length();i++) {
            char ch = text.charAt(i);
            for(int j=0;j<5;j++) {
                if(ch == s.charAt(j)) {
                    freq[j]++;
                }
            }
        }
        freq[2] >>=1;
        freq[3] >>=1;
       Arrays.sort(freq);
       int minCount = freq[0];
       return minCount;
    }
}