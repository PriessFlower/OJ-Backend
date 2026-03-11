-- 算法题目初始化数据
-- 创建时间：2026-03-11
USE `oj-system`;

-- ----------------------------
-- 题目1：两数之和（简单）
-- ----------------------------
INSERT INTO `oj_problem` (`title`, `content`, `tags`, `difficulty`, `ans_language`, `answer`, `submit_num`, `accepted_num`, `judge_case`, `judge_config`, `thumb_num`, `favour_num`, `creator`, `del_flag`)
VALUES (
  '两数之和',
  '给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出和为目标值 target 的那两个整数，并返回它们的数组下标。\n\n你可以假设每种输入只会对应一个答案，并且你不能使用两次相同的元素，你可以按任意顺序返回答案。\n\n**输入格式：**\n第一行：整数数组 nums（空格分隔）\n第二行：目标值 target\n\n**输出格式：**\n两个下标（空格分隔，从小到大）\n\n**示例 1：**\n输入：\n2 7 11 15\n9\n输出：\n0 1\n解释：nums[0] + nums[1] = 2 + 7 = 9\n\n**示例 2：**\n输入：\n3 2 4\n6\n输出：\n1 2\n\n**提示：**\n- 2 <= nums.length <= 10^4\n- -10^9 <= nums[i] <= 10^9\n- 只会存在一个有效答案',
  '["数组","哈希表"]',
  0,
  'java',
  'import java.util.*;\npublic class Main {\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);\n        String[] parts = sc.nextLine().trim().split(" ");\n        int n = parts.length;\n        int[] nums = new int[n];\n        for (int i = 0; i < n; i++) nums[i] = Integer.parseInt(parts[i]);\n        int target = Integer.parseInt(sc.nextLine().trim());\n        Map<Integer, Integer> map = new HashMap<>();\n        for (int i = 0; i < n; i++) {\n            int comp = target - nums[i];\n            if (map.containsKey(comp)) {\n                int j = map.get(comp);\n                System.out.println(Math.min(i, j) + " " + Math.max(i, j));\n                return;\n            }\n            map.put(nums[i], i);\n        }\n    }\n}',
  0, 0,
  '[{"input":"2 7 11 15\n9","output":"0 1"},{"input":"3 2 4\n6","output":"1 2"},{"input":"3 3\n6","output":"0 1"}]',
  '{"timeLimit":1000,"memoryLimit":256,"stackLimit":256}',
  0, 0, '1067246875800000001', 0
);

-- ----------------------------
-- 题目3：最大子数组和（中等）
-- ----------------------------
INSERT INTO `oj_problem` (`title`, `content`, `tags`, `difficulty`, `ans_language`, `answer`, `submit_num`, `accepted_num`, `judge_case`, `judge_config`, `thumb_num`, `favour_num`, `creator`, `del_flag`)
VALUES (
  '最大子数组和',
  '给你一个整数数组 nums，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。\n\n**输入格式：**\n一行整数数组 nums（空格分隔）\n\n**输出格式：**\n最大子数组的和\n\n**示例 1：**\n输入：-2 1 -3 4 -1 2 1 -5 4\n输出：6\n解释：连续子数组 [4,-1,2,1] 的和最大，为 6\n\n**示例 2：**\n输入：5 4 -1 7 8\n输出：23\n\n**提示：**\n- 1 <= nums.length <= 10^5\n- -10^4 <= nums[i] <= 10^4',
  '["数组","动态规划","分治"]',
  1,
  'java',
  'import java.util.*;\npublic class Main {\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);\n        String[] parts = sc.nextLine().trim().split(" ");\n        int n = parts.length;\n        int maxSum = Integer.parseInt(parts[0]);\n        int curSum = maxSum;\n        for (int i = 1; i < n; i++) {\n            int x = Integer.parseInt(parts[i]);\n            curSum = Math.max(x, curSum + x);\n            maxSum = Math.max(maxSum, curSum);\n        }\n        System.out.println(maxSum);\n    }\n}',
  0, 0,
  '[{"input":"-2 1 -3 4 -1 2 1 -5 4","output":"6"},{"input":"1","output":"1"},{"input":"5 4 -1 7 8","output":"23"},{"input":"-1 -2 -3","output":"-1"}]',
  '{"timeLimit":1000,"memoryLimit":256,"stackLimit":256}',
  0, 0, '1067246875800000001', 0
);

