# Statistical-Analytics-Dashboard-Using-Parallel-Algorithms

# Team Members

2520030292 - P. Revanth

2520030505 - P. Sasi Kumar

# Statistical Analytics Dashboard Using Parallel Algorithms

## 3. Supervisor

Supervisor Name

## 4. Abstract

Statistical analytics is the process of collecting, processing, and analyzing numerical data to obtain meaningful insights. Traditional sequential processing may take more time when working with large datasets. This project proposes a **Statistical Analytics Dashboard Using Parallel Algorithms** to efficiently perform statistical computations and analyze the performance of sequential and parallel processing.
The system calculates important descriptive statistics such as **mean, variance, minimum, maximum, cumulative sums, and frequency distributions**. The project uses **parallel reduction algorithms** for operations such as sum, minimum, maximum, mean, and variance, while **parallel prefix sum algorithms** are used to calculate cumulative sums.

## 5. Problem Statement

Statistical analysis is used to understand and extract useful information from numerical datasets. Given a dataset containing `n` numerical values, the objective of this project is to calculate important statistical measures such as **mean, variance, minimum, maximum, cumulative sum, and frequency distribution**.
Traditional sequential algorithms process the dataset one element at a time. Although these algorithms are simple, processing large datasets can require more execution time.
Therefore, this project uses **parallel reduction and parallel prefix sum algorithms** to perform statistical operations efficiently. The system also compares sequential and parallel execution performance to analyze the benefits of parallel processing.

## 6. Objectives

The main objectives of this project are:

* To develop a statistical analytics dashboard for numerical datasets.
* To calculate **mean, variance, minimum, and maximum** values.
* To implement **parallel reduction algorithms** for statistical computations.
* To implement **parallel prefix sum algorithms** for cumulative sum calculations.
* To generate **frequency distributions** for dataset analysis.
* To compare the performance of **sequential and parallel algorithms**.
* To measure and analyze execution time.
* To display statistical results and performance comparisons using graphical reports.
* To demonstrate the advantages of parallel computing for large datasets.

## 2. Design Methodology & Technical Soundness

The project follows a modular design based on parallel statistical algorithms.

### Methodology

Input Numerical Dataset

↓

Data Validation and Preparation

↓

Sequential Statistical Computation

↓

Parallel Reduction Algorithms

↓

Parallel Prefix Sum

↓

Frequency Distribution

↓

Performance Comparison

↓

Graphical Reports

↓

Statistical Analytics Dashboard

### Technical Approach

The system accepts a numerical dataset and performs statistical analysis using both sequential and parallel algorithms.

The **parallel reduction algorithm** is used to calculate operations such as:

* Sum
* Minimum
* Maximum
* Mean
* Variance

The dataset is divided into smaller parts, and partial calculations are performed simultaneously. The partial results are then combined to produce the final result.

The **parallel prefix sum algorithm** is used to calculate cumulative sums.

For example:

Input:

2, 4, 6, 8

Cumulative Sum:

2, 6, 12, 20

The system measures the execution time of both sequential and parallel implementations and displays the comparison using graphical reports.

### Complexity

* **Sequential Time Complexity:** `O(n)`
* **Parallel Reduction Work Complexity:** `O(n)`
* **Parallel Prefix Sum Work Complexity:** `O(n)`
* **Space Complexity:** `O(n)`

The performance improvement of the parallel implementation depends on factors such as dataset size, number of processors, synchronization overhead, and available system resources.

---

## 3. Implementation Progress Against Planned Milestones

| Milestone | Planned Work                          | Status      |
| --------- | ------------------------------------- | ----------- |
| 1         | Topic Selection                       | Completed   |
| 2         | Problem Definition                    | Completed   |
| 3         | Objectives Definition                 | Completed   |
| 4         | Study of Parallel Algorithms          | Completed   |
| 5         | System Design and Methodology         | Completed   |
| 6         | Sequential Statistical Implementation | In Progress |
| 7         | Parallel Reduction Implementation     | In Progress |
| 8         | Parallel Prefix Sum Implementation    | In Progress |
| 9         | Frequency Distribution Module         | In Progress |
| 10        | Performance Testing                   | In Progress |
| 11        | Graphical Dashboard Development       | In Progress |
| 12        | Documentation and README              | In Progress |
| 13        | Final Demonstration                   | Planned     |
| 14        | Final Presentation                    | Planned     |

**Current Phase:** Implementation, Testing, Performance Analysis, and Dashboard Development.

> Update the status according to the actual progress of the team before submission.

---

## 4. Repository Discipline – Commit History, Structure & Documentation

The project repository follows a structured organization to make the code easy to understand, maintain, test, and evaluate.

