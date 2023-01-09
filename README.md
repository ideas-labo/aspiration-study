# Do Performance Aspirations Matter for Guiding Software Configuration Tuning? An Empirical Investigation under Dual Performance Objectives

This repository contains the data and code for the following paper:

> Tao Chen and Miqing Li. 2023. Do Performance Aspirations Matter for Guiding Software Configuration Tuning? An Empirical Investigation under Dual Performance Objectives. ACM Transactions on Software Engineering and Methodology, doi: 10.1145/3571853.

## Introduction

Configurable software systems can be tuned for better performance. Leveraging on some Pareto optimizers, recent work has shifted from tuning for a single, time-related performance objective to two intrinsically different objectives that assess distinct performance aspects of the system, each with varying aspirations to be satisfied, e.g., *the latency is less than 10s* while *the memory usage is no more than 1GB*. Before we design better optimziers, a crucial engineering decision to make therein is how to handle the performance requirements with clear aspirations in the tuning process. For this, the community takes two alternative optimization models: either quantifying and incorporating the aspirations into the search objectives that guide the tuning, or not considering the aspirations during the search but purely using them in the later decision-making process only. However, despite being a crucial decision that determines how an optimizer can be designed and tailored, there is a rather limited understanding of which optimization model should be chosen under what particular circumstance, and why. In this paper, we seek to close this gap. We do that through a comprehensive empirical study that covers 15 combinations of the state-of-the-art performance requirement patterns that quantify the aspirations, four types of aspiration space, three Pareto optimizers, and eight real-world systems/environments, leading to 1,296 cases of investigation. Our findings reveal that (1) the realism of aspirations is the key factor that determines whether they should be used to guide the tuning; (2) the given patterns and the position of the given realistic aspirations in the objective landscape are less important for the choice, but they do matter to the extents of improvement; (3) the available tuning budget can also influence the choice for unrealistic aspirations but it is insignificant under realistic ones.

## Code


### Requirements

* Java 1.6+

The [`code`](https://github.com/ideas-labo/aspiration-study/tree/main/code) folder contains all the information about the source code. To run the code, find the `AutoRun.java` in the corresponding folder and editing some of the global parameter in the source code to run the relevant experiments.

The [`library`](https://github.com/ideas-labo/aspiration-study/tree/main/library) folder includes all the necessary external libraries for running the code, please include them into the build path when compiling. 

## Dataset

The experiment data reported in the work can be found at: [https://zenodo.org/record/5764258](https://zenodo.org/record/5764258). The naming rules are self-explained. 

Note that the full-scale data size is too large (nearly 60 GB), therefore here we only upload the data of the final results for all runs. To reduce the file size, the basic notations required for text editor software to parse the data file have been removed, thereby the ".rtf" files can only be read by command tools such as `cat` or `vim`.

## Supplementary

The `supplementary-systems.pdf` contains the detailed specification of the systems studied in this work.
