*Done with Patrícia Dias (98546)*

| Metric | Value | Reason | Project Value | Adding code smells |
|--------|:-------:|--------|:---------------:|:--------------------:|
|Critical Issues| >0 | They could prejudice the project, so it is important to discard them. | 25 | 25 |
|Duplicated Lines (%)| >5% | The project should have the minimun duplicated lines possibles. | 1.8% | 1.8 %| 
|Maintainability Rating | worse than B | Since there were 4 people working on a larger project, it was necessary to have maintainability | A | A |
|Reliability Rating | worse than B | Bugs can ruin the project's performance and expose debbugging information to attackers, so they can compromise the reability of the project | C | C |
|Security Rating | worse than B | It is important to fix vulnerabilities to protect the system's information from attackers, so that the system is secure. | D | D |  

Code smells influence maintainability, but after adding some it stayed the same.

**Code smells:** 
- initial - 139
- after - 144

**Code smells added:**
- unused variables
- unused imports
- private variables changed to public