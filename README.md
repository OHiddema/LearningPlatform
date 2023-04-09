### Introduction
Each student learns in a different way.
To reach a goal, for example learning a programming language, several targets have to be achieved.
For each target, a number of sequential steps are taken.
The steps taken are different for each student.
The goal of this program is to identify frequently returning patterns in these sequential steps to reach a target.
In a next step, these patterns might be used for an educational recommender system.

### Tools

VMSP is an algorithm for discovering maximal sequential patterns in sequence databases, proposed by Fournier-Viger et al.(2013).
This program uses this algorithm.

### Input

The input for this program is a JSON file.
It contains a number of objects.
Each object represents the steps taken by a student to reach a certain target;
it contains a student number, a target code and a list of steps.

The VMSP algorithm uses a number of parameters, which can be set in the settings.json file.

### Output

After grouping the objects by target code, the VMSP algorithm is run for each target code and returns the maximal sequential patterns.
For each pattern found, it also returns the number of occurences.
The output is also a JSON file: output.json.
The structure of this file is not explained here (yet).


### Further reading.
Open source data mining library in Java: https://www.philippe-fournier-viger.com/spmf/

Paper on sequential data mining in educational data: https://arxiv.org/ftp/arxiv/papers/2302/2302.01932.pdf