### Repository Structure

```text
Statistical-Analytics-Dashboard/
│
├── README.md
│
├── src/
│   ├── Main.java
│   ├── SequentialStatistics.java
│   ├── ParallelReduction.java
│   ├── ParallelPrefixSum.java
│   ├── FrequencyDistribution.java
│   └── PerformanceAnalyzer.java
│
├── data/
│   └── SampleDataset.csv
│
├── test/
│   └── TestCases.txt
│
├── docs/
│   ├── Abstract.docx
│   ├── Project_Report.docx
│   └── System_Architecture.png
│
├── graphs/
│   ├── ExecutionTimeComparison.png
│   └── StatisticalAnalysis.png
│
└── PPT/
    └── Statistical_Analytics_Dashboard.pptx
```

### Suggested Commit History

```text
Initial project setup
Added team member details
Added project abstract
Added problem statement
Added project objectives
Added system design and methodology
Implemented sequential statistical calculations
Implemented parallel reduction algorithm
Implemented parallel prefix sum algorithm
Added minimum and maximum calculation
Added mean and variance calculation
Added frequency distribution module
Added performance comparison
Added graphical reports
Added test cases
Updated README documentation
Added project presentation
Final testing and cleanup

## 5. Demonstration, Presentation & Response to Queries

### Demonstration

The project will be demonstrated using a sample numerical dataset.

**Input:**

```text
Dataset: 10, 20, 15, 30, 25, 40, 35
```

The system performs the following calculations:

* Mean
* Variance
* Minimum
* Maximum
* Cumulative Sum
* Frequency Distribution

**Example Output:**

```text
Mean: 25
Minimum: 10
Maximum: 40

Cumulative Sum:
10, 30, 45, 75, 100, 140, 175
```

The system also compares:

```text
Sequential Execution Time
        VS
Parallel Execution Time
```

The results are displayed using graphical reports such as:

* Bar charts
* Line graphs
* Frequency distribution charts
* Execution time comparison graphs

## 6. Individual Contribution & Team Coordination

### Member 1 – Algorithm & Core Implementation

**Name:** P. Revanth

**Roll Number:** 2520030292

Responsibilities:

* Studied statistical analysis algorithms.
* Designed the core system architecture.
* Implemented sequential statistical calculations.
* Worked on mean and variance calculations.
* Implemented minimum and maximum operations.
* Worked on parallel reduction algorithms.
* Verified the correctness of statistical results.
* Contributed to testing and code review.

### Member 2 – Parallel Processing, Dashboard & Documentation

**Name:** P. Sasi Kumar

**Roll Number:** 2520030505

Responsibilities:

* Studied parallel computing concepts.
* Worked on parallel prefix sum implementation.
* Assisted with parallel reduction algorithms.
* Developed frequency distribution analysis.
* Measured sequential and parallel execution times.
* Worked on performance comparison.
* Developed graphical reports and dashboard components.
* Maintained project documentation and README.
* Organized the project repository.
* Prepared the project demonstration and presentation.

### Team Coordination

Both team members contribute to:

* Project discussions
* Algorithm design
* Code implementation
* Code review
* Testing
* Performance analysis
* Documentation
* Dashboard development
* Presentation preparation
* Demonstration
* Viva preparation
* Final project submission

The team follows regular communication and task division to ensure that implementation, testing, visualization, and documentation progress together.

---

## 7. Current Project Status

**Project:** Statistical Analytics Dashboard Using Parallel Algorithms

**Team:** Team 23

**Current Phase:** Implementation, Performance Analysis, and Dashboard Development

### Completed

* Topic selection
* Team formation
* Problem definition
* Project objectives
* Study of descriptive statistics
* Study of parallel reduction
* Study of parallel prefix sum
* System design
* Project methodology
* Initial repository structure

### In Progress

* Sequential statistical implementation
* Parallel reduction implementation
* Parallel prefix sum implementation
* Mean and variance calculation
* Frequency distribution
* Performance testing
* Execution time comparison
* Dashboard development
* Graphical report generation
* README documentation

### Planned

* Complete implementation
* Final testing
* Performance optimization
* Final dashboard integration
* Final demonstration
* Project presentation
* Viva preparation
* Final submission

### Expected Outcome

The completed system will accept large numerical datasets and calculate **mean, variance, minimum, maximum, cumulative sums, and frequency distributions** using both sequential and parallel algorithms.

It will implement **parallel reduction and parallel prefix sum techniques** to improve the efficiency of statistical analysis. The system will compare sequential and parallel execution times and present the results through graphical reports.

The project demonstrates how **parallel algorithms can improve the performance of statistical analytics**, especially when processing large datasets.
