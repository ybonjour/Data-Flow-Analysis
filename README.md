Dafa Flow Analysis
==================

A small tool which takes a control flow graph - described in the DOT/Graphviz format - as an input and executes the selected data flow analysis.
As an output you receive an image of the control flow graph and the result of the executed data flow analysis (entry and exit sets of each program point).

Available Data Flow Analysis
----------------------------
- Available Expressions: For each program point, which expressions must have already been computed and not later modified, on all paths to the program point
- Live variables: For each program point, which variables may be live at the exit from the program point. A variable is live if its content will be read in some paths starting from the program point.
- Strongly live variables: For each program point, which variables may be strongly live at the exit from the program point. A variable is strongly live, if it is live and it is not only used to calculate values of dead variables.
- Reaching Definitions: For each program point, which assignments have been made and not overwritten, when program execution reaches this point along some path.
- Constant Propagation: For each program point, whether or not a variable has a constant value, whenever execution reaches that point.
- Precise constant propagation: The same as constant propagation. But it takes condition expressions into account. If a branch is never executed, this will be considered.

Installation
----------------------------
1. Download and compile the source
2. Install[Graphviz][graphviz]
3. Adapt the paths in graphviz.properties

[graphviz]:http://www.graphviz.org