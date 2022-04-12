## f)

The project passed the defined quality gate. To pass the quality gate, the project has to pass a set of threshold measures. Even though the project has 1 bug, several code smells and 1 security hotspot, the project passed the set of treshold measures.

## g)

| **Issue** | **Problem Description** | **How to Solve** |
|-----------|-------------------------|------------------|
|Bug|Creating a new Random object each time a random value is needed is inefficient and may produce numbers which are not random depending on the JDK|For better efficiency and randomness, create a single Random (`private Random rand = SecureRandom.getInstanceStrong();`), then store, and reuse it (`int rValue = this.rand.nextInt();`)|
|Code smell (major)|A for loop stop condition should test the loop counter against an invariant value (i.e. one that is true at both the beginning and ending of every loop iteration). Ideally, this means that the stop condition is set to a local variable just before the loop begins. Stop conditions that are not invariant are slightly less efficient, as well as being difficult to understand and maintain, and likely lead to the introduction of errors in the future|Move the updating statement (`i++`) to the for condition|
|Code smell (major|Unused parameters are misleading. Whatever the values passed to such parameters, the behavior will be the same|Remove the unused `subset` parameter from the method `intersects()`|
|Code smell (major|Standard outputs should not be used directly to log anything. It is highly recommended defining and using a dedicated logger|Replace the use of `System.out.println` by a logger (e.g., `logger.log("Betting with three random bets...");`)|