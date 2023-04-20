### Introduction
Each student learns in a different way.
To reach a goal, for example learning a programming language, several targets have to be achieved.
For each target, the student takes a number of sequential steps.
The steps taken and the order of these steps, are different for each student.
The goal of this program is to identify frequently returning patterns in the steps taken by each student.
These patterns are then used to advise the student which next step to take.

### Tools
VMSP is an efficient algorithm for discovering maximal sequential patterns in sequence databases,
proposed by Fournier-Viger et al.(2013).
This program uses this algorithm.

The VMSP algorithm uses a number of parameters, which can be set in a json file:
1. maxPatternLength: the maximum pattern length that will be searched for.
2. maxGap: allows to specify if gaps are allowed in sequential patterns. For example: maxGap =  1 means no gap is allowed. maxGap = 2 means a gap of 1 item between two steps is allowed, etc. If the parameter is not used, by default maxGap is set to +∞.
3. minSupRel: the relative minimum support of a pattern. A value bewtween 0 and 1. For example, if this value is set to 0.5, at least 50% of all the sequnces need to contain this pattern.

### Frequently returning patterns
For each target, the application keeps track of a list of frequently returning patterns and the number of occurrences for each pattern.
This list can be updated periodically by feeding the application with all the learning data accumulated over time.

### Student recommender system.
For the target the students is working on, the student sends a list of all steps taken for that target and receives a recommendation for the next steps to take.
This recommendation is calculated by trying to (partially) match the sequence of steps taken by the student, with the frequently returning patterns for this target.
It returns zero, one or more recommended steps.

### Further reading.
Open source data mining library in Java: https://www.philippe-fournier-viger.com/spmf/

VMSP: Efficient Vertical Mining of Maximal Sequential Patterns: https://www.philippe-fournier-viger.com/spmf/VMSP_maximal_sequential_patterns_2014.pdf

Paper on sequential data mining in educational data: https://arxiv.org/ftp/arxiv/papers/2302/2302.01932.pdf