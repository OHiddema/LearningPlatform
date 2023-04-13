### Introduction
Each student learns in a different way.
To reach a goal, for example learning a programming language, several targets have to be achieved.
For each target, the student takes a number of sequential steps.
The steps taken and the order of these steps, are different for each student.
The goal of this program is to identify frequently returning patterns in the steps taken by each student.
In a next step, these patterns might be used for an educational recommender system.

### Tools

VMSP is an efficient algorithm for discovering maximal sequential patterns in sequence databases, proposed by Fournier-Viger et al.(2013).
This program uses this algorithm.

### Input

The input for this program is a JSON file.
It contains a number of objects.
Each object represents the steps taken by a student to reach a certain target;
it contains a student number, a target code and a list of steps.

The VMSP algorithm uses a number of parameters, which can be set in the settings.json file:
1. maxPatternLength: the maximum pattern length that will be searched for.
2. maxGap: allows to specify if gaps are allowed in sequential patterns. For example: maxGap =  1 means no gap is allowed. maxGap = 2 means a gap of 1 item between two steps is allowed, etc. If the parameter is not used, by default maxGap is set to +∞.
3. minSupRel: the relative minimum support of a pattern. A value bewtween 0 and 1. For example, if this value is set to 0.5, at least 50% of all the sequnces need to contain this pattern.


### Output

After grouping the objects by target code, the VMSP algorithm is run for each target code and returns the maximal sequential patterns.
For each pattern found, it also returns the number of occurences.
The output is also a JSON file: output.json.
The structure of this file is not explained here (yet).


### Further reading.
Open source data mining library in Java: https://www.philippe-fournier-viger.com/spmf/

VMSP: Efficient Vertical Mining of Maximal Sequential Patterns: https://www.philippe-fournier-viger.com/spmf/VMSP_maximal_sequential_patterns_2014.pdf

Paper on sequential data mining in educational data: https://arxiv.org/ftp/arxiv/papers/2302/2302.01932.pdf