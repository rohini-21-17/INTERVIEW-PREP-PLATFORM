package com.interviewprep.service;

import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Replaces OpenAIService entirely.
 *
 * Two responsibilities:
 * 1. getQuestions(topic, difficulty, count) — returns questions from the
 * built-in bank
 * 2. evaluateAnswer(questionText, answerText) — keyword-based scoring (0-10)
 * with feedback
 */
@Service
public class QuestionBankService {

    // ──────────────────────────────────────────────────────────────────────────
    // QUESTION BANK
    // Structure: topic (lowercase) -> difficulty -> list of questions
    // ──────────────────────────────────────────────────────────────────────────

    private static final Map<String, Map<String, List<String>>> BANK = new HashMap<>();

    static {

        // ── Java ──────────────────────────────────────────────────────────────
        put("java", "EASY",
                "What is the difference between JDK, JRE, and JVM?",
                "Explain the difference between == and .equals() in Java.",
                "What are the primitive data types in Java?",
                "What is autoboxing and unboxing in Java?",
                "What is the difference between String, StringBuilder, and StringBuffer?",
                "What is a constructor in Java and what are its types?",
                "Explain access modifiers in Java: public, private, protected, default.",
                "What is the difference between an array and an ArrayList?",
                "What does the 'static' keyword mean in Java?",
                "What is a NullPointerException and how do you avoid it?");
        put("java", "MEDIUM",
                "Explain the concept of Java garbage collection and how it works.",
                "What is the difference between HashMap and LinkedHashMap?",
                "What are generics in Java and why are they useful?",
                "Explain the Iterator pattern and how it is used in Java Collections.",
                "What is the difference between checked and unchecked exceptions?",
                "How does the synchronized keyword work in Java multithreading?",
                "Explain the difference between Comparable and Comparator interfaces.",
                "What is method overloading vs method overriding?",
                "What is the Java Memory Model and what are heap and stack?",
                "Explain the try-with-resources statement in Java.");
        put("java", "HARD",
                "Explain Java's happens-before relationship in the context of concurrency.",
                "What is a deadlock and how do you detect and prevent it in Java?",
                "How does the ConcurrentHashMap differ from Collections.synchronizedMap()?",
                "Explain the Fork/Join framework and when you would use it.",
                "What are soft references, weak references, and phantom references in Java?",
                "Explain the ClassLoader hierarchy and how class loading works in JVM.",
                "What is the difference between volatile and AtomicInteger?",
                "How does Java's memory model handle visibility across threads?",
                "Explain the design of Java's ExecutorService and thread pools.",
                "What are sealed classes in Java and when would you use them?");

        // ── Spring Boot ───────────────────────────────────────────────────────
        put("spring boot", "EASY",
                "What is Spring Boot and how does it differ from Spring Framework?",
                "What is the purpose of @SpringBootApplication annotation?",
                "What is dependency injection and how does Spring implement it?",
                "Explain the difference between @Component, @Service, and @Repository.",
                "What is application.properties used for in Spring Boot?",
                "What is a REST API and how do you create one in Spring Boot?",
                "What does @Autowired do in Spring?",
                "What is the difference between @GetMapping and @PostMapping?",
                "How do you run a Spring Boot application?",
                "What is Spring Initializr?");
        put("spring boot", "MEDIUM",
                "Explain how Spring Security handles authentication and authorization.",
                "What is Spring Data JPA and how does it simplify database access?",
                "What is the difference between @RequestBody and @RequestParam?",
                "Explain the bean lifecycle in Spring.",
                "What is a Spring Boot starter and how does it work?",
                "How do you handle exceptions globally in Spring Boot?",
                "What is the difference between @Controller and @RestController?",
                "Explain Spring Boot's auto-configuration mechanism.",
                "What is @Transactional and when should you use it?",
                "How do you configure CORS in Spring Boot?");
        put("spring boot", "HARD",
                "How do you implement JWT-based stateless authentication in Spring Security?",
                "Explain how Spring Boot's embedded server and actuator work together.",
                "What is the N+1 problem in JPA and how do you resolve it?",
                "How do you implement a custom AuthenticationProvider in Spring Security?",
                "Explain the difference between EAGER and LAZY loading in JPA and their trade-offs.",
                "How would you implement rate limiting in a Spring Boot REST API?",
                "What are Spring Boot Profiles and how do you use them for multi-environment configs?",
                "Explain optimistic vs pessimistic locking in Spring Data JPA.",
                "How do you write integration tests for a Spring Boot REST API?",
                "What is Spring Cloud and how does it relate to microservices?");

        // ── Data Structures ───────────────────────────────────────────────────
        put("data structures", "EASY",
                "What is the difference between a stack and a queue?",
                "What is a linked list and how does it differ from an array?",
                "Explain what a binary tree is.",
                "What is the time complexity of searching in an unsorted array?",
                "What is a hash table and what problem does it solve?",
                "What is a queue and give a real-world example of its use.",
                "What is the difference between a tree and a graph?",
                "What does LIFO mean and which data structure uses it?",
                "What is an array and what are its limitations?",
                "What is a doubly linked list?");
        put("data structures", "MEDIUM",
                "Explain how a binary search tree (BST) works and its time complexities.",
                "What is a heap data structure and when would you use it?",
                "Explain the difference between BFS and DFS traversal.",
                "What is a trie and what problems does it solve efficiently?",
                "What is the difference between a min-heap and a max-heap?",
                "Explain how HashMap handles collisions.",
                "What is a balanced binary tree and why is balance important?",
                "What are the different types of tree traversals (in-order, pre-order, post-order)?",
                "What is a deque (double-ended queue) and when is it useful?",
                "Explain the concept of amortized time complexity with an example.");
        put("data structures", "HARD",
                "Explain AVL trees and the rotations used for rebalancing.",
                "What is a segment tree and what operations does it support efficiently?",
                "Explain how a disjoint set (union-find) data structure works.",
                "What is a Fenwick tree (Binary Indexed Tree) and when is it used?",
                "Explain the difference between a B-tree and a B+ tree.",
                "What is a skip list and how does it achieve O(log n) search?",
                "How does consistent hashing work and why is it used in distributed systems?",
                "Explain the LRU cache implementation using HashMap and LinkedList.",
                "What is a suffix array and how is it constructed?",
                "Explain how a Red-Black tree maintains balance.");

        // ── Algorithms ────────────────────────────────────────────────────────
        put("algorithms", "EASY",
                "What is the difference between linear search and binary search?",
                "What is bubble sort and what is its time complexity?",
                "What does O(n log n) time complexity mean?",
                "What is recursion? Give a simple example.",
                "Explain selection sort.",
                "What is the difference between a greedy algorithm and dynamic programming?",
                "What is Big-O notation used for?",
                "What is insertion sort and when is it efficient?",
                "What is the two-pointer technique?",
                "What is a brute-force algorithm?");
        put("algorithms", "MEDIUM",
                "Explain merge sort and its time and space complexity.",
                "What is quicksort and what is its average vs worst-case complexity?",
                "Explain Dijkstra's algorithm and when you would use it.",
                "What is dynamic programming and explain the concept of memoization.",
                "What is the sliding window technique? Give an example problem.",
                "Explain the concept of divide and conquer with an example.",
                "What is topological sorting and when is it used?",
                "Explain the Floyd-Warshall algorithm.",
                "What is the difference between DFS and BFS in terms of use cases?",
                "What is a greedy algorithm? Give an example.");
        put("algorithms", "HARD",
                "Explain the Knuth-Morris-Pratt (KMP) string matching algorithm.",
                "What is the difference between top-down and bottom-up dynamic programming?",
                "Explain Bellman-Ford algorithm and how it handles negative weights.",
                "What is the travelling salesman problem and why is it NP-hard?",
                "Explain how Kruskal's and Prim's algorithms find the minimum spanning tree.",
                "What is the A* search algorithm and how does it differ from Dijkstra?",
                "Explain the concept of network flow and the Ford-Fulkerson method.",
                "What is amortized analysis and how does it apply to dynamic arrays?",
                "Explain the randomized quicksort and its expected time complexity.",
                "What are NP-complete problems? Give examples.");

        // ── DBMS ──────────────────────────────────────────────────────────────
        put("dbms", "EASY",
                "What is a database and what is a DBMS?",
                "What is the difference between DDL, DML, and DCL in SQL?",
                "What is a primary key and why is it important?",
                "What is the difference between a relational and non-relational database?",
                "What is normalization and why is it used?",
                "What is a foreign key?",
                "What is the difference between DELETE, TRUNCATE, and DROP?",
                "What is a view in a database?",
                "What is an index and what is it used for?",
                "What are CRUD operations?");
        put("dbms", "MEDIUM",
                "Explain ACID properties in database transactions.",
                "What is the difference between INNER JOIN, LEFT JOIN, and FULL OUTER JOIN?",
                "What is database normalization? Explain 1NF, 2NF, and 3NF.",
                "What is a clustered vs non-clustered index?",
                "What is a stored procedure and when would you use one?",
                "Explain the difference between optimistic and pessimistic concurrency control.",
                "What is a transaction and how do COMMIT and ROLLBACK work?",
                "What is database denormalization and when is it appropriate?",
                "Explain the CAP theorem.",
                "What is an ER diagram and what are its components?");
        put("dbms", "HARD",
                "Explain different types of database isolation levels and their trade-offs.",
                "What are phantom reads, dirty reads, and non-repeatable reads?",
                "How does a B+ tree index work internally in a relational database?",
                "Explain the difference between OLTP and OLAP systems.",
                "What is database sharding and when would you use it?",
                "Explain MVCC (Multi-Version Concurrency Control).",
                "What is a distributed transaction and what protocols manage it?",
                "Explain the two-phase commit (2PC) protocol.",
                "What is write-ahead logging (WAL) in databases?",
                "How do you design a schema for a large-scale e-commerce platform?");

        // ── Operating Systems ─────────────────────────────────────────────────
        put("operating systems", "EASY",
                "What is an operating system and what are its main functions?",
                "What is the difference between a process and a thread?",
                "What is virtual memory?",
                "What is a system call?",
                "What is the difference between multiprogramming and multitasking?",
                "What is a context switch?",
                "What is a semaphore?",
                "What are the different states of a process?",
                "What is a scheduler in an OS?",
                "What is a file system?");
        put("operating systems", "MEDIUM",
                "Explain the difference between preemptive and non-preemptive scheduling.",
                "What is a deadlock and what are the four necessary conditions for it?",
                "Explain paging and segmentation in memory management.",
                "What is the difference between a mutex and a semaphore?",
                "What is thrashing in operating systems?",
                "Explain the producer-consumer problem.",
                "What is the difference between internal and external fragmentation?",
                "What are the different CPU scheduling algorithms?",
                "Explain the concept of demand paging.",
                "What is inter-process communication (IPC) and what are its methods?");
        put("operating systems", "HARD",
                "Explain the banker's algorithm for deadlock avoidance.",
                "What is the difference between hard real-time and soft real-time systems?",
                "Explain the concept of copy-on-write in OS memory management.",
                "How does the OS handle page replacement? Explain LRU and FIFO policies.",
                "What is a hypervisor and what are the types of virtualization?",
                "Explain the working of the Linux kernel scheduler (CFS).",
                "What are spinlocks and when should you use them instead of mutexes?",
                "Explain how memory-mapped files work.",
                "What is the difference between a monolithic kernel and a microkernel?",
                "Explain how TLB (Translation Lookaside Buffer) works.");

        // ── Computer Networks ─────────────────────────────────────────────────
        put("computer networks", "EASY",
                "What is the OSI model? Name its 7 layers.",
                "What is the difference between TCP and UDP?",
                "What is an IP address and what is the difference between IPv4 and IPv6?",
                "What is DNS and how does it work?",
                "What is a MAC address?",
                "What is the difference between HTTP and HTTPS?",
                "What is a router and how does it differ from a switch?",
                "What is a firewall?",
                "What is a subnet mask?",
                "What is latency in networking?");
        put("computer networks", "MEDIUM",
                "Explain the TCP three-way handshake.",
                "What is the difference between HTTP/1.1 and HTTP/2?",
                "What is NAT (Network Address Translation) and why is it used?",
                "Explain how ARP (Address Resolution Protocol) works.",
                "What is a socket in networking?",
                "What is the difference between a hub, switch, and router?",
                "What is SSL/TLS and how does it secure communication?",
                "Explain the difference between TCP flow control and congestion control.",
                "What is DHCP and how does it assign IP addresses?",
                "What is the difference between symmetric and asymmetric encryption?");
        put("computer networks", "HARD",
                "Explain how BGP (Border Gateway Protocol) works.",
                "What is QUIC protocol and why was it developed?",
                "Explain the TCP sliding window mechanism in detail.",
                "What is a CDN and how does it reduce latency?",
                "How does HTTPS work end-to-end including certificate validation?",
                "What are the differences between REST and gRPC?",
                "Explain how WebSockets differ from HTTP long polling.",
                "What is anycast routing and when is it used?",
                "Explain the concept of network congestion and TCP's response to it.",
                "How does OSPF work as a link-state routing protocol?");

        // ── System Design ─────────────────────────────────────────────────────
        put("system design", "EASY",
                "What is scalability in the context of system design?",
                "What is the difference between horizontal and vertical scaling?",
                "What is a load balancer and why is it used?",
                "What is caching and why is it important?",
                "What is a microservices architecture?",
                "What is an API gateway?",
                "What is the difference between SQL and NoSQL databases?",
                "What is a message queue?",
                "What does high availability mean?",
                "What is a CDN (Content Delivery Network)?");
        put("system design", "MEDIUM",
                "How would you design a URL shortener like bit.ly?",
                "Explain the concept of consistent hashing.",
                "What is the CAP theorem and how does it affect system design decisions?",
                "What is database replication and what are its types?",
                "Explain the difference between synchronous and asynchronous communication.",
                "What is a rate limiter and how would you implement one?",
                "What is event-driven architecture?",
                "How does a distributed cache like Redis work?",
                "What is the difference between a monolith and microservices?",
                "What is a circuit breaker pattern in microservices?");
        put("system design", "HARD",
                "How would you design Twitter's tweet feed for 100 million users?",
                "Explain how Google's Bigtable or Cassandra handles distributed writes.",
                "How would you design a distributed rate limiter?",
                "Explain the saga pattern for distributed transactions.",
                "How would you design a real-time collaborative document editor?",
                "What is the difference between eventual consistency and strong consistency?",
                "How would you design a notification system that handles millions of users?",
                "Explain how Kafka achieves high throughput and fault tolerance.",
                "How would you design a distributed ID generator (like Snowflake)?",
                "What are the trade-offs between using a single large database vs sharding?");

        // ── OOP Concepts ──────────────────────────────────────────────────────
        put("oop concepts", "EASY",
                "What are the four pillars of Object-Oriented Programming?",
                "What is the difference between a class and an object?",
                "What is encapsulation and why is it important?",
                "What is inheritance and what problem does it solve?",
                "What is polymorphism? Give an example.",
                "What is abstraction in OOP?",
                "What is the difference between an abstract class and an interface?",
                "What is a constructor?",
                "What is method overriding?",
                "What is the 'this' keyword in Java?");
        put("oop concepts", "MEDIUM",
                "Explain the SOLID principles of object-oriented design.",
                "What is the difference between composition and inheritance? When do you prefer composition?",
                "What is the Liskov Substitution Principle with an example?",
                "What is a design pattern? Name and explain three common patterns.",
                "What is the difference between an interface and an abstract class in Java 8+?",
                "What is coupling and cohesion in OOP?",
                "Explain the Factory design pattern.",
                "What is the Singleton pattern and what are its drawbacks?",
                "What is method hiding vs method overriding?",
                "Explain the Open/Closed Principle with an example.");
        put("oop concepts", "HARD",
                "Explain the Dependency Inversion Principle and how dependency injection implements it.",
                "What is the difference between the Strategy and State design patterns?",
                "Explain the Observer pattern and give a real-world use case.",
                "How does the Decorator pattern differ from inheritance?",
                "What is the Visitor pattern and when would you use it?",
                "Explain how to apply SOLID principles to avoid code that is hard to maintain.",
                "What is the difference between the Proxy and Decorator patterns?",
                "Explain the Command pattern and its use in implementing undo functionality.",
                "What is the Template Method pattern?",
                "How does the Composite pattern work?");

        // ── SQL ───────────────────────────────────────────────────────────────
        put("sql", "EASY",
                "What is the difference between WHERE and HAVING clauses?",
                "What is a JOIN in SQL? What are the different types?",
                "What is the purpose of the GROUP BY clause?",
                "What is the difference between UNION and UNION ALL?",
                "What does SELECT DISTINCT do?",
                "What is a subquery?",
                "What is the difference between COUNT(*) and COUNT(column)?",
                "What does ORDER BY do in SQL?",
                "What is a NULL value in SQL?",
                "What is the difference between CHAR and VARCHAR?");
        put("sql", "MEDIUM",
                "Explain the difference between INNER JOIN, LEFT JOIN, RIGHT JOIN, and FULL OUTER JOIN.",
                "What is a correlated subquery and how does it differ from a regular subquery?",
                "What are window functions in SQL? Give an example using ROW_NUMBER().",
                "How do indexes improve query performance?",
                "What is a CTE (Common Table Expression) and how is it different from a subquery?",
                "What is the difference between a clustered and non-clustered index?",
                "Explain the SQL execution order of a SELECT statement.",
                "What is query optimization and what steps does a query planner take?",
                "What is a self-join and when would you use one?",
                "What is database normalization and how does it affect SQL queries?");
        put("sql", "HARD",
                "How would you find the second-highest salary in a table without using LIMIT/TOP?",
                "Explain how to detect and remove duplicate rows from a large table efficiently.",
                "What is the difference between EXISTS and IN in SQL? Which is more performant?",
                "Explain how partitioning works in large SQL databases.",
                "How would you write a query to find the running total of sales per day?",
                "What is a materialized view and how does it differ from a regular view?",
                "Explain how EXPLAIN/EXPLAIN ANALYZE works and how to read a query plan.",
                "What are lateral joins and when are they useful?",
                "How do you handle hierarchical data (like org charts) in SQL?",
                "What is the difference between OLTP and OLAP query patterns?");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // KEYWORD EVALUATION ENGINE
    // Maps topic keywords to expected concepts. Score = matched keywords / total
    // expected * 10
    // ──────────────────────────────────────────────────────────────────────────

    private static final Map<String, List<String>> TOPIC_KEYWORDS = new HashMap<>();

    static {
        TOPIC_KEYWORDS.put("java", List.of(
                "class", "object", "interface", "abstract", "inheritance", "polymorphism",
                "encapsulation", "jvm", "heap", "stack", "garbage", "collection", "thread",
                "synchronized", "exception", "generic", "iterator", "stream", "lambda",
                "override", "overload", "static", "final", "volatile", "constructor"));
        TOPIC_KEYWORDS.put("spring boot", List.of(
                "bean", "autowired", "injection", "dependency", "controller", "service",
                "repository", "rest", "http", "request", "response", "jpa", "security",
                "jwt", "token", "filter", "configuration", "annotation", "transaction",
                "mapping", "endpoint", "cors", "starter", "autoconfiguration"));
        TOPIC_KEYWORDS.put("data structures", List.of(
                "array", "list", "tree", "graph", "stack", "queue", "heap", "hash",
                "node", "pointer", "traversal", "binary", "search", "insert", "delete",
                "complexity", "time", "space", "balanced", "bfs", "dfs", "linked"));
        TOPIC_KEYWORDS.put("algorithms", List.of(
                "sort", "search", "complexity", "recursion", "dynamic", "greedy",
                "divide", "conquer", "bfs", "dfs", "graph", "tree", "memoization",
                "optimization", "backtracking", "binary", "merge", "quick", "heap",
                "time", "space", "big-o", "iteration", "pointer"));
        TOPIC_KEYWORDS.put("dbms", List.of(
                "table", "row", "column", "key", "index", "join", "query", "transaction",
                "acid", "normalization", "schema", "relation", "sql", "primary", "foreign",
                "constraint", "view", "procedure", "trigger", "isolation", "commit", "rollback"));
        TOPIC_KEYWORDS.put("operating systems", List.of(
                "process", "thread", "scheduler", "memory", "virtual", "paging",
                "segmentation", "deadlock", "mutex", "semaphore", "interrupt", "kernel",
                "system call", "context", "switch", "cpu", "io", "file", "disk", "cache"));
        TOPIC_KEYWORDS.put("computer networks", List.of(
                "tcp", "udp", "ip", "http", "dns", "packet", "protocol", "socket",
                "port", "router", "switch", "osi", "layer", "bandwidth", "latency",
                "ssl", "tls", "encryption", "subnet", "routing", "handshake", "ack"));
        TOPIC_KEYWORDS.put("system design", List.of(
                "scale", "load", "balance", "cache", "database", "service", "api",
                "availability", "consistency", "partition", "replication", "sharding",
                "queue", "async", "sync", "microservice", "cdn", "latency", "throughput",
                "fault", "tolerance", "distributed", "cap", "eventual"));
        TOPIC_KEYWORDS.put("oop concepts", List.of(
                "class", "object", "inherit", "polymorphism", "encapsulation", "abstraction",
                "interface", "abstract", "override", "solid", "single", "open", "closed",
                "liskov", "dependency", "composition", "coupling", "cohesion", "pattern",
                "factory", "singleton", "observer", "decorator", "strategy"));
        TOPIC_KEYWORDS.put("sql", List.of(
                "select", "from", "where", "join", "group", "having", "order", "index",
                "query", "table", "column", "row", "aggregate", "count", "sum", "avg",
                "subquery", "cte", "window", "partition", "union", "distinct", "null", "key"));
    }

    // ──────────────────────────────────────────────────────────────────────────
    // PUBLIC API
    // ──────────────────────────────────────────────────────────────────────────

    /**
     * Returns up to {@code count} questions for the given topic and difficulty.
     * Falls back to any difficulty if the exact combination has fewer questions.
     */
    public List<String> getQuestions(String topic, String difficulty, int count) {
        String key = topic.toLowerCase().trim();
        String diff = difficulty.toUpperCase().trim();

        List<String> pool = findPool(key, diff);

        if (pool.isEmpty()) {
            // Try partial match (e.g. "Java Core" -> "java")
            for (String bankKey : BANK.keySet()) {
                if (key.contains(bankKey) || bankKey.contains(key)) {
                    pool = findPool(bankKey, diff);
                    if (!pool.isEmpty())
                        break;
                }
            }
        }

        if (pool.isEmpty()) {
            return List.of("Explain the key concepts and best practices of " + topic + ".",
                    "What are the most important things to know about " + topic + "?",
                    "Describe a challenging problem you solved using " + topic + ".",
                    "What are common pitfalls when working with " + topic + "?",
                    "How would you explain " + topic + " to a junior developer?")
                    .subList(0, Math.min(count, 5));
        }

        List<String> shuffled = new ArrayList<>(pool);
        Collections.shuffle(shuffled);
        return shuffled.subList(0, Math.min(count, shuffled.size()));
    }

    /**
     * Keyword-based evaluation. Returns a map with "score" (int 0-10) and
     * "feedback" (String).
     */
    public Map<String, Object> evaluateAnswer(String questionText, String answerText) {
        if (answerText == null || answerText.trim().length() < 10) {
            return result(0, "Your answer is too short. Please provide a detailed explanation.");
        }

        String lowerAnswer = answerText.toLowerCase();
        String lowerQuestion = questionText.toLowerCase();

        // Detect which topic this question belongs to
        List<String> relevantKeywords = detectKeywords(lowerQuestion, lowerAnswer);

        int wordCount = answerText.trim().split("\\s+").length;
        int keywordsHit = countKeywordHits(lowerAnswer, relevantKeywords);
        int totalKeywords = Math.max(relevantKeywords.size(), 1);

        // Base score from keyword coverage (0-7)
        double keywordRatio = (double) keywordsHit / totalKeywords;
        int keywordScore = (int) Math.round(keywordRatio * 7);

        // Length bonus (0-2): good answers have 50-300 words
        int lengthBonus = 0;
        if (wordCount >= 20)
            lengthBonus = 1;
        if (wordCount >= 60)
            lengthBonus = 2;

        // Structure bonus (0-1): uses examples, comparisons, or definitions
        int structureBonus = 0;
        if (lowerAnswer.contains("example") || lowerAnswer.contains("for instance")
                || lowerAnswer.contains("such as") || lowerAnswer.contains("e.g")
                || lowerAnswer.contains("because") || lowerAnswer.contains("therefore")
                || lowerAnswer.contains("however") || lowerAnswer.contains("difference")) {
            structureBonus = 1;
        }

        int rawScore = keywordScore + lengthBonus + structureBonus;
        int score = Math.max(1, Math.min(10, rawScore)); // clamp 1-10

        String feedback = buildFeedback(score, keywordsHit, totalKeywords, wordCount, relevantKeywords, lowerAnswer);

        return result(score, feedback);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // HELPERS
    // ──────────────────────────────────────────────────────────────────────────

    private List<String> findPool(String key, String diff) {
        Map<String, List<String>> byDiff = BANK.get(key);
        if (byDiff == null)
            return Collections.emptyList();
        List<String> exact = byDiff.get(diff);
        if (exact != null && !exact.isEmpty())
            return exact;
        // Merge all difficulties as fallback
        List<String> merged = new ArrayList<>();
        byDiff.values().forEach(merged::addAll);
        return merged;
    }

    private List<String> detectKeywords(String question, String answer) {
        // Find best matching topic by counting keyword hits from each topic in the
        // combined text
        String combined = question + " " + answer;
        String bestTopic = null;
        int bestHits = 0;
        for (Map.Entry<String, List<String>> entry : TOPIC_KEYWORDS.entrySet()) {
            int hits = countKeywordHits(combined, entry.getValue());
            if (hits > bestHits) {
                bestHits = hits;
                bestTopic = entry.getKey();
            }
        }
        // General technical keywords as fallback
        List<String> fallback = List.of(
                "explain", "define", "describe", "implement", "difference", "example",
                "use case", "advantage", "disadvantage", "concept", "method", "approach");
        return bestTopic != null ? TOPIC_KEYWORDS.get(bestTopic) : fallback;
    }

    private int countKeywordHits(String text, List<String> keywords) {
        int count = 0;
        for (String kw : keywords) {
            if (text.contains(kw.toLowerCase()))
                count++;
        }
        return count;
    }

    private String buildFeedback(int score, int hits, int total, int wordCount,
            List<String> keywords, String lowerAnswer) {
        StringBuilder sb = new StringBuilder();

        if (score >= 9) {
            sb.append(
                    "Excellent answer! You demonstrated strong command of the topic with relevant terminology and clear explanation.");
        } else if (score >= 7) {
            sb.append("Good answer. You covered the key points well.");
        } else if (score >= 5) {
            sb.append("Decent attempt. Your answer shows basic understanding but could be more comprehensive.");
        } else if (score >= 3) {
            sb.append("Partial answer. You touched on some concepts but missed several important points.");
        } else {
            sb.append("Your answer needs significant improvement. Make sure to address the core concepts directly.");
        }

        // Add keyword-specific feedback
        List<String> missed = new ArrayList<>();
        for (String kw : keywords) {
            if (!lowerAnswer.contains(kw.toLowerCase())) {
                missed.add(kw);
            }
        }
        if (!missed.isEmpty() && score < 8) {
            int showCount = Math.min(4, missed.size());
            sb.append(" Consider including concepts like: ");
            sb.append(String.join(", ", missed.subList(0, showCount)));
            sb.append(".");
        }

        if (wordCount < 20 && score < 8) {
            sb.append(" Try to elaborate more — a detailed answer with examples scores higher.");
        }

        return sb.toString();
    }

    private Map<String, Object> result(int score, String feedback) {
        Map<String, Object> map = new HashMap<>();
        map.put("score", score);
        map.put("feedback", feedback);
        return map;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // STATIC HELPER to populate the bank
    // ──────────────────────────────────────────────────────────────────────────

    private static void put(String topic, String difficulty, String... questions) {
        BANK.computeIfAbsent(topic, k -> new HashMap<>())
                .put(difficulty, new ArrayList<>(Arrays.asList(questions)));
    }
}