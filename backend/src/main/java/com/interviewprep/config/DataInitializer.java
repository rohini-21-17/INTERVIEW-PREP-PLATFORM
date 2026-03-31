package com.interviewprep.config;

import com.interviewprep.model.Question;
import com.interviewprep.model.Topic;
import com.interviewprep.repository.QuestionRepository;
import com.interviewprep.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final TopicRepository topicRepository;
    private final QuestionRepository questionRepository;

    // ── Topic seed data ────────────────────────────────────────────────────────
    private static final List<String[]> DEFAULT_TOPICS = List.of(
            new String[] { "Java", "Core Java, OOP, Collections, Multithreading, JVM" },
            new String[] { "Spring Boot", "REST APIs, Spring Security, JPA, Dependency Injection" },
            new String[] { "Data Structures", "Arrays, Linked Lists, Trees, Graphs, Heaps" },
            new String[] { "Algorithms", "Sorting, Searching, Dynamic Programming, Recursion" },
            new String[] { "DBMS", "SQL, Normalization, Transactions, Indexes, ACID" },
            new String[] { "Operating Systems", "Processes, Threads, Memory Management, Scheduling" },
            new String[] { "Computer Networks", "TCP/IP, HTTP, DNS, OSI Model, Sockets" },
            new String[] { "System Design", "Scalability, Load Balancing, Caching, Microservices" },
            new String[] { "OOP Concepts", "Encapsulation, Inheritance, Polymorphism, Abstraction" },
            new String[] { "SQL", "Queries, Joins, Aggregations, Subqueries, Indexes" });

    // ── Question seed data (topic name → difficulty → questions) ───────────────
    private static final Map<String, Map<String, List<String>>> SEED_QUESTIONS = Map.of(

            "Java", Map.of(
                    "EASY", List.of(
                            "What is the difference between JDK, JRE, and JVM?",
                            "Explain the difference between == and .equals() in Java.",
                            "What are the primitive data types in Java?",
                            "What is a constructor in Java and what are its types?",
                            "What does the 'static' keyword mean in Java?"),
                    "MEDIUM", List.of(
                            "Explain the concept of Java garbage collection and how it works.",
                            "What is the difference between HashMap and LinkedHashMap?",
                            "What are generics in Java and why are they useful?",
                            "What is the difference between checked and unchecked exceptions?",
                            "How does the synchronized keyword work in Java multithreading?"),
                    "HARD", List.of(
                            "Explain Java's happens-before relationship in the context of concurrency.",
                            "What is a deadlock and how do you detect and prevent it in Java?",
                            "How does the ConcurrentHashMap differ from Collections.synchronizedMap()?",
                            "What are soft references, weak references, and phantom references in Java?",
                            "Explain the ClassLoader hierarchy and how class loading works in JVM.")),

            "Spring Boot", Map.of(
                    "EASY", List.of(
                            "What is Spring Boot and how does it differ from Spring Framework?",
                            "What is the purpose of @SpringBootApplication annotation?",
                            "What is dependency injection and how does Spring implement it?",
                            "Explain the difference between @Component, @Service, and @Repository.",
                            "What does @Autowired do in Spring?"),
                    "MEDIUM", List.of(
                            "Explain how Spring Security handles authentication and authorization.",
                            "What is Spring Data JPA and how does it simplify database access?",
                            "What is the difference between @RequestBody and @RequestParam?",
                            "What is @Transactional and when should you use it?",
                            "How do you handle exceptions globally in Spring Boot?"),
                    "HARD", List.of(
                            "How do you implement JWT-based stateless authentication in Spring Security?",
                            "What is the N+1 problem in JPA and how do you resolve it?",
                            "How do you implement a custom AuthenticationProvider in Spring Security?",
                            "Explain the difference between EAGER and LAZY loading in JPA and their trade-offs.",
                            "How would you implement rate limiting in a Spring Boot REST API?")),

            "Data Structures", Map.of(
                    "EASY", List.of(
                            "What is the difference between a stack and a queue?",
                            "What is a linked list and how does it differ from an array?",
                            "Explain what a binary tree is.",
                            "What is the time complexity of searching in an unsorted array?",
                            "What is a hash table and what problem does it solve?"),
                    "MEDIUM", List.of(
                            "Explain how a binary search tree (BST) works and its time complexities.",
                            "What is a heap data structure and when would you use it?",
                            "Explain the difference between BFS and DFS traversal.",
                            "What is a trie and what problems does it solve efficiently?",
                            "Explain how HashMap handles collisions."),
                    "HARD", List.of(
                            "Explain AVL trees and the rotations used for rebalancing.",
                            "What is a segment tree and what operations does it support efficiently?",
                            "Explain how a disjoint set (union-find) data structure works.",
                            "Explain the LRU cache implementation using HashMap and LinkedList.",
                            "How does consistent hashing work and why is it used in distributed systems?")),

            "Algorithms", Map.of(
                    "EASY", List.of(
                            "What is the difference between linear search and binary search?",
                            "What is bubble sort and what is its time complexity?",
                            "What does O(n log n) time complexity mean?",
                            "What is recursion? Give a simple example.",
                            "What is Big-O notation used for?"),
                    "MEDIUM", List.of(
                            "Explain merge sort and its time and space complexity.",
                            "What is quicksort and what is its average vs worst-case complexity?",
                            "Explain Dijkstra's algorithm and when you would use it.",
                            "What is dynamic programming and explain the concept of memoization.",
                            "What is the sliding window technique? Give an example problem."),
                    "HARD", List.of(
                            "Explain the Knuth-Morris-Pratt (KMP) string matching algorithm.",
                            "What is the difference between top-down and bottom-up dynamic programming?",
                            "Explain Bellman-Ford algorithm and how it handles negative weights.",
                            "Explain how Kruskal's and Prim's algorithms find the minimum spanning tree.",
                            "What are NP-complete problems? Give examples.")),

            "DBMS", Map.of(
                    "EASY", List.of(
                            "What is a database and what is a DBMS?",
                            "What is the difference between DDL, DML, and DCL in SQL?",
                            "What is a primary key and why is it important?",
                            "What is normalization and why is it used?",
                            "What is the difference between DELETE, TRUNCATE, and DROP?"),
                    "MEDIUM", List.of(
                            "Explain ACID properties in database transactions.",
                            "What is the difference between INNER JOIN, LEFT JOIN, and FULL OUTER JOIN?",
                            "What is database normalization? Explain 1NF, 2NF, and 3NF.",
                            "What is a clustered vs non-clustered index?",
                            "Explain the difference between optimistic and pessimistic concurrency control."),
                    "HARD", List.of(
                            "Explain different types of database isolation levels and their trade-offs.",
                            "What are phantom reads, dirty reads, and non-repeatable reads?",
                            "How does a B+ tree index work internally in a relational database?",
                            "What is database sharding and when would you use it?",
                            "What is write-ahead logging (WAL) in databases?")),

            "Operating Systems", Map.of(
                    "EASY", List.of(
                            "What is an operating system and what are its main functions?",
                            "What is the difference between a process and a thread?",
                            "What is virtual memory?",
                            "What is a system call?",
                            "What is a context switch?"),
                    "MEDIUM", List.of(
                            "Explain the difference between preemptive and non-preemptive scheduling.",
                            "What is a deadlock and what are the four necessary conditions for it?",
                            "Explain paging and segmentation in memory management.",
                            "What is the difference between a mutex and a semaphore?",
                            "What is thrashing in operating systems?"),
                    "HARD", List.of(
                            "Explain the banker's algorithm for deadlock avoidance.",
                            "How does the OS handle page replacement? Explain LRU and FIFO policies.",
                            "What is a hypervisor and what are the types of virtualization?",
                            "What are spinlocks and when should you use them instead of mutexes?",
                            "What is the difference between a monolithic kernel and a microkernel?")),

            "Computer Networks", Map.of(
                    "EASY", List.of(
                            "What is the OSI model? Name its 7 layers.",
                            "What is the difference between TCP and UDP?",
                            "What is an IP address and what is the difference between IPv4 and IPv6?",
                            "What is DNS and how does it work?",
                            "What is the difference between HTTP and HTTPS?"),
                    "MEDIUM", List.of(
                            "Explain the TCP three-way handshake.",
                            "What is the difference between HTTP/1.1 and HTTP/2?",
                            "What is NAT (Network Address Translation) and why is it used?",
                            "What is SSL/TLS and how does it secure communication?",
                            "What is DHCP and how does it assign IP addresses?"),
                    "HARD", List.of(
                            "Explain how BGP (Border Gateway Protocol) works.",
                            "Explain the TCP sliding window mechanism in detail.",
                            "What is a CDN and how does it reduce latency?",
                            "How does HTTPS work end-to-end including certificate validation?",
                            "What are the differences between REST and gRPC?")),

            "System Design", Map.of(
                    "EASY", List.of(
                            "What is scalability in the context of system design?",
                            "What is the difference between horizontal and vertical scaling?",
                            "What is a load balancer and why is it used?",
                            "What is caching and why is it important?",
                            "What is a microservices architecture?"),
                    "MEDIUM", List.of(
                            "How would you design a URL shortener like bit.ly?",
                            "Explain the concept of consistent hashing.",
                            "What is the CAP theorem and how does it affect system design decisions?",
                            "What is database replication and what are its types?",
                            "What is a rate limiter and how would you implement one?"),
                    "HARD", List.of(
                            "How would you design Twitter's tweet feed for 100 million users?",
                            "How would you design a distributed rate limiter?",
                            "Explain the saga pattern for distributed transactions.",
                            "How would you design a real-time collaborative document editor?",
                            "How would you design a distributed ID generator (like Snowflake)?")),

            "OOP Concepts", Map.of(
                    "EASY", List.of(
                            "What are the four pillars of Object-Oriented Programming?",
                            "What is the difference between a class and an object?",
                            "What is encapsulation and why is it important?",
                            "What is inheritance and what problem does it solve?",
                            "What is the difference between an abstract class and an interface?"),
                    "MEDIUM", List.of(
                            "Explain the SOLID principles of object-oriented design.",
                            "What is the difference between composition and inheritance?",
                            "What is the Liskov Substitution Principle with an example?",
                            "What is a design pattern? Name and explain three common patterns.",
                            "Explain the Factory design pattern."),
                    "HARD", List.of(
                            "Explain the Dependency Inversion Principle and how dependency injection implements it.",
                            "What is the difference between the Strategy and State design patterns?",
                            "Explain the Observer pattern and give a real-world use case.",
                            "How does the Decorator pattern differ from inheritance?",
                            "Explain how to apply SOLID principles to avoid hard-to-maintain code.")),

            "SQL", Map.of(
                    "EASY", List.of(
                            "What is the difference between WHERE and HAVING clauses?",
                            "What is a JOIN in SQL? What are the different types?",
                            "What is the purpose of the GROUP BY clause?",
                            "What is the difference between UNION and UNION ALL?",
                            "What does SELECT DISTINCT do?"),
                    "MEDIUM", List.of(
                            "Explain the difference between INNER JOIN, LEFT JOIN, RIGHT JOIN, and FULL OUTER JOIN.",
                            "What is a correlated subquery and how does it differ from a regular subquery?",
                            "What are window functions in SQL? Give an example using ROW_NUMBER().",
                            "How do indexes improve query performance?",
                            "What is a CTE (Common Table Expression) and how is it different from a subquery?"),
                    "HARD", List.of(
                            "How would you find the second-highest salary in a table without using LIMIT/TOP?",
                            "Explain how to detect and remove duplicate rows from a large table efficiently.",
                            "What is the difference between EXISTS and IN in SQL? Which is more performant?",
                            "How would you write a query to find the running total of sales per day?",
                            "How do you handle hierarchical data (like org charts) in SQL?")));

    @Override
    public void run(String... args) {
        seedTopics();
        seedQuestions();
    }

    private void seedTopics() {
        int seeded = 0;
        for (String[] topicData : DEFAULT_TOPICS) {
            if (!topicRepository.existsByNameIgnoreCase(topicData[0])) {
                topicRepository.save(Topic.builder()
                        .name(topicData[0])
                        .description(topicData[1])
                        .build());
                seeded++;
            }
        }
        if (seeded > 0) {
            logger.info("Seeded {} default topics.", seeded);
        }
    }

    private void seedQuestions() {
        int seeded = 0;
        for (Map.Entry<String, Map<String, List<String>>> topicEntry : SEED_QUESTIONS.entrySet()) {
            String topicName = topicEntry.getKey();
            Topic topic = topicRepository.findByNameIgnoreCase(topicName).orElse(null);
            if (topic == null)
                continue;

            for (Map.Entry<String, List<String>> diffEntry : topicEntry.getValue().entrySet()) {
                Question.Difficulty difficulty = Question.Difficulty.valueOf(diffEntry.getKey());
                List<Question> existing = questionRepository.findByTopicAndDifficulty(topic, difficulty);
                List<String> existingTexts = existing.stream()
                        .map(Question::getQuestionText).toList();

                for (String qText : diffEntry.getValue()) {
                    if (!existingTexts.contains(qText)) {
                        questionRepository.save(Question.builder()
                                .topic(topic)
                                .questionText(qText)
                                .difficulty(difficulty)
                                .build());
                        seeded++;
                    }
                }
            }
        }
        if (seeded > 0) {
            logger.info("Seeded {} questions across all topics.", seeded);
        } else {
            logger.info("Questions already seeded, skipping.");
        }
    }
}