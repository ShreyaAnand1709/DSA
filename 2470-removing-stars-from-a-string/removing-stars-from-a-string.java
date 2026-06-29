class Solution {
    public String removeStars(String s) {
        Stack<Character> st = new Stack<>();
        for(int i=0;i<s.length();i++) {
            char ch = s.charAt(i);
            if(ch != '*') {
                st.push(ch);
            } else {
                st.pop();
            }
        }
        StringBuilder result = new StringBuilder();
        while(!st.isEmpty()) {
            result.append(st.pop());
        }
        result.reverse();
        String res = new String(result);
        return res;
    }
}