-- ----------------------------
-- 题目4：反转链表（简单）
-- ----------------------------
INSERT INTO `oj_problem` (`title`, `content`, `tags`, `difficulty`, `ans_language`, `answer`, `submit_num`, `accepted_num`, `judge_case`, `judge_config`, `thumb_num`, `favour_num`, `creator`, `del_flag`)
VALUES (
  '反转链表',
  '给你一个链表，请你反转链表，并返回反转后的链表。\n\n本题以空格分隔的整数序列表示链表节点值，输出同格式的反转结果。\n\n**输入格式：**\n一行整数序列（空格分隔），表示链表各节点的值\n\n**输出格式：**\n反转后的链表节点值（空格分隔）\n\n**示例 1：**\n输入：1 2 3 4 5\n输出：5 4 3 2 1\n\n**示例 2：**\n输入：1 2\n输出：2 1\n\n**提示：**\n- 链表中节点的数目范围是 [1, 5000]\n- -5000 <= Node.val <= 5000',
  '["链表","递归"]',
  0,
  'java',
  'import java.util.*;\npublic class Main {\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);\n        String[] parts = sc.nextLine().trim().split(" ");\n        StringBuilder sb = new StringBuilder();\n        for (int i = parts.length - 1; i >= 0; i--) {\n            if (i < parts.length - 1) sb.append(" ");\n            sb.append(parts[i]);\n        }\n        System.out.println(sb);\n    }\n}',
  0, 0,
  '[{"input":"1 2 3 4 5","output":"5 4 3 2 1"},{"input":"1 2","output":"2 1"},{"input":"1","output":"1"}]',
  '{"timeLimit":1000,"memoryLimit":256,"stackLimit":256}',
  0, 0, '1067246875800000001', 0
);

-- ----------------------------
-- 题目5：最长递增子序列（中等）
-- ----------------------------
INSERT INTO `oj_problem` (`title`, `content`, `tags`, `difficulty`, `ans_language`, `answer`, `submit_num`, `accepted_num`, `judge_case`, `judge_config`, `thumb_num`, `favour_num`, `creator`, `del_flag`)
VALUES (
  '最长递增子序列',
  '给你一个整数数组 nums，找到其中最长严格递增子序列的长度。\n\n子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。\n\n**输入格式：**\n一行整数数组 nums（空格分隔）\n\n**输出格式：**\n最长严格递增子序列的长度\n\n**示例 1：**\n输入：10 9 2 5 3 7 101 18\n输出：4\n解释：最长递增子序列是 [2,3,7,101]，长度为 4\n\n**示例 2：**\n输入：0 1 0 3 2 3\n输出：4\n\n**示例 3：**\n输入：7 7 7 7 7\n输出：1\n\n**提示：**\n- 1 <= nums.length <= 2500\n- -10^4 <= nums[i] <= 10^4',
  '["数组","动态规划","二分查找"]',
  1,
  'java',
  'import java.util.*;\npublic class Main {\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);\n        String[] parts = sc.nextLine().trim().split(" ");\n        int n = parts.length;\n        int[] nums = new int[n];\n        for (int i = 0; i < n; i++) nums[i] = Integer.parseInt(parts[i]);\n        int[] dp = new int[n];\n        Arrays.fill(dp, 1);\n        int ans = 1;\n        for (int i = 1; i < n; i++) {\n            for (int j = 0; j < i; j++) {\n                if (nums[j] < nums[i]) dp[i] = Math.max(dp[i], dp[j] + 1);\n            }\n            ans = Math.max(ans, dp[i]);\n        }\n        System.out.println(ans);\n    }\n}',
  0, 0,
  '[{"input":"10 9 2 5 3 7 101 18","output":"4"},{"input":"0 1 0 3 2 3","output":"4"},{"input":"7 7 7 7 7","output":"1"},{"input":"1","output":"1"}]',
  '{"timeLimit":2000,"memoryLimit":256,"stackLimit":256}',
  0, 0, '1067246875800000001', 0
);

-- ----------------------------
-- 题目2：爬楼梯（简单）
-- ----------------------------
INSERT INTO `oj_problem` (`title`, `content`, `tags`, `difficulty`, `ans_language`, `answer`, `submit_num`, `accepted_num`, `judge_case`, `judge_config`, `thumb_num`, `favour_num`, `creator`, `del_flag`)
VALUES (
  '爬楼梯',
  '假设你正在爬楼梯，需要 n 阶你才能到达楼顶。每次你可以爬 1 或 2 个台阶，你有多少种不同的方法可以爬到楼顶？\n\n**输入格式：**\n一个整数 n\n\n**输出格式：**\n爬到楼顶的方法总数\n\n**示例 1：**\n输入：2\n输出：2\n解释：1+1 或 2，共 2 种方法\n\n**示例 2：**\n输入：3\n输出：3\n解释：1+1+1、1+2、2+1，共 3 种方法\n\n**提示：**\n- 1 <= n <= 45',
  '["动态规划","记忆化搜索","数学"]',
  0,
  'java',
  'import java.util.*;\npublic class Main {\n    public static void main(String[] args) {\n        Scanner sc = new Scanner(System.in);\n        int n = sc.nextInt();\n        if (n <= 2) { System.out.println(n); return; }\n        int a = 1, b = 2;\n        for (int i = 3; i <= n; i++) {\n            int c = a + b;\n            a = b;\n            b = c;\n        }\n        System.out.println(b);\n    }\n}',
  0, 0,
  '[{"input":"2","output":"2"},{"input":"3","output":"3"},{"input":"5","output":"8"},{"input":"10","output":"89"}]',
  '{"timeLimit":1000,"memoryLimit":256,"stackLimit":256}',
  0, 0, '1067246875800000001', 0
